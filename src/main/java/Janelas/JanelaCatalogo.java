package Janelas;

import DAO.CatalogoDAO;
import DAO.LaboratorioDAO;
import Model.CatalogoTableModel;
import Objetos.CatalogoMedicamento;
import Objetos.Funcionario;
import Objetos.Laboratorio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class JanelaCatalogo extends JFrame {
    private Funcionario user;
    private Menu menu;
    private CatalogoTableModel modelo = new CatalogoTableModel();
    private List<Laboratorio> laboratorios;

    private JTextField txtEan, txtNome, txtDescricao, txtValor;
    private JComboBox<String> cbLaboratorio, cbFiltroLaboratorio;
    private JTable tabela;
    private JButton btnCadastrar, btnAlterar, btnInativar, btnLimpar, btnVoltar;

    public JanelaCatalogo(Funcionario user, Menu menu) {
        this.user = user;
        this.menu = menu;
        
        setTitle("Catálogo de Medicamentos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        carregarLaboratorios();
        modelo.recarregaTabela();
        
        getContentPane().setBackground(new Color(30, 30, 30));
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(30, 30, 30));

        // Header
        JLabel lblTitle = new JLabel("CATÁLOGO DE LABORATÓRIO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Center Panel (Form + Table)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color fgColor = Color.WHITE;
        Color bgColor = new Color(60, 60, 60);

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblEan = new JLabel("EAN:"); lblEan.setForeground(fgColor); lblEan.setFont(labelFont);
        formPanel.add(lblEan, gbc);
        
        gbc.gridx = 1;
        txtEan = new JTextField(15); txtEan.setBackground(bgColor); txtEan.setForeground(fgColor);
        formPanel.add(txtEan, gbc);

        gbc.gridx = 2;
        JLabel lblNome = new JLabel("Nome:"); lblNome.setForeground(fgColor); lblNome.setFont(labelFont);
        formPanel.add(lblNome, gbc);
        
        gbc.gridx = 3; gbc.weightx = 1.0;
        txtNome = new JTextField(); txtNome.setBackground(bgColor); txtNome.setForeground(fgColor);
        formPanel.add(txtNome, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblDesc = new JLabel("Descrição:"); lblDesc.setForeground(fgColor); lblDesc.setFont(labelFont);
        formPanel.add(lblDesc, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        txtDescricao = new JTextField(); txtDescricao.setBackground(bgColor); txtDescricao.setForeground(fgColor);
        formPanel.add(txtDescricao, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0;
        JLabel lblValor = new JLabel("Valor Unitário:"); lblValor.setForeground(fgColor); lblValor.setFont(labelFont);
        formPanel.add(lblValor, gbc);
        
        gbc.gridx = 1;
        txtValor = new JTextField(15); txtValor.setBackground(bgColor); txtValor.setForeground(fgColor);
        formPanel.add(txtValor, gbc);

        gbc.gridx = 2;
        JLabel lblLab = new JLabel("Laboratório:"); lblLab.setForeground(fgColor); lblLab.setFont(labelFont);
        formPanel.add(lblLab, gbc);
        
        gbc.gridx = 3; gbc.weightx = 1.0;
        cbLaboratorio = new JComboBox<>(); cbLaboratorio.setBackground(bgColor); cbLaboratorio.setForeground(fgColor);
        formPanel.add(cbLaboratorio, gbc);

        centerPanel.add(formPanel, BorderLayout.NORTH);

        // Table Panel with Filter
        JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
        tablePanel.setOpaque(false);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(false);
        JLabel lblFiltro = new JLabel("Filtrar por Laboratório: "); lblFiltro.setForeground(fgColor);
        cbFiltroLaboratorio = new JComboBox<>(); cbFiltroLaboratorio.setBackground(bgColor); cbFiltroLaboratorio.setForeground(fgColor);
        cbFiltroLaboratorio.addActionListener(e -> filtrarTabela());
        filterPanel.add(lblFiltro); filterPanel.add(cbFiltroLaboratorio);
        tablePanel.add(filterPanel, BorderLayout.NORTH);

        tabela = new JTable(modelo);
        tabela.setBackground(bgColor); tabela.setForeground(fgColor);
        tabela.setRowHeight(25); tabela.getTableHeader().setBackground(new Color(45, 45, 45)); tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                preencherFormulario();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setOpaque(false);

        btnLimpar = styleButton(new JButton("Limpar Campos"));
        btnLimpar.setBackground(new Color(100, 100, 100));
        btnLimpar.addActionListener(e -> limparCampos());

        btnInativar = styleButton(new JButton("Inativar/Excluir"));
        btnInativar.setBackground(new Color(150, 0, 0));
        btnInativar.addActionListener(e -> inativarCatalogo());

        btnAlterar = styleButton(new JButton("Alterar"));
        btnAlterar.setBackground(new Color(0, 120, 215));
        btnAlterar.addActionListener(e -> alterarCatalogo());

        btnCadastrar = styleButton(new JButton("Cadastrar"));
        btnCadastrar.setBackground(new Color(0, 100, 0));
        btnCadastrar.addActionListener(e -> cadastrarCatalogo());

        btnVoltar = styleButton(new JButton("Voltar"));
        btnVoltar.addActionListener(e -> voltar());

        buttonPanel.add(btnLimpar);
        buttonPanel.add(btnInativar);
        buttonPanel.add(btnAlterar);
        buttonPanel.add(btnCadastrar);
        buttonPanel.add(btnVoltar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JButton styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 45, 45));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 40));
        return btn;
    }

    private void carregarLaboratorios() {
        try {
            LaboratorioDAO labDAO = new LaboratorioDAO();
            laboratorios = labDAO.read();
            
            cbLaboratorio.removeAllItems();
            cbFiltroLaboratorio.removeAllItems();
            cbFiltroLaboratorio.addItem("Todos");

            for (Laboratorio l : laboratorios) {
                cbLaboratorio.addItem(l.getNomeLab());
                cbFiltroLaboratorio.addItem(l.getNomeLab());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar laboratórios: " + e.getMessage());
        }
    }

    private void filtrarTabela() {
        if (cbFiltroLaboratorio.getSelectedIndex() == 0) {
            modelo.recarregaTabela(); // Todos
        } else {
            String labSelecionado = (String) cbFiltroLaboratorio.getSelectedItem();
            if (labSelecionado != null) {
                // Encontrar o CNPJ do laboratório selecionado
                for (Laboratorio l : laboratorios) {
                    if (l.getNomeLab().equals(labSelecionado)) {
                        modelo.recarregaTabelaCNPJ(l.getCnpjLab());
                        break;
                    }
                }
            }
        }
    }

    private void cadastrarCatalogo() {
        if (!validarFormulario()) return;

        CatalogoDAO dao = new CatalogoDAO();
        
        // Validação de EAN duplicado (Procedure)
        if (dao.verificarEanDuplicado(txtEan.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Erro: Este EAN já está cadastrado no catálogo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CatalogoMedicamento cat = instanciarCatalogo();
        dao.create(cat);
        modelo.recarregaTabela();
        limparCampos();
    }

    private void alterarCatalogo() {
        if (tabela.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para alterar.");
            return;
        }
        if (!validarFormulario()) return;

        CatalogoMedicamento cat = instanciarCatalogo();
        CatalogoMedicamento selecionado = modelo.pegaDadosLinha(tabela.getSelectedRow());
        cat.setCodCatMed(selecionado.getCodCatMed()); // Manter o ID

        CatalogoDAO dao = new CatalogoDAO();
        
        // Verifica se mudou o EAN e se o novo EAN já existe
        if (!selecionado.getEanMed().equals(cat.getEanMed()) && dao.verificarEanDuplicado(cat.getEanMed())) {
            JOptionPane.showMessageDialog(this, "Erro: Este EAN já está cadastrado no catálogo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dao.update(cat);
        modelo.recarregaTabela();
        limparCampos();
    }

    private void inativarCatalogo() {
        if (tabela.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para excluir.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este catálogo?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            CatalogoMedicamento selecionado = modelo.pegaDadosLinha(tabela.getSelectedRow());
            CatalogoDAO dao = new CatalogoDAO();
            dao.delete(selecionado);
            modelo.recarregaTabela();
            limparCampos();
        }
    }

    private boolean validarFormulario() {
        String ean = txtEan.getText().trim();
        if (!ean.matches("\\d{13}")) {
            JOptionPane.showMessageDialog(this, "Erro: EAN inválido. Deve conter exatamente 13 dígitos numéricos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtNome.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erro: Nome e Descrição são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double valor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "Erro: O campo Valor Unitário deve ser maior que zero.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: O campo Valor Unitário deve conter apenas números.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbLaboratorio.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Erro: Selecione um Laboratório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private CatalogoMedicamento instanciarCatalogo() {
        CatalogoMedicamento cat = new CatalogoMedicamento();
        cat.setEanMed(txtEan.getText().trim());
        cat.setNomeCatalogo(txtNome.getText().trim());
        cat.setDescCatalogo(txtDescricao.getText().trim());
        cat.setValorCatalogo(Double.parseDouble(txtValor.getText().trim().replace(",", ".")));
        
        String labNome = (String) cbLaboratorio.getSelectedItem();
        for (Laboratorio l : laboratorios) {
            if (l.getNomeLab().equals(labNome)) {
                cat.setCnpjLabCat(l.getCnpjLab());
                break;
            }
        }
        return cat;
    }

    private void preencherFormulario() {
        if (tabela.getSelectedRow() != -1) {
            CatalogoMedicamento cat = modelo.pegaDadosLinha(tabela.getSelectedRow());
            txtEan.setText(cat.getEanMed());
            txtNome.setText(cat.getNomeCatalogo());
            txtDescricao.setText(cat.getDescCatalogo());
            txtValor.setText(String.valueOf(cat.getValorCatalogo()));
            
            for (Laboratorio l : laboratorios) {
                if (l.getCnpjLab().equals(cat.getCnpjLabCat())) {
                    cbLaboratorio.setSelectedItem(l.getNomeLab());
                    break;
                }
            }
        }
    }

    private void limparCampos() {
        txtEan.setText("");
        txtNome.setText("");
        txtDescricao.setText("");
        txtValor.setText("");
        if (cbLaboratorio.getItemCount() > 0) cbLaboratorio.setSelectedIndex(0);
        tabela.clearSelection();
    }

    private void voltar() {
        if (menu != null) menu.setVisible(true);
        this.dispose();
    }
}
