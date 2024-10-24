package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CredVentasDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    public int registrarCredito(int idventa) {
        String sql = "INSERT INTO cred_ventas (id_venta) VALUES (?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idventa);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return 1;
    }

    public int RegistrarAbono(double monto, int id_credito, int id_usuario) {
        String sql = "INSERT INTO abo_ventas (monto, id_credito, id_usuario) VALUES (?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, monto);
            ps.setInt(2, id_credito);
            ps.setInt(3, id_usuario);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return 1;
    }

    public List Listarventas(String valorBusqueda, int desde, int porPagina) {
        List<Credventas> lista = new ArrayList();
        String sql = "SELECT cr.id, v.vendedor, v.total, v.fecha, v.metodo, c.nombre FROM cred_ventas cr INNER JOIN ventas v ON cr.id_venta = v.id INNER JOIN clientes c ON v.cliente = c.id WHERE v.estado = ? LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT cr.id, v.vendedor, v.total, v.fecha, v.metodo, c.nombre FROM cred_ventas cr INNER JOIN ventas v ON cr.id_venta = v.id INNER JOIN clientes c ON v.cliente = c.id WHERE c.nombre LIKE '%" + valorBusqueda + "%' AND v.estado = ? OR v.fecha LIKE '%" + valorBusqueda + "%' AND v.estado = ? LIMIT " + desde + "," + porPagina;

        try {
            con = cn.getConnection();
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
                ps.setString(1, "COMPLETADO");
            } else {
                ps = con.prepareStatement(busqueda);
                ps.setString(1, "COMPLETADO");
                ps.setString(2, "COMPLETADO");
            }
            rs = ps.executeQuery();
            while (rs.next()) {                
                Credventas cred = new Credventas();
                double total = rs.getDouble("total");
                cred.setId(rs.getInt("id"));
                cred.setNombre_cli(rs.getString("nombre"));
                cred.setVendedor(rs.getString("vendedor"));
                cred.setTotal(total);
                cred.setFecha(rs.getString("fecha"));
                // calcular abonos
                String abonos = "SELECT SUM(monto) AS total FROM abo_ventas WHERE caja = ? AND id_credito = ?";
                PreparedStatement ps1 = con.prepareStatement(abonos);
                ps1.setInt(1, 1);
                ps1.setInt(2, rs.getInt("id"));
                ResultSet rs1 = ps1.executeQuery();
                if(rs1.next()){
                    double abono = rs1.getDouble("total");
                    cred.setAbonado(abono);
                    cred.setRestante(total - abono);
                }
                
                lista.add(cred);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    } 
    
    public double totalCreditos(String valorBusqueda) {
        double total = 0;
        try {
            con = cn.getConnection();
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement("SELECT COUNT(id) AS total FROM cred_ventas");
            } else {
                String busqueda = "SELECT COUNT(cr.id) AS total FROM cred_ventas cr INNER JOIN ventas v ON cr.id_venta = v.id INNER JOIN clientes c ON v.cliente = c.id WHERE c.nombre LIKE '%" + valorBusqueda + "%' OR v.fecha LIKE '%" + valorBusqueda + "%'";
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return total;
    }
}
