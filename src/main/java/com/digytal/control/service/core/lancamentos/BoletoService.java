package com.digytal.control.service.core.lancamentos;

import com.digytal.control.infra.business.CampoObrigatorioException;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validation;
import com.digytal.control.integracao.asaas.IntegradorPagamento;
import com.digytal.control.integracao.asaas.model.BoletoRequest;
import com.digytal.control.integracao.asaas.model.BoletoResponse;
import com.digytal.control.integracao.asaas.model.Cadastro;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoEntity;
import com.digytal.control.model.core.cadastros.cadastro.CadastroEntity;
import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoDetalheRequest;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoEntity;
import com.digytal.control.model.core.lancamentos.parcelamento.liquidacao.ParcelaPagamentoEntity;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaBoleto;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaBoletoStatus;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaEntity;
import com.digytal.control.repository.acessos.EmpresaContaMeioPagamentoRepository;
import com.digytal.control.repository.cadastros.CadastroRepository;
import com.digytal.control.repository.lancamentos.ParcelaPagamentoRepository;
import com.digytal.control.repository.lancamentos.ParcelaRepository;
import com.digytal.control.repository.lancamentos.ParcelamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.digytal.control.infra.commons.validation.Attributes.ID;
import static com.digytal.control.infra.utils.Scale.scale2;

@Service
public class BoletoService {
    @Autowired
    private ParcelaRepository parcelaRepository;
    @Autowired
    private ParcelamentoRepository parcelamentoRepository;
    @Autowired
    private CadastroRepository cadastroRepository;
    @Autowired
    private IntegradorPagamento integrador;
    @Autowired
    private PagamentoService lancamentoService;
    @Autowired
    private EmpresaContaMeioPagamentoRepository empresaContaMeioPagamentoRepository;
    @Autowired
    private ParcelaPagamentoRepository parcelaPagamentoRepository;

    @Transactional
    public BoletoResponse gerarBoleto(Integer parcelaId,Double valorBoleto){
        return gerarBoleto(parcelaId, valorBoleto,null);
    }
    @Transactional
    public BoletoResponse gerarBoleto(Integer parcelaId, Double valorBoleto, LocalDate dataVencimento){
        ParcelaEntity parcela = parcelaRepository.findById(parcelaId).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.PARCELA, ID));
        ParcelamentoEntity parcelamento = parcelamentoRepository.findById(parcela.getParcelamento()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.PARCELAMENTO, ID));
        CadastroEntity cadastro = cadastroRepository.findById(parcelamento.getPartes().getCadastro()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.CADASTRO_ENTITY, ID));

        Double valor = valorBoleto == null || valorBoleto.equals(0.0)? parcela.getNegociacao().getValorAtual(): valorBoleto;
        dataVencimento = dataVencimento==null? parcela.getNegociacao().getDataVencimento() : dataVencimento;
        return gerarBoleto(cadastro, parcela, dataVencimento, valor);
    }
    private BoletoResponse gerarBoleto(CadastroEntity cadastro, ParcelaEntity parcela, LocalDate dataVencimento, Double valor){
        ParcelaBoleto boleto = parcela.getBoleto();
        if(boleto.isSolicitado() && boleto.getStatus()==ParcelaBoletoStatus.EMITIDO){
            BoletoResponse response = new BoletoResponse();
            return response;
        }
        parcela.getBoleto().setSolicitado(true);
        parcela.getBoleto().setStatus(ParcelaBoletoStatus.SOLICITADO);
        parcela.getNegociacao().setDataVencimento(dataVencimento);


        if(cadastro.getEmail()==null || cadastro.getCpfCnpj()==null || !Validation.cpfCnpj(cadastro.getCpfCnpj()))
            throw new CampoObrigatorioException("É necessário informar o CPF/CNPJ e e-mail no cadastro do cliente");

        Cadastro cad = new Cadastro();
        cad.setCpfCnpj(cad.getCpfCnpj());
        cad.setId(cadastro.getCodigoIntegracao());
        if(cadastro.getCodigoIntegracao()==null || cadastro.getCodigoIntegracao().length()==0){
            cad.setDeleted(false);
            cad.setEmail(cadastro.getEmail());
            cad.setName(cadastro.getNomeFantasia());
            cad.setCpfCnpj(cadastro.getCpfCnpj());
            cad = integrador.cadastrar(cad);
            cadastro.setCodigoIntegracao(cad.getId());
            integrador.desativarNotificacoes(cadastro.getCodigoIntegracao());
            cadastroRepository.save(cadastro);
        }
        BoletoRequest boletoRequest = new BoletoRequest();
        boletoRequest.setDescription(parcela.getDescricao());
        boletoRequest.setValue(valor);
        boletoRequest.setDueDate(dataVencimento.toString());
        boletoRequest.setCustomer(cadastro.getCodigoIntegracao());
        boletoRequest.setExternalReference(parcela.getId().toString());

        BoletoResponse response = integrador.gerarBoleto(boletoRequest);
        if(response!=null){
            boleto.setStatus(ParcelaBoletoStatus.EMITIDO);
            boleto.setNumeroAutorizacao(response.getId());
            boleto.setUrlImpressao(response.getBankSlipUrl());
            parcelaRepository.save(parcela);
        }
        System.out.println(response);

        return response;
    }
    @Transactional
    public void compensar(ParcelaEntity parcela){

        BoletoResponse response = integrador.obterBoleto(parcela.getBoleto().getNumeroAutorizacao());
        ParcelamentoEntity parcelamento = parcelamentoRepository.findById(parcela.getParcelamento()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.PARCELAMENTO,ID));

        LancamentoRequest lancamento = new LancamentoRequest();
        lancamento.setFormaPagamento(MeioPagamento.BOLETO);
        lancamento.setTipo(LancamentoTipo.RECEITA);
        lancamento.setPartes(RegistroParteRequest.of(parcelamento.getPartes().getEmpresa(), parcelamento.getPartes().getCadastro(), 1)); //1=USUARIO SISTEMA
        LancamentoDetalheRequest detalhe = new LancamentoDetalheRequest();
        detalhe.setNumeroDocumento("asaas_"+response.getNossoNumero());
        detalhe.setDescricao( String.format("Comp.Aut.Bol Parc.:%d/%d", parcelamento.getId(), parcela.getId()) );
        detalhe.setValor(response.getNetValue());
        lancamento.setDetalhe(detalhe);

        EmpresaContaMeioPagamentoEntity conta = empresaContaMeioPagamentoRepository.findByEmpresaAndMeioPagamento(parcelamento.getPartes().getEmpresa(), MeioPagamento.BOLETO);
        Integer contaBanco = conta==null?null:conta.getConta();
        lancamento.setContaBancoEmpresa(contaBanco);
        lancamentoService.incluir(lancamento, null);

        ParcelaBoleto boleto = parcela.getBoleto();
        boleto.setDataCompensacao(response.getPaymentDate());
        boleto.setDataPagamento(response.getClientPaymentDate());
        boleto.setValorCompensado(response.getNetValue());
        boleto.setStatus(ParcelaBoletoStatus.PAGO);

        //atualiza o valor da parcela o total do parcelamento
        parcelamento.getNegociacao().setValorAmortizado(scale2(parcelamento.getNegociacao().getValorAmortizado() + response.getValue()).doubleValue());
        parcelamento.getNegociacao().setValorAtual(Math.abs(scale2(parcelamento.getNegociacao().getValorAtual() - response.getValue()).doubleValue()));
        parcelamentoRepository.save(parcelamento);

        parcela.getNegociacao().setValorAmortizado(scale2(parcela.getNegociacao().getValorAmortizado() + response.getValue()).doubleValue());
        parcela.getNegociacao().setValorAtual(Math.abs( scale2(parcela.getNegociacao().getValorAtual() - response.getValue()).doubleValue()));

        parcela.getQuitacao().setEfetuada(Validation.isZero(parcela.getNegociacao().getValorAtual()));
        parcela.getQuitacao().setData(parcela.getQuitacao().isEfetuada()?LocalDate.now():null);
        parcelaRepository.save(parcela);

        //etapa de gerar a linha de pagamento
        ParcelaPagamentoEntity pagamento = new ParcelaPagamentoEntity();
        pagamento.setData(LocalDate.now());
        pagamento.setCompetencia(RegistroData.periodo(pagamento.getData()));
        pagamento.setContaBanco(contaBanco);
        pagamento.setParcela(parcela.getId());
        pagamento.setParcelamento(parcelamento.getId());
        pagamento.setValorOrigianal(response.getValue());
        pagamento.setValor(response.getNetValue());
        pagamento.setMeioPagamento(MeioPagamento.BOLETO);
        pagamento.setUsuario(parcelamento.getPartes().getUsuario());
        pagamento.setNumeroAutorizacao(boleto.getNumeroAutorizacao());
        parcelaPagamentoRepository.save(pagamento);
    }

}
