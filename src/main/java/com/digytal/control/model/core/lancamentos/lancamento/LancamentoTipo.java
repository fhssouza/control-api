package com.digytal.control.model.core.lancamentos.lancamento;

import com.digytal.control.infra.persistence.EnumConverter;
import com.digytal.control.infra.persistence.EnumerateId;

public enum LancamentoTipo implements EnumerateId {
    RECEITA("R","Receita"),
    DESPESA("D","Desesa");
    private String id;
    private String descricao;
    private LancamentoTipo(String id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }
    public String getId() {
        return id;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getUpper() {
        return descricao.toUpperCase();
    }

    @javax.persistence.Converter(autoApply = true)
    public static class Converter extends EnumConverter<LancamentoTipo, String> {
        public Converter() {
            super(LancamentoTipo.class);
        }
    }
}
