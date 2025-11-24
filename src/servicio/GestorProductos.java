package servicio;

import modelo.Bebida;
import modelo.Combo;
import modelo.Comida;
import modelo.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase servicio para gestionar todas las operaciones relacionadas con productos.
 * Proporciona métodos para agregar, buscar y administrar productos del restaurante.
 * 
 * @author José Castrellón
 * @version 2.0
 * @since 2025
 */
public class GestorProductos {
    private List<Producto> productosDisponibles;
    
    /**
     * Constructor que inicializa la lista de productos disponibles.
     */
    public GestorProductos() {
        this.productosDisponibles = new ArrayList<>();
        inicializarProductosEjemplo();
    }
    
    /**
     * Obtiene la lista completa de productos disponibles.
     * 
     * @return Lista de productos disponibles
     */
    public List<Producto> getProductosDisponibles() {
        return new ArrayList<>(productosDisponibles);
    }
    
    /**
     * Agrega una nueva comida al sistema.
     * 
     * @param nombre el nombre de la comida
     * @param precio el precio de la comida
     * @param tipo el tipo de comida (entrada, principal, postre)
     * @param esVegetariano indica si la comida es vegetariana
     * @throws IllegalArgumentException si el precio no es positivo o el nombre está vacío
     */
    public void agregarComida(String nombre, double precio, String tipo, boolean esVegetariano) {
        validarProducto(nombre, precio);
        productosDisponibles.add(new Comida(nombre, precio, tipo, esVegetariano));
    }
    
    /**
     * Agrega una nueva bebida al sistema.
     * 
     * @param nombre el nombre de la bebida
     * @param precio el precio base de la bebida
     * @param tamaño el tamaño de la bebida (pequeño, mediano, grande)
     * @param conAlcohol indica si la bebida contiene alcohol
     * @throws IllegalArgumentException si el precio no es positivo o el nombre está vacío
     */
    public void agregarBebida(String nombre, double precio, String tamaño, boolean conAlcohol) {
        validarProducto(nombre, precio);
        productosDisponibles.add(new Bebida(nombre, precio, tamaño, conAlcohol));
    }
    
    /**
     * Agrega un nuevo combo al sistema.
     * 
     * @param nombre el nombre del combo
     * @param descuento el porcentaje de descuento del combo
     * @throws IllegalArgumentException si el descuento no está entre 0 y 100 o el nombre está vacío
     */
    public void agregarCombo(String nombre, double descuento) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100%.");
        }
        
        Combo combo = new Combo(nombre, descuento);
        productosDisponibles.add(combo);
    }
    
    /**
     * Busca un producto por su nombre.
     * 
     * @param nombre el nombre del producto a buscar
     * @return el producto encontrado o null si no existe
     */
    public Producto buscarProductoPorNombre(String nombre) {
        return productosDisponibles.stream()
            .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Obtiene productos filtrados por tipo.
     * 
     * @param tipo el tipo de producto a filtrar ("Comida", "Bebida", "Combo")
     * @return lista de productos del tipo especificado
     */
    public List<Producto> getProductosPorTipo(String tipo) {
        List<Producto> resultado = new ArrayList<>();
        
        for (Producto producto : productosDisponibles) {
            switch (tipo) {
                case "Comida":
                    if (producto instanceof Comida) resultado.add(producto);
                    break;
                case "Bebida":
                    if (producto instanceof Bebida) resultado.add(producto);
                    break;
                case "Combo":
                    if (producto instanceof Combo) resultado.add(producto);
                    break;
            }
        }
        
        return resultado;
    }
    
    /**
     * Obtiene el número total de productos disponibles.
     * 
     * @return el conteo de productos disponibles
     */
    public int getTotalProductos() {
        return productosDisponibles.size();
    }
    
    /**
     * Valida los datos básicos de un producto antes de agregarlo.
     * 
     * @param nombre el nombre del producto
     * @param precio el precio del producto
     * @throws IllegalArgumentException si la validación falla
     */
    private void validarProducto(String nombre, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo.");
        }
    }
    
    /**
     * Inicializa el sistema con productos de ejemplo para demostración.
     */
    private void inicializarProductosEjemplo() {
        // Comidas de ejemplo
        agregarComida("Hamburguesa Clásica", 12.99, "principal", false);
        agregarComida("Ensalada César", 8.50, "entrada", true);
        agregarComida("Pizza Margarita", 15.99, "principal", true);
        agregarComida("Tiramisú", 6.99, "postre", true);
        
        // Bebidas de ejemplo
        agregarBebida("Coca-Cola", 2.50, "mediano", false);
        agregarBebida("Cerveza Artesanal", 5.99, "grande", true);
        agregarBebida("Agua Mineral", 1.50, "pequeño", false);
        
        // Combos de ejemplo
        Combo comboFamiliar = new Combo("Combo Familiar", 15);
        comboFamiliar.agregarProducto(new Comida("Pizza Familiar", 25.99, "principal", true));
        comboFamiliar.agregarProducto(new Bebida("Refresco", 3.50, "grande", false));
        comboFamiliar.agregarProducto(new Comida("Helado", 4.99, "postre", true));
        productosDisponibles.add(comboFamiliar);
    }
}