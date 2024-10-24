/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Cajas;
import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.Combo;
import Modelo.Compras;
import Modelo.ComprasDao;
import Modelo.Config;
import Modelo.CredVentasDao;
import Modelo.Credventas;
import Modelo.Detalle;
import Modelo.Eventos;
import Modelo.LoginDAO;
import Modelo.Productos;
import Modelo.ProductosDao;
import Modelo.Proveedor;
import Modelo.ProveedorDao;
import Modelo.Venta;
import Modelo.VentaDao;
import Modelo.Login;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public final class Sistema extends javax.swing.JFrame {

    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(fechaVenta);
    String anio = new SimpleDateFormat("yyyy").format(fechaVenta);
    Cliente cl = new Cliente();
    ClienteDao client = new ClienteDao();
    Proveedor pr = new Proveedor();
    ProveedorDao PrDao = new ProveedorDao();
    Productos pro = new Productos();
    ProductosDao proDao = new ProductosDao();
    Venta v = new Venta();
    VentaDao Vdao = new VentaDao();
    Detalle Dv = new Detalle();
    Config conf = new Config();
    Eventos event = new Eventos();
    Login lg = new Login();
    LoginDAO login = new LoginDAO();

    Credventas cred = new Credventas();
    CredVentasDao creddao = new CredVentasDao();
    Compras comp = new Compras();
    ComprasDao compDao = new ComprasDao();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    DefaultTableModel modelo1 = new DefaultTableModel();
    int item;
    double Totalpagar = 0.00;
    String porPagina = "25";

    public Sistema() {
    }

    public Sistema(Login priv) {
        initComponents();
        this.setLocationRelativeTo(null);
        txtIdCliente.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdproducto.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdConfig.setVisible(false);
        txtIdCV.setVisible(false);
        txtIdUser.setVisible(false);
        txtIdPC.setVisible(false);
        id_usuario.setVisible(false);
        txtIdCompras.setVisible(false);
        txtMontoRestante.setVisible(false);
        txtIdCredVenta.setVisible(false);
        ListarConfig();
        if (priv.getRol().equals("Asistente")) {
            btnProductos.setEnabled(false);
            btnProveedor.setEnabled(false);
            btnConfig.setEnabled(false);
            btnUsuarios.setEnabled(false);
        }
        id_usuario.setText("" + priv.getId());
        LabelVendedor.setText(priv.getNombre());
        paneControl.setEnabled(false);
        page.setVisible(false);
        mostrarTotales();
        generarSerie();
    }

    private void productosVentas(JTable tabla, JButton ant, JButton sig, JLabel pag, String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = proDao.totalProductos(valorBusqueda);
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));

        List<Productos> ListarPro = proDao.ListarProductos(valorBusqueda, desde, Integer.parseInt(cantidad));
        modelo = (DefaultTableModel) tabla.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getNombre();
            ob[2] = ListarPro.get(i).getPrecio();
            ob[3] = ListarPro.get(i).getStock();
            modelo.addRow(ob);
        }
        tabla.setModel(modelo);
        colorTable(tabla);
        pagination(ant, sig, pag, pagina, totalPage);
    }

    private void ListarCliente(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = client.totalCliente(valorBusqueda);
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));

        List<Cliente> ListarCl = client.ListarCliente(valorBusqueda, desde, Integer.parseInt(cantidad));
        modelo = (DefaultTableModel) TableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getDni();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getTelefono();
            ob[4] = ListarCl.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableCliente.setModel(modelo);
        colorTable(TableCliente);
        pagination(btnAnteriorCliente, btnSiguienteCliente, paginationCliente, pagina, totalPage);

    }

    private void pagination(JButton anterior, JButton siguiente, JLabel info, int pagina, int totalPage) {
        anterior.setEnabled(true);
        siguiente.setEnabled(true);
        if (pagina <= 1) {
            anterior.setEnabled(false);
        }
        if (pagina >= totalPage) {
            siguiente.setEnabled(false);
        }
        info.setText("Página " + pagina + " de un Total " + totalPage + " Páginas");
    }

    private void ListarProveedor(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = PrDao.totalProveedor(valorBusqueda);
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));

        List<Proveedor> ListarPr = PrDao.ListarProveedor(valorBusqueda, desde, Integer.parseInt(cantidad));
        modelo = (DefaultTableModel) TableProveedor.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRuc();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);
        colorTable(TableProveedor);
        pagination(btnAnteriorProveedor, btnSiguienteProveedor, paginationProveedor, pagina, totalPage);
    }

    private void ListarUsuarios() {
        List<Login> Listar = login.ListarUsuarios(txtBuscarUser.getText());
        modelo = (DefaultTableModel) TableUsuarios.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getCorreo();
            ob[3] = Listar.get(i).getRol();
            modelo.addRow(ob);
        }
        TableUsuarios.setModel(modelo);
        colorTable(TableUsuarios);
    }

    private void ListarProductos(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = proDao.totalProductos(valorBusqueda);
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));

        List<Productos> ListarPro = proDao.ListarProductos(valorBusqueda, desde, Integer.parseInt(cantidad));
        modelo = (DefaultTableModel) TableProducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedorPro();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            modelo.addRow(ob);
        }
        TableProducto.setModel(modelo);
        colorTable(TableProducto);
        pagination(btnAnteriorProducto, btnSiguienteProducto, paginationProducto, pagina, totalPage);
    }

    private void ListarConfig() {
        conf = Vdao.BuscarDatos();
        txtIdConfig.setText("" + conf.getId());
        txtRucConfig.setText("" + conf.getRuc());
        txtNombreConfig.setText("" + conf.getNombre());
        txtTelefonoConfig.setText("" + conf.getTelefono());
        txtDireccionConfig.setText("" + conf.getDireccion());
        txtMensaje.setText("" + conf.getMensaje());
    }

    private void ListarVentas(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = Vdao.totalVentas(valorBusqueda, Integer.parseInt(id_usuario.getText()));
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));

        List<Venta> ListarVenta = Vdao.Listarventas(valorBusqueda, desde, Integer.parseInt(cantidad), Integer.parseInt(id_usuario.getText()));

        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getNombre_cli();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();
            ob[4] = ListarVenta.get(i).getFecha();
            ob[5] = ListarVenta.get(i).getMetodo();
            ob[6] = ListarVenta.get(i).getEstado();
            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);
        colorTable(TableVentas);
        pagination(btnAnteriorVentas, btnSiguienteVentas, paginationVentas, pagina, totalPage);
    }

    private void ListarCredVentas(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = creddao.totalCreditos(valorBusqueda);
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));

        List<Credventas> Listar = creddao.Listarventas(valorBusqueda, desde, Integer.parseInt(cantidad));

        modelo = (DefaultTableModel) tableCredVenta.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre_cli();
            ob[2] = Listar.get(i).getVendedor();
            ob[3] = Listar.get(i).getAbonado();
            ob[4] = Math.floor(Listar.get(i).getRestante());
            ob[5] = Listar.get(i).getTotal();
            ob[6] = Listar.get(i).getFecha();
            modelo.addRow(ob);
        }
        tableCredVenta.setModel(modelo);
        colorTable(tableCredVenta);
        pagination(btnAnteriorCredVenta, btnSiguienteCredVenta, paginationCredVenta, pagina, totalPage);

    }

    private void ListarCajas(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = Vdao.totalCajas(valorBusqueda, Integer.parseInt(id_usuario.getText()));
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));

        List<Cajas> Listar = Vdao.ListarCajas(valorBusqueda, desde, Integer.parseInt(cantidad));

        modelo = (DefaultTableModel) TableCajas.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getMonto_incial();
            ob[2] = Listar.get(i).getMonto_final();
            ob[3] = Listar.get(i).getF_apertura();
            ob[4] = Listar.get(i).getF_cierre();
            ob[5] = Listar.get(i).getUsuario();
            ob[6] = Listar.get(i).getEstado();
            modelo.addRow(ob);
        }
        TableCajas.setModel(modelo);
        colorTable(TableCajas);
        pagination(btnAnteriorCaja, btnSiguienteCaja, paginationCaja, pagina, totalPage);

    }

    private void ListarCompras(String valorBusqueda, int pagina, String cantidad) {
        double totalRegistro = compDao.totalCompras(valorBusqueda, Integer.parseInt(id_usuario.getText()));
        int desde = (pagina - 1) * Integer.parseInt(cantidad);
        int totalPage = (int) Math.ceil(totalRegistro / Double.parseDouble(cantidad));

        List<Compras> Listar = compDao.ListarCompras(valorBusqueda, desde, Integer.parseInt(cantidad), Integer.parseInt(id_usuario.getText()));

        modelo = (DefaultTableModel) TableCompras.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre_pro();
            ob[2] = Listar.get(i).getTotal();
            ob[3] = Listar.get(i).getFecha();
            ob[4] = Listar.get(i).getEstado();
            modelo.addRow(ob);
        }
        TableCompras.setModel(modelo);
        colorTable(TableCompras);
        pagination(btnAnteriorCompras, btnSiguienteCompras, paginationCompras, pagina, totalPage);
    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel38 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnCompras = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        tipo = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        btnTablero = new javax.swing.JButton();
        LabelVendedor = new javax.swing.JLabel();
        btnCredVentas = new javax.swing.JButton();
        btnCajas = new javax.swing.JButton();
        paneControl = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        btnEliminarventa = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        txtBuscarTemp = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnAnteriorTemp = new javax.swing.JButton();
        btnSiguienteTemp = new javax.swing.JButton();
        paginationTemp = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtRucVenta = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtNombreClienteventa = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        cbxMetodo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtIdCV = new javax.swing.JTextField();
        txtSerie = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        btnGenerarVenta = new javax.swing.JButton();
        btnHistorialVenta = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDirecionCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        btnSiguienteCliente = new javax.swing.JButton();
        btnAnteriorCliente = new javax.swing.JButton();
        paginationCliente = new javax.swing.JLabel();
        txtBuscarCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNombreproveedor = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        btnguardarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        paginationProveedor = new javax.swing.JLabel();
        btnAnteriorProveedor = new javax.swing.JButton();
        btnSiguienteProveedor = new javax.swing.JButton();
        txtBuscarProveedor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtDesPro = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtCantPro = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtPrecioPro = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        cbxProveedorPro = new javax.swing.JComboBox<>();
        btnGuardarpro = new javax.swing.JButton();
        btnEditarpro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnNuevoPro = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        txtIdproducto = new javax.swing.JTextField();
        txtBuscarProducto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        paginationProducto = new javax.swing.JLabel();
        btnAnteriorProducto = new javax.swing.JButton();
        btnSiguienteProducto = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtBuscarVentas = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        paginationVentas = new javax.swing.JLabel();
        btnAnteriorVentas = new javax.swing.JButton();
        btnSiguienteVentas = new javax.swing.JButton();
        btnAnularVenta = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtMensaje = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtNombreConfig = new javax.swing.JTextField();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        page = new javax.swing.JTextField();
        id_usuario = new javax.swing.JTextField();
        txtIdConfig = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnIniciar = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        btnModificarUser = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableUsuarios = new javax.swing.JTable();
        txtBuscarUser = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIdUser = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        panelGrafico = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        txtCodigoCompra = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        TableCompra = new javax.swing.JTable();
        btnEliminarCompra = new javax.swing.JButton();
        jPanel54 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblProductosCompra = new javax.swing.JTable();
        txtBuscarTempCompra = new javax.swing.JTextField();
        btnAgregarCompra = new javax.swing.JButton();
        btnAnteriorTempCompra = new javax.swing.JButton();
        btnSiguienteTempCompra = new javax.swing.JButton();
        paginationTempCompra = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        txtIdentidadProveedor = new javax.swing.JTextField();
        jPanel46 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        txtProveedorCompra = new javax.swing.JTextField();
        jPanel47 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        LabelTotalCompra = new javax.swing.JLabel();
        txtIdPC = new javax.swing.JTextField();
        btnGenerarCompra = new javax.swing.JButton();
        btnHistorialCompras = new javax.swing.JButton();
        jPanel58 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        TableCompras = new javax.swing.JTable();
        btnPdfCompras = new javax.swing.JButton();
        txtIdCompras = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtBuscarCompras = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        paginationCompras = new javax.swing.JLabel();
        btnAnteriorCompras = new javax.swing.JButton();
        btnSiguienteCompras = new javax.swing.JButton();
        btnAnularCompra = new javax.swing.JButton();
        jPanel59 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        TableCajas = new javax.swing.JTable();
        jPanel60 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        txtMontoIncial = new javax.swing.JTextField();
        btnGuardarCaja = new javax.swing.JButton();
        btnEditarCaja = new javax.swing.JButton();
        jPanel61 = new javax.swing.JPanel();
        btnSiguienteCaja = new javax.swing.JButton();
        btnAnteriorCaja = new javax.swing.JButton();
        paginationCaja = new javax.swing.JLabel();
        txtBuscarCaja = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableCredVenta = new javax.swing.JTable();
        txtIdCredVenta = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtBuscarCredVenta = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        paginationCredVenta = new javax.swing.JLabel();
        btnAnteriorCredVenta = new javax.swing.JButton();
        btnSiguienteCredVenta = new javax.swing.JButton();
        jPanel68 = new javax.swing.JPanel();
        btnAbonar = new javax.swing.JButton();
        txtMontoAbono = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        txtMontoRestante = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Panel de Adminstración");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/template.png"))); // NOI18N
        getContentPane().add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 1320, 120));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/mini-logo.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 120));

        btnNuevaVenta.setBackground(new java.awt.Color(0, 0, 0));
        btnNuevaVenta.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnNuevaVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/venta.png"))); // NOI18N
        btnNuevaVenta.setText("Ventas");
        btnNuevaVenta.setBorder(null);
        btnNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevaVenta.setFocusable(false);
        btnNuevaVenta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 150, 45));

        btnClientes.setBackground(new java.awt.Color(0, 0, 0));
        btnClientes.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setBorder(null);
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.setFocusable(false);
        btnClientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        jPanel1.add(btnClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 150, 45));

        btnProveedor.setBackground(new java.awt.Color(0, 0, 0));
        btnProveedor.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedor");
        btnProveedor.setBorder(null);
        btnProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProveedor.setFocusable(false);
        btnProveedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });
        jPanel1.add(btnProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 150, 45));

        btnProductos.setBackground(new java.awt.Color(0, 0, 0));
        btnProductos.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/producto.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setBorder(null);
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductos.setFocusable(false);
        btnProductos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductosMouseClicked(evt);
            }
        });
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        jPanel1.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 150, 45));

        btnCompras.setBackground(new java.awt.Color(0, 0, 0));
        btnCompras.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnCompras.setForeground(new java.awt.Color(255, 255, 255));
        btnCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/compra.png"))); // NOI18N
        btnCompras.setText("Compras");
        btnCompras.setBorder(null);
        btnCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCompras.setFocusable(false);
        btnCompras.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });
        jPanel1.add(btnCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 150, 45));

        btnConfig.setBackground(new java.awt.Color(0, 0, 0));
        btnConfig.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnConfig.setForeground(new java.awt.Color(255, 255, 255));
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/config.png"))); // NOI18N
        btnConfig.setText("Config");
        btnConfig.setBorder(null);
        btnConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfig.setFocusable(false);
        btnConfig.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });
        jPanel1.add(btnConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 150, 45));

        tipo.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 209, -1, -1));

        btnUsuarios.setBackground(new java.awt.Color(0, 0, 0));
        btnUsuarios.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/user.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.setBorder(null);
        btnUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuarios.setFocusable(false);
        btnUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        jPanel1.add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 610, 150, 45));

        btnTablero.setBackground(new java.awt.Color(0, 0, 0));
        btnTablero.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTablero.setForeground(new java.awt.Color(255, 255, 255));
        btnTablero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/tablero.png"))); // NOI18N
        btnTablero.setText("Tablero");
        btnTablero.setBorder(null);
        btnTablero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTablero.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTablero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableroActionPerformed(evt);
            }
        });
        jPanel1.add(btnTablero, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 157, 150, 45));

        LabelVendedor.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        LabelVendedor.setForeground(new java.awt.Color(255, 255, 255));
        LabelVendedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelVendedor.setText("Administrador");
        jPanel1.add(LabelVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 127, 200, -1));

        btnCredVentas.setBackground(new java.awt.Color(0, 0, 0));
        btnCredVentas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnCredVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnCredVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/credito.png"))); // NOI18N
        btnCredVentas.setText("Creditos");
        btnCredVentas.setBorder(null);
        btnCredVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCredVentas.setFocusable(false);
        btnCredVentas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCredVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCredVentasActionPerformed(evt);
            }
        });
        jPanel1.add(btnCredVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 150, 45));

        btnCajas.setBackground(new java.awt.Color(0, 0, 0));
        btnCajas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnCajas.setForeground(new java.awt.Color(255, 255, 255));
        btnCajas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/caja.png"))); // NOI18N
        btnCajas.setText("Cajas");
        btnCajas.setBorder(null);
        btnCajas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCajas.setFocusable(false);
        btnCajas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajasActionPerformed(evt);
            }
        });
        jPanel1.add(btnCajas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 150, 45));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 690));

        paneControl.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/barcode.png"))); // NOI18N
        jLabel3.setText("BarCode - Enter");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 190, 30));

        txtCodigoVenta.setBorder(null);
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
        });
        jPanel2.add(txtCodigoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 300, 30));

        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Descripción", "Cant.", "Precio", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVenta.setRowHeight(23);
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setMinWidth(60);
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(60);
            TableVenta.getColumnModel().getColumn(0).setMaxWidth(100);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVenta.getColumnModel().getColumn(2).setMinWidth(60);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableVenta.getColumnModel().getColumn(2).setMaxWidth(100);
            TableVenta.getColumnModel().getColumn(3).setMinWidth(120);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(80);
            TableVenta.getColumnModel().getColumn(3).setMaxWidth(120);
            TableVenta.getColumnModel().getColumn(4).setMinWidth(120);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableVenta.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 101, 590, 250));

        btnEliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarventa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarventaActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminarventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 50, 45));

        jPanel14.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 2));

        jPanel19.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 300, 2));

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Producto", "Precio", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductos.setRowHeight(23);
        jScrollPane7.setViewportView(tblProductos);
        if (tblProductos.getColumnModel().getColumnCount() > 0) {
            tblProductos.getColumnModel().getColumn(0).setMinWidth(50);
            tblProductos.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblProductos.getColumnModel().getColumn(0).setMaxWidth(50);
            tblProductos.getColumnModel().getColumn(2).setMinWidth(100);
            tblProductos.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblProductos.getColumnModel().getColumn(2).setMaxWidth(100);
            tblProductos.getColumnModel().getColumn(3).setMinWidth(50);
            tblProductos.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblProductos.getColumnModel().getColumn(3).setMaxWidth(50);
        }

        jPanel2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, 670, 380));

        txtBuscarTemp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarTempKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscarTemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 40, 360, 40));

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/plus.png"))); // NOI18N
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1215, 40, 70, 40));

        btnAnteriorTemp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorTemp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorTemp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorTempActionPerformed(evt);
            }
        });
        jPanel2.add(btnAnteriorTemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 510, 80, 40));

        btnSiguienteTemp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteTemp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteTemp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteTempActionPerformed(evt);
            }
        });
        jPanel2.add(btnSiguienteTemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 510, 80, 40));

        paginationTemp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationTemp.setText("Página 1 de un Total 20 Páginas");
        jPanel2.add(paginationTemp, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 520, 340, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 40, 40));

        jPanel66.setBackground(new java.awt.Color(255, 255, 255));
        jPanel66.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de venta"));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Buscar cliente");

        txtRucVenta.setBorder(null);
        txtRucVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyTyped(evt);
            }
        });

        jPanel24.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("Nombre:");

        txtNombreClienteventa.setEditable(false);
        txtNombreClienteventa.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreClienteventa.setBorder(null);

        jPanel25.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jLabel61.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel61.setText("Metodo");

        cbxMetodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CONTADO", "CREDITO" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ingresos.png"))); // NOI18N
        jLabel10.setText("Total a Pagar:");

        LabelTotal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LabelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LabelTotal.setText("0.00");

        txtSerie.setEditable(false);

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel40.setText("Serie :");

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/print.png"))); // NOI18N
        btnGenerarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel66Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtIdCV, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel66Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel40)
                                .addGap(123, 123, 123))
                            .addGroup(jPanel66Layout.createSequentialGroup()
                                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel66Layout.createSequentialGroup()
                                        .addComponent(jLabel61)
                                        .addGap(111, 111, 111)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel66Layout.createSequentialGroup()
                                        .addComponent(cbxMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(100, 100, 100)
                                        .addComponent(LabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel66Layout.createSequentialGroup()
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel66Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel66Layout.createSequentialGroup()
                                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRucVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreClienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                                .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36))))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel66Layout.createSequentialGroup()
                .addComponent(txtIdCV, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRucVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreClienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel66Layout.createSequentialGroup()
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel66Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel61))
                            .addComponent(jLabel10))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 590, 190));

        btnHistorialVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/historial.png"))); // NOI18N
        btnHistorialVenta.setText("Historial ventas");
        btnHistorialVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHistorialVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialVentaActionPerformed(evt);
            }
        });
        jPanel2.add(btnHistorialVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 40, 160, 45));

        paneControl.addTab("VENTAS", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        TableCliente.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Dni/Ruc", "Nombre", "Teléfono", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableCliente.setRowHeight(23);
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(0).setMinWidth(70);
            TableCliente.getColumnModel().getColumn(0).setPreferredWidth(40);
            TableCliente.getColumnModel().getColumn(0).setMaxWidth(70);
            TableCliente.getColumnModel().getColumn(1).setMinWidth(150);
            TableCliente.getColumnModel().getColumn(1).setPreferredWidth(120);
            TableCliente.getColumnModel().getColumn(1).setMaxWidth(150);
            TableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCliente.getColumnModel().getColumn(3).setMinWidth(150);
            TableCliente.getColumnModel().getColumn(3).setPreferredWidth(120);
            TableCliente.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 1000, 450));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Cliente"));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Dni/Ruc:");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtDniCliente.setBorder(null);
        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });
        jPanel9.add(txtDniCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 160, 30));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Nombre:");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        txtNombreCliente.setBorder(null);
        jPanel9.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 160, 30));

        txtTelefonoCliente.setBorder(null);
        jPanel9.add(txtTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 160, 30));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Télefono:");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("Dirección:");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        txtDirecionCliente.setBorder(null);
        jPanel9.add(txtDirecionCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 203, 160, 30));

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 275, 100, 40));

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/editar.png"))); // NOI18N
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnEditarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 275, 90, 40));

        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnEliminarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 335, 100, 40));

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel9.add(btnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 335, 90, 40));

        jPanel26.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 160, 2));

        jPanel27.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 160, 2));

        jPanel28.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 160, 2));

        jPanel29.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 233, 160, 2));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 270, 410));

        btnSiguienteCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnSiguienteCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 510, 90, 40));

        btnAnteriorCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnAnteriorCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 510, 100, 40));

        paginationCliente.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationCliente.setText("Página 1 de un Total 20 Páginas");
        jPanel3.add(paginationCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 380, -1));

        txtBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyReleased(evt);
            }
        });
        jPanel3.add(txtBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 270, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jLabel4.setText("Buscar");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, -1));
        jPanel3.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 30, 20));

        paneControl.addTab("CLIENTES", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Ruc", "Nombre", "Teléfono", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableProveedor.setRowHeight(23);
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setMinWidth(50);
            TableProveedor.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableProveedor.getColumnModel().getColumn(0).setMaxWidth(50);
            TableProveedor.getColumnModel().getColumn(1).setMinWidth(120);
            TableProveedor.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableProveedor.getColumnModel().getColumn(1).setMaxWidth(120);
            TableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableProveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 57, 1000, 440));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Proveedor"));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel17.setText("Ruc:");
        jPanel10.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtRucProveedor.setBorder(null);
        jPanel10.add(txtRucProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 138, 30));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel18.setText("Nombre:");
        jPanel10.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        txtNombreproveedor.setBorder(null);
        jPanel10.add(txtNombreproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 138, 30));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel19.setText("Teléfono:");
        jPanel10.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        txtTelefonoProveedor.setBorder(null);
        jPanel10.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 138, 30));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel20.setText("Dirección:");
        jPanel10.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        txtDireccionProveedor.setBorder(null);
        jPanel10.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 138, 30));

        btnguardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnguardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnguardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarProveedorActionPerformed(evt);
            }
        });
        jPanel10.add(btnguardarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 70, 35));

        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/editar.png"))); // NOI18N
        btnEditarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });
        jPanel10.add(btnEditarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 70, 35));

        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });
        jPanel10.add(btnNuevoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 70, 35));

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jPanel10.add(btnEliminarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 70, 35));
        jPanel10.add(txtIdProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 24, -1));

        jPanel34.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 138, 2));

        jPanel35.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 138, 2));

        jPanel36.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 138, 2));

        jPanel37.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 138, 2));

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 260, 420));

        paginationProveedor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationProveedor.setText("Página 1 de un Total 20 Páginas");
        jPanel4.add(paginationProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 380, -1));

        btnAnteriorProveedor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnAnteriorProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 510, 100, 40));

        btnSiguienteProveedor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnSiguienteProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 510, 90, 40));

        txtBuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProveedorKeyReleased(evt);
            }
        });
        jPanel4.add(txtBuscarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 260, 30));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jLabel11.setText("Buscar");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, -1));

        paneControl.addTab("PROVEEDORES", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));

        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Código", "Descripción", "Proveedor", "Stock", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableProducto.setRowHeight(23);
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(0).setMinWidth(50);
            TableProducto.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableProducto.getColumnModel().getColumn(0).setMaxWidth(50);
            TableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProducto.getColumnModel().getColumn(3).setPreferredWidth(60);
            TableProducto.getColumnModel().getColumn(4).setMinWidth(70);
            TableProducto.getColumnModel().getColumn(4).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(4).setMaxWidth(70);
            TableProducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 960, 440));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Producto"));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel22.setText("Código:");
        jPanel11.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        txtCodigoPro.setBorder(null);
        jPanel11.add(txtCodigoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 180, 30));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel23.setText("Descripción:");
        jPanel11.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        txtDesPro.setBorder(null);
        jPanel11.add(txtDesPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 180, 30));

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel24.setText("Cantidad:");
        jPanel11.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        txtCantPro.setBorder(null);
        jPanel11.add(txtCantPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 180, 30));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel25.setText("Precio:");
        jPanel11.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        txtPrecioPro.setBorder(null);
        txtPrecioPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProKeyTyped(evt);
            }
        });
        jPanel11.add(txtPrecioPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 180, 30));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel26.setText("Proveedor:");
        jPanel11.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        cbxProveedorPro.setBorder(null);
        cbxProveedorPro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProveedorProItemStateChanged(evt);
            }
        });
        cbxProveedorPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProveedorProActionPerformed(evt);
            }
        });
        jPanel11.add(cbxProveedorPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 180, 30));

        btnGuardarpro.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        btnGuardarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnGuardarpro.setText("Registrar");
        btnGuardarpro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarproActionPerformed(evt);
            }
        });
        jPanel11.add(btnGuardarpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 325, 110, 40));

        btnEditarpro.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        btnEditarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/editar.png"))); // NOI18N
        btnEditarpro.setText("Editar");
        btnEditarpro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarproActionPerformed(evt);
            }
        });
        jPanel11.add(btnEditarpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 325, 110, 40));

        btnEliminarPro.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarPro.setText("Eliminar");
        btnEliminarPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });
        jPanel11.add(btnEliminarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 385, 110, 40));

        btnNuevoPro.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoPro.setText("Nuevo");
        btnNuevoPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });
        jPanel11.add(btnNuevoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 385, 110, 40));

        jPanel30.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 180, 2));

        jPanel31.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 180, 2));

        jPanel32.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 180, 2));

        jPanel33.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 180, 2));
        jPanel11.add(txtIdproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 20, -1));

        jPanel5.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 300, 440));

        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyReleased(evt);
            }
        });
        jPanel5.add(txtBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 250, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 50, 30));

        paginationProducto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationProducto.setText("Página 1 de un Total 20 Páginas");
        jPanel5.add(paginationProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 520, 380, -1));

        btnAnteriorProducto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorProductoActionPerformed(evt);
            }
        });
        jPanel5.add(btnAnteriorProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 510, 100, 40));

        btnSiguienteProducto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteProductoActionPerformed(evt);
            }
        });
        jPanel5.add(btnSiguienteProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 510, 90, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/reportes.png"))); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1127, 10, 150, 40));

        paneControl.addTab("PRODUCTOS", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));

        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cliente", "Vendedor", "Total", "Fecha", "Metodo", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVentas.setRowHeight(23);
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setMinWidth(50);
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableVentas.getColumnModel().getColumn(0).setMaxWidth(50);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(3).setMinWidth(120);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(80);
            TableVentas.getColumnModel().getColumn(3).setMaxWidth(120);
            TableVentas.getColumnModel().getColumn(4).setMinWidth(120);
            TableVentas.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableVentas.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 1240, 390));

        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/reportes.png"))); // NOI18N
        btnPdfVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });
        jPanel6.add(btnPdfVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 80, -1));
        jPanel6.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 70, 46, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Historial Ventas");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 140, -1));

        txtBuscarVentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarVentasKeyReleased(evt);
            }
        });
        jPanel6.add(txtBuscarVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 300, 40));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 40, 40));

        paginationVentas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationVentas.setText("Página 1 de un Total 20 Páginas");
        jPanel6.add(paginationVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 380, -1));

        btnAnteriorVentas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorVentasActionPerformed(evt);
            }
        });
        jPanel6.add(btnAnteriorVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 510, 100, 40));

        btnSiguienteVentas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteVentasActionPerformed(evt);
            }
        });
        jPanel6.add(btnSiguienteVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 510, 90, 40));

        btnAnularVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anular.png"))); // NOI18N
        btnAnularVenta.setText("Anular");
        btnAnularVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnularVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularVentaActionPerformed(evt);
            }
        });
        jPanel6.add(btnAnularVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 50, 110, 45));

        paneControl.addTab("HISTORIAL VENTAS", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel30.setText("Dirección");
        jPanel8.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel29.setText("Teléfono");
        jPanel8.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, -1, -1));

        txtTelefonoConfig.setBorder(null);
        jPanel8.add(txtTelefonoConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 218, 30));

        txtDireccionConfig.setBorder(null);
        jPanel8.add(txtDireccionConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 147, 30));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel31.setText("Mensaje");
        jPanel8.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        txtMensaje.setBorder(null);
        jPanel8.add(txtMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 400, 30));

        btnActualizarConfig.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizarConfig.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/editar.png"))); // NOI18N
        btnActualizarConfig.setText("Modificar");
        btnActualizarConfig.setBorder(null);
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });
        jPanel8.add(btnActualizarConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 400, 40));

        jLabel27.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel27.setText("Ruc");
        jPanel8.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        txtRucConfig.setBorder(null);
        jPanel8.add(txtRucConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 147, 30));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel28.setText("Nombre");
        jPanel8.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        txtNombreConfig.setBorder(null);
        jPanel8.add(txtNombreConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 220, 30));

        jPanel41.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 147, 2));

        jPanel42.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 147, 2));

        jPanel43.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 400, 2));

        jPanel44.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 220, 2));

        jPanel45.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 220, 2));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, 460, 480));

        page.setText("1");
        jPanel7.add(page, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, -1, -1));

        id_usuario.setText("1");
        jPanel7.add(id_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 30, 20, -1));
        jPanel7.add(txtIdConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 30, 20, -1));

        paneControl.addTab("CONFIGURACIÓN", jPanel7);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Usuario"));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel34.setText("Correo Electrónico");
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel35.setText("Password");
        jPanel13.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        txtCorreo.setBorder(null);
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel13.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 226, 30));

        txtPass.setBorder(null);
        jPanel13.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 226, 30));

        btnIniciar.setBackground(new java.awt.Color(102, 102, 102));
        btnIniciar.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnIniciar.setText("Registrar");
        btnIniciar.setBorder(null);
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });
        jPanel13.add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 335, 110, 40));

        jLabel36.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel36.setText("Nombre:");
        jPanel13.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        txtNombre.setBorder(null);
        jPanel13.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 226, 30));

        jLabel37.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel37.setText("Rol:");
        jPanel13.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Asistente" }));
        jPanel13.add(cbxRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 226, 30));

        jPanel15.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 226, 2));

        jPanel16.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 226, 2));

        jPanel17.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 226, 2));

        btnModificarUser.setBackground(new java.awt.Color(204, 204, 255));
        btnModificarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/editar.png"))); // NOI18N
        btnModificarUser.setText("Modificar");
        btnModificarUser.setBorder(null);
        btnModificarUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarUserActionPerformed(evt);
            }
        });
        jPanel13.add(btnModificarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 335, 100, 40));

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 280, 400));

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));

        TableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableUsuarios.setRowHeight(23);
        TableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableUsuariosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TableUsuarios);
        if (TableUsuarios.getColumnModel().getColumnCount() > 0) {
            TableUsuarios.getColumnModel().getColumn(0).setMinWidth(60);
            TableUsuarios.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableUsuarios.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jPanel12.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 980, 490));

        txtBuscarUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarUserKeyReleased(evt);
            }
        });
        jPanel12.add(txtBuscarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 280, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jLabel5.setText("Buscar");
        jPanel12.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 90, -1));
        jPanel12.add(txtIdUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 60, 60, -1));

        paneControl.addTab("USUARIOS", jPanel12);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelGrafico.setBorder(javax.swing.BorderFactory.createTitledBorder("Comparación de ventas por mes"));

        javax.swing.GroupLayout panelGraficoLayout = new javax.swing.GroupLayout(panelGrafico);
        panelGrafico.setLayout(panelGraficoLayout);
        panelGraficoLayout.setHorizontalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1218, Short.MAX_VALUE)
        );
        panelGraficoLayout.setVerticalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );

        jPanel18.add(panelGrafico, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 1230, 400));

        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        jLabel32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel20.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 80));

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("CLIENTES");
        jPanel20.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 100, -1));

        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("0");
        jPanel20.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 10, 80, -1));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/card1.jpg"))); // NOI18N
        jPanel20.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 80));

        jPanel18.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 180, 80));

        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        jLabel45.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel21.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 80));

        jLabel46.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel46.setText("PROVEEDORES");
        jPanel21.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 110, -1));

        jLabel55.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel55.setText("0");
        jPanel21.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 10, 80, -1));

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/card2.jpg"))); // NOI18N
        jPanel21.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 80));

        jPanel18.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 180, 80));

        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/producto.png"))); // NOI18N
        jLabel57.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel22.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 80));

        jLabel63.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel63.setText("PRODUCTOS");
        jPanel22.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 100, -1));

        jLabel64.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel64.setText("0");
        jPanel22.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 10, 80, -1));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/card3.jpg"))); // NOI18N
        jPanel22.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 80));

        jPanel18.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 180, 80));

        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/venta.png"))); // NOI18N
        jLabel66.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel39.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 80));

        jLabel67.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel67.setText("VENTAS");
        jPanel39.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 100, -1));

        jLabel68.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel68.setText("0");
        jPanel39.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 10, 80, -1));

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/card4.jpg"))); // NOI18N
        jPanel39.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 80));

        jPanel18.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 30, 180, 80));

        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/egresos.png"))); // NOI18N
        jLabel70.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel40.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 80));

        jLabel71.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel71.setText("CAJA");
        jPanel40.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 100, -1));

        jLabel72.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel72.setText("0");
        jPanel40.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 10, 80, -1));

        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/card1.jpg"))); // NOI18N
        jPanel40.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 80));

        jPanel18.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 30, 180, 80));

        paneControl.addTab("TABLERO", jPanel18);

        jPanel53.setBackground(new java.awt.Color(255, 255, 255));
        jPanel53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/barcode.png"))); // NOI18N
        jLabel41.setText("BarCode - Enter");
        jPanel53.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 150, 30));

        txtCodigoCompra.setBorder(null);
        txtCodigoCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoCompraKeyPressed(evt);
            }
        });
        jPanel53.add(txtCodigoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 300, 30));

        TableCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Descripción", "Cant.", "Precio", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableCompra.setRowHeight(23);
        jScrollPane8.setViewportView(TableCompra);
        if (TableCompra.getColumnModel().getColumnCount() > 0) {
            TableCompra.getColumnModel().getColumn(0).setMinWidth(60);
            TableCompra.getColumnModel().getColumn(0).setPreferredWidth(60);
            TableCompra.getColumnModel().getColumn(0).setMaxWidth(100);
            TableCompra.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableCompra.getColumnModel().getColumn(2).setMinWidth(60);
            TableCompra.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableCompra.getColumnModel().getColumn(2).setMaxWidth(100);
            TableCompra.getColumnModel().getColumn(3).setMinWidth(120);
            TableCompra.getColumnModel().getColumn(3).setPreferredWidth(80);
            TableCompra.getColumnModel().getColumn(3).setMaxWidth(120);
            TableCompra.getColumnModel().getColumn(4).setMinWidth(120);
            TableCompra.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableCompra.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        jPanel53.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 101, 590, 230));

        btnEliminarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminarCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCompraActionPerformed(evt);
            }
        });
        jPanel53.add(btnEliminarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 50, 45));

        jPanel54.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel53.add(jPanel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 2));

        jPanel55.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel53.add(jPanel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 300, 2));

        tblProductosCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Producto", "Precio", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductosCompra.setRowHeight(23);
        jScrollPane9.setViewportView(tblProductosCompra);
        if (tblProductosCompra.getColumnModel().getColumnCount() > 0) {
            tblProductosCompra.getColumnModel().getColumn(0).setMinWidth(50);
            tblProductosCompra.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblProductosCompra.getColumnModel().getColumn(0).setMaxWidth(50);
            tblProductosCompra.getColumnModel().getColumn(2).setMinWidth(100);
            tblProductosCompra.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblProductosCompra.getColumnModel().getColumn(2).setMaxWidth(100);
            tblProductosCompra.getColumnModel().getColumn(3).setMinWidth(50);
            tblProductosCompra.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblProductosCompra.getColumnModel().getColumn(3).setMaxWidth(50);
        }

        jPanel53.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, 670, 380));

        txtBuscarTempCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarTempCompraKeyReleased(evt);
            }
        });
        jPanel53.add(txtBuscarTempCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 40, 360, 40));

        btnAgregarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/plus.png"))); // NOI18N
        btnAgregarCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCompraActionPerformed(evt);
            }
        });
        jPanel53.add(btnAgregarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1215, 40, 70, 40));

        btnAnteriorTempCompra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorTempCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorTempCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorTempCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorTempCompraActionPerformed(evt);
            }
        });
        jPanel53.add(btnAnteriorTempCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 510, 100, 40));

        btnSiguienteTempCompra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteTempCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteTempCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteTempCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteTempCompraActionPerformed(evt);
            }
        });
        jPanel53.add(btnSiguienteTempCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 510, 90, 40));

        paginationTempCompra.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationTempCompra.setText("Página 1 de un Total 20 Páginas");
        jPanel53.add(paginationTempCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 520, 340, -1));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jPanel53.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 40, 40));

        jPanel67.setBackground(new java.awt.Color(255, 255, 255));
        jPanel67.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de compra"));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel43.setText("Buscar proveedor");

        txtIdentidadProveedor.setBorder(null);
        txtIdentidadProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdentidadProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdentidadProveedorKeyTyped(evt);
            }
        });

        jPanel46.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel47.setText("Nombre:");

        txtProveedorCompra.setEditable(false);
        txtProveedorCompra.setBackground(new java.awt.Color(255, 255, 255));
        txtProveedorCompra.setBorder(null);

        jPanel47.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ingresos.png"))); // NOI18N
        jLabel75.setText("Total a Pagar:");

        LabelTotalCompra.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LabelTotalCompra.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LabelTotalCompra.setText("0.00");

        btnGenerarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/print.png"))); // NOI18N
        btnGenerarCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenerarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarCompraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtIdPC, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel47)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                        .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdentidadProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProveedorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(LabelTotalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addComponent(btnGenerarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                .addComponent(txtIdPC, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addGap(10, 10, 10)
                        .addComponent(LabelTotalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel47))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdentidadProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProveedorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnGenerarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jPanel53.add(jPanel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 590, 190));

        btnHistorialCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/historial.png"))); // NOI18N
        btnHistorialCompras.setText("Historial compras");
        btnHistorialCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHistorialCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialComprasActionPerformed(evt);
            }
        });
        jPanel53.add(btnHistorialCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(427, 40, 170, 45));

        paneControl.addTab("COMPRAS", jPanel53);

        jPanel58.setBackground(new java.awt.Color(255, 255, 255));
        jPanel58.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane10.setBackground(new java.awt.Color(255, 255, 255));

        TableCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Proveedor", "Total", "Fecha", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableCompras.setRowHeight(23);
        TableCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableComprasMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(TableCompras);
        if (TableCompras.getColumnModel().getColumnCount() > 0) {
            TableCompras.getColumnModel().getColumn(0).setMinWidth(50);
            TableCompras.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableCompras.getColumnModel().getColumn(0).setMaxWidth(50);
            TableCompras.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableCompras.getColumnModel().getColumn(2).setMinWidth(300);
            TableCompras.getColumnModel().getColumn(2).setPreferredWidth(150);
            TableCompras.getColumnModel().getColumn(2).setMaxWidth(120);
            TableCompras.getColumnModel().getColumn(3).setMinWidth(300);
            TableCompras.getColumnModel().getColumn(3).setPreferredWidth(150);
            TableCompras.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        jPanel58.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 1240, 390));

        btnPdfCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/reportes.png"))); // NOI18N
        btnPdfCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPdfCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfComprasActionPerformed(evt);
            }
        });
        jPanel58.add(btnPdfCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 80, -1));
        jPanel58.add(txtIdCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 46, -1));

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Historial Compras");
        jPanel58.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 70, 170, -1));

        txtBuscarCompras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarComprasKeyReleased(evt);
            }
        });
        jPanel58.add(txtBuscarCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 300, 40));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jPanel58.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 40, 40));

        paginationCompras.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationCompras.setText("Página 1 de un Total 20 Páginas");
        jPanel58.add(paginationCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 380, -1));

        btnAnteriorCompras.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorComprasActionPerformed(evt);
            }
        });
        jPanel58.add(btnAnteriorCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 510, 100, 40));

        btnSiguienteCompras.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteComprasActionPerformed(evt);
            }
        });
        jPanel58.add(btnSiguienteCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 510, 90, 40));

        btnAnularCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anular.png"))); // NOI18N
        btnAnularCompra.setText("Anular");
        btnAnularCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnularCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularCompraActionPerformed(evt);
            }
        });
        jPanel58.add(btnAnularCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 50, 110, 45));

        paneControl.addTab("HISTORIAL COMPRAS", jPanel58);

        jPanel59.setBackground(new java.awt.Color(255, 255, 255));
        jPanel59.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane11.setBackground(new java.awt.Color(255, 255, 255));

        TableCajas.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        TableCajas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Monto inicial", "Monto total", "F. apertura", "F. cierre", "Usuario", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableCajas.setRowHeight(23);
        jScrollPane11.setViewportView(TableCajas);
        if (TableCajas.getColumnModel().getColumnCount() > 0) {
            TableCajas.getColumnModel().getColumn(0).setMinWidth(70);
            TableCajas.getColumnModel().getColumn(0).setPreferredWidth(40);
            TableCajas.getColumnModel().getColumn(0).setMaxWidth(70);
            TableCajas.getColumnModel().getColumn(1).setMinWidth(150);
            TableCajas.getColumnModel().getColumn(1).setPreferredWidth(120);
            TableCajas.getColumnModel().getColumn(1).setMaxWidth(150);
            TableCajas.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCajas.getColumnModel().getColumn(3).setMinWidth(150);
            TableCajas.getColumnModel().getColumn(3).setPreferredWidth(120);
            TableCajas.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jPanel59.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 1000, 450));

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));
        jPanel60.setBorder(javax.swing.BorderFactory.createTitledBorder("Apertura"));
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel54.setText("Monto Inicial");
        jPanel60.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        txtMontoIncial.setBorder(null);
        txtMontoIncial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoIncialKeyTyped(evt);
            }
        });
        jPanel60.add(txtMontoIncial, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 160, 30));

        btnGuardarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnGuardarCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCajaActionPerformed(evt);
            }
        });
        jPanel60.add(btnGuardarCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 275, 90, 45));

        btnEditarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/cerrar.png"))); // NOI18N
        btnEditarCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCajaActionPerformed(evt);
            }
        });
        jPanel60.add(btnEditarCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 275, 90, 45));

        jPanel61.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jPanel60.add(jPanel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 160, 2));

        jPanel59.add(jPanel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 270, 410));

        btnSiguienteCaja.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteCajaActionPerformed(evt);
            }
        });
        jPanel59.add(btnSiguienteCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 510, 90, 40));

        btnAnteriorCaja.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorCajaActionPerformed(evt);
            }
        });
        jPanel59.add(btnAnteriorCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 510, 100, 40));

        paginationCaja.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationCaja.setText("Página 1 de un Total 20 Páginas");
        jPanel59.add(paginationCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 380, -1));

        txtBuscarCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCajaKeyReleased(evt);
            }
        });
        jPanel59.add(txtBuscarCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 270, 30));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jLabel58.setText("Buscar");
        jPanel59.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 90, -1));

        paneControl.addTab("CAJAS", jPanel59);

        jPanel65.setBackground(new java.awt.Color(255, 255, 255));
        jPanel65.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane12.setBackground(new java.awt.Color(255, 255, 255));

        tableCredVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cliente", "Vendedor", "Abonado", "Restante", "Total", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCredVenta.setRowHeight(23);
        tableCredVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCredVentaMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tableCredVenta);
        if (tableCredVenta.getColumnModel().getColumnCount() > 0) {
            tableCredVenta.getColumnModel().getColumn(0).setMinWidth(50);
            tableCredVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableCredVenta.getColumnModel().getColumn(0).setMaxWidth(50);
            tableCredVenta.getColumnModel().getColumn(1).setPreferredWidth(60);
            tableCredVenta.getColumnModel().getColumn(2).setPreferredWidth(60);
            tableCredVenta.getColumnModel().getColumn(5).setMinWidth(120);
            tableCredVenta.getColumnModel().getColumn(5).setPreferredWidth(80);
            tableCredVenta.getColumnModel().getColumn(5).setMaxWidth(120);
            tableCredVenta.getColumnModel().getColumn(6).setMinWidth(120);
            tableCredVenta.getColumnModel().getColumn(6).setPreferredWidth(80);
            tableCredVenta.getColumnModel().getColumn(6).setMaxWidth(120);
        }

        jPanel65.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 910, 390));
        jPanel65.add(txtIdCredVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 46, -1));

        jLabel59.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Administar creditos");
        jPanel65.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 200, -1));

        txtBuscarCredVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCredVentaKeyReleased(evt);
            }
        });
        jPanel65.add(txtBuscarCredVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 300, 40));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/buscar.png"))); // NOI18N
        jPanel65.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 40, 40));

        paginationCredVenta.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paginationCredVenta.setText("Página 1 de un Total 20 Páginas");
        jPanel65.add(paginationCredVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 380, -1));

        btnAnteriorCredVenta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnteriorCredVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/anterior.png"))); // NOI18N
        btnAnteriorCredVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnteriorCredVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorCredVentaActionPerformed(evt);
            }
        });
        jPanel65.add(btnAnteriorCredVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 510, 100, 40));

        btnSiguienteCredVenta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSiguienteCredVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/siguiente.png"))); // NOI18N
        btnSiguienteCredVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguienteCredVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteCredVentaActionPerformed(evt);
            }
        });
        jPanel65.add(btnSiguienteCredVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 510, 90, 40));

        jPanel68.setBackground(new java.awt.Color(255, 255, 255));
        jPanel68.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo abono"));
        jPanel68.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAbonar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnAbonar.setText("Agregar");
        btnAbonar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbonarActionPerformed(evt);
            }
        });
        jPanel68.add(btnAbonar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 110, 40));
        jPanel68.add(txtMontoAbono, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 220, 40));

        jLabel62.setText("Monto");
        jPanel68.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        jPanel65.add(jPanel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 110, 320, 390));
        jPanel65.add(txtMontoRestante, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 60, 50, -1));

        paneControl.addTab("ADMINISTRAR CREDITOS", jPanel65);

        getContentPane().add(paneControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 95, 1320, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        page.setText("1");
        ListarCliente(txtBuscarCliente.getText(), Integer.parseInt(page.getText()), porPagina);
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        LimpiarCliente();
        paneControl.setSelectedIndex(1);
        menuInactivo();
        menuActivo(btnClientes);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        page.setText("1");
        ListarProveedor(txtBuscarProveedor.getText(), Integer.parseInt(page.getText()), porPagina);
        paneControl.setSelectedIndex(2);
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        LimpiarProveedor();
        menuInactivo();
        menuActivo(btnProveedor);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        page.setText("1");
        ListarProductos(txtBuscarProducto.getText(), Integer.parseInt(page.getText()), porPagina);
        paneControl.setSelectedIndex(3);
        btnEditarpro.setEnabled(false);
        btnEliminarPro.setEnabled(false);
        btnGuardarpro.setEnabled(true);
        LimpiarProductos();
        menuInactivo();
        menuActivo(btnProductos);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        page.setText("1");
        productosVentas(tblProductos, btnAnteriorTemp, btnSiguienteTemp, paginationTemp, txtBuscarTemp.getText(), Integer.parseInt(page.getText()), porPagina);
        paneControl.setSelectedIndex(0);
        menuInactivo();
        menuActivo(btnNuevaVenta);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        paneControl.setSelectedIndex(5);
        menuInactivo();
        menuActivo(btnConfig);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        page.setText("1");
        productosVentas(tblProductosCompra, btnAnteriorTempCompra, btnSiguienteTempCompra, paginationTempCompra, txtBuscarTempCompra.getText(), Integer.parseInt(page.getText()), porPagina);
        paneControl.setSelectedIndex(8);
        menuInactivo();
        menuActivo(btnCompras);
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
        paneControl.setSelectedIndex(6);
        LimpiarTable();
        ListarUsuarios();
        menuInactivo();
        menuActivo(btnUsuarios);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseClicked
        // TODO add your handling code here:
        cbxProveedorPro.removeAllItems();
        llenarProveedor();

    }//GEN-LAST:event_btnProductosMouseClicked

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if (txtNombre.getText().equals("") || txtCorreo.getText().equals("") || txtPass.getPassword().equals("")) {
            JOptionPane.showMessageDialog(null, "Todo los campos son requeridos");
        } else {
            String correo = txtCorreo.getText();
            String pass = String.valueOf(txtPass.getPassword());
            String nom = txtNombre.getText();
            String rol = cbxRol.getSelectedItem().toString();
            lg.setNombre(nom);
            lg.setCorreo(correo);
            lg.setPass(pass);
            lg.setRol(rol);
            String respuesta = login.Registrar(lg);
            switch (respuesta) {
                case "existe" ->
                    JOptionPane.showMessageDialog(null, "USUARIO YA EXISTE");
                case "registrado" -> {
                    JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                    LimpiarTable();
                    ListarUsuarios();
                }
                default ->
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
            }
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucConfig.getText()) && !"".equals(txtNombreConfig.getText()) && !"".equals(txtTelefonoConfig.getText()) && !"".equals(txtDireccionConfig.getText())) {
            conf.setRuc(txtRucConfig.getText());
            conf.setNombre(txtNombreConfig.getText());
            conf.setTelefono(txtTelefonoConfig.getText());
            conf.setDireccion(txtDireccionConfig.getText());
            conf.setMensaje(txtMensaje.getText());
            conf.setId(Integer.parseInt(txtIdConfig.getText()));
            Vdao.ModificarDatos(conf);
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificado");
            ListarConfig();
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        // TODO add your handling code here:
        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(TableVentas.getValueAt(fila, 0).toString());
        String estado = TableVentas.getValueAt(fila, 6).toString();
        if ("ANULADO".equalsIgnoreCase(estado)) {
            btnAnularVenta.setEnabled(false);
        } else {
            btnAnularVenta.setEnabled(true);
        }
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        // TODO add your handling code here:
        LimpiarProductos();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdproducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdproducto.getText());
                proDao.EliminarProductos(id);
                LimpiarTable();
                LimpiarProductos();
                ListarProductos(txtBuscarProducto.getText(), Integer.parseInt(page.getText()), porPagina);
                btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnEditarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarproActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdproducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtCodigoPro.getText()) && !"".equals(txtDesPro.getText()) && !"".equals(txtCantPro.getText()) && !"".equals(txtPrecioPro.getText())) {
                pro.setCodigo(txtCodigoPro.getText());
                pro.setNombre(txtDesPro.getText());
                Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
                pro.setProveedor(itemP.getId());
                pro.setStock(Integer.parseInt(txtCantPro.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
                pro.setId(Integer.parseInt(txtIdproducto.getText()));
                String respuesta = proDao.ModificarProductos(pro);
                switch (respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "PRODUCTO YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "PRODUCTO MODIFICADO");
                        LimpiarProductos();
                        LimpiarTable();
                        ListarProductos(txtBuscarProducto.getText(), Integer.parseInt(page.getText()), porPagina);
                        cbxProveedorPro.removeAllItems();
                        llenarProveedor();
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
                }
                btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarproActionPerformed

    private void btnGuardarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarproActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCodigoPro.getText()) && !"".equals(txtDesPro.getText()) && !"".equals(cbxProveedorPro.getSelectedItem()) && !"".equals(txtCantPro.getText()) && !"".equals(txtPrecioPro.getText())) {
            pro.setCodigo(txtCodigoPro.getText());
            pro.setNombre(txtDesPro.getText());
            Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
            pro.setProveedor(itemP.getId());
            pro.setStock(Integer.parseInt(txtCantPro.getText()));
            pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
            String respuesta = proDao.RegistrarProductos(pro);
            switch (respuesta) {
                case "existe" ->
                    JOptionPane.showMessageDialog(null, "PRODUCTO YA EXISTE");
                case "registrado" -> {
                    JOptionPane.showMessageDialog(null, "PRODUCTO REGISTRADO");
                    LimpiarProductos();
                    LimpiarTable();
                    ListarProductos(txtBuscarProducto.getText(), Integer.parseInt(page.getText()), porPagina);
                    cbxProveedorPro.removeAllItems();
                    llenarProveedor();
                }
                default ->
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
            }
            btnEditarpro.setEnabled(false);
            btnEliminarPro.setEnabled(false);
            btnGuardarpro.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarproActionPerformed

    private void cbxProveedorProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProveedorProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProActionPerformed

    private void cbxProveedorProItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProveedorProItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cbxProveedorProItemStateChanged

    private void txtPrecioProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtPrecioProKeyTyped

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        // TODO add your handling code here:
        btnEditarpro.setEnabled(true);
        btnEliminarPro.setEnabled(true);
        btnGuardarpro.setEnabled(true);
        int fila = TableProducto.rowAtPoint(evt.getPoint());
        txtIdproducto.setText(TableProducto.getValueAt(fila, 0).toString());
        pro = proDao.BuscarId(Integer.parseInt(txtIdproducto.getText()));
        txtCodigoPro.setText(pro.getCodigo());
        txtDesPro.setText(pro.getNombre());
        txtCantPro.setText("" + pro.getStock());
        txtPrecioPro.setText("" + pro.getPrecio());
        cbxProveedorPro.setSelectedItem(new Combo(pro.getProveedor(), pro.getProveedorPro()));
    }//GEN-LAST:event_TableProductoMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTable();
                ListarProveedor(txtBuscarProveedor.getText(), Integer.parseInt(page.getText()), porPagina);
                LimpiarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarProveedor();
        btnEditarProveedor.setEnabled(false);
        btnEliminarProveedor.setEnabled(false);
        btnguardarProveedor.setEnabled(true);
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRucProveedor.getText()) && !"".equals(txtNombreproveedor.getText()) && !"".equals(txtTelefonoProveedor.getText()) && !"".equals(txtDireccionProveedor.getText())) {
                pr.setRuc(txtRucProveedor.getText());
                pr.setNombre(txtNombreproveedor.getText());
                pr.setTelefono(txtTelefonoProveedor.getText());
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                String respuesta = PrDao.ModificarProveedor(pr);
                switch (respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "PROVEEDOR YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "PROVEEDOR MODIFICADO");
                        LimpiarProveedor();
                        LimpiarTable();
                        ListarProveedor(txtBuscarProveedor.getText(), Integer.parseInt(page.getText()), porPagina);
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
                }
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnguardarProveedor.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnguardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucProveedor.getText()) && !"".equals(txtNombreproveedor.getText()) && !"".equals(txtTelefonoProveedor.getText()) && !"".equals(txtDireccionProveedor.getText())) {
            pr.setRuc(txtRucProveedor.getText());
            pr.setNombre(txtNombreproveedor.getText());
            pr.setTelefono(txtTelefonoProveedor.getText());
            pr.setDireccion(txtDireccionProveedor.getText());
            String respuesta = PrDao.RegistrarProveedor(pr);
            switch (respuesta) {
                case "existe" ->
                    JOptionPane.showMessageDialog(null, "PROVEEDOR YA EXISTE");
                case "registrado" -> {
                    JOptionPane.showMessageDialog(null, "PROVEEDOR REGISTRADO");
                    LimpiarProveedor();
                    LimpiarTable();
                    ListarProveedor(txtBuscarProveedor.getText(), Integer.parseInt(page.getText()), porPagina);
                }
                default ->
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
            }
            btnEditarProveedor.setEnabled(false);
            btnEliminarProveedor.setEnabled(false);
            btnguardarProveedor.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos esta vacios");
        }
    }//GEN-LAST:event_btnguardarProveedorActionPerformed

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        // TODO add your handling code here:
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        btnguardarProveedor.setEnabled(false);
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtNombreproveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        // TODO add your handling code here:
        btnEditarCliente.setEnabled(true);
        btnEliminarCliente.setEnabled(true);
        btnGuardarCliente.setEnabled(false);
        int fila = TableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtDniCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(TableCliente.getValueAt(fila, 3).toString());
        txtDirecionCliente.setText(TableCliente.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        // TODO add your handling code here:
        if (TableVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreClienteventa.getText())) {
                if (Vdao.comprobarCaja(Integer.parseInt(id_usuario.getText()))) {
                    RegistrarVenta();
                    RegistrarDetalle();
                    ActualizarStock(TableVenta, 1);
                    LimpiarTableTemp(TableVenta);
                    LimpiarClienteventa();
                    generarSerie();
                    LabelTotal.setText("0.00");
                } else {
                    JOptionPane.showMessageDialog(null, "La caja esta cerada");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar un cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Noy productos en la venta");
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void txtRucVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucVentaKeyTyped

    private void txtRucVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtRucVenta.getText())) {
                String dni = txtRucVenta.getText();
                cl = client.Buscarcliente(dni);
                if (cl.getNombre() != null) {
                    txtNombreClienteventa.setText("" + cl.getNombre());
                    txtIdCV.setText("" + cl.getId());
                } else {
                    txtRucVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtRucVentaKeyPressed

    private void btnEliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarventaActionPerformed
        // TODO add your handling code here:
        modelo1 = (DefaultTableModel) TableVenta.getModel();
        modelo1.removeRow(TableVenta.getSelectedRow());
        TotalPagar(TableVenta, LabelTotal);
        txtCodigoVenta.requestFocus();
    }//GEN-LAST:event_btnEliminarventaActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                pro = proDao.BuscarPro(cod);
                if (pro.getNombre() != null) {
                    agregarProductoVenta(pro.getId(), pro.getNombre(), pro.getPrecio(), pro.getStock());
                    TotalPagar(TableVenta, LabelTotal);
                } else {
                    JOptionPane.showMessageDialog(null, "EL CODIGO NO EXISTE");
                }
                txtCodigoVenta.setText("");
                txtCodigoVenta.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del productos");
                txtCodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed
    private void agregarProductoVenta(int idProducto, String descripcion, double precio, int stock) {
        item = item + 1;
        tmp = (DefaultTableModel) TableVenta.getModel();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            if (TableVenta.getValueAt(i, 0).equals(idProducto)) {
                int cantActual = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
                int nuevoCantidad = cantActual + 1;
                if (stock >= nuevoCantidad) {
                    double nuevoSub = precio * nuevoCantidad;
                    tmp.setValueAt(nuevoCantidad, i, 2);
                    tmp.setValueAt(nuevoSub, i, 4);
                }else{
                   JOptionPane.showMessageDialog(null, "Stock agotado"); 
                }
                return;
            }
        }
        ArrayList lista = new ArrayList();
        lista.add(item);
        lista.add(idProducto);
        lista.add(descripcion);
        lista.add(1);
        lista.add(precio);
        lista.add(precio);
        Object[] O = new Object[6];
        O[0] = lista.get(1);
        O[1] = lista.get(2);
        O[2] = lista.get(3);
        O[3] = lista.get(4);
        O[4] = lista.get(5);
        tmp.addRow(O);
        TableVenta.setModel(tmp);
    }

    private void agregarProductoCompra(int idProducto, String descripcion, double precio) {
        item = item + 1;
        tmp = (DefaultTableModel) TableCompra.getModel();
        for (int i = 0; i < TableCompra.getRowCount(); i++) {
            if (TableCompra.getValueAt(i, 0).equals(idProducto)) {
                int cantActual = Integer.parseInt(TableCompra.getValueAt(i, 2).toString());
                int nuevoCantidad = cantActual + 1;
                double nuevoSub = precio * nuevoCantidad;
                tmp.setValueAt(nuevoCantidad, i, 2);
                tmp.setValueAt(nuevoSub, i, 4);
                return;
            }
        }
        ArrayList lista = new ArrayList();
        lista.add(item);
        lista.add(idProducto);
        lista.add(descripcion);
        lista.add(1);
        lista.add(precio);
        lista.add(precio);
        Object[] O = new Object[6];
        O[0] = lista.get(1);
        O[1] = lista.get(2);
        O[2] = lista.get(3);
        O[3] = lista.get(4);
        O[4] = lista.get(5);
        tmp.addRow(O);
        TableCompra.setModel(tmp);
    }
    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniClienteKeyTyped

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                client.EliminarCliente(id);
                LimpiarTable();
                LimpiarCliente();
                ListarCliente(txtBuscarCliente.getText(), Integer.parseInt(page.getText()), porPagina);
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtDniCliente.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefonoCliente.getText()) && !"".equals(txtDirecionCliente.getText())) {
            cl.setDni(txtDniCliente.getText());
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(txtTelefonoCliente.getText());
            cl.setDireccion(txtDirecionCliente.getText());
            String respuesta = client.RegistrarCliente(cl);
            switch (respuesta) {
                case "existe" ->
                    JOptionPane.showMessageDialog(null, "CLIENTE YA EXISTE");
                case "registrado" -> {
                    JOptionPane.showMessageDialog(null, "CLIENTE REGISTRADO");
                    LimpiarCliente();
                    LimpiarTable();
                    ListarCliente(txtBuscarCliente.getText(), Integer.parseInt(page.getText()), porPagina);
                }
                default ->
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
            }
            btnEditarCliente.setEnabled(false);
            btnEliminarCliente.setEnabled(false);
            btnGuardarCliente.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {
            if (!"".equals(txtDniCliente.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefonoCliente.getText())) {
                cl.setDni(txtDniCliente.getText());
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDirecionCliente.getText());
                cl.setId(Integer.parseInt(txtIdCliente.getText()));
                String respuesta = client.ModificarCliente(cl);
                switch (respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "CLIENTE YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "CLIENTE MODIFICADO");
                        LimpiarCliente();
                        LimpiarTable();
                        ListarCliente(txtBuscarCliente.getText(), Integer.parseInt(page.getText()), porPagina);
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        LimpiarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnGuardarCliente.setEnabled(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnAnteriorClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorClienteActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        ListarCliente(txtBuscarCliente.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorClienteActionPerformed

    private void btnSiguienteClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteClienteActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        ListarCliente(txtBuscarCliente.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteClienteActionPerformed

    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        LimpiarTable();
        page.setText("1");
        ListarCliente(txtBuscarCliente.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarClienteKeyReleased

    private void txtBuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarProductos(txtBuscarProducto.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarProductoKeyReleased

    private void btnAnteriorProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorProductoActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        ListarProductos(txtBuscarProducto.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorProductoActionPerformed

    private void btnSiguienteProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteProductoActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        ListarProductos(txtBuscarProducto.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteProductoActionPerformed

    private void txtBuscarProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProveedorKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarProveedor(txtBuscarProveedor.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarProveedorKeyReleased

    private void btnAnteriorProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorProveedorActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        ListarProveedor(txtBuscarProveedor.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorProveedorActionPerformed

    private void btnSiguienteProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteProveedorActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        ListarProveedor(txtBuscarProveedor.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteProveedorActionPerformed

    private void btnAnteriorVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorVentasActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        ListarVentas(txtBuscarVentas.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorVentasActionPerformed

    private void btnSiguienteVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteVentasActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        ListarVentas(txtBuscarVentas.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteVentasActionPerformed

    private void txtBuscarVentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarVentasKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarVentas(txtBuscarVentas.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarVentasKeyReleased

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (tblProductos.getSelectedRow() >= 0) {
            int id = Integer.parseInt(tblProductos.getValueAt(tblProductos.getSelectedRow(), 0).toString());
            String descripcion = tblProductos.getValueAt(tblProductos.getSelectedRow(), 1).toString();
            double precio = Double.parseDouble(tblProductos.getValueAt(tblProductos.getSelectedRow(), 2).toString());
            int stock = Integer.parseInt(tblProductos.getValueAt(tblProductos.getSelectedRow(), 3).toString());
            agregarProductoVenta(id, descripcion, precio, stock);
            TotalPagar(TableVenta, LabelTotal);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONA UN PRODUCTO");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtBuscarTempKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarTempKeyReleased
        page.setText("1");
        LimpiarTable();
        productosVentas(tblProductos, btnAnteriorTemp, btnSiguienteTemp, paginationTemp, txtBuscarTemp.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarTempKeyReleased

    private void btnAnteriorTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorTempActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        productosVentas(tblProductos, btnAnteriorTemp, btnSiguienteTemp, paginationTemp, txtBuscarTemp.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorTempActionPerformed

    private void btnSiguienteTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteTempActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        productosVentas(tblProductos, btnAnteriorTemp, btnSiguienteTemp, paginationTemp, txtBuscarTemp.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteTempActionPerformed

    private void btnTableroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableroActionPerformed
        mostrarTotales();
    }//GEN-LAST:event_btnTableroActionPerformed

    private void TableUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableUsuariosMouseClicked
        int fila = TableUsuarios.rowAtPoint(evt.getPoint());
        txtIdUser.setText(TableUsuarios.getValueAt(fila, 0).toString());
        txtNombre.setText(TableUsuarios.getValueAt(fila, 1).toString());
        txtCorreo.setText(TableUsuarios.getValueAt(fila, 2).toString());
        cbxRol.setSelectedItem(TableUsuarios.getValueAt(fila, 3).toString());
    }//GEN-LAST:event_TableUsuariosMouseClicked

    private void btnModificarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUserActionPerformed
        if ("".equals(txtIdUser.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {
            if (txtNombre.getText().equals("") || txtCorreo.getText().equals("") || txtPass.getPassword().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son requeridos");
            } else {
                String correo = txtCorreo.getText();
                String nom = txtNombre.getText();
                String rol = cbxRol.getSelectedItem().toString();
                lg.setNombre(nom);
                lg.setCorreo(correo);
                lg.setRol(rol);
                lg.setId(Integer.parseInt(txtIdUser.getText()));
                String respuesta = login.Modificar(lg);
                switch (respuesta) {
                    case "existe" ->
                        JOptionPane.showMessageDialog(null, "USUARIO YA EXISTE");
                    case "modificado" -> {
                        JOptionPane.showMessageDialog(null, "USUARIO MODIFICADO");
                        LimpiarTable();
                        ListarUsuarios();
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR");
                }
            }
        }

    }//GEN-LAST:event_btnModificarUserActionPerformed

    private void txtBuscarUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarUserKeyReleased
        LimpiarTable();
        ListarUsuarios();
    }//GEN-LAST:event_txtBuscarUserKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Vdao.reporteProductos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnCredVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCredVentasActionPerformed
        paneControl.setSelectedIndex(11);
        LimpiarTable();
        page.setText("1");
        ListarCredVentas(txtBuscarCredVenta.getText(), Integer.parseInt(page.getText()), porPagina);
        menuInactivo();
        menuActivo(btnCredVentas);
    }//GEN-LAST:event_btnCredVentasActionPerformed

    private void btnCajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajasActionPerformed
        paneControl.setSelectedIndex(10);
        LimpiarTable();
        page.setText("1");
        ListarCajas(txtBuscarCaja.getText(), Integer.parseInt(page.getText()), porPagina);
        menuInactivo();
        menuActivo(btnCajas);
    }//GEN-LAST:event_btnCajasActionPerformed

    private void txtCodigoCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCompraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoCompra.getText())) {
                String cod = txtCodigoCompra.getText();
                pro = proDao.BuscarPro(cod);
                if (pro.getNombre() != null) {
                    agregarProductoCompra(pro.getId(), pro.getNombre(), pro.getPrecio());
                    TotalPagar(TableCompra, LabelTotalCompra);
                } else {
                    JOptionPane.showMessageDialog(null, "EL CODIGO NO EXISTE");
                }
                txtCodigoCompra.requestFocus();
                txtCodigoCompra.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del productos");
                txtCodigoCompra.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoCompraKeyPressed

    private void txtBuscarTempCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarTempCompraKeyReleased
        page.setText("1");
        LimpiarTable();
        productosVentas(tblProductosCompra, btnAnteriorTempCompra, btnSiguienteTempCompra, paginationTempCompra, txtBuscarTempCompra.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarTempCompraKeyReleased

    private void btnAgregarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCompraActionPerformed
        if (tblProductosCompra.getSelectedRow() >= 0) {
            int id = Integer.parseInt(tblProductosCompra.getValueAt(tblProductosCompra.getSelectedRow(), 0).toString());
            String descripcion = tblProductosCompra.getValueAt(tblProductosCompra.getSelectedRow(), 1).toString();
            double precio = Double.parseDouble(tblProductosCompra.getValueAt(tblProductosCompra.getSelectedRow(), 2).toString());
            agregarProductoCompra(id, descripcion, precio);
            TotalPagar(TableCompra, LabelTotalCompra);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONA UN PRODUCTO");
        }
    }//GEN-LAST:event_btnAgregarCompraActionPerformed

    private void btnAnteriorTempCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorTempCompraActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        productosVentas(tblProductosCompra, btnAnteriorTempCompra, btnSiguienteTempCompra, paginationTempCompra, txtBuscarTempCompra.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorTempCompraActionPerformed

    private void btnSiguienteTempCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteTempCompraActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        productosVentas(tblProductosCompra, btnAnteriorTempCompra, btnSiguienteTempCompra, paginationTempCompra, txtBuscarTempCompra.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteTempCompraActionPerformed

    private void TableComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableComprasMouseClicked
        int fila = TableCompras.rowAtPoint(evt.getPoint());
        txtIdCompras.setText(TableCompras.getValueAt(fila, 0).toString());
        String estado = TableCompras.getValueAt(fila, 4).toString();
        if ("ANULADO".equalsIgnoreCase(estado)) {
            btnAnularCompra.setEnabled(false);
        } else {
            btnAnularCompra.setEnabled(true);
        }
    }//GEN-LAST:event_TableComprasMouseClicked

    private void btnPdfComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfComprasActionPerformed
        if (txtIdCompras.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
            compDao.reporteCompra(Integer.parseInt(txtIdCompras.getText()));
        }
    }//GEN-LAST:event_btnPdfComprasActionPerformed

    private void txtBuscarComprasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarComprasKeyReleased
        page.setText("1");
        LimpiarTable();
        ListarCompras(txtBuscarCompras.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarComprasKeyReleased

    private void btnAnteriorComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorComprasActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        ListarCompras(txtBuscarCompras.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorComprasActionPerformed

    private void btnSiguienteComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteComprasActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        ListarCompras(txtBuscarCompras.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteComprasActionPerformed

    private void txtMontoIncialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoIncialKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoIncialKeyTyped

    private void btnGuardarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCajaActionPerformed
        if (!"".equals(txtMontoIncial.getText())) {
            String respuesta = Vdao.abrirCaja(Double.parseDouble(txtMontoIncial.getText()), Integer.parseInt(id_usuario.getText()));
            switch (respuesta) {
                case "existe" ->
                    JOptionPane.showMessageDialog(null, "LA CAJA ESTA ABIERTA");
                case "registrado" -> {
                    JOptionPane.showMessageDialog(null, "CAJA ABIERTO");
                    txtMontoIncial.setText("");
                    LimpiarTable();
                    ListarCajas(txtBuscarCaja.getText(), Integer.parseInt(page.getText()), porPagina);
                }
                default ->
                    JOptionPane.showMessageDialog(null, "ERROR AL ABRIR CAJA");
            }
        } else {
            JOptionPane.showMessageDialog(null, "EL MONTO INICIAL ES REQUERIDO");
        }
    }//GEN-LAST:event_btnGuardarCajaActionPerformed

    private void btnEditarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCajaActionPerformed
        int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de cerrar la caja");
        if (pregunta == 0) {
            double ingreso = Vdao.totalIngreso(Integer.parseInt(id_usuario.getText()));
            double abono = Vdao.totalAbono(Integer.parseInt(id_usuario.getText()));
            double total = ingreso + abono;
            if (total > 0) {
                Cajas cj = new Cajas();
                cj.setMonto_final(total);
                cj.setF_cierre(new SimpleDateFormat("yyyy-MM-dd H:m:s").format(fechaVenta));
                cj.setEstado("CERRADO");
                cj.setId_usuario(Integer.parseInt(id_usuario.getText()));
                if (Vdao.cerrarCaja(cj)) {
                    Vdao.cambiarEstado("ventas", Integer.parseInt(id_usuario.getText()));
                    Vdao.cambiarEstado("abo_ventas", Integer.parseInt(id_usuario.getText()));
                    JOptionPane.showMessageDialog(null, "CAJA CERRADA");
                    LimpiarTable();
                    ListarCajas(txtBuscarCaja.getText(), Integer.parseInt(page.getText()), porPagina);
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR AL CERRAR LA CAJA");
                }
            } else {
                JOptionPane.showMessageDialog(null, "SALDO NO DISPONIBLE");
            }

        }
    }//GEN-LAST:event_btnEditarCajaActionPerformed

    private void btnSiguienteCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteCajaActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        ListarCajas(txtBuscarCaja.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteCajaActionPerformed

    private void btnAnteriorCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorCajaActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        ListarCajas(txtBuscarCaja.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorCajaActionPerformed

    private void txtBuscarCajaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCajaKeyReleased
        LimpiarTable();
        page.setText("1");
        ListarCajas(txtBuscarCaja.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarCajaKeyReleased

    private void tableCredVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCredVentaMouseClicked
        int fila = tableCredVenta.rowAtPoint(evt.getPoint());
        txtIdCredVenta.setText(tableCredVenta.getValueAt(fila, 0).toString());
        txtMontoRestante.setText(tableCredVenta.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_tableCredVentaMouseClicked

    private void txtBuscarCredVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCredVentaKeyReleased
        LimpiarTable();
        page.setText("1");
        ListarCredVentas(txtBuscarCredVenta.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_txtBuscarCredVentaKeyReleased

    private void btnAnteriorCredVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorCredVentaActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina - 1));
        LimpiarTable();
        ListarCredVentas(txtBuscarCredVenta.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnAnteriorCredVentaActionPerformed

    private void btnSiguienteCredVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteCredVentaActionPerformed
        int pagina = Integer.parseInt(page.getText());
        page.setText("" + (pagina + 1));
        LimpiarTable();
        ListarCredVentas(txtBuscarCredVenta.getText(), Integer.parseInt(page.getText()), porPagina);
    }//GEN-LAST:event_btnSiguienteCredVentaActionPerformed

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed

        if (txtIdVenta.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
            Vdao.reporteVenta(Integer.parseInt(txtIdVenta.getText()));
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void btnAbonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbonarActionPerformed
        if (txtIdCredVenta.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
            if (!"".equals(txtMontoAbono.getText())) {
                double total = Double.parseDouble(txtMontoRestante.getText());
                double abono = Double.parseDouble(txtMontoAbono.getText());
                if (total >= abono) {
                    int result = creddao.RegistrarAbono(abono, Integer.parseInt(txtIdCredVenta.getText()), Integer.parseInt(id_usuario.getText()));
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "Abono agregado");
                        txtIdCredVenta.setText("");
                        txtMontoAbono.setText("");
                        txtMontoRestante.setText("");
                        LimpiarTable();
                        page.setText("1");
                        ListarCredVentas(txtBuscarCredVenta.getText(), Integer.parseInt(page.getText()), porPagina);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al agregar abono");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El monto es mayor a restante");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El monto es requerido");
            }
        }

    }//GEN-LAST:event_btnAbonarActionPerformed

    private void btnHistorialVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialVentaActionPerformed
        LimpiarTable();
        page.setText("1");
        ListarVentas(txtBuscarVentas.getText(), Integer.parseInt(page.getText()), porPagina);
        paneControl.setSelectedIndex(4);
    }//GEN-LAST:event_btnHistorialVentaActionPerformed

    private void btnEliminarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCompraActionPerformed
        modelo1 = (DefaultTableModel) TableCompra.getModel();
        modelo1.removeRow(TableCompra.getSelectedRow());
        TotalPagar(TableCompra, LabelTotalCompra);
        txtCodigoCompra.requestFocus();
    }//GEN-LAST:event_btnEliminarCompraActionPerformed

    private void txtIdentidadProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdentidadProveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtIdentidadProveedor.getText())) {
                pr = PrDao.BuscarProveedor(txtIdentidadProveedor.getText());
                if (pr.getNombre() != null) {
                    txtProveedorCompra.setText("" + pr.getNombre());
                    txtIdPC.setText("" + pr.getId());
                } else {
                    txtIdentidadProveedor.setText("");
                    JOptionPane.showMessageDialog(null, "El proveedor no existe");
                }
            }
        }
    }//GEN-LAST:event_txtIdentidadProveedorKeyPressed

    private void txtIdentidadProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdentidadProveedorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdentidadProveedorKeyTyped

    private void btnGenerarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarCompraActionPerformed
        if (TableCompra.getRowCount() > 0) {
            if (!"".equals(txtProveedorCompra.getText())) {
                RegistrarCompra();
                RegistrarDetalleCompra();
                ActualizarStock(TableCompra, 0);
                LimpiarTableTemp(TableCompra);
                LimpiarProveedorCompra();
                LabelTotalCompra.setText("0.00");
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar un proveedor");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Noy productos en el carrito");
        }
    }//GEN-LAST:event_btnGenerarCompraActionPerformed

    private void btnHistorialComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialComprasActionPerformed
        LimpiarTable();
        page.setText("1");
        ListarCompras(txtBuscarCompras.getText(), Integer.parseInt(page.getText()), porPagina);
        paneControl.setSelectedIndex(9);
    }//GEN-LAST:event_btnHistorialComprasActionPerformed

    private void btnAnularCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularCompraActionPerformed
        if (!"".equals(txtIdCompras.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de anular");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCompras.getText());
                if (compDao.anular(id)) {
                    List<Detalle> Lista = compDao.BuscarCompra(id);
                    for (int i = 0; i < Lista.size(); i++) {
                        pro = proDao.BuscarId(Lista.get(i).getId_pro());
                        int cantidad = pro.getStock() - Lista.get(i).getCantidad();
                        proDao.ActualizarStock(cantidad, Lista.get(i).getId_pro());
                    }
                }
                JOptionPane.showMessageDialog(null, "Compra anulado");
                LimpiarTable();
                ListarCompras(txtBuscarCompras.getText(), Integer.parseInt(page.getText()), porPagina);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnAnularCompraActionPerformed

    private void btnAnularVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularVentaActionPerformed
        if (!"".equals(txtIdVenta.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de anular");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdVenta.getText());
                if (Vdao.anular(id)) {
                    List<Detalle> Lista = Vdao.BuscarVenta(id);
                    for (int i = 0; i < Lista.size(); i++) {
                        pro = proDao.BuscarId(Lista.get(i).getId_pro());
                        int cantidad = pro.getStock() + Lista.get(i).getCantidad();
                        proDao.ActualizarStock(cantidad, Lista.get(i).getId_pro());
                    }
                }
                JOptionPane.showMessageDialog(null, "Venta anulado");
                LimpiarTable();
                ListarVentas(txtBuscarVentas.getText(), Integer.parseInt(page.getText()), porPagina);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnAnularVentaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Sistema().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JLabel LabelTotalCompra;
    private javax.swing.JLabel LabelVendedor;
    private javax.swing.JTable TableCajas;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableCompra;
    private javax.swing.JTable TableCompras;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableUsuarios;
    private javax.swing.JTable TableVenta;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnAbonar;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarCompra;
    private javax.swing.JButton btnAnteriorCaja;
    private javax.swing.JButton btnAnteriorCliente;
    private javax.swing.JButton btnAnteriorCompras;
    private javax.swing.JButton btnAnteriorCredVenta;
    private javax.swing.JButton btnAnteriorProducto;
    private javax.swing.JButton btnAnteriorProveedor;
    private javax.swing.JButton btnAnteriorTemp;
    private javax.swing.JButton btnAnteriorTempCompra;
    private javax.swing.JButton btnAnteriorVentas;
    private javax.swing.JButton btnAnularCompra;
    private javax.swing.JButton btnAnularVenta;
    private javax.swing.JButton btnCajas;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnCredVentas;
    private javax.swing.JButton btnEditarCaja;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEditarpro;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarCompra;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarventa;
    private javax.swing.JButton btnGenerarCompra;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGuardarCaja;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarpro;
    private javax.swing.JButton btnHistorialCompras;
    private javax.swing.JButton btnHistorialVenta;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnModificarUser;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfCompras;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnSiguienteCaja;
    private javax.swing.JButton btnSiguienteCliente;
    private javax.swing.JButton btnSiguienteCompras;
    private javax.swing.JButton btnSiguienteCredVenta;
    private javax.swing.JButton btnSiguienteProducto;
    private javax.swing.JButton btnSiguienteProveedor;
    private javax.swing.JButton btnSiguienteTemp;
    private javax.swing.JButton btnSiguienteTempCompra;
    private javax.swing.JButton btnSiguienteVentas;
    private javax.swing.JButton btnTablero;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnguardarProveedor;
    private javax.swing.JComboBox<String> cbxMetodo;
    private javax.swing.JComboBox<Object> cbxProveedorPro;
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JTextField id_usuario;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField page;
    private javax.swing.JLabel paginationCaja;
    private javax.swing.JLabel paginationCliente;
    private javax.swing.JLabel paginationCompras;
    private javax.swing.JLabel paginationCredVenta;
    private javax.swing.JLabel paginationProducto;
    private javax.swing.JLabel paginationProveedor;
    private javax.swing.JLabel paginationTemp;
    private javax.swing.JLabel paginationTempCompra;
    private javax.swing.JLabel paginationVentas;
    private javax.swing.JTabbedPane paneControl;
    private javax.swing.JPanel panelGrafico;
    private javax.swing.JTable tableCredVenta;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTable tblProductosCompra;
    private javax.swing.JLabel tipo;
    private javax.swing.JTextField txtBuscarCaja;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtBuscarCompras;
    private javax.swing.JTextField txtBuscarCredVenta;
    private javax.swing.JTextField txtBuscarProducto;
    private javax.swing.JTextField txtBuscarProveedor;
    private javax.swing.JTextField txtBuscarTemp;
    private javax.swing.JTextField txtBuscarTempCompra;
    private javax.swing.JTextField txtBuscarUser;
    private javax.swing.JTextField txtBuscarVentas;
    private javax.swing.JTextField txtCantPro;
    private javax.swing.JTextField txtCodigoCompra;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDesPro;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDirecionCliente;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtIdCV;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdCompras;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdCredVenta;
    private javax.swing.JTextField txtIdPC;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdentidadProveedor;
    private javax.swing.JTextField txtIdproducto;
    private javax.swing.JTextField txtMensaje;
    private javax.swing.JTextField txtMontoAbono;
    private javax.swing.JTextField txtMontoIncial;
    private javax.swing.JTextField txtMontoRestante;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteventa;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreproveedor;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtProveedorCompra;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtRucVenta;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables
    private void LimpiarCliente() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDirecionCliente.setText("");
    }

    private void LimpiarProveedor() {
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtNombreproveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
    }

    private void LimpiarProductos() {
        txtCodigoPro.setText("");
        cbxProveedorPro.setSelectedItem(null);
        txtDesPro.setText("");
        txtCantPro.setText("");
        txtPrecioPro.setText("");
    }

    private void TotalPagar(JTable tabla, JLabel total) {
        Totalpagar = 0.00;
        int numFila = tabla.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tabla.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        total.setText(String.format("%.2f", Totalpagar));
    }

    private void RegistrarVenta() {
        String metodo = cbxMetodo.getSelectedItem().toString();
        v.setCliente(Integer.parseInt(txtIdCV.getText()));
        v.setVendedor(LabelVendedor.getText());
        v.setMetodo(metodo);
        v.setTotal(Totalpagar);
        v.setFecha(fechaActual);
        v.setId_usuario(Integer.parseInt(id_usuario.getText()));
        Vdao.RegistrarVenta(v);
        if ("CREDITO".equals(metodo)) {
            int id = Vdao.IdVenta();
            creddao.registrarCredito(id);
        }
    }

    private void RegistrarCompra() {
        comp.setId_proveedor(Integer.parseInt(txtIdPC.getText()));
        comp.setTotal(Totalpagar);
        comp.setFecha(fechaActual);
        comp.setId_usuario(Integer.parseInt(id_usuario.getText()));
        compDao.RegistrarCompra(comp);
    }

    private void RegistrarDetalle() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(Integer.parseInt(TableVenta.getValueAt(i, 0).toString()));
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId_detalle(id);
            Dv.setSubTotal(precio * cant);
            Vdao.RegistrarDetalle(Dv);
        }
        System.out.println(id);
        Vdao.reporteVenta(id);
    }

    private void RegistrarDetalleCompra() {
        int id = compDao.IdCompra();
        for (int i = 0; i < TableCompra.getRowCount(); i++) {
            int cant = Integer.parseInt(TableCompra.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableCompra.getValueAt(i, 3).toString());
            Dv.setId_pro(Integer.parseInt(TableCompra.getValueAt(i, 0).toString()));
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId_detalle(id);
            compDao.RegistrarDetalle(Dv);
        }
        compDao.reporteCompra(id);
    }

    private void ActualizarStock(JTable table, int accion) {
        for (int i = 0; i < table.getRowCount(); i++) {
            int id = Integer.parseInt(table.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(table.getValueAt(i, 2).toString());
            pro = proDao.BuscarId(id);
            int StockActual;
            if (accion == 1) {
                StockActual = pro.getStock() - cant;
            } else {
                StockActual = pro.getStock() + cant;
            }
            proDao.ActualizarStock(StockActual, id);

        }
    }

    private void LimpiarTableTemp(JTable table) {
        tmp = (DefaultTableModel) table.getModel();
        int fila = table.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    private void LimpiarClienteventa() {
        txtRucVenta.setText("");
        txtNombreClienteventa.setText("");
        txtIdCV.setText("");
    }

    private void LimpiarProveedorCompra() {
        txtIdentidadProveedor.setText("");
        txtProveedorCompra.setText("");
        txtIdPC.setText("");
    }

    private void llenarProveedor() {
        List<Proveedor> lista = PrDao.comboProveedor();
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            cbxProveedorPro.addItem(new Combo(id, nombre));
        }
    }

    private void colorTable(JTable tabla) {
        JTableHeader header = tabla.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 0, 0));
        header.setForeground(Color.white);
    }

    private void menuInactivo() {
        btnTablero.setBackground(new Color(0, 0, 0));
        btnNuevaVenta.setBackground(new Color(0, 0, 0));
        btnClientes.setBackground(new Color(0, 0, 0));
        btnProveedor.setBackground(new Color(0, 0, 0));
        btnProductos.setBackground(new Color(0, 0, 0));
        btnCompras.setBackground(new Color(0, 0, 0));
        btnConfig.setBackground(new Color(0, 0, 0));
        btnUsuarios.setBackground(new Color(0, 0, 0));
        btnCajas.setBackground(new Color(0, 0, 0));
        btnCredVentas.setBackground(new Color(0, 0, 0));
        btnTablero.setEnabled(true);
        btnNuevaVenta.setEnabled(true);
        btnClientes.setEnabled(true);
        btnProveedor.setEnabled(true);
        btnProductos.setEnabled(true);
        btnCompras.setEnabled(true);
        btnConfig.setEnabled(true);
        btnUsuarios.setEnabled(true);
        btnCajas.setEnabled(true);
        btnCredVentas.setEnabled(true);
    }

    private void menuActivo(JButton boton) {
        boton.setEnabled(false);
    }

    private void mostrarTotales() {
        menuInactivo();
        menuActivo(btnTablero);
        panelGrafico.removeAll();
        Vdao.reporteGrafico(panelGrafico, anio, Integer.parseInt(id_usuario.getText()));
        double ingresos = Vdao.totalIngreso(Integer.parseInt(id_usuario.getText()));
        double abonos = Vdao.totalAbono(Integer.parseInt(id_usuario.getText()));
        double totalGeneral = ingresos + abonos;
        jLabel72.setText("" + totalGeneral);
        jLabel42.setText("" + Math.round(client.totalCliente("")));
        jLabel55.setText("" + Math.round(PrDao.totalProveedor("")));
        jLabel64.setText("" + Math.round(proDao.totalProductos("")));
        jLabel68.setText("" + Math.round(Vdao.totalVentas("", Integer.parseInt(id_usuario.getText()))));
        paneControl.setSelectedIndex(7);
    }

    private void generarSerie() {
        int serie = Vdao.IdVenta();
        if (serie == 0) {
            txtSerie.setText("0000001");
        } else {
            int increment = serie;
            increment = increment + 1;
            txtSerie.setText("000000" + increment);
        }
    }

}
