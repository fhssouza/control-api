package com.digytal.control.model.modulo.financeiro.parcelamento.parcela;

import com.digytal.control.model.modulo.financeiro.parcelamento.ParcelamentoValor;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "apl_finaceiro", name = "tab_parcelamento_parcela")
@Data
public class ParcelaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String descricao;
    @Embedded
    private ParcelamentoValor valor = new ParcelamentoValor();
    @Embedded
    private ParcelaAliquota aliquota = new ParcelaAliquota();
    @Embedded
    private PacelaPendencia pendencia = new PacelaPendencia();
    @Embedded
    private ParcelaQuitacao quitacao = new ParcelaQuitacao();
    @Column(name = "parc_lancto_id", insertable = false, updatable = false)
    private Integer parcelamento;
    private String observacao;

}
