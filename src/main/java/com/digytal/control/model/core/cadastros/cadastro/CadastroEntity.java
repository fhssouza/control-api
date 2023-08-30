package com.digytal.control.model.core.cadastros.cadastro;

import com.digytal.control.model.core.comum.RegistroCadastralEntity;
import com.digytal.control.model.core.comum.cadastratamento.CadastroPerfil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "apl_cadastros", name = "tab_cadastro")
@Data
public class CadastroEntity extends RegistroCadastralEntity {
    @Embedded
    private CadastroPerfil perfil = new CadastroPerfil();
    @Column(name = "cod_integracao")
    private String codigoIntegracao;
}
