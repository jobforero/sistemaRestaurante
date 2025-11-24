package modelo;

/**
 * Subclase que representa un producto de tipo Bebida en el restaurante.
 * Hereda de la clase Producto y aplica recargos según el tamaño.
 * Implementa el principio de polimorfismo al sobreescribir calcularPrecio().
 * 
 * @author José Castrellón
 * @version 2.0
 * @since 2025
 * @see Producto
 */
public class Bebida extends Producto {
    
    /**
     * Tamaño de la bebida: "pequeño", "mediano" o "grande".
     */
    private String tamaño;
    
    /**
     * Indica si la bebida contiene alcohol.
     */
    private boolean conAlcohol;
    
    /**
     * Constructor para crear una nueva Bebida.
     * 
     * @param nombre El nombre de la bebida, no puede ser nulo o vacío
     * @param precio El precio base de la bebida (tamaño pequeño), debe ser positivo
     * @param tamaño El tamaño de la bebida (pequeño, mediano, grande)
     * @param conAlcohol Indica si la bebida contiene alcohol
     */
    public Bebida(String nombre, double precio, String tamaño, boolean conAlcohol) {
        super(nombre, precio);
        this.tamaño = tamaño;
        this.conAlcohol = conAlcohol;
    }
    
    /**
     * Calcula el precio final de la bebida aplicando recargos por tamaño.
     * Mediano: 20% de recargo, Grande: 40% de recargo.
     * 
     * @return El precio final de la bebida con recargos aplicados
     */
    @Override
    public double calcularPrecio() {
        // Aplicar recargo por tamaño usando switch-case
        double precioFinal = precio;
        switch(tamaño.toLowerCase()) {
            case "mediano":
                precioFinal *= 1.2; // 20% de recargo
                break;
            case "grande":
                precioFinal *= 1.4; // 40% de recargo
                break;
            // Para tamaño "pequeño" no se aplica recargo
        }
        return precioFinal;
    }
    
    /**
     * Obtiene el tamaño de la bebida.
     * 
     * @return El tamaño de la bebida
     */
    public String getTamaño() {
        return tamaño;
    }
    
    /**
     * Verifica si la bebida contiene alcohol.
     * 
     * @return true si contiene alcohol, false en caso contrario
     */
    public boolean isConAlcohol() {
        return conAlcohol;
    }
    
    /**
     * Representación detallada en String de la bebida.
     * Incluye información sobre el contenido de alcohol.
     * 
     * @return String con formato que incluye nombre, tamaño, precio y contenido de alcohol
     */
    @Override
    public String toString() {
        String alcohol = conAlcohol ? " (Con Alcohol)" : " (Sin Alcohol)";
        return String.format("%s [%s] - $%.2f%s", nombre, tamaño, calcularPrecio(), alcohol);
    }
}