/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BD.Conexao;
import Objetos.Medicamento;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
                m.setEanMed(rs.getString("EAN_Med"));
                m.setNomeMed(rs.getString("Nome_Med"));
                m.setDescricaoMed(rs.getString("Desc_Med"));
                m.setDataValidadeMed(rs.getString("DataVal_Med"));
                m.setQuantidadeMed(rs.getInt("Qtd_Med"));
                m.setValorMed(rs.getDouble("Valor_Med"));
                m.setValorCompra(rs.getDouble("Valor_Compra"));
                m.setNomeLab(rs.getString("Nome_Lab"));
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
                    "INSERT INTO medicamento (EAN_Med, Nome_Med, Desc_Med, DataVal_Med, Qtd_Med, Valor_Med, Valor_Compra, Nome_Lab, Cod_CatMed) VALUES (?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, m.getEanMed());
            stmt.setString(2, m.getNomeMed());
            stmt.setString(3, m.getDescricaoMed());
            stmt.setString(4, m.getDataValidadeMed());
            stmt.setInt(5, m.getQuantidadeMed());
            stmt.setDouble(6, m.getValorMed());
            if (m.getValorCompra() != null) {
                stmt.setDouble(7, m.getValorCompra());
            } else {
                stmt.setNull(7, Types.DECIMAL);
            }
            stmt.setString(8, m.getNomeLab());
            stmt.setInt(9, m.getCodCatMed());

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
                    "UPDATE medicamento SET EAN_Med = ?, Nome_Med = ?, Desc_Med = ?, DataVal_Med = ?, Qtd_Med = ?, Valor_Med = ?, Valor_Compra = ?, Nome_Lab = ?, Cod_CatMed = ? WHERE Cod_Med = ?");
            stmt.setString(1, m.getEanMed());
            stmt.setString(2, m.getNomeMed());
            stmt.setString(3, m.getDescricaoMed());
            stmt.setString(4, m.getDataValidadeMed());
            stmt.setInt(5, m.getQuantidadeMed());
            stmt.setDouble(6, m.getValorMed());
            if (m.getValorCompra() != null) {
                stmt.setDouble(7, m.getValorCompra());
            } else {
                stmt.setNull(7, Types.DECIMAL);
            }
            stmt.setString(8, m.getNomeLab());
            stmt.setInt(9, m.getCodCatMed());
            stmt.setInt(10, m.getCodMed());

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
                m.setEanMed(rs.getString("EAN_Med"));
                m.setNomeMed(rs.getString("Nome_Med"));
                m.setDescricaoMed(rs.getString("Desc_Med"));
                m.setDataValidadeMed(rs.getString("DataVal_Med"));
                m.setQuantidadeMed(rs.getInt("Qtd_Med"));
                m.setValorMed(rs.getDouble("Valor_Med"));
                m.setValorCompra(rs.getDouble("Valor_Compra"));
                m.setNomeLab(rs.getString("Nome_Lab"));
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
        String sql = "INSERT INTO medicamento (EAN_Med, Nome_Med, Valor_Med, Qtd_Med, Desc_Med, DataVal_Med, Valor_Compra, Nome_Lab, Cod_CatMed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexao.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Define os valores
            stmt.setString(1, med.getEanMed());
            stmt.setString(2, med.getNomeMed());
            stmt.setDouble(3, med.getValorMed());
            stmt.setInt(4, med.getQuantidadeMed());
            stmt.setString(5, med.getDescricaoMed());
            stmt.setString(6, med.getDataValidadeMed());
            if (med.getValorCompra() != null) {
                stmt.setDouble(7, med.getValorCompra());
            } else {
                stmt.setNull(7, Types.DECIMAL);
            }
            stmt.setString(8, med.getNomeLab());
            stmt.setInt(9, med.getCodCatMed());
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

    /**
     * Verifica se um EAN já existe na tabela medicamento usando a stored procedure.
     * @param ean O código EAN a verificar (13 dígitos)
     * @return true se o EAN já existe, false se está livre
     */
    public boolean verificarEanDuplicado(String ean) {
        Connection con = Conexao.getConnection();
        CallableStatement cstmt = null;

        try {
            cstmt = con.prepareCall("{CALL sp_verifica_ean_medicamento(?, ?)}");
            cstmt.setString(1, ean);
            cstmt.registerOutParameter(2, Types.TINYINT);
            cstmt.execute();

            int resultado = cstmt.getInt(2);
            return resultado == 1;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar EAN: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) { /* ignore */ }
            Conexao.closeConnection(con);
        }
    }
}
