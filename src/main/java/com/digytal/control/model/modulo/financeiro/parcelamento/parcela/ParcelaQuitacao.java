package com.digytal.control.model.modulo.financeiro.parcelamento.parcela;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Data
public class ParcelaQuitacao {
    @Column(name = "quit_is_efetuada")
    private boolean efetuada;
    @Column(name = "quit_data")
    private LocalDate data;
}
