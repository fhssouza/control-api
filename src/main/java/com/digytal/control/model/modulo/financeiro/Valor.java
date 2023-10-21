package com.digytal.control.model.modulo.financeiro;

import com.digytal.control.infra.utils.Calculos;
import com.digytal.control.model.modulo.acesso.empresa.aplicacao.AplicacaoTipo;
import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.RoundingMode;

@Embeddable
@Getter
public class Valor {
    @Column(name = "vl_informado")
    private Double valorInformado;
    @Column(name = "vl_operacional")
    private Double valorOperacional;

    public static Valor of(AplicacaoTipo tipo, Double valor){
        final RoundingMode RM= RoundingMode.HALF_EVEN;
        Valor instance = new Valor();
        Double valorInformado = Calculos.aplicarEscala4(valor);
        instance.valorInformado = valorInformado;
        instance.valorOperacional = Calculos.negativar(valorInformado,AplicacaoTipo.DESPESA==tipo);
        return instance;
    }

}
