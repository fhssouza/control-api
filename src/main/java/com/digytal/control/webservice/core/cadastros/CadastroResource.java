package com.digytal.control.webservice.core.cadastros;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.core.comum.cadastratamento.CadastroRequest;
import com.digytal.control.model.core.comum.cadastratamento.EmpresaRequest;
import com.digytal.control.service.core.cadastros.cadastro.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cadastros")
public class CadastroResource {
    @Autowired
    private CadastroService service;

    @PutMapping("/{id}")
    public Response alterarEmpresa(@PathVariable("id") Integer id, @RequestBody CadastroRequest request){
        return ResponseFactory.create(service.alterar(id, request),"Cadastro alterado com sucesso");
    }
    @PostMapping()
    @ResponseStatus( HttpStatus.CREATED )
    public Response incluirEmpresa(@RequestBody CadastroRequest request){
        return ResponseFactory.create(service.incluir(request),"Cadastro registrado com sucesso");
    }

    @GetMapping("/organizacao/{organizacao}/nome/{nome}")
    public Response listar(@PathVariable("organizacao") Integer organizacao,  @PathVariable("nome") String nome){
        return ResponseFactory.ok(service.listar(organizacao, nome),"Consulta realizada com sucesso");
    }
}
