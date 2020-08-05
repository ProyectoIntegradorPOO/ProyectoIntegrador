/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import data.Conn;
import entities.Articulos;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author jonatan
 */
public class AlmacenController {
    Connection cn=Conn.connectSQLite();
    PreparedStatement ps;
    
    public static int cantidadArticulo(Articulos a){
        int cantidad=0;
        
        
        
        return cantidad;
    }
}
