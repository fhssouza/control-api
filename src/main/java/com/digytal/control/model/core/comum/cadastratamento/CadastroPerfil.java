package com.digytal.control.model.core.comum.cadastratamento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class CadastroPerfil {
    @Schema(description="Determina se o estabelecimento é um cliente", example = "true", allowableValues = {"true","false"})
    @Column(name = "is_cliente")
    private boolean cliente;
    @Schema(description="Determina se o estabelecimento é um fornecedor", example = "false", allowableValues = {"true","false"})
    @Column(name = "is_fornecedor")
    private boolean fornecedor;
}
