package com.digytal.control.model.core.cadastros.produto;

import lombok.Data;

@Data
public class ProdutoResponse extends ProdutoRequest {
    private Integer id;
    private Double saldo;
}
