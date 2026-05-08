package Model;
import DAO.ItensVendaDAO;
import Objetos.ItensVenda;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ItensVendaTableModel extends AbstractTableModel {
    private List<ItensVenda> dados = new ArrayList<>();
    private String[] colunas = {"Quantidade", "Valor", "Data de Validade", "Nota fiscal", "Medicamento"};

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
                return dados.get(linha).getQuantidadeItemVenda();
            case 1:
                return dados.get(linha).getValorItemVenda();
            case 2:
                return dados.get(linha).getDataValItemVenda();
            case 3:
                return dados.get(linha).getNotaFiscalVendaItem();
            case 4:
                return dados.get(linha).getNomeMedItemVenda(); // ALTERADO
        }
        return null;
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        switch (coluna) {
            case 0:
                dados.get(linha).setQuantidadeItemVenda(Integer.parseInt((String) valor));
                break;
            case 1:
                dados.get(linha).setValorItemVenda(Double.valueOf((String) valor));
                break;
            case 2:
                dados.get(linha).setDataValItemVenda((String) valor);
                break;
            case 3:
                dados.get(linha).setNotaFiscalVendaItem(Integer.parseInt((String) valor));
                break;
            case 4:
                // somente leitura, não editar o nome do medicamento
                break;
        }
        this.fireTableRowsUpdated(linha, linha);
    }

    public void addLinha(ItensVenda iv) {
        addItem(iv);
    }

    public void removeLinha(int linha) {
        this.dados.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public ItensVenda pegaDadosLinha(int linha) {
        return dados.get(linha);
    }

    private void lerDados() {
        ItensVendaDAO ivdao = new ItensVendaDAO();
        for (ItensVenda iv : ivdao.read()) {
            this.addLinha(iv);
        }
        this.fireTableDataChanged();
    }

    public void recarregaTabela() {
        this.dados.clear();
        lerDados();
        this.fireTableDataChanged();
    }

    public void addItem(ItensVenda itemVenda) {
        dados.add(itemVenda);
        int lastIndex = dados.size() - 1;
        fireTableRowsInserted(lastIndex, lastIndex);
    }

    public void setItensVenda(List<ItensVenda> itens) {
        this.dados.clear();
        this.dados.addAll(itens);
        fireTableDataChanged();
    }
}