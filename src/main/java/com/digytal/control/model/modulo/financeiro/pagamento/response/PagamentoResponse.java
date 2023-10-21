package com.digytal.control.model.modulo.financeiro.pagamento.response;
import com.digytal.control.model.comum.RegistroSimples;
import com.digytal.control.model.modulo.financeiro.Valor;
import com.digytal.control.model.modulo.financeiro.response.LancamentoResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Resposta do pagamento",description = "Resposta referente ao pagamento realizado")
public class PagamentoResponse extends LancamentoResponse {
    @Schema(name = "valor do pagamento",subTypes = Valor.class)
    private Valor valor;
    @Schema(name = "dados do pagamento",subTypes = RegistroSimples.class)
    private RegistroSimples cadastro;
}
