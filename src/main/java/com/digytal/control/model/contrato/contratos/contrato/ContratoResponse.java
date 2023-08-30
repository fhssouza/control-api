package com.digytal.control.model.contrato.contratos.contrato;

import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.core.comum.RegistroPartes;
import lombok.Data;

import javax.persistence.*;

@Data
public class ContratoResponse {
    private Integer id;
    private String descricao;
    private ContratoTipo tipo;
    private ContratoSituacao situacao;
    private Double valorPrevisto;
    private Double valorAplicado;
    private Double valorDesconto;
    @Embedded
    private RegistroPartes partes = new RegistroPartes();
    @Embedded
    private RegistroData data;

}
