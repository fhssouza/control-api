package com.digytal.control.webservice.core.lancamentos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.core.lancamentos.transferencia.TransferenciaBalcao;
import com.digytal.control.service.core.lancamentos.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaResource {
    @Autowired
    private PagamentoService service;
    @PostMapping("/conta-balcao/{valor}")
    @ResponseStatus( HttpStatus.CREATED )
    public Response realizarPagamento(@PathVariable("valor") Double valor, @RequestBody TransferenciaBalcao request){
        service.transferir(request,valor);
        return ResponseFactory.create(true,"Transferência realizada com sucesso");
    }
}
