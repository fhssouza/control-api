package com.digytal.control.webservice.core.acessos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoRequest;
import com.digytal.control.service.core.acessos.EmpresaContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresa-contas")
public class EmpresaContaResource {
    @Autowired
    private EmpresaContaService service;
    @PostMapping("/{id}/formas-pagamento")
    @ResponseStatus( HttpStatus.CREATED )
    public Response incluir(@PathVariable("id") Integer id ,@RequestBody EmpresaContaMeioPagamentoRequest request){
        return ResponseFactory.create(service.incluir(id, request),"Forma de pagamento adicionada a esta conta");
    }
    @DeleteMapping("/formas-pagamento/{id}")
    public Response excluir(@PathVariable("id") Integer id){
        return ResponseFactory.ok(service.excluir(id),"Forma de pagamento exclúida desta conta");
    }
    @PatchMapping("/formas-pagamento/{id}/taxa/{taxa}")
    public Response alterar(@PathVariable("id") Integer id, @PathVariable("taxa") Double taxa){
        return ResponseFactory.ok(service.alterarTaxa(id,taxa),"Forma de pagamento alterada com sucesso");
    }
    @GetMapping("/{id}/formas-pagamento")
    public Response listarFormasPagamento(@PathVariable("id") Integer id){
        return ResponseFactory.ok(service.listarContaFormasPagamento(id),"Consulta realizada com sucesso");
    }
}
