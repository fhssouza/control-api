package com.digytal.control.infra.utils;

import com.digytal.control.infra.persistence.EnumerateId;
import com.digytal.control.model.contrato.contratos.contrato.ContratoTipo;
import com.digytal.control.model.core.comum.MeioPagamento;

import java.util.Arrays;

public class EnumUtils {
    public static  <T> T find(Class <? extends  EnumerateId> cls, Object id){
        if(id==null)
            return null;
        else
        return (T) Arrays.stream(cls.getEnumConstants()).filter(i-> i.getId().equals(id.toString().toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        MeioPagamento formaPagamento = find(MeioPagamento.class, "P");
        System.out.println(formaPagamento.getDescricao());

        ContratoTipo tipo= find(ContratoTipo.class, "S");
        System.out.println(tipo.getDescricao());
    }
}
