package com.digytal.control.model.modulo.financeiro;

import com.digytal.control.model.comum.MeioPagamento;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class Movimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "meio_pagto")
    private MeioPagamento meioPagamento;

}
