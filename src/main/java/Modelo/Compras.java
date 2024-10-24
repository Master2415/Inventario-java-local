
package Modelo;

public class Compras {
    private int id;
    private int id_proveedor;
    private String nombre_pro;
    private double total;
    private String fecha;
    private int id_usuario;
    private String estado;
    
    public Compras(){
        
    }

    public Compras(int id, int id_proveedor, String nombre_pro, double total, String fecha, int id_usuario, String estado) {
        this.id = id;
        this.id_proveedor = id_proveedor;
        this.nombre_pro = nombre_pro;
        this.total = total;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre_pro() {
        return nombre_pro;
    }

    public void setNombre_pro(String nombre_pro) {
        this.nombre_pro = nombre_pro;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
