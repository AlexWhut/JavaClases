package TiendaTecnologia;

/**
 * Se lanza cuando se intenta añadir al carrito un producto
 * que no tiene unidades disponibles en el inventario.
 */
public class StockInsuficienteException extends Exception {

    private static final long serialVersionUID = 1L;

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
