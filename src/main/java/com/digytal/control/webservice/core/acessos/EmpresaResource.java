package com.digytal.control.webservice.core.acessos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.core.acessos.empresa.conta.EmpresaContaCadastroRequest;
import com.digytal.control.model.core.comum.cadastratamento.EmpresaRequest;
import com.digytal.control.service.core.acessos.EmpresaContaService;
import com.digytal.control.service.core.acessos.EmpresaService;
import com.digytal.control.service.core.params.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {
    @Autowired
    private EmpresaService service;
    @Autowired
    private BancoService bancoService;

    @Autowired
    private EmpresaContaService empresaContaService;

    @PostMapping("/{id}/contas")
    @ResponseStatus( HttpStatus.CREATED )
    public Response incluirConta(@PathVariable("id") Integer id, @RequestBody EmpresaContaCadastroRequest request){
        return ResponseFactory.create(service.incluirConta(id, request),"Conta banco adicionanda a empresa");
    }
    @PutMapping("/contas/{id}")
    public Response alterarConta(@PathVariable("id") Integer id, @RequestBody EmpresaContaCadastroRequest request){
        return ResponseFactory.ok(service.alterarConta(id, request),"Conta banco alterada com sucesso");
    }
    @GetMapping("/{id}/contas")
    public Response listarContas(@PathVariable("id") Integer id){
        return ResponseFactory.create(service.listarContas(id),"Consulta realizada com sucesso");
    }

    @GetMapping("/bancos/{nome}")
    public Response listarBancos(@PathVariable("nome") String nome){
        return ResponseFactory.ok(bancoService.listar(nome),"Consulta realizada com sucesso");
    }

    @GetMapping("/{id}")
    public Response buscar(@PathVariable("id") Integer id){
        return ResponseFactory.ok(service.buscar(id),"Pesquisa realizada com sucesso");
    }
    @PutMapping("/{id}")
    public Response alterarEmpresa(@PathVariable("id") Integer id, @RequestBody EmpresaRequest request){
        return ResponseFactory.ok(service.alterar(id, request),"Empresa alterada com sucesso");
    }
    @PostMapping()
    @ResponseStatus( HttpStatus.CREATED )
    public Response incluirEmpresa(@RequestBody EmpresaRequest request){
        return ResponseFactory.create(service.incluir(request),"Empresa registrada com sucesso");
    }

    @GetMapping("/{id}/verifica-conta-forma-pagamento")
    public Response verificarContaFormaPagamento(@PathVariable("id") Integer id){
        return ResponseFactory.ok(empresaContaService.verificarContaFormaPagamento(id),"Verificação realizada com sucesso");
    }


}
