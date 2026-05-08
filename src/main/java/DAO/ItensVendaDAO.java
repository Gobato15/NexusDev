package DAO;

import BD.Conexao;
import Objetos.ItensVenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ItensVendaDAO {

    public List<ItensVenda> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ItensVenda> itensVenda = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM item_venda");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ItensVenda iv = new ItensVenda();
                iv.setCodigoItemVenda(rs.getInt("Cod_ItemVenda"));
                iv.setDataValItemVenda(rs.getString("DataVal_ItemVenda"));
                iv.setQuantidadeItemVenda(rs.getInt("Qtd_ItemVenda"));
                iv.setValorItemVenda(rs.getDouble("Valor_ItemVenda"));
                iv.setNotaFiscalVendaItem(rs.getInt("NotaFiscal_Saida"));
                iv.setCodMedItemVenda(rs.getInt("Cod_Med"));
                itensVenda.add(iv);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }

        return itensVenda;
    }

    public void create(ItensVenda i) {

        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO item_venda (DataVal_ItemVenda, Qtd_ItemVenda, Valor_ItemVenda, Cod_Med, NotaFiscal_Saida)"
                    + " VALUES (?, ?, ?, ?, ?)"
            );

            stmt.setString(1, i.getDataValItemVenda());
            stmt.setInt(2, i.getQuantidadeItemVenda());
            stmt.setDouble(3, i.getValorItemVenda());
            stmt.setInt(4, i.getCodMedItemVenda());
            stmt.setInt(5, i.getNotaFiscalVendaItem());

            stmt.execute();

            double total = totalDaNota(i.getNotaFiscalVendaItem());
            new VendaDAO().atualizarValorTotal(i.getNotaFiscalVendaItem(), total);

            JOptionPane.showMessageDialog(null, "Item cadastrado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e);
            System.out.println("1");
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public double totalDaNota(int notaFiscal) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(
                    "SELECT SUM(Valor_ItemVenda * Qtd_ItemVenda) AS Total "
                    + "FROM item_venda WHERE NotaFiscal_Saida = ?"
            );

            stmt.setInt(1, notaFiscal);
            rs = stmt.executeQuery();

            if (rs.next()) {
                double total = rs.getDouble("Total");
                if (rs.wasNull()) {
                    return 0;
                }
                return total;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao somar itens: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }

        return 0;
    }

    public List<ItensVenda> readByNotaFiscal(int notaFiscal) {
        List<ItensVenda> itens = new ArrayList<>();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(
                    "SELECT iv.*, m.Nome_Med " +
                    "FROM item_venda iv " +
                    "INNER JOIN medicamento m ON iv.Cod_Med = m.Cod_Med " +
                    "WHERE iv.NotaFiscal_Saida = ?"
            );
            stmt.setInt(1, notaFiscal);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ItensVenda item = new ItensVenda();

                item.setNotaFiscalVendaItem(rs.getInt("NotaFiscal_Saida"));
                item.setCodMedItemVenda(rs.getInt("Cod_Med"));
                item.setQuantidadeItemVenda(rs.getInt("Qtd_ItemVenda"));
                item.setValorItemVenda(rs.getDouble("Valor_ItemVenda"));
                item.setDataValItemVenda(rs.getDate("DataVal_ItemVenda").toString());
                item.setNomeMedItemVenda(rs.getString("Nome_Med")); // NOVO

                itens.add(item);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao buscar itens da venda:\n" + e.getMessage()
            );
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }

        return itens;
    }

}