# PROMPT 2 — PROJETO JAVA DESKTOP
## Responsável: Integrante 2
## Projeto: NexusDev Desktop — https://github.com/Lufelip3/NexusDev

---

## CONTEXTO

Este prompt trata exclusivamente das alterações no projeto Java desktop do TCC NexusDev.
O projeto usa Java Swing + JDBC + DAO + padrão MVC, desenvolvido no NetBeans com arquivos `.form`.

**IMPORTANTE — Dependência com o banco:**
Este prompt pressupõe que o **Integrante 1 já gerou o banco unificado `nexusdev`**.
Antes de implementar qualquer item aqui, consulte o documento de impactos gerado
pelo Integrante 1 para saber:
- Quais campos de tabela foram adicionados ou removidos
- Quais métodos DAO devem ser removidos por terem sido substituídos por triggers
- Como atualizar a string de conexão para o banco `nexusdev`

---

## VERIFICAÇÃO CRUZADA — PARIDADE COM O PROJETO WEB

O projeto web (PHP) e o projeto Java devem ter **paridade funcional** — as mesmas
funcionalidades devem existir nas duas versões. Antes de implementar cada item,
verificar se o equivalente já existe no projeto web e, se sim, replicar o mesmo
comportamento no Java (e vice-versa).

Lista de verificações a fazer **antes de começar**:

| Funcionalidade | Existe no Java? | Existe no Web? | Ação |
|---|---|---|---|
| Filtro por laboratório na listagem de catálogo | Verificar | Sim (nova compra) | Implementar no Java |
| Botão "Limpar Campos" nos formulários | Sim (só Funcionário) | Verificar no web | Padronizar em ambos |
| Validação de CNPJ com formato completo | Verificar | Verificar | Implementar em ambos |
| Validação de CPF com formato completo | Verificar | Verificar | Implementar em ambos |
| Validação de valor/quantidade > 0 | Verificar | Verificar | Implementar em ambos |
| Validação de quantidade na Venda | Verificar | Verificar | Implementar em ambos |
| Validação de quantidade na Compra | Verificar | Verificar | Implementar em ambos |
| Ver medicamentos inativos/excluídos | Existe em Lab e Drog | Verificar no web | Replicar em Medicamentos |
| Inativar medicamento (soft delete) | Verificar | Verificar | Implementar se ausente |

Ao final deste prompt, reportar o resultado dessas verificações.

---

## TAREFA 1 — ATUALIZAR CONEXÃO COM O BANCO UNIFICADO

Atualizar a classe de conexão do projeto Java para apontar para o banco `nexusdev`.

Arquivo provável: `src/BD/Conexao.java` ou equivalente.

Alterar:
```java
// DE:
String url = "jdbc:mysql://localhost:3306/Drogaria";
// PARA:
String url = "jdbc:mysql://localhost:3306/nexusdev";
```

Após a troca, verificar se todas as queries dos DAOs ainda funcionam com
os novos nomes de colunas e tabelas do banco unificado.
Consulte o documento de impactos gerado pelo Integrante 1.

---

## TAREFA 2 — AUDITORIA E LIMPEZA DE CÓDIGO DUPLICADO COM TRIGGERS

**Esta tarefa deve ser feita antes de qualquer outra coisa.**

O banco unificado possui triggers que controlam automaticamente o estoque.
O projeto Java pode conter código que faz as mesmas operações manualmente,
causando dupla execução.

### 2.1 — Verificar e remover código duplicado

Buscar nas classes DAO e nas Janelas qualquer operação manual de
atualização de estoque que agora é responsabilidade das triggers:

**Trigger `trg_finalizar_venda`** (decrementa `Qtd_Med` ao finalizar venda):
- Procurar: `UPDATE medicamento SET Qtd_Med = Qtd_Med - ?`
- Ou: chamadas a métodos como `baixarEstoque()`, `subtrairQuantidade()`,
  `atualizarEstoqueVenda()` etc.
- Se encontrado: **REMOVER** o código Java que faz isso manualmente

**Trigger `trg_finalizar_compra`** (decrementa `catalogo_medicamento.quantidade`):
- Procurar: `UPDATE catalogo_medicamento SET quantidade = quantidade - ?`
- Se encontrado: **REMOVER**

**Trigger `trg_cancelar_venda_finalizada`** (reverte estoque ao excluir venda):
- Procurar: lógica de reversão de `Qtd_Med` ao excluir ou cancelar uma venda
- Se encontrado: **REMOVER**

**Trigger `trg_cancela_compra_reverte_estoque`** (reverte estoque ao cancelar compra):
- Procurar: lógica de reversão ao inativar/cancelar compra via `Ativo_Compra = 0`
- Se encontrado: **REMOVER**

### 2.2 — Verificar uso da stored procedure

Verificar se o Java chama `sp_atualiza_estoque_apos_compra` em algum DAO.
Se sim, avaliar se ainda deve ser chamada ou se agora é redundante com as triggers.
Documentar a decisão tomada.

---

## TAREFA 3 — SEPARAÇÃO DA JANELA DE MEDICAMENTOS EM DUAS

### 3.1 — JANELA A: "Medicamentos Internos" (somente visualização e gestão)

Criar uma **nova janela** para exibir e gerenciar o estoque de medicamentos.
Esta janela **não tem formulário de cadastro** — o cadastro ocorre automaticamente
ao finalizar uma compra.

**Modelo de referência:** seguir o padrão visual e funcional das janelas de
Laboratório e Drogaria já existentes no projeto para Ver Excluídos e Inativar.

**Componentes da nova janela:**
- JTable exibindo medicamentos com `Ativo_Med = 1`
- Colunas: Código, Nome, Descrição, Data de Validade, Quantidade, Valor
- Botão **"Inativar"** — executa `UPDATE medicamento SET Ativo_Med = 0 WHERE Cod_Med = ?`
  (soft delete, igual ao padrão de Lab e Drog)
- Botão **"Ver Excluídos"** — abre sub-janela ou altera a JTable para exibir
  registros com `Ativo_Med = 0`, com botão "Reativar" que faz
  `UPDATE medicamento SET Ativo_Med = 1 WHERE Cod_Med = ?`
- Botão **"Voltar"**
- **NÃO deve ter:** campos de formulário, botão Cadastrar, botão Alterar,
  botão Limpar Campos

**Verificação cruzada:** Verificar se o módulo de medicamentos do projeto web
(`Medicamento/index.php`) possui as mesmas funcionalidades de inativar e
ver inativos. Se não tiver, reportar para o Integrante 3 implementar.

---

### 3.2 — JANELA B: "Catálogo de Laboratório" (transformação da janela atual)

Transformar a janela atual de medicamento no cadastro da tabela `catalogo_medicamento`.

**IMPORTANTE:** A tabela `catalogo_medicamento` no banco unificado **não tem**
os campos `quantidade` nem `dataValItemCat` (removidos pelo Integrante 1).
Certifique-se de consultar o banco unificado antes de construir o formulário.

**Campos do formulário:**
- EAN (JTextField — 13 dígitos numéricos, validado)
- Nome (JTextField)
- Descrição (JTextField)
- Valor unitário (JTextField — decimal, maior que zero)
- Laboratório (JComboBox — carrega `Nome_Lab` dos laboratórios com `Ativo_Lab = 1`)
- **NÃO incluir:** campo Quantidade, campo Data de Validade

**JComboBox de filtro por laboratório (NOVO):**
Adicionar um JComboBox acima da JTable de listagem do catálogo.
- Opções: "Todos" + os nomes dos laboratórios ativos carregados do banco
- Ao selecionar um laboratório, a JTable exibe apenas os itens daquele laboratório
- Ao selecionar "Todos", exibe todos os itens do catálogo
- Implementar a mesma lógica de filtro já existente na janela
  "Nova Compra" do projeto — reutilizar o padrão

**Validação de EAN duplicado:**
Antes de executar o INSERT no catálogo, verificar se o EAN já existe.
Usar a stored procedure `sp_verifica_ean_catalogo` criada pelo Integrante 1:
```java
// Chamar: CALL sp_verifica_ean_catalogo(?, ?)
// Se resultado = 1: JOptionPane com "Erro: Este EAN já está cadastrado no catálogo."
// Se resultado = 0: prosseguir com o INSERT
```

**Botões:** Cadastrar, Alterar, Inativar, Ver Excluídos, **Limpar Campos**, Voltar

**Verificação cruzada:** Verificar se o módulo de catálogo do projeto web possui:
- Filtro por laboratório (se não tiver, reportar para o Integrante 3)
- Formulário sem campo de quantidade (corrigir se necessário)
- Validação de EAN duplicado via procedure (o Integrante 3 deve implementar igual)

---

## TAREFA 4 — BOTÃO "LIMPAR CAMPOS" EM TODAS AS JANELAS DE CADASTRO

Adicionar o botão **"Limpar Campos"** em todas as janelas com formulário,
seguindo o padrão já existente na tela de Funcionário.

**Janelas a verificar e adicionar se necessário:**
- Cadastro de Laboratório
- Cadastro de Drogaria
- Cadastro de Catálogo de Medicamento (Janela B — já incluso acima)
- Qualquer outra janela com formulário que ainda não tenha o botão

**Comportamento padrão do botão:**
```java
private void btnLimparActionPerformed(ActionEvent evt) {
    // Limpar todos os JTextField
    txtNome.setText("");
    txtDescricao.setText("");
    // ... todos os outros campos ...

    // Redefinir JComboBox para o primeiro item
    cmbLaboratorio.setSelectedIndex(0);

    // Desfazer seleção na JTable
    tabelaDados.clearSelection();
}
```

**Verificação cruzada:** Verificar se o projeto web possui botão ou lógica
equivalente de limpar formulário (ex: botão "Cancelar" que redireciona
para o formulário vazio, ou botão "Limpar" em JavaScript).
Se não tiver, reportar para o Integrante 3.

---

## TAREFA 5 — VALIDAÇÕES COMPLETAS DE FORMULÁRIO

Aplicar validações em **todos os campos de todos os formulários** antes de
executar INSERT ou UPDATE no banco. Para cada erro, exibir:
```java
JOptionPane.showMessageDialog(this,
    "Mensagem de erro específica",
    "Erro de Validação",
    JOptionPane.ERROR_MESSAGE);
return; // interromper a operação
```

### 5.1 — Campos numéricos (Quantidade, Valor, Número de endereço)

```java
// Padrão de validação para campos numéricos:
try {
    double valor = Double.parseDouble(txtValor.getText().trim());
    if (valor <= 0) {
        JOptionPane.showMessageDialog(this,
            "Erro: O campo Valor deve ser maior que zero.",
            "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        return;
    }
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this,
        "Erro: O campo Valor deve conter apenas números.",
        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
}
```

### 5.2 — Campo CNPJ

```java
// Formato obrigatório: XX.XXX.XXX/XXXX-XX (18 caracteres)
String cnpj = txtCNPJ.getText().trim();
if (!cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
    JOptionPane.showMessageDialog(this,
        "Erro: CNPJ inválido. Preencha no formato XX.XXX.XXX/XXXX-XX.",
        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
}
```

### 5.3 — Campo CPF

```java
// Formato obrigatório: XXX.XXX.XXX-XX (14 caracteres)
String cpf = txtCPF.getText().trim();
if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
    JOptionPane.showMessageDialog(this,
        "Erro: CPF inválido. Preencha no formato XXX.XXX.XXX-XX.",
        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
}
```

### 5.4 — Campo EAN/GTIN

```java
String ean = txtEAN.getText().trim();
if (!ean.matches("\\d{13}")) {
    JOptionPane.showMessageDialog(this,
        "Erro: EAN inválido. Deve conter exatamente 13 dígitos numéricos.",
        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
}
```

### 5.5 — Campos de texto obrigatórios

```java
if (txtNome.getText().trim().isEmpty()) {
    JOptionPane.showMessageDialog(this,
        "Erro: O campo Nome é obrigatório.",
        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
}
```

### 5.6 — Campo E-mail

```java
String email = txtEmail.getText().trim();
if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
    JOptionPane.showMessageDialog(this,
        "Erro: E-mail inválido.",
        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
}
```

### 5.7 — Campo de Data

```java
// Usar SimpleDateFormat para validar e verificar se não é passada:
try {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    sdf.setLenient(false);
    Date dataVal = sdf.parse(txtDataValidade.getText().trim());
    if (dataVal.before(new Date())) {
        JOptionPane.showMessageDialog(this,
            "Erro: A data de validade não pode ser anterior à data atual.",
            "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        return;
    }
} catch (ParseException e) {
    JOptionPane.showMessageDialog(this,
        "Erro: Data inválida. Use o formato DD/MM/AAAA.",
        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
}
```

### 5.8 — Compra: item sem quantidade ou quantidade inválida

Localizar na janela de "Nova Compra" o evento do botão que adiciona um
item ao carrinho e aplicar:
```java
// Antes de adicionar o item:
try {
    int qtd = Integer.parseInt(txtQuantidade.getText().trim());
    if (qtd <= 0) {
        JOptionPane.showMessageDialog(this,
            "Erro: Informe uma quantidade válida (maior que zero) antes de adicionar o item.",
            "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        return;
    }
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this,
        "Erro: A quantidade deve ser um número inteiro maior que zero.",
        "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    return;
}
```

### 5.9 — Venda: item sem quantidade ou quantidade inválida

Aplicar a **mesma validação da Compra** na janela de "Nova Venda",
no evento do botão que adiciona um medicamento ao carrinho.
Mensagem: `"Erro: Informe uma quantidade válida (maior que zero) antes de adicionar o item à venda."`

**Verificação cruzada:** Confirmar com o Integrante 3 se o projeto web
possui essas mesmas validações (5.8 e 5.9) nas telas de compra e venda.
Se não tiver, reportar para implementação no web.

---

## TAREFA 6 — RELATÓRIO FINAL DE VERIFICAÇÃO CRUZADA

Ao concluir todas as tarefas, produzir um relatório respondendo:

1. Havia código Java duplicando lógica de triggers? Quais métodos foram removidos?
2. Quais funcionalidades existem no Java mas estão ausentes no Web?
   (Reportar para o Integrante 3)
3. Quais funcionalidades existem no Web mas estavam ausentes no Java?
   (Registrar o que foi implementado)
4. Alguma query Java precisou ser atualizada por mudança de coluna no banco unificado?
5. A stored procedure de EAN foi chamada corretamente em todos os pontos de INSERT?

---

## FORMATO DE ENTREGA ESPERADO

Para cada tarefa, fornecer:
1. **Nome exato do arquivo** `.java` ou `.form` modificado
2. **Nome do método** ou evento alterado
3. **Código completo** da solução
4. Para remoções: indicar o trecho removido e o motivo
5. Relatório final de verificação cruzada (Tarefa 6)
