package com.digytal.control.model.modulo.financeiro.parcelamento;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@Embeddable
public class ParcelamentoValor {
    @Column(name = "num_parcela")
    private Integer numeroParcela=0;
    @Column(name = "dt_vencto")
    private LocalDate dataVencimento=LocalDate.now();
    @Column(name = "vl_original")
    private Double valorOriginal=0.0;
    @Column(name = "vl_multa")
    private Double valorMulta=0.0;
    @Column(name = "vl_juros")
    private Double valorJuros=0.0;
    @Column(name = "vl_correcao")
    private Double valorCorrecao=0.0;
    @Column(name = "vl_desconto")
    private Double valorDesconto=0.0;
    @Column(name = "vl_amortizado")
    private Double valorAmortizado=0.0;
    @Column(name = "vl_atual")
    private Double valorAtual=0.0;
    public static ParcelamentoValor of(Integer numeroParcela, Double valor, LocalDate dataVencimento){
        ParcelamentoValor instance = new ParcelamentoValor();
        instance.setValorOriginal(valor);
        instance.setValorAtual(valor);
        instance.setNumeroParcela(numeroParcela);
        instance.setDataVencimento(dataVencimento);
        return instance;
    }
}
