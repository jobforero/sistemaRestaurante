package servicio;

import modelo.Factura;
import modelo.Pedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase servicio para gestionar todas las operaciones relacionadas con facturas.
 * Proporciona métodos para generar, buscar y administrar facturas del restaurante.
 * 
 * @author José Castrellón
 * @version 2.0
 * @since 2025
 */
public class GestorFacturas {
    private List<Factura> facturas;
    private GestorPedidos gestorPedidos;
    
    /**
     * Constructor que inicializa la lista de facturas.
     * 
     * @param gestorPedidos el gestor de pedidos para validaciones
     */
    public GestorFacturas(GestorPedidos gestorPedidos) {
        this.facturas = new ArrayList<>();
        this.gestorPedidos = gestorPedidos;
    }
    
    /**
     * Genera una nueva factura para un pedido.
     * 
     * @param idPedido el ID del pedido a facturar
     * @param cliente el nombre del cliente
     * @return la factura generada, o null si el pedido no puede ser facturado
     * @throws IllegalArgumentException si el cliente está vacío o el pedido no existe
     */
    public Factura generarFactura(int idPedido, String cliente) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        
        Optional<Pedido> pedido = gestorPedidos.buscarPedidoPorId(idPedido);
        if (pedido.isEmpty()) {
            throw new IllegalArgumentException("El pedido con ID " + idPedido + " no existe.");
        }
        
        if (!gestorPedidos.pedidoPuedeSerFacturado(idPedido)) {
            throw new IllegalStateException("El pedido no puede ser facturado. Verifique que esté pendiente y tenga productos.");
        }
        
        Factura factura = new Factura(pedido.get(), cliente.trim());
        facturas.add(factura);
        return factura;
    }
    
    /**
     * Busca una factura por su número.
     * 
     * @param numero el número de factura a buscar
     * @return Optional con la factura encontrada o vacío si no existe
     */
    public Optional<Factura> buscarFacturaPorNumero(int numero) {
        return facturas.stream()
            .filter(f -> f.getNumero() == numero)
            .findFirst();
    }
    
    /**
     * Obtiene todas las facturas del sistema.
     * 
     * @return lista completa de facturas
     */
    public List<Factura> getTodasLasFacturas() {
        return new ArrayList<>(facturas);
    }
    
    /**
     * Obtiene facturas filtradas por cliente.
     * 
     * @param cliente el nombre del cliente a filtrar
     * @return lista de facturas del cliente especificado
     */
    public List<Factura> getFacturasPorCliente(String cliente) {
        List<Factura> resultado = new ArrayList<>();
        for (Factura factura : facturas) {
            if (factura.getCliente().equalsIgnoreCase(cliente)) {
                resultado.add(factura);
            }
        }
        return resultado;
    }
    
    /**
     * Calcula el total facturado en todas las facturas.
     * 
     * @return la suma total de todas las facturas
     */
    public double getTotalFacturado() {
        double total = 0;
        for (Factura factura : facturas) {
            total += factura.getTotal();
        }
        return total;
    }
    
    /**
     * Calcula el total facturado para un cliente específico.
     * 
     * @param cliente el nombre del cliente
     * @return la suma total facturada al cliente
     */
    public double getTotalFacturadoPorCliente(String cliente) {
        double total = 0;
        for (Factura factura : getFacturasPorCliente(cliente)) {
            total += factura.getTotal();
        }
        return total;
    }
    
    /**
     * Obtiene el número total de facturas generadas.
     * 
     * @return el conteo total de facturas
     */
    public int getTotalFacturas() {
        return facturas.size();
    }
    
    /**
     * Obtiene la factura con el monto más alto.
     * 
     * @return la factura con el mayor total, o null si no hay facturas
     */
    public Factura getFacturaMayorMonto() {
        if (facturas.isEmpty()) {
            return null;
        }
        
        Factura mayor = facturas.get(0);
        for (Factura factura : facturas) {
            if (factura.getTotal() > mayor.getTotal()) {
                mayor = factura;
            }
        }
        return mayor;
    }
    
    /**
     * Obtiene la factura con el monto más bajo.
     * 
     * @return la factura con el menor total, o null si no hay facturas
     */
    public Factura getFacturaMenorMonto() {
        if (facturas.isEmpty()) {
            return null;
        }
        
        Factura menor = facturas.get(0);
        for (Factura factura : facturas) {
            if (factura.getTotal() < menor.getTotal()) {
                menor = factura;
            }
        }
        return menor;
    }
    
    /**
     * Verifica si existe una factura para un pedido específico.
     * 
     * @param idPedido el ID del pedido a verificar
     * @return true si ya existe una factura para el pedido, false en caso contrario
     */
    public boolean existeFacturaParaPedido(int idPedido) {
        for (Factura factura : facturas) {
            if (factura.getPedido().getId() == idPedido) {
                return true;
            }
        }
        return false;
    }
}