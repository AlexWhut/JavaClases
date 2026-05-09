package TiendaTecnologia;

/**
 * Clase base abstracta para todos los productos de la tienda.
 *
 * <p>Define los atributos comunes (nombre, precio, stock, tipo) y obliga
 * a cada subclase a implementar {@link #getDescripcion()}, que devuelve
 * una descripción específica del producto concreto.</p>
 *
 * <p><b>Pilares de POO aplicados:</b>
 * <ul>
 *   <li>Abstracción — esta clase no puede instanciarse directamente.</li>
 *   <li>Encapsulación — atributos privados, acceso controlado por getters/setters.</li>
 * </ul>
 * </p>
 */
public abstract class Producto {

    private String nombre;
    private double precio;
    private int stock;
    private TipoProducto tipo;

    /**
     * Crea un producto válido.
     *
     * @param nombre Nombre del producto (no puede estar vacío).
     * @param precio Precio en euros (debe ser mayor que 0).
     * @param stock  Unidades disponibles (debe ser 0 o más).
     * @param tipo   Categoría del producto.
     * @throws IllegalArgumentException si algún valor no es válido.
     */
    public Producto(String nombre, double precio, int stock, TipoProducto tipo) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.tipo = tipo;
    }

    // -------------------------------------------------------------------------
    // Metodo abstracto - POLIMORFISMO en accion:
    // Al llamar producto.getDescripcion() sobre un ArrayList<Producto>,
    // Java ejecuta la version correcta de cada subclase automaticamente.
    // -------------------------------------------------------------------------

    /**
     * Devuelve una descripción detallada del producto con sus características propias.
     *
     * @return cadena descriptiva específica de cada tipo de producto.
     */
    public abstract String getDescripcion();

    // -------------------------------------------------------------------------
    // Getters y setters con validacion
    // -------------------------------------------------------------------------

    public String getNombre() { return nombre; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) {
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser mayor que 0.");
        this.precio = precio;
    }

    public int getStock() { return stock; }

    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.stock = stock;
    }

    public TipoProducto getTipo() { return tipo; }

    /**
     * Representación legible del producto para mostrar en listas.
     */
    @Override
    public String toString() {
        return String.format("%-25s | %7.2f euros | Stock: %d", nombre, precio, stock);
    }
}
