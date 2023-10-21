package com.digytal.control.webservice.modulo.financeiro.consulta;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.consulta.lancamentos.PagamentoFiltro;
import com.digytal.control.service.modulo.financeiro.consulta.PagamentoConsultaService;
import com.digytal.control.webservice.AbstractResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas/pagamentos")
@Tag(name = "Recursos referente a consultas e pagamentos")
public class PagamentoConsultaResource extends AbstractResource {
    @Autowired
    private PagamentoConsultaService service;
    @GetMapping("")
    public Response consultarPagamentos(PagamentoFiltro filtro ){
        return ResponseFactory.ok(service.listarPagamentos(filtro),"Consulta realizada com sucesso!");
    }
    @GetMapping("/completo")
    public Response consultarPagamentosCompleto(PagamentoFiltro filtro ){
        return ResponseFactory.ok(service.listarPagamentosCompleto(filtro),"Consulta realizada com sucesso!");
    }
}
