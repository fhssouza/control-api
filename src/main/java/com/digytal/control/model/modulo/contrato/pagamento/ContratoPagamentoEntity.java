package com.digytal.control.model.modulo.contrato.pagamento;

import com.digytal.control.model.comum.MeioPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(schema = "apl_contrato", name = "tab_contrato_forma_pagamento")
@Data
public class ContratoPagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Schema(description = "identificador único do contrato")
    private Integer id;
    private String localiza;
    @Column(name = "meio_pagto")
    @Schema(description = "meio de pagamento",requiredMode = Schema.RequiredMode.REQUIRED,subTypes = MeioPagamento.class)
    private MeioPagamento meioPagamento;
    @Column(name = "vl_original")
    @Schema(description = "valor original do contrato",type = "numeric", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double valorOriginal;
    @Column(name = "vl_pago")
    @Schema(description = "valor pago",type = "numeric",requiredMode = Schema.RequiredMode.REQUIRED)
    private Double valorPago;
    @Column(name = "tx_meio_pagto")
    @Schema(description = "valor pago",type = "numeric",requiredMode = Schema.RequiredMode.REQUIRED)
    private Double taxaPagamento;
    @Embedded
    @Schema( subTypes = ContratoPagamentoParcelado.class)
    private ContratoPagamentoParcelado parcelamento;
    @Column(name = "contrato_id", insertable = false, updatable = false)
    @Schema(description = "numero do contrato",type = "numeric",requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer contrato;
}
