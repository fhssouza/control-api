package com.digytal.control.model.core.acessos.organizacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="OrganizacaoResponse", description="Estrutura das organizações do sistema")
public class OrganizacaoResponse extends OrganizacaoRequest{
    @Schema(description="Identificação da organização no sistema ", example = "1")
    private Integer id;
}
