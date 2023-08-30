package com.digytal.control.model.core.acessos.empresa.conta;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(schema = "apl_acessos", name = "tab_empresa_conta")
@Data
public class EmpresaContaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String numero;
    private String agencia;
    @Column(name = "empresa_id")
    private Integer empresa;
    private String legenda;
    private Double saldo;
    @Column(name = "banco_id")
    private Integer banco;
    @Column(name = "is_conta_padrao")
    private boolean contaPadrao;
    @Column(name = "is_conta_credito")
    private boolean contaCredito;
    @Embedded
    private EmpresaContaFatura fatura;
    @Column(name = "chave_pix")
    private String chavePix;
    private String descricao;
}

