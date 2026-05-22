/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BD.Conexao;
import Objetos.Medicamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MedicamentoDAO {

    public List<Medicamento> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM medicamento WHERE Qtd_Med > 0 AND Ativo_Med = 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setCodMed(rs.getInt("Cod_Med"));
                m.setNomeMed(rs.getString("Nome_Med"));
                m.setDescricaoMed(rs.getString("Desc_Med"));
                m.setDataValidadeMed(rs.getString("DataVal_Med"));
                m.setQuantidadeMed(rs.getInt("Qtd_Med"));
                m.setValorMed(rs.getDouble("Valor_Med"));
                m.setCodCatMed(rs.getInt("Cod_CatMed"));

                medicamentos.add(m);
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Falha ao obter dados: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return medicamentos;
    }

    public void create(Medicamento m) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO medicamento (Nome_Med, Desc_Med, DataVal_Med, Qtd_Med, Valor_Med, Cod_CatMed) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, m.getNomeMed());
            stmt.setString(2, m.getDescricaoMed());
            stmt.setString(3, m.getDataValidadeMed());
            stmt.setInt(4, m.getQuantidadeMed());
            stmt.setDouble(5, m.getValorMed());
            stmt.setInt(6, m.getCodCatMed());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Medicamento cadastrado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void update(Medicamento m) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE medicamento set Nome_Med = ?, Desc_Med = ?, DataVal_Med = ?, Qtd_Med = ?, Valor_Med = ?, Cod_CatMed = ? where Cod_Med = ?");
            stmt.setString(1, m.getNomeMed());
            stmt.setString(2, m.getDescricaoMed());
            stmt.setString(3, m.getDataValidadeMed());
            stmt.setInt(4, m.getQuantidadeMed());
            stmt.setDouble(5, m.getValorMed());
            stmt.setInt(6, m.getCodCatMed());
            stmt.setInt(7, m.getCodMed());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Medicamento atualizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao atualizar: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void delete(Medicamento m) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE medicamento SET Ativo_Med = 0 where Cod_Med = ?");
            stmt.setInt(1, m.getCodMed());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Medicamento removido com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao desativar: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void ativar(Medicamento m) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE medicamento SET Ativo_Med = 1 where Cod_Med = ?");
            stmt.setInt(1, m.getCodMed());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Medicamento reativado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao reativar: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public List<Medicamento> readInativos() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM medicamento WHERE Ativo_Med = 0");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setCodMed(rs.getInt("Cod_Med"));
                m.setNomeMed(rs.getString("Nome_Med"));
                m.setDescricaoMed(rs.getString("Desc_Med"));
                m.setDataValidadeMed(rs.getString("DataVal_Med"));
                m.setQuantidadeMed(rs.getInt("Qtd_Med"));
                m.setValorMed(rs.getDouble("Valor_Med"));
                m.setCodCatMed(rs.getInt("Cod_CatMed"));

                medicamentos.add(m);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao obter dados inativos: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return medicamentos;
    }

    public int createAndReturnId(Medicamento med) {
        String sql = "INSERT INTO medicamento (Nome_Med, Valor_Med, Qtd_Med, Desc_Med, DataVal_Med, Cod_CatMed) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexao.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Define os valores
            stmt.setString(1, med.getNomeMed());
            stmt.setDouble(2, med.getValorMed());
            stmt.setInt(3, med.getQuantidadeMed());
            stmt.setString(4, med.getDescricaoMed());
            stmt.setString(5, med.getDataValidadeMed());
            stmt.setInt(6, med.getCodCatMed());
            // Executa o INSERT
            stmt.executeUpdate();

            // Pega o Cod_Med gerado pelo auto_increment
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o Cod_Med
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            e.printStackTrace();
        }

        return -1; // Erro
    }
}
