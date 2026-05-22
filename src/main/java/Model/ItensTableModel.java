package Model;

import DAO.ItensDAO;
import Objetos.Itens;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ItensTableModel extends AbstractTableModel {

    private List<Itens> dados = new ArrayList<>();

    // ✅ Nome do Produto adicionado como primeira coluna
    private String[] colunas = {
        "Nome do Produto",
        "Quantidade",
        "Valor Unitário",
        "Subtotal",
        "Data de Validade"
    };

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
        Itens item = dados.get(linha);
        switch (coluna) {
            case 0: return item.getNomeMedItem();   // ✅ Nome do produto
            case 1: return item.getQuantidadeItem();
            case 2: return String.format("R$ %.2f", item.getValorItem());
            case 3: return String.format("R$ %.2f", item.getValorItem() * item.getQuantidadeItem());
            case 4: return item.getDataValItem();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // somente leitura na tela de detalhes
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        switch (coluna) {
            case 1:
                dados.get(linha).setQuantidadeItem(Integer.parseInt((String) valor));
                break;
            case 2:
                dados.get(linha).setValorItem(Double.valueOf((String) valor));
                break;
            case 4:
                dados.get(linha).setDataValItem((String) valor);
                break;
        }
        this.fireTableRowsUpdated(linha, linha);
    }

    public void addLinha(Itens i) {
        addItem(i);
    }

    public void removeLinha(int linha) {
        this.dados.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public Itens pegaDadosLinha(int linha) {
        return dados.get(linha);
    }

    private void lerDados() {
        ItensDAO idao = new ItensDAO();
        for (Itens i : idao.read()) {
            this.addLinha(i);
        }
        this.fireTableDataChanged();
    }

    public void recarregaTabela() {
        this.dados.clear();
        lerDados();
        this.fireTableDataChanged();
    }

    public void addItem(Itens item) {
        dados.add(item);
        int lastIndex = dados.size() - 1;
        fireTableRowsInserted(lastIndex, lastIndex);
    }

    public void setItens(List<Itens> itens) {
        this.dados.clear();
        this.dados.addAll(itens);
        fireTableDataChanged();
    }

    /**
     * Procura item pelo Cod_CatMed no carrinho.
     * @return índice da linha ou -1 se não encontrado
     */
    public int findByCodCatMed(int codCatMed) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getCodCatMedItem() == codCatMed) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Incrementa a quantidade de um item já existente no carrinho.
     */
    public void incrementarQuantidade(int linha, int qtdAdicional) {
        Itens item = dados.get(linha);
        item.setQuantidadeItem(item.getQuantidadeItem() + qtdAdicional);
        fireTableRowsUpdated(linha, linha);
    }
}