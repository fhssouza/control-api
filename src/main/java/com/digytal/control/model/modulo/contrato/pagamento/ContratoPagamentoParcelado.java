package com.digytal.control.model.modulo.contrato.pagamento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Data
public class ContratoPagamentoParcelado {
    @Column(name = "vl_parcela")
    @Schema(description = "valor da parcela",type = "numeric",example = "475.8",requiredMode = Schema.RequiredMode.REQUIRED)
    private Double valorParcela;
    @Column(name = "num_parcelas")
    @Schema(description = "numero de parcelas",type = "numeric",minLength = 2,example = "4",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer numeroParcelas;
    @Column(name = "dt_pri_vencto")
    @Schema(description = "data do vencimento da parcela",type = "Date",example = "2023-12-05",requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataPrimeiroVencimento;
}
