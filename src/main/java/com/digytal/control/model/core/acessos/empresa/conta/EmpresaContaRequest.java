package com.digytal.control.model.core.acessos.empresa.conta;

import lombok.Data;

@Data
public class EmpresaContaRequest {
    private String numero;
    private String agencia;
    private String legenda;
    private Integer banco;
    private boolean contaCredito;
    private boolean contaPadrao;
    private EmpresaContaFatura fatura;
    private String chavePix;
    private String descricao;
}
