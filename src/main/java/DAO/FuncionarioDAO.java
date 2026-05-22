package DAO;

import BD.Conexao;
import Objetos.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FuncionarioDAO {

    // =============================
    // LISTAR FUNCIONÁRIOS ATIVOS
    // =============================
    public List<Funcionario> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        if (con == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco de dados!");
            return funcionarios;
        }

        try {
            stmt = con.prepareStatement("SELECT * FROM funcionario WHERE Ativo_Fun = 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setCpf(rs.getString("CPF"));
                f.setNome_Fun(rs.getString("Nome_Fun"));
                f.setTelefone_Fun(rs.getString("Telefone_Fun"));
                f.setCep_Fun(rs.getString("Cep_Fun"));
                f.setNumero_Fun(rs.getInt("Num_Fun"));
                f.setEmail_Fun(rs.getString("Email_Fun"));
                f.setSenhaHashDireto(rs.getString("Senha_Fun"));
                f.setFuncao(rs.getString("Funcao"));
                f.setAtivo(rs.getBoolean("Ativo_Fun"));

                funcionarios.add(f);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao obter dados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }

        return funcionarios;
    }

    // =============================
    // LISTAR FUNCIONÁRIOS EXCLUÍDOS
    // =============================
    public List<Funcionario> readExcluidos() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        if (con == null) return funcionarios;

        try {
            stmt = con.prepareStatement("SELECT * FROM funcionario WHERE Ativo_Fun = 0");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setCpf(rs.getString("CPF"));
                f.setNome_Fun(rs.getString("Nome_Fun"));
                f.setTelefone_Fun(rs.getString("Telefone_Fun"));
                f.setCep_Fun(rs.getString("Cep_Fun"));
                f.setNumero_Fun(rs.getInt("Num_Fun"));
                f.setEmail_Fun(rs.getString("Email_Fun"));
                f.setSenhaHashDireto(rs.getString("Senha_Fun"));
                f.setFuncao(rs.getString("Funcao"));
                f.setAtivo(rs.getBoolean("Ativo_Fun"));

                funcionarios.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }

        return funcionarios;
    }

    // =============================
    // CADASTRAR FUNCIONÁRIO
    // =============================
    public void salvar(Funcionario f) {
        Connection con = Conexao.getConnection();
        
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco de dados!");
            return;
        }
        
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                "INSERT INTO funcionario (CPF, Nome_Fun, Telefone_Fun, Cep_Fun, Num_Fun, Email_Fun, Senha_Fun, Funcao, Ativo_Fun) VALUES (?,?,?,?,?,?,?,?,?)"
            );

            stmt.setString(1, f.getCpf());
            stmt.setString(2, f.getNome_Fun());
            stmt.setString(3, f.getTelefone_Fun());
            stmt.setString(4, f.getCep_Fun());
            stmt.setInt(5, f.getNumero_Fun());
            stmt.setString(6, f.getEmail_Fun());
            stmt.setString(7, f.getSenhaHash());
            stmt.setString(8, f.getFuncao());
            stmt.setBoolean(9, true); // Padrão ativo

            stmt.execute();
            System.out.println("✓ Funcionário salvo com sucesso no banco!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar funcionário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    // =============================
    // ATUALIZAR FUNCIONÁRIO
    // =============================
    public void update(Funcionario f) {
        Connection con = Conexao.getConnection();
        
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco de dados!");
            return;
        }
        
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                "UPDATE funcionario SET Nome_Fun=?, Telefone_Fun=?, Cep_Fun=?, Num_Fun=?, Email_Fun=?, Senha_Fun=?, Funcao=? WHERE CPF=?"
            );

            stmt.setString(1, f.getNome_Fun());
            stmt.setString(2, f.getTelefone_Fun());
            stmt.setString(3, f.getCep_Fun());
            stmt.setInt(4, f.getNumero_Fun());
            stmt.setString(5, f.getEmail_Fun());
            stmt.setString(6, f.getSenhaHash());
            stmt.setString(7, f.getFuncao());
            stmt.setString(8, f.getCpf());

            stmt.execute();
            System.out.println("✓ Funcionário atualizado com sucesso no banco!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar funcionário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    // =============================
    // DESATIVAR FUNCIONÁRIO (SOFT DELETE)
    // =============================
    public void desativar(String cpf) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE funcionario SET Ativo_Fun = 0 WHERE CPF = ?");
            stmt.setString(1, cpf);
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Funcionário desativado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao desativar: " + e.getMessage());
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    // =============================
    // REATIVAR FUNCIONÁRIO
    // =============================
    public void reativar(String cpf) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE funcionario SET Ativo_Fun = 1 WHERE CPF = ?");
            stmt.setString(1, cpf);
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Funcionário reativado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao reativar: " + e.getMessage());
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    // =============================
    // REMOVER FUNCIONÁRIO (HARD DELETE)
    // =============================
    public void delete(Funcionario f) {
        Connection con = Conexao.getConnection();
        
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco de dados!");
            return;
        }
        
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM funcionario WHERE CPF=?");
            stmt.setString(1, f.getCpf());
            stmt.execute();
            System.out.println("✓ Funcionário removido com sucesso do banco!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover funcionário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    // =============================
    // LOGIN — BUSCAR FUNCIONÁRIO
    // =============================
    public Funcionario verificaFuncionario(String email) {
        Connection con = Conexao.getConnection();
        
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível conectar ao banco de dados!");
            return null;
        }
        
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM funcionario WHERE Email_Fun = ? AND Ativo_Fun = 1");
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Funcionario f = new Funcionario();

                f.setCpf(rs.getString("CPF"));
                f.setNome_Fun(rs.getString("Nome_Fun"));
                f.setTelefone_Fun(rs.getString("Telefone_Fun"));
                f.setCep_Fun(rs.getString("Cep_Fun"));
                f.setNumero_Fun(rs.getInt("Num_Fun"));
                f.setEmail_Fun(rs.getString("Email_Fun"));
                f.setSenhaHashDireto(rs.getString("Senha_Fun"));
                f.setFuncao(rs.getString("Funcao"));
                f.setAtivo(rs.getBoolean("Ativo_Fun"));

                return f;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar funcionário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }

        return null;
    }
}