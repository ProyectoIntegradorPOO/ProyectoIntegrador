package data;

import entities.Articulos;
import entities.familiaArticulos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import igu.util.ErrorLogger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Asullom
 */
public class ArticulosData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ArticulosData.class.getName());
   static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(SQLiteConfig.DEFAULT_DATE_STRING_FORMAT);
    

    public static int create(Articulos d) {
        int rsId = 0; 
        String[] returns = {"idart"};
        String sql = "INSERT INTO articulos(fam_id, nombre, codigo, cantidad_producto, tipo_producto, precio_unidario,  descripcion, fecha_ingreso, fecha_registro) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setInt(++i, d.getFam_id());
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getCodigo());
            ps.setDouble(++i, d.getCantidad_producto());
            ps.setString(++i, d.getTipo_producto());
            ps.setDouble(++i, d.getPrecio_unidario());
            
            ps.setString(++i, d.getDescripcion());
            ps.setString(++i, sdf.format(d.getFecha_ingreso()));
            ps.setString(++i, sdf.format(dt));
            rsId = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(idart)  from clientes
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    public static int update(Articulos d) {
        
        int comit = 0;
        String sql = "UPDATE articulos SET "
                 + "fam_id=?, "
                + "nombre=?, "
                + "codigo=?, "
                + "cantidad_producto=?, "
                + "tipo_producto=?,"
                + "precio_unidario=?,"
                
                + "descripcion=?,"
                + "fecha_ingreso=?"
                + "WHERE idart=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, d.getFam_id());
            ps.setString(++i, d.getNombre());
            ps.setString(++i, d.getCodigo());
            ps.setDouble(++i, d.getCantidad_producto());
            ps.setString(++i, d.getTipo_producto());
            ps.setDouble(++i, d.getPrecio_unidario());
            
            ps.setString(++i, d.getDescripcion());
            ps.setString(++i, sdf.format(d.getFecha_ingreso()));
            ps.setInt(++i, d.getIdart());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int idart) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM articulos WHERE idart = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idart);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }
    

    public static List<Articulos> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Articulos> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM articulos ORDER BY idart";
        } else {
            sql = "SELECT * FROM articulos WHERE (idart LIKE'" + filter + "%' OR "
                    + "nombre LIKE'" + filter + "%' OR codigo LIKE'" + filter + "%' OR "+ filter +"%' OR cantidad_producto LIKE'"+ filter +"%' OR tipo_producto LIKE'"+ filter +"%' OR precio_unidario LIKE'"+ filter +"%' OR fecha_ingreso LIKE'"+ filter +"%' OR descripcion LIKE'"
                    + "idart LIKE'" + filter + "%') "
                    + "ORDER BY nombre";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Articulos d = new Articulos();
                d.setIdart(rs.getInt("idart"));
                d.setFam_id(rs.getInt("fam_id"));
                d.setNombre(rs.getString("nombre"));
                d.setCodigo(rs.getString("codigo"));
                d.setCantidad_producto(rs.getDouble("cantidad_producto"));
                d.setTipo_producto(rs.getString("tipo_producto"));
                d.setPrecio_unidario(rs.getDouble("precio_unidario"));
               
                d.setDescripcion(rs.getString("descripcion"));
                 //d.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                String fecha = rs.getString("fecha_ingreso");
                 try {
                    Date date = sdf.parse(fecha);
                    
                 //   System.out.println("Xlist.date:" + date);
                    d.setFecha_ingreso(date);
                    
                    d.setFecha_registro(sdf.parse(rs.getString("fecha_registro")));
                   // System.out.println("list.fecha_ingreso:" + rs.getString("fecha_ingreso"));
                } catch (Exception e) {
                }
                ls.add(d);
                
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }
     public static List<Articulos> listActivesByFamily(int fam_id) {
        System.out.println("listByCliente.fam_id:" + fam_id);
        String sql = "";
        List<Articulos> ls = new ArrayList<Articulos>();

        sql = " SELECT * FROM articulos "
                + " WHERE fam_id = " + fam_id 
                + " ORDER BY idart ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Articulos d = new Articulos();
                
                d.setIdart(rs.getInt("idart"));
                d.setFam_id(rs.getInt("fam_id"));
                d.setNombre(rs.getString("nombre"));
                d.setCodigo(rs.getString("codigo"));
                d.setCantidad_producto(rs.getDouble("cantidad_producto"));
                d.setTipo_producto(rs.getString("tipo_producto"));
                d.setPrecio_unidario(rs.getDouble("precio_unidario"));
                d.setDescripcion(rs.getString("descripcion"));
                 try {
                    d.setFecha_ingreso(sdf.parse(rs.getString("fecha_ingreso")));
                    d.setFecha_registro(sdf.parse(rs.getString("fecha_registro")));
                } catch (Exception e) {
                }
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listByCliente", ex);
        }
        return ls;
    }
    public static List<Articulos> listArticlesById(int idart){
        System.out.println("listById.idart:" + idart);
        String sql = "";
        List<Articulos> ls = new ArrayList<Articulos>();

        sql = " SELECT * FROM articulos "
                + " WHERE idart = " + idart 
                + " ORDER BY idart ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Articulos d = new Articulos();
                d.setIdart(rs.getInt("idart"));
                try {
                    d.setFecha_ingreso(sdf.parse(rs.getString("fecha_ingreso")));
                    d.setFecha_registro(sdf.parse(rs.getString("fecha_registro")));
                } catch (Exception e) {
                }

                d.setFam_id(rs.getInt("fam_id"));
                d.setNombre(rs.getString("nombre"));
                d.setCodigo(rs.getString("codigo"));

                d.setCantidad_producto(rs.getDouble("cantidad_producto"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listByCliente", ex);
        }
        return ls;
    }

    public static Articulos getByPId(int idart) {
        Articulos d = new Articulos();

            String sql = "SELECT * FROM articulos WHERE idart = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, idart);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdart(rs.getInt("idart"));
                d.setFam_id(rs.getInt("fam_id"));
                d.setNombre(rs.getString("nombre"));
                d.setCodigo(rs.getString("codigo"));
                d.setCantidad_producto(rs.getDouble("cantidad_producto"));
                d.setTipo_producto(rs.getString("tipo_producto"));
                d.setPrecio_unidario(rs.getDouble("precio_unidario"));
               
                String fecha = rs.getString("fecha_ingreso");
                try {
                    Date date = sdf.parse(fecha);
                    d.setFecha_ingreso(date);
                    
                    d.setFecha_registro(sdf.parse(rs.getString("fecha_registroS")));
                } catch (Exception e) {
                }
                
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
    public static Articulos ContarPorNombre(String nombre) {
        Articulos d = new Articulos();

            String sql = "SELECT * FROM articulos WHERE nombre = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, nombre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setIdart(rs.getInt("idart"));
                d.setNombre(rs.getString("nombre"));
                d.setCodigo(rs.getString("codigo"));
                d.setCantidad_producto(rs.getDouble("cantidad_producto"));
                d.setTipo_producto(rs.getString("tipo_producto"));
                d.setPrecio_unidario(rs.getDouble("precio_unidario"));
               
                String fecha = rs.getString("fecha_ingreso");
                try {
                    Date date = sdf.parse(fecha);
                    d.setFecha_ingreso(date);
                    
                    d.setFecha_registro(sdf.parse(rs.getString("fecha_registroS")));
                } catch (Exception e) {
                }
                
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
