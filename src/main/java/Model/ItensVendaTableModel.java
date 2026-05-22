package Model;
import DAO.ItensVendaDAO;
import Objetos.ItensVenda;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ItensVendaTableModel extends AbstractTableModel {
    private List<ItensVenda> dados = new ArrayList<>();
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
        ItensVenda item = dados.get(linha);
        switch (coluna) {
            case 0: return item.getNomeMedItemVenda();
            case 1: return item.getQuantidadeItemVenda();
            case 2: return String.format("R$ %.2f", item.getValorItemVenda());
            case 3: return String.format("R$ %.2f", item.getValorItemVenda() * item.getQuantidadeItemVenda());
            case 4: return item.getDataValItemVenda();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        switch (coluna) {
            case 1:
                dados.get(linha).setQuantidadeItemVenda(Integer.parseInt((String) valor));
                break;
            case 2:
                dados.get(linha).setValorItemVenda(Double.valueOf((String) valor));
                break;
            case 4:
                dados.get(linha).setDataValItemVenda((String) valor);
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

    /**
     * Procura item pelo Cod_Med no carrinho.
     * @return índice da linha ou -1 se não encontrado
     */
    public int findByCodMed(int codMed) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getCodMedItemVenda() == codMed) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Incrementa a quantidade de um item já existente no carrinho.
     */
    public void incrementarQuantidade(int linha, int qtdAdicional) {
        ItensVenda item = dados.get(linha);
        item.setQuantidadeItemVenda(item.getQuantidadeItemVenda() + qtdAdicional);
        fireTableRowsUpdated(linha, linha);
    }
}