package com.digytal.control.model.core.params.banco;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "apl_params", name = "tab_banco")
@Data
public class BancoEntity {
    @Id
    private Integer id;
    private Integer compe;
    private Long ispb;
    private String nome;
    @Column(name = "apelido")
    private String legenda;
}
