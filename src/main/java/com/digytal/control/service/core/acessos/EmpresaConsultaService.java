package com.digytal.control.service.core.acessos;

import com.digytal.control.model.core.acessos.empresa.EmpresaConsultaResponse;
import com.digytal.control.repository.acessos.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaConsultaService {
    @Autowired
    private EmpresaRepository empresaRepository;
    public List<EmpresaConsultaResponse> listarEmpresas(Integer usuario){
        return empresaRepository.listarEmpresas(usuario);
    }
}
