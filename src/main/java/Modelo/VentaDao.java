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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class VentaDao {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    final String series1 = "ventas";
    final String series2 = "compras";

    public int IdVenta() {
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
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

    public int RegistrarVenta(Venta v) {
        String sql = "INSERT INTO ventas (cliente, vendedor, total, fecha, metodo, id_usuario) VALUES (?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getFecha());
            ps.setString(5, v.getMetodo());
            ps.setInt(6, v.getId_usuario());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }

    public int RegistrarDetalle(Detalle Dv) {
        String sql = "INSERT INTO detalle (id_pro, cantidad, precio, subTotal, id_venta) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setDouble(4, Dv.getSubTotal());
            ps.setInt(5, Dv.getId_detalle());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }

    public List Listarventas(String valorBusqueda, int desde, int porPagina, int id_usuario) {
        List<Venta> ListaVenta = new ArrayList();
        String sql = "SELECT v.*, c.nombre FROM ventas v INNER JOIN clientes c ON v.cliente = c.id WHERE v.id_usuario = ? ORDER BY v.id DESC LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT v.*, c.nombre FROM ventas v INNER JOIN clientes c ON v.cliente = c.id WHERE c.nombre LIKE '%" + valorBusqueda + "%' AND v.id_usuario = ? OR v.fecha LIKE '%" + valorBusqueda + "%' AND v.id_usuario = ? ORDER BY v.id DESC LIMIT " + desde + "," + porPagina;
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
                Venta vent = new Venta();
                vent.setId(rs.getInt("id"));
                vent.setNombre_cli(rs.getString("nombre"));
                vent.setVendedor(rs.getString("vendedor"));
                vent.setTotal(rs.getDouble("total"));
                vent.setFecha(rs.getString("fecha"));
                vent.setMetodo(rs.getString("metodo"));
                vent.setEstado(rs.getString("estado"));
                ListaVenta.add(vent);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaVenta;
    }

    public double totalVentas(String valorBusqueda, int id_usuario) {
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM ventas WHERE id_usuario = ?";
        String busqueda = "SELECT COUNT(*) AS total FROM ventas v INNER JOIN clientes c ON v.cliente = c.id WHERE c.nombre LIKE '%" + valorBusqueda + "%' AND v.id_usuario = ? OR v.fecha LIKE '%" + valorBusqueda + "%' AND v.id_usuario = ?";
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
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return total;
    }

    public List BuscarVenta(int id) {
        List<Detalle> lista = new ArrayList();        
        String sql = "SELECT * FROM detalle WHERE id_venta = ?";
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
    
    public boolean anular(int id) {
        String sql = "UPDATE ventas SET estado = ? WHERE id = ?";
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

    public boolean ModificarDatos(Config conf) {
        String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, conf.getRuc());
            ps.setString(2, conf.getNombre());
            ps.setString(3, conf.getTelefono());
            ps.setString(4, conf.getDireccion());
            ps.setString(5, conf.getMensaje());
            ps.setInt(6, conf.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public void reporteVenta(int idVenta) {
        Config conf;
        conf = BuscarDatos();
        String sql = "SELECT v.vendedor, v.total,v.fecha, c.dni, c.nombre, c.telefono, c.direccion FROM ventas v INNER JOIN clientes c ON v.cliente = c.id WHERE v.id = ?";
        JasperReport reporte;
        con = cn.getConnection();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Img/reporte.jpg"));
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/ReporteVenta.jasper"));
            Map parametro = new HashMap();

            //obtener datos del cliente
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            if (rs.next()) {
                parametro.put("nombreEmpresa", conf.getNombre());
                parametro.put("rucEmpresa", conf.getRuc());
                parametro.put("telefonoEmpresa", conf.getTelefono());
                parametro.put("direccionEmpresa", conf.getDireccion());
                parametro.put("fecha", rs.getString("fecha"));
                parametro.put("serie", "000000" + idVenta);
                parametro.put("docCliente", rs.getString("dni"));
                parametro.put("telefonoCliente", rs.getString("telefono"));
                parametro.put("nombreCliente", rs.getString("nombre"));
                parametro.put("direccionCliente", rs.getString("direccion"));
                parametro.put("logoEmpresa", icon.getImage());
                parametro.put("totalPagar", rs.getString("total"));
                parametro.put("id", idVenta);
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

    public void reporteProductos() {
        Config conf;
        conf = BuscarDatos();
        JasperReport reporte;
        con = cn.getConnection();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Img/logo.jpg"));
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/Productos.jasper"));
            Map parametro = new HashMap();
            parametro.put("logoEmpresa", icon.getImage());
            parametro.put("nombreEmpresa", conf.getNombre());
            JasperPrint print = JasperFillManager.fillReport(reporte, parametro, con);
            JasperViewer view = new JasperViewer(print, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void reporteGrafico(JPanel panel, String anio, int id_usuario) {
        String desde = "01-01-" + anio;
        String hasta = "31-12-" + anio;
        try {
            con = cn.getConnection();
            String sql1 = "SELECT SUM(IF(MONTH(fecha) = 1, total, 0)) AS ene, SUM(IF(MONTH(fecha) = 2, total, 0)) AS feb, SUM(IF(MONTH(fecha) = 3, total, 0)) AS mar, SUM(IF(MONTH(fecha) = 4, total, 0)) AS abr, SUM(IF(MONTH(fecha) = 5, total, 0)) AS may, SUM(IF(MONTH(fecha) = 6, total, 0)) AS jun, SUM(IF(MONTH(fecha) = 7, total, 0)) AS jul, SUM(IF(MONTH(fecha) = 8, total, 0)) AS ago, SUM(IF(MONTH(fecha) = 9, total, 0)) AS sep, SUM(IF(MONTH(fecha) = 10, total, 0)) AS oct, SUM(IF(MONTH(fecha) = 11, total, 0)) AS nov, SUM(IF(MONTH(fecha) = 12, total, 0)) AS dic FROM ventas WHERE fecha BETWEEN ? AND ? AND estado = ? AND id_usuario = ?";
            ps = con.prepareStatement(sql1);
            ps.setString(1, desde);
            ps.setString(2, hasta);
            ps.setString(3, "COMPLETADO");
            ps.setInt(4, id_usuario);
            rs = ps.executeQuery();
            DefaultCategoryDataset ds = new DefaultCategoryDataset();
            if (rs.next()) {
                ds.addValue(rs.getInt("ene"), series1, "Ene");
                ds.addValue(rs.getInt("feb"), series1, "Feb");
                ds.addValue(rs.getInt("mar"), series1, "Mar");
                ds.addValue(rs.getInt("abr"), series1, "Abr");
                ds.addValue(rs.getInt("may"), series1, "May");
                ds.addValue(rs.getInt("jun"), series1, "Jun");
                ds.addValue(rs.getInt("jul"), series1, "Jul");
                ds.addValue(rs.getInt("ago"), series1, "Ago");
                ds.addValue(rs.getInt("sep"), series1, "Sep");
                ds.addValue(rs.getInt("oct"), series1, "Oct");
                ds.addValue(rs.getInt("nov"), series1, "Nov");
                ds.addValue(rs.getInt("dic"), series1, "Dic");
            }
            String sql2 = "SELECT SUM(IF(MONTH(fecha) = 1, total, 0)) AS ene, SUM(IF(MONTH(fecha) = 2, total, 0)) AS feb, SUM(IF(MONTH(fecha) = 3, total, 0)) AS mar, SUM(IF(MONTH(fecha) = 4, total, 0)) AS abr, SUM(IF(MONTH(fecha) = 5, total, 0)) AS may, SUM(IF(MONTH(fecha) = 6, total, 0)) AS jun, SUM(IF(MONTH(fecha) = 7, total, 0)) AS jul, SUM(IF(MONTH(fecha) = 8, total, 0)) AS ago, SUM(IF(MONTH(fecha) = 9, total, 0)) AS sep, SUM(IF(MONTH(fecha) = 10, total, 0)) AS oct, SUM(IF(MONTH(fecha) = 11, total, 0)) AS nov, SUM(IF(MONTH(fecha) = 12, total, 0)) AS dic FROM compras WHERE fecha BETWEEN ? AND ? AND estado = ? AND id_usuario = ?";
            ps = con.prepareStatement(sql2);
            ps.setString(1, desde);
            ps.setString(2, hasta);
            ps.setString(3, "COMPLETADO");
            ps.setInt(4, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                ds.addValue(rs.getInt("ene"), series2, "Ene");
                ds.addValue(rs.getInt("feb"), series2, "Feb");
                ds.addValue(rs.getInt("mar"), series2, "Mar");
                ds.addValue(rs.getInt("abr"), series2, "Abr");
                ds.addValue(rs.getInt("may"), series2, "May");
                ds.addValue(rs.getInt("jun"), series2, "Jun");
                ds.addValue(rs.getInt("jul"), series2, "Jul");
                ds.addValue(rs.getInt("ago"), series2, "Ago");
                ds.addValue(rs.getInt("sep"), series2, "Sep");
                ds.addValue(rs.getInt("oct"), series2, "Oct");
                ds.addValue(rs.getInt("nov"), series2, "Nov");
                ds.addValue(rs.getInt("dic"), series2, "Dic");
            }
            JFreeChart jf = ChartFactory.createBarChart3D("COMPARACIÓN DE VENTAS Y COMPRAS POR MES", "", "Total", ds, PlotOrientation.VERTICAL, true, true, false);
            ChartPanel f = new ChartPanel(jf);
            f.setSize(1230, 400);
            panel.add(f);

        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.toString());
        }
    }

    public boolean comprobarCaja(int id_usuario) {
        String consulta = "SELECT * FROM cajas WHERE estado = ? AND id_usuario = ?";
        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, "ABIERTO");
            ps.setInt(2, id_usuario);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public String abrirCaja(double monto, int id_usuario) {
        String consulta = "SELECT * FROM cajas WHERE estado = ? AND id_usuario = ?";
        String sql = "INSERT INTO cajas (monto_inicial, id_usuario) VALUES (?,?)";
        try {
            ps = con.prepareStatement(consulta);
            ps.setString(1, "ABIERTO");
            ps.setInt(2, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                return "existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setDouble(1, monto);
                ps.setDouble(2, id_usuario);
                ps.execute();
                return "registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "error";
        }
    }

    public List ListarCajas(String valorBusqueda, int desde, int porPagina) {
        List<Cajas> Lista = new ArrayList();
        String sql = "SELECT c.*, u.nombre FROM cajas c INNER JOIN usuarios u ON c.id_usuario = u.id ORDER BY c.id DESC LIMIT " + desde + "," + porPagina;
        String busqueda = "SELECT c.*, u.nombre FROM cajas c INNER JOIN usuarios u ON c.id_usuario = u.id WHERE u.nombre LIKE '%" + valorBusqueda + "%' OR c.f_apertura LIKE '%" + valorBusqueda + "%' ORDER BY c.id DESC LIMIT " + desde + "," + porPagina;
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Cajas cj = new Cajas();
                cj.setId(rs.getInt("id"));
                cj.setMonto_incial(rs.getDouble("monto_inicial"));
                cj.setMonto_final(rs.getDouble("monto_final"));
                cj.setF_apertura(rs.getString("f_apertura"));
                cj.setF_cierre(rs.getString("f_cierre"));
                cj.setEstado(rs.getString("estado"));
                cj.setUsuario(rs.getString("nombre"));
                Lista.add(cj);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Lista;
    }

    public double totalCajas(String valorBusqueda, int id_usuario) {
        double total = 0.00;
        String sql = "SELECT COUNT(*) AS total FROM cajas WHERE id_usuario = ?";
        String busqueda = "SELECT COUNT(*) AS total FROM cajas c INNER JOIN usuarios u ON c.id_usuario = u.id WHERE c.f_apertura LIKE '%" + valorBusqueda + "%' AND c.id_usuario = ? OR u.nombre LIKE '%" + valorBusqueda + "%' AND c.id_usuario = ?";
        try {
            if (valorBusqueda.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(busqueda);
            }
            ps.setInt(1, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return total;
    }

    public double totalIngreso(int id_usuario) {
        double total = 0.00;
        String sql = "SELECT SUM(total) AS total FROM ventas WHERE metodo = ? AND caja = ? AND estado = ? AND id_usuario = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "CONTADO");
            ps.setInt(2, 1);
            ps.setString(3, "COMPLETADO");
            ps.setInt(4, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return total;
    }

    public double totalAbono(int id_usuario) {
        double total = 0.00;
        String sql = "SELECT SUM(monto) AS total FROM abo_ventas WHERE caja = ? AND id_usuario = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, 1);
            ps.setInt(2, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("error");
        }
        return total;
    }

    public boolean cerrarCaja(Cajas cj) {
        String sql = "UPDATE cajas SET monto_final=?, f_cierre=?, estado=? WHERE estado = ? AND id_usuario = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, cj.getMonto_final());
            ps.setString(2, cj.getF_cierre());
            ps.setString(3, cj.getEstado());
            ps.setString(4, "ABIERTO");
            ps.setInt(5, cj.getId_usuario());
            ps.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean cambiarEstado(String table, int id_usuario) {
        String sql = "UPDATE " + table + " SET caja=? WHERE caja = ? AND id_usuario = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, 0);
            ps.setInt(2, 1);
            ps.setInt(3, id_usuario);
            ps.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
