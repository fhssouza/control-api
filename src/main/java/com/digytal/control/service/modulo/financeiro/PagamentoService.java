package com.digytal.control.service.modulo.financeiro;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.business.SaldoInsuficienteException;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.model.comum.MeioPagamento;
import com.digytal.control.model.modulo.acesso.empresa.aplicacao.AplicacaoTipo;
import com.digytal.control.model.modulo.acesso.empresa.conta.ContaEntity;
import com.digytal.control.model.modulo.acesso.empresa.pagamento.FormaPagamentoEntity;
import com.digytal.control.model.modulo.financeiro.Valor;
import com.digytal.control.model.modulo.financeiro.pagamento.PagamentoEntity;
import com.digytal.control.model.modulo.financeiro.transacao.TransacaoRateioRequest;
import com.digytal.control.model.modulo.financeiro.transacao.TransacaoRequest;
import com.digytal.control.repository.modulo.acesso.empresa.ContaRepository;
import com.digytal.control.repository.modulo.acesso.empresa.FormaPagamentoRepository;
import com.digytal.control.service.comum.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.digytal.control.infra.commons.validation.Attributes.ID;

@Service
@Slf4j
public class PagamentoService extends AbstractService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private ContaRepository contaRepository;
    public PagamentoEntity criarPagamento(AplicacaoTipo tipo, TransacaoRateioRequest rateio){
        Double valor = rateio.getValorPago();
        MeioPagamento meioPagamento = rateio.getMeioPagamento();
        FormaPagamentoEntity formaPagamento = formaPagamentoRepository.findByEmpresaAndMeioPagamentoAndNumeroParcelas(requestInfo.getEmpresa(),meioPagamento,1);
        if(formaPagamento==null)
            throw new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID);

        ContaEntity conta = contaRepository.findById(formaPagamento.getConta()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID));

        if(tipo == AplicacaoTipo.DESPESA &&  valor > conta.getSaldo())
            throw new SaldoInsuficienteException();

        PagamentoEntity entity = new PagamentoEntity();
        entity.setMeioPagamento(meioPagamento);
        atualizarPagamentoSaldoConta(tipo, meioPagamento, valor, entity);

        entity.setValor(Valor.of(tipo, valor));
        entity.setConta(conta.getId());
        conta.setSaldo(conta.getSaldo() + entity.getValor().getValorOperacional());

        contaRepository.save(conta);
        return  entity;
    }
    private void atualizarPagamentoSaldoConta(AplicacaoTipo tipo, MeioPagamento meioPagamento, Double valor, PagamentoEntity entity){
        FormaPagamentoEntity formaPagamento = formaPagamentoRepository.findByEmpresaAndMeioPagamentoAndNumeroParcelas(requestInfo.getEmpresa(),meioPagamento,1);
        if(formaPagamento==null)
            throw new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID);

        ContaEntity conta = contaRepository.findById(formaPagamento.getConta()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID));
        if(tipo == AplicacaoTipo.DESPESA &&  valor > conta.getSaldo())
            throw new SaldoInsuficienteException();

        entity.setValor(Valor.of(tipo, valor));
        entity.setConta(conta.getId());
        conta.setSaldo(conta.getSaldo() + entity.getValor().getValorOperacional());

        contaRepository.save(conta);
    }
    private void definirPagamentoPadrao(TransacaoRequest request){
        if(request.getFormasPagamento()==null || request.getFormasPagamento().size()==0){
            request.setFormasPagamento(definirPagamentoPadrao(request.getValor()));
        }
    }
    private List<TransacaoRateioRequest> definirPagamentoPadrao(Double valor){
        TransacaoRateioRequest rateio = new TransacaoRateioRequest();
        rateio.setMeioPagamento(MeioPagamento.DINHEIRO);
        rateio.setValorPago(valor);
        rateio.setValorOriginal(valor);
        rateio.setTaxaPagamento(0.0);
        return Collections.singletonList(rateio);
    }

    /*
    @Autowired
    private PagamentoRepository repository;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ParcelamentoService parcelamentoService;

    @Transactional
    public void incluir(AplicacaoTipo tipo, PagamentoRequest request, String numeroContrato){
        try {
            Validations.build(DESCRICAO, VALOR).notEmpty().check(request);
            request.setCadastro(Definition.seNulo(request.getCadastro(),1));
            request.setTitulo(Definition.seNuloOuVazio(request.getTitulo(),request.getDescricao(), 40));
            Validations.build(TITULO).notEmpty().maxLen(40).check(request);
            Validations.build(DESCRICAO).maxLen(200).check(request);

            definirPagamentoPadrao(request);

            Double total = request.getFormasPagamento().stream().mapToDouble(f -> f.getValorPago()).sum();
            if( Calculos.compararIgualMenorZero(request.getValor()))
                throw new RegistroIncompativelException("O valor do pagamento não pode ser menor ou igual a zero");

            if ( ! Calculos.compararIgualdade(total, request.getValor()))
                throw new RegistroIncompativelException("Os valores informados no rateiro de pagamento diferem do valor total");

            for (TransacaoRateioRequest rateio : request.getFormasPagamento()) {
                if (Arrays.stream(new MeioPagamento[]{MeioPagamento.SALDO, MeioPagamento.COMPENSACAO}).anyMatch(rateio.getMeioPagamento()::equals))
                    throw new RegistroIncompativelException("Não é permitido utilizar este meio de pagamento");

                rateio.setValorOriginal(Calculos.seNuloOuZero(rateio.getValorOriginal(), rateio.getValorPago()));
                rateio.setTaxaPagamento(Calculos.seNuloZera(rateio.getTaxaPagamento()));
                if (rateio.getMeioPagamento().isInstantaneo()) {
                    PagamentoEntity entity = new PagamentoEntity();
                    build(tipo, entity, request,numeroContrato);
                    entity.setMeioPagamento(rateio.getMeioPagamento());
                    atualizarPagamentoSaldoConta(tipo, rateio.getMeioPagamento(), rateio.getValorPago(),entity);
                    repository.save(entity);
                } else {
                    ParcelamentoRequest parcelamento = new ParcelamentoRequest();
                    BeanUtils.copyProperties(request, parcelamento);
                    parcelamentoService.gerarParcelamento(tipo, parcelamento, rateio,numeroContrato);


                   // if (MeioPagamento.CREDITO == rateio.getMeioPagamento()) {
                     //   atualizarContaCredito(tipo, rateio.getMeioPagamento(), rateio.getValorPago());
                    //}


                }
            }
        }catch (BusinessException ex){
            log.warn(BusinessException.logMessage(ex));
            throw ex;
        }catch (Exception ex){
            log.error("Erro ao tentar realizar um pagamento",ex);
            throw new ErroNaoMapeadoException();
        }
    }
    private void definirPagamentoPadrao(PagamentoRequest request){
        if(request.getFormasPagamento()==null || request.getFormasPagamento().size()==0){
            request.setFormasPagamento(definirPagamentoPadrao(request.getValor()));
        }
    }
    public List<TransacaoRateioRequest> definirPagamentoPadrao(Double valor){
        TransacaoRateioRequest rateio = new TransacaoRateioRequest();
        rateio.setMeioPagamento(MeioPagamento.DINHEIRO);
        rateio.setValorPago(valor);
        rateio.setValorOriginal(valor);
        rateio.setTaxaPagamento(0.0);
        return Collections.singletonList(rateio);
    }
    private void atualizarPagamentoSaldoConta(AplicacaoTipo tipo, MeioPagamento meioPagamento, Double valor, PagamentoEntity entity){
        FormaPagamentoEntity formaPagamento = formaPagamentoRepository.findByEmpresaAndMeioPagamentoAndNumeroParcelas(requestInfo.getEmpresa(),meioPagamento,1);
        if(formaPagamento==null)
            throw new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID);

        ContaEntity conta = contaRepository.findById(formaPagamento.getConta()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID));
        if(tipo == AplicacaoTipo.DESPESA &&  valor > conta.getSaldo())
            throw new SaldoInsuficienteException();

        entity.setValor(Valor.of(tipo, valor));
        entity.setConta(conta.getId());
        conta.setSaldo(conta.getSaldo() + entity.getValor().getValorOperacional());

        contaRepository.save(conta);
    }

    */

    /*
    private void atualizarContaCredito(AplicacaoTipo tipo, MeioPagamento meioPagamento, Double valor){
        Double valorOperacional = Calculos.aplicarEscala4(valor);
        if(tipo == AplicacaoTipo.DESPESA ){
            FormaPagamentoEntity formaPagamento = formaPagamentoRepository.findByEmpresaAndMeioPagamentoAndNumeroParcelas(requestInfo.getEmpresa(),meioPagamento,1);
            if(formaPagamento==null)
                throw new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID);

            ContaEntity conta = contaRepository.findById(formaPagamento.getConta()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID));

            //comparar mais coeso
            if(valorOperacional > conta.getSaldo())
                throw new SaldoInsuficienteException();

            conta.setSaldo(conta.getSaldo() +  ( Calculos.negativar(valorOperacional,AplicacaoTipo.DESPESA==tipo) ));
            contaRepository.save(conta);
        }
    }
    */

}
