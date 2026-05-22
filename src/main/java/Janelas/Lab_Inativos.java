package Janelas;

import DAO.LaboratorioDAO;
import Model.LaboratorioTableModel;
import Objetos.Laboratorio;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Lab_Inativos extends javax.swing.JFrame {

    LaboratorioTableModel modelo = new LaboratorioTableModel();
    private LaboratorioTableModel parentModelo;

    public Lab_Inativos() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        jTInativos.setModel(modelo);
        modelo.recarregaTabelaInativos();
        styleComponents();
    }

    public Lab_Inativos(LaboratorioTableModel parentModelo) {
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
        Color darkBg = new Color(30, 30, 30);
        Color surfaceBg = new Color(45, 45, 45);
        Color fieldBg = new Color(60, 60, 60);

        getContentPane().setBackground(darkBg);
        
        // Label
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));

        // Buttons
        javax.swing.JButton[] botoes = {jBReativar, jBVoltar};
                for (javax.swing.JButton btn : botoes) {
            btn.setBackground(new java.awt.Color(45, 45, 45));
            btn.setForeground(java.awt.Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
            btn.setPreferredSize(new java.awt.Dimension(160, 40));
            btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(70, 70, 70)));
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        jBReativar.setBackground(new Color(40, 167, 69));

        // Table
        jTInativos.setBackground(fieldBg);
        jTInativos.setForeground(Color.WHITE);
        jTInativos.setGridColor(new Color(80, 80, 80));
        jTInativos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        jTInativos.setRowHeight(25);
        jTInativos.setSelectionBackground(new Color(0, 100, 0, 80));
        jTInativos.setSelectionForeground(Color.WHITE);
        jTInativos.getTableHeader().setBackground(darkBg);
        jTInativos.getTableHeader().setForeground(Color.WHITE);
        jTInativos.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        jTInativos.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(surfaceBg);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jBReativar = new javax.swing.JButton();
        jBVoltar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTInativos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Laboratórios Desativados");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); 
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LABORATÓRIOS DESATIVADOS");

        jBReativar.setBackground(new java.awt.Color(40, 167, 69));
        jBReativar.setFont(new java.awt.Font("Segoe UI", 1, 14)); 
        jBReativar.setForeground(new java.awt.Color(255, 255, 255));
        jBReativar.setText("REATIVAR SELECIONADO");
        jBReativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBReativarActionPerformed(evt);
            }
        });

        jBVoltar.setBackground(new java.awt.Color(108, 117, 125));
        jBVoltar.setFont(new java.awt.Font("Segoe UI", 1, 14)); 
        jBVoltar.setForeground(new java.awt.Color(255, 255, 255));
        jBVoltar.setText("VOLTAR");
        jBVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVoltarActionPerformed(evt);
            }
        });

        jTInativos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Nome", "CNPJ", "Telefone", "E-mail", "Número", "CEP"
            }
        ));
        jScrollPane1.setViewportView(jTInativos);

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
            Laboratorio l = modelo.pegaDadosLinha(jTInativos.getSelectedRow());
            LaboratorioDAO dao = new LaboratorioDAO();
            dao.ativar(l);
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
        java.awt.EventQueue.invokeLater(() -> {
            new Lab_Inativos().setVisible(true);
        });
    }

    private javax.swing.JButton jBReativar;
    private javax.swing.JButton jBVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTInativos;
}
