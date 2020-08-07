package data;

import data.*;
import entities.Marca;
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
public class MarcaData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(MarcaData.class.getName());

    public static int create(Marca d) {
        int rsId = 0;
        String[] returns = {"idmarca"};
        String sql = "INSERT INTO marca(nommarca, descripcion) "
                + "VALUES(?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, d.getNomMarca());
            ps.setString(++i, d.getDescripcion());
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

    public static int update(Marca d) {
        System.out.println("actualizar d.getIdmarca(): " + d.getIdMarca());
        int comit = 0;
        String sql = "UPDATE clientes SET "
                + "nommarca=?, "
                + "descripcion=? "
                + "WHERE idmarca=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, d.getNomMarca());
            ps.setString(++i, d.getDescripcion());
           
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int idMarca) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM marca WHERE idmarca = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idMarca);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Marca> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Marca> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM marca ORDER BY idmarca";
        } else {
            sql = "SELECT * FROM marca WHERE (idmarca LIKE'" + filter + "%' OR "
                    + "nommarca LIKE'" + filter + "%' OR descripcion LIKE'" + filter + "%' OR "
                    + "idmarca LIKE'" + filter + "%') "
                    + "ORDER BY nommarca";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Marca d = new Marca();
                d.setIdMarca(rs.getInt("idmarca"));
                d.setNomMarca(rs.getString("nommarca"));
                d.setDescripcion(rs.getString("descripcion"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static Marca getByPId(int idmarca) {
        Marca d = new Marca();

            String sql = "SELECT * FROM marca WHERE idmarca = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idmarca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdMarca(rs.getInt("idmarca"));
                d.setNomMarca(rs.getString("nommarca"));
                d.setDescripcion(rs.getString("descripcion"));
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
     



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
}public static List<Marca> listCmb(String filter) {
        List<Marca> ls = new ArrayList();
        ls.add(new Marca("Seleccione familia de artículo..."));
        ls.addAll(list(filter));
        return ls;}}
