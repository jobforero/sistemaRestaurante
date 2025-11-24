package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Pedido en el sistema del restaurante.
 * Un pedido contiene una lista de productos y gestiona su estado.
 * Implementa el principio de encapsulamiento en POO.
 * 
 * @author Ing. Sistema Restaurante
 * @version 2.0
 * @since 2024
 */
public class Pedido {
    
    /**
     * Contador estático para generar IDs únicos automáticamente.
     */
    private static int contadorId = 1;
    
    /**
     * ID único del pedido.
     */
    private int id;
    
    /**
     * Lista de productos en el pedido.
     */
    private List<Producto> productos;
    
    /**
     * Fecha y hora en que se creó el pedido.
     */
    private LocalDateTime fecha;
    
    /**
     * Estado actual del pedido: "pendiente", "completado" o "cancelado".
     */
    private String estado;
    
    /**
     * Constructor para crear un nuevo Pedido.
     * Asigna automáticamente un ID único y establece la fecha actual.
     * Inicializa el estado como "pendiente".
     */
    public Pedido() {
        this.id = contadorId++;
        this.productos = new ArrayList<>();
        this.fecha = LocalDateTime.now();
        this.estado = "pendiente";
    }
    
    /**
     * Agrega un producto al pedido.
     * 
     * @param producto El producto a agregar al pedido, no puede ser nulo
     */
    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }
    
    /**
     * Calcula el total del pedido sumando los precios de todos los productos.
     * Utiliza un bucle for-each para recorrer la lista de productos.
     * 
     * @return El total calculado del pedido
     */
    public double calcularTotal() {
        double total = 0;
        // Usando for-each para recorrer todos los productos del pedido
        for (Producto producto : productos) {
            total += producto.calcularPrecio();
        }
        return total;
    }
    
    /**
     * Obtiene el ID único del pedido.
     * 
     * @return El ID del pedido
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene la lista de productos del pedido.
     * 
     * @return Lista de productos en el pedido (copia para proteger encapsulamiento)
     */
    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }
    
    /**
     * Obtiene la fecha y hora en que se creó el pedido.
     * 
     * @return La fecha de creación del pedido
     */
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    /**
     * Obtiene el estado actual del pedido.
     * 
     * @return El estado del pedido (pendiente, completado, cancelado)
     */
    public String getEstado() {
        return estado;
    }
    
    /**
     * Establece el estado del pedido.
     * 
     * @param estado El nuevo estado del pedido
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Representación en String del pedido.
     * Muestra información resumida del pedido.
     * 
     * @return String con formato que muestra ID, total y estado
     */
    @Override
    public String toString() {
        return String.format("Pedido #%d - Total: $%.2f - Estado: %s", 
                           id, calcularTotal(), estado);
    }
    
    /**
     * Verifica si el pedido está vacío (no tiene productos).
     * 
     * @return true si el pedido no tiene productos, false en caso contrario
     */
    public boolean estaVacio() {
        return productos.isEmpty();
    }
    
    /**
     * Obtiene la cantidad de productos en el pedido.
     * 
     * @return El número de productos en el pedido
     */
    public int getCantidadProductos() {
        return productos.size();
    }
}