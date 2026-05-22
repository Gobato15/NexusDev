package Janelas;

import DAO.FuncionarioDAO;
import Objetos.Funcionario;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Fun_Inativos extends javax.swing.JFrame {

    DefaultTableModel modelo = new DefaultTableModel();
    private TelaCadastroFun parentJanela;

    public Fun_Inativos() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        
        modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Nome", "CPF", "Telefone", "E-mail", "Função"}
        );
        jTInativos.setModel(modelo);
        styleComponents();
        recarregaTabela();
    }

    public Fun_Inativos(TelaCadastroFun parentJanela) {
        this();
        this.parentJanela = parentJanela;
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

    private void recarregaTabela() {
        modelo.setRowCount(0);
        FuncionarioDAO dao = new FuncionarioDAO();
        List<Funcionario> lista = dao.readExcluidos();
        for (Funcionario f : lista) {
            modelo.addRow(new Object[]{
                f.getNome_Fun(),
                f.getCpf(),
                f.getTelefone_Fun(),
                f.getEmail_Fun(),
                f.getFuncao()
            });
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTInativos = new javax.swing.JTable();
        jBReativar = new javax.swing.JButton();
        jBVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Funcionários Excluídos");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); 
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FUNCIONÁRIOS EXCLUÍDOS");

        jTInativos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Nome", "CPF", "Telefone", "E-mail", "Função"
            }
        ));
        jScrollPane1.setViewportView(jTInativos);

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
        int selectedRow = jTInativos.getSelectedRow();
        if (selectedRow != -1) {
            String cpf = modelo.getValueAt(selectedRow, 1).toString();
            FuncionarioDAO dao = new FuncionarioDAO();
            dao.reativar(cpf);
            recarregaTabela();
            if (parentJanela != null) {
                parentJanela.carregarTabelaFuncionarios();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para reativar!");
        }
    }

    private void jBVoltarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Fun_Inativos().setVisible(true);
        });
    }

    private javax.swing.JButton jBReativar;
    private javax.swing.JButton jBVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTInativos;
}
