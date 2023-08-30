package com.digytal.control.model.core.comum.cadastratamento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CadastroSimplificadoRequest {
    @Schema(description="Primeiro nome ou Nome fantasia", maximum="50",example = "LOJAS E ACESSORIOS PRODUTOS IMPORTADOS")
    private String nomeFantasia;

    @Schema(description="Sobrenome ou Razão social", maximum="50",example = "J & R IMPORTADOS LTDA")
    private String sobrenomeSocial;

    @Schema(description="Conta de e-mail da empresa", maximum="70",example = "contato@jrimportados.com")
    private String email;
}
