/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import entities.familiaArticulos;
import igu.util.ErrorLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author jonatan
 */
public class familiaData {
     static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ArticulosData.class.getName());
   static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(SQLiteConfig.DEFAULT_DATE_STRING_FORMAT);
    

    public static int create(familiaArticulos d) {
        int rsId = 0; 
        String[] returns = {"idfam"};
        String sql = "INSERT INTO familiaArticulos(nombre, descripcion) "
                + "VALUES(?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getNombreFamilia());
            ps.setString(++i, d.getDescripcion());
            
            rsId = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idfam)  from clientes
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    public static int update(familiaArticulos d) {
        
        int comit = 0;
        String sql = "UPDATE familiaArticulos SET "
                + "nombre=?, "
                + "descripcion=? "
               
                + "WHERE idfam=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getNombreFamilia());
            ps.setString(++i, d.getDescripcion());
            
            ps.setInt(++i, d.getIdfam());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int idfam) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM familiaArticulos WHERE idfam = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idfam);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }
     public static List<familiaArticulos> listCmb(String filter) {
        List<familiaArticulos> ls = new ArrayList();
        ls.add(new familiaArticulos("Seleccione familia de artículo..."));
        ls.addAll(list(filter));
        return ls;
    }

    public static List<familiaArticulos> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<familiaArticulos> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM familiaArticulos ORDER BY idfam";
        } else {
            sql = "SELECT * FROM familiaArticulos WHERE (idfam LIKE'" + filter + "%' OR "
                    + "nombre LIKE'" + filter + "%' OR descripcion LIKE'"+ filter+ "%' OR "
                    + "idfam LIKE'" + filter + "%') "
                    + "ORDER BY nombre";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                familiaArticulos d = new familiaArticulos();
                d.setIdfam(rs.getInt("idfam"));
                d.setNombreFamilia(rs.getString("nombre"));
                
               
                d.setDescripcion(rs.getString("descripcion"));
                 //d.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                
                 try {
                    
                } catch (Exception e) {
                }
                ls.add(d);
                
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }
    public static List<familiaArticulos> listFamilysById(int idfam){
        System.out.println("listById.idfam:" + idfam);
        String sql = "";
        List<familiaArticulos> ls = new ArrayList<familiaArticulos>();

        sql = " SELECT * FROM familiaArticulos "
                + " WHERE idfam = " + idfam 
                + " ORDER BY idfam ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                familiaArticulos d = new familiaArticulos();
                d.setIdfam(rs.getInt("idfam"));
                

                
                d.setNombreFamilia(rs.getString("nombre"));
                d.setDescripcion(rs.getString("descripcion"));

               
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listByCliente", ex);
        }
        return ls;
    }

    public static familiaArticulos getByPId(int idfam) {
        familiaArticulos d = new familiaArticulos();

            String sql = "SELECT * FROM familiaArticulos WHERE idfam = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idfam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdfam(rs.getInt("idfam"));
                d.setNombreFamilia(rs.getString("nombre"));
                d.setDescripcion(rs.getString("descripcion"));
               
                
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
    public static familiaArticulos ContarPorNombre(String nombre) {
        familiaArticulos d = new familiaArticulos();

            String sql = "SELECT * FROM familiaArticulos WHERE nombre = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, nombre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdfam(rs.getInt("idfam"));
                d.setNombreFamilia(rs.getString("nombre"));
                d.setDescripcion(rs.getString("descripcion"));
                
                
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
}
