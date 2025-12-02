package sistemaRestaurante;

import modelo.*;
import servicio.GestorPedidos;
import servicio.GestorProductos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Interfaz grafica para que los clientes realicen sus pedidos.
 * Proporciona una experiencia simple e intuitiva para seleccionar productos
 * y generar pedidos que se integran con el sistema principal.
 * 
 * @author Grupo 1 Desarrollo Software
 * @version 1.0
 * @since 2025
 */
public class ClientePedidoGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Gestores compartidos con el sistema principal.
     */
    private GestorProductos gestorProductos;
    private GestorPedidos gestorPedidos;

    /**
     * Componentes de la interfaz.
     */
    private JTextField txtNombreCliente;
    private JTable tablaProductos;
    private JTable tablaCarrito;
    private DefaultTableModel modelProductos;
    private DefaultTableModel modelCarrito;
    private JLabel lblTotal;
    private Pedido pedidoActual;

    /**
     * Constructor que inicializa la interfaz de cliente.
     * 
     * @param gestorProductos gestor de productos compartido
     * @param gestorPedidos   gestor de pedidos compartido
     */
    public ClientePedidoGUI(GestorProductos gestorProductos, GestorPedidos gestorPedidos) {
        this.gestorProductos = gestorProductos;
        this.gestorPedidos = gestorPedidos;

        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    /**
     * Configura las propiedades de la ventana.
     */
    private void configurarVentana() {
        setTitle("Sistema de Pedidos - Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al configurar look and feel: " + e.getMessage());
        }
    }

    /**
     * Inicializa todos los componentes de la interfaz.
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior con bienvenida y nombre del cliente
        add(crearPanelSuperior(), BorderLayout.NORTH);

        // Panel central con productos y carrito
        add(crearPanelCentral(), BorderLayout.CENTER);

        // Panel inferior con total y botones
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    /**
     * Crea el panel superior con bienvenida.
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(52, 152, 219));

        // Titulo
        JLabel lblTitulo = new JLabel("¡Bienvenido! Realiza tu Pedido", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        // Panel para nombre del cliente
        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNombre.setBackground(new Color(52, 152, 219));

        JLabel lblNombre = new JLabel("Tu Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);

        txtNombreCliente = new JTextField(25);
        txtNombreCliente.setFont(new Font("Arial", Font.PLAIN, 14));

        panelNombre.add(lblNombre);
        panelNombre.add(txtNombreCliente);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(panelNombre, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel central con productos disponibles y carrito.
     */
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Panel de productos disponibles
        panel.add(crearPanelProductos());

        // Panel del carrito
        panel.add(crearPanelCarrito());

        return panel;
    }

    /**
     * Crea el panel de productos disponibles.
     */
    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                "Productos Disponibles",
                0,
                0,
                new Font("Arial", Font.BOLD, 16),
                new Color(46, 204, 113)));

        // Configurar tabla de productos
        String[] columnas = { "Producto", "Tipo", "Precio" };
        modelProductos = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modelProductos);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaProductos.setRowHeight(25);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Cargar productos
        actualizarTablaProductos();

        // Boton para agregar al carrito
        JButton btnAgregar = new JButton("+ Agregar al Carrito");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgregar.setBackground(new Color(46, 204, 113));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> agregarAlCarrito());

        panel.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        panel.add(btnAgregar, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel del carrito de compras.
     */
    private JPanel crearPanelCarrito() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
                "Tu Carrito",
                0,
                0,
                new Font("Arial", Font.BOLD, 16),
                new Color(231, 76, 60)));

        // Configurar tabla del carrito
        String[] columnas = { "Producto", "Precio" };
        modelCarrito = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCarrito = new JTable(modelCarrito);
        tablaCarrito.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaCarrito.setRowHeight(25);
        tablaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Boton para quitar del carrito
        JButton btnQuitar = new JButton("- Quitar del Carrito");
        btnQuitar.setFont(new Font("Arial", Font.BOLD, 14));
        btnQuitar.setBackground(new Color(231, 76, 60));
        btnQuitar.setForeground(Color.WHITE);
        btnQuitar.setFocusPainted(false);
        btnQuitar.addActionListener(e -> quitarDelCarrito());

        panel.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);
        panel.add(btnQuitar, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel inferior con total y botones de accion.
     */
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(236, 240, 241));

        // Panel del total
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTotal.setBackground(new Color(236, 240, 241));

        JLabel lblTotalTexto = new JLabel("TOTAL:");
        lblTotalTexto.setFont(new Font("Arial", Font.BOLD, 24));

        lblTotal = new JLabel("$0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 28));
        lblTotal.setForeground(new Color(46, 204, 113));

        panelTotal.add(lblTotalTexto);
        panelTotal.add(lblTotal);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(new Color(236, 240, 241));

        JButton btnRealizarPedido = new JButton("Realizar Pedido");
        btnRealizarPedido.setFont(new Font("Arial", Font.BOLD, 16));
        btnRealizarPedido.setBackground(new Color(39, 174, 96));
        btnRealizarPedido.setForeground(Color.WHITE);
        btnRealizarPedido.setPreferredSize(new Dimension(200, 45));
        btnRealizarPedido.setFocusPainted(false);
        btnRealizarPedido.addActionListener(e -> realizarPedido());

        JButton btnLimpiar = new JButton("Limpiar Carrito");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 16));
        btnLimpiar.setBackground(new Color(243, 156, 18));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setPreferredSize(new Dimension(200, 45));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(e -> limpiarCarrito());

        panelBotones.add(btnRealizarPedido);
        panelBotones.add(btnLimpiar);

        panel.add(panelTotal, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Actualiza la tabla de productos disponibles.
     */
    private void actualizarTablaProductos() {
        modelProductos.setRowCount(0);
        List<Producto> productos = gestorProductos.getProductosDisponibles();

        for (Producto producto : productos) {
            String tipo = "";
            if (producto instanceof Comida) {
                Comida comida = (Comida) producto;
                tipo = "Comida (" + comida.getTipo() + ")";
            } else if (producto instanceof Bebida) {
                Bebida bebida = (Bebida) producto;
                tipo = "Bebida (" + bebida.getTamano() + ")";
            } else if (producto instanceof Combo) {
                tipo = "Combo";
            }

            modelProductos.addRow(new Object[] {
                    producto.getNombre(),
                    tipo,
                    String.format("$%.2f", producto.calcularPrecio())
            });
        }
    }

    /**
     * Agrega el producto seleccionado al carrito.
     */
    private void agregarAlCarrito() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecciona un producto primero.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el producto seleccionado
        List<Producto> productos = gestorProductos.getProductosDisponibles();
        Producto productoSeleccionado = productos.get(filaSeleccionada);

        // Crear pedido temporal si no existe
        if (pedidoActual == null) {
            pedidoActual = new Pedido();
        }

        // Agregar producto al pedido temporal
        pedidoActual.agregarProducto(productoSeleccionado);

        // Actualizar carrito visual
        modelCarrito.addRow(new Object[] {
                productoSeleccionado.getNombre(),
                String.format("$%.2f", productoSeleccionado.calcularPrecio())
        });

        // Actualizar total
        actualizarTotal();
    }

    /**
     * Quita el producto seleccionado del carrito.
     */
    private void quitarDelCarrito() {
        int filaSeleccionada = tablaCarrito.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecciona un producto del carrito primero.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Quitar de la tabla visual
        modelCarrito.removeRow(filaSeleccionada);

        // Recrear el pedido temporal con los productos restantes
        if (pedidoActual != null) {
            List<Producto> productosActuales = pedidoActual.getProductos();
            if (filaSeleccionada < productosActuales.size()) {
                productosActuales.remove(filaSeleccionada);

                // Recrear pedido
                pedidoActual = new Pedido();
                for (Producto p : productosActuales) {
                    pedidoActual.agregarProducto(p);
                }
            }
        }

        // Actualizar total
        actualizarTotal();
    }

    /**
     * Limpia todo el carrito.
     */
    private void limpiarCarrito() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas limpiar todo el carrito?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            modelCarrito.setRowCount(0);
            pedidoActual = null;
            actualizarTotal();
        }
    }

    /**
     * Actualiza el total mostrado.
     */
    private void actualizarTotal() {
        double total = 0;
        if (pedidoActual != null) {
            total = pedidoActual.calcularTotal();
        }
        lblTotal.setText(String.format("$%.2f", total));
    }

    /**
     * Realiza el pedido y lo registra en el sistema.
     */
    private void realizarPedido() {
        // Validar nombre del cliente
        String nombreCliente = txtNombreCliente.getText().trim();
        if (nombreCliente.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingresa tu nombre antes de realizar el pedido.",
                    "Nombre Requerido",
                    JOptionPane.WARNING_MESSAGE);
            txtNombreCliente.requestFocus();
            return;
        }

        // Validar que haya productos en el carrito
        if (pedidoActual == null || pedidoActual.estaVacio()) {
            JOptionPane.showMessageDialog(this,
                    "Tu carrito está vacío. Agrega productos antes de realizar el pedido.",
                    "Carrito Vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear el pedido oficial en el sistema
        Pedido pedidoOficial = gestorPedidos.crearPedidoCliente(nombreCliente);

        // Copiar productos del pedido temporal al oficial
        for (Producto producto : pedidoActual.getProductos()) {
            gestorPedidos.agregarProductoAPedido(pedidoOficial.getId(), producto);
        }

        // Mostrar confirmacion
        JOptionPane.showMessageDialog(this,
                "¡Pedido realizado exitosamente!\n\n" +
                        "Número de Pedido: #" + pedidoOficial.getId() + "\n" +
                        "Cliente: " + nombreCliente + "\n" +
                        "Total: $" + String.format("%.2f", pedidoOficial.calcularTotal()) + "\n\n" +
                        "Tu pedido está siendo procesado.",
                "Pedido Confirmado",
                JOptionPane.INFORMATION_MESSAGE);

        // Limpiar formulario
        txtNombreCliente.setText("");
        modelCarrito.setRowCount(0);
        pedidoActual = null;
        actualizarTotal();
    }
}
