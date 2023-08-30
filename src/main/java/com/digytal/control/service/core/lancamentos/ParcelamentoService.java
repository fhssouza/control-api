package com.digytal.control.service.core.lancamentos;

import com.digytal.control.infra.business.RegistroIncompativelException;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validation;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoEntity;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoDetalheRequest;
import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoNegociacao;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoEntity;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoParcelaCorrecaoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.liquidacao.ParcelaPagamentoEntity;
import com.digytal.control.model.core.lancamentos.parcelamento.liquidacao.ParcelaPagamentoFormaRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.liquidacao.ParcelaPagamentoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaEntity;
import com.digytal.control.repository.acessos.*;
import com.digytal.control.repository.lancamentos.ParcelaPagamentoRepository;
import com.digytal.control.repository.lancamentos.ParcelaRepository;
import com.digytal.control.repository.lancamentos.ParcelamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.digytal.control.infra.commons.validation.Attributes.ID;
import static com.digytal.control.infra.utils.Scale.scale2;

@Service
public class ParcelamentoService {
    @Autowired
    private ParcelaRepository parcelaRepository;
    @Autowired
    private ParcelamentoRepository parcelamentoRepository;
    @Autowired
    private ParcelaPagamentoRepository parcelaPagamentoRepository;
    @Autowired
    private EmpresaContaRepository empresaContaRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private EmpresaContaMeioPagamentoRepository empresaContaFormaPagamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    /*
    @Transactional
    public void realizarPagamento(Integer id, Integer usuario,Integer empresa, ParcelaPagamentoContaRequest request){
        ParcelaPagamentoRequest pagamento = new ParcelaPagamentoRequest();
        pagamento.setFormaPagamento(FormaPagamento.SALDO);
        pagamento.setValor(request.getValor());
        pagamento.setContaBanco(request.getContaBanco());
        realizarPagamento(id, usuario, empresa, pagamento,true);
    }
    */
    @Transactional
    public void realizarPagamento(Integer id, Integer empresa, Integer usuario, List<ParcelaPagamentoFormaRequest> requests){
        for(ParcelaPagamentoFormaRequest request: requests){
            EmpresaContaMeioPagamentoEntity conta = empresaContaFormaPagamentoRepository.findByEmpresaAndMeioPagamento(empresa, request.getMeioPagamento());
            Integer contaBanco = conta==null?null:conta.getConta();

            ParcelaPagamentoRequest pagamento = new ParcelaPagamentoRequest();
            pagamento.setMeioPagamento(request.getMeioPagamento());
            pagamento.setValor(request.getValor());
            pagamento.setContaBanco(contaBanco);

            if(request.getMeioPagamento() == MeioPagamento.CREDITO)
                throw new RegistroIncompativelException("Não é permitido pagar uma parcela com a forma de pagamento CREDITO");

            realizarPagamento(id,usuario, empresa, pagamento,pagamento.getMeioPagamento().isAtualizaSaldo());

        }
    }

    private void realizarPagamento(Integer id,Integer usuario,Integer empresa, ParcelaPagamentoRequest request, boolean gerarLancamento){

        if(!usuarioRepository.hasEmpresa(usuario, empresa))
            throw new RegistroIncompativelException("Este usuário não possui permissão para realizar esta operação");

        ParcelaEntity parcela = parcelaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.PARCELA, ID));

        ParcelamentoEntity parcelamento = parcelamentoRepository.findById(parcela.getParcelamento())
                .orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.LANCAMENTO, ID));

        if(parcelamento.getMeioPagamento()== MeioPagamento.CREDITO)
            throw new RegistroIncompativelException("Não é permitida a compensão manual de parcela de Cartão de Crédito");

        if(!empresaContaRepository.existsById(request.getContaBanco()))
            throw new RegistroNaoLocalizadoException(Entities.CONTA_BANCO_ENTITY, ID);

        if(parcelamento.isFatura())
            throw new RegistroIncompativelException("Esta parcela pertence a um parcelamento tipo FATURA");

        if(parcela.getQuitacao().isEfetuada())
            throw new RegistroIncompativelException("Esta parcela já está com status quitada, N° " + parcela.getNegociacao().getNumeroParcela());

        int compare = Double.compare(parcela.getNegociacao().getValorAtual(), request.getValor());
        if(compare==-1)
            throw new RegistroIncompativelException("O valor recebido precisa ser igual ou inferior ao valor atual");

        Double valor = scale2(BigDecimal.valueOf(request.getValor())).doubleValue();

        //etapa de gerar a linha de pagamento
        ParcelaPagamentoEntity pagamento = new ParcelaPagamentoEntity();
        pagamento.setData(LocalDate.now());
        pagamento.setCompetencia(RegistroData.periodo(pagamento.getData()));
        pagamento.setContaBanco(request.getContaBanco());
        pagamento.setParcela(id);
        pagamento.setParcelamento(parcelamento.getId());
        pagamento.setValor(valor);
        pagamento.setMeioPagamento(request.getMeioPagamento());
        pagamento.setUsuario(usuario);
        parcelaPagamentoRepository.save(pagamento);

        //atualiza o valor da parcela o total do parcelamento
        parcelamento.getNegociacao().setValorAmortizado(scale2(parcelamento.getNegociacao().getValorAmortizado() + valor).doubleValue());
        parcelamento.getNegociacao().setValorAtual(Math.abs(scale2(parcelamento.getNegociacao().getValorAtual() - valor).doubleValue()));
        parcelamentoRepository.save(parcelamento);

        parcela.getNegociacao().setValorAmortizado(scale2(parcela.getNegociacao().getValorAmortizado() + valor).doubleValue());
        parcela.getNegociacao().setValorAtual(Math.abs( scale2(parcela.getNegociacao().getValorAtual() - valor).doubleValue()));

        parcela.getQuitacao().setEfetuada(Validation.isZero(parcela.getNegociacao().getValorAtual()));
        parcela.getQuitacao().setData(parcela.getQuitacao().isEfetuada()?LocalDate.now():null);
        parcelaRepository.save(parcela);

        //gera uma linha de lancamento
        if(gerarLancamento)
            realizarFluxoPagamento(parcelamento, parcela,request, usuario);

    }

    @Autowired
    private PagamentoService lancamentoService;

    private void realizarFluxoPagamento(ParcelamentoEntity parcelamento, ParcelaEntity parcela, ParcelaPagamentoRequest request, Integer usuario ){
        LancamentoRequest lancto = new LancamentoRequest();
        lancto.setTipo(parcelamento.getTipo());
        lancto.setFormaPagamento(request.getMeioPagamento());
        lancto.setContaBancoEmpresa(request.getContaBanco());
        LancamentoDetalheRequest detalhe = new LancamentoDetalheRequest();
        detalhe.setNumeroDocumento(String.format("PARC %04d/%03d", parcelamento.getId(), parcela.getNegociacao().getNumeroParcela()));
        detalhe.setDescricao(String.format("%s PARCLTO/PARC %d/%d", (parcelamento.getTipo() == LancamentoTipo.DESPESA?"PAGTO.":"RECTO."), parcelamento.getId(), parcela.getNegociacao().getNumeroParcela()));
        detalhe.setValor(request.getValor());

        RegistroParteRequest parte = new RegistroParteRequest();
        parte.setCadastro(parcelamento.getPartes().getCadastro());
        parte.setEmpresa(parcelamento.getPartes().getEmpresa());
        parte.setUsuario(usuario);

        lancto.setDetalhe(detalhe);
        lancto.setPartes(parte);
        lancamentoService.incluir(lancto, parcelamento.getContrato());
    }
    @Transactional
    public void realizarCorrecaoMonetariaManual(Integer parcelamentoId, Integer parcelaId, ParcelamentoParcelaCorrecaoRequest request){
        ParcelamentoEntity parcelamentoEntity = parcelamentoRepository.findById(parcelamentoId)
                .orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.PARCELAMENTO, ID));

        ParcelaEntity parcelaEntity = parcelaRepository.findById(parcelaId)
                .orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.PARCELA, ID));


        ParcelamentoNegociacao parcelamento = parcelamentoEntity.getNegociacao();
        ParcelamentoNegociacao parcelamentoParcela = parcelaEntity.getNegociacao();

        //reduz as correcoes por parcela
        parcelamento.setValorMulta(parcelamento.getValorMulta() - parcelamentoParcela.getValorMulta());
        parcelamento.setValorJuros(parcelamento.getValorJuros() - parcelamentoParcela.getValorJuros());
        parcelamento.setValorDesconto(parcelamento.getValorDesconto() - parcelamentoParcela.getValorDesconto());
        parcelamento.setValorCorrecao(parcelamento.getValorCorrecao() - parcelamentoParcela.getValorCorrecao());

        //define uma nova correção a parcela
        parcelamentoParcela.setValorMulta(request.getValorMulta());
        parcelamentoParcela.setValorJuros(request.getValorJuros());
        parcelamentoParcela.setValorDesconto(request.getValorDesconto());
        parcelamentoParcela.setValorCorrecao(request.getValorCorrecao());


        parcelamentoParcela.setValorAtual(scale2(parcelamentoParcela.getValorOriginal()+parcelamentoParcela.getValorMulta()+parcelamentoParcela.getValorJuros()+parcelamentoParcela.getValorCorrecao()-parcelamentoParcela.getValorDesconto()-parcelamentoParcela.getValorAmortizado()).doubleValue());
        parcelaEntity.setObservacao(request.getObservacao());
        parcelaRepository.save(parcelaEntity);

        parcelamento.setValorMulta(parcelamento.getValorMulta() + parcelamentoParcela.getValorMulta());
        parcelamento.setValorJuros(parcelamento.getValorJuros() + parcelamentoParcela.getValorJuros());
        parcelamento.setValorCorrecao(parcelamento.getValorCorrecao() + parcelamentoParcela.getValorCorrecao());
        parcelamento.setValorDesconto(parcelamento.getValorDesconto() + parcelamentoParcela.getValorDesconto());
        parcelamento.setValorAtual(scale2(parcelamento.getValorOriginal()+parcelamento.getValorMulta()+parcelamento.getValorJuros()+parcelamento.getValorCorrecao()-parcelamento.getValorDesconto()-parcelamento.getValorAmortizado()).doubleValue());
        parcelamentoRepository.save(parcelamentoEntity);
    }

}
