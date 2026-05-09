package TiendaTecnologia;

/**
 * Teclado de ordenador. Hereda de {@link Producto}.
 * Añade la disposición de teclas (QWERTY, ISO, etc.).
 */
public class Teclado extends Producto {

    private String disposicion;

    /**
     * @param nombre      Nombre o modelo del teclado.
     * @param precio      Precio en euros.
     * @param stock       Unidades disponibles.
     * @param disposicion Distribución del teclado (ej: "QWERTY", "ISO").
     */
    public Teclado(String nombre, double precio, int stock, String disposicion) {
        // super() inicializa nombre, precio, stock y asigna TipoProducto.TECLADO
        super(nombre, precio, stock, TipoProducto.TECLADO);
        if (disposicion == null || disposicion.isBlank()) {
            throw new IllegalArgumentException("La disposición no puede estar vacía.");
        }
        this.disposicion = disposicion;
    }

    public String getDisposicion() { return disposicion; }

    @Override
    public String getDescripcion() {
        return String.format("Teclado %-20s [%s] - %.2f euros", getNombre(), disposicion, getPrecio());
    }
}
