/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Janelas;

import DAO.DrogariaDAO;
import DAO.ItensVendaDAO;
import DAO.MedicamentoDAO;
import DAO.VendaDAO;
import Model.ItensVendaTableModel;
import Model.MedicamentoTableModel;
import Objetos.DrogariaObjeto;
import Objetos.Funcionario;
import Objetos.ItensVenda;
import Objetos.Venda;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author luis.fmleite
 */
public class NovaJanelaVenda extends javax.swing.JFrame {

    private Funcionario user;
    private String cpf;
    private List<DrogariaObjeto> drogarias = new ArrayList<>();
    private String cnpjDrogaria;
    private int notaFiscalVenda;
    private double valorTotalVenda = 0.0;
    ItensVendaTableModel modeloItem = new ItensVendaTableModel();
    MedicamentoTableModel modeloMed = new MedicamentoTableModel();
    private Menu menu;

    /**
     * Creates new form NovaJanelaCompra
     */
    public NovaJanelaVenda() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    public NovaJanelaVenda(Funcionario user, int notaGerada, String cpf, String cnpj, Menu menu) {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.user = user;
        carregarDrogarias();
        this.menu = menu;

        jBFinalizarNovaCompra.setEnabled(false);

        this.cpf = user.getCpf();
        this.notaFiscalVenda = notaGerada;
        modeloMed.recarregaTabela();
        jTTabelaNovaCompra.setModel(modeloMed);
        jTTabelaItensCompra.setModel(modeloItem);
        atualizarValorTotal();

        jCDrogarias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarHabilitacaoBotao();
            }
        });
        styleComponents();
    }

    private void verificarHabilitacaoBotao() {
        int index = jCDrogarias.getSelectedIndex();
        boolean temItens = modeloItem.getRowCount() > 0;
        
        if (index <= 0) {
            cnpjDrogaria = null;
            jBFinalizarNovaCompra.setEnabled(false);
        } else {
            // Ajuste do índice: subtrai 1 porque o item 0 é o prompt "-- Selecione --"
            cnpjDrogaria = drogarias.get(index - 1).getCnpjDrogaria();
            jBFinalizarNovaCompra.setEnabled(temItens);
        }
    }
    // Método para calcular o valor total da compra

    private void carregarDrogarias() {
        try {
            DrogariaDAO drogDAO = new DrogariaDAO();
            drogarias = drogDAO.read();

            jCDrogarias.removeAllItems();
            jCDrogarias.addItem("-- Selecione uma drogaria --");

            for (DrogariaObjeto d : drogarias) {
                jCDrogarias.addItem(d.getNomeDrogaria());
            }

            jCDrogarias.setSelectedIndex(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar drogarias:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calcularValorTotal() {
        double total = 0.0;

        // Percorre todos os itens da tabela
        for (int i = 0; i < modeloItem.getRowCount(); i++) {
            ItensVenda itemVenda = modeloItem.pegaDadosLinha(i);
            double valorItem = itemVenda.getValorItemVenda() * itemVenda.getQuantidadeItemVenda();
            total += valorItem;
        }

        return total;
    }

    // Método para atualizar a exibição do valor total
    private void atualizarValorTotal() {
        valorTotalVenda = calcularValorTotal();
        jLabel5.setText(String.format("Itens da compra - Total: R$ %.2f", valorTotalVenda));
        verificarHabilitacaoBotao();
    }

    private void styleComponents() {
        Color darkBg = new Color(30, 30, 30);
        Color surfaceBg = new Color(45, 45, 45);
        Color fieldBg = new Color(60, 60, 60);

        getContentPane().setBackground(darkBg);
        
        // Panels styling
        if (getContentPane() instanceof javax.swing.JPanel) {
            javax.swing.JPanel main = (javax.swing.JPanel) getContentPane();
            main.setBackground(darkBg);
            for (java.awt.Component c : main.getComponents()) {
                if (c instanceof javax.swing.JPanel) {
                    ((javax.swing.JPanel) c).setBackground(darkBg);
                    for (java.awt.Component subC : ((javax.swing.JPanel) c).getComponents()) {
                        if (subC instanceof javax.swing.JPanel) {
                            ((javax.swing.JPanel) subC).setBackground(darkBg);
                            for (java.awt.Component subSubC : ((javax.swing.JPanel) subC).getComponents()) {
                                if (subSubC instanceof javax.swing.JLabel) {
                                    ((javax.swing.JLabel) subSubC).setForeground(Color.WHITE);
                                }
                            }
                        } else if (subC instanceof javax.swing.JLabel) {
                            ((javax.swing.JLabel) subC).setForeground(Color.WHITE);
                        }
                    }
                } else if (c instanceof javax.swing.JLabel) {
                    ((javax.swing.JLabel) c).setForeground(Color.WHITE);
                }
            }
        }

        jLabel1.setForeground(Color.WHITE);
        jLabel4.setForeground(Color.WHITE);
        jLabel5.setForeground(Color.WHITE);
        
        jCDrogarias.setBackground(fieldBg);
        jCDrogarias.setForeground(Color.WHITE);
        jCDrogarias.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(80, 80, 80)));

        javax.swing.JButton[] botoes = {jBFinalizarNovaCompra, jBCancelarNovaCompra};
                for (javax.swing.JButton btn : botoes) {
            btn.setBackground(new java.awt.Color(45, 45, 45));
            btn.setForeground(java.awt.Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            btn.setPreferredSize(new java.awt.Dimension(160, 40));
            btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(70, 70, 70)));
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        jBFinalizarNovaCompra.setBackground(new Color(0, 100, 0)); // Verde Premium
        jBCancelarNovaCompra.setBackground(new Color(150, 0, 0)); // Vermelho

        // Tables
        javax.swing.JTable[] tables = {jTTabelaNovaCompra, jTTabelaItensCompra};
        javax.swing.JScrollPane[] scrolls = {jScrollPane2, jScrollPane3};
        
        for (int i=0; i<tables.length; i++) {
            tables[i].setBackground(fieldBg);
            tables[i].setForeground(Color.WHITE);
            tables[i].setGridColor(new Color(80, 80, 80));
            tables[i].setRowHeight(25);
            tables[i].setSelectionBackground(new Color(0, 100, 0, 80));
            tables[i].setSelectionForeground(Color.WHITE);
            tables[i].getTableHeader().setBackground(new Color(30, 30, 30));
            tables[i].getTableHeader().setForeground(Color.WHITE);
            tables[i].getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            scrolls[i].getViewport().setBackground(surfaceBg);
            scrolls[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jBFinalizarNovaCompra = new javax.swing.JButton();
        jBCancelarNovaCompra = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTTabelaNovaCompra = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTTabelaItensCompra = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCDrogarias = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                fecharJanela();
            }
        });
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setPreferredSize(new java.awt.Dimension(1920, 1080));

        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        mainPanel.setLayout(new java.awt.BorderLayout(10, 10));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        // Header Panel
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout(10, 10));

        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Nova Venda");
        headerPanel.add(jLabel1, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel drogariaPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        javax.swing.JLabel lblDrogaria = new javax.swing.JLabel("Drogaria:");
        lblDrogaria.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        drogariaPanel.add(lblDrogaria);

        jCDrogarias.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCDrogarias.setPreferredSize(new java.awt.Dimension(200, 30));
        jCDrogarias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCDrogariasActionPerformed(evt);
            }
        });
        drogariaPanel.add(jCDrogarias);
        headerPanel.add(drogariaPanel, java.awt.BorderLayout.EAST);

        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);

        // Center Tables Panel
        javax.swing.JPanel tablesPanel = new javax.swing.JPanel(new java.awt.GridLayout(2, 1, 10, 15));

        // Top Table Panel - Estoque
        javax.swing.JPanel topTablePanel = new javax.swing.JPanel(new java.awt.BorderLayout(0, 5));
        jLabel4.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)); // NOI18N
        jLabel4.setText("Medicamentos em Estoque (Clique em um item para adicionar à venda)");
        topTablePanel.add(jLabel4, java.awt.BorderLayout.NORTH);

        jTTabelaNovaCompra.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
        jTTabelaNovaCompra.setRowHeight(25);
        jTTabelaNovaCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTTabelaNovaCompraMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTTabelaNovaCompra);
        topTablePanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        // Bottom Table Panel - Carrinho
        javax.swing.JPanel bottomTablePanel = new javax.swing.JPanel(new java.awt.BorderLayout(0, 5));
        javax.swing.JLabel lblCarrinho = new javax.swing.JLabel("Itens da Venda (Carrinho)");
        lblCarrinho.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        lblCarrinho.setForeground(java.awt.Color.WHITE);
        bottomTablePanel.add(lblCarrinho, java.awt.BorderLayout.NORTH);

        jTTabelaItensCompra.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
        jTTabelaItensCompra.setRowHeight(25);
        jTTabelaItensCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTTabelaItensCompraMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTTabelaItensCompra);
        bottomTablePanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        tablesPanel.add(topTablePanel);
        tablesPanel.add(bottomTablePanel);

        mainPanel.add(tablesPanel, java.awt.BorderLayout.CENTER);

        // Footer Panel
        javax.swing.JPanel footerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        jLabel5.setText("Total: R$ 0,00");
        footerPanel.add(jLabel5, java.awt.BorderLayout.WEST);

        javax.swing.JPanel buttonsPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jBCancelarNovaCompra.setText("Cancelar");
        jBCancelarNovaCompra.setPreferredSize(new java.awt.Dimension(160, 40));
        jBCancelarNovaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarNovaCompraActionPerformed(evt);
            }
        });
        buttonsPanel.add(jBCancelarNovaCompra);

        jBFinalizarNovaCompra.setText("Finalizar");
        jBFinalizarNovaCompra.setPreferredSize(new java.awt.Dimension(160, 40));
        jBFinalizarNovaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFinalizarNovaCompraActionPerformed(evt);
            }
        });
        buttonsPanel.add(jBFinalizarNovaCompra);

        footerPanel.add(buttonsPanel, java.awt.BorderLayout.EAST);

        mainPanel.add(footerPanel, java.awt.BorderLayout.SOUTH);

        setSize(1920, 1080);
        setLocationRelativeTo(null); // Center on screen
    }// </editor-fold>//GEN-END:initComponents

    private void jBFinalizarNovaCompraActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBFinalizarNovaCompraActionPerformed
        int index = jCDrogarias.getSelectedIndex();

        // Validar se tem itens
        if (modeloItem.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Adicione pelo menos um item antes de finalizar!",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar as quantidades antes de finalizar
        java.util.Map<Integer, Integer> qtdPorMed = new java.util.HashMap<>();
        
        for (int i = 0; i < modeloItem.getRowCount(); i++) {
            ItensVenda itemVenda = modeloItem.pegaDadosLinha(i);
            
            if (itemVenda.getQuantidadeItemVenda() <= 0) {
                JOptionPane.showMessageDialog(this,
                        "A quantidade do item '" + itemVenda.getNomeMedItemVenda() + "' deve ser maior que zero!",
                        "Validação",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int codMed = itemVenda.getCodMedItemVenda();
            qtdPorMed.put(codMed, qtdPorMed.getOrDefault(codMed, 0) + itemVenda.getQuantidadeItemVenda());
        }

        for (java.util.Map.Entry<Integer, Integer> entry : qtdPorMed.entrySet()) {
            int codMed = entry.getKey();
            int qtdTotalCarrinho = entry.getValue();
            
            // Busca o medicamento para verificar se tem estoque
            Objetos.Medicamento med = null;
            for (int j = 0; j < modeloMed.getRowCount(); j++) {
                if (modeloMed.pegaDadosLinha(j).getCodMed() == codMed) {
                    med = modeloMed.pegaDadosLinha(j);
                    break;
                }
            }
            
            if (med != null && qtdTotalCarrinho > med.getQuantidadeMed()) {
                JOptionPane.showMessageDialog(this,
                        "A quantidade total do item '" + med.getNomeMed() + "' no carrinho (" + qtdTotalCarrinho + ") excede o estoque disponível (" + med.getQuantidadeMed() + ")!",
                        "Estoque Insuficiente",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        if (cnpjDrogaria == null || cnpjDrogaria.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma drogaria antes de finalizar a venda!",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        atualizarValorTotal();
        int confirma = JOptionPane.showConfirmDialog(this,
                String.format("Finalizar a venda?\n"
                        + "Nota Fiscal: %d\n"
                        + "Total de itens: %d\n"
                        + "Valor Total: R$ %.2f",
                        notaFiscalVenda,
                        modeloItem.getRowCount(),
                        valorTotalVenda),
                "Confirmar Venda",
                JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            try {
                DrogariaObjeto drogariaSelecionada = drogarias.get(index - 1);
                cnpjDrogaria = drogariaSelecionada.getCnpjDrogaria();
                System.out.println(cnpjDrogaria);
                VendaDAO vendaDAO = new VendaDAO();
                ItensVendaDAO itensDAO = new ItensVendaDAO();
                MedicamentoDAO medDAO = new MedicamentoDAO();

                // Atualiza o valor total da compra no banco
                vendaDAO.atualizarValorTotal(notaFiscalVenda, valorTotalVenda);
                vendaDAO.atualizarCnpj(notaFiscalVenda, cnpjDrogaria);

                // Salvar todos os itens da venda
                for (int i = 0; i < modeloItem.getRowCount(); i++) {
                    ItensVenda itemVenda = modeloItem.pegaDadosLinha(i);

                    // Garante que o item tem a nota fiscal correta
                    itemVenda.setNotaFiscalVendaItem(notaFiscalVenda);

                    // Salva o item no banco
                    itensDAO.create(itemVenda);
                }

                // Marca a venda como finalizada — dispara trigger trg_finalizar_venda
                // que decrementa automaticamente o estoque de medicamento
                vendaDAO.finalizar(notaFiscalVenda);

                JOptionPane.showMessageDialog(this,
                        String.format("Venda finalizada com sucesso!\n"
                                + "Nota Fiscal: %d\n"
                                + "Total de itens: %d\n"
                                + "Valor Total: R$ %.2f",
                                notaFiscalVenda,
                                modeloItem.getRowCount(),
                                valorTotalVenda),
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                JanelaVenda vmed = new JanelaVenda(user, menu);
                vmed.setVisible(true);
                dispose();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao finalizar venda: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        }

    }// GEN-LAST:event_jBFinalizarNovaCompraActionPerformed

    private void jTTabelaNovaCompraMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTTabelaNovaCompraMouseClicked
        // QUANDO SELECIONA ITEM NA TABELA DE MEDICAMNENTO, MOSTRA PARA INSERIR
        // QUANTIDADE
        // PRECISO PEGAR OS DADOS DO MEDICAMENTO QUE É UM ITEM DA COMPRA,
        // ADICIONAR NO ATRIBUTO DO ITEM A QUANTIDADE E ADICIONAR NO MODEL DA TABELA DE
        // ITEM;
        if (jTTabelaNovaCompra.getSelectedRow() != -1) {

            int linhaSelecionada = jTTabelaNovaCompra.getSelectedRow();

            // Medicamento selecionado
            var medicamento = modeloMed.pegaDadosLinha(linhaSelecionada);
            int estoqueDisponivel = medicamento.getQuantidadeMed();

            int quantidadeJaNoCarrinho = 0;
            for (int i = 0; i < modeloItem.getRowCount(); i++) {
                ItensVenda it = modeloItem.pegaDadosLinha(i);
                if (it.getCodMedItemVenda() == medicamento.getCodMed()) {
                    quantidadeJaNoCarrinho += it.getQuantidadeItemVenda();
                }
            }
            int estoqueRealDisponivel = estoqueDisponivel - quantidadeJaNoCarrinho;

            String input = JOptionPane.showInputDialog(
                    this,
                    "Quantidade do Medicamento\nEstoque disponível: " + estoqueRealDisponivel);
            // Cancelou o input
            if (input == null) {
                return;
            }
            int QTD_Item;
            try {
                QTD_Item = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Digite um número válido.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Quantidade inválida
            if (QTD_Item <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "A quantidade deve ser maior que zero.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Quantidade maior que o estoque real
            if (QTD_Item > estoqueRealDisponivel) {
                JOptionPane.showMessageDialog(
                        this,
                        "Quantidade solicitada maior que o estoque restante disponível.\n"
                                + "Estoque restante: " + estoqueRealDisponivel,
                        "Estoque insuficiente",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // PASSOU NA VALIDAÇÃO — deduplicação por CodMed
            int linhaExistente = modeloItem.findByCodMed(medicamento.getCodMed());
            if (linhaExistente >= 0) {
                // Já existe no carrinho → incrementa quantidade
                modeloItem.incrementarQuantidade(linhaExistente, QTD_Item);
            } else {
                // Novo item → cria linha
                ItensVenda itemVenda = new ItensVenda();
                itemVenda.setDataValItemVenda(medicamento.getDataValidadeMed());
                itemVenda.setValorItemVenda(medicamento.getValorMed());
                itemVenda.setCodMedItemVenda(medicamento.getCodMed());
                itemVenda.setNomeMedItemVenda(medicamento.getNomeMed());
                itemVenda.setQuantidadeItemVenda(QTD_Item);
                modeloItem.addItem(itemVenda);
            }
            atualizarValorTotal();

        }

    }// GEN-LAST:event_jTTabelaNovaCompraMouseClicked

    private void jTTabelaItensCompraMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTTabelaItensCompraMouseClicked
        if (jTTabelaItensCompra.getSelectedRow() != -1) {
            int confirma = JOptionPane.showConfirmDialog(this,
                    "Deseja remover este item da venda?",
                    "Remover Item",
                    JOptionPane.YES_NO_OPTION);

            if (confirma == JOptionPane.YES_OPTION) {
                modeloItem.removeLinha(jTTabelaItensCompra.getSelectedRow());
                atualizarValorTotal();
            }
        }
    }// GEN-LAST:event_jTTabelaItensCompraMouseClicked

    private void jBCancelarNovaCompraActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBCancelarNovaCompraActionPerformed
        int confirma = JOptionPane.showConfirmDialog(this,
                "Deseja realmente cancelar esta venda?\nTodos os itens adicionados serão perdidos!",
                "Confirmar Cancelamento",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirma == JOptionPane.YES_OPTION) {
            fecharJanela();
        }
    }// GEN-LAST:event_jBCancelarNovaCompraActionPerformed

    private void fecharJanela() {
        // EXCLUI A VENDA NO BANCO
        Venda v = new Venda();
        v.setNotaFiscalVenda(notaFiscalVenda);

        VendaDAO dao = new VendaDAO();
        dao.delete(v);
        if(user != null && menu != null) {
            JanelaVenda vmed = new JanelaVenda(user, menu);
            vmed.setVisible(true);
        }
        dispose();
    }

    private void jCDrogariasActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCDrogariasActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jCDrogariasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NovaJanelaVenda.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NovaJanelaVenda.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NovaJanelaVenda.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NovaJanelaVenda.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NovaJanelaVenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCancelarNovaCompra;
    private javax.swing.JButton jBFinalizarNovaCompra;
    private javax.swing.JComboBox<String> jCDrogarias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTTabelaItensCompra;
    private javax.swing.JTable jTTabelaNovaCompra;
    // End of variables declaration//GEN-END:variables

}
