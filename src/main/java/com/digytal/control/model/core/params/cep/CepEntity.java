package com.digytal.control.model.core.params.cep;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "apl_params", name = "tab_cep")
@Data
public class CepEntity {
    @Id
    private String cep;
    private String logradouro;
    private String bairro;
    private String complemento;
    private String localidade;
    private String estado;
    private String uf;
    private Integer ibge;
    @Column(name = "is_valido")
    private boolean valido;
    @Transient
    private String pais = "BRASIL";
}
