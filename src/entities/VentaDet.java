/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author jonatan
 */
public class VentaDet {
        private int id;
        private int mov_tipo;
	private String Producto;
	private double cant;
        
	
	
	
	
	
        
	
        
	
	private double precio_so;
        
	
	private double total;
        private double saldo_so;

    public double getSaldo_so() {
        return saldo_so;
    }

    public void setSaldo_so(double saldo_so) {
        this.saldo_so = saldo_so;
    }
	private int esenefec;
	private Date fecha;
	private Date fecha_pago;
	//private int user_id;
        private int idVenta;
        private int activo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMov_tipo() {
        return mov_tipo;
    }

    public void setMov_tipo(int mov_tipo) {
        this.mov_tipo = mov_tipo;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String Producto) {
        this.Producto = Producto;
    }

    public double getCant() {
        return cant;
    }

    public void setCant(double cant) {
        this.cant = cant;
    }

    

    public double getPrecio_so() {
        return precio_so;
    }

    public void setPrecio_so(double precio_so) {
        this.precio_so = precio_so;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getEsenefec() {
        return esenefec;
    }

    public void setEsenefec(int esenefec) {
        this.esenefec = esenefec;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

  
        
   
        
}
