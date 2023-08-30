package com.digytal.control.model.core.comum;

import com.digytal.control.model.core.comum.endereco.Endereco;
import com.digytal.control.model.core.comum.telefone.Telefone;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
public class RegistroCadastralEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    @Column(name = "sobrenome_social")
    private String sobrenomeSocial;
    @Column(name = "rg_ie")
    private String rgIe;
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;
    @Column(name = "email")
    private String email;
    @Embedded
    private Endereco endereco = new Endereco();
    @Embedded
    private Telefone telefone = new Telefone();
    @Column(name = "dt_aniversario")
    private LocalDate aniversario;
    @Column(name = "ativ_com_prof")
    private String atividadeComecialProfissional="";
    @Column(name = "organizacao_id")
    private Integer organizacao;
    @Column(name = "is_incompleto")
    private boolean incompleto;
}
