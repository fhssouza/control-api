package com.digytal.control.model.core.acessos.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name="Nova Senha", description="Recurso para criação de uma nova senha")
public class SenhaRequest {
    @Schema(description="ID identificação do usuário", maximum="50",required = true,example = "1")
    private Integer usuario;
    @Schema(description="Senha utilizada até o presente momento", minimum="8",required = true,example = "StronP@ss")
    private String senhaAtual;
    @Schema(description="Nova senha de acordo com a política segurança", minimum="8",required = true,example = "NewStronP@ss")
    private String novaSenha;
    @Schema(description="Repetição da nova senha para confirmação", minimum="8",required = true,example = "NewStronP@ss")
    private String novaSenhaConfirmacao;
}
