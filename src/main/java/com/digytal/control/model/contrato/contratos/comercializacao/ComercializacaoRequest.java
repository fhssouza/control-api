package com.digytal.control.model.contrato.contratos.comercializacao;

import com.digytal.control.model.contrato.meiopagamento.ContratoPagamentoRequest;
import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ComercializacaoRequest {
    private LocalDate data;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime hora = LocalTime.now();
    private String descricao;
    private RegistroParteRequest partes;
    private Integer intermediador;
    private List<ComercializacaoItemRequest> itens = new ArrayList<>();
    private List<ContratoPagamentoRequest> pagamentos = new ArrayList<>();
}
