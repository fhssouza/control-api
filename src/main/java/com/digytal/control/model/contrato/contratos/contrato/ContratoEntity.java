package com.digytal.control.model.contrato.contratos.contrato;

import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.core.comum.RegistroPartes;
import com.digytal.control.model.contrato.meiopagamento.ContratoPagamentoEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "apl_contratos", name = "tab_contrato")
@Data
public class ContratoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String descricao;
    @Column(name = "tipo")
    private ContratoTipo tipo;
    @Column(name = "sit_contrato")
    private ContratoSituacao situacao;
    @Column(name = "vl_previsto")
    private Double valorPrevisto;
    @Column(name = "vl_aplicado")
    private Double valorAplicado;
    @Column(name = "vl_desconto")
    private Double valorDesconto;
    @Embedded
    private RegistroPartes partes = new RegistroPartes();
    @Column(name = "user_intermediador_id")
    private Integer intermediador;
    @Embedded
    private RegistroData data;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contrato_id")
    private List<ContratoPagamentoEntity> pagamentos = new ArrayList<>();
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contrato_id")
    private List<ContratoItemEntity> itens = new ArrayList<>();
    @Embedded
    private ContratoVigencia vigencia;
}
