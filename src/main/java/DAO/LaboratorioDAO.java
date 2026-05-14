/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BD.Conexao;
import Objetos.Laboratorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author andrey.munhoz
 */
public class LaboratorioDAO {

    public List<Laboratorio> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Laboratorio> laboratorio = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM laboratorio WHERE Ativo_Lab = 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Laboratorio l = new Laboratorio();
                l.setCnpjLab(rs.getString("CNPJ_Lab"));
                l.setNomeLab(rs.getString("Nome_Lab"));
                l.setTelefoneLab(rs.getString("Telefone_Lab"));
                l.setCepLab(rs.getString("Cep_Lab"));
                l.setNumeroLab(rs.getInt("Num_Lab"));
                l.setEmailLab(rs.getString("Email_Lab"));
                System.out.println(l.getNomeLab());
                laboratorio.add(l);
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Falha ao obter dados: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return laboratorio;
    }

    public List<Laboratorio> readInativos() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Laboratorio> lista = new ArrayList<>();

        try {
            stmt = con.prepareStatement(
                    "SELECT * FROM laboratorio WHERE Ativo_Lab = 0");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Laboratorio l = new Laboratorio();
                l.setCnpjLab(rs.getString("CNPJ_Lab"));
                l.setNomeLab(rs.getString("Nome_Lab"));
                l.setTelefoneLab(rs.getString("Telefone_Lab"));
                l.setCepLab(rs.getString("Cep_Lab"));
                l.setNumeroLab(rs.getInt("Num_Lab"));
                l.setEmailLab(rs.getString("Email_Lab"));
                lista.add(l);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }

        return lista;
    }

    public Laboratorio findByCnpj(String cnpj) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Laboratorio lab = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM laboratorio WHERE CNPJ_Lab = ?");
            stmt.setString(1, cnpj);
            rs = stmt.executeQuery();

            if (rs.next()) {
                lab = new Laboratorio();
                lab.setCnpjLab(rs.getString("CNPJ_Lab"));
                lab.setNomeLab(rs.getString("Nome_Lab"));
                lab.setTelefoneLab(rs.getString("Telefone_Lab"));
                lab.setCepLab(rs.getString("Cep_Lab"));
                lab.setNumeroLab(rs.getInt("Num_Lab"));
                lab.setEmailLab(rs.getString("Email_Lab"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao buscar laboratório: " + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return lab;
    }

    public void create(Laboratorio l) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO laboratorio (Num_Lab, Email_Lab, CNPJ_Lab, Nome_Lab, Telefone_Lab, Cep_Lab) VALUES (?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, l.getNumeroLab());
            stmt.setString(2, l.getEmailLab());
            stmt.setString(3, l.getCnpjLab());
            stmt.setString(4, l.getNomeLab());
            stmt.setString(5, l.getTelefoneLab());
            stmt.setString(6, l.getCepLab());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Laboratório cadastrado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar: " + e.getMessage());
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void update(Laboratorio l) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE laboratorio set Nome_Lab = ?, Telefone_Lab = ?, Cep_Lab = ?, Num_Lab = ?, Email_Lab = ? where CNPJ_Lab = ?");
            stmt.setString(1, l.getNomeLab());
            stmt.setString(2, l.getTelefoneLab());
            stmt.setString(3, l.getCepLab());
            stmt.setInt(4, l.getNumeroLab());
            stmt.setString(5, l.getEmailLab());
            stmt.setString(6, l.getCnpjLab());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Laboratório atualizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao atualizar: " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void delete(Laboratorio l) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE laboratorio SET Ativo_Lab = 0 WHERE CNPJ_Lab = ?");
            stmt.setString(1, l.getCnpjLab());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Laboratório desativado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao desativar: " + e.getMessage());
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void ativar(Laboratorio l) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE laboratorio SET Ativo_Lab = 1 WHERE CNPJ_Lab = ?");
            stmt.setString(1, l.getCnpjLab());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Laboratório reativado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao reativar: " + e.getMessage());
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

}
