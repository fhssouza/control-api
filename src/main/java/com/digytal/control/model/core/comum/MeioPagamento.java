package com.digytal.control.model.core.comum;

import com.digytal.control.infra.persistence.EnumConverter;
import com.digytal.control.infra.persistence.EnumerateId;

import java.util.Arrays;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MeioPagamento implements EnumerateId {
    DINHEIRO("A","Dinheiro",true),
    PIX("X","Pix",true),
    DEBITO("D","Débito",true),
    BOLETO("B","Boleto",false),
    CREDITO("C","Crédito",false),
    PARCELADO("Z","Parcelado",false),
    SALDO ("Y","Saldo",true),
    ;
    private String id;
    private String descricao;
    private boolean atualizaSaldo;
    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
    private MeioPagamento(String id, String descricao, boolean atualizaSaldo){
        this.id = id;
        this.descricao=descricao;
        this.atualizaSaldo=atualizaSaldo;
    }

    public boolean isAtualizaSaldo() {
        return atualizaSaldo;
    }

    @Override
    public String getUpper() {
        return descricao.toUpperCase();
    }

    @javax.persistence.Converter(autoApply = true)
    public static class Converter extends EnumConverter<MeioPagamento, String> {
        public Converter() {
            super(MeioPagamento.class);
        }
    }
    public static MeioPagamento find(String id){
        return  Arrays.stream(MeioPagamento.class.getEnumConstants()).filter(i-> i.id.equals(id.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
