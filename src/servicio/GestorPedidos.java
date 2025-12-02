package servicio;

import modelo.Pedido;
import modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase servicio para gestionar todas las operaciones relacionadas con pedidos.
 * Proporciona metodos para crear, buscar y administrar pedidos del restaurante.
 * 
 * @author Grupo 1 Desarrollo Software
 * @version 2.1
 * @since 2025
 */
public class GestorPedidos {
    private List<Pedido> pedidos;

    /**
     * Constructor que inicializa la lista de pedidos.
     */
    public GestorPedidos() {
        this.pedidos = new ArrayList<>();
    }

    /**
     * Crea un nuevo pedido vacio.
     * 
     * @return el nuevo pedido creado
     */
    public Pedido crearPedido() {
        Pedido nuevoPedido = new Pedido();
        pedidos.add(nuevoPedido);
        return nuevoPedido;
    }

    /**
     * Crea un nuevo pedido para un cliente.
     * 
     * @param nombreCliente el nombre del cliente que realiza el pedido
     * @return el nuevo pedido creado
     */
    public Pedido crearPedidoCliente(String nombreCliente) {
        Pedido nuevoPedido = new Pedido(nombreCliente);
        pedidos.add(nuevoPedido);
        return nuevoPedido;
    }

    /**
     * Agrega un producto a un pedido existente.
     * 
     * @param idPedido el ID del pedido
     * @param producto el producto a agregar
     * @return true si se agrego exitosamente, false si el pedido no existe
     */
    public boolean agregarProductoAPedido(int idPedido, Producto producto) {
        Optional<Pedido> pedido = buscarPedidoPorId(idPedido);
        if (pedido.isPresent() && producto != null) {
            pedido.get().agregarProducto(producto);
            return true;
        }
        return false;
    }

    /**
     * Busca un pedido por su ID.
     * 
     * @param id el ID del pedido a buscar
     * @return Optional con el pedido encontrado o vacio si no existe
     */
    public Optional<Pedido> buscarPedidoPorId(int id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    /**
     * Obtiene todos los pedidos pendientes.
     * 
     * @return lista de pedidos con estado "pendiente"
     */
    public List<Pedido> getPedidosPendientes() {
        List<Pedido> pendientes = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if ("pendiente".equals(pedido.getEstado())) {
                pendientes.add(pedido);
            }
        }
        return pendientes;
    }

    /**
     * Obtiene todos los pedidos completados.
     * 
     * @return lista de pedidos con estado "completado"
     */
    public List<Pedido> getPedidosCompletados() {
        List<Pedido> completados = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if ("completado".equals(pedido.getEstado())) {
                completados.add(pedido);
            }
        }
        return completados;
    }

    /**
     * Obtiene todos los pedidos del sistema.
     * 
     * @return lista completa de pedidos
     */
    public List<Pedido> getTodosLosPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
     * Calcula el total de un pedido especifico.
     * 
     * @param idPedido el ID del pedido
     * @return el total calculado del pedido, o 0 si el pedido no existe
     */
    public double calcularTotalPedido(int idPedido) {
        Optional<Pedido> pedido = buscarPedidoPorId(idPedido);
        return pedido.map(Pedido::calcularTotal).orElse(0.0);
    }

    /**
     * Cambia el estado de un pedido.
     * 
     * @param idPedido    el ID del pedido
     * @param nuevoEstado el nuevo estado del pedido
     * @return true si se cambio exitosamente, false si el pedido no existe
     */
    public boolean cambiarEstadoPedido(int idPedido, String nuevoEstado) {
        Optional<Pedido> pedido = buscarPedidoPorId(idPedido);
        if (pedido.isPresent()) {
            pedido.get().setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    /**
     * Obtiene el numero total de pedidos en el sistema.
     * 
     * @return el conteo total de pedidos
     */
    public int getTotalPedidos() {
        return pedidos.size();
    }

    /**
     * Obtiene el numero de pedidos pendientes.
     * 
     * @return el conteo de pedidos pendientes
     */
    public int getTotalPedidosPendientes() {
        return getPedidosPendientes().size();
    }

    /**
     * Verifica si un pedido puede ser facturado (esta pendiente y tiene productos).
     * 
     * @param idPedido el ID del pedido a verificar
     * @return true si el pedido puede ser facturado, false en caso contrario
     */
    public boolean pedidoPuedeSerFacturado(int idPedido) {
        Optional<Pedido> pedido = buscarPedidoPorId(idPedido);
        return pedido.isPresent() &&
                "pendiente".equals(pedido.get().getEstado()) &&
                !pedido.get().getProductos().isEmpty();
    }
}