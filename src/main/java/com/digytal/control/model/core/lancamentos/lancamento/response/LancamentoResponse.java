package com.digytal.control.model.core.lancamentos.lancamento.response;

import com.digytal.control.model.core.lancamentos.LancamentoValor;
import lombok.Data;

@Data
public class LancamentoResponse extends LancamentoAbstractResponse{
    private LancamentoValor valor = new LancamentoValor();
}
