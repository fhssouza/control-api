package com.digytal.control.model.core.lancamentos.parcelamento;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@Embeddable
public class ParcelamentoNegociacao {
    @Column(name = "parc_neg_num_parcela")
    private Integer numeroParcela=0;
    @Column(name = "parc_neg_dt_vencto")
    private LocalDate dataVencimento=LocalDate.now();
    @Column(name = "parc_neg_vl_original")
    private Double valorOriginal=0.0;
    @Column(name = "parc_neg_vl_multa")
    private Double valorMulta=0.0;
    @Column(name = "parc_neg_vl_juros")
    private Double valorJuros=0.0;
    @Column(name = "parc_neg_vl_correcao")
    private Double valorCorrecao=0.0;
    @Column(name = "parc_neg_vl_amortizado")
    private Double valorAmortizado=0.0;
    @Column(name = "parc_neg_vl_atual")
    private Double valorAtual=0.0;
    @Column(name = "parc_neg_vl_desconto")
    private Double valorDesconto=0.0;
}
