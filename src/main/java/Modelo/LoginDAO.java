package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LoginDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();

    public Login validar(String correo, String pass) {
        Login l = new Login();
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND pass = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }

    public String Registrar(Login reg) {
        String consulta = "SELECT * FROM usuarios WHERE correo = ?";
        String sql = "INSERT INTO usuarios (nombre, correo, pass, rol) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(consulta);
            ps.setString(1, reg.getCorreo());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, reg.getNombre());
                ps.setString(2, reg.getCorreo());
                ps.setString(3, reg.getPass());
                ps.setString(4, reg.getRol());
                ps.execute();
                return "registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }

    public String Modificar(Login reg) {
        String consulta = "SELECT * FROM usuarios WHERE correo = ? AND id != ?";
        String sql = "UPDATE usuarios SET nombre=?, correo=?, rol=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(consulta);
            ps.setString(1, reg.getCorreo());
            ps.setInt(2, reg.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, reg.getNombre());
                ps.setString(2, reg.getCorreo());
                ps.setString(3, reg.getRol());
                ps.setInt(4, reg.getId());
                ps.execute();
                return "modificado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }

    public List ListarUsuarios(String valorBusqueda) {
        List<Login> Lista = new ArrayList();
        String sql = "SELECT * FROM usuarios";
        String busqueda = "SELECT * FROM usuarios WHERE nombre LIKE '%" + valorBusqueda + "%' OR correo LIKE '%" + valorBusqueda + "%'";
        try {
            con = cn.getConnection();
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Login lg = new Login();
                lg.setId(rs.getInt("id"));
                lg.setNombre(rs.getString("nombre"));
                lg.setCorreo(rs.getString("correo"));
                lg.setRol(rs.getString("rol"));
                Lista.add(lg);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    public boolean cerrarConexion() {
        try {
            con.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL CERRAR LA CONEXION");
            return false;
        }
    }
}
