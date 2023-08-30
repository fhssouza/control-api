package com.digytal.control.model.gerencial;

import com.digytal.control.model.core.comum.MeioPagamento;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContratoPagamentoAnalitico {
    private Integer id;
    private String descricao;
    private LocalDate data;
    private Double valorPrevisto;
    private Double valorAplicado;
    private MeioPagamento meioPagamento;
    private Double valorPago;
    private Integer numeroParcelas;
    private Double valorParcela;
    private LocalDate parcelaPrimeiroVencimento;
}
