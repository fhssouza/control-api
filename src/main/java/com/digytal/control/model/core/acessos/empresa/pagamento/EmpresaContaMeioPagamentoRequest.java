package com.digytal.control.model.core.acessos.empresa.pagamento;

import com.digytal.control.model.core.comum.MeioPagamento;
import lombok.Data;

@Data
public class EmpresaContaMeioPagamentoRequest {
    private MeioPagamento meioPagamento;
    private Double taxa;
}
