package com.digytal.control.model.core.comum.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@Schema(description="Estrutura de uma requisição de informação da cidade de endereço nos cadastros do sistema")
public class Cidade {
    @Schema(description="Nome da cidade", maximum="70", example = "SAO PAULO")
    @Column(name = "end_cidade")
    private String nome;
    @Schema(description="Estado da cidade", maximum="70", example = "SAO PAULO")
    @Column(name = "end_estado")
    private String estado;
    @Schema(description="Sigla do estado da cidade", maximum="2", example = "SP")
    @Column(name = "end_uf")
    private String uf;
}
