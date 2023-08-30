package com.digytal.control.webservice.contratos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.contrato.contratos.comercializacao.ComercializacaoRequest;
import com.digytal.control.service.contratos.ComercializacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comercializacoes")
public class ComercializacaoResource {
    @Autowired
    private ComercializacaoService service;
    @PostMapping("/venda-direta")
    @ResponseStatus( HttpStatus.CREATED )
    public Response confirmarVendaDireta(@RequestBody ComercializacaoRequest request){
        return ResponseFactory.create(service.confirmarVendaDireta(request),"Venda Direta gerada com sucesso");
    }

}
