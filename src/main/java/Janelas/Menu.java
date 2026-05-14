package Janelas;

import Objetos.Funcionario;
import java.awt.Color;
import javax.swing.JOptionPane;

public class Menu extends javax.swing.JFrame {

    private Funcionario user;
    private String funcaoUsuario;
    private String nomeUsuario;

    public Menu() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    public Menu(Funcionario f) {
        initComponents();
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(Color.GRAY);
        this.user = f;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBCadastrarMed = new javax.swing.JButton();
        jBCompra = new javax.swing.JButton();
        jBDrogaria = new javax.swing.JButton();
        jBLaboratorio = new javax.swing.JButton();
        jBVenda = new javax.swing.JButton();
        jBCadastrarFun = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jBCadastrarMed.setText("Medicamento");
        jBCadastrarMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCadastrarMedActionPerformed(evt);
            }
        });

        jBCompra.setText("Compra");
        jBCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCompraActionPerformed(evt);
            }
        });

        jBDrogaria.setText("Drogaria");
        jBDrogaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDrogariaActionPerformed(evt);
            }
        });

        jBLaboratorio.setText("Laboratório");
        jBLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLaboratorioActionPerformed(evt);
            }
        });

        jBVenda.setText("Venda");
        jBVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVendaActionPerformed(evt);
            }
        });

        jBCadastrarFun.setText("Cadastro Funcionário");
        jBCadastrarFun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCadastrarFunActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TELA DE OPÇÕES");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jBLaboratorio)
                                                .addGap(33, 33, 33)
                                                .addComponent(jBVenda)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15,
                                                        Short.MAX_VALUE)
                                                .addComponent(jBCompra)
                                                .addGap(29, 29, 29)
                                                .addComponent(jBDrogaria))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(jBCadastrarMed, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jBCadastrarFun)
                                                .addGap(13, 13, 13)))
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(21, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBCadastrarMed)
                                        .addComponent(jBCadastrarFun))
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBVenda)
                                        .addComponent(jBCompra)
                                        .addComponent(jBDrogaria)
                                        .addComponent(jBLaboratorio))
                                .addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBCadastrarMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCadastrarMedActionPerformed
        CadastroMedicamento cadmed = new CadastroMedicamento(user, this);
        cadmed.setVisible(true);
        cadmed.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // ✅ após setVisible
        this.setVisible(false);
    }//GEN-LAST:event_jBCadastrarMedActionPerformed

    private void jBCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCompraActionPerformed
        JanelaCompra commed = new JanelaCompra(user, this);
        commed.setVisible(true);
        commed.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // ✅ após setVisible
        this.setVisible(false);
    }//GEN-LAST:event_jBCompraActionPerformed

    private void jBLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLaboratorioActionPerformed
        JanelaLaboratorio lab = new JanelaLaboratorio(this);
        lab.setVisible(true);
        lab.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // ✅ após setVisible
        this.setVisible(false);
    }//GEN-LAST:event_jBLaboratorioActionPerformed

    private void jBVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVendaActionPerformed
        JanelaVenda vmed = new JanelaVenda(user, this);
        vmed.setVisible(true);
        vmed.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // ✅ após setVisible
        this.setVisible(false);
    }//GEN-LAST:event_jBVendaActionPerformed

    private void jBDrogariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDrogariaActionPerformed
        Drogaria drogaria = new Drogaria(this);
        drogaria.setVisible(true);
        drogaria.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // ✅ após setVisible
        this.setVisible(false);
    }//GEN-LAST:event_jBDrogariaActionPerformed

    private void jBCadastrarFunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCadastrarFunActionPerformed
        TelaCadastroFun cadfun = new TelaCadastroFun(this);
        cadfun.setVisible(true);
        cadfun.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // ✅ após setVisible
        this.setVisible(false);
    }//GEN-LAST:event_jBCadastrarFunActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCadastrarFun;
    private javax.swing.JButton jBCadastrarMed;
    private javax.swing.JButton jBCompra;
    private javax.swing.JButton jBDrogaria;
    private javax.swing.JButton jBLaboratorio;
    private javax.swing.JButton jBVenda;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
