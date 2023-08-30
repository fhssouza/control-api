package com.digytal.control.model.core.acessos.organizacao;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "apl_acessos", name = "tab_organizacao")
@Data
public class OrganizacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String cpfCnpj;
    private String nome;
}
