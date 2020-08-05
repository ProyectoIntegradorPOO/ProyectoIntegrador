package data;

import entities.Venta;
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
import igu.util.Config;
import igu.util.ErrorLogger;

/**
 *
 * @author Asullom
 */
public class VentaData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(VentaData.class.getName());

    static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT_PE);

    public static int create(Venta d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO venta(idCliente, nombreCliente) " //activo
                + "VALUES(?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdCliente());
            ps.setString(++i, d.getNombreCliente());
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from venta
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
            System.out.println("create.rsId:" + rsId);
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    public static int update(Venta d) {
        System.out.println("actualizar d.getIdVenta(): " + d.getIdVenta());
        int comit = 0;
        String sql = "UPDATE venta SET "
                + "idCliente=?, "
                + "nombreCliente=?, "
               
                + "fecha_actualizacion=?, "
                + "activo=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, d.getIdCliente());
            ps.setString(++i, d.getNombreCliente());
            
            ps.setString(++i, sdf.format(dt));
            ps.setInt(++i, d.getActivo());
            ps.setInt(++i, d.getIdVenta());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM venta WHERE id = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Venta> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Venta> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM venta ORDER BY id";
        } else {
            sql = "SELECT * FROM venta WHERE (id LIKE'" + filter + "%' OR "
                    + "nombres LIKE'" + filter + "%' OR cod LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY nombres";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Venta d = new Venta();
                d.setIdVenta(rs.getInt("id"));

                try {
                    d.setFecha_registroventa(sdf.parse(rs.getString("date_created")));
                    d.setFecha_actualizacionventa(sdf.parse(rs.getString("fecha_actualizacion")));
                } catch (Exception e) {
                }

                d.setIdCliente(rs.getInt("idCliente"));
                d.setNombreCliente(rs.getString("nombreCliente"));
                

                d.setActivo(rs.getInt("activo"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static List<Venta> listActivesByCliente(int idCliente) {
        System.out.println("listByCliente.idCliente:" + idCliente);
        String sql = "";
        List<Venta> ls = new ArrayList<Venta>();

        sql = " SELECT * FROM venta "
                + " WHERE idCliente = " + idCliente + " and activo=1 "
                + " ORDER BY id ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Venta d = new Venta();
                d.setIdVenta(rs.getInt("id"));
                try {
                    d.setFecha_registroventa(sdf.parse(rs.getString("date_created")));
                    d.setFecha_actualizacionventa(sdf.parse(rs.getString("fecha_actualizacion")));
                } catch (Exception e) {
                }

                d.setIdCliente(rs.getInt("idCliente"));
                d.setNombreCliente(rs.getString("nombreCliente"));
                

                d.setActivo(rs.getInt("activo"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listByCliente", ex);
        }
        return ls;
    }

    public static Venta getByPId(int id) {
        Venta d = new Venta();

        String sql = "SELECT * FROM venta WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdVenta(rs.getInt("id"));
                try {
                    d.setFecha_registroventa(sdf.parse(rs.getString("date_created")));
                    d.setFecha_actualizacionventa(sdf.parse(rs.getString("fecha_actualizacion")));
                } catch (Exception e) {
                }

                d.setIdCliente(rs.getInt("idCliente"));
                d.setNombreCliente(rs.getString("nombreCliente"));
                

                d.setActivo(rs.getInt("activo"));

            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
    
    public static int updateActivo(int id, int estado) { // cambia de estado a todos los items de la venta
        System.out.println("actualizar id: " + id);
        int comit = 0;
        String sql = "UPDATE compra_det SET "
                + "activo=? "
               
                + "WHERE comp_id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);

            ps.setInt(++i, estado);
            ps.setInt(++i, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "updateActivo", ex);
        }
        return comit;
    }
}
