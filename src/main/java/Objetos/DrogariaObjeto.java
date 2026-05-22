/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;
        

/**
 *
 * @author luis.fmleite
 */
public class DrogariaObjeto {

    private String nomeDrogaria;
    private String cnpjDrogaria;
    private int numeroDrogaria;
    private String cepDrogaria;
    private String emailDrogaria;
    private String telefoneDrogaria; 
    private boolean ativo;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String toString() {
        return nomeDrogaria;
    }
    public String getNomeDrogaria() {
        return nomeDrogaria;
    }

    public void setNomeDrogaria(String nomeDrogaria) {
        this.nomeDrogaria = nomeDrogaria;
    }

    public String getCnpjDrogaria() {
        return cnpjDrogaria;
    }

    public void setCnpjDrogaria(String cnpjDrogaria) {
        this.cnpjDrogaria = cnpjDrogaria;
    }

    public int getNumeroDrogaria() {
        return numeroDrogaria;
    }

    public void setNumeroDrogaria(int numeroDrogaria) {
        this.numeroDrogaria = numeroDrogaria;
    }

    public String getCepDrogaria() {
        return cepDrogaria;
    }

    public void setCepDrogaria(String cepDrogaria) {
        this.cepDrogaria = cepDrogaria;
    }

    public String getEmailDrogaria() {
        return emailDrogaria;
    }

    public void setEmailDrogaria(String emailDrogaria) {
        this.emailDrogaria = emailDrogaria;
    }

    public String getTelefoneDrogaria() {
        return telefoneDrogaria;
    }

    public void setTelefoneDrogaria(String telefoneDrogaria) {
        this.telefoneDrogaria = telefoneDrogaria;
    }
}
