package com.digytal.control.model.modulo.financeiro.parcelamento.parcela;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class ParcelaAliquota {
    @Column(name = "al_multa")
    private Double aliquotaMulta=0.0;
    @Column(name = "al_juros")
    private Double aliquotaJuros=0.0;
    @Column(name = "al_correcao")
    private Double aliquotaCorrecao=0.0;
}
