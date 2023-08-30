package com.digytal.control.model.core.lancamentos.lancamento.request;

import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import lombok.Data;

@Data
public class TransacaoRequest {
    private RegistroParteRequest partes;
    private LancamentoDetalheRequest detalhe;
    private LancamentoTipo tipo;
    private MeioPagamento formaPagamento;
}
