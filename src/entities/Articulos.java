/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author ratecsi
 */
public class Articulos {
    private int idart;
    private int fam_id;
    private String nombre;
    private String codigo;
    private Double cantidad_producto;
    private String tipo_producto;
    private Double precio_unidario;
    private Date fecha_ingreso;
    private Date fecha_registro;
    private String descripcion;
    
    

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getIdart() {
        return idart;
    }

    public void setIdart(int idart) {
        this.idart = idart;
    }
    

    

    public int getFam_id() {
        return fam_id;
    }

    public void setFam_id(int fam_id) {
        this.fam_id = fam_id;
    }
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getCodigo(){
        return codigo;
    }
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
    public Double getCantidad_producto(){
        return cantidad_producto;
    }
    public void setCantidad_producto(Double cantidad_producto){
        this.cantidad_producto = cantidad_producto;
    }
    public String getTipo_producto(){
        return tipo_producto;
    }
    public void setTipo_producto(String tipo_producto){
        this.tipo_producto = tipo_producto;
    }
    public Double getPrecio_unidario(){
        return precio_unidario;
    }
    public void setPrecio_unidario(Double precio_unidario){
        this.precio_unidario = precio_unidario;
    }
   public Date getFecha_ingreso(){
        return fecha_ingreso;
    }
    public void setFecha_ingreso(Date fecha_ingreso){
        this.fecha_ingreso = fecha_ingreso;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
}
