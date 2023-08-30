package com.digytal.control.model.contrato.meiopagamento;

import com.digytal.control.model.core.comum.MeioPagamento;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ContratoPagamentoRequest {
    private MeioPagamento meioPagamento;
    private Double valor;
    private Integer numeroParcelas;
    private Double valorParcela;
    private LocalDate dataPrimeiroVencimento;
}
