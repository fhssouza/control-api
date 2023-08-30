package com.digytal.control.model.core.acessos.usuario;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;


@Entity
@Immutable
@Table(schema = "apl_acessos", name = "tab_usuario_empresa")
@Data
public class UsuarioEmpresaEntity {
    @Id
    @Column(name = "usuario_id")
    private Integer usuario;

    @Column(name = "empresa_id")
    private Integer empresa;

    @Column(name = "is_padrao")
    private boolean padrao;

}
