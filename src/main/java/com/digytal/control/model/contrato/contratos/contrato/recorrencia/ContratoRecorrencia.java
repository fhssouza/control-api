package com.digytal.control.model.contrato.contratos.contrato.recorrencia;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContratoRecorrencia {
    private Periodicidade periodicidade;
    private Integer repeticoesTotal;
    private Integer repeticoesRealizadas;
    private LocalDateTime dataProximaOcorrencia;
}
