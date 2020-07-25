package data;

import entities.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import igu.util.ErrorLogger;
import java.util.logging.Level;

/**
 *
 * @author Asullom
 */
public class CienteData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(CienteData.class.getName());

    public static int create(Cliente d) {
        int rsId = 0;
        String[] returns = {"idcliente"};
        String sql = "INSERT INTO clientes(nombre, telefono, direccion) "
                + "VALUES(?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getTelefono());
            ps.setString(++i, d.getDireccion());
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idcliente)  from clientes
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    public static int update(Cliente d) {
        System.out.println("actualizar d.getIdcliente(): " + d.getIdcliente());
        int comit = 0;
        String sql = "UPDATE clientes SET "
                + "nombre=?, "
                + "telefono=? "
                + "WHERE idcliente=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getTelefono());
            ps.setInt(++i, d.getIdcliente());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int idcliente) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM clientes WHERE idcliente = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idcliente);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Cliente> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Cliente> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM clientes ORDER BY idcliente";
        } else {
            sql = "SELECT * FROM clientes WHERE (idcliente LIKE'" + filter + "%' OR "
                    + "nombre LIKE'" + filter + "%' OR telefono LIKE'" + filter + "%' OR "
                    + "idcliente LIKE'" + filter + "%') "
                    + "ORDER BY nombre";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Cliente d = new Cliente();
                d.setIdcliente(rs.getInt("idcliente"));
                d.setNombre(rs.getString("nombre"));
                d.setTelefono(rs.getString("telefono"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static Cliente getByPId(int idcliente) {
        Cliente d = new Cliente();

            String sql = "SELECT * FROM clientes WHERE idcliente = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idcliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdcliente(rs.getInt("idcliente"));
                d.setNombre(rs.getString("nombre"));
                d.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    /*
    public static void iniciarTransaccion() {
        try {
            cn.setAutoCommit(false);
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "iniciarTransaccion", ex);
        }
    }

    public static void finalizarTransaccion() {
        try {
            cn.commit();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "finalizarTransaccion", ex);
        }
    }

    public static void cancelarTransaccion() {
        try {
            cn.rollback();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "cancelarTransaccion", ex);
        }
    }
     */
}
