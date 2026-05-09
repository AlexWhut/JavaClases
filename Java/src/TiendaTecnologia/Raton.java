package TiendaTecnologia;

/**
 * Ratón de ordenador. Hereda de {@link Producto}.
 * Añade el atributo propio: si es inalámbrico o no.
 */
public class Raton extends Producto {

    private boolean inalambrico;

    /**
     * @param nombre      Nombre o modelo del ratón.
     * @param precio      Precio en euros.
     * @param stock       Unidades disponibles.
     * @param inalambrico true si es inalámbrico, false si es con cable.
     */
    public Raton(String nombre, double precio, int stock, boolean inalambrico) {
        // Llamamos al constructor del padre para inicializar los atributos comunes.
        // Sin este "super()" el compilador daría error porque Producto no tiene constructor vacío.
        super(nombre, precio, stock, TipoProducto.RATON);
        this.inalambrico = inalambrico;
    }

    public boolean isInalambrico() { return inalambrico; }

    /**
     * POLIMORFISMO: esta implementación es específica de Ratón.
     * Cuando recorramos un ArrayList&lt;Producto&gt; y llamemos a getDescripcion(),
     * Java ejecutará esta versión si el objeto es un Ratón.
     */
    @Override
    public String getDescripcion() {
        String conexion = inalambrico ? "Inalámbrico" : "Con cable";
        return String.format("Raton %-20s [%s] - %.2f euros", getNombre(), conexion, getPrecio());
    }
}
