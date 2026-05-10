package TiendaTecnologia;

import java.util.ArrayList;

/**
 * Representa el ticket/factura generado al completar una venta.
 *
 * <p>El ID es incremental y se genera automáticamente usando un contador
 * estático compartido por todas las facturas (pertenece a la clase, no
 * a cada instancia).</p>
 */
public class Factura {

    // "static" -> esta variable pertenece a la CLASE, no a un objeto concreto.
    // Todas las facturas comparten el mismo contador y lo incrementan.
    private static int contador = 0;

    private String idFactura;
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private double total;
    private String metodoPago;

    /**
     * Crea una factura. El ID se asigna automáticamente.
     *
     * @param cliente    Cliente que realizó la compra.
     * @param productos  Lista de productos comprados.
     * @param total      Importe total pagado.
     * @param metodoPago "Tarjeta" o "Efectivo".
     */
    public Factura(Cliente cliente, ArrayList<Producto> productos, double total, String metodoPago) {
        contador++;
        // %04d -> entero con al menos 4 digitos, rellena con ceros a la izquierda
        this.idFactura = String.format("%04d", contador);
        this.cliente = cliente;
        this.productos = productos;
        this.total = total;
        this.metodoPago = metodoPago;
    }

    /** Imprime la factura con formato de ticket de tienda. */
    public void imprimir() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         TIENDA TECH - FACTURA          ");
        System.out.println("========================================");
        System.out.println("  Factura N.º : " + idFactura);
        System.out.println("  Cliente     : " + cliente.getNombre());
        System.out.println("  Edad        : " + cliente.getEdad() + " años");
        System.out.println("  Método pago : " + metodoPago);
        System.out.println("----------------------------------------");
        System.out.println("  PRODUCTOS:");
        for (Producto p : productos) {
            // Aquí vemos POLIMORFISMO: getDescripcion() ejecuta la versión
            // de Raton, Teclado, Pantalla o GraficaGPU según corresponda.
            System.out.println("    - " + p.getDescripcion());
        }
        System.out.println("----------------------------------------");
        System.out.println("  TOTAL PAGADO : " + total + " euros");
        System.out.println("========================================");
        System.out.println("      ¡Gracias por su compra!           ");
        System.out.println("========================================");
        System.out.println();
    }

    public String getIdFactura() { return idFactura; }
    public double getTotal() { return total; }
}
