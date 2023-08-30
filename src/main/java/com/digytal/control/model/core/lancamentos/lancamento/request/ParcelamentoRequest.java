package com.digytal.control.model.core.lancamentos.lancamento.request;

import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoNegociacaoRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Estrutura de uma requisição de lançamento do tipo parcelamento no sistema")
public class ParcelamentoRequest extends TransacaoRequest {
    private ParcelamentoNegociacaoRequest negociacao;
    private boolean fatura;
}
