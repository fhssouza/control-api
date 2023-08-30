package com.digytal.control.model.core.comum.cadastratamento;

import lombok.Data;

@Data
public class CadastroRequest extends CadastroCompletoRequest {
    private CadastroPerfil perfil;
}
