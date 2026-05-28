package Janelas;

import DAO.MedicamentoDAO;
import Model.MedicamentoTableModel;
import Objetos.Funcionario;
import Objetos.Medicamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MedicamentoInterno extends JFrame {
    private Funcionario user;
    private Menu menu;
    private MedicamentoTableModel modelo = new MedicamentoTableModel();

    private JTable tabela;
    private JButton btnInativar, btnVerExcluidos, btnVoltar;

    public MedicamentoInterno(Funcionario user, Menu menu) {
        this.user = user;
        this.menu = menu;
        
        setTitle("Estoque Físico de Medicamentos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        modelo.recarregaTabela();
        
        getContentPane().setBackground(new Color(30, 30, 30));
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(30, 30, 30));

        // Header
        JLabel lblTitle = new JLabel("ESTOQUE FÍSICO DE MEDICAMENTOS", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Center Panel (Table)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setOpaque(false);
        
        JLabel lblSubtitle = new JLabel("Lista de medicamentos ativos em estoque (Para cadastro, finalize uma compra)");
        lblSubtitle.setForeground(Color.LIGHT_GRAY);
        lblSubtitle.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        centerPanel.add(lblSubtitle, BorderLayout.NORTH);

        tabela = new JTable(modelo);
        tabela.setBackground(new Color(60, 60, 60)); 
        tabela.setForeground(Color.WHITE);
        tabela.setRowHeight(25); 
        tabela.getTableHeader().setBackground(new Color(45, 45, 45)); 
        tabela.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setOpaque(false);

        btnVerExcluidos = styleButton(new JButton("Ver Excluídos"));
        btnVerExcluidos.setBackground(new Color(100, 100, 0));
        btnVerExcluidos.addActionListener(e -> verExcluidos());

        btnInativar = styleButton(new JButton("Inativar Selecionado"));
        btnInativar.setBackground(new Color(150, 0, 0));
        btnInativar.addActionListener(e -> inativarMedicamento());

        btnVoltar = styleButton(new JButton("Voltar ao Menu"));
        btnVoltar.addActionListener(e -> voltar());

        buttonPanel.add(btnVerExcluidos);
        buttonPanel.add(btnInativar);
        buttonPanel.add(btnVoltar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JButton styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 45, 45));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 40));
        return btn;
    }

    private void inativarMedicamento() {
        if (tabela.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um medicamento na tabela para inativar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente inativar este medicamento? Ele não aparecerá mais para vendas.", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Medicamento selecionado = modelo.pegaDadosLinha(tabela.getSelectedRow());
            MedicamentoDAO dao = new MedicamentoDAO();
            dao.delete(selecionado); // O delete já faz UPDATE Ativo_Med = 0
            modelo.recarregaTabela();
        }
    }

    private void verExcluidos() {
        Med_Inativos inativos = new Med_Inativos(modelo);
        inativos.setVisible(true);
    }

    private void voltar() {
        if (menu != null) menu.setVisible(true);
        this.dispose();
    }
}
