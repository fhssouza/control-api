package com.digytal.control.model.core.cadastros.produto;

import lombok.Data;

@Data
public class ProdutoRequest {
    private String codigoBarras;
    private String sku;
    private String nome;
    private String unidadeMedida;
    private Double valor;
    private boolean servico;
    private boolean atualizaSaldo;
    private Integer organizacao;
}
