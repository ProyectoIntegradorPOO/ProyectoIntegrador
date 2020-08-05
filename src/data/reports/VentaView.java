package data.reports;

import java.util.Date;

/**
 *
 * @author Asullom
 */
public class VentaView {

    private int idVenta;
    private int idCliente;
    private String nombreCliente;

    private int comp_activo;

    private int id;
    private int mov_tipo;
    private String producto;
    private double cantidad;

    private double precio_so;

    private double compra_so;

    private double adelanto_so;

    private double saldo_so;

    private Date fecha;
    private int esenefec;
    private Date fecha_pago;

    private int activo;
    
    
    //Datos para visualizar
    //private double egreso_do;//total_do-saldo_porpagar_do
    //private double egreso_so;//total_so-saldo_porpagar_so

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getComp_activo() {
        return comp_activo;
    }

    public void setComp_activo(int comp_activo) {
        this.comp_activo = comp_activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMov_tipo() {
        return mov_tipo;
    }

    public void setMov_tipo(int mov_tipo) {
        this.mov_tipo = mov_tipo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_so() {
        return precio_so;
    }

    public void setPrecio_so(double precio_so) {
        this.precio_so = precio_so;
    }

    public double getCompra_so() {
        return compra_so;
    }

    public void setCompra_so(double compra_so) {
        this.compra_so = compra_so;
    }

    public double getAdelanto_so() {
        return adelanto_so;
    }

    public void setAdelanto_so(double adelanto_so) {
        this.adelanto_so = adelanto_so;
    }

    public double getSaldo_so() {
        return saldo_so;
    }

    public void setSaldo_so(double saldo_so) {
        this.saldo_so = saldo_so;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEsenefec() {
        return esenefec;
    }

    public void setEsenefec(int esenefec) {
        this.esenefec = esenefec;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    
    
    
   

}
