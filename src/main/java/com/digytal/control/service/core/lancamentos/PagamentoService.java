package com.digytal.control.service.core.lancamentos;
import com.digytal.control.infra.business.*;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.model.core.acessos.empresa.EmpresaEntity;
import com.digytal.control.model.core.acessos.empresa.conta.EmpresaContaEntity;
import com.digytal.control.model.core.acessos.empresa.conta.EmpresaContaFatura;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoEntity;
import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.core.comum.RegistroPartes;
import com.digytal.control.model.core.lancamentos.LancamentoValor;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoContrato;
import com.digytal.control.model.core.lancamentos.lancamento.request.TransacaoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaBoletoStatus;
import com.digytal.control.model.core.lancamentos.transferencia.TransferenciaBalcao;
import com.digytal.control.model.core.lancamentos.transferencia.TransferenciaRequest;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoDetalheRequest;
import com.digytal.control.model.core.lancamentos.lancamento.PagamentoEntity;
import com.digytal.control.model.core.lancamentos.lancamento.request.ParcelamentoRequest;
import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoNegociacao;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoEntity;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoNegociacaoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaEntity;
import com.digytal.control.repository.acessos.EmpresaContaMeioPagamentoRepository;
import com.digytal.control.repository.acessos.EmpresaContaRepository;
import com.digytal.control.repository.lancamentos.PagamentoRepository;
import com.digytal.control.repository.lancamentos.ParcelamentoRepository;
import com.digytal.control.service.core.comum.OperacaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.digytal.control.infra.commons.validation.Attributes.*;
import static com.digytal.control.infra.utils.Scale.scale2;

@Service
public class PagamentoService extends OperacaoService {
    @Autowired
    private EmpresaContaRepository empresaContaRepository;
    @Autowired
    private PagamentoRepository lancamentoRepository;
    @Autowired
    private ParcelamentoRepository parcelamentoRepository;
    @Autowired
    private EmpresaContaMeioPagamentoRepository empresaContaMeioPagamentoRepository;
    @Transactional
    public void transferir(TransferenciaRequest request){
        confirmarTransferencia(request);
    }
    @Transactional
    public void transferir(TransferenciaBalcao request, Double valor){
        EmpresaContaMeioPagamentoEntity contaDebito = empresaContaMeioPagamentoRepository.findByEmpresaAndMeioPagamento(request.getPartes().getEmpresa(), MeioPagamento.DEBITO);
        if(contaDebito==null)
            throw new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, EMPRESA);

        TransferenciaRequest transferencia = new TransferenciaRequest();
        BeanUtils.copyProperties(request,transferencia);
        transferencia.setContaOrigem(contaDebito.getConta());

        LancamentoDetalheRequest detalhe = new LancamentoDetalheRequest();
        detalhe.setDescricao("Transf. Depósito");
        detalhe.setNumeroDocumento(""+System.currentTimeMillis());
        detalhe.setValor(valor);
        transferencia.setDetalhe(detalhe);
        confirmarTransferencia(transferencia);
    }
    private void confirmarTransferencia(TransferenciaRequest request){
        Validations.build(CONTA_ORIGEM, CONTA_DESTINO).notEmpty().check(request);
        if(request.getContaOrigem().equals(request.getContaDestino()))
            throw new RegistroIncompativelException("A contas de origem e destino precisam ser diferentes");

        Double valor = request.getDetalhe().getValor();
        if(valor ==null || valor <= 0.0)
            throw new ValorNegativoException(VALOR);

        RegistroParteRequest partes = request.getPartes();

        EmpresaEntity empresaEntity = validarPartes(partes,false);

        LancamentoDetalheRequest detalhe = request.getDetalhe();
        PagamentoEntity entityDespesa = new PagamentoEntity();
        entityDespesa.setNumeroDocumento(detalhe.getNumeroDocumento()==null?"DPTR": detalhe.getNumeroDocumento());
        entityDespesa.setDescricao(detalhe.getDescricao()==null?"DESPESA POR TRANSFERENCIA": detalhe.getDescricao());
        entityDespesa.setData(RegistroData.of());
        entityDespesa.setTipo(LancamentoTipo.DESPESA);
        RegistroPartes parte = new RegistroPartes();
        parte.setEmpresa(partes.getEmpresa());
        parte.setOrganizacao(empresaEntity.getOrganizacao());
        parte.setCadastro(null);
        parte.setUsuario(request.getPartes().getUsuario());
        entityDespesa.setPartes(parte);

        executarFluxoLancamento(request.getContaOrigem(), valor, entityDespesa);
        lancamentoRepository.save(entityDespesa);

        PagamentoEntity entityReceita = new PagamentoEntity();
        entityReceita.setTipo(LancamentoTipo.RECEITA);
        entityReceita.setData(RegistroData.of());
        entityReceita.setNumeroDocumento(detalhe.getNumeroDocumento()==null?"RPTR": detalhe.getNumeroDocumento());
        entityReceita.setDescricao(detalhe.getDescricao()==null?"RECEITA POR TRANSFERENCIA": detalhe.getDescricao());
        entityReceita.setPartes(parte);

        executarFluxoLancamento(request.getContaDestino(), valor, entityReceita);
        lancamentoRepository.save(entityReceita);

    }
    @Transactional
    public Integer incluir(TransacaoRequest request, LancamentoContrato contrato){

        Validations.build(EMPRESA,CADASTRO,USUARIO).notEmpty().check(request.getPartes());
        Validations.build(NUMERO_DOCUMENTO,DESCRICAO,VALOR).notEmpty().check(request.getDetalhe());

        RegistroParteRequest parte = request.getPartes();
        EmpresaEntity empresaEntity = validarPartes(parte,true);

        if(request.getDetalhe().getValor() <= 0.0)
            throw new ValorNegativoException(VALOR);

        LancamentoDetalheRequest detalhe = request.getDetalhe();
        PagamentoEntity entity = new PagamentoEntity();

        //definirPlanoConta(entity,request.getPlanoContasId(), request.getPlanoContaTipo(), empresaEntity.getOrganizacao());

        entity.setDescricao(detalhe.getDescricao());
        entity.setNumeroDocumento(detalhe.getNumeroDocumento());
        entity.setData(RegistroData.of());

        RegistroPartes partes = new RegistroPartes();
        partes.setUsuario(parte.getUsuario());
        partes.setCadastro(parte.getCadastro());

        partes.setOrganizacao(empresaEntity.getOrganizacao());
        partes.setEmpresa(empresaEntity.getId());

        entity.setPartes(partes);

        entity.setContrato(contrato);
        entity.setTipo(request.getTipo());
        entity.setMeioPagamento(request.getFormaPagamento());

        if(request instanceof ParcelamentoRequest) {
            ParcelamentoNegociacaoRequest parcelamento = ((ParcelamentoRequest) request).getNegociacao();
            Validations.build(DATA_PRIMEIRO_VENCTO,NUMERO_PARCELAS).notEmpty().check(parcelamento);

            ParcelamentoEntity parcelamentoEntity = new ParcelamentoEntity();
            BeanUtils.copyProperties(entity, parcelamentoEntity);

            ParcelamentoRequest pr = (ParcelamentoRequest) request;
            executarFluxoParcelamento(request.getDetalhe().getValor(), pr.getNegociacao(), parcelamentoEntity, null, pr.isFatura());

            parcelamentoRepository.save(parcelamentoEntity);
        }else {
            LancamentoRequest lr = (LancamentoRequest) request;

            executarFluxoLancamento(lr.getContaBancoEmpresa(), request.getDetalhe().getValor(), entity);
            lancamentoRepository.save(entity);
        }
        return entity.getId() ;
    }
    /*
    private void definirPlanoConta(LancamentoEntity entity, TransacaoTipo tipo, Integer organizacao ){
        if(planoContasId ==null && tipo == null)
            throw new CampoObrigatorioException("É necessário informar o plano de contas ou o tipo de lançamento R=Receita ou D=Despesa");

        PlanoContaEntity planoContaEntity = null;
        if(planoContasId ==null) {
            planoContaEntity = planoContaRepository.findPrincipal(organizacao, tipo);
            if(planoContaEntity==null)
                throw new RegistroIncompativelException("Não existe um plano de contas definido como padrão para o tipo movimento " + tipo.name());
        }else {
            planoContaEntity = planoContaRepository.findById(planoContasId)
                    .orElseThrow(()->  new RegistroNaoLocalizadoException(Entities.PLANO_CONTA_ENTITY, ID));
        }
        if(tipo != planoContaEntity.getTipo())
            throw new RegistroIncompativelException("O parâmetro plano de contas não correponde a uma " + tipo.name());

        entity.setPlanoConta(planoContaEntity.getId());
        entity.setPlanoContaTipo(planoContaEntity.getTipo().getId());
    }

     */


    private void executarFluxoLancamento(Integer contaBancoEmpresa, Double valor, PagamentoEntity entity){
        EmpresaContaEntity contaBanco =null;
        if(contaBancoEmpresa!=null) {
            contaBanco = empresaContaRepository.findById(contaBancoEmpresa)
                    .orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID));
        }
        else{
            contaBanco = empresaContaRepository.findByEmpresaAndContaPadraoAndContaCredito(entity.getPartes().getEmpresa(), true,false)
                    .orElseThrow(() -> new ContaPadraoInexistenteException());
        }
        if(!contaBanco.getEmpresa().equals(entity.getPartes().getEmpresa()))
            throw new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID);

        //if(contaBanco.isContaCredito())
          //  throw new RegistroIncompativelException("Conta do tipo CRÉDITO não pode ser selecionada no fluxo de lançamento");

        LancamentoTipo tipo = entity.getTipo();
        if(tipo == LancamentoTipo.DESPESA &&  valor > contaBanco.getSaldo())
            throw new SaldoInsuficienteException();

        entity.setValor(LancamentoValor.of(tipo, valor, contaBanco.getSaldo()));
        entity.setContaBanco(contaBanco.getId());
        contaBanco.setSaldo(contaBanco.getSaldo() + entity.getValor().getValorOperacional());
        empresaContaRepository.save(contaBanco);
    }

    private void executarFluxoParcelamento(Double valor, ParcelamentoNegociacaoRequest request, ParcelamentoEntity entity, Integer contaBancoEmpresa, boolean fatura){
        LancamentoTipo transacaoTipo = entity.getTipo();
        EmpresaContaEntity contaBanco =null;

        if(contaBancoEmpresa!=null) {
            contaBanco = empresaContaRepository.findById(contaBancoEmpresa)
                    .orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID));

            if(!contaBanco.getEmpresa().equals(entity.getPartes().getEmpresa()))
                throw new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID);
        }else{
            contaBanco = empresaContaRepository.findByEmpresaAndContaPadraoAndContaCredito(entity.getPartes().getEmpresa(), true,fatura)
                    .orElseThrow(() -> new ContaPadraoInexistenteException());
        }

        if(fatura && (!contaBanco.isContaCredito()))
            throw new RegistroIncompativelException("A conta informada precisa ser do tipo CRÉDITO para parcelamento do tipo fatura");

        if(contaBanco.isContaCredito()) {

            if(transacaoTipo == LancamentoTipo.DESPESA &&  valor > contaBanco.getSaldo())
                throw new SaldoInsuficienteException();

            contaBanco.setSaldo(scale2(contaBanco.getSaldo() - valor).doubleValue());
            empresaContaRepository.save(contaBanco);
        }

        entity.setFatura(contaBanco.isContaCredito());
        entity.setContaBanco(contaBanco.getId());
        entity.getNegociacao().setNumeroParcela(request.getNumeroParcelas());
        entity.getNegociacao().setDataVencimento(request.getDataPrimeiroVencimento());
        entity.getNegociacao().setValorAtual(valor);
        entity.getNegociacao().setValorOriginal(valor);
        entity.setParcelas(parcelar(valor, request.getNumeroParcelas(),contaBanco, entity.getData().getDia(), request.getDataPrimeiroVencimento(), entity.getMeioPagamento() == MeioPagamento.BOLETO));

    }

    private List<ParcelaEntity> parcelar(Double valor, Integer numeroParcelas, EmpresaContaEntity conta, LocalDate dataLancamento, LocalDate primeiroVencimento, boolean solicitarBoleto){
        BigDecimal valorReal= BigDecimal.valueOf(valor);

        BigDecimal valorParcela = valorReal.divide(BigDecimal.valueOf(numeroParcelas),4, RoundingMode.HALF_EVEN);
        List<ParcelaEntity> parcelas = new ArrayList<>();


        Integer diaVencimento = primeiroVencimento.getDayOfMonth();
        LocalDate dataVencimento = primeiroVencimento;

        if(dataVencimento==null)
            dataVencimento = definirDataVencimento(null,diaVencimento);

        if(conta.isContaCredito()){
            EmpresaContaFatura fatura = conta.getFatura();
            diaVencimento = fatura.getDiaVencimento();
            Integer diasIntervalo =fatura.getDiasIntervalo();

            Integer diaLancamento = dataLancamento.getDayOfMonth();
            Integer diaFechamento = Math.round(30 + diaVencimento  - diasIntervalo) ;

            if(diaLancamento < diaVencimento || diaLancamento > diaFechamento)
                dataVencimento = definirDataVencimento(dataVencimento,diaVencimento);

        }

        if(dataVencimento.isBefore(LocalDate.now()))
            throw new RegistroIncompativelException("A data de vencimento é inferior a data atual");

        for(int p=1; p<=numeroParcelas; p++){
            ParcelaEntity parcela = new ParcelaEntity();
            parcela.setDescricao(String.format("PARC.NR.%03d", p) );
            if(solicitarBoleto) {
                parcela.getBoleto().setSolicitado(true);
                parcela.getBoleto().setStatus(ParcelaBoletoStatus.SOLICITADO);
            }
            ParcelamentoNegociacao parcelamento = parcela.getNegociacao();
            parcelamento.setDataVencimento(dataVencimento);
            parcelamento.setNumeroParcela(p);

            if(p<numeroParcelas){
                valorReal = valorReal.subtract(scale2(valorParcela));
                parcelamento.setValorOriginal(scale2(valorParcela).doubleValue());
            }else
                parcelamento.setValorOriginal(scale2(valorReal).doubleValue());

            parcelamento.setValorAtual(parcelamento.getValorOriginal());

            parcelas.add(parcela);

            dataVencimento = definirDataVencimento(dataVencimento,diaVencimento);
        }
        return parcelas;
    }
    private LocalDate definirDataVencimento(LocalDate dataInicial, Integer diaVencimento){
        if(dataInicial==null)
            return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), diaVencimento);
        else{
            return dataInicial.plusMonths(1);
        }
    }


}
