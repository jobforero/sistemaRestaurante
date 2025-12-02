package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclase que representa un Combo de productos en el restaurante.
 * Un Combo contiene multiples productos y aplica un descuento al total.
 * Implementa el principio de composicion en POO.
 * 
 * @author Grupo 1 Desarrollo Software
 * @version 2.1
 * @since 2025
 * @see Producto
 */
public class Combo extends Producto {
    
    /**
     * Lista de productos incluidos en el combo.
     */
    private List<Producto> productos;
    
    /**
     * Porcentaje de descuento aplicado al combo.
     */
    private double descuento;
    
    /**
     * Constructor para crear un nuevo Combo.
     * 
     * @param nombre El nombre del combo, no puede ser nulo o vacio
     * @param descuento El porcentaje de descuento aplicado al combo (0-100)
     */
    public Combo(String nombre, double descuento) {
        super(nombre, 0); // Precio se calculara dinamicamente
        this.productos = new ArrayList<>();
        this.descuento = descuento;
    }
    
    /**
     * Agrega un producto al combo.
     * 
     * @param producto El producto a agregar al combo, no puede ser nulo
     */
    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }
    
    /**
     * Calcula el precio total del combo aplicando el descuento.
     * Suma los precios de todos los productos y aplica el porcentaje de descuento.
     * 
     * @return El precio final del combo con descuento aplicado
     */
    @Override
    public double calcularPrecio() {
        double total = 0;
        // Usando for-each para recorrer todos los productos del combo
        for (Producto producto : productos) {
            total += producto.calcularPrecio();
        }
        return total * (1 - descuento / 100);
    }
    
    /**
     * Obtiene la lista de productos del combo.
     * 
     * @return Lista de productos incluidos en el combo
     */
    public List<Producto> getProductos() {
        return new ArrayList<>(productos); // Retorna copia para proteger encapsulamiento
    }
    
    /**
     * Obtiene el porcentaje de descuento del combo.
     * 
     * @return El porcentaje de descuento aplicado al combo
     */
    public double getDescuento() {
        return descuento;
    }
    
    /**
     * Representacion detallada en String del combo.
     * Muestra el descuento aplicado y el precio final.
     * 
     * @return String con formato que incluye nombre, descuento y precio final
     */
    @Override
    public String toString() {
        return String.format("%s [Combo - %.0f%% descuento] - $%.2f", 
                           nombre, descuento, calcularPrecio());
    }
}