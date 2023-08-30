CREATE SCHEMA apl_acessos;

CREATE TABLE apl_acessos.tab_organizacao (
	id                          serial4                         NOT NULL,
	cpf_cnpj                    varchar(20)                     NOT NULL,
	nome                        varchar(100)                    NOT NULL,
	CONSTRAINT pk_acessos_organizacao                           PRIMARY KEY (id)
);

CREATE TABLE apl_acessos.tab_empresa (
	id                          serial4                         NOT NULL,
	cpf_cnpj                    varchar(20)                     NOT NULL,
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
	organizacao_id              int4                            NOT NULL,
	ativ_com_prof               varchar(100)                        NULL,
	is_incompleto               bool                            NOT NULL,
	CONSTRAINT pk_acessos_empresa                               PRIMARY KEY (id)
);

ALTER TABLE apl_acessos.tab_empresa                             ADD CONSTRAINT fk_acessos_empresa_organizacao FOREIGN KEY (organizacao_id) REFERENCES apl_acessos.tab_organizacao(id);

CREATE TABLE apl_acessos.tab_usuario (
	id                          serial4                         NOT NULL,
	documento                   varchar(20)                     NOT NULL,
	email                       varchar(80)                     NOT NULL,
	is_expirado                 bool                            NOT NULL,
	is_bloqueado                bool                            NOT NULL,
	is_consultor                bool                            NOT NULL,
	login                       varchar(80)                     NOT NULL,
	nome                        varchar(50)                     NOT NULL,
	sobrenome                   varchar(50)                     NOT NULL,
	senha                       varchar(255)                    NOT NULL,
	cadastro_id                 int4                            NULL,
	CONSTRAINT pk_acessos_usuario                               PRIMARY KEY (id)
);

CREATE TABLE apl_acessos.tab_empresa_conta (
	id                          serial4                         NOT NULL,
	agencia                     varchar(10)                     NOT NULL,
	numero                      varchar(10)                     NOT NULL,
	saldo                       numeric(10,2)                   NOT NULL,
	legenda                     varchar(35)                     NOT NULL,
	descricao                   varchar(150)                        NULL,
	chave_pix                   varchar(100)                        NULL,
	is_conta_padrao             bool                            NOT NULL,
	is_conta_credito            bool                            NOT NULL,
	banco_id                    int4                                NULL,
	empresa_id                  int4                            NOT NULL,
	dia_vencimento              int4                                NULL,
	dias_intervalo              int4                                NULL,
	CONSTRAINT pk_acessos_empresa_conta                         PRIMARY KEY (id),
	CONSTRAINT fk_acessos_empresa_conta_emp                     FOREIGN KEY (empresa_id)            REFERENCES apl_acessos.tab_empresa(id),
	CONSTRAINT fk_acessos_empresa_conta_banco                   FOREIGN KEY (banco_id)              REFERENCES apl_params.tab_banco(id)
);

CREATE TABLE apl_acessos.tab_empresa_conta_meio_pagto (
	id                          serial4                         NOT NULL,
	empresa_id                  int4                            NOT NULL,
	empresa_conta_id            int4                            NOT NULL,
	meio_pagto                  char(1)                         NOT NULL,
	taxa                        numeric(4,2)                    NOT NULL,
	UNIQUE                      (empresa_id, meio_pagto),
	CONSTRAINT pk_acessos_empresa_conta_forma_pagto             PRIMARY KEY (id),
	CONSTRAINT fk_acessos_empresa_conta_emp_empresa             FOREIGN KEY (empresa_id)            REFERENCES apl_acessos.tab_empresa(id),
	CONSTRAINT fk_acessos_empresa_conta_emp_conta               FOREIGN KEY (empresa_conta_id)      REFERENCES apl_acessos.tab_empresa_conta(id),
	CONSTRAINT ck_acessos_empresa_meio_pagamento_tipo           CHECK (meio_pagto                   in ('A','B','C','D','X','Z'))
);

CREATE TABLE apl_acessos.tab_usuario_empresa (
	usuario_id                  int4                            NOT NULL,
	empresa_id                  int4                            NOT NULL,
	is_padrao                   bool            DEFAULT 'true'  NOT NULL,
	CONSTRAINT pk_acessos_usuario_empresa                       PRIMARY KEY (usuario_id,empresa_id),
    CONSTRAINT fk_acessos_usuario_empresa_emp                   FOREIGN KEY (empresa_id)            REFERENCES apl_acessos.tab_empresa(id),
    CONSTRAINT fk_acessos_usuario_empresa_user                  FOREIGN KEY (usuario_id)            REFERENCES apl_acessos.tab_usuario(id)
);

