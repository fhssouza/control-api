package com.digytal.control.model.core.lancamentos.parcelamento.liquidacao;

import com.digytal.control.model.core.comum.MeioPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ParcelaPagamentoFormaRequest {
    @Schema(description="Valor recebido para pagamento", required = true,example = "208.33")
    private Double valor;
    @Schema(description="Meio de pagamento", required = true)
    private MeioPagamento meioPagamento;

}
