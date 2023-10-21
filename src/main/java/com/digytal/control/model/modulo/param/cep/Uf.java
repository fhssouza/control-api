package com.digytal.control.model.modulo.param.cep;

public enum Uf {
    AC("ACRE"),
    AL("ALAGOAS"),
    AP("AMAPA"),
    AM("AMAZONAS"),
    BA("BAHIA"),
    CE("CEARA"),
    ES("ESPIRITO SANTO"),
    GO("GOIAS"),
    MA("MARANHAO"),
    MT("MATO GROSSO"),
    MS("MATO GROSSO DO SUL"),
    MG("MINAS GERAIS"),
    PA("PARA"),
    PB("PARAIBA"),
    PR("PARANA"),
    PE("PERNAMBUCO"),
    PI("PIAUI"),
    RJ("RIO DE JANEIRO"),
    RN("RIO GRANDE DO NORTE"),
    RS("RIO GRANDE DO SUL"),
    RO("RONDONIA"),
    RR("RORAIMA"),
    SC("SANTA CATARINA"),
    SP("SAO PAULO"),
    SE("SERGIPE"),
    DF("DISTRITO FEDERAL"),
    BR("BRASIL"),
    EX("EXTERIOR"),
    ;
    private String nome;
    private Uf(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
