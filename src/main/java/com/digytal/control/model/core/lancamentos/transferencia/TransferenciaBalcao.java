package com.digytal.control.model.core.lancamentos.transferencia;

import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.digytal.control.model.core.lancamentos.lancamento.request.LancamentoDetalheRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Estrutura de uma requisição de tranferência\\retirada de valor da conta caixa\\balcão")
public class TransferenciaBalcao {
    private RegistroParteRequest partes;
    @Schema(description="ID da conta banco empresa destino", required = true, example = "2")
    private Integer contaDestino;
    private LancamentoDetalheRequest detalhe;
}
