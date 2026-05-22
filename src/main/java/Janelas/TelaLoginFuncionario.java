package Janelas;

import DAO.FuncionarioDAO;
import Objetos.Funcionario;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

public class TelaLoginFuncionario extends javax.swing.JFrame {

    Funcionario user;

    public TelaLoginFuncionario() {
        initComponents();
        this.setLocationRelativeTo(null);
        styleComponents();
    }

    private void styleComponents() {
        getContentPane().setBackground(new Color(30, 30, 30));
        
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        jLabel2.setForeground(new Color(200, 200, 200));
        jLabel3.setForeground(new Color(200, 200, 200));
        
        jTNomeLogin.setBackground(new Color(60, 60, 60));
        jTNomeLogin.setForeground(Color.WHITE);
        jTNomeLogin.setCaretColor(Color.WHITE);
        jTNomeLogin.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        jPSenha.setBackground(new Color(60, 60, 60));
        jPSenha.setForeground(Color.WHITE);
        jPSenha.setCaretColor(Color.WHITE);
        jPSenha.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        jBEntrar.setBackground(new Color(0, 120, 215));
        jBEntrar.setForeground(Color.WHITE);
        jBEntrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jBEntrar.setFocusPainted(false);
        jBEntrar.setPreferredSize(new java.awt.Dimension(160, 40));
        jBEntrar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(70, 70, 70)));
        jBEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTNomeLogin = new javax.swing.JTextField();
        jBEntrar = new javax.swing.JButton();
        jPSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NexusDev - Login");
        setResizable(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NEXUS DEV");

        jLabel2.setText("E-mail:");

        jLabel3.setText("Senha:");

        jBEntrar.setText("ENTRAR");
        jBEntrar.addActionListener(evt -> jBEntrarActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTNomeLogin)
                    .addComponent(jPSenha)
                    .addComponent(jBEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTNomeLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jBEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    private void jBEntrarActionPerformed(java.awt.event.ActionEvent evt) {
        String email = jTNomeLogin.getText();
        String senhaDigitada = new String(jPSenha.getPassword());

        if (email.isEmpty() || senhaDigitada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha email e senha!");
            return;
        }

        FuncionarioDAO dao = new FuncionarioDAO();
        user = dao.verificaFuncionario(email);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Email não encontrado!");
            return;
        }

        if (user.verificarSenha(senhaDigitada)) {
            new Menu(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TelaLoginFuncionario().setVisible(true));
    }

    private javax.swing.JButton jBEntrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jPSenha;
    private javax.swing.JTextField jTNomeLogin;
}
