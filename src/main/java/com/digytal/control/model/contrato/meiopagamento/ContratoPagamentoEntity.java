package com.digytal.control.model.contrato.meiopagamento;

import com.digytal.control.model.core.comum.MeioPagamento;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "apl_contratos", name = "tab_contrato_forma_pagamento")
@Data
public class ContratoPagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Column(name = "meio_pagto")
    private MeioPagamento meioPagamento;
    @Column(name = "vl_pago")
    private Double valor;
    @Column(name = "vl_parcela")
    private Double valorParcela;
    @Column(name = "num_parcelas")
    private Integer numeroParcelas;
    @Column(name = "dt_pri_vencto")
    private LocalDate dataPrimeiroVencimento;
    @Column(name = "contrato_id", insertable = false, updatable = false)
    private Integer contrato;
}
