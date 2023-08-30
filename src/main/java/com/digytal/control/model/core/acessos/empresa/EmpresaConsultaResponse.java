package com.digytal.control.model.core.acessos.empresa;

import lombok.Data;

@Data
public class EmpresaConsultaResponse {
    private Integer id;
    private String cpfCnpj;
    private String nomeFantasia;
    private String sobrenomeSocial;
    private Integer organizacao;
    private boolean padrao;
}
