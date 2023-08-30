package com.digytal.control.model.core.lancamentos.parcelamento.parcela;

import com.digytal.control.model.core.lancamentos.parcelamento.ParcelaQuitacao;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoNegociacao;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "apl_lancamentos", name = "tab_parcelamento_parcela")
@Data
public class ParcelaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String descricao;
    @Embedded
    private ParcelamentoNegociacao negociacao = new ParcelamentoNegociacao();
    @Embedded
    private ParcelaAliquota aliquota = new ParcelaAliquota();
    @Embedded
    private PacelaNegociacaoPendencia pendencia = new PacelaNegociacaoPendencia();
    @Embedded
    private ParcelaQuitacao quitacao = new ParcelaQuitacao();
    @Column(name = "parc_lancto_id", insertable = false, updatable = false)
    private Integer parcelamento;
    private String observacao;
    @Embedded
    private ParcelaBoleto boleto = new ParcelaBoleto();
}
