package modelo;

/**
 * Subclase que representa un producto de tipo Comida en el restaurante.
 * Hereda de la clase Producto y añade características específicas de comida.
 * Implementa el principio de herencia en POO.
 * 
 * @author José Castrellón
 * @version 2.0
 * @since 202
 * @see Producto
 */
public class Comida extends Producto {
    
    /**
     * Tipo de comida: "entrada", "principal" o "postre".
     */
    private String tipo;
    
    /**
     * Indica si la comida es vegetariana.
     */
    private boolean esVegetariano;
    
    /**
     * Constructor para crear una nueva Comida.
     * 
     * @param nombre El nombre de la comida, no puede ser nulo o vacío
     * @param precio El precio base de la comida, debe ser positivo
     * @param tipo El tipo de comida (entrada, principal, postre)
     * @param esVegetariano Indica si la comida es vegetariana
     */
    public Comida(String nombre, double precio, String tipo, boolean esVegetariano) {
        super(nombre, precio);
        this.tipo = tipo;
        this.esVegetariano = esVegetariano;
    }
    
    /**
     * Calcula el precio final de la comida.
     * Para comidas simples, devuelve el precio base sin modificaciones.
     * 
     * @return El precio final de la comida
     */
    @Override
    public double calcularPrecio() {
        return precio; // Precio base para comida sin modificaciones
    }
    
    /**
     * Obtiene el tipo de comida.
     * 
     * @return El tipo de comida (entrada, principal, postre)
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Verifica si la comida es vegetariana.
     * 
     * @return true si es vegetariana, false en caso contrario
     */
    public boolean isEsVegetariano() {
        return esVegetariano;
    }
    
    /**
     * Representación detallada en String de la comida.
     * Incluye información sobre si es vegetariana.
     * 
     * @return String con formato que incluye nombre, tipo, precio y si es vegetariana
     */
    @Override
    public String toString() {
        String veg = esVegetariano ? " (Vegetariano)" : "";
        return String.format("%s [%s] - $%.2f%s", nombre, tipo, calcularPrecio(), veg);
    }
}