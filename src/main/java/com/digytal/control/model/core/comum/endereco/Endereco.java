package com.digytal.control.model.core.comum.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
public class Endereco {
    @Schema(description="Cep do endereço no cadastro", maximum="11", example = "64000110")
    @Column(name = "end_cep")
    private String cep;
    @Schema(description="Logradouro do endereço no cadastro", maximum="50",example = "PRACA DA SE")
    @Column(name = "end_logradouro")
    private String logradouro;
    @Schema(description="Número do endereço no cadastro", maximum="50",example = "123-A")
    @Column(name = "end_numero")
    private String numero;
    @Schema(description="Bairro do endereço no cadastro", maximum="50",example = "CENTRO")
    @Column(name = "end_bairro")
    private String bairro;
    @Schema(description="Complemento do endereço no cadastro", maximum="50",example = "BL-A AP-121")
    @Column(name = "end_complemento")
    private String complemento;
    @Schema(description="Referência do endereço no cadastro", maximum="50",example = "AO LADO DA NASA")
    @Column(name = "end_referencia")
    private String referencia;
    @Schema(description="Telefone do endereço no cadastro", maximum="11",example = "11912345678")
    @Column(name = "end_telefone")
    private Long telefone;
    @Schema(description="Código IBGE da cidade do endereço no cadastro", maximum="7", example = "1200013")
    @Column(name = "end_ibge")
    private Integer ibge;
    @Schema(description="Cidade do endereço")
    @Embedded
    private Cidade cidade = new Cidade();

}
