package com.digytal.control.model.core.lancamentos.parcelamento;

import com.digytal.control.model.core.lancamentos.lancamento.Lancamento;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.PacelaNegociacaoPendencia;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "apl_lancamentos", name = "tab_parcelamento")
@Data
public class ParcelamentoEntity extends Lancamento {
    @Embedded
    @Setter(AccessLevel.NONE)
    private ParcelamentoNegociacao negociacao = new ParcelamentoNegociacao();

    @Embedded
    private PacelaNegociacaoPendencia pendencia = new PacelaNegociacaoPendencia();

    @Column(name = "is_fatura")
    private boolean fatura;
    @Column(name = "is_quitada")
    private boolean quitada;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parc_lancto_id")
    private List<ParcelaEntity> parcelas = new ArrayList<>();
}
