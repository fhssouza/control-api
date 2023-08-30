package com.digytal.control.model.core.lancamentos.parcelamento.parcela;

import com.digytal.control.model.core.lancamentos.parcelamento.ParcelaQuitacao;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoNegociacao;
import lombok.Data;
@Data
public class ParcelaResponse {
    private Integer id;
    private String descricao;
    private ParcelaAliquota aliquota = new ParcelaAliquota();
    private ParcelamentoNegociacao negociacao = new ParcelamentoNegociacao();
    private PacelaNegociacaoPendencia pendencia = new PacelaNegociacaoPendencia();
    private ParcelaQuitacao quitacao = new ParcelaQuitacao();
    private ParcelaBoleto boleto = new ParcelaBoleto();
    private Integer parcelamento;
    private String observacao;
}
