package com.digytal.control.model.core.comum.cadastratamento;

import com.digytal.control.model.core.comum.endereco.Endereco;
import com.digytal.control.model.core.comum.telefone.Telefone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CadastroCompletoRequest extends CadastroSimplificadoRequest {
    @Schema(description="ID da organização do cadastro",example = "1")
    private Integer organizacao;
    private Telefone telefone = new Telefone();
    @Schema(description="CPF ou CNPJ", maximum="14",example = "58777339000189")
    private String cpfCnpj;
    @Schema(description="RG ou inscrição estadual", maximum="20",example = "1546334")
    private String rgIe;
    @Schema(description="Data de nascimento ou constituição da empresa", example = "1984-06-30")
    private LocalDate aniversario;
    @Schema(description="Atividade comercial ou profissional", example = "VENDA DE PRODUTOS IMPORTADOS")
    private String atividadeComecialProfissional;
    private Endereco endereco;
}
