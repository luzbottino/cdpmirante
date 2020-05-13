insert into OPERADOR (nome, login, senha, perfil, administrador, data_cadastro) values('Administrador', 'admin', '123456', 1, true, now());
insert into OPERADOR (nome, login, senha, perfil, administrador, data_cadastro) values('Maria de Castro', 'mcastro', '123456', 0, false, now());

insert into PESSOA (nome, documento, DATA_NASCIMENTO, NOME_MAE, NOME_PAI, DATA_CADASTRO, LOGIN_OPERADOR, TIPO) values('Henrique Albuquerque', '04715587435', DATE'2003-07-09', 'Antonia Silva', 'Henrique Chaves', now(), 'admin', 1);

insert into TELEFONE (ddd, NUMERO, DATA_CADASTRO, LOGIN_OPERADOR, TIPO, ID_PESSOA) values('061', '981913485', now(), 'admin', 0, 1);
insert into TELEFONE (ddd, NUMERO, DATA_CADASTRO, LOGIN_OPERADOR, TIPO, ID_PESSOA) values('061', '981913854', now(), 'admin', 0, 1);
