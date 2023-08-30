package com.digytal.control.model.core.lancamentos.parcelamento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description="Estrutura de uma requisição de lançamento do tipo Parcelamento no sistema")
public class ParcelamentoNegociacaoRequest {
    @Schema(description="Data do vencimento da primeira parcela para gerar as parcelas do parcelamento", required = true,example = "2023-03-05")
    private LocalDate dataPrimeiroVencimento;
    @Schema(description="Número de parcelas para gerar as parcelas do parcelamento", required = true,example = "8")
    private Integer numeroParcelas;
    public ParcelamentoNegociacaoRequest(){

    }
    public static ParcelamentoNegociacaoRequest of(Integer numeroParcelas, LocalDate dataPrimeiroVencimento ){
        ParcelamentoNegociacaoRequest instancia = new ParcelamentoNegociacaoRequest();
        instancia.dataPrimeiroVencimento=dataPrimeiroVencimento;
        instancia.numeroParcelas = numeroParcelas;
        return instancia;
    }
}
