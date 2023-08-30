package com.digytal.control.model.core.acessos.empresa.conta;

import lombok.Data;

@Data
public class EmpresaContaResponse extends EmpresaContaCadastroRequest {
   private Integer id;
   private Integer empresa;
}
