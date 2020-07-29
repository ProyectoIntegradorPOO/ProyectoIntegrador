package data;

import entities.Proveedor;
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
public class ProveedorData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ProveedorData.class.getName());

    public static int create(Proveedor d) {
        int rsId = 0;
        String[] returns = {"idprov"};
        String sql = "INSERT INTO proveedores(nombre, telefono, direccion) "
                + "VALUES(?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getNombreprov());
            ps.setString(++i, d.getTelefonopro());
            ps.setString(++i, d.getDireccionprov());
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idprov)  from proveedores
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

    public static int update(Proveedor d) {
        
        int comit = 0;
        String sql = "UPDATE proveedores SET "
                + "nombre=?, "
                + "telefono=? "
                + "WHERE idprov=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getNombreprov());
            ps.setString(++i, d.getTelefonopro());
            ps.setInt(++i, d.getIdprov());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int idprov) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM proveedores WHERE idprov = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idprov);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Proveedor> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Proveedor> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM proveedores ORDER BY idprov";
        } else {
            sql = "SELECT * FROM proveedores WHERE (idprov LIKE'" + filter + "%' OR "
                    + "nombre LIKE'" + filter + "%' OR telefono LIKE'" + filter + "%' OR "
                    + "idprov LIKE'" + filter + "%') "
                    + "ORDER BY nombre";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Proveedor d = new Proveedor();
                d.setIdprov(rs.getInt("idprov"));
                d.setNombreprov(rs.getString("nombre"));
                d.setTelefonopro(rs.getString("telefono"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static Proveedor getByPId(int idprov) {
        Proveedor d = new Proveedor();

            String sql = "SELECT * FROM proveedores WHERE idprov = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idprov);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdprov(rs.getInt("idprov"));
                d.setNombreprov(rs.getString("nombre"));
                d.setTelefonopro(rs.getString("telefono"));
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
