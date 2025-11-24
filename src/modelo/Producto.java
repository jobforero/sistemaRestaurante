package modelo;

/**
 * Clase abstracta que representa un Producto genérico en el sistema del restaurante.
 * Define la estructura base para todos los tipos de productos disponibles.
 * Esta clase implementa el principio de abstracción en POO.
 * 
  * @author José Castrellón
 * @version 2.0
 * @since 2025
 */
public abstract class Producto {
    
    /**
     * Nombre del producto.
     */
    protected String nombre;
    
    /**
     * Precio base del producto.
     */
    protected double precio;
    
    /**
     * Constructor para crear un nuevo Producto.
     * 
     * @param nombre El nombre del producto, no puede ser nulo o vacío
     * @param precio El precio base del producto, debe ser positivo
     */
    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
    
    /**
     * Método abstracto para calcular el precio final del producto.
     * Debe ser implementado por las subclases para aplicar lógica específica
     * como descuentos, recargos, etc. (Principio de polimorfismo).
     * 
     * @return El precio final calculado del producto
     */
    public abstract double calcularPrecio();
    
    /**
     * Obtiene el nombre del producto.
     * 
     * @return El nombre del producto
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el precio base del producto.
     * 
     * @return El precio base del producto
     */
    public double getPrecio() {
        return precio;
    }
    
    /**
     * Representación en String del producto.
     * Muestra el nombre y precio formateado del producto.
     * 
     * @return String con formato que muestra nombre y precio
     */
    @Override
    public String toString() {
        return String.format("%s - $%.2f", nombre, calcularPrecio());
    }
}