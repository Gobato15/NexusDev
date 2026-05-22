package Objetos;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Funcionario {
    private String nome_Fun;
    private String cpf;
    private String telefone_Fun;
    private String cep_Fun;
    private int numero_Fun;
    private String email_Fun;
    private String funcao;
    private String senhaHash;
    private boolean ativo;
    
    // Encoder único para gerar/verificar hash
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    // =========================
    // GETTERS / SETTERS
    // =========================
    
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public String getNome_Fun() {
        return nome_Fun;
    }
    
    public void setNome_Fun(String nome_Fun) {
        this.nome_Fun = nome_Fun;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone_Fun() {
        return telefone_Fun;
    }
    
    public void setTelefone_Fun(String telefone_Fun) {
        this.telefone_Fun = telefone_Fun;
    }
    
    public String getCep_Fun() {
        return cep_Fun;
    }
    
    public void setCep_Fun(String cep_Fun) {
        this.cep_Fun = cep_Fun;
    }
    
    public int getNumero_Fun() {
        return numero_Fun;
    }
    
    public void setNumero_Fun(int numero_Fun) {
        this.numero_Fun = numero_Fun;
    }
    
    public String getEmail_Fun() {
        return email_Fun;
    }
    
    public void setEmail_Fun(String email_Fun) {
        this.email_Fun = email_Fun;
    }
    
    // =========================
    // SENHA
    // =========================
    
    // usado no cadastro → converte senha normal para HASH automaticamente
    public void setSenha(String senhaPlain) {
        this.senhaHash = encoder.encode(senhaPlain);
    }
    
    // usado ao carregar do banco → recebe o hash direto
    public void setSenhaHashDireto(String hash) {
        this.senhaHash = hash;
    }
    
    public String getSenhaHash() {
        return senhaHash;
    }
    
    // verifica a senha digitada na tela de login
    public boolean verificarSenha(String senhaDigitada) {
        if (this.senhaHash == null || this.senhaHash.isEmpty()) {
            return false;
        }
        return encoder.matches(senhaDigitada, this.senhaHash);
    }
    
    // =========================
    // FUNÇÃO (cargo)
    // =========================
    
    public String getFuncao() {
        return funcao;
    }
    
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSenha_Fun() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}