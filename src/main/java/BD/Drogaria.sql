CREATE DATABASE Drogaria;
USE Drogaria;

-- ===============================
-- Tabela: drogaria
-- ===============================
CREATE TABLE drogaria (
  CNPJ_Drog VARCHAR(18) PRIMARY KEY,
  Nome_Drog VARCHAR(50) NOT NULL,
  Telefone_Drog VARCHAR(15) NOT NULL,
  Cep_Drog VARCHAR(10) NOT NULL,
  Num_Drog INT NOT NULL,
  Email_Drog VARCHAR(50) UNIQUE NOT NULL,
  Ativo_Drog TINYINT(1) NOT NULL DEFAULT 1

);
-- ===============================
-- Tabela: laboratorio
-- ===============================
CREATE TABLE laboratorio (
  CNPJ_Lab VARCHAR(18) PRIMARY KEY,
  Nome_Lab VARCHAR(50) NOT NULL,
  Telefone_Lab VARCHAR(15) NOT NULL,
  Cep_Lab VARCHAR(10) NOT NULL,
  Num_Lab INT UNIQUE NOT NULL,
  Email_Lab VARCHAR(50) UNIQUE NOT NULL,
  Ativo_Lab TINYINT(1) NOT NULL DEFAULT 1

);
-- ===============================
-- Tabela Funcionário
-- ===============================

CREATE TABLE funcionario (
CPF VARCHAR(14) UNIQUE PRIMARY KEY,
Nome_Fun VARCHAR(50) NOT NULL,
Telefone_Fun VARCHAR(15) NOT NULL,
Cep_Fun VARCHAR(10) NOT NULL,
Num_Fun INT NOT NULL,
Email_Fun VARCHAR(50) UNIQUE NOT NULL,
Senha_Fun VARCHAR(255) NOT NULL,
Funcao VARCHAR(50),
Ativo_Fun TINYINT(1) NOT NULL DEFAULT 1

);

-- ===============================
-- Tabela: catalogo_medicamento
-- ===============================
CREATE TABLE catalogo_medicamento (
  Cod_CatMed INT AUTO_INCREMENT PRIMARY KEY,
  Nome_CatMed VARCHAR(50) NOT NULL,
  Desc_CatMed VARCHAR(100) NOT NULL,
  Valor_CatMed DECIMAL(10,2) NOT NULL,
  datacompraItemCat DATE,
  dataValItemCat DATE,
  quantidade INT NOT NULL,
  CNPJ_Lab VARCHAR(18),
  FOREIGN KEY (CNPJ_Lab) REFERENCES laboratorio(CNPJ_Lab)
);

-- ===============================
-- Tabela: medicamento
-- ===============================
CREATE TABLE medicamento (
  Cod_Med INT AUTO_INCREMENT PRIMARY KEY,
  Nome_Med VARCHAR(50) NOT NULL,
  Desc_Med VARCHAR(100) NOT NULL,
  DataVal_Med DATE NOT NULL,
  Qtd_Med INT NOT NULL,
  Valor_Med DECIMAL(10,2) NOT NULL,
  Valor_Compra DECIMAL(10,2) DEFAULT 0,
  Nome_Lab VARCHAR(50),
  Cod_CatMed INT,
  FOREIGN KEY (Cod_CatMed) REFERENCES catalogo_medicamento(Cod_CatMed)
);

-- ===============================
-- Tabela: compra
-- ===============================
CREATE TABLE compra (
  NotaFiscal_Entrada INT AUTO_INCREMENT PRIMARY KEY,
  Valor_Total DECIMAL(10,2),
  Data_Compra DATE DEFAULT (CURRENT_DATE),
  CPF VARCHAR(14),
  CNPJ_Lab VARCHAR(18),
  Ativo_Compra TINYINT(1) NOT NULL DEFAULT 1,
  FOREIGN KEY (CPF) REFERENCES funcionario(CPF),
  FOREIGN KEY (CNPJ_Lab) REFERENCES laboratorio(CNPJ_Lab)
);

-- ===============================
-- Tabela: venda
-- ===============================
CREATE TABLE venda (
  NotaFiscal_Saida INT AUTO_INCREMENT PRIMARY KEY,
  Data_Venda DATE DEFAULT (CURRENT_DATE),
  Valor_Venda DECIMAL(10,2),
  CNPJ_Drog VARCHAR(18),
  CPF VARCHAR(14),
  FOREIGN KEY (CNPJ_Drog) REFERENCES drogaria(CNPJ_Drog),
  FOREIGN KEY (CPF) REFERENCES funcionario(CPF)
);

-- ===============================
-- Tabela: item
-- ===============================
CREATE TABLE item (
  Cod_Item INT AUTO_INCREMENT PRIMARY KEY,
  DataVal_Item DATE NOT NULL,
  Qtd_Item INT NOT NULL,
  Valor_Item DECIMAL(10,2) NOT NULL,
  Data_Venda DATE NOT NULL,
  NotaFiscal_Entrada INT,
  Cod_CatMed INT,
  Cod_Med INT,
  FOREIGN KEY (NotaFiscal_Entrada) REFERENCES compra(NotaFiscal_Entrada),
  FOREIGN KEY (Cod_CatMed) REFERENCES catalogo_medicamento(Cod_CatMed),
  FOREIGN KEY (Cod_Med) REFERENCES medicamento(Cod_Med)
);

-- ===============================
-- Tabela: item_venda
-- ===============================
CREATE TABLE item_venda (
  Cod_ItemVenda INT AUTO_INCREMENT PRIMARY KEY,
  DataVal_ItemVenda DATE NOT NULL,
  Qtd_ItemVenda INT NOT NULL,
  Valor_ItemVenda DECIMAL(10,2) NOT NULL,
  Cod_Med INT,
  NotaFiscal_Saida INT,
  FOREIGN KEY (Cod_Med) REFERENCES medicamento(Cod_Med),
  FOREIGN KEY (NotaFiscal_Saida) REFERENCES venda(NotaFiscal_Saida)
);

-- ===============================
-- INSERTS
-- ===============================

-- 1. Drogaria
INSERT INTO drogaria (CNPJ_Drog, Nome_Drog, Telefone_Drog, Cep_Drog, Num_Drog, Email_Drog) VALUES
('12.345.678/0001-11', 'Drogaria Central', '(11)99888-1111', '01001-000', 101, 'contato@drogariacentral.com'),
('23.456.789/0001-22', 'Drogaria Vida', '(11)97777-2222', '01002-000', 102, 'contato@drogariavida.com'),
('34.567.890/0001-33', 'Drogaria São João', '(21)98888-3333', '20010-000', 103, 'sac@drogariasãojoao.com'),
('45.678.901/0001-44', 'Drogaria Bem Estar', '(31)96666-4444', '30110-000', 104, 'contato@drogariabemestar.com'),
('56.789.012/0001-55', 'Drogaria Popular', '(41)95555-5555', '80010-000', 105, 'vendas@drogariapopular.com'),
('67.890.123/0001-66', 'Drogaria São Paulo', '(51)94444-6666', '90010-000', 106, 'suporte@drogariasaopaulo.com'),
('78.901.234/0001-77', 'Drogaria FarmaMais', '(71)93333-7777', '40010-000', 107, 'atendimento@farmamais.com'),
('89.012.345/0001-88', 'Drogaria Econômica', '(85)92222-8888', '60010-000', 108, 'contato@economica.com'),
('90.123.456/0001-99', 'Drogaria Nacional', '(61)91111-9999', '70010-000', 109, 'sac@drogarianacional.com'),
('11.222.333/0001-00', 'Drogaria Prime', '(19)90000-0000', '13010-000', 110, 'prime@drogariaprime.com');

INSERT INTO laboratorio (CNPJ_Lab, Nome_Lab, Telefone_Lab, Cep_Lab, Num_Lab, Email_Lab) VALUES
('01.111.111/0001-01', 'Pfizer', '(11)90000-0001', '01010-000', 1, 'contato@pfizer.com'),
('02.222.222/0001-02', 'EMS', '(19)90000-0002', '13020-000', 2, 'contato@ems.com'),
('03.333.333/0001-03', 'Eurofarma', '(11)90000-0003', '04567-000', 3, 'vendas@eurofarma.com'),
('04.444.444/0001-04', 'Aché', '(11)90000-0004', '04710-000', 4, 'sac@ache.com'),
('05.555.555/0001-05', 'Biolab', '(11)90000-0005', '04610-000', 5, 'atendimento@biolab.com'),
('06.666.666/0001-06', 'Neo Química', '(11)90000-0006', '03510-000', 6, 'info@neoquimica.com'),
('07.777.777/0001-07', 'Sanofi', '(11)90000-0007', '04010-000', 7, 'contato@sanofi.com'),
('08.888.888/0001-08', 'Roche', '(11)90000-0008', '05010-000', 8, 'roche@contato.com'),
('09.999.999/0001-09', 'Bayer', '(11)90000-0009', '06010-000', 9, 'bayer@br.com'),
('10.000.000/0001-10', 'Medley', '(11)90000-0010', '07010-000', 10, 'medley@farmaco.com');

INSERT INTO catalogo_medicamento (Nome_CatMed, Desc_CatMed, Valor_CatMed, CNPJ_Lab, datacompraItemCat, dataValItemCat, quantidade) VALUES
('Paracetamol', 'Analgésico e antipirético', 12.50, '01.111.111/0001-01', '2024-01-15', '2026-01-15', 100),
('Ibuprofeno', 'Anti-inflamatório não esteroide', 18.90, '02.222.222/0001-02', '2024-02-20', '2026-02-20', 150),
('Amoxicilina', 'Antibiótico de amplo espectro', 32.00, '03.333.333/0001-03', '2024-03-10', '2025-03-10', 80),
('Loratadina', 'Antialérgico', 15.75, '04.444.444/0001-04', '2024-01-25', '2026-01-25', 200),
('Omeprazol', 'Inibidor da bomba de prótons', 25.00, '05.555.555/0001-05', '2024-04-05', '2026-04-05', 120),
('Dipirona', 'Analgésico e antitérmico', 10.00, '06.666.666/0001-06', '2024-02-15', '2026-02-15', 250),
('Cetoprofeno', 'Anti-inflamatório', 19.50, '07.777.777/0001-07', '2024-03-20', '2025-09-20', 90),
('Sinvastatina', 'Reduz colesterol', 28.40, '08.888.888/0001-08', '2024-01-30', '2026-01-30', 110),
('Azitromicina', 'Antibiótico', 35.00, '09.999.999/0001-09', '2024-04-10', '2025-04-10', 70),
('Losartana', 'Antihipertensivo', 22.90, '10.000.000/0001-10', '2024-02-28', '2026-02-28', 180);

INSERT INTO medicamento (Nome_Med, Desc_Med, DataVal_Med, Qtd_Med, Valor_Med, Valor_Compra, Nome_Lab, Cod_CatMed) VALUES
('Paracetamol 500mg', 'Comprimidos 500mg', '2026-05-20', 150, 12.50, 8.00, 'Pfizer', 1),
('Ibuprofeno 600mg', 'Comprimidos 600mg', '2025-12-10', 200, 18.90, 10.00, 'EMS', 2),
('Amoxicilina 500mg', 'Cápsulas 500mg', '2026-02-15', 120, 32.00, 20.00, 'Eurofarma', 3),
('Loratadina 10mg', 'Comprimidos 10mg', '2026-01-10', 180, 15.75, 8.00, 'Aché', 4),
('Omeprazol 20mg', 'Cápsulas 20mg', '2027-04-20', 250, 25.00, 12.00, 'Biolab', 5),
('Dipirona 1g', 'Comprimidos 1g', '2025-11-10', 300, 10.00, 5.00, 'Neo Química', 6),
('Cetoprofeno 100mg', 'Cápsulas 100mg', '2026-03-12', 150, 19.50, 10.00, 'Sanofi', 7),
('Sinvastatina 20mg', 'Comprimidos 20mg', '2027-07-30', 100, 28.40, 15.00, 'Roche', 8),
('Azitromicina 500mg', 'Cápsulas 500mg', '2026-12-01', 90, 35.00, 20.00, 'Bayer', 9),
('Losartana 50mg', 'Comprimidos 50mg', '2027-09-05', 180, 22.90, 12.00, 'Medley', 10);

CREATE OR REPLACE VIEW vw_relatorio_vendas_detalhadas AS
SELECT
    V.NotaFiscal_Saida,
    V.Data_Venda AS Data_Venda,
    V.Valor_Venda AS Valor_Total_Venda,
    M.Nome_Med AS Medicamento_Vendido,
    M.Valor_Med AS Valor_Base_Medicamento,
    IV.Qtd_ItemVenda AS Quantidade,
    IV.Valor_ItemVenda AS Valor_Unitario_Venda,
    F.Nome_Fun AS Funcionario_Venda,
    D.Nome_Drog AS Drogaria_Venda
FROM
    venda V
JOIN
    item_venda IV ON V.NotaFiscal_Saida = IV.NotaFiscal_Saida
JOIN
    medicamento M ON IV.Cod_Med = M.Cod_Med
JOIN
    funcionario F ON V.CPF = F.CPF
JOIN
    drogaria D ON V.CNPJ_Drog = D.CNPJ_Drog;

DELIMITER $$

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_atualiza_estoque_apos_compra $$
CREATE PROCEDURE sp_atualiza_estoque_apos_compra (
    IN p_Cod_Med INT,
    IN p_Qtd_Comprada INT
)
BEGIN
    DECLARE v_estoque_atual INT;

    -- 1. Busca o estoque atual do medicamento
    SELECT Qtd_Med INTO v_estoque_atual
    FROM medicamento
    WHERE Cod_Med = p_Cod_Med;

    -- 2. Atualiza o estoque com a nova quantidade
    UPDATE medicamento
    SET Qtd_Med = v_estoque_atual + p_Qtd_Comprada
    WHERE Cod_Med = p_Cod_Med;

    -- 3. Retorna uma mensagem de confirmação
    SELECT CONCAT(
        'Estoque atualizado com sucesso! (Cod_Med: ', p_Cod_Med, ') ',
        'Quantidade adicionada: ', p_Qtd_Comprada,
        '. Novo total: ', v_estoque_atual + p_Qtd_Comprada
    ) AS Mensagem_Estoque;
END $$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS trg_baixa_estoque_apos_venda $$
CREATE TRIGGER trg_baixa_estoque_apos_venda
AFTER INSERT ON item_venda
FOR EACH ROW
BEGIN
    DECLARE v_cod_med INT;
    DECLARE v_qtd_vendida INT;

    -- 1️⃣ Pega o código e quantidade vendidos diretamente do novo registro
    SET v_cod_med = NEW.Cod_Med;
    SET v_qtd_vendida = NEW.Qtd_ItemVenda;

    -- 2️⃣ Atualiza o estoque do medicamento
    IF v_cod_med IS NOT NULL THEN
        UPDATE medicamento
        SET Qtd_Med = Qtd_Med - v_qtd_vendida
        WHERE Cod_Med = v_cod_med;
    END IF;
END $$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS trg_cancela_compra_reverte_estoque $$
CREATE TRIGGER trg_cancela_compra_reverte_estoque
AFTER UPDATE ON compra
FOR EACH ROW
BEGIN
    IF OLD.Ativo_Compra = 1 AND NEW.Ativo_Compra = 0 THEN
        UPDATE medicamento m
        JOIN item i ON m.Cod_Med = i.Cod_Med
        SET m.Qtd_Med = m.Qtd_Med - i.Qtd_Item
        WHERE i.NotaFiscal_Entrada = NEW.NotaFiscal_Entrada;
    END IF;
END $$

DELIMITER ;
