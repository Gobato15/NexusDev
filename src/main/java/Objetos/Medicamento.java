/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

/**
 *
 * @author luis.fmleite
 */
public class Medicamento {

    private String nomeMed;
    private String descricaoMed;
    private String dataValidadeMed;
    private Double valorMed;
    private int quantidadeMed;
    private int codMed;
    private int codCatMed;
    private boolean ativoMed;

    /**
     * @return the nomeMed
     */
    public String getNomeMed() {
        return nomeMed;
    }

    /**
     * @param nomeMed the nomeMed to set
     */
    public void setNomeMed(String nomeMed) {
        this.nomeMed = nomeMed;
    }

    /**
     * @return the descricaoMed
     */
    public String getDescricaoMed() {
        return descricaoMed;
    }

    /**
     * @param descricaoMed the descricaoMed to set
     */
    public void setDescricaoMed(String descricaoMed) {
        this.descricaoMed = descricaoMed;
    }

    /**
     * @return the dataValidadeMed
     */
    public String getDataValidadeMed() {
        return dataValidadeMed;
    }

    /**
     * @param dataValidadeMed the dataValidadeMed to set
     */
    public void setDataValidadeMed(String dataValidadeMed) {
        this.dataValidadeMed = dataValidadeMed;
    }

    /**
     * @return the valorMed
     */
    public Double getValorMed() {
        return valorMed;
    }

    /**
     * @param valorMed the valorMed to set
     */
    public void setValorMed(Double valorMed) {
        this.valorMed = valorMed;
    }

    /**
     * @return the quantidadeMed
     */
    public int getQuantidadeMed() {
        return quantidadeMed;
    }

    /**
     * @param quantidadeMed the quantidadeMed to set
     */
    public void setQuantidadeMed(int quantidadeMed) {
        this.quantidadeMed = quantidadeMed;
    }

    /**
     * @return the codMed
     */
    public int getCodMed() {
        return codMed;
    }

    /**
     * @param codMed the codMed to set
     */
    public void setCodMed(int codMed) {
        this.codMed = codMed;
    }

    /**
     * @return the codCatMed
     */
    public int getCodCatMed() {
        return codCatMed;
    }

    /**
     * @param codCatMed the codCatMed to set
     */
    public void setCodCatMed(int codCatMed) {
        this.codCatMed = codCatMed;
    }

    public boolean isAtivoMed() {
        return ativoMed;
    }

    public void setAtivoMed(boolean ativoMed) {
        this.ativoMed = ativoMed;
    }

}
