package com.digytal.control.model.educacional.conteudos.aula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AulaResponse {
    private Integer id;
    private String duracao;
    private String titulo;
    private String descricao;
    private String url;

}
