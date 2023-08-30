package com.digytal.control.webservice.core.acessos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.service.core.acessos.EmpresaConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
    @Autowired
    private EmpresaConsultaService empresaConsultaService;

    @GetMapping("/{usuario}/empresas")
    public Response listarEmpresas(@PathVariable("usuario") Integer usuario){
        return ResponseFactory.create(empresaConsultaService.listarEmpresas(usuario),"Consulta realizada com sucesso");
    }
}
