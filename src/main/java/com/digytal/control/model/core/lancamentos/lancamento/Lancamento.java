package com.digytal.control.model.core.lancamentos.lancamento;

import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.core.comum.RegistroPartes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Column(name = "nr_documento")
    private String numeroDocumento;
    private String descricao;
    @Column(name = "tipo")
    private LancamentoTipo tipo;
    @Embedded
    private RegistroData data;
    @Embedded
    private RegistroPartes partes;
    @Column(name = "observacao")
    private String observacao;
    @Embedded
    private LancamentoContrato contrato;
    @Column(name = "meio_pagto")
    private MeioPagamento meioPagamento;
    @Column(name = "conta_banco_id")
    private Integer contaBanco;
}
