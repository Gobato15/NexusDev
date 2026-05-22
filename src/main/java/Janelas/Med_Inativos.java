package Janelas;

import DAO.MedicamentoDAO;
import Model.MedicamentoTableModel;
import Objetos.Medicamento;
import java.awt.Color;
import java.awt.Font;

public class Med_Inativos extends javax.swing.JFrame {

    MedicamentoTableModel modelo = new MedicamentoTableModel();
    private MedicamentoTableModel parentModelo;

    public Med_Inativos() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        jTInativos.setModel(modelo);
        modelo.recarregaTabelaInativos();
        
        // Estilização Premium
        styleComponents();
    }

    public Med_Inativos(MedicamentoTableModel parentModelo) {
        this();
        this.parentModelo = parentModelo;
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (parentModelo != null) {
                    parentModelo.recarregaTabela();
                }
            }
        });
    }

    private void styleComponents() {
        getContentPane().setBackground(new Color(45, 45, 45));
        
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        jTInativos.setBackground(new Color(60, 60, 60));
        jTInativos.setForeground(Color.WHITE);
        jTInativos.setGridColor(new Color(80, 80, 80));
        jTInativos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTInativos.setRowHeight(25);
        jTInativos.setSelectionBackground(new Color(0, 100, 0, 80));
        jTInativos.setSelectionForeground(Color.WHITE);
        jTInativos.getTableHeader().setBackground(new Color(30, 30, 30));
        jTInativos.getTableHeader().setForeground(Color.WHITE);
        jTInativos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jScrollPane1.getViewport().setBackground(new Color(45, 45, 45));

        jBReativar.setBackground(new Color(40, 167, 69));
        jBReativar.setForeground(Color.WHITE);
        jBReativar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jBReativar.setPreferredSize(new java.awt.Dimension(160, 40));
        jBReativar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(70, 70, 70)));
        jBReativar.setFocusPainted(false);
        jBReativar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        jBVoltar.setBackground(new Color(45, 45, 45));
        jBVoltar.setForeground(Color.WHITE);
        jBVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jBVoltar.setPreferredSize(new java.awt.Dimension(160, 40));
        jBVoltar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(70, 70, 70)));
        jBVoltar.setFocusPainted(false);
        jBVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTInativos = new javax.swing.JTable();
        jBReativar = new javax.swing.JButton();
        jBVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Medicamentos Desativados");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MEDICAMENTOS DESATIVADOS");

        jTInativos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Nome", "Descrição", "Quantidade", "Valor", "Validade", "Cód. Catálogo"
            }
        ));
        jScrollPane1.setViewportView(jTInativos);

        jBReativar.setText("REATIVAR SELECIONADO");
        jBReativar.addActionListener(evt -> jBReativarActionPerformed(evt));

        jBVoltar.setText("VOLTAR");
        jBVoltar.addActionListener(evt -> jBVoltarActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBReativar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBReativar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        pack();
    }

    private void jBReativarActionPerformed(java.awt.event.ActionEvent evt) {
        if (jTInativos.getSelectedRow() != -1) {
            Medicamento m = modelo.pegaDadosLinha(jTInativos.getSelectedRow());
            MedicamentoDAO dao = new MedicamentoDAO();
            dao.ativar(m);
            modelo.recarregaTabelaInativos();
            if (parentModelo != null) {
                parentModelo.recarregaTabela();
            }
        }
    }

    private void jBVoltarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Med_Inativos().setVisible(true));
    }

    private javax.swing.JButton jBReativar;
    private javax.swing.JButton jBVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTInativos;
}
