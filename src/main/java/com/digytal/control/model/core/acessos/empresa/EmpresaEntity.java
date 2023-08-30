package com.digytal.control.model.core.acessos.empresa;

import com.digytal.control.model.core.comum.RegistroCadastralEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "apl_acessos", name = "tab_empresa")
@Data
public class EmpresaEntity extends RegistroCadastralEntity {

}
