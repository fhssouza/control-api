package com.digytal.control.model.core.acessos.organizacao;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Estrutura das requisições de inclusão e alteração relacionadas as organizações do sistema")
public class OrganizacaoRequest {

    @Schema(description="Nome da organização", maximum="80",required = true,example = "GRUPO ATACADISTA LTDA")
    private String nome;
}
