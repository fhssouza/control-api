package com.digytal.control.model.core.acessos.empresa.conta;

import lombok.Data;

@Data
public class ContaPagamentoResponse {
    private boolean dinheiro;
    private boolean boleto;
    private boolean parcelado;
    private boolean credito;
    private boolean debito;
    private boolean pix;
}
