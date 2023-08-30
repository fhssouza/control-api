CREATE SCHEMA apl_contratos;
CREATE TABLE apl_contratos.tab_contrato (
	id                          serial4                         NOT NULL,
	descricao                   varchar(100)                    NOT NULL,
	cpt_ano                     int4                            NOT NULL,
	cpt_per_competencia         int4                            NOT NULL,
	cpt_dh_lancto               timestamp                       NOT NULL,
    vl_previsto                 numeric(9,2)                    NOT NULL,
    vl_aplicado                 numeric(9,2)                    NOT NULL,
    vl_desconto                 numeric(9,2)                    NOT NULL,
	cpt_dia                     date                            NOT NULL,
	cpt_mes                     int4                            NOT NULL,
	cpt_per_lancto              int4                            NOT NULL,
	part_cadastro_id            int4                            NOT NULL,
	part_empresa_id             int4                            NOT NULL,
	part_organizacao_id         int4                            NOT NULL,
	part_usuario_id             int4                            NOT NULL,
	user_intermediador_id       int4                            NOT NULL,
	sit_contrato                char(1)                         NOT NULL,
	tipo                        char(1)                         NOT NULL,
	vigencia_inicio             timestamp                           NULL,
	vigencia_fim                timestamp                           NULL,
	CONSTRAINT pk_contratos_contrato                            PRIMARY KEY (id),
    CONSTRAINT fk_contratos_contrato_organizacao                FOREIGN KEY (part_organizacao_id)               REFERENCES apl_acessos.tab_organizacao(id),
    CONSTRAINT fk_contratos_contrato_empresa                    FOREIGN KEY (part_empresa_id)                   REFERENCES apl_acessos.tab_empresa(id),
    CONSTRAINT fk_contratos_contrato_usuario                    FOREIGN KEY (part_usuario_id)                   REFERENCES apl_acessos.tab_usuario(id),
    CONSTRAINT fk_contratos_contrato_intermediador              FOREIGN KEY (user_intermediador_id)             REFERENCES apl_acessos.tab_usuario(id),
    CONSTRAINT fk_contratos_contrato_cadastro                   FOREIGN KEY (part_cadastro_id)                  REFERENCES apl_cadastros.tab_cadastro(id),
    CONSTRAINT ck_contratos_contrato_situacao                   CHECK (sit_contrato                             in ('S','E','A','C','B','U','R','P','V')),
    CONSTRAINT ck_contratos_contrato_tipo                       CHECK (tipo                                     in ('C','V','L','S'))

);

CREATE TABLE apl_contratos.tab_contrato_item (
	id                          serial4                         NOT NULL,
	prod_id                     int4                            NOT NULL,
	prod_nome                   varchar(80)                     NOT NULL,
	prod_und                    varchar(10)                     NOT NULL,
	prod_cb                     varchar(20)                     NOT NULL,
    prod_sku                    varchar(20)                     NOT NULL,
	prod_valor                  numeric(10,4)                   NOT NULL,
	prod_saldo                  numeric(10,4)                   NOT NULL,
	item_quant                  numeric(10,4)                   NOT NULL,
	item_vl_unit                numeric(10,4)                   NOT NULL,
	item_vl_previsto            numeric(10,4)                   NOT NULL,
    item_vl_aplicado            numeric(10,4)                   NOT NULL,
	contrato_id                 int4                                NULL,

	CONSTRAINT pk_contratos_contrato_item                       PRIMARY KEY (id),
    CONSTRAINT fk_contratos_contrato_item_contrato              FOREIGN KEY (contrato_id)                       REFERENCES apl_contratos.tab_contrato(id),
    CONSTRAINT fk_contratos_contrato_item_produto               FOREIGN KEY (prod_id)                           REFERENCES apl_cadastros.tab_produto(id)

);

CREATE TABLE apl_contratos.tab_contrato_forma_pagamento (
	id                          serial4                         NOT NULL,
	meio_pagto                  char(1)                         NOT NULL,
	vl_pago                     numeric(9,2)                    NOT NULL,
	vl_parcela                  numeric(9,2)                    NOT NULL,
	num_parcelas                int4                            NOT NULL,
    dt_pri_vencto               date                            NOT NULL,
	contrato_id                 int4                                NULL,

	CONSTRAINT pk_contratos_contrato_meio_pagamento             PRIMARY KEY (id),
	CONSTRAINT fk_contratos_contrato_meio_pagamento_contrato    FOREIGN KEY (contrato_id)                       REFERENCES apl_contratos.tab_contrato(id),
	CONSTRAINT ck_contratos_contrato_meio_pagamento_tipo        CHECK (meio_pagto                               in ('A','B','C','D','X','Z'))
);