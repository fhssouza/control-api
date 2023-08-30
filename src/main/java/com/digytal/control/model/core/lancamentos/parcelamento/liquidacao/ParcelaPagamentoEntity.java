package com.digytal.control.model.core.lancamentos.parcelamento.liquidacao;

import com.digytal.control.model.core.comum.MeioPagamento;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "apl_lancamentos", name = "tab_parcelamento_parcela_pagto")
@Data
public class ParcelaPagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    private Double valor;
    @Column(name = "dt_pagto")
    private LocalDate data;
    @Column(name="conta_banco_id")
    private Integer contaBanco;
    @Column(name="parcela_id")
    private Integer parcela;
    @Column(name="parcelamento_id")
    private Integer parcelamento;
    @Column(name="cpt_per_competencia")
    private Integer competencia;
    @Column(name = "meio_pagto")
    private MeioPagamento meioPagamento;
    @Column(name = "bol_nr_autorizacao")
    private String numeroAutorizacao;
    @Column(name = "bol_vl_original")
    private Double valorOrigianal;
    @Column(name = "usuario_id")
    private Integer usuario;
}
