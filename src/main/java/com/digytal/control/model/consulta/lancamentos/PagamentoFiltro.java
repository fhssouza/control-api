package com.digytal.control.model.consulta.lancamentos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Filtro de pagamento", description = "Filtro para localizar pagamentos específico")
public class PagamentoFiltro extends LancamentoFiltro{
    @Schema(description = "numero da conta",type = "numeric",example = "1001728",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer conta;
    public static PagamentoFiltro of(){
        PagamentoFiltro filtro = new PagamentoFiltro();
        BeanUtils.copyProperties(LancamentoFiltro.of(), filtro);
        return filtro;
    }
}
