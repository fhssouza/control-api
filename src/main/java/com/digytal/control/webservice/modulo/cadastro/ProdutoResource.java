package com.digytal.control.webservice.modulo.cadastro;

import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.infra.http.response.ResponseMessage;
import com.digytal.control.model.modulo.cadastro.produto.ProdutoRequest;
import com.digytal.control.service.modulo.cadastro.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Recursos referente a produto")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @Operation(summary = "Incluir um produto", description = "Cadastrar um produto na base de dados")
    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R403),
            @ApiResponse(responseCode = ResponseMessage.R409),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response incluir(@RequestBody ProdutoRequest request) {
        return ResponseFactory.create(service.incluir(request), ResponseMessage.inclusao(Entities.PRODUTO_ENTITY.getLabel()));
    }

    @Operation(summary = "Alterar um produto", description = "Alterar um produto da base de dados")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R404),
            @ApiResponse(responseCode = ResponseMessage.R409),
    })
    public Response alterar(@PathVariable("id") Integer id, @RequestBody ProdutoRequest request) {
        return ResponseFactory.create(service.alterar(id, request), ResponseMessage.alteracao(Entities.PRODUTO_ENTITY.getLabel()));
    }

    @Operation(summary = "Listar o(s) produto(s)", description = "Retorna uma lista de produto(s)")
    @GetMapping("/com-internos/{com-internos}/nome/{nome}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar(@PathVariable("com-internos") boolean comInternos,@PathVariable("nome") String nome) {
        return ResponseFactory.ok(service.listar(comInternos,nome), ResponseMessage.listagem(Entities.PRODUTO_ENTITY.getLabel()));
    }
    @Operation(summary = "Lista o produto pelo id", description = "Retorna o produto selecionado pelo id")
    @GetMapping("/pesquisar/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response pesquisar(@PathVariable("id") Integer produceId) {
        return ResponseFactory.ok(service.pesquisar(produceId), ResponseMessage.listagem(Entities.PRODUTO_ENTITY.getLabel()));
    }

}
