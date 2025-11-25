package modelo;

/**
 * Subclase que representa un producto de tipo Bebida en el restaurante.
 * Hereda de la clase Producto y aplica recargos segun el tamano.
 * Implementa el principio de polimorfismo al sobreescribir calcularPrecio().
 * 
 * @author Grupo 1 Desarrollo Software
 * @version 2.1
 * @since 2025
 * @see Producto
 */
public class Bebida extends Producto {
    
    /**
     * Tamano de la bebida: "pequeno", "mediano" o "grande".
     */
    private String tamano;
    
    /**
     * Indica si la bebida contiene alcohol.
     */
    private boolean conAlcohol;
    
    /**
     * Constructor para crear una nueva Bebida.
     * 
     * @param nombre El nombre de la bebida, no puede ser nulo o vacio
     * @param precio El precio base de la bebida (tamano pequeno), debe ser positivo
     * @param tamano El tamano de la bebida (pequeno, mediano, grande)
     * @param conAlcohol Indica si la bebida contiene alcohol
     */
    public Bebida(String nombre, double precio, String tamano, boolean conAlcohol) {
        super(nombre, precio);
        this.tamano = tamano;
        this.conAlcohol = conAlcohol;
    }
    
    /**
     * Calcula el precio final de la bebida aplicando recargos por tamano.
     * Mediano: 20% de recargo, Grande: 40% de recargo.
     * 
     * @return El precio final de la bebida con recargos aplicados
     */
    @Override
    public double calcularPrecio() {
        // Aplicar recargo por tamano usando switch-case
        double precioFinal = precio;
        switch(tamano.toLowerCase()) {
            case "mediano":
                precioFinal *= 1.2; // 20% de recargo
                break;
            case "grande":
                precioFinal *= 1.4; // 40% de recargo
                break;
            // Para tamano "pequeno" no se aplica recargo
        }
        return precioFinal;
    }
    
    /**
     * Obtiene el tamano de la bebida.
     * 
     * @return El tamano de la bebida
     */
    public String getTamano() {
        return tamano;
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
     * Representacion detallada en String de la bebida.
     * Incluye informacion sobre el contenido de alcohol.
     * 
     * @return String con formato que incluye nombre, tamano, precio y contenido de alcohol
     */
    @Override
    public String toString() {
        String alcohol = conAlcohol ? " (Con Alcohol)" : " (Sin Alcohol)";
        return String.format("%s [%s] - $%.2f%s", nombre, tamano, calcularPrecio(), alcohol);
    }
}