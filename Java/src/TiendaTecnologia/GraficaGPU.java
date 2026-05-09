package TiendaTecnologia;

/**
 * Tarjeta gráfica (GPU). Hereda de {@link Producto}.
 * Añade la memoria de vídeo en gigabytes (VRAM).
 */
public class GraficaGPU extends Producto {

    private int memoriaGB;

    /**
     * @param nombre    Nombre o modelo de la gráfica.
     * @param precio    Precio en euros.
     * @param stock     Unidades disponibles.
     * @param memoriaGB Memoria VRAM en GB (debe ser mayor que 0).
     */
    public GraficaGPU(String nombre, double precio, int stock, int memoriaGB) {
        super(nombre, precio, stock, TipoProducto.GRAFICA);
        if (memoriaGB <= 0) {
            throw new IllegalArgumentException("La memoria VRAM debe ser mayor que 0 GB.");
        }
        this.memoriaGB = memoriaGB;
    }

    public int getMemoriaGB() { return memoriaGB; }

    @Override
    public String getDescripcion() {
        return String.format("Grafica %-20s [%dGB VRAM] - %.2f euros", getNombre(), memoriaGB, getPrecio());
    }
}
