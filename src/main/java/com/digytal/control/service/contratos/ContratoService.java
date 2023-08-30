package com.digytal.control.service.contratos;

import com.digytal.control.infra.business.ConfiguracaoContaEmpresaInvalidaException;
import com.digytal.control.infra.business.RegistroIncompativelException;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.model.contrato.contratos.comercializacao.ComercializacaoItemRequest;
import com.digytal.control.model.contrato.contratos.contrato.ContratoItemEntity;
import com.digytal.control.model.contrato.meiopagamento.ContratoPagamentoRequest;
import com.digytal.control.model.core.acessos.empresa.EmpresaEntity;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoEntity;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.contrato.contratos.contrato.ContratoEntity;
import com.digytal.control.model.contrato.meiopagamento.ContratoPagamentoEntity;
import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.cadastros.produto.ProdutoEntity;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoContrato;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoDetalheRequest;
import com.digytal.control.model.core.lancamentos.lancamento.request.ParcelamentoRequest;
import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoNegociacaoRequest;
import com.digytal.control.repository.acessos.EmpresaContaMeioPagamentoRepository;
import com.digytal.control.repository.acessos.EmpresaContaRepository;
import com.digytal.control.repository.acessos.EmpresaRepository;
import com.digytal.control.repository.cadastros.CadastroRepository;
import com.digytal.control.repository.contratos.ContratoRepository;
import com.digytal.control.repository.estoque.ProdutoRepository;
import com.digytal.control.service.core.comum.OperacaoService;
import com.digytal.control.service.core.lancamentos.PagamentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.digytal.control.infra.commons.validation.Attributes.ID;

@Service
public abstract class ContratoService extends OperacaoService {
    @Autowired
    protected ContratoRepository contratoRepository;
    @Autowired
    private PagamentoService lancamentoService;
    @Autowired
    private CadastroRepository cadastroRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EmpresaContaRepository empresaContaRepository;
    @Autowired
    private EmpresaContaMeioPagamentoRepository empresaContaFormaPagamentoRepository;

    protected Integer persist(RegistroParteRequest partes,Integer intermediador, ContratoEntity entity, LancamentoTipo transacaoTipo){
        EmpresaEntity empresaEntity = validarPartes(partes,true);

        entity.setData(RegistroData.of());
        entity.getPartes().setCadastro(partes.getCadastro());
        entity.getPartes().setEmpresa(empresaEntity.getId());
        entity.getPartes().setOrganizacao(empresaEntity.getOrganizacao());
        entity.getPartes().setUsuario(partes.getUsuario());
        entity.setValorPrevisto(calcularValorPrevisto(entity));
        entity.setValorAplicado(calcularValorAplicado(entity));
        entity.setIntermediador(intermediador==null ? entity.getPartes().getUsuario() : intermediador);

        entity.setValorDesconto(0.0);

        entity.getPagamentos().stream().forEach(m->{
            if(!empresaContaFormaPagamentoRepository.existsByEmpresaAndMeioPagamento(partes.getEmpresa(),m.getMeioPagamento()))
                throw new ConfiguracaoContaEmpresaInvalidaException(m.getMeioPagamento().getUpper());
        });

        Double valorPago = entity.getPagamentos().stream().collect(Collectors.summingDouble(o -> o.getValor()));
        if(valorPago.compareTo(entity.getValorAplicado())  < 0 )
            throw new RegistroIncompativelException ("É necessário revisar o rateio de pagamento");

        contratoRepository.save(entity);

        for(ContratoPagamentoEntity pagto : entity.getPagamentos()){
            if(pagto.getMeioPagamento().isAtualizaSaldo()){
                gerarLancamento(entity,pagto, transacaoTipo);
            }else{
                gerarParcelamento(entity,pagto,transacaoTipo);

            }
        }

        return entity.getId();
    }
    protected Double calcularValorPrevisto(ContratoEntity entity){
        Double valor = entity.getItens().stream().collect(Collectors.summingDouble(o -> o.getValorPrevisto()));
        return  valor;
    }
    protected Double calcularValorAplicado(ContratoEntity entity){
        Double valor = entity.getItens().stream().collect(Collectors.summingDouble(o -> o.getValorAplicado()));
        return  valor;
    }
    private String gerarLancamentoDescricao(String contratoTipoDescricao, String formaPagamentoDescricao, Integer contratoId){
        return String.format("%s %04d - %s", contratoTipoDescricao, contratoId,formaPagamentoDescricao);
    }
    private void gerarParcelamento(ContratoEntity entity, ContratoPagamentoEntity pagto, LancamentoTipo transacaoTipo){
        ParcelamentoRequest parcelamento = new ParcelamentoRequest();
        parcelamento.setTipo(transacaoTipo);
        parcelamento.setFormaPagamento(pagto.getMeioPagamento());
        parcelamento.setPartes(RegistroParteRequest.of(entity.getPartes().getEmpresa(), entity.getPartes().getCadastro(), entity.getPartes().getUsuario()));
        LancamentoDetalheRequest detalhe = new LancamentoDetalheRequest();
        detalhe.setNumeroDocumento(""+System.currentTimeMillis());
        detalhe.setDescricao( gerarLancamentoDescricao(entity.getDescricao(),pagto.getMeioPagamento().getDescricao(), entity.getId() ));
        detalhe.setValor(pagto.getValor());
        parcelamento.setDetalhe(detalhe);
        parcelamento.setNegociacao(ParcelamentoNegociacaoRequest.of(pagto.getNumeroParcelas(), pagto.getDataPrimeiroVencimento()));

        LancamentoContrato contrato = new LancamentoContrato();
        contrato.setId(entity.getId());
        contrato.setTipo(entity.getTipo().getId());
        lancamentoService.incluir(parcelamento, contrato);
    }
    private void gerarLancamento(ContratoEntity entity, ContratoPagamentoEntity pagto, LancamentoTipo transacaoTipo){
        LancamentoRequest lancamento = new LancamentoRequest();
        lancamento.setFormaPagamento(pagto.getMeioPagamento());
        lancamento.setTipo(transacaoTipo);
        lancamento.setPartes(RegistroParteRequest.of(entity.getPartes().getEmpresa(), entity.getPartes().getCadastro(), entity.getPartes().getUsuario()));
        LancamentoDetalheRequest detalhe = new LancamentoDetalheRequest();
        detalhe.setNumeroDocumento(""+System.currentTimeMillis());
        detalhe.setDescricao( gerarLancamentoDescricao(entity.getDescricao(),pagto.getMeioPagamento().getDescricao(), entity.getId() ));
        detalhe.setValor(pagto.getValor());
        lancamento.setDetalhe(detalhe);

        EmpresaContaMeioPagamentoEntity conta = empresaContaFormaPagamentoRepository.findByEmpresaAndMeioPagamento(entity.getPartes().getEmpresa(), pagto.getMeioPagamento());
        Integer contaBanco = conta==null?null:conta.getConta();
        lancamento.setContaBancoEmpresa(contaBanco);
        LancamentoContrato contrato = new LancamentoContrato();
        contrato.setId(entity.getId());
        contrato.setTipo(entity.getTipo().getId());
        lancamentoService.incluir(lancamento, contrato);
    }
    protected ProdutoEntity findProduto(Integer id){
        ProdutoEntity entity = produtoRepository.findById(id)
                .orElseThrow(()->  new RegistroNaoLocalizadoException(Entities.PRODUTO_ENTITY, ID));
        return entity;
    }
    protected List<ContratoItemEntity> itens(List<ComercializacaoItemRequest> itens){
        return itens.stream().map(i->{
            ContratoItemEntity item= new ContratoItemEntity();
            BeanUtils.copyProperties(i,item);
            item.setValorPrevisto(i.getProduto().getValor() * i.getQuantidade());
            item.setValorAplicado(i.getValorAplicado());
            return item;
        }).collect(Collectors.toList());
    }
    protected List<ContratoPagamentoEntity> pagamentos(Double valorTotal, List<ContratoPagamentoRequest> itens){
        List<ContratoPagamentoEntity> pagamentos= itens.stream().map(i->{
            ContratoPagamentoEntity item= new ContratoPagamentoEntity();
            item.setValor(i.getValor());
            item.setValorParcela(i.getValorParcela());
            item.setMeioPagamento(i.getMeioPagamento());
            item.setNumeroParcelas(i.getNumeroParcelas()==null?1:i.getNumeroParcelas());
            item.setDataPrimeiroVencimento(i.getDataPrimeiroVencimento()==null? LocalDate.now():i.getDataPrimeiroVencimento());
            return item;
        }).collect(Collectors.toList());

        if(pagamentos==null || pagamentos.size()==0)
            pagamentos.add(pagamentoAVista(valorTotal));
        return pagamentos;
    }
    protected ContratoPagamentoEntity pagamentoAVista(Double valor){
        ContratoPagamentoEntity pagamento = new ContratoPagamentoEntity();
        pagamento.setDataPrimeiroVencimento(LocalDate.now());
        pagamento.setValor(valor);
        pagamento.setValorParcela(valor);
        pagamento.setNumeroParcelas(1);
        pagamento.setMeioPagamento(MeioPagamento.DINHEIRO);
        return pagamento;
    }

}
