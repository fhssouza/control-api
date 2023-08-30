package com.digytal.control.model.gerencial;

import lombok.Data;

@Data
public class ContratoPagamento {
    private Double dinheiro = 0.0;
    private Double pix= 0.0;
    private Double debito= 0.0;
    private Double credito= 0.0;
    private Double boleto= 0.0;
    private Double parcelado= 0.0;
}
