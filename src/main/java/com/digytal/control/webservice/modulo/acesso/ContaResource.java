package com.digytal.control.webservice.modulo.acesso;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.modulo.acesso.empresa.conta.ContaRequest;
import com.digytal.control.model.modulo.acesso.empresa.conta.ContaResponse;
import com.digytal.control.model.modulo.acesso.empresa.pagamento.FormaPagamentoRequest;
import com.digytal.control.service.modulo.acesso.ContaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
@Tag(name = "Recursos referente a contas")
public class ContaResource {
    @Autowired
    private ContaService service;
    @GetMapping
    public Response listarContas(){
        return ResponseFactory.ok(service.listarContas(),"Consulta realizada com sucesso");
    }
    @GetMapping("/carteira")
    public Response buscarContaCarteira(){
        ContaResponse response = service.buscarContaCarteira();
        return ResponseFactory.ok(response,"Consulta realizada com sucesso!");
    }
    @GetMapping("/{id}/formas-pagamento")
    public Response listarFormasPagamento(@PathVariable("id") Integer id){
        return ResponseFactory.ok(service.listarFormasPagamento(id),"Consulta realizada com sucesso");
    }
    @PostMapping("/{id}/formas-pagamento")
    @ResponseStatus( HttpStatus.CREATED )
    public Response incluir(@PathVariable("id") Integer id ,@RequestBody FormaPagamentoRequest request){
        return ResponseFactory.create(service.incluirFormaPagamento(id, request),"Forma de pagamento adicionada a esta conta");
    }

    @PostMapping()
    @ResponseStatus( HttpStatus.CREATED )
    public Response incluir(@RequestBody ContaRequest request){
        return ResponseFactory.create(service.incluir(request),"Conta adicionada a empresa");
    }
    @PutMapping("/{id}")
    public Response alterar(@PathVariable("id") Integer id, @RequestBody ContaRequest request){
        return ResponseFactory.ok(service.alterar(id, request),"Conta alterada com sucesso");
    }
    @DeleteMapping("/formas-pagamento/{id}")
    public Response excluirFormaPagamento(@PathVariable("id") Integer id){
        return ResponseFactory.ok(service.excluirFormaPagamento(id),"Forma de pagamento excluida com sucesso");
    }

}
