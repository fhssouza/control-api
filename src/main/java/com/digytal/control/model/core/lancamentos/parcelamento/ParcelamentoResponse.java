package com.digytal.control.model.core.lancamentos.parcelamento;

import com.digytal.control.model.core.lancamentos.parcelamento.parcela.PacelaNegociacaoPendencia;
import com.digytal.control.model.core.lancamentos.lancamento.response.LancamentoAbstractResponse;
import lombok.Data;

@Data
public class ParcelamentoResponse extends LancamentoAbstractResponse {
    private ParcelamentoNegociacao negociacao = new ParcelamentoNegociacao();
    private PacelaNegociacaoPendencia pendencia = new PacelaNegociacaoPendencia();
    private boolean quitada;
}
