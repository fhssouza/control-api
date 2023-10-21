package com.digytal.control.model.modulo.financeiro.pagamento;

import com.digytal.control.model.comum.MeioPagamento;
import com.digytal.control.model.modulo.financeiro.Valor;
import com.digytal.control.model.modulo.financeiro.Movimento;
import lombok.Data;
import javax.persistence.*;
@Entity
@Table(schema = "apl_finaceiro", name = "tab_pagamento")
@Data
public class PagamentoEntity extends Movimento {
    @Column(name = "conta_id")
    private Integer conta;
    @Embedded
    private Valor valor;
}
