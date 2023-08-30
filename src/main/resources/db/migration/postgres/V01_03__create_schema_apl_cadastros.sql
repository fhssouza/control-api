CREATE SCHEMA apl_cadastros;

CREATE TABLE apl_cadastros.tab_cadastro (
	id                          serial4                         NOT NULL,
    cpf_cnpj                    varchar(20)                         NULL,
    nome_fantasia               varchar(80)                     NOT NULL,
    sobrenome_social            varchar(80)                     NOT NULL,
    email                       varchar(80)                     NOT NULL,
    dt_aniversario              date                                NULL,
    rg_ie                       varchar(25)                         NULL,
    end_cep                     varchar(8)                          NULL,
    end_logradouro              varchar(100)                        NULL,
    end_numero                  varchar(8)                          NULL,
    end_complemento             varchar(40)                         NULL,
    end_referencia              varchar(100)                        NULL,
    end_telefone                int8                                NULL,
    end_bairro                  varchar(80)                         NULL,
    end_estado                  varchar(70)                         NULL,
    end_cidade                  varchar(100)                        NULL,
    end_uf                      char(2)                             NULL,
    end_ibge                    int4                                NULL,
    tel_celular                 int8                                NULL,
    tel_celular_whatsapp        bool                                NULL,
    tel_fixo                    int8                                NULL,
    is_cliente                  bool                            NOT NULL,
    is_fornecedor               bool                            NOT NULL,
    ativ_com_prof               varchar(100)                        NULL,
    organizacao_id              int4                                NULL,
    is_incompleto               bool                            NOT NULL,
    cod_integracao              varchar(50)                         NULL,
	CONSTRAINT tab_cadastros_cadastro                           PRIMARY KEY (id),
	CONSTRAINT fk_cadastros_cadastro_organizacao                FOREIGN KEY (organizacao_id)                       REFERENCES apl_acessos.tab_organizacao(id)
);

ALTER TABLE apl_acessos.tab_usuario                             ADD CONSTRAINT fk_acessos_usuario_cadastro FOREIGN KEY (cadastro_id) REFERENCES apl_cadastros.tab_cadastro(id);

CREATE TABLE apl_cadastros.tab_produto (
	id                          serial4                         NOT NULL,
	nome                        varchar(70)                     NOT NULL,
	und_medida                  varchar(10)                     NOT NULL,
	cod_barras                  varchar(20)                         NULL,
	sku                         varchar(20)                         NULL,
	vl_produto                  numeric(10,4)                   NOT NULL,
	saldo                       numeric(10,4)                   NOT NULL,
	is_servico                  bool                            NOT NULL,
	fl_atualiza_saldo           bool                            NOT NULL,
	app_is_visivel              bool                            NOT NULL,
	app_ordem_visualizacao      int4                            NOT NULL,
	organizacao_id              int4                            NOT NULL,
	CONSTRAINT pk_cadastros_produto                             PRIMARY KEY (id),
	CONSTRAINT fk_cadastros_produto_organizacao                 FOREIGN KEY (organizacao_id)                       REFERENCES apl_acessos.tab_organizacao(id)
);