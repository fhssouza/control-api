package com.digytal.control.webservice.modulo.cadastro;

import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.infra.http.response.ResponseMessage;
import com.digytal.control.model.modulo.cadastro.produto.categoria.CategoriaRequest;
import com.digytal.control.service.modulo.cadastro.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Recursos referente a categoria")
public class CategoriaResource {
    @Autowired
    private CategoriaService service;

    @Operation(summary = "Incluir uma categorias", description = "Cadastrar uma categoria na base de dados")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R403),
            @ApiResponse(responseCode = ResponseMessage.R409),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response incluir(@RequestBody CategoriaRequest request) {
        return ResponseFactory.create(service.incluir(request), ResponseMessage.inclusao(Entities.CATEGORIA_ENTITY.getLabel()));
    }

    @Operation(summary = "Alterar uma categoria", description = "Alterar uma categoria da base de dados")
    @PutMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R404),
            @ApiResponse(responseCode = ResponseMessage.R409),
    })
    public Response alterar(@PathVariable(value = "id") Integer id, @RequestBody CategoriaRequest request){
        return ResponseFactory.create(service.alterar(id, request), ResponseMessage.alteracao(Entities.CATEGORIA_ENTITY.getLabel()));
    }

    @Operation(summary = "Lista a(s) categoria(s) por nome", description = "Retorna um filtro de categoria")
    @GetMapping(value = "/nome/{nome}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar(@PathVariable(value = "nome") String nome) {
        return ResponseFactory.ok(service. listar(nome),ResponseMessage.listagem(Entities.CATEGORIA_ENTITY.getLabel()));
    }
    @Operation(summary = "Lista todas as categorias", description = "Retorna todas as categorias")
    @GetMapping(value = "/listagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar(){
        return ResponseFactory.ok(service.listar(),ResponseMessage.listagem(Entities.CATEGORIA_ENTITY.getLabel()));
    }
}
