package com.digytal.control.model.core.lancamentos.parcelamento.liquidacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ParcelaPagamentoContaRequest {
    @Schema(description="Valor recebido para pagamento", required = true,example = "125.33")
    private Double valor;
    @Schema(description="Código da conta que terá o saldo atualizado", required = true,example = "1")
    private Integer contaBanco;
    @Schema(description="Usuário que confirmou a operação de pagamento", required = true,example = "1")
    private Integer usuario;
}
