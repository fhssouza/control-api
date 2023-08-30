package com.digytal.control.model.core.lancamentos.lancamento;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class LancamentoContrato {
    @Column(name = "contrato_id")
    private Integer id;
    @Column(name = "contrato_tipo")
    private String tipo;
}
