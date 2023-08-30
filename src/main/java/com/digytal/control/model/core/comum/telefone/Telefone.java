package com.digytal.control.model.core.comum.telefone;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
@Schema(description="Contatos por telefone")
public class Telefone {
    @Schema(description="Número para telefone fixo", maximum="10",example = "1133640967")
    @Column(name = "tel_fixo")
    private Long fixo;
    @Schema(description="Número para telefone celular", maximum="11",example = "11966540923")
    @Column(name = "tel_celular")
    private Long celular;
    @Schema(description="Define se celular é WhatsApp", example = "true", allowableValues = {"true","false"})
    @Column(name = "tel_celular_whatsapp")
    private boolean celularWhatsApp;
}
