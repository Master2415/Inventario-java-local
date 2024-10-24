
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProveedorDao {
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = cn.getConnection();
    public String RegistrarProveedor(Proveedor pr){
        String consulta = "SELECT * FROM proveedor WHERE ruc = ?";
        String sql = "INSERT INTO proveedor (ruc, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, pr.getRuc());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, pr.getRuc());
           ps.setString(2, pr.getNombre());
           ps.setString(3, pr.getTelefono());
           ps.setString(4, pr.getDireccion());
                ps.execute();
                return "registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }
    
    public List ListarProveedor(String valorBusqueda, int desde, int porPagina){
        List<Proveedor> Listapr = new ArrayList();
        String sql = "SELECT * FROM proveedor LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT * FROM proveedor WHERE ruc LIKE '%" + valorBusqueda + "%' OR nombre LIKE '%" + valorBusqueda + "%' LIMIT " + desde + "," + porPagina;
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            while (rs.next()) {                
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("id"));
                pr.setRuc(rs.getString("ruc"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getString("telefono"));
                pr.setDireccion(rs.getString("direccion"));
                Listapr.add(pr);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listapr;
    }
    
    public boolean EliminarProveedor(int id){
        String sql = "DELETE FROM proveedor WHERE id = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public double totalProveedor(String valorBusqueda) {
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM proveedor";
        String busqueda = "SELECT COUNT(*) AS total FROM proveedor WHERE ruc LIKE '%" + valorBusqueda + "%' OR nombre LIKE '%" + valorBusqueda + "%'";
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
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
    
    public String ModificarProveedor(Proveedor pr){
        String consulta = "SELECT * FROM proveedor WHERE ruc = ? AND id != ?";
        String sql = "UPDATE proveedor SET ruc=?, nombre=?, telefono=?, direccion=? WHERE id=?";
        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, pr.getRuc());
            ps.setInt(2, pr.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, pr.getRuc());
                ps.setString(2, pr.getNombre());
                ps.setString(3, pr.getTelefono());
                ps.setString(4, pr.getDireccion());
                ps.setInt(5, pr.getId());
                ps.execute();
                return "modificado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }
    
    public List comboProveedor(){
        List<Proveedor> Listapr = new ArrayList();
        String sql = "SELECT * FROM proveedor";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("id"));
                pr.setRuc(rs.getString("ruc"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getString("telefono"));
                pr.setDireccion(rs.getString("direccion"));
                Listapr.add(pr);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listapr;
    }
    
    public Proveedor BuscarProveedor(String identidad) {
        Proveedor pr = new Proveedor();
        String sql = "SELECT * FROM proveedor WHERE ruc = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, identidad);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr.setId(rs.getInt("id"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getString("telefono"));
                pr.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pr;
    }
}
