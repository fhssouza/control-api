package com.digytal.control.model.core.cadastros.cadastro;

import com.digytal.control.model.core.comum.RegistroCadastralResponse;
import com.digytal.control.model.core.comum.cadastratamento.CadastroPerfil;
import lombok.Data;

@Data
public class CadastroResponse extends RegistroCadastralResponse {
    private CadastroPerfil perfil;
}
