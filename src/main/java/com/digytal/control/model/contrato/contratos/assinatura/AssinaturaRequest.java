package com.digytal.control.model.contrato.contratos.assinatura;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Estrutura de uma requisição de uma assinatura no sistema")
public class AssinaturaRequest {
    private Integer cadastro;
    private Integer produto;
    private Integer empresa;
    private String descricao;
}
