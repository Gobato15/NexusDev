/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author luis.fmleite
 */
public class Laboratorio {
    
    private String nomeLab;
    private String cnpjLab;
    private String telefoneLab;
    private String emailLab;
    private int numeroLab;
    private String cepLab;
    private boolean ativo;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
   
    public String toString() {
        return nomeLab;
    }
    public String getNomeLab() {
        return nomeLab;
    }

    public void setNomeLab(String nomeLab) {
        this.nomeLab = nomeLab;
    }

    public String getCnpjLab() {
        return cnpjLab;
    }

    public void setCnpjLab(String cnpjLab) {
        this.cnpjLab = cnpjLab;
    }

    public String getTelefoneLab() {
        return telefoneLab;
    }

    public void setTelefoneLab(String telefoneLab) {
        this.telefoneLab = telefoneLab;
    }

    public String getEmailLab() {
        return emailLab;
    }

    public void setEmailLab(String emailLab) {
        this.emailLab = emailLab;
    }

    public int getNumeroLab() {
        return numeroLab;
    }

    public void setNumeroLab(int numeroLab) {
        this.numeroLab = numeroLab;
    }

    public String getCepLab() {
        return cepLab;
    }

    public void setCepLab(String cepLab) {
        this.cepLab = cepLab;
    }

   
}
