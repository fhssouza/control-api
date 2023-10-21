package com.digytal.control.webservice.modulo.cadastro;

import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.infra.http.response.ResponseMessage;
import com.digytal.control.model.modulo.cadastro.produto.unidademedida.UnidadeMedidaRequest;
import com.digytal.control.service.modulo.cadastro.UnidadeMedidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unidades-medida")
@Tag(name = "Recursos referente a unidade medida")
public class UnidadeMedidaResource {
    @Autowired
    private UnidadeMedidaService service;
    @Operation(summary = "Inclui uma unidade medida", description = "Cadastrar uma unidade na base de dados")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R403),
            @ApiResponse(responseCode = ResponseMessage.R409),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response incluir(@RequestBody UnidadeMedidaRequest request) {
        return ResponseFactory.create(service.incluir(request), ResponseMessage.inclusao(Entities.UNIDADE_MEDIDA_ENTITY.getLabel()));
    }

    @Operation(summary = "Altera uma unidade medida", description = "Alterar uma unidade da base de dados")
    @PutMapping ("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R404),
            @ApiResponse(responseCode = ResponseMessage.R409),
    })
    public Response alterar(@PathVariable("id") Integer id, @RequestBody UnidadeMedidaRequest request){
        return ResponseFactory.create(service.alterar(id,request), ResponseMessage.alteracao(Entities.UNIDADE_MEDIDA_ENTITY.getLabel()));
    }
    @Operation(summary = "Consulta as unidades de medida", description = "Retorna uma consulta de unidades de medida com base no nome informado")
    @GetMapping(value = "/nome/{nome}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar(@PathVariable(value = "nome") String nome) {
        return ResponseFactory.ok(service.listar(nome),ResponseMessage.consulta(Entities.UNIDADE_MEDIDA_ENTITY.getLabel()));
    }
    @Operation(summary = "Lista as unidades de medida sem aplicar filtros", description = "Retorna todos os registros de unidades de medida")
    @GetMapping(value = "/listagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listar() {
        return ResponseFactory.ok(service.listar(),ResponseMessage.listagem(Entities.UNIDADE_MEDIDA_ENTITY.getLabel()));
    }
}
