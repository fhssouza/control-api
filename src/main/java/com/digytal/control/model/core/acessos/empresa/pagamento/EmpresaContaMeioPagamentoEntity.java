package com.digytal.control.model.core.acessos.empresa.pagamento;

import com.digytal.control.model.core.comum.MeioPagamento;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "apl_acessos", name = "tab_empresa_conta_meio_pagto")
@Data
public class EmpresaContaMeioPagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Column(name = "empresa_conta_id")
    private Integer conta;
    @Column(name = "empresa_id")
    private Integer empresa;
    @Column(name = "meio_pagto")
    private MeioPagamento meioPagamento;
    @Column(name = "taxa")
    private Double taxa;
}
