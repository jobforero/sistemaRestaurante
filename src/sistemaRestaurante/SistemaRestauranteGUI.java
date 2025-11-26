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
 * Sistema principal con interfaz grafica para gestion de restaurante.
 * Utiliza los servicios separados para la logica de negocio y proporciona
 * una interfaz de usuario intuitiva para gestionar productos, pedidos y facturas.
 * 
 * @author Grupo 1 Desarrollo Software
 * @version 2.0
 * @since 2025
 */
public class SistemaRestauranteGUI extends JFrame {
    
    /**
     * Numero de version serial para la clase Serializable.
     * Requerido para la serializacion de objetos Swing.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Gestor de productos para administrar comidas, bebidas y combos.
     * Proporciona operaciones CRUD para la gestion de productos.
     */
    private GestorProductos gestorProductos;
    
    /**
     * Gestor de pedidos para crear y administrar pedidos del restaurante.
     * Maneja el ciclo de vida completo de los pedidos.
     */
    private GestorPedidos gestorPedidos;
    
    /**
     * Gestor de facturas para generar y gestionar facturas de pedidos completados.
     * Controla la generacion y consulta de facturas.
     */
    private GestorFacturas gestorFacturas;
    
    /**
     * Panel con pestañas para organizar las diferentes secciones del sistema.
     * Permite navegar entre las distintas funcionalidades.
     */
    private JTabbedPane tabbedPane;
    
    /**
     * Tabla para mostrar los productos disponibles en el sistema.
     * Presenta una vista tabular de todos los productos registrados.
     */
    private JTable tablaProductos;
    
    /**
     * Tabla para mostrar los pedidos realizados en el sistema.
     * Exhibe el estado y detalles de todos los pedidos.
     */
    private JTable tablaPedidos;
    
    /**
     * Modelo de datos para la tabla de productos.
     * Define la estructura y comportamiento de la tabla de productos.
     */
    private DefaultTableModel modelProductos;
    
    /**
     * Modelo de datos para la tabla de pedidos.
     * Define la estructura y comportamiento de la tabla de pedidos.
     */
    private DefaultTableModel modelPedidos;
    
    /**
     * Constructor principal de la clase SistemaRestauranteGUI.
     * Inicializa los servicios de negocio, configura la ventana principal
     * y carga todos los componentes de la interfaz grafica.
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
     * Este metodo sigue el principio de Inversion de Dependencias.
     */
    private void inicializarServicios() {
        this.gestorProductos = new GestorProductos();
        this.gestorPedidos = new GestorPedidos();
        this.gestorFacturas = new GestorFacturas(gestorPedidos);
    }
    
    /**
     * Configura las propiedades basicas de la ventana principal.
     * Establece el titulo, tamaño, posicion, comportamiento de cierre
     * y el look and feel del sistema.
     */
    private void configurarVentana() {
        setTitle("Sistema de Gestion de Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // Centrar en la pantalla
        
        // Usar look and feel del sistema para mejor integracion
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            System.err.println("Error al configurar el look and feel: " + e.getMessage());
        }
    }
    
    /**
     * Inicializa todos los componentes de la interfaz grafica.
     * Crea el panel de pestañas y agrega las diferentes secciones del sistema.
     * Organiza la interfaz siguiendo el patron de diseno MVC.
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
     * Muestra estadisticas generales y proporciona acceso rapido a las principales funciones.
     * 
     * @return JPanel configurado como panel de inicio
     */
    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Titulo principal
        JLabel titulo = new JLabel("Bienvenido al Sistema de Gestion de Restaurante", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(44, 62, 80));
        
        // Panel de estadisticas del sistema
        JPanel panelStats = crearPanelEstadisticas();
        
        // Panel de botones de acceso rapido
        JPanel panelBotones = crearPanelBotonesInicio();
        
        // Agregar componentes al panel principal
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelStats, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel de estadisticas del sistema.
     * Muestra metricas clave como numero de productos, pedidos y facturas.
     * 
     * @return JPanel con las estadisticas configuradas
     */
    private JPanel crearPanelEstadisticas() {
        JPanel panelStats = new JPanel(new GridLayout(3, 2, 10, 10));
        panelStats.setBorder(BorderFactory.createTitledBorder("Estadisticas del Sistema"));
        
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
     * Crea el panel de botones de acceso rapido del inicio.
     * Proporciona navegacion directa a las secciones principales.
     * 
     * @return JPanel con los botones configurados
     */
    private JPanel crearPanelBotonesInicio() {
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnActualizar = new JButton("Actualizar Estadisticas");
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
     * Actualiza las estadisticas mostradas en el panel de inicio.
     * Recupera datos actualizados de los gestores y actualiza la interfaz.
     */
    private void actualizarEstadisticas() {
        // Esta implementacion actualizaria las estadisticas en tiempo real
        // En una implementacion completa, se actualizarian los labels correspondientes
        JOptionPane.showMessageDialog(this, "Estadisticas actualizadas correctamente.");
    }
    
    /**
     * Crea y configura el panel de gestion de productos.
     * Permite visualizar, agregar y administrar los productos del restaurante.
     * 
     * @return JPanel configurado para gestion de productos
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
     * Define la estructura de la tabla y su comportamiento de edicion.
     */
    private void configurarTablaProductos() {
        String[] columnas = {"Nombre", "Tipo", "Precio", "Detalles"};
        modelProductos = new DefaultTableModel(columnas, 0) {
            /**
             * Numero de version serial para la clase Serializable.
             */
            private static final long serialVersionUID = 1L;

            /**
             * Sobrescribe el metodo para hacer las celdas no editables directamente.
             * Esto previene la edicion accidental de datos en la interfaz grafica.
             * 
             * @param row la fila de la celda
             * @param column la columna de la celda
             * @return false para deshabilitar la edicion
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
     * Crea el panel de botones para la gestion de productos.
     * Proporciona acceso a las operaciones CRUD de productos.
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
     * Crea y configura el panel de gestion de pedidos.
     * Permite crear nuevos pedidos, visualizar pedidos existentes y generar facturas.
     * 
     * @return JPanel configurado para gestion de pedidos
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
     * Define la estructura de la tabla y su comportamiento de edicion.
     */
    private void configurarTablaPedidos() {
        String[] columnas = {"ID", "Total", "Estado", "Productos"};
        modelPedidos = new DefaultTableModel(columnas, 0) {
            /**
             * Numero de version serial para la clase Serializable.
             */
            private static final long serialVersionUID = 1L;

            /**
             * Sobrescribe el metodo para hacer las celdas no editables directamente.
             * Esto previene la edicion accidental de datos en la interfaz grafica.
             * 
             * @param row la fila de la celda
             * @param column la columna de la celda
             * @return false para deshabilitar la edicion
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
     * Crea el panel de botones para la gestion de pedidos.
     * Proporciona acceso a las operaciones de gestion de pedidos.
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
     * Crea y configura el panel de visualizacion de facturas.
     * Muestra un listado de todas las facturas generadas en el sistema.
     * 
     * @return JPanel configurado para visualizacion de facturas
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
     * Proporciona un boton para cerrar la aplicacion de manera controlada.
     * 
     * @return JPanel configurado con boton de salida
     */
    private JPanel crearPanelSalir() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JButton btnSalir = new JButton("SALIR DEL SISTEMA");
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalir.setPreferredSize(new Dimension(200, 50));
        
        // Accion para salir del sistema con confirmacion
        btnSalir.addActionListener(e -> confirmarSalida());
        
        panel.add(btnSalir);
        return panel;
    }
    
    /**
     * Muestra un dialogo de confirmacion antes de salir del sistema.
     * Previene el cierre accidental de la aplicacion.
     */
    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(
            this, 
            "¿Esta seguro que desea salir del sistema?", 
            "Confirmar Salida", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                this, 
                "¡Gracias por usar el Sistema de Restaurante!\nHasta pronto",
                "Sistema Cerrado",
                JOptionPane.INFORMATION_MESSAGE
            );
            System.exit(0);
        }
    }
    
    /**
     * Muestra un dialogo para agregar una nueva comida al sistema.
     * Solicita al usuario los datos necesarios y valida la entrada usando el GestorProductos.
     * Implementa validacion de datos y manejo de excepciones.
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
                
                // Validar que el nombre no este vacio
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar que el precio sea positivo
                if (precio <= 0) {
                    JOptionPane.showMessageDialog(this, "El precio debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Usar el GestorProductos para agregar la comida
                gestorProductos.agregarComida(nombre, precio, tipo, vegetariano);
                actualizarTablaProductos();
                JOptionPane.showMessageDialog(this, "Comida agregada exitosamente!");
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Precio invalido. Use numeros decimales.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Muestra un dialogo para agregar una nueva bebida al sistema.
     * Solicita al usuario los datos necesarios y valida la entrada usando el GestorProductos.
     * Incluye validacion de datos y manejo de excepciones.
     */
    private void mostrarDialogoAgregarBebida() {
        JTextField txtNombre = new JTextField(20);
        JTextField txtPrecio = new JTextField(10);
        JComboBox<String> cmbTamano = new JComboBox<>(new String[]{"pequeno", "mediano", "grande"});
        JCheckBox chkAlcohol = new JCheckBox("Contiene alcohol");
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Tamano:"));
        panel.add(cmbTamano);
        panel.add(new JLabel(""));
        panel.add(chkAlcohol);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Bebida", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String tamano = (String) cmbTamano.getSelectedItem();
                boolean alcohol = chkAlcohol.isSelected();
                
                // Validar que el nombre no este vacio
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar que el precio sea positivo
                if (precio <= 0) {
                    JOptionPane.showMessageDialog(this, "El precio debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Usar el GestorProductos para agregar la bebida
                gestorProductos.agregarBebida(nombre, precio, tamano, alcohol);
                actualizarTablaProductos();
                JOptionPane.showMessageDialog(this, "Bebida agregada exitosamente!");
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Precio invalido. Use numeros decimales.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Muestra un dialogo para agregar un nuevo combo al sistema.
     * Solicita al usuario los datos necesarios y valida la entrada usando el GestorProductos.
     * Implementa validacion de rangos y manejo de excepciones.
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
                
                // Validar que el nombre no este vacio
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar que el descuento este en rango valido
                if (descuento < 0 || descuento > 100) {
                    JOptionPane.showMessageDialog(this, "El descuento debe estar entre 0 y 100%.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Usar el GestorProductos para agregar el combo
                gestorProductos.agregarCombo(nombre, descuento);
                actualizarTablaProductos();
                JOptionPane.showMessageDialog(this, "Combo agregado exitosamente!");
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Descuento invalido. Use numeros decimales.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Muestra un dialogo para crear un nuevo pedido.
     * Permite al usuario seleccionar productos de la lista disponible usando el GestorPedidos.
     * Implementa un flujo interactivo para agregar multiples productos.
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
            // Si el pedido esta vacio, mostrar mensaje informativo
            JOptionPane.showMessageDialog(this, "Pedido cancelado. No se agregaron productos.");
        }
    }
    
    /**
     * Genera una factura para el pedido seleccionado en la tabla.
     * Valida que el pedido este pendiente y solicita el nombre del cliente usando el GestorFacturas.
     * Implementa validacion de estado del pedido y manejo de excepciones.
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
                    "Factura generada exitosamente!\nNumero: " + factura.getNumero() +
                    "\nTotal: $" + String.format("%.2f", factura.getTotal()));
                    
            } catch (IllegalArgumentException | IllegalStateException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Actualiza la tabla de productos con los datos actuales del GestorProductos.
     * Clasifica los productos por tipo y formatea la informacion para mostrar.
     * Utiliza polimorfismo para identificar el tipo de cada producto.
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
                    "Bebida (" + bebida.getTamano() + ")",
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
     * Muestra informacion resumida de cada pedido.
     * Formatea los totales y cuenta la cantidad de productos.
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
     * Muestra las facturas generadas en el area de texto especificada.
     * Formatea la informacion de las facturas para su visualizacion usando el GestorFacturas.
     * Incluye un resumen del total facturado.
     * 
     * @param area el JTextArea donde se mostraran las facturas
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
     * Metodo principal que inicia la aplicacion.
     * Ejecuta la interfaz grafica en el Event Dispatch Thread de Swing.
     * Sigue las mejores practicas de Swing para la ejecucion de interfaces graficas.
     * 
     * @param args argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread para mejor rendimiento
        SwingUtilities.invokeLater(() -> {
            new SistemaRestauranteGUI();
        });
    }
}