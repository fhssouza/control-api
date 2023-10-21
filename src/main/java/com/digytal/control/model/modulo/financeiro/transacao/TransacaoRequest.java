package com.digytal.control.model.modulo.financeiro.transacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(name = "Requisição de lancamentos",description = "Pré requisitos para lançameto")
public class TransacaoRequest {
    @Schema(description="data do lançamento",type = "date",example = "2023-01-01")
    private LocalDate data;
    @Schema( description = "numero do documento",type = "string",example = "089660")
    private String numeroDocumento;
    @Schema(description="título do lançamento",type = "string",example = "Receitas Diversas")
    private String titulo;
    @Schema(description="descrição do lançamento",type = "string",requiredMode = Schema.RequiredMode.REQUIRED,example = "Receitas Diversas sobre Venda ou Serviço")
    private String descricao;
    @Schema(description="calor informado",type = "numeric",requiredMode = Schema.RequiredMode.REQUIRED,example = "356.14")
    private Double valor;
    @Schema(description="Área, departamento ou setor",type = "numeric",example = "1")
    private Integer area;
    @Schema(description="idetificador único da natureza do lançamento",type = "numeric",example = "1")
    private Integer natureza;
    @Schema(description="identificador único do cadastro que originou o registro",type = "numeric", example = "1")
    private Integer cadastro;
    @Schema(description="forma ou rateio de pagamento")
    private List<TransacaoRateioRequest> formasPagamento;
    @Schema(description="Observações adicionais",type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "Receitas Diversas sobre Venda ou Serviço")
    private String observacao;
}
