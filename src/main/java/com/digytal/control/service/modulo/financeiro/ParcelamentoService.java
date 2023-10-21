package com.digytal.control.service.modulo.financeiro;

import com.digytal.control.infra.business.RegistroIncompativelException;
import com.digytal.control.infra.utils.Calculos;
import com.digytal.control.model.modulo.acesso.empresa.aplicacao.AplicacaoTipo;
import com.digytal.control.model.modulo.financeiro.parcelamento.ParcelamentoEntity;
import com.digytal.control.model.modulo.financeiro.parcelamento.ParcelamentoValor;
import com.digytal.control.model.modulo.financeiro.parcelamento.parcela.ParcelaEntity;
import com.digytal.control.model.modulo.financeiro.transacao.TransacaoParcelamentoRequest;
import com.digytal.control.model.modulo.financeiro.transacao.TransacaoRateioRequest;
import com.digytal.control.repository.modulo.fincanceiro.ParcelamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelamentoService{
    public ParcelamentoEntity criarParcelamento(AplicacaoTipo tipo, TransacaoRateioRequest rateio) {
        ParcelamentoEntity entity = new ParcelamentoEntity();
        TransacaoParcelamentoRequest parcelamento = conferirParcelamento(rateio.getParcelamento());
        ParcelamentoValor valor = ParcelamentoValor.of(parcelamento.getNumeroParcelas(), rateio.getValorPago(), parcelamento.getDataPrimeiroVencimento());
        entity.setValor(valor);
        entity.setMeioPagamento(rateio.getMeioPagamento());
        entity.setParcelas(parcelar(rateio.getValorPago(), parcelamento));
        return entity;
    }
    private TransacaoParcelamentoRequest conferirParcelamento(TransacaoParcelamentoRequest parcelamento){
        if(parcelamento ==null)
            parcelamento = new TransacaoParcelamentoRequest();

        parcelamento.setDataPrimeiroVencimento(parcelamento.getDataPrimeiroVencimento()==null ? LocalDate.now().plusDays(30) : parcelamento.getDataPrimeiroVencimento());
        parcelamento.setNumeroParcelas(parcelamento.getNumeroParcelas()==null ? 1: parcelamento.getNumeroParcelas());
        return  parcelamento;
    }
    private List<ParcelaEntity> parcelar(Double valor, TransacaoParcelamentoRequest parcelamento){
        BigDecimal valorReal= BigDecimal.valueOf(valor);
        Integer numeroParcelas = parcelamento.getNumeroParcelas();
        BigDecimal valorParcela = valorReal.divide(BigDecimal.valueOf(numeroParcelas),4, RoundingMode.HALF_EVEN);
        List<ParcelaEntity> parcelas = new ArrayList<>();

        Integer diaVencimento = parcelamento.getDataPrimeiroVencimento().getDayOfMonth();
        LocalDate dataVencimento = parcelamento.getDataPrimeiroVencimento();
        if(dataVencimento==null)
            dataVencimento = definirDataVencimento(null,diaVencimento);

        if(dataVencimento.isBefore(LocalDate.now()))
            throw new RegistroIncompativelException("A data de vencimento é inferior a data atual");

        for(int p=1; p<=numeroParcelas; p++){
            ParcelaEntity parcela = new ParcelaEntity();
            parcela.setDescricao(String.format("PARC.NR.%03d", p) );
            ParcelamentoValor parcelamentoValor = parcela.getValor();
            parcelamentoValor.setDataVencimento(dataVencimento);
            parcelamentoValor.setNumeroParcela(p);

            if(p<numeroParcelas){
                valorReal = valorReal.subtract(Calculos.aplicarEscala(valorParcela));
                parcelamentoValor.setValorOriginal(Calculos.aplicarEscala(valorParcela).doubleValue());
            }else
                parcelamentoValor.setValorOriginal(Calculos.aplicarEscala(valorReal).doubleValue());

            parcelamentoValor.setValorAtual(parcelamentoValor.getValorOriginal());

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