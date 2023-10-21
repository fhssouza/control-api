package com.digytal.control.model.modulo.cadastro.produto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class ProdutoApp {
    @Column(name = "app_is_visivel")
    private boolean visivel;
    @Column(name = "app_ordem_visualizacao")
    private int ordemVisualizacao=0;
}
