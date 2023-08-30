package com.digytal.control.model.core.comum.telefone;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Estrutura de uma requisição de informação de telefone nos cadastros do sistema")
public class TelefoneRequest {
    @Schema(description="Número para telefone fixo", maximum="10",example = "1133548998")
    private Long fixo;
    @Schema(description="Número para telefone celular", maximum="11",example = "11966540923")
    private Long celular;
    @Schema(description="Determina se o telefone celular é whatsapp", example = "true", allowableValues = {"true","false"})
    private boolean celularWhatsapp;
}
