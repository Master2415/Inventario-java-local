package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ComprasDao {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public int IdCompra() {
        int id = 0;
        String sql = "SELECT MAX(id) FROM compras";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }

    public int RegistrarCompra(Compras c) {
        String sql = "INSERT INTO compras (total, fecha, id_proveedor, id_usuario) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, c.getTotal());
            ps.setString(2, c.getFecha());
            ps.setInt(3, c.getId_proveedor());
            ps.setInt(4, c.getId_usuario());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }

    public int RegistrarDetalle(Detalle Dv) {
        String sql = "INSERT INTO detalle_compras (cantidad, precio, subTotal, id_pro, id_compra) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getCantidad());
            ps.setDouble(2, Dv.getPrecio());
            ps.setDouble(3, (Dv.getCantidad() * Dv.getPrecio()));
            ps.setInt(4, Dv.getId_pro());
            ps.setInt(5, Dv.getId_detalle());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }

    public List ListarCompras(String valorBusqueda, int desde, int porPagina, int id_usuario) {
        List<Compras> Lista = new ArrayList();
        String sql = "SELECT c.*, p.nombre FROM compras c INNER JOIN proveedor p ON c.id_proveedor = p.id WHERE c.id_usuario = ? ORDER BY c.id DESC LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT c.*, p.nombre FROM compras c INNER JOIN proveedor p ON c.id_proveedor = p.id WHERE p.nombre LIKE '%" + valorBusqueda + "%' AND c.id_usuario = ? OR c.fecha LIKE '%" + valorBusqueda + "%' AND c.id_usuario = ? ORDER BY c.id DESC LIMIT " + desde + "," + porPagina;
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, id_usuario);
            } else {
                ps = con.prepareStatement(busqueda);
                ps.setInt(1, id_usuario);
                ps.setInt(2, id_usuario);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Compras comp = new Compras();
                comp.setId(rs.getInt("id"));
                comp.setNombre_pro(rs.getString("nombre"));
                comp.setTotal(rs.getDouble("total"));
                comp.setFecha(rs.getString("fecha"));
                comp.setEstado(rs.getString("estado"));
                Lista.add(comp);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    public double totalCompras(String valorBusqueda, int id_usuario) {
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM compras WHERE id_usuario = ?";
        String busqueda = "SELECT COUNT(*) AS total FROM compras c INNER JOIN proveedor p ON c.id_proveedor = p.id WHERE p.nombre LIKE '%" + valorBusqueda + "%' AND c.id_usuario = ? OR c.fecha LIKE '%" + valorBusqueda + "%' AND c.id_usuario = ?";
        try {
            con = cn.getConnection();
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, id_usuario);
            } else {
                ps = con.prepareStatement(busqueda);
                ps.setInt(1, id_usuario);
                ps.setInt(2, id_usuario);
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

    public List BuscarCompra(int id) {
        List<Detalle> lista = new ArrayList();        
        String sql = "SELECT * FROM detalle_compras WHERE id_compra = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Detalle temp = new Detalle();
                temp.setCantidad(rs.getInt("cantidad"));
                temp.setId_pro(rs.getInt("id_pro"));
                lista.add(temp);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }

    public Config BuscarDatos() {
        Config conf = new Config();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getString("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setMensaje(rs.getString("mensaje"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    public void reporteCompra(int idCompra) {
        Config conf;
        conf = BuscarDatos();
        String sql = "SELECT c.total, c.fecha, p.ruc, p.nombre, p.telefono, p.direccion FROM compras c INNER JOIN proveedor p ON c.id_proveedor = p.id WHERE c.id = ?";
        JasperReport reporte;
        con = cn.getConnection();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Img/reporte.jpg"));
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/ReporteCompra.jasper"));
            Map parametro = new HashMap();

            //obtener datos del proveedor
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCompra);
            rs = ps.executeQuery();
            if (rs.next()) {
                parametro.put("nombreEmpresa", conf.getNombre());
                parametro.put("rucEmpresa", conf.getRuc());
                parametro.put("telefonoEmpresa", conf.getTelefono());
                parametro.put("direccionEmpresa", conf.getDireccion());
                parametro.put("fecha", rs.getString("fecha"));
                parametro.put("serie", "" + idCompra);
                parametro.put("docProveedor", rs.getString("ruc"));
                parametro.put("telefonoProveedor", rs.getString("telefono"));
                parametro.put("nombreProveedor", rs.getString("nombre"));
                parametro.put("direccionProveedor", rs.getString("direccion"));
                parametro.put("logoEmpresa", icon.getImage());
                parametro.put("totalPagar", rs.getString("total"));
                parametro.put("id", idCompra);
            }
            JasperPrint print = JasperFillManager.fillReport(reporte, parametro, con);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean anular(int id) {
        String sql = "UPDATE compras SET estado = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "ANULADO");
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
