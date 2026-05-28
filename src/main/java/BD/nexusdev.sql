-- 1. DROP DATABASE IF EXISTS nexusdev;
DROP DATABASE IF EXISTS nexusdev;

-- 2. CREATE DATABASE nexusdev;
CREATE DATABASE IF NOT EXISTS nexusdev;

-- 3. USE nexusdev;
USE nexusdev;

-- 4. CREATE TABLE drogaria
CREATE TABLE IF NOT EXISTS drogaria (
  CNPJ_Drog VARCHAR(18) PRIMARY KEY,
  Nome_Drog VARCHAR(50) NOT NULL,
  Telefone_Drog VARCHAR(15) NOT NULL,
  Cep_Drog VARCHAR(10) NOT NULL,
  Num_Drog INT NOT NULL,
  Email_Drog VARCHAR(50) UNIQUE NOT NULL,
  Ativo_Drog TINYINT(1) NOT NULL DEFAULT 1,
  Foto_Drog VARCHAR(255) NULL
);

-- 5. CREATE TABLE laboratorio
CREATE TABLE IF NOT EXISTS laboratorio (
  CNPJ_Lab VARCHAR(18) PRIMARY KEY,
  Nome_Lab VARCHAR(50) NOT NULL,
  Telefone_Lab VARCHAR(15) NOT NULL,
  Cep_Lab VARCHAR(10) NOT NULL,
  Num_Lab INT UNIQUE NOT NULL,
  Email_Lab VARCHAR(50) UNIQUE NOT NULL,
  Ativo_Lab TINYINT(1) NOT NULL DEFAULT 1,
  Foto_Lab VARCHAR(255) NULL
);

-- 6. CREATE TABLE funcionario
CREATE TABLE IF NOT EXISTS funcionario (
  CPF VARCHAR(14) UNIQUE PRIMARY KEY,
  Nome_Fun VARCHAR(50) NOT NULL,
  Telefone_Fun VARCHAR(15) NOT NULL,
  Cep_Fun VARCHAR(10) NOT NULL,
  Num_Fun INT NOT NULL,
  Email_Fun VARCHAR(50) UNIQUE NOT NULL,
  Senha_Fun VARCHAR(255) NOT NULL,
  Funcao VARCHAR(50),
  Ativo_Fun TINYINT(1) NOT NULL DEFAULT 1,
  imagem VARCHAR(255) NULL
);

-- 7. CREATE TABLE catalogo_medicamento
CREATE TABLE IF NOT EXISTS catalogo_medicamento (
  Cod_CatMed INT AUTO_INCREMENT PRIMARY KEY,
  EAN_Med VARCHAR(13) UNIQUE,
  Nome_CatMed VARCHAR(50) NOT NULL,
  Desc_CatMed VARCHAR(100) NOT NULL,
  Valor_CatMed DECIMAL(10,2) NOT NULL,
  datacompraItemCat DATE,
  dataValItemCat DATE, -- Mantido por retrocompatibilidade
  quantidade INT NOT NULL, -- Mantido por retrocompatibilidade
  CNPJ_Lab VARCHAR(18),
  FOREIGN KEY (CNPJ_Lab) REFERENCES laboratorio(CNPJ_Lab)
);

-- 8. CREATE TABLE medicamento
CREATE TABLE IF NOT EXISTS medicamento (
  Cod_Med INT AUTO_INCREMENT PRIMARY KEY,
  EAN_Med VARCHAR(13) UNIQUE,
  Nome_Med VARCHAR(50) NOT NULL,
  Desc_Med VARCHAR(100) NOT NULL,
  DataVal_Med DATE NOT NULL,
  Qtd_Med INT NOT NULL,
  Valor_Med DECIMAL(10,2) NOT NULL,
  Valor_Compra DECIMAL(10,2) DEFAULT 0,
  Nome_Lab VARCHAR(50),
  Cod_CatMed INT,
  Foto_Med VARCHAR(255) NULL,
  Ativo_Med TINYINT(1) NOT NULL DEFAULT 1,
  FOREIGN KEY (Cod_CatMed) REFERENCES catalogo_medicamento(Cod_CatMed)
);

-- 9. CREATE TABLE compra
CREATE TABLE IF NOT EXISTS compra (
  NotaFiscal_Entrada INT AUTO_INCREMENT PRIMARY KEY,
  Valor_Total DECIMAL(10,2),
  Data_Compra DATE DEFAULT (CURRENT_DATE),
  CPF VARCHAR(14),
  CNPJ_Lab VARCHAR(18),
  Ativo_Compra TINYINT(1) NOT NULL DEFAULT 1,
  Finalizada TINYINT(1) NOT NULL DEFAULT 0,
  FOREIGN KEY (CPF) REFERENCES funcionario(CPF),
  FOREIGN KEY (CNPJ_Lab) REFERENCES laboratorio(CNPJ_Lab)
);

-- 10. CREATE TABLE venda
CREATE TABLE IF NOT EXISTS venda (
  NotaFiscal_Saida INT AUTO_INCREMENT PRIMARY KEY,
  Data_Venda DATE DEFAULT (CURRENT_DATE),
  Valor_Venda DECIMAL(10,2),
  CNPJ_Drog VARCHAR(18),
  CPF VARCHAR(14),
  Finalizada TINYINT(1) NOT NULL DEFAULT 0,
  FOREIGN KEY (CNPJ_Drog) REFERENCES drogaria(CNPJ_Drog),
  FOREIGN KEY (CPF) REFERENCES funcionario(CPF)
);

-- 11. CREATE TABLE item
CREATE TABLE IF NOT EXISTS item (
  Cod_Item INT AUTO_INCREMENT PRIMARY KEY,
  DataVal_Item DATE NOT NULL,
  Qtd_Item INT NOT NULL,
  Valor_Item DECIMAL(10,2) NOT NULL,
  Data_Venda DATE NOT NULL,
  NotaFiscal_Entrada INT,
  Cod_CatMed INT,
  Cod_Med INT,
  FOREIGN KEY (NotaFiscal_Entrada) REFERENCES compra(NotaFiscal_Entrada) ON DELETE CASCADE,
  FOREIGN KEY (Cod_CatMed) REFERENCES catalogo_medicamento(Cod_CatMed),
  FOREIGN KEY (Cod_Med) REFERENCES medicamento(Cod_Med)
);

-- 12. CREATE TABLE item_venda
CREATE TABLE IF NOT EXISTS item_venda (
  Cod_ItemVenda INT AUTO_INCREMENT PRIMARY KEY,
  DataVal_ItemVenda DATE NOT NULL,
  Qtd_ItemVenda INT NOT NULL,
  Valor_ItemVenda DECIMAL(10,2) NOT NULL,
  Cod_Med INT,
  NotaFiscal_Saida INT,
  FOREIGN KEY (Cod_Med) REFERENCES medicamento(Cod_Med),
  FOREIGN KEY (NotaFiscal_Saida) REFERENCES venda(NotaFiscal_Saida) ON DELETE CASCADE
);

-- 13. TRIGGERS
DELIMITER $$

-- trg_finalizar_venda
DROP TRIGGER IF EXISTS trg_finalizar_venda $$
CREATE TRIGGER trg_finalizar_venda
AFTER UPDATE ON venda
FOR EACH ROW
BEGIN
    IF NEW.Finalizada = 1 AND OLD.Finalizada = 0 THEN
        UPDATE medicamento m
        JOIN item_venda iv ON m.Cod_Med = iv.Cod_Med
        SET m.Qtd_Med = m.Qtd_Med - iv.Qtd_ItemVenda
        WHERE iv.NotaFiscal_Saida = NEW.NotaFiscal_Saida;
    END IF;
END $$

-- trg_finalizar_compra
DROP TRIGGER IF EXISTS trg_finalizar_compra $$
CREATE TRIGGER trg_finalizar_compra
AFTER UPDATE ON compra
FOR EACH ROW
BEGIN
    IF NEW.Finalizada = 1 AND OLD.Finalizada = 0 THEN
        UPDATE catalogo_medicamento cm
        JOIN item i ON cm.Cod_CatMed = i.Cod_CatMed
        SET cm.quantidade = cm.quantidade - i.Qtd_Item
        WHERE i.NotaFiscal_Entrada = NEW.NotaFiscal_Entrada;
    END IF;
END $$

-- trg_cancelar_venda_finalizada
DROP TRIGGER IF EXISTS trg_cancelar_venda_finalizada $$
CREATE TRIGGER trg_cancelar_venda_finalizada
BEFORE DELETE ON venda
FOR EACH ROW
BEGIN
    IF OLD.Finalizada = 1 THEN
        UPDATE medicamento m
        JOIN item_venda iv ON m.Cod_Med = iv.Cod_Med
        SET m.Qtd_Med = m.Qtd_Med + iv.Qtd_ItemVenda
        WHERE iv.NotaFiscal_Saida = OLD.NotaFiscal_Saida;
    END IF;
END $$

-- trg_cancela_compra_reverte_estoque
DROP TRIGGER IF EXISTS trg_cancela_compra_reverte_estoque $$
CREATE TRIGGER trg_cancela_compra_reverte_estoque
AFTER UPDATE ON compra
FOR EACH ROW
BEGIN
    IF OLD.Ativo_Compra = 1 AND NEW.Ativo_Compra = 0 AND OLD.Finalizada = 1 THEN
        UPDATE medicamento m
        JOIN item i ON m.Cod_Med = i.Cod_Med
        SET m.Qtd_Med = m.Qtd_Med + i.Qtd_Item
        WHERE i.NotaFiscal_Entrada = NEW.NotaFiscal_Entrada;

        UPDATE catalogo_medicamento cm
        JOIN item i ON cm.Cod_CatMed = i.Cod_CatMed
        SET cm.quantidade = cm.quantidade + i.Qtd_Item
        WHERE i.NotaFiscal_Entrada = NEW.NotaFiscal_Entrada;
    END IF;
END $$

DELIMITER ;

-- 14. STORED PROCEDURES
DELIMITER $$

-- sp_atualiza_estoque_apos_compra
DROP PROCEDURE IF EXISTS sp_atualiza_estoque_apos_compra $$
CREATE PROCEDURE sp_atualiza_estoque_apos_compra (
    IN p_Cod_Med INT,
    IN p_Qtd_Comprada INT
)
BEGIN
    DECLARE v_estoque_atual INT;
    
    -- Busca o estoque atual do medicamento
    SELECT Qtd_Med INTO v_estoque_atual
    FROM medicamento
    WHERE Cod_Med = p_Cod_Med;
    
    -- Atualiza o estoque com a nova quantidade
    UPDATE medicamento
    SET Qtd_Med = v_estoque_atual + p_Qtd_Comprada
    WHERE Cod_Med = p_Cod_Med;
    
    -- Retorna uma mensagem de confirmação
    SELECT CONCAT(
        'Estoque atualizado com sucesso! (Cod_Med: ', p_Cod_Med, ') ',
        'Quantidade adicionada: ', p_Qtd_Comprada,
        '. Novo total: ', v_estoque_atual + p_Qtd_Comprada
    ) AS Mensagem_Estoque;
END $$

-- sp_verifica_ean_catalogo
DROP PROCEDURE IF EXISTS sp_verifica_ean_catalogo $$
CREATE PROCEDURE sp_verifica_ean_catalogo (
    IN p_EAN VARCHAR(13),
    OUT p_resultado TINYINT
)
BEGIN
    DECLARE v_count INT;
    SELECT COUNT(*) INTO v_count FROM catalogo_medicamento WHERE EAN_Med = p_EAN;
    IF v_count > 0 THEN
        SET p_resultado = 1;
    ELSE
        SET p_resultado = 0;
    END IF;
END $$

-- sp_verifica_ean_medicamento
DROP PROCEDURE IF EXISTS sp_verifica_ean_medicamento $$
CREATE PROCEDURE sp_verifica_ean_medicamento (
    IN p_EAN VARCHAR(13),
    OUT p_resultado TINYINT
)
BEGIN
    DECLARE v_count INT;
    SELECT COUNT(*) INTO v_count FROM medicamento WHERE EAN_Med = p_EAN;
    IF v_count > 0 THEN
        SET p_resultado = 1;
    ELSE
        SET p_resultado = 0;
    END IF;
END $$

DELIMITER ;

-- 15. VIEW vw_relatorio_vendas_detalhadas
DROP VIEW IF EXISTS vw_relatorio_vendas_detalhadas;
CREATE VIEW vw_relatorio_vendas_detalhadas AS
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

-- 16. INSERTS DE SEED

INSERT IGNORE INTO funcionario (CPF, Nome_Fun, Telefone_Fun, Cep_Fun, Num_Fun, Email_Fun, Senha_Fun, Funcao, Ativo_Fun, imagem) VALUES
('123456789-10','Nexus ADM','19998796179','13502-030',284,'nexusadm@gmail.com','$2y$10$f9tyQJC2VhXl0GQswvKS6.wweN6W3qlNHZXoF9jpo4iCmyTt4PuAi','Administrador',1,NULL),
('98765432109','Nexus Usario','19998796179','13502-030',284,'nexususuario@gmail.com','$2y$10$Ww64qXEO1CqynfymGx2HYu.M5ZPeoB5VmizcYBJ0azOjoD4RNRXGK','Usuario',1,NULL);

INSERT IGNORE INTO drogaria (CNPJ_Drog, Nome_Drog, Telefone_Drog, Cep_Drog, Num_Drog, Email_Drog) VALUES
('12.345.678/0001-11', 'Drogaria Central', '(11)99888-1111', '01001-000', 101, 'contato@drogariacentral.com'),
('23.456.789/0001-22', 'Drogaria Vida', '(11)97777-2222', '01002-000', 102, 'contato@drogariavida.com');

INSERT IGNORE INTO laboratorio (CNPJ_Lab, Nome_Lab, Telefone_Lab, Cep_Lab, Num_Lab, Email_Lab) VALUES
('01.111.111/0001-01', 'Pfizer', '(11)90000-0001', '01010-000', 1, 'contatopfizer@lab.com'),
('02.222.222/0001-02', 'EMS', '(11)90000-0002', '02010-000', 2, 'contatoems@lab.com'),
('03.333.333/0001-03', 'Eurofarma', '(11)90000-0003', '03010-000', 3, 'contatoeurofarma@lab.com');

INSERT IGNORE INTO catalogo_medicamento (EAN_Med, Nome_CatMed, Desc_CatMed, Valor_CatMed, CNPJ_Lab, datacompraItemCat, dataValItemCat, quantidade) VALUES
('7891000000001', 'Pfizer Spray Forte', 'Antiviral Farmacológico', 59.57, '01.111.111/0001-01', '2024-01-01', '2026-12-31', 120),
('7891000000011', 'EMS Gotas Retard', 'Antitérmico Farmacológico', 52.47, '02.222.222/0001-02', '2024-01-01', '2026-12-31', 785),
('7891000000021', 'Eurofarma Injecao 1g', 'Anti-inflamatório Farmacológico', 92.05, '03.333.333/0001-03', '2024-01-01', '2026-12-31', 766);

INSERT IGNORE INTO medicamento (EAN_Med, Nome_Med, Desc_Med, DataVal_Med, Qtd_Med, Valor_Med, Valor_Compra, Nome_Lab, Cod_CatMed, Foto_Med) VALUES
('7891000000001', 'Pfizer Spray Forte', 'Antiviral Farmacológico', '2026-12-31', 25, 59.57, 30.00, 'Pfizer', 1, NULL),
('7891000000011', 'EMS Gotas Retard', 'Antitérmico Farmacológico', '2026-12-31', 23, 52.47, 25.00, 'EMS', 2, NULL),
('7891000000021', 'Eurofarma Injecao 1g', 'Anti-inflamatório Farmacológico', '2026-12-31', 36, 92.05, 45.00, 'Eurofarma', 3, NULL);
