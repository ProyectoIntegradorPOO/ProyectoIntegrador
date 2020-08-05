package data.reports;

import data.*;

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
public class VentaViewReport {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(VentaViewReport.class.getName());

    static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT_PE);

    public static List<VentaView> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<VentaView> ls = new ArrayList();
        String sql = "";
        sql = " SELECT \n"
                + "	d.idVenta,\n"
                + "	c.idCliente,\n"
                + "	c.nombreCliente,\n"
               
                + "	c.activo as comp_activo,\n"
                + "	d.id, fecha, mov_tipo,\n"
                + "	producto,\n"
                + "	cantidad,\n"
               
                + "	precio_so,\n"
                + "	0 as compra_so, \n"
               
                + "	total_so as adelanto_so, \n"
               
                + "	saldo_so\n"
                + "	FROM venta_det d \n"
                + "		INNER JOIN venta c ON c.id = d.idVenta\n"
                + "		\n"
                + "	WHERE mov_tipo in (2,3) \n"
                + "	UNION\n"
                + "	SELECT \n"
                + "	d.idVenta,\n"
                + "	c.idCliente,\n"
                + "	c.nombreCliente,\n"
               
                + "	c.activo as comp_activo,\n"
                + "	d.id, fecha, mov_tipo,\n"
                + "	producto,\n"
                + "	cantidad,\n"
               
                + "	precio_so,\n"
                + "      total_so\n"
               
                + "	  END compra_so,\n"
                + "	CASE\n"
                + "	   total_so\n"
                + "	  ELSE 0\n"
                + "	  END compra_so,\n"
                + "	  \n"
                + "	0 as adelanto_so, \n"
               
                + "	0 as saldo_so\n"
                + "\n"
                + "	FROM compra_det d \n"
                + "		INNER JOIN venta c ON c.id = d.idVenta\n"
                + "	WHERE mov_tipo in (1)\n"
                + "\n"
                + "	ORDER BY d.idVenta, d.id ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                VentaView d = new VentaView();

                String fechax = rs.getString("fecha");
                System.err.println("fechax:"+fechax);
                try {
                    Date datex = sdf.parse(fechax);
                    d.setFecha(datex);
                } catch (Exception e) {
                    System.err.println("e:"+e);
                }
                d.setIdVenta(rs.getInt("idVenta"));
                d.setNombreCliente(rs.getString("nombreCliente"));
                d.setId(rs.getInt("id"));
                d.setMov_tipo(rs.getInt("mov_tipo"));

                d.setProducto(rs.getString("producto"));
                d.setCantidad(rs.getDouble("cantidad"));

                d.setPrecio_so(rs.getDouble("precio_so"));

                d.setCompra_so(rs.getDouble("compra_so"));
               
                d.setAdelanto_so(rs.getDouble("adelanto_so"));
              
                d.setSaldo_so(rs.getDouble("saldo_so"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

}
