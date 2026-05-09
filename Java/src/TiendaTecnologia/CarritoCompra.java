package TiendaTecnologia;

import java.util.ArrayList;

/**
 * Acumula los productos seleccionados por el cliente antes de pagar.
 *
 * <p>
 * Cada vez que se añade un producto, su stock en el inventario disminuye
 * en 1. Si el producto no tiene stock se lanza
 * {@link StockInsuficienteException}.
 * </p>
 */
public class CarritoCompra {

    private ArrayList<Producto> items;

    public CarritoCompra() {
        this.items = new ArrayList<>();
    }

    /**
     * Añade un producto al carrito y descuenta 1 unidad del stock.
     *
     * @param producto Producto a añadir.
     * @throws StockInsuficienteException si el producto no tiene unidades
     *                                    disponibles.
     */
    public void agregarProducto(Producto producto) throws StockInsuficienteException {
        if (producto.getStock() == 0) {
            throw new StockInsuficienteException(
                    "No quedan unidades de '" + producto.getNombre() + "' en el inventario.");
        }
        // Descontamos stock al reservar el producto en el carrito
        producto.setStock(producto.getStock() - 1);
        items.add(producto);
        System.out.println("  [OK] '" + producto.getNombre() + "' añadido al carrito.");

    }

    /**
     * Suma los precios de todos los productos del carrito.
     *
     * @return total en euros.
     */
    public double calcularTotal() {
        double total = 0;
        for (Producto p : items) {
            total += p.getPrecio();
        }
        return total;
    }

    /**
     * @return true si el carrito no tiene ningún producto.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Devuelve una copia de los items actuales (para crear la factura).
     */
    public ArrayList<Producto> getItems() {
        return new ArrayList<>(items);
    }

    /** Vacía el carrito tras completar el pago. */
    public void vaciar() {
        items.clear();
    }

    /** Imprime en pantalla los productos del carrito y el total. */
    public void mostrarCarrito() {
        if (items.isEmpty()) {
            System.out.println("  El carrito está vacío.");
            return;
        }
        System.out.println("  --- CARRITO ACTUAL ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, items.get(i).getDescripcion());
            // System.out.println(" " + (i + 1) + ". " + items.get(i).getDescripcion());
        }
        System.out.printf("  TOTAL: %.2f euros%n", calcularTotal());
        // System.out.println(" TOTAL: " + calcularTotal() + " euros");
    }
}
