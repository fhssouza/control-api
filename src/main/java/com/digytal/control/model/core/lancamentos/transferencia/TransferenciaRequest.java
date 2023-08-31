package com.digytal.control.model.core.lancamentos.transferencia;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Estrutura de uma requisição de tranferência entre contas no sistema")
public class TransferenciaRequest extends TransferenciaBalcao {
    @Schema(description="ID da conta banco empresa origem", required = true, example = "1")
    private Integer contaOrigem;
}
