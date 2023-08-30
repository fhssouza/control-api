package com.digytal.control.model.core.lancamentos.parcelamento.parcela;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Data
public class PacelaNegociacaoPendencia {
    @Column(name = "parc_neg_is_atrasada")
    private boolean atrasada;
    @Column(name = "parc_neg_dias_atraso")
    private Integer diasAtraso=0;
    @Column(name = "parc_neg_num_pendentes")
    private Integer numeroParcelas=0;
    @Column(name = "parc_neg_is_negociada")
    private boolean negociada;
    @Column(name = "parc_neg_dt_prox_vencto")
    private LocalDate dataProximoVencimento;
}
