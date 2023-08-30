package com.digytal.control.model.core.comum;

import com.digytal.control.model.core.comum.endereco.Endereco;
import com.digytal.control.model.core.comum.telefone.Telefone;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class RegistroCadastralResponse {
    private Integer id;
    private String nomeFantasia;
    private String sobrenomeSocial;
    private String rgIe;
    private String cpfCnpj;
    private String email;
    private LocalDate aniversario;
    private String atividadeComecialProfissional;
    private Integer organizacao;
    private boolean incompleto;
    private Endereco endereco = new Endereco();
    private Telefone telefone = new Telefone();
}
