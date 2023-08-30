INSERT INTO apl_acessos.tab_organizacao (cpf_cnpj, nome) VALUES('39198282000119', 'EMPRESA 01');
INSERT INTO apl_acessos.tab_organizacao (cpf_cnpj, nome) VALUES('09199995000136', 'EMPRESA 02');
INSERT INTO apl_acessos.tab_organizacao (cpf_cnpj, nome) VALUES('56276839027', 'DIGYTAL');

INSERT INTO apl_acessos.tab_empresa (cpf_cnpj, nome_fantasia, sobrenome_social, dt_aniversario, rg_ie, email, end_cep, end_logradouro, end_numero, end_complemento, end_referencia, end_telefone, end_bairro, end_estado, end_cidade, end_uf, end_ibge, tel_celular, tel_celular_whatsapp, tel_fixo, organizacao_id, ativ_com_prof, is_incompleto) VALUES('39198282000119', 'EMPRESA 01', 'EMPRESA 01', '2023-08-16', '', 'gleyson@digytal.com.br', '64005453', 'RUA ALFERES DEODATO COSTA VELOSO', '123', 'CENTRO', 'AO LADO DA PRF', NULL, 'SAO JOAQUIM', 'PIAUI', 'TERESINA', 'PI', 2211001, 11958940362, true, NULL, 1, 'VENDA DE ACESSORIOS PARA CELULAR', false);
INSERT INTO apl_acessos.tab_empresa (cpf_cnpj, nome_fantasia, sobrenome_social, dt_aniversario, rg_ie, email, end_cep, end_logradouro, end_numero, end_complemento, end_referencia, end_telefone, end_bairro, end_estado, end_cidade, end_uf, end_ibge, tel_celular, tel_celular_whatsapp, tel_fixo, organizacao_id, ativ_com_prof, is_incompleto) VALUES('09199995000136', 'EMPRESA 02', 'EMPRESA 02', '2023-08-16', '', 'gleyson@digytal.com.br', '64005453', 'RUA ALFERES DEODATO COSTA VELOSO', '123', 'CENTRO', 'AO LADO DA PRF', NULL, 'SAO JOAQUIM', 'PIAUI', 'TERESINA', 'PI', 2211001, 11958940362, true, NULL, 2, 'VENDA DE MATERIAIS ELETRICOS E DE CONSTRUÇÃO', false);
INSERT INTO apl_acessos.tab_empresa (cpf_cnpj, nome_fantasia, sobrenome_social, dt_aniversario, rg_ie, email, end_cep, end_logradouro, end_numero, end_complemento, end_referencia, end_telefone, end_bairro, end_estado, end_cidade, end_uf, end_ibge, tel_celular, tel_celular_whatsapp, tel_fixo, organizacao_id, ativ_com_prof, is_incompleto) VALUES('56276839027', 'DIGYTAL', 'DIGYTAL SOLUCOES', '2023-08-16', '', 'gleyson@digytal.com.br', '64005453', 'RUA ALFERES DEODATO COSTA VELOSO', '123', 'CENTRO', 'AO LADO DA PRF', NULL, 'SAO JOAQUIM', 'PIAUI', 'TERESINA', 'PI', 2211001, 11958940362, true, NULL, 2, 'CONSULTORIA EM DESENVOLVIMENTO DE SOFTWARES', false);

INSERT INTO apl_acessos.tab_usuario (documento, email, is_expirado, is_bloqueado, is_consultor, login, nome, sobrenome, senha, cadastro_id) VALUES('36112890000170', 'gleyson@digytal.com.br', false, false, false, '36112890000170', 'EMPRESA 01', 'EMPRESA 01', '$2a$10$.zSverlZRBRGvFGE4Srj8eobQSafB0UKlvAnSa1f3zqjohAtWML92', NULL);
INSERT INTO apl_acessos.tab_usuario (documento, email, is_expirado, is_bloqueado, is_consultor, login, nome, sobrenome, senha, cadastro_id) VALUES('09199995000136', 'gleyson@digytal.com.br', false, false, false, '09199995000136', 'EMPRESA 02', 'EMPRESA 02', '$2a$10$.zSverlZRBRGvFGE4Srj8eobQSafB0UKlvAnSa1f3zqjohAtWML92', NULL);
INSERT INTO apl_acessos.tab_usuario (documento, email, is_expirado, is_bloqueado, is_consultor, login, nome, sobrenome, senha, cadastro_id) VALUES('56276839027', 'glysns@hotmail.com', false, false, true, '56276839027', 'GLEYSON', 'GLEYSON', '$2a$10$.zSverlZRBRGvFGE4Srj8eobQSafB0UKlvAnSa1f3zqjohAtWML92', NULL);
INSERT INTO apl_acessos.tab_usuario (documento, email, is_expirado, is_bloqueado, is_consultor, login, nome, sobrenome, senha, cadastro_id) VALUES('20656021047', 'mesquitagabr@gmail.com', false, false, true, '20656021047', 'BIANCA', 'BIANCA', '$2a$10$.zSverlZRBRGvFGE4Srj8eobQSafB0UKlvAnSa1f3zqjohAtWML92', NULL);

INSERT INTO apl_acessos.tab_usuario_empresa (usuario_id, empresa_id, is_padrao) VALUES(2, 1, true);
INSERT INTO apl_acessos.tab_usuario_empresa (usuario_id, empresa_id, is_padrao) VALUES(3, 2, true);
INSERT INTO apl_acessos.tab_usuario_empresa (usuario_id, empresa_id, is_padrao) VALUES(4, 1, true);
INSERT INTO apl_acessos.tab_usuario_empresa (usuario_id, empresa_id, is_padrao) VALUES(4, 2, false);
INSERT INTO apl_acessos.tab_usuario_empresa (usuario_id, empresa_id, is_padrao) VALUES(4, 3, false);
INSERT INTO apl_acessos.tab_usuario_empresa (usuario_id, empresa_id, is_padrao) VALUES(5, 1, true);
INSERT INTO apl_acessos.tab_usuario_empresa (usuario_id, empresa_id, is_padrao) VALUES(5, 2, false);

INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX191', 'CCX191', 0.00, 'CONTA BALCAO (caixa empresa)',  '', false, false, 9999, 1, NULL, NULL,'Representação da conta caixa, balcão para recebimento e pagamento via dinheiro no estabelecimento');
INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX192', 'CCX192', 0.00, 'CONTA DEB\PIX\BOL', '11958940362', true, false, 1, 1, NULL, NULL,'Representação de uma conta bancária para recebimento e pagamento depósito, pix ou compensação de boleto em conta');
INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX193', 'CCX193', 0.00, 'CONTA CREDITO',  'gleyson-juridica@hotmail.com', false, false, 1, 1, NULL, NULL,'Representação de uma conta para recebimento de parcelas pagamentos via cartão de crédito');

INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX361', 'CCX361', 0.00, 'CONTA BALCAO (caixa empresa)',  '', false, false, 9999, 2, NULL, NULL,'Representação da conta caixa, balcão para recebimento e pagamento via dinheiro no estabelecimento');
INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX362', 'CCX362', 0.00, 'CONTA DEB\PIX\BOL', '11958940362', true, false, 1, 2, NULL, NULL,'Representação de uma conta bancária para recebimento e pagamento depósito, pix ou compensação de boleto em conta');
INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX363', 'CCX363', 0.00, 'CONTA CREDITO',  'gleyson-juridica@hotmail.com', false, false, 1, 2, NULL, NULL,'Representação de uma conta para recebimento de parcelas pagamentos via cartão de crédito');

INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX271', 'CCX271', 0.00, 'CONTA BALCAO (caixa empresa)',  '', false, false, 9999, 3, NULL, NULL,'Representação da conta caixa, balcão para recebimento e pagamento via dinheiro no estabelecimento');
INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX272', 'CCX272', 0.00, 'CONTA DEB\PIX\BOL', '11958940362', true, false, 1, 3, NULL, NULL,'Representação de uma conta bancária para recebimento e pagamento depósito, pix ou compensação de boleto em conta');
INSERT INTO apl_acessos.tab_empresa_conta (agencia, numero, saldo, legenda, chave_pix, is_conta_padrao, is_conta_credito, banco_id, empresa_id, dia_vencimento, dias_intervalo, descricao) VALUES('AGX273', 'CCX273', 0.00, 'CONTA CREDITO',  'gleyson-juridica@hotmail.com', false, false, 1, 3, NULL, NULL,'Representação de uma conta para recebimento de parcelas pagamentos via cartão de crédito');


INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(1, 1, 'A', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(1, 1, 'Z', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(1, 2, 'X', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(1, 2, 'B', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(1, 2, 'D', 0.50);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(1, 3, 'C', 1.50);

INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(2, 4, 'A', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(2, 4, 'Z', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(2, 5, 'X', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(2, 5, 'B', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(2, 5, 'D', 0.50);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(2, 6, 'C', 1.50);

INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(3, 7, 'A', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(3, 7, 'Z', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(3, 8, 'X', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(3, 8, 'B', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(3, 8, 'D', 0.00);
INSERT INTO apl_acessos.tab_empresa_conta_meio_pagto (empresa_id, empresa_conta_id, meio_pagto, taxa) VALUES(3, 9, 'C', 0.00);

