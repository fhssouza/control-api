--SE PRECISAR SO DELETAR

delete from apl_financeiro.tab_pagamento  ;
delete from apl_acesso.tab_aplicacao  ;
delete from apl_acesso.tab_usuario_empresa ;
delete from apl_acesso.tab_usuario ;
delete from apl_acesso.tab_forma_pagamento  ;
delete from apl_acesso.tab_conta  ;
delete from apl_acesso.tab_empresa  ;
delete from apl_acesso.tab_organizacao  ;

drop schema apl_contrato cascade ;
drop schema apl_finaceiro cascade ;
drop schema apl_cadastro cascade ;
drop schema apl_acesso cascade ;
drop schema apl_param cascade ;
DELETE FROM public.flyway_schema_history;



INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Consultoria contábil', 'R', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Contabilidade escritório', 'R', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Contabilidade online', 'R', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Palestras', 'R', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Assessoria', 'R', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Elboração de IRPF', 'R', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Material escritório', 'D', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Internet fixa', 'D', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Internet móvel', 'D', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Taxi', 'D', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Despachante', 'D', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Impostos', 'D', false, true, false, 1);
INSERT INTO apl_acesso.tab_aplicacao (nome, tipo, is_area, is_natureza, is_principal, organizacao_id) VALUES('Escritorio Central', NULL, true, false, false, 1);

INSERT INTO apl_cadastro.tab_cadastro (cpf_cnpj, nome_fantasia, sobrenome_social, email, dt_aniversario, rg_ie, end_cep, end_logradouro, end_numero, end_complemento, end_referencia, end_telefone, end_bairro, end_estado, end_cidade, end_uf, end_ibge, tel_celular, tel_celular_whatsapp, tel_fixo, is_cliente, is_fornecedor, ativ_com_prof, organizacao_id, is_incompleto, integra_asaas_id) VALUES('86169394072', 'LUCAS ALVES', 'FERREIRA LIMA', 'lucas.alves@gmail.com', '2023-10-01', '', '69945970', 'AVENIDA PARANA', '123', '296', '', NULL, 'CENTRO', 'ACRE', 'ACRELANDIA', 'AC', 1200013, 12312312312, true, NULL, true, false, '', 1, false, NULL);
INSERT INTO apl_cadastro.tab_cadastro (cpf_cnpj, nome_fantasia, sobrenome_social, email, dt_aniversario, rg_ie, end_cep, end_logradouro, end_numero, end_complemento, end_referencia, end_telefone, end_bairro, end_estado, end_cidade, end_uf, end_ibge, tel_celular, tel_celular_whatsapp, tel_fixo, is_cliente, is_fornecedor, ativ_com_prof, organizacao_id, is_incompleto, integra_asaas_id) VALUES('69742756000102', 'VIVO FIXO MOVEL', 'VIVO EMP DE TELECOM LTDA', 'atendimento@vivo.com', '2023-10-01', '3456345', '69935970', 'RUA DOM GIOCONDO MARIA GROTTE', 'W12', '230', '', NULL, 'CENTRO', 'ACRE', 'ASSIS BRASIL', 'AC', 1200054, 90890890890, true, NULL, false, true, '', 1, false, NULL);
