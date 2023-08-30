package com.digytal.control.model.core.lancamentos;

import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
@Getter
public class LancamentoValor {
    @Column(name = "vl_informado")
    private Double valorInformado;
    @Column(name = "vl_real")
    private Double valorReal;
    @Column(name = "vl_operacional")
    private Double valorOperacional;
    @Column(name = "vl_saldo_anterior")
    private Double saldoAnterior;
    public static LancamentoValor zero(){
        return of(LancamentoTipo.RECEITA, 0.0,0.0);
    }
    public static LancamentoValor of(LancamentoTipo tipo, Double valor){
        return of(tipo, valor,null);
    }
    public static LancamentoValor of(LancamentoTipo tipo, Double valor, Double saldoAnterior){
        final RoundingMode RM= RoundingMode.HALF_EVEN;
        LancamentoValor instance = new LancamentoValor();
        instance.valorInformado = valor;
        instance.valorReal = new BigDecimal(valor).setScale(2, RM).doubleValue();
        instance.valorReal = valorOperado(tipo,instance.valorReal);
        instance.valorOperacional = new BigDecimal(valor).setScale(4, RM).doubleValue();
        instance.valorOperacional = valorOperado(tipo,instance.valorOperacional);
        instance.saldoAnterior = saldoAnterior==null?0.0:saldoAnterior;
        return instance;
    }
    private static Double valorOperado(LancamentoTipo tipo, Double valor){
        return tipo == LancamentoTipo.RECEITA?valor: valor * -1;
    }
}
