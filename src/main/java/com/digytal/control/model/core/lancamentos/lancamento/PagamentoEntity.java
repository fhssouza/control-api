package com.digytal.control.model.core.lancamentos.lancamento;

import com.digytal.control.model.core.lancamentos.LancamentoValor;
import lombok.Data;
import javax.persistence.*;


@Entity
@Table(schema = "apl_lancamentos", name = "tab_lancamento")
@Data
public class PagamentoEntity extends Lancamento {
    @Embedded
    private LancamentoValor valor;

}
