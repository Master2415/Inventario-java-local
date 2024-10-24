package Modelo;

public class Cajas {
    private int id;
    private double monto_incial;
    private double monto_final;
    private String f_apertura;
    private String f_cierre;
    private int id_usuario;
    private String estado;
    private String usuario;

    public Cajas() {
    }

    public Cajas(int id, double monto_incial, double monto_final, String f_apertura, String f_cierre, int id_usuario, String estado, String usuario) {
        this.id = id;
        this.monto_incial = monto_incial;
        this.monto_final = monto_final;
        this.f_apertura = f_apertura;
        this.f_cierre = f_cierre;
        this.id_usuario = id_usuario;
        this.estado = estado;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonto_incial() {
        return monto_incial;
    }

    public void setMonto_incial(double monto_incial) {
        this.monto_incial = monto_incial;
    }

    public double getMonto_final() {
        return monto_final;
    }

    public void setMonto_final(double monto_final) {
        this.monto_final = monto_final;
    }

    public String getF_apertura() {
        return f_apertura;
    }

    public void setF_apertura(String f_apertura) {
        this.f_apertura = f_apertura;
    }

    public String getF_cierre() {
        return f_cierre;
    }

    public void setF_cierre(String f_cierre) {
        this.f_cierre = f_cierre;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    
}
