package com.digytal.control.model.gerencial.venda;

import com.digytal.control.model.core.comum.MeioPagamento;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ContratoVendaPagamento {
    private MeioPagamento meioPagamento;
    private Double valorPago;
    private Integer numeroParcelas;
    private Double valorParcela;
    private LocalDate parcelaPrimeiroVencimento;

}
