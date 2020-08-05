package data;

import entities.VentaDet;
import entities.SaldosVenta;
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
public class VentaDetData1 {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(VentaDetData1.class.getName());

    static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT_PE);

    public static int create(VentaDet d) {
        System.out.println("d.setIdVenta: " + d.getIdVenta());
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO venta_det(idVenta, mov_tipo, producto, cantidad,   "
                
                + " precio_so, total_so, esenefec" //fecha_pago,
                + " ) " //activo
                + "VALUES(?,?,?,?,?  ,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdVenta());
            ps.setInt(++i, d.getMov_tipo());
            ps.setString(++i, d.getProducto());
            ps.setDouble(++i, d.getCant());
            

            
            
            double precio_so=4.5;
            
            
            ps.setDouble(++i, Math.round(precio_so * 100.0) / 100.0); //d.getPrecio_so()
           
            ps.setDouble(++i, Math.round(precio_so * d.getCant() * 100.0) / 100.0); //d.getTotal()

            //ps.setString(++i, sdf.format(d.getFecha()));
           
            ps.setInt(++i, 1);
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from venta_det
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

    public static int update(VentaDet d) {
        System.out.println("actualizar d.setIdVenta(): " + d.getIdVenta());
        int comit = 0;
        String sql = "UPDATE venta_det SET "
                + "idVenta=?, "
                + "mov_tipo=?, "
                + "producto=?, "
                + "cantidad=?, "
                
                + "precio_so=?, "
                
                + "total_so=?, "
                //  + "fecha=?, "
                + "esenefec=? "
                
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, d.getIdVenta());
            ps.setInt(++i, d.getMov_tipo());
            ps.setString(++i, d.getProducto());
            ps.setDouble(++i, d.getCant());
           

            
            

            double pre_so = 4.5;
            ps.setDouble(++i, Math.round(pre_so * 100.0) / 100.0); //d.getPrecio_so()
            
            ps.setDouble(++i, Math.round(pre_so * d.getCant() * 100.0) / 100.0); //d.getTotal()

//            ps.setString(++i, sdf.format(d.getFecha()));
           
            ps.setInt(++i, 1);
            ps.setInt(++i, d.getIdVenta());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int createAdelanto(VentaDet d) {
        System.out.println("d.setIdVenta: " + d.getIdVenta());
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO venta_det(idVenta, mov_tipo, producto, cantidad,   "
                
                + " precio_so,  total_so, saldo_so, esenefec,   " //fecha_pago,
                + " user_id) " //activo
                + "VALUES(?,?,?,?,?  ,?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setInt(++i, d.getIdVenta());
            ps.setInt(++i, d.getMov_tipo());
            ps.setString(++i, d.getProducto());
            ps.setDouble(++i, d.getCant());
            

           

            ps.setDouble(++i, d.getPrecio_so()); //d.getPrecio_so()
           
            ps.setDouble(++i, Math.round(d.getTotal() * 100.0) / 100.0); //d.getTotal()
            ps.setDouble(++i, Math.round(d.getSaldo_so() * 100.0) / 100.0);
            //ps.setString(++i, sdf.format(d.getFecha()));
            
            ps.setInt(++i, 1);

            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from venta_det
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

    public static int updateAdelanto(VentaDet d) {
        System.out.println("actualizar d.setIdVenta(): " + d.getIdVenta());
        int comit = 0;
        String sql = "UPDATE venta_det SET "
                + "producto=?, "
               
                
                + "total_so=?, "
                + "saldo_so=?, "
                + "esenefec=?, "
               
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);

            ps.setString(++i, d.getProducto());
            
           
            ps.setDouble(++i, Math.round(d.getTotal() * 100.0) / 100.0);
            ps.setDouble(++i, Math.round(d.getSaldo_so() * 100.0) / 100.0);

            ps.setInt(++i, 1);
           

            ps.setInt(++i, d.getIdVenta());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "updateAdelanto", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM venta_det WHERE id = ?";
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

    public static List<VentaDet> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<VentaDet> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM venta_det ORDER BY idVenta, id ";
        } else {
            sql = "SELECT * FROM venta_det WHERE (id LIKE'" + filter + "%' OR "
                    + "mov_tipo LIKE'" + filter + "%' OR producto LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY idVenta, id";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                VentaDet d = new VentaDet();
                d.setId(rs.getInt("id"));
                String fechax = rs.getString("fecha");
                try {
                    Date datex = sdf.parse(fechax);
                    d.setFecha(datex);
                } catch (Exception e) {
                }
                d.setIdVenta(rs.getInt("idVenta"));
                d.setMov_tipo(rs.getInt("mov_tipo"));
                d.setProducto(rs.getString("producto"));
                d.setCant(rs.getDouble("cantidad"));

              
                d.setPrecio_so(rs.getDouble("precio_so"));

               
                d.setTotal(rs.getDouble("total_so"));
                
                d.setActivo(rs.getInt("activo"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static List<VentaDet> listByCompra(int idVenta) {
        String sql = "";
        List<VentaDet> ls = new ArrayList<VentaDet>();

        sql = " SELECT * FROM venta_det "
                + " WHERE idVenta = " + idVenta + " "
                + " ORDER BY id ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                VentaDet d = new VentaDet();
                d.setId(rs.getInt("id"));
                String fechax = rs.getString("fecha");
                try {
                    Date datex = sdf.parse(fechax);
                    d.setFecha(datex);
                } catch (Exception e) {
                }
                d.setIdVenta(rs.getInt("idVenta"));
                d.setMov_tipo(rs.getInt("mov_tipo"));
                d.setProducto(rs.getString("producto"));
                d.setCant(rs.getDouble("cantidad"));

               
                d.setPrecio_so(rs.getDouble("precio_so"));

               
                d.setTotal(rs.getDouble("total_so"));
                
                d.setActivo(rs.getInt("activo"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listByCompra", ex);
        }
        return ls;
    }

    public static VentaDet getByPId(int id) {
        VentaDet d = new VentaDet();

        String sql = "SELECT * FROM venta_det WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String fechax = rs.getString("fecha");
                try {
                    Date datex = sdf.parse(fechax);
                    d.setFecha(datex);
                } catch (Exception e) {
                }
                d.setIdVenta(rs.getInt("idVenta"));
                d.setMov_tipo(rs.getInt("mov_tipo"));
                d.setProducto(rs.getString("producto"));
                d.setCant(rs.getDouble("cantidad"));

                
                d.setPrecio_so(rs.getDouble("precio_so"));

                
                d.setTotal(rs.getDouble("total_so"));
               
                d.setActivo(rs.getInt("activo"));

            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }

    public static SaldosVenta getSaldosByCompId(int idVenta) {
        SaldosVenta d = new SaldosVenta();

        try {
            String sql = "SELECT "
                    + "   'compas' as mov "
                    + "    ,sum(DISTINCT total_so) as sum_so "
                 
                    + "  FROM venta_det WHERE  mov_tipo=1 and idVenta = ? ";

            ps = cn.prepareStatement(sql);
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setSum_com_so(Math.round(rs.getDouble("sum_so") * 100.0) / 100.0);
                

            }
            sql = "SELECT "
                    + "   'adelantos' as mov "
                    + "    ,sum(DISTINCT saldo_so) as sum_saldo_do "
                    + "    ,sum(DISTINCT total_so) as sum_so "
                   
                    + "  FROM venta_det WHERE  mov_tipo in (2,3) and idVenta = ? ";

            ps = cn.prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            while (rs.next()) {
                d.setSum_ade_so(Math.round(rs.getDouble("sum_so") * 100.0) / 100.0);
               
                d.setSum_sal_do(Math.round(rs.getDouble("sum_saldo_do") * 100.0) / 100.0);
            }

           
            d.setSaldo_so(Math.round((d.getSum_com_so() - d.getSum_ade_so() ) * 100.0) / 100.0);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getSaldosByCompId", ex);
        }
        return d;
    }

    public static int updateCampoxx(String key, VentaDet d) {
       
        int comit = 0;
        String sql = "UPDATE venta_det SET "
                + key + "=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            if (key.equals("producto")) {
                ps.setString(++i, d.getProducto());
            }

            ps.setInt(++i, d.getIdVenta());
            ps.setInt(++i, d.getMov_tipo());
            ps.setString(++i, d.getProducto());
            ps.setDouble(++i, d.getCant());
           

            
            double pre_so=4.5;
            ps.setDouble(++i, Math.round(pre_so * 100.0) / 100.0); //d.getPre ps.setDouble(++i, Math.round(pre_do * d.getCant() * 100.0) / 100.cio_so()
            
            ps.setDouble(++i, Math.round(pre_so * d.getCant() * 100.0) / 100.0); //d.getTotal()

            ps.setInt(++i, 1);
            ps.setInt(++i, 1);
            ps.setInt(++i, d.getIdVenta());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }
}
