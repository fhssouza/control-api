package com.digytal.control.model.core.comum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegistroParteRequest {
    @Schema(description="ID da empresa que proprietária do registro", required = true, example = "1")
    private Integer empresa;
    @Schema(description="ID do cadastro que originou o registro", required = true, example = "1")
    private Integer cadastro;
    @Schema(description="ID do usuario que originou o registro", required = true, example = "1")
    private Integer usuario;
    public RegistroParteRequest(){

    }
    public static RegistroParteRequest of(Integer empresa, Integer cadastro, Integer usuario){
        RegistroParteRequest instancia = new RegistroParteRequest();
        instancia.cadastro=cadastro;
        instancia.empresa=empresa;
        instancia.usuario=usuario;
        return instancia;
    }
}
