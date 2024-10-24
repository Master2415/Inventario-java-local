
package Modelo;

public class Detalle {
    private int id;
    private int id_pro;
    private int cantidad;
    private double precio;
    private double subTotal;
    private String descripcion;
    private int id_detalle;
    private String fecha;
    
    public Detalle(){
        
    }

    public Detalle(int id, int id_pro, int cantidad, double precio, double subTotal, String descripcion, int id_detalle, String fecha) {
        this.id = id;
        this.id_pro = id_pro;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subTotal = subTotal;
        this.descripcion = descripcion;
        this.id_detalle = id_detalle;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_venta) {
        this.id_detalle = id_venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

   
    
}
