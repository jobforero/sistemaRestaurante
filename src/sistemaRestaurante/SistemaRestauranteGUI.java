package sistemaRestaurante;

import modelo.*;
import servicio.GestorFacturas;
import servicio.GestorPedidos;
import servicio.GestorProductos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Sistema principal con interfaz gráfica para gestión de restaurante.
 * Utiliza los servicios separados para la lógica de negocio y proporciona
 * una interfaz de usuario intuitiva para gestionar productos, pedidos y facturas.
 * 
 * @author José Castrellón
 * @version 2.0
 * @since 2025
 */
public class SistemaRestauranteGUI extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Gestor de productos para administrar comidas, bebidas y combos.
     */
    private GestorProductos gestorProductos;
    
    /**
     * Gestor de pedidos para crear y administrar pedidos del restaurante.
     */
    private GestorPedidos gestorPedidos;
    
    /**
     * Gestor de facturas para generar y gestionar facturas de pedidos completados.
     */
    private GestorFacturas gestorFacturas;
    
    /**
     * Panel con pestañas para organizar las diferentes secciones del sistema.
     */
    private JTabbedPane tabbedPane;
    
    /**
     * Tabla para mostrar los productos disponibles en el sistema.
     */
    private JTable tablaProductos;
    
    /**
     * Tabla para mostrar los pedidos realizados en el sistema.
     */
    private JTable tablaPedidos;
    
    /**
     * Modelo de datos para la tabla de productos.
     */
    private DefaultTableModel modelProductos;
    
    /**
     * Modelo de datos para la tabla de pedidos.
     */
    private DefaultTableModel modelPedidos;
    
    /**
     * Constructor principal de la clase SistemaRestauranteGUI.
     * Inicializa los servicios de negocio, configura la ventana principal
     * y carga todos los componentes de la interfaz gráfica.
     */
    public SistemaRestauranteGUI() {
        // Inicializar servicios de negocio
        inicializarServicios();
        
        // Configurar propiedades de la ventana
        configurarVentana();
        
        // Inicializar componentes de la interfaz
        inicializarComponentes();
        
        // Hacer visible la ventana
        setVisible(true);
    }
    
    /**
     * Inicializa los servicios de negocio del sistema.
     * Crea las instancias de los gestores y establece las dependencias entre ellos.
     */
    private void inicializarServicios() {
        this.gestorProductos = new GestorProductos();
        this.gestorPedidos = new GestorPedidos();
        this.gestorFacturas = new GestorFacturas(gestorPedidos);
    }
    
    /**
     * Configura las propiedades básicas de la ventana principal.
     * Establece el título, tamaño, posición, comportamiento de cierre
     * y el look and feel del sistema.
     */
    private void configurarVentana() {
        setTitle("🍽️ Sistema de Gestión de Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // Centrar en la pantalla
        
        // Usar look and feel del sistema para mejor integración
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            System.err.println("Error al configurar el look and feel: " + e.getMessage());
        }
    }
    
    /**
     * Inicializa todos los componentes de la interfaz gráfica.
     * Crea el panel de pestañas y agrega las diferentes secciones del sistema.
     */
    private void inicializarComponentes() {
        tabbedPane = new JTabbedPane();
        
        // Agregar las pestañas principales del sistema
        tabbedPane.addTab("Inicio", crearPanelInicio());
        tabbedPane.addTab("Productos", crearPanelProductos());
        tabbedPane.addTab("Pedidos", crearPanelPedidos());
        tabbedPane.addTab("Facturas", crearPanelFacturas());
        tabbedPane.addTab("Salir", crearPanelSalir());
        
        add(tabbedPane);
    }
    
    /**
     * Crea y configura el panel de inicio del sistema.
     * Muestra estadísticas generales y proporciona acceso rápido a las principales funciones.
     * 
     * @return JPanel configurado como panel de inicio
     */
    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título principal
        JLabel titulo = new JLabel("Bienvenido al Sistema de Gestión de Restaurante", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(44, 62, 80));
        
        // Panel de estadísticas del sistema
        JPanel panelStats = crearPanelEstadisticas();
        
        // Panel de botones de acceso rápido
        JPanel panelBotones = crearPanelBotonesInicio();
        
        // Agregar componentes al panel principal
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelStats, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel de estadísticas del sistema.
     * 
     * @return JPanel con las estadísticas configuradas
     */
    private JPanel crearPanelEstadisticas() {
        JPanel panelStats = new JPanel(new GridLayout(3, 2, 10, 10));
        panelStats.setBorder(BorderFactory.createTitledBorder("Estadísticas del Sistema"));
        
        JLabel lblProductos = new JLabel("Productos Disponibles:");
        JLabel lblProductosCount = new JLabel("0");
        lblProductosCount.setForeground(Color.BLUE);
        
        JLabel lblPedidos = new JLabel("Pedidos Activos:");
        JLabel lblPedidosCount = new JLabel("0");
        lblPedidosCount.setForeground(Color.ORANGE);
        
        JLabel lblFacturas = new JLabel("Facturas Generadas:");
        JLabel lblFacturasCount = new JLabel("0");
        lblFacturasCount.setForeground(Color.GREEN);
        
        panelStats.add(lblProductos);
        panelStats.add(lblProductosCount);
        panelStats.add(lblPedidos);
        panelStats.add(lblPedidosCount);
        panelStats.add(lblFacturas);
        panelStats.add(lblFacturasCount);
        
        return panelStats;
    }
    
    /**
     * Crea el panel de botones de acceso rápido del inicio.
     * 
     * @return JPanel con los botones configurados
     */
    private JPanel crearPanelBotonesInicio() {
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnActualizar = new JButton("Actualizar Estadísticas");
        JButton btnProductos = new JButton("Gestionar Productos");
        JButton btnPedidos = new JButton("Gestionar Pedidos");
        
        // Configurar acciones de los botones
        btnActualizar.addActionListener(e -> actualizarEstadisticas());
        btnProductos.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        btnPedidos.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnProductos);
        panelBotones.add(btnPedidos);
        
        return panelBotones;
    }
    
    /**
     * Actualiza las estadísticas mostradas en el panel de inicio.
     */
    private void actualizarEstadisticas() {
        // Esta implementación actualizaría las estadísticas en tiempo real
        // En una implementación completa, se actualizarían los labels correspondientes
        JOptionPane.showMessageDialog(this, "Estadísticas actualizadas correctamente.");
    }
    
    /**
     * Crea y configura el panel de gestión de productos.
     * Permite visualizar, agregar y administrar los productos del restaurante.
     * 
     * @return JPanel configurado para gestión de productos
     */
    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Configurar modelo y tabla de productos
        configurarTablaProductos();
        
        // Crear panel de botones para productos
        JPanel panelBotones = crearPanelBotonesProductos();
        
        // Agregar componentes al panel principal
        panel.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        // Cargar datos iniciales en la tabla
        actualizarTablaProductos();
        
        return panel;
    }
    
    /**
     * Configura la tabla de productos con sus columnas y propiedades.
     */
    private void configurarTablaProductos() {
        String[] columnas = {"Nombre", "Tipo", "Precio", "Detalles"};
        modelProductos = new DefaultTableModel(columnas, 0) {
            /**
             * Sobrescribe el método para hacer las celdas no editables directamente.
             * 
             * @param row la fila de la celda
             * @param column la columna de la celda
             * @return false para deshabilitar la edición
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProductos = new JTable(modelProductos);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    /**
     * Crea el panel de botones para la gestión de productos.
     * 
     * @return JPanel con los botones de productos configurados
     */
    private JPanel crearPanelBotonesProductos() {
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnAgregarComida = new JButton("Agregar Comida");
        JButton btnAgregarBebida = new JButton("Agregar Bebida");
        JButton btnAgregarCombo = new JButton("Agregar Combo");
        JButton btnActualizar = new JButton("Actualizar Lista");
        
        // Configurar acciones de los botones
        btnAgregarComida.addActionListener(e -> mostrarDialogoAgregarComida());
        btnAgregarBebida.addActionListener(e -> mostrarDialogoAgregarBebida());
        btnAgregarCombo.addActionListener(e -> mostrarDialogoAgregarCombo());
        btnActualizar.addActionListener(e -> actualizarTablaProductos());
        
        panelBotones.add(btnAgregarComida);
        panelBotones.add(btnAgregarBebida);
        panelBotones.add(btnAgregarCombo);
        panelBotones.add(btnActualizar);
        
        return panelBotones;
    }
    
    /**
     * Crea y configura el panel de gestión de pedidos.
     * Permite crear nuevos pedidos, visualizar pedidos existentes y generar facturas.
     * 
     * @return JPanel configurado para gestión de pedidos
     */
    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Configurar modelo y tabla de pedidos
        configurarTablaPedidos();
        
        // Crear panel de botones para pedidos
        JPanel panelBotones = crearPanelBotonesPedidos();
        
        // Agregar componentes al panel principal
        panel.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        // Cargar datos iniciales en la tabla
        actualizarTablaPedidos();
        
        return panel;
    }
    
    /**
     * Configura la tabla de pedidos con sus columnas y propiedades.
     */
    private void configurarTablaPedidos() {
        String[] columnas = {"ID", "Total", "Estado", "Productos"};
        modelPedidos = new DefaultTableModel(columnas, 0) {
            /**
             * Sobrescribe el método para hacer las celdas no editables directamente.
             * 
             * @param row la fila de la celda
             * @param column la columna de la celda
             * @return false para deshabilitar la edición
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPedidos = new JTable(modelPedidos);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    /**
     * Crea el panel de botones para la gestión de pedidos.
     * 
     * @return JPanel con los botones de pedidos configurados
     */
    private JPanel crearPanelBotonesPedidos() {
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnNuevoPedido = new JButton("Nuevo Pedido");
        JButton btnGenerarFactura = new JButton("Generar Factura");
        JButton btnActualizar = new JButton("Actualizar");
        
        // Configurar acciones de los botones
        btnNuevoPedido.addActionListener(e -> mostrarDialogoNuevoPedido());
        btnGenerarFactura.addActionListener(e -> generarFacturaDesdeSeleccion());
        btnActualizar.addActionListener(e -> actualizarTablaPedidos());
        
        panelBotones.add(btnNuevoPedido);
        panelBotones.add(btnGenerarFactura);
        panelBotones.add(btnActualizar);
        
        return panelBotones;
    }
    
    /**
     * Crea y configura el panel de visualización de facturas.
     * Muestra un listado de todas las facturas generadas en el sistema.
     * 
     * @return JPanel configurado para visualización de facturas
     */
    private JPanel crearPanelFacturas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea areaFacturas = new JTextArea();
        areaFacturas.setEditable(false);
        areaFacturas.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JButton btnMostrarFacturas = new JButton("Mostrar Facturas");
        btnMostrarFacturas.addActionListener(e -> mostrarFacturasEnArea(areaFacturas));
        
        panel.add(new JScrollPane(areaFacturas), BorderLayout.CENTER);
        panel.add(btnMostrarFacturas, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea y configura el panel de salida del sistema.
     * Proporciona un botón para cerrar la aplicación de manera controlada.
     * 
     * @return JPanel configurado con botón de salida
     */
    private JPanel crearPanelSalir() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JButton btnSalir = new JButton("🚪 SALIR DEL SISTEMA");
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalir.setPreferredSize(new Dimension(200, 50));
        
        // Acción para salir del sistema con confirmación
        btnSalir.addActionListener(e -> confirmarSalida());
        
        panel.add(btnSalir);
        return panel;
    }
    
    /**
     * Muestra un diálogo de confirmación antes de salir del sistema.
     */
    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro que desea salir del sistema?", 
            "Confirmar Salida", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                this, 
                "¡Gracias por usar el Sistema de Restaurante!\nHasta pronto 👋",
                "Sistema Cerrado",
                JOptionPane.INFORMATION_MESSAGE
            );
            System.exit(0);
        }
    }
    
    /**
     * Muestra un diálogo para agregar una nueva comida al sistema.
     * Solicita al usuario los datos necesarios y valida la entrada usando el GestorProductos.
     */
    private void mostrarDialogoAgregarComida() {
        JTextField txtNombre = new JTextField(20);
        JTextField txtPrecio = new JTextField(10);
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"entrada", "principal", "postre"});
        JCheckBox chkVegetariano = new JCheckBox("Es vegetariano");
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Tipo:"));
        panel.add(cmbTipo);
        panel.add(new JLabel(""));
        panel.add(chkVegetariano);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Comida", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String tipo = (String) cmbTipo.getSelectedItem();
                boolean vegetariano = chkVegetariano.isSelected();
                
                // Usar el GestorProductos para agregar la comida
                gestorProductos.agregarComida(nombre, precio, tipo, vegetariano);
                actualizarTablaProductos();
                JOptionPane.showMessageDialog(this, "Comida agregada exitosamente!");
                
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Muestra un diálogo para agregar una nueva bebida al sistema.
     * Solicita al usuario los datos necesarios y valida la entrada usando el GestorProductos.
     */
    private void mostrarDialogoAgregarBebida() {
        JTextField txtNombre = new JTextField(20);
        JTextField txtPrecio = new JTextField(10);
        JComboBox<String> cmbTamaño = new JComboBox<>(new String[]{"pequeño", "mediano", "grande"});
        JCheckBox chkAlcohol = new JCheckBox("Contiene alcohol");
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Tamaño:"));
        panel.add(cmbTamaño);
        panel.add(new JLabel(""));
        panel.add(chkAlcohol);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Bebida", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String tamaño = (String) cmbTamaño.getSelectedItem();
                boolean alcohol = chkAlcohol.isSelected();
                
                // Usar el GestorProductos para agregar la bebida
                gestorProductos.agregarBebida(nombre, precio, tamaño, alcohol);
                actualizarTablaProductos();
                JOptionPane.showMessageDialog(this, "Bebida agregada exitosamente!");
                
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Muestra un diálogo para agregar un nuevo combo al sistema.
     * Solicita al usuario los datos necesarios y valida la entrada usando el GestorProductos.
     */
    private void mostrarDialogoAgregarCombo() {
        JTextField txtNombre = new JTextField(20);
        JTextField txtDescuento = new JTextField(10);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Nombre del combo:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Descuento (%):"));
        panel.add(txtDescuento);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Combo", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                double descuento = Double.parseDouble(txtDescuento.getText().trim());
                
                // Usar el GestorProductos para agregar el combo
                gestorProductos.agregarCombo(nombre, descuento);
                actualizarTablaProductos();
                JOptionPane.showMessageDialog(this, "Combo agregado exitosamente!");
                
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Muestra un diálogo para crear un nuevo pedido.
     * Permite al usuario seleccionar productos de la lista disponible usando el GestorPedidos.
     */
    private void mostrarDialogoNuevoPedido() {
        // Validar que existan productos disponibles
        if (gestorProductos.getTotalProductos() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear nuevo pedido usando el GestorPedidos
        Pedido nuevoPedido = gestorPedidos.crearPedido();
        boolean continuar = true;
        
        while (continuar) {
            // Obtener lista de productos disponibles
            List<Producto> productos = gestorProductos.getProductosDisponibles();
            String[] opcionesProductos = new String[productos.size()];
            
            for (int i = 0; i < productos.size(); i++) {
                opcionesProductos[i] = productos.get(i).toString();
            }
            
            String productoSeleccionado = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione un producto:",
                "Agregar Producto al Pedido",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesProductos,
                opcionesProductos[0]
            );
            
            if (productoSeleccionado != null) {
                // Buscar el producto seleccionado y agregarlo al pedido
                for (Producto producto : productos) {
                    if (producto.toString().equals(productoSeleccionado)) {
                        gestorPedidos.agregarProductoAPedido(nuevoPedido.getId(), producto);
                        JOptionPane.showMessageDialog(this, 
                            "Producto agregado: " + producto.getNombre() +
                            "\nPrecio: $" + String.format("%.2f", producto.calcularPrecio()));
                        break;
                    }
                }
                
                int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea agregar otro producto?",
                    "Continuar",
                    JOptionPane.YES_NO_OPTION
                );
                
                continuar = (respuesta == JOptionPane.YES_OPTION);
            } else {
                continuar = false;
            }
        }
        
        // Verificar si el pedido tiene productos antes de confirmar
        if (!nuevoPedido.getProductos().isEmpty()) {
            actualizarTablaPedidos();
            JOptionPane.showMessageDialog(this, 
                "Pedido creado exitosamente!\nID: " + nuevoPedido.getId() + 
                "\nTotal: $" + String.format("%.2f", nuevoPedido.calcularTotal()));
        } else {
            // Si el pedido está vacío, eliminarlo
            // En una implementación completa, se eliminaría de la lista
            JOptionPane.showMessageDialog(this, "Pedido cancelado. No se agregaron productos.");
        }
    }
    
    /**
     * Genera una factura para el pedido seleccionado en la tabla.
     * Valida que el pedido esté pendiente y solicita el nombre del cliente usando el GestorFacturas.
     */
    private void generarFacturaDesdeSeleccion() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int idPedido = (Integer) modelPedidos.getValueAt(filaSeleccionada, 0);
        
        String cliente = JOptionPane.showInputDialog(this, "Nombre del cliente:");
        if (cliente != null && !cliente.trim().isEmpty()) {
            try {
                // Usar el GestorFacturas para generar la factura
                Factura factura = gestorFacturas.generarFactura(idPedido, cliente.trim());
                actualizarTablaPedidos();
                
                // Mostrar factura en consola
                factura.imprimirFactura();
                JOptionPane.showMessageDialog(this, 
                    "Factura generada exitosamente!\nNúmero: " + factura.getNumero() +
                    "\nTotal: $" + String.format("%.2f", factura.getTotal()));
                    
            } catch (IllegalArgumentException | IllegalStateException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Actualiza la tabla de productos con los datos actuales del GestorProductos.
     * Clasifica los productos por tipo y formatea la información para mostrar.
     */
    private void actualizarTablaProductos() {
        modelProductos.setRowCount(0);
        List<Producto> productos = gestorProductos.getProductosDisponibles();
        
        // Usar for-each para recorrer todos los productos
        for (Producto producto : productos) {
            if (producto instanceof Comida) {
                Comida comida = (Comida) producto;
                modelProductos.addRow(new Object[]{
                    comida.getNombre(),
                    "Comida (" + comida.getTipo() + ")",
                    String.format("$%.2f", comida.calcularPrecio()),
                    comida.isEsVegetariano() ? "Vegetariano" : "No vegetariano"
                });
            } else if (producto instanceof Bebida) {
                Bebida bebida = (Bebida) producto;
                modelProductos.addRow(new Object[]{
                    bebida.getNombre(),
                    "Bebida (" + bebida.getTamaño() + ")",
                    String.format("$%.2f", bebida.calcularPrecio()),
                    bebida.isConAlcohol() ? "Con alcohol" : "Sin alcohol"
                });
            } else if (producto instanceof Combo) {
                Combo combo = (Combo) producto;
                modelProductos.addRow(new Object[]{
                    combo.getNombre(),
                    "Combo",
                    String.format("$%.2f", combo.calcularPrecio()),
                    String.format("%.0f%% descuento", combo.getDescuento())
                });
            }
        }
    }
    
    /**
     * Actualiza la tabla de pedidos con los datos actuales del GestorPedidos.
     * Muestra información resumida de cada pedido.
     */
    private void actualizarTablaPedidos() {
        modelPedidos.setRowCount(0);
        List<Pedido> pedidos = gestorPedidos.getTodosLosPedidos();
        
        // Usar for-each para recorrer todos los pedidos
        for (Pedido pedido : pedidos) {
            modelPedidos.addRow(new Object[]{
                pedido.getId(),
                String.format("$%.2f", pedido.calcularTotal()),
                pedido.getEstado(),
                pedido.getProductos().size() + " productos"
            });
        }
    }
    
    /**
     * Muestra las facturas generadas en el área de texto especificada.
     * Formatea la información de las facturas para su visualización usando el GestorFacturas.
     * 
     * @param area el JTextArea donde se mostrarán las facturas
     */
    private void mostrarFacturasEnArea(JTextArea area) {
        List<Factura> facturas = gestorFacturas.getTodasLasFacturas();
        
        if (facturas.isEmpty()) {
            area.setText("No hay facturas generadas.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== FACTURAS GENERADAS ===\n\n");
        
        // Usar for-each para recorrer todas las facturas
        for (Factura factura : facturas) {
            sb.append("Factura #").append(factura.getNumero())
              .append(" - Cliente: ").append(factura.getCliente())
              .append(" - Total: $").append(String.format("%.2f", factura.getTotal()))
              .append("\n");
        }
        
        sb.append("\nTotal facturado: $").append(String.format("%.2f", gestorFacturas.getTotalFacturado()));
        area.setText(sb.toString());
    }
    
    /**
     * Método principal que inicia la aplicación.
     * Ejecuta la interfaz gráfica en el Event Dispatch Thread de Swing.
     * 
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread para mejor rendimiento
        SwingUtilities.invokeLater(() -> {
            new SistemaRestauranteGUI();
        });
    }
}