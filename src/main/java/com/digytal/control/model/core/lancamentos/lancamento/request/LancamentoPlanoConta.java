package com.digytal.control.model.core.lancamentos.lancamento.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Estrutura de uma requisição de lançamento informando a conta banco e plano de contas no sistema")
public class LancamentoPlanoConta {
    @Schema(description="Id da conta banco da empresa", required = true, example = "0978-7765")
    private String contaBanco;
    @Schema(description="Id do plnao de contas", required = true, example = "1")
    private Integer planoConta;
}
