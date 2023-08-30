package com.digytal.control.model.core.acessos.empresa.conta;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class EmpresaContaFatura {
    @Column(name = "dias_intervalo")
    private Integer diasIntervalo;
    @Column(name = "dia_vencimento")
    private Integer diaVencimento;
}
