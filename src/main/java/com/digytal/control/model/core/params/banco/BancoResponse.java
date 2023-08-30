package com.digytal.control.model.core.params.banco;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
public class BancoResponse {
    private Integer id;
    private Integer compe;
    private Long ispb;
    private String nome;
    private String legenda;
}
