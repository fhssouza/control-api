package com.digytal.control.model.modulo.contrato;

import com.digytal.control.infra.persistence.EnumConverter;
import com.digytal.control.infra.persistence.EnumerateId;

public enum ContratoTipo implements EnumerateId {
    COMPRA("C","Compra"),
    VENDA("V","Venda"),
    LOCACAO("L","Locação"),
    SERVICO("S","Serviço");

    private String id;
    private String descricao;
    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
    private ContratoTipo(String id,String descricao){
        this.id = id;
        this.descricao=descricao;
    }

    @Override
    public String getUpper() {
        return descricao.toUpperCase();
    }

    @javax.persistence.Converter(autoApply = true)
    public static class Converter extends EnumConverter<ContratoTipo, String> {
        public Converter() {
            super(ContratoTipo.class);
        }
    }
}
