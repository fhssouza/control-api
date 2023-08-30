package com.digytal.control.model.contrato.contratos.comercializacao;

import com.digytal.control.model.core.cadastros.produto.ProdutoItem;
import lombok.Data;

@Data
public class ComercializacaoItemRequest {
    private ProdutoItem produto;
    private Double quantidade;
    private Double valorUnitario;
    private Double valorAplicado;
}
