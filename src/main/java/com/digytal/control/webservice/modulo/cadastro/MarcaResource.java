package com.digytal.control.webservice.modulo.cadastro;

import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.infra.http.response.ResponseMessage;
import com.digytal.control.model.modulo.cadastro.produto.marca.MarcaRequest;
import com.digytal.control.service.modulo.cadastro.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marcas")
@Tag(name = "Recursos referente a marca")
public class MarcaResource {
    @Autowired
    private MarcaService service;

    @Operation(summary = "Incluir uma marca", description = "Cadastrar uma marca na base de dados")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R403),
            @ApiResponse(responseCode = ResponseMessage.R409),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response incluir( @RequestBody MarcaRequest request) {
        return ResponseFactory.create(service.incluir(request), ResponseMessage.inclusao(Entities.MARCA_ENTITY.getLabel()));
    }

    @Operation(summary = "Alteração de marca", description = "Alterar uma marca da base de dados")
    @PutMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R404),
            @ApiResponse(responseCode = ResponseMessage.R409),
    })
    public Response alterar(@PathVariable(value = "id") Integer id, @RequestBody MarcaRequest request){
        return ResponseFactory.create(service.alterar(id, request), ResponseMessage.alteracao(Entities.MARCA_ENTITY.getLabel()));
    }

    @Operation(summary = "consultar marca(s) por nome", description = "Retorna um filtro dos registros de marca")
    @GetMapping(value = "/nome/{nome}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar(@PathVariable(value = "nome") String nome) {
        return ResponseFactory.ok(service. listar(nome),ResponseMessage.listagem(Entities.MARCA_ENTITY.getLabel()));
    }
    @Operation(summary = "Listar todas as marcas", description = "Retorna todos os registros de marca")
    @GetMapping(value = "/listagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar(){
        return ResponseFactory.ok(service.listar(),ResponseMessage.listagem(Entities.MARCA_ENTITY.getLabel()));
    }

}
