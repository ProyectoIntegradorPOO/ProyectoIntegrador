/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jonatan
 */
public class Conn {

    public static final String DEFAULT_DATE_STRING_FORMAT_PE = "dd/MM/yyyy";
    public static final String DEFAULT_DATE_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Connection connectMysql() {

        Connection conn = null;

        try {

            String dbURL = "jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            conn = DriverManager.getConnection(dbURL, "root", "cuaster1023");

            /*
             String name = "";
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("select * from Person");
             while (rs.next()) {

             name = name + ", " + rs.getString("name");
             }
             JOptionPane.showMessageDialog(null, "Connect to " + name);*/
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión" + e);
        }
        return conn;
    }

    public static void closeMysqlconnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Connection connectSQLite() {

        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:proyectoIntegrador.db?foreign_keys=on;";
            
            conn = DriverManager.getConnection(dbURL);
            

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión" + e);
        }
        return conn;
    }

    public static void closeSQLite(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
