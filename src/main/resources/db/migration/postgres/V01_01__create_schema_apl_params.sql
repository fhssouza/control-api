CREATE SCHEMA apl_params;

CREATE TABLE apl_params.tab_ibge (
	id                          int4                            NOT NULL,
	nome 					    varchar(100) 		            NOT NULL,
	sigla 					    varchar(10) 		            NOT NULL,
	uf_id 					    int4 				            NOT NULL,
	uf_nome 				    varchar(50) 		            NOT NULL,
	uf_sigla 				    varchar(4) 			            NOT NULL,
	prioridade 				    int4 				            NOT NULL,
	nome_oficial 			    varchar(100) 		            NOT NULL,
	CONSTRAINT pk_params_ibge                                   PRIMARY KEY (id)
);

CREATE TABLE apl_params.tab_banco (
	id 						    int4 				            NOT NULL,
	compe 				        int4 				            NOT NULL,
	ispb 				        int4 				            NOT NULL,
	nome 					    varchar(70) 		            NOT NULL,
	apelido 				    varchar(70) 		            NOT NULL,
	prioridade 				    int4 				            NOT NULL,
	CONSTRAINT pk_params_banco                                  PRIMARY KEY (id)
);

CREATE TABLE apl_params.tab_cep (
	cep                         varchar(10)                     NOT NULL,
	logradouro                  varchar(80)                         NULL,
	complemento                 varchar(60)                         NULL,
	bairro                      varchar(80)                         NULL,
	localidade                  varchar(80)                         NULL,
	estado                      varchar(60)                         NULL,
	uf                          char(2)                             NULL,
	ibge                        int4                                NULL,
	is_valido                   boolean                         NOT NULL,
	CONSTRAINT pk_params_cep                                    PRIMARY KEY (cep)
);