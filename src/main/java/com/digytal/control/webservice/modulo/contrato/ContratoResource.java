package com.digytal.control.webservice.modulo.contrato;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.modulo.contrato.request.ContratoRequest;
import com.digytal.control.service.modulo.contrato.ContratoVendaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contratos")
@Tag(name = "Recursos referente a lançamento de contratos")
public class ContratoResource {
    @Autowired
    private ContratoVendaService contratoVendaService;
    @PostMapping("/venda")
    public Response pagar(@RequestBody ContratoRequest request){
        Integer id = contratoVendaService.gravarContrato(request);
        return ResponseFactory.create(true,String.format("Contrato de Venda de Nº %d gerado com sucesso", id));

    }
}
