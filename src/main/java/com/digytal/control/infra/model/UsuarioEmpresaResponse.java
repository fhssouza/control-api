package com.digytal.control.infra.model;

import com.digytal.control.model.core.acessos.empresa.EmpresaConsultaResponse;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioEmpresaResponse extends UsuarioResponse {
    private boolean consultor;
    private List<EmpresaConsultaResponse> empresas;
}
