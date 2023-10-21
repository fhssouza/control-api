package com.digytal.control.webservice.modulo.cadastro;

import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.infra.http.response.ResponseMessage;
import com.digytal.control.model.modulo.cadastro.produto.modelo.ModeloRequest;
import com.digytal.control.service.modulo.cadastro.ModeloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modelos")
@Tag(name = "Recursos referente ao modelo")
public class ModeloResource {
    @Autowired
    private ModeloService service;

    @Operation(summary = "Incluir um modelo", description = "Cadastrar um modelo na base de dados")
    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R403),
            @ApiResponse(responseCode = ResponseMessage.R409),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response incluir(@RequestBody ModeloRequest request) {
        return ResponseFactory.create(service.incluir(request), ResponseMessage.inclusao(Entities.MODELO_ENTITY.getLabel()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar um modelo", description = "Alterar um modelo da base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R404),
            @ApiResponse(responseCode = ResponseMessage.R409),
    })
    public Response alterar(@PathVariable("id") Integer id, @RequestBody ModeloRequest request) {
        return ResponseFactory.create(service.alterar(id, request), ResponseMessage.inclusao(Entities.MODELO_ENTITY.getLabel()));
    }

    @Operation(summary = "Lista o(s) modelo(s) por nome", description = "Retornar um filtro dos registros de modelo")
    @GetMapping("/nome/{nome}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar(@PathVariable("nome") String nome) {
        return ResponseFactory.ok(service.listar(nome),ResponseMessage.inclusao(Entities.MODULO_ENTITY.getLabel()));
    }
    @Operation(summary = "Lista todas os modelos sem filtro", description = "Retorna todos os registros de modelo")
    @GetMapping(value = "/listagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar(){
        return ResponseFactory.ok(service.listar(),ResponseMessage.listagem(Entities.MODELO_ENTITY.getLabel()));
    }
}
