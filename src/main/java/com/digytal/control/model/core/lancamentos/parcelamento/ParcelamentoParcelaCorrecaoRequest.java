package com.digytal.control.model.core.lancamentos.parcelamento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name="Parcelamento Parcela Correção", description="Estrutura de atualização\\correção da parcela de um parcelamento")
@Data
public class ParcelamentoParcelaCorrecaoRequest {
    @Schema(description="Valor multa", example = "1.45")
    private Double valorMulta= 0.0;
    @Schema(description="Valor juros", example = "0.33")
    private Double valorJuros= 0.0;
    @Schema(description="Valor correção", example = "5.27")
    private Double valorCorrecao= 0.0;
    @Schema(description="Valor desconto", example = "0.0")
    private Double valorDesconto= 0.0;
    @Schema(description="Observação da atualização\\correção", example = "APLICANDO CORRECAO DEVIDO ATRASO")
    private String observacao;
}
