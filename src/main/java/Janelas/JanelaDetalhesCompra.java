/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Janelas;

import DAO.ItensDAO;
import Model.ItensTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 *
 * @author Usuário
 */
public class JanelaDetalhesCompra extends javax.swing.JFrame {

    private int notaFiscal;
    private ItensTableModel modelo = new ItensTableModel();

    // Cores do sistema — Premium Dark Mode
    private static final Color DARK_BG = new Color(30, 30, 30);
    private static final Color SURFACE_BG = new Color(45, 45, 45);
    private static final Color FIELD_BG = new Color(60, 60, 60);
    private static final Color BORDER_COLOR = new Color(70, 70, 70);
    private static final Color GRID_COLOR = new Color(80, 80, 80);
    private static final Color GREEN_BTN = new Color(0, 100, 0);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 14);

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JanelaDetalhesCompra.class.getName());

    // Components
    private JTable jTTabelaItensCompra;
    private JScrollPane jScrollPane1;
    private JLabel jLabelTitulo;
    private JLabel jLabelNotaFiscal;
    private JButton jBFechar;

    /**
     * Creates new form JanelaDetalhesCompra
     */
    public JanelaDetalhesCompra() {
        buildUI();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    public JanelaDetalhesCompra(int notaFiscal) {
        buildUI();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.notaFiscal = notaFiscal;

        jTTabelaItensCompra.setModel(modelo);
        jLabelNotaFiscal.setText("Nota Fiscal: " + notaFiscal);
        carregarItensDaCompra();
    }

    private void carregarItensDaCompra() {
        ItensDAO dao = new ItensDAO();
        modelo.setItens(dao.readByNotaFiscal(notaFiscal));
    }

    private void buildUI() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalhes da Compra");
        setSize(1920, 1080);

        // ─── Main Panel ───
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(DARK_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setContentPane(mainPanel);

        // ─── Header Panel ───
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BG);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(0, 0, 15, 0)));

        // Title left
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setBackground(DARK_BG);

        jLabelTitulo = new JLabel("Detalhes da Compra");
        jLabelTitulo.setFont(FONT_TITLE);
        jLabelTitulo.setForeground(Color.WHITE);
        titlePanel.add(jLabelTitulo);

        // Nota Fiscal label center
        jLabelNotaFiscal = new JLabel("Nota Fiscal: —");
        jLabelNotaFiscal.setFont(FONT_SUBTITLE);
        jLabelNotaFiscal.setForeground(new Color(180, 180, 180));
        jLabelNotaFiscal.setHorizontalAlignment(SwingConstants.CENTER);

        // Button right
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setBackground(DARK_BG);

        jBFechar = new JButton("Fechar");
        styleButton(jBFechar, GREEN_BTN);
        jBFechar.addActionListener(e -> dispose());
        btnPanel.add(jBFechar);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(jLabelNotaFiscal, BorderLayout.CENTER);
        headerPanel.add(btnPanel, BorderLayout.EAST);

        // ─── Table ───
        jTTabelaItensCompra = new JTable();
        styleTable(jTTabelaItensCompra);

        jScrollPane1 = new JScrollPane(jTTabelaItensCompra);
        jScrollPane1.getViewport().setBackground(SURFACE_BG);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        // ─── Assembly ───
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(jScrollPane1, BorderLayout.CENTER);
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(FONT_BTN);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleTable(JTable table) {
        table.setBackground(FIELD_BG);
        table.setForeground(Color.WHITE);
        table.setGridColor(GRID_COLOR);
        table.setRowHeight(28);
        table.setFont(FONT_TABLE);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(0, 100, 0, 80));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(DARK_BG);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(FONT_TABLE_HEADER);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JanelaDetalhesCompra().setVisible(true));
    }
}
