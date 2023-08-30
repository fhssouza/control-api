package com.digytal.control.webservice.contratos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.contrato.contratos.assinatura.AssinaturaRequest;
import com.digytal.control.service.contratos.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/assinaturas")
public class AssinaturaResource {
    @Autowired
    private AssinaturaService service;
    @PostMapping()
    @ResponseStatus( HttpStatus.CREATED )
    public Response gerar(@RequestBody AssinaturaRequest request){
         return ResponseFactory.create(service.gerar(request),"Assinatura geradda com sucesso");
    }
}
