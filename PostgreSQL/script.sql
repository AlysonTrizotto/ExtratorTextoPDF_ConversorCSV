/*Criando a tabela para a importação dos dados*/
CREATE TABLE IF NOT EXISTS dadosAns(DATA_ date, 
									REG_ANS integer, 
								   cd_conta_contabil integer,
								   descricao varchar(500), 
								   vl_saldo_final float);


CREATE TABLE IF NOT EXISTS OperadorasAns(REG_ANS integer, 
										CNPJ VARCHAR(20),
										RAZAO_SOCIAL VARCHAR(150),
										NOME_FANTASIA VARCHAR(100),
										MODALIDADE VARCHAR(120),
										LOGRADOURO VARCHAR(150),
										NUMERO VARCHAR(30),
										COMPLEMENTO VARCHAR(150),
										BAIRRO VARCHAR(30),
										CIDADE VARCHAR(35),
										UF CHARACTER(2),
										CEP INTEGER,
										DDD CHARACTER(2),
										TELEFONE CHARACTER(25),
										FAX CHARACTER(30),
										EMAIL VARCHAR(60),
										REPRESENTANTE VARCHAR(100),
										CARGO VARCHAR(80),
										DATA_REG DATE);
										
/*
/* Apagando a tabela*/
DROP TABLE IF EXISTS dadosAns;

DROP TABLE IF EXISTS OperadorasAns;
*/

/*Copiando os dados*/
/*2019*/
copy dadosans FROM 'E:\Projeto Estagio\Planilhas/1T2019.csv'
delimiter ';'  CSV  header encoding 'windows-1251';

copy dadosans FROM 'E:\Projeto Estagio\Planilhas/2T2019.csv' 
delimiter ';'  CSV  header encoding 'windows-1251';

copy dadosans FROM 'E:\Projeto Estagio\Planilhas/3T2019.csv' 
delimiter ';'  CSV  header encoding 'windows-1251';

copy dadosans FROM 'E:\Projeto Estagio\Planilhas/4T2019.csv' 
delimiter ';'  CSV  header encoding 'windows-1251';

/*2020*/
copy dadosans FROM 'E:\Projeto Estagio\Planilhas/1T2020.csv' 
delimiter ';'  CSV  header encoding 'windows-1251';

copy dadosans FROM 'E:\Projeto Estagio\Planilhas/2T2020.csv'
delimiter ';'  CSV  header encoding 'windows-1251';

copy dadosans FROM 'E:\Projeto Estagio\Planilhas/3T2020.csv' 
delimiter ';'  CSV  header encoding 'windows-1251';

copy dadosans FROM 'E:\Projeto Estagio\Planilhas/4T2020.csv' 
delimiter ';'  CSV  header encoding 'windows-1251';

/*2021*/
copy dadosans FROM 'E:\Projeto Estagio\Planilhas/1T2021.csv' 
delimiter ';'  CSV  header encoding 'windows-1251';

copy dadosans FROM 'E:\Projeto Estagio\Planilhas/2T2021.csv' 
delimiter ';'  CSV  header encoding 'windows-1251';

/*oPERADORAS*/
copy OperadorasANS FROM 'E:\Projeto Estagio\Planilhas/Relatorio_cadop (1).csv'
DELIMITER ';' CSV HEADER ENCODING 'windows-1251';

/*Pegando os 10 últimos registros*/
SELECT * FROM public.dadosans Order by DATA_ desc limit 10;

/*Selecinando todos os registros*/
SELECT * FROM public.dadosans;
SELECT * FROM public.OperadorasANS;

insert into OperadorasANS values(306444);

/*SELECT ÚLTIMO TRIMESTRE*/
SELECT SOMA.TOTAL 
FROM (SELECT REG_ANS, SUM(VL_SALDO_FINAL) AS TOTAL
	FROM DADOSANS
	WHERE descricao LIKE 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSIS%' AND
	  DATA_ = '01/04/2021'
	GROUP BY REG_ANS) AS SOMA
ORDER BY SOMA.TOTAL DESC LIMIT 10;

/*SELECT ÚLTIMO ANO*/
SELECT SOMA.TOTAL 
FROM (SELECT REG_ANS, SUM(VL_SALDO_FINAL) AS TOTAL
	FROM DADOSANS
	WHERE descricao  LIKE 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSIS%' AND
	  DATA_ BETWEEN '01/04/2020' AND '01/04/2021'
	GROUP BY REG_ANS) AS SOMA
ORDER BY SOMA.TOTAL DESC LIMIT 10;

/*SELECT C/ NOME_FANTASIA REFERENTE AO ÚLTIMO TRIMESTRE*/
SELECT SOMA.TOTAL, OP.NOME_FANTASIA AS NOME
FROM (SELECT REG_ANS, SUM(VL_SALDO_FINAL) AS TOTAL
	FROM DADOSANS
	WHERE descricao LIKE 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSIS%' AND
	  DATA_ = '01/04/2021'
	GROUP BY REG_ANS) AS SOMA, OperadorasANS AS OP
WHERE SOMA.REG_ANS = OP.REG_ANS
ORDER BY TOTAL DESC LIMIT 10;

/*SELECT COM NOME REFERENTE AO ÚLTIMO ANO*/
SELECT SOMA.TOTAL, OP.NOME_FANTASIA AS NOME
FROM (SELECT REG_ANS, SUM(VL_SALDO_FINAL) AS TOTAL
	FROM DADOSANS
	WHERE descricao LIKE 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSIS%' AND
	  DATA_ BETWEEN '01/04/2020' AND '01/04/2021'
	GROUP BY REG_ANS) AS SOMA, OperadorasANS AS OP
WHERE SOMA.REG_ANS = OP.REG_ANS
ORDER BY TOTAL DESC LIMIT 10;
