/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.CatalogoDAO;
import Objetos.CatalogoMedicamento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author andrey.munhoz
 */
public class CatalogoTableModel extends AbstractTableModel {

    private List<CatalogoMedicamento> dados = new ArrayList<>();
    private String[] colunas = { "Nome", "Código", "Descrição", "Valor", "CNPJ Lab", "Data Validade", "Quantidade" };

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return dados.get(linha).getNomeCatalogo();
            case 1:
                return dados.get(linha).getCodCatMed();
            case 2:
                return dados.get(linha).getDescCatalogo();
            case 3:
                return dados.get(linha).getValorCatalogo();
            case 4:
                return dados.get(linha).getCnpjLabCat();
            case 5:
                return dados.get(linha).getDataValItemCat();
            case 6:
                return dados.get(linha).getQuantidade();
        }
        return null;
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        switch (coluna) {
            case 0:
                dados.get(linha).setNomeCatalogo((String) valor);
                break;
            case 1:
                dados.get(linha).setCodCatMed(Integer.parseInt((String) valor));
                break;
            case 2:
                dados.get(linha).setDescCatalogo((String) valor);
                break;
            case 3:
                dados.get(linha).setValorCatalogo(Double.valueOf((String) valor));
                break;
            case 4:
                dados.get(linha).setCnpjLabCat((String) valor);
                break;
            case 5:
                dados.get(linha).setDataValItemCat((String) valor);
                break;
            case 6:
                dados.get(linha).setQuantidade(Integer.parseInt((String) valor));
                break;
        }
        this.fireTableRowsUpdated(linha, linha);
    }

    public void addLinha(CatalogoMedicamento cd) {
        this.dados.add(cd);
        this.fireTableDataChanged();
    }

    public void removeLinha(int linha) {
        this.dados.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public CatalogoMedicamento pegaDadosLinha(int linha) {
        return dados.get(linha);
    }

    private void lerDados() {
        CatalogoDAO cdao = new CatalogoDAO();
        for (CatalogoMedicamento cd : cdao.read()) {
            this.addLinha(cd);
        }
        this.fireTableDataChanged();
    }

    private void lerDadosCNPJ(String cnpj) {
        CatalogoDAO cdao = new CatalogoDAO();
        for (CatalogoMedicamento cd : cdao.readCNPJ(cnpj)) {
            this.addLinha(cd);
        }
        this.fireTableDataChanged();
    }

    public void recarregaTabela() {
        this.dados.clear();
        lerDados();
        this.fireTableDataChanged();
    }

    public void recarregaTabelaCNPJ(String cnpj) {
        this.dados.clear();
        lerDadosCNPJ(cnpj);
        this.fireTableDataChanged();
    }
}