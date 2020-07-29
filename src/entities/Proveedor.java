/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author jonatan
 */
public class Proveedor {
    int idprov;
    String nombreprov, telefonopro,direccionprov, infoprov;

    public int getIdprov() {
        return idprov;
    }

    public void setIdprov(int idprov) {
        this.idprov = idprov;
    }

    public String getNombreprov() {
        return nombreprov;
    }

    public void setNombreprov(String nombreprov) {
        this.nombreprov = nombreprov;
    }

    public String getTelefonopro() {
        return telefonopro;
    }

    public void setTelefonopro(String telefonopro) {
        this.telefonopro = telefonopro;
    }

    public String getDireccionprov() {
        return direccionprov;
    }

    public void setDireccionprov(String direccionprov) {
        this.direccionprov = direccionprov;
    }

    public String getInfoprov() {
        return infoprov;
    }

    public void setInfoprov(String infoprov) {
        this.infoprov = infoprov;
    }
    
    
}
