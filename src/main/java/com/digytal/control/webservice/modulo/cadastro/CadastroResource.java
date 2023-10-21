package com.digytal.control.webservice.modulo.cadastro;

import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.infra.http.response.ResponseMessage;
import com.digytal.control.model.modulo.cadastro.CadastroRequest;
import com.digytal.control.service.modulo.cadastro.CadastroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cadastros")
@Tag(name = "Recursos referente a cadastro de clientes e fornecedores")
public class CadastroResource {
    @Autowired
    private CadastroService service;

    @PutMapping("/{id}")
    @Operation(summary = "Alterar um cadastro", description = "Altera um cadastro da base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R404),
            @ApiResponse(responseCode = ResponseMessage.R409),
    })
    public Response alterar(@PathVariable("id") Integer id, @RequestBody CadastroRequest request){
        return ResponseFactory.create(service.alterar(id, request),ResponseMessage.alteracao(Entities.CADASTRO_ENTITY.getLabel()));
    }
    @PostMapping()
    @Operation(summary = "Incluir uma cadastro", description = "Inclui um cadastro na base de dados")
  
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R201),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R401),
            @ApiResponse(responseCode = ResponseMessage.R403),
            @ApiResponse(responseCode = ResponseMessage.R409),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response incluir(@RequestBody CadastroRequest request){
        return ResponseFactory.create(service.incluir(request),ResponseMessage.inclusao(Entities.CADASTRO_ENTITY.getLabel()));
    }

    @GetMapping("/clientes/nome/{nome}")
    @Operation(summary = "Listar o(s) cliente(s)", description = "Retorna uma lista do(s) cliente(s) da base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listarClientes(@PathVariable("nome") String nome){
        return ResponseFactory.ok(service.listarClientes(nome),ResponseMessage.listagem(Entities.CLIENTE_ENTITY.getLabel()));
    }
    @GetMapping("/fornecedores/nome/{nome}")
    @Operation(summary = "Listar o(s) fornecedor(es)", description = "Retorna uma lista do(s) fornecedor(es) da base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseMessage.R200),
            @ApiResponse(responseCode = ResponseMessage.R204),
            @ApiResponse(responseCode = ResponseMessage.R400),
            @ApiResponse(responseCode = ResponseMessage.R500),
    })
    public Response listarFornecedores(@PathVariable("nome") String nome){
        return ResponseFactory.ok(service.listarForncedores(nome),ResponseMessage.listagem(Entities.FORNECEDORES_ENTITY.getLabel()));
    }
}
