/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import entities.User;
//import static igu.User.userPanel.borndateField;
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
import java.util.logging.Logger;

/**
 *
 * @author jonatan
 */
public class userData {
     static Connection cn = Conn.connectMysql();
    static PreparedStatement ps;
    
    static Date dt = new Date();
    
    
    public static int registrar(User u) {
        //u.nombres = "Juan";
        
        int rsu = 0;
        String sql = "INSERT INTO usersdb(Nombres,Apellidos,Telefono,Sexo,Info_adic) "
                + "VALUES(?,?,?,?,?)";
        int i = 0;
        try {
            
            ps = cn.prepareStatement(sql);
            ps.setString(++i, u.getName());
            ps.setString(++i, u.getLastname());
            ps.setString(++i, u.getPhone());
            ps.setString(++i, u.getSex());
            
            ps.setString(++i, u.getInfoadic());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsu;
    }

    public static int actualizar(User u) {
         
        int rsu = 0;
        String sql = "UPDATE usersdb SET "
                + "Nombres=?, "
                + "Apellidos=?, "
                + "Telefono=?, "
                + "Sexo=?, "       
                + "Info_adic=? "
                + "WHERE Id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            
            ps.setString(++i, u.getName());
            ps.setString(++i, u.getLastname());
            ps.setString(++i, u.getPhone());
            ps.setString(++i, u.getSex());            
            ps.setString(++i, u.getInfoadic());
            ps.setInt(++i, u.getId());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsu;
    }

    public static int eliminar(int id) {
        int rsu = 0;
        String sql = "DELETE FROM usersdb WHERE Id = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsu;
    }

    public static List<User> list(String busca) {
        List<User> ls = new ArrayList<User>();
        // SimpleDateFormat dformat=new SimpleDateFormat("yyyy-MM-dd");
           // String fecha=dformat.format(borndateField.getDate());
        String sql = "";
        if (busca.equals("")) {
            sql = "SELECT * FROM usersdb ORDER BY Id";
        } else {
            sql = "SELECT * FROM usersdb WHERE (Id LIKE'" + busca + "%' OR "
                    + "Nombres LIKE'" + busca + "%' OR Apellidos LIKE'" + busca + "%' OR "
                    + "Telefono LIKE'"+ busca +"%' OR Sexo LIKE'" + busca+ "%'  OR Info_adic LIKE'" + busca+ "%' OR "
                    + "id LIKE'" + busca + "%') "
                    + "ORDER BY Nombres";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("Id"));
                u.setName(rs.getString("Nombres"));
                u.setLastname(rs.getString("Apellidos"));
                u.setPhone(rs.getString("Telefono"));
                u.setSex(rs.getString("Sexo"));
                
                u.setInfoadic(rs.getString("Info_adic"));
                
               /* try {
                    Date date = dformat.parse(fecha);
                    
                    u.setBorndate(date);

                    
                } catch (Exception e) {
                }*/
                ls.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(userData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

   

    
}
