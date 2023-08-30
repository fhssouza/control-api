package com.digytal.control.model.core.cadastros.produto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "apl_cadastros", name = "tab_produto")
@Data
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String nome;
    @Column(name = "und_medida")
    private String unidadeMedida;
    @Column(name = "cod_barras")
    private String codigoBarras;
    @Column(name = "sku")
    private String sku;
    @Column(name = "vl_produto")
    private Double valor;
    @Column(name = "is_servico")
    private boolean servico;
    @Column(name = "organizacao_id")
    private Integer organizacao;
    private Double saldo;
    @Column(name = "fl_atualiza_saldo")
    private boolean atualizaSaldo;
    @Embedded
    private ProdutoApp app = new ProdutoApp();
}
