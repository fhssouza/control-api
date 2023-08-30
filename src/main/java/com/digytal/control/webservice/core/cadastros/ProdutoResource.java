package com.digytal.control.webservice.core.cadastros;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.core.cadastros.produto.ProdutoEntity;
import com.digytal.control.model.core.cadastros.produto.ProdutoRequest;
import com.digytal.control.repository.estoque.ProdutoRepository;
import com.digytal.control.service.core.cadastros.produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;
    @PostMapping()
    public Response incluir(@RequestBody ProdutoRequest request){
        return ResponseFactory.create(service.incluir(request),"Produto cadastrado com sucesso");
    }

    @PutMapping("/{id}")
    public Response alterar(@PathVariable("id") Integer id, @RequestBody ProdutoRequest request){
        return ResponseFactory.ok(service.alterar(id,request),"Produto alterado com sucesso");
    }

    @GetMapping("/organizacao/{organizacao}/nome/{nome}")
    public Response listar(@PathVariable("organizacao") Integer organizacao,  @PathVariable("nome") String nome){
        return ResponseFactory.ok(service.listar(organizacao, nome),"Consulta realizada com sucesso");
    }

}
