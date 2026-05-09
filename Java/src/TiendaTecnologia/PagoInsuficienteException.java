package TiendaTecnologia;

/**
 * Se lanza cuando el efectivo entregado por el cliente
 * es menor que el total del carrito.
 */
public class PagoInsuficienteException extends Exception {

    private static final long serialVersionUID = 1L;

    public PagoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
