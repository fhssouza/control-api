package com.digytal.control.model.core.lancamentos.parcelamento.liquidacao;

import com.digytal.control.model.core.comum.MeioPagamento;
import lombok.Data;
@Data
public class ParcelaPagamentoRequest {
    private Double valor;
    private Integer contaBanco;
    private MeioPagamento meioPagamento;
}
