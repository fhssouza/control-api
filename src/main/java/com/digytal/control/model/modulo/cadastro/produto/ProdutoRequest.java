package com.digytal.control.model.modulo.cadastro.produto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(name = "Requisição do produto",description = "Pré requisitos referente ao produto")
public class ProdutoRequest {
    @Schema(description = "nome do produto",minLength = 2,maxLength = 50,type = "string",example = "TECLADO GAMER MULTILED REDRAGON 300BPM", requiredMode= Schema.RequiredMode.REQUIRED)
    private String nome;
    @Schema(description = "nome do produto abreviado",minLength = 2,maxLength = 40,type = "string",example = "TECLADO GAMER MLED REDRAGON 300BPM")
    private String nomeAbreviado;
    @Schema(description = "unidade embalagem" ,type ="int", example = "1")
    private Integer unidadeEmbalagem;
    @Schema(description = "unidade medida", type = "int",example = "1",requiredMode= Schema.RequiredMode.REQUIRED)
    private Integer unidadeMedida;;
    @Schema(description = "códico de barras",minLength = 3,maxLength = 15,type = "string",example = "98758868")
    private String codigoBarras;
    @Schema(description = "sigla de estoque",minLength = 2,maxLength = 15,type = "string",example = "TCL233")
    private String sku;
    @Schema(description = "valor",type = "double", example = "18.5",requiredMode= Schema.RequiredMode.REQUIRED)
    private Double valor;
    @Schema(description = "taxa de liquidação",type = "double", example = "0.0")
    private Double taxaLiquidacao;
    @Schema(description = "campo que determina se o produto é considerado um serviço no sistema",type = "boolean",example = "false",allowableValues = {"true","false"})
    private boolean servico;
    @Schema(type = "integer",example = "1",description = "identificador relacionado a marca do produto")
    private Integer marca;
    @Schema(type = "integer",example = "1",description = "Identificador relacionado ao modelo do produto")
    private Integer modelo;
    @Schema(type = "integer",example = "1",description = "Identificador relacionado a categoria do produto")
    private Integer categoria;
    @Schema(description = "atualizar saldo?",type = "boolean",example = "false",allowableValues = {"true","false"})
    private boolean atualizaSaldo;
    @Schema(description = "esse produto é considerado um produto interno da organização ou mepresa?",type = "boolean",example = "true",allowableValues = {"true","false"})
    private boolean interno;
   
}
