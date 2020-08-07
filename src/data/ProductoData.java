/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static data.ArticulosData.sdf;
import entities.Producto;
import igu.util.ErrorLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Arnold Alex
 */
public class ProductoData {
     static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ArticulosData.class.getName());
    
 public static int create(Producto d) {
        int rsId = 0; 
        String[] returns = {"idproducto"};
        String sql = "INSERT INTO Producto (nomproducto, idmarca, nommarca, precio) "
                + "VALUES(?,?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getNomProducto());
            ps.setInt(++i, d.getIdMarca());
            ps.setString(++i, d.getNomMarca());
            ps.setDouble(++i, d.getPrecio());
            
          
            rsId = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idproducto)  from clientes
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }
 public static int update(Producto d) {
        
        int comit = 0;
        String sql = "UPDATE Producto SET "
                + "nomproducto=?, "
                + "idmarca=?, "
                + "monmarca=?, "
                + "precio=? "
              
                + "WHERE idproducto=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getNomProducto());
            ps.setInt(++i, d.getIdMarca());
            ps.setString(++i, d.getNomMarca());
            ps.setDouble(++i, d.getPrecio());
            ps.setInt(++i, d.getIdProducto());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }
public static int delete(int idproducto) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM Producto WHERE idproducto = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idproducto);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    } 
public static List<Producto> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Producto> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM Producto ORDER BY idproducto";
        } else {
            sql = "SELECT * FROM Producto WHERE (idproducto LIKE'" + filter + "%' OR "
                    + "nombre LIKE'" + filter + "%' OR codigo LIKE'" + filter + "%' OR "+ filter +"%' OR cantidad_producto LIKE'"+ filter +"%' OR tipo_producto LIKE'"+ filter +"%' OR precio_unidario LIKE'"+ filter +"%' OR fecha_ingreso LIKE'"+ filter +"%' OR descripcion LIKE'"
                    + "idproducto LIKE'" + filter + "%') "
                    + "ORDER BY nombre";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto d = new Producto();
                d.setNomProducto(rs.getString("nomproducto"));
                d.setIdMarca(rs.getInt("idmarca"));
                d.setNomMarca(rs.getString("nommarca"));
                d.setPrecio(rs.getDouble("precio"));
                
               
                ls.add(d);
                
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    
}  
public static List<Producto> listArticlesById(int idproducto){
        System.out.println("listById.idproducto:" + idproducto);
        String sql = "";
        List<Producto> ls = new ArrayList<Producto>();

        sql = " SELECT * FROM Producto "
                + " WHERE idproducto = " + idproducto
                + " ORDER BY idproducto ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto d = new Producto();
                try {
                 
                } catch (Exception e) {
                }

                d.setIdProducto(rs.getInt("idproducto"));
                d.setNomProducto(rs.getString("nomproducto"));
                d.setIdMarca(rs.getInt("idmarca"));
                d.setNomMarca(rs.getString("nommarca"));
                d.setPrecio(rs.getDouble("precio"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listByCliente", ex);
        }
        return ls;
    } 
public static Producto getByPId(int idproducto) {
        Producto d = new Producto();

            String sql = "SELECT * FROM Producto WHERE idproducto = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idproducto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdProducto(rs.getInt("idproducto"));
                d.setNomProducto(rs.getString("nomproducto"));
                d.setIdMarca(rs.getInt("idmarca"));
                d.setNomMarca(rs.getString("nommarca"));
                d.setPrecio(rs.getDouble("precio"));
               
              
                
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }

}
