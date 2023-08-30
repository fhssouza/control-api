package com.digytal.control.model.core.cadastros.produto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Data
public class ProdutoItem {
    @Column(name = "prod_id")
    private Integer id;
    @Column(name = "prod_cb")
    private String codBarras;
    @Column(name = "prod_sku")
    private String sku;
    @Column(name = "prod_nome")
    private String nome;
    @Column(name = "prod_und")
    private String und;
    @Column(name = "prod_valor")
    private Double valor;
    @Column(name = "prod_saldo")
    private Double saldo;
    public ProdutoItem(){

    }
    public static ProdutoItem of(Integer id, String nome, String und, Double valor,String codBarras, String sku, Double saldo ){
        ProdutoItem instancia = new ProdutoItem();
        instancia.id = id;
        instancia.codBarras = codBarras;
        instancia.nome = nome;
        instancia.valor=valor;
        instancia.und = und;
        instancia.sku= Objects.toString(sku, codBarras);
        instancia.saldo=saldo;
        return instancia;
    }
}
