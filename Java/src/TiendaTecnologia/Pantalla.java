package TiendaTecnologia;

/**
 * Monitor / pantalla. Hereda de {@link Producto}.
 * Añade el tamaño en pulgadas.
 */
public class Pantalla extends Producto {

    private int pulgadas;

    /**
     * @param nombre   Nombre o modelo de la pantalla.
     * @param precio   Precio en euros.
     * @param stock    Unidades disponibles.
     * @param pulgadas Tamaño de la pantalla en pulgadas (debe ser mayor que 0).
     */
    public Pantalla(String nombre, double precio, int stock, int pulgadas) {
        super(nombre, precio, stock, TipoProducto.PANTALLA);
        if (pulgadas <= 0) {
            throw new IllegalArgumentException("Las pulgadas deben ser mayores que 0.");
        }
        this.pulgadas = pulgadas;
    }

    public int getPulgadas() { return pulgadas; }

    @Override
    public String getDescripcion() {
        return String.format("Pantalla %-20s [%d\"] - %.2f euros", getNombre(), pulgadas, getPrecio());
    }
}
