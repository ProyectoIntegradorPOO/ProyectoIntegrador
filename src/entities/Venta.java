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
public class Venta {
    private int idVenta, idCliente,  activo; 
    private String nombreCliente;
    private Date fecha_registroventa,fecha_actualizacionventa;

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Date getFecha_registroventa() {
        return fecha_registroventa;
    }

    public void setFecha_registroventa(Date fecha_registroventa) {
        this.fecha_registroventa = fecha_registroventa;
    }

    public Date getFecha_actualizacionventa() {
        return fecha_actualizacionventa;
    }

    public void setFecha_actualizacionventa(Date fecha_actualizacionventa) {
        this.fecha_actualizacionventa = fecha_actualizacionventa;
    }
           
    
}
