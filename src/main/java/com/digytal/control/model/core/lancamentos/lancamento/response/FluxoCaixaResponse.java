package com.digytal.control.model.core.lancamentos.lancamento.response;

import lombok.Data;

@Data
public class FluxoCaixaResponse extends LancamentoResponse {
    private Double valorReceita;
    private Double valorDespesa;
}
