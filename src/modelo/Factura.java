package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una Factura en el sistema del restaurante.
 * Una factura se genera a partir de un pedido completado.
 * Implementa el principio de composicion al contener un Pedido.
 * 
 * @author Grupo 1 Desarrollo Software
 * @version 2.1
 * @since 2025
 */
public class Factura {
    
    /**
     * Contador estatico para generar numeros de factura unicos automaticamente.
     */
    private static int contadorNumero = 1;
    
    /**
     * Numero unico de la factura.
     */
    private int numero;
    
    /**
     * Pedido asociado a la factura.
     */
    private Pedido pedido;
    
    /**
     * Nombre del cliente para la factura.
     */
    private String cliente;
    
    /**
     * Fecha y hora de emision de la factura.
     */
    private LocalDateTime fecha;
    
    /**
     * Total de la factura.
     */
    private double total;
    
    /**
     * Constructor para crear una nueva Factura.
     * 
     * @param pedido El pedido a facturar, no puede ser nulo
     * @param cliente El nombre del cliente, no puede ser nulo o vacio
     * @throws IllegalArgumentException si el pedido es nulo o el cliente esta vacio
     */
    public Factura(Pedido pedido, String cliente) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio.");
        }
        
        this.numero = contadorNumero++;
        this.pedido = pedido;
        this.cliente = cliente.trim();
        this.fecha = LocalDateTime.now();
        this.total = pedido.calcularTotal();
        pedido.setEstado("completado");
    }
    
    /**
     * Imprime la factura en formato legible en la consola.
     * Muestra todos los detalles del pedido y el total a pagar.
     * Utiliza estructuras de control para formatear la salida.
     */
    public void imprimirFactura() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           FACTURA #" + numero);
        System.out.println("=".repeat(50));
        System.out.println("Cliente: " + cliente);
        System.out.println("Fecha: " + fecha.format(formatter));
        System.out.println("Pedido #: " + pedido.getId());
        System.out.println("-".repeat(50));
        
        // Usando for-each para recorrer y mostrar todos los productos
        for (Producto producto : pedido.getProductos()) {
            System.out.printf("- %s\n", producto.toString());
        }
        
        System.out.println("-".repeat(50));
        System.out.printf("TOTAL: $%.2f\n", total);
        System.out.println("=".repeat(50));
    }
    
    /**
     * Obtiene el numero unico de la factura.
     * 
     * @return El numero de factura
     */
    public int getNumero() {
        return numero;
    }
    
    /**
     * Obtiene el pedido asociado a la factura.
     * 
     * @return El pedido facturado
     */
    public Pedido getPedido() {
        return pedido;
    }
    
    /**
     * Obtiene el nombre del cliente de la factura.
     * 
     * @return El nombre del cliente
     */
    public String getCliente() {
        return cliente;
    }
    
    /**
     * Obtiene la fecha de emision de la factura.
     * 
     * @return La fecha de la factura
     */
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    /**
     * Obtiene el total de la factura.
     * 
     * @return El total a pagar
     */
    public double getTotal() {
        return total;
    }
    
    /**
     * Representacion en String de la factura.
     * Muestra informacion resumida de la factura.
     * 
     * @return String con informacion basica de la factura
     */
    @Override
    public String toString() {
        return String.format("Factura #%d - Cliente: %s - Total: $%.2f", 
                           numero, cliente, total);
    }
    
    /**
     * Genera un resumen de la factura en formato compacto.
     * 
     * @return String con resumen de la factura
     */
    public String generarResumen() {
        return String.format("Factura #%d | Cliente: %s | Pedido: #%d | Total: $%.2f", 
                           numero, cliente, pedido.getId(), total);
    }
}