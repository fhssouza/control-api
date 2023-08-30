package com.digytal.control.model.contrato.contratos.contrato;

import com.digytal.control.model.core.cadastros.produto.ProdutoItem;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(schema = "apl_contratos", name = "tab_contrato_item")
@Data
public class ContratoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Embedded
    private ProdutoItem produto;
    @Column(name = "item_quant")
    private Double quantidade;
    @Column(name = "item_vl_unit")
    private Double valorUnitario;
    @Column(name = "item_vl_previsto")
    private Double valorPrevisto;
    @Column(name = "item_vl_aplicado")
    private Double valorAplicado;
}
