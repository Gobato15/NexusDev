package Janelas;

import Objetos.Funcionario;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Menu extends javax.swing.JFrame {

    private Funcionario user;

    public Menu() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        styleComponents();
    }

    public Menu(Funcionario f) {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.user = f;
        styleComponents();
    }

    private void styleComponents() {
        getContentPane().setBackground(new Color(30, 30, 30));
        
        // Estilização dos botões
        JButton[] botoes = {jBCatalogo, jBEstoqueMed, jBCadastrarFun, jBVenda, jBCompra, jBDrogaria, jBLaboratorio};
                for (javax.swing.JButton btn : botoes) {
            btn.setBackground(new java.awt.Color(45, 45, 45));
            btn.setForeground(java.awt.Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            btn.setPreferredSize(new java.awt.Dimension(160, 40));
            btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(70, 70, 70)));
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanelButtons = new javax.swing.JPanel();
        jBCatalogo = new javax.swing.JButton();
        jBEstoqueMed = new javax.swing.JButton();
        jBCadastrarFun = new javax.swing.JButton();
        jBVenda = new javax.swing.JButton();
        jBCompra = new javax.swing.JButton();
        jBDrogaria = new javax.swing.JButton();
        jBLaboratorio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NexusDev - Menu Principal");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NEXUS DEV - SISTEMA FARMACÊUTICO");

        jPanelButtons.setOpaque(false);
        jPanelButtons.setLayout(new java.awt.GridLayout(3, 3, 30, 30));

        jBCatalogo.setText("CATÁLOGO MEDICAMENTO");
        jBCatalogo.addActionListener(evt -> jBCatalogoActionPerformed(evt));
        jPanelButtons.add(jBCatalogo);

        jBEstoqueMed.setText("ESTOQUE FÍSICO");
        jBEstoqueMed.addActionListener(evt -> jBEstoqueMedActionPerformed(evt));
        jPanelButtons.add(jBEstoqueMed);

        jBCadastrarFun.setText("FUNCIONÁRIOS");
        jBCadastrarFun.addActionListener(evt -> jBCadastrarFunActionPerformed(evt));
        jPanelButtons.add(jBCadastrarFun);

        jBVenda.setText("VENDAS");
        jBVenda.addActionListener(evt -> jBVendaActionPerformed(evt));
        jPanelButtons.add(jBVenda);

        jBCompra.setText("COMPRAS");
        jBCompra.addActionListener(evt -> jBCompraActionPerformed(evt));
        jPanelButtons.add(jBCompra);

        jBDrogaria.setText("DROGARIAS");
        jBDrogaria.addActionListener(evt -> jBDrogariaActionPerformed(evt));
        jPanelButtons.add(jBDrogaria);

        jBLaboratorio.setText("LABORATÓRIOS");
        jBLaboratorio.addActionListener(evt -> jBLaboratorioActionPerformed(evt));
        jPanelButtons.add(jBLaboratorio);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addGap(100, 100, 100))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1)
                .addGap(60, 60, 60)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addGap(100, 100, 100))
        );

        pack();
    }

    private void jBCatalogoActionPerformed(java.awt.event.ActionEvent evt) {
        JanelaCatalogo cat = new JanelaCatalogo(user, this);
        cat.setVisible(true);
        this.setVisible(false);
    }

    private void jBEstoqueMedActionPerformed(java.awt.event.ActionEvent evt) {
        MedicamentoInterno est = new MedicamentoInterno(user, this);
        est.setVisible(true);
        this.setVisible(false);
    }

    private void jBCompraActionPerformed(java.awt.event.ActionEvent evt) {
        JanelaCompra commed = new JanelaCompra(user, this);
        commed.setVisible(true);
        this.setVisible(false);
    }

    private void jBLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {
        JanelaLaboratorio lab = new JanelaLaboratorio(this);
        lab.setVisible(true);
        this.setVisible(false);
    }

    private void jBVendaActionPerformed(java.awt.event.ActionEvent evt) {
        JanelaVenda vmed = new JanelaVenda(user, this);
        vmed.setVisible(true);
        this.setVisible(false);
    }

    private void jBDrogariaActionPerformed(java.awt.event.ActionEvent evt) {
        Drogaria drogaria = new Drogaria(this);
        drogaria.setVisible(true);
        this.setVisible(false);
    }

    private void jBCadastrarFunActionPerformed(java.awt.event.ActionEvent evt) {
        if (user != null && "Administrador".equalsIgnoreCase(user.getFuncao())) {
            TelaCadastroFun cadfun = new TelaCadastroFun(this);
            cadfun.setVisible(true);
            this.setVisible(false);
        } else {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Acesso negado!\nApenas administradores podem gerenciar funcionários.",
                "Permissão Insuficiente",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }

    private javax.swing.JButton jBCadastrarFun;
    private javax.swing.JButton jBCatalogo;
    private javax.swing.JButton jBEstoqueMed;
    private javax.swing.JButton jBCompra;
    private javax.swing.JButton jBDrogaria;
    private javax.swing.JButton jBLaboratorio;
    private javax.swing.JButton jBVenda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelButtons;
}
