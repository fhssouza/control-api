package com.digytal.control.model.core.lancamentos.lancamento.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LancamentoDetalheRequest {
    @Schema(description="Número de documento externo ou manual", required = true,example = "NF2345")
    private String numeroDocumento;
    @Schema(description="Descrição do lançamento", required = true,example = "LANÇAMENTO DE RECEITA GERAL")
    private String descricao;
    @Schema(description="Valor informado", required = true,example = "356.14")
    private Double valor;
}
