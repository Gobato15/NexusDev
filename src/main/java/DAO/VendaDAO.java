/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BD.Conexao;
import Objetos.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author andrey.munhoz
 */
public class VendaDAO {

    public List<Venda> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Venda> vendas = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM venda");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Venda v = new Venda();
                v.setNotaFiscalVenda(rs.getInt("NotaFiscal_Saida"));
                v.setDataVenda(rs.getString("Data_Venda"));
                v.setValorVenda(rs.getDouble("Valor_Venda"));
                v.setCnpjVenda(rs.getString("CNPJ_Drog"));
                v.setCpfVenda(rs.getString("CPF"));
                vendas.add(v);
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Falha ao obter dados: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return vendas;
    }

    public void create(Venda v) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO venda (Data_Venda, Valor_Venda, CNPJ_Drog, CPF) VALUES (?,?,?,?)");
            stmt.setString(1, v.getDataVenda());
            stmt.setDouble(2, v.getValorVenda());
            stmt.setString(3, v.getCnpjVenda());
            stmt.setString(4, v.getCpfVenda());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Venda cadastrada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void update(Venda v) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE venda set Data_Venda = ?, Valor_Venda = ?, CNPJ_Drog = ?, CPF = ? where NotaFiscal_Saida = ?");
            stmt.setString(1, v.getDataVenda());
            stmt.setDouble(2, v.getValorVenda());
            stmt.setString(3, v.getCnpjVenda());
            stmt.setString(4, v.getCpfVenda());
            stmt.setInt(5, v.getNotaFiscalVenda());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Venda atualizada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao atualizar: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public int createAndReturnNota(Venda v) {
        String sql = "INSERT INTO venda (Valor_Venda, CPF, CNPJ_Drog) VALUES (?, ?, ?)";

        try (Connection con = Conexao.getConnection(); PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, v.getValorVenda());
            stmt.setString(2, v.getCpfVenda());

            if (v.getCnpjVenda() == null) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, v.getCnpjVenda());
            }

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                JOptionPane.showMessageDialog(null, "Inserção falhou: nenhuma linha afetada.");
                return -1;
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // chave gerada
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma chave gerada pela inserção.");
                    return -1;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar venda: " + e);
            return -1;
        }
    }

    public void atualizarValorTotal(int nota, double total) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE venda SET Valor_Venda = ? WHERE NotaFiscal_Saida = ?"
            );
            stmt.setDouble(1, total);
            stmt.setInt(2, nota);
            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar total: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void atualizarCnpj(int nota, String cnpj) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE venda SET CNPJ_Drog = ? WHERE NotaFiscal_Saida = ?"
            );

            stmt.setString(1, cnpj);
            stmt.setInt(2, nota);

            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao definir CNPJ: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
    public List<Venda> readByCnpj(String cnpj) {
        List<Venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda WHERE CNPJ_Drog = ?";

        try (Connection con = Conexao.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cnpj);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venda v = new Venda();
                v.setNotaFiscalVenda(rs.getInt("NotaFiscal_Saida"));
                v.setValorVenda(rs.getDouble("Valor_Venda"));
                v.setCpfVenda(rs.getString("CPF"));
                v.setCnpjVenda(rs.getString("CNPJ_Drog"));
                lista.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    public void delete(Venda v) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM venda where NotaFiscal_Saida = ?");
            stmt.setInt(1, v.getNotaFiscalVenda());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Venda removida com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao remover: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void finalizar(int notaFiscal) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE venda SET Finalizada = 1 WHERE NotaFiscal_Saida = ?");
            stmt.setInt(1, notaFiscal);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao finalizar venda: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }
}
