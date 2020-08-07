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
public class familiaArticulos {
    private int idfam;
    private String nombreFamilia;
    private String descripcion;
    
    public familiaArticulos(){
        
    }
    
    public familiaArticulos(String nombre){
        this.nombreFamilia=nombre;
        
    }
    public String toString(){
       return nombreFamilia;
    }

    public int getIdfam() {
        return idfam;
    }

    public void setIdfam(int idfam) {
        this.idfam = idfam;
    }

   

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
