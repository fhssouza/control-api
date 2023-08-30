package com.digytal.control.webservice.core.acessos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.core.cadastros.produto.ProdutoEntity;
import com.digytal.control.repository.estoque.ProdutoRepository;
import com.digytal.control.service.core.acessos.EmpresaConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizacoes")
public class OrganizacaoResource {
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/{id}/produtos")
    public Response listar(@PathVariable("id")Integer id){
        List<ProdutoEntity> responses = produtoRepository.findByOrganizacao(id);
        return ResponseFactory.create(responses,"Consulta realizada com sucesso");
    }


}
