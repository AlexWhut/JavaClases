package TiendaTecnologia;

import java.util.ArrayList;

/**
 * Gestiona el catálogo de productos de la tienda.
 *
 * <p>Almacena todos los productos en un {@code ArrayList<Producto>}.
 * Al ser de tipo {@code Producto} (clase abstracta), puede contener
 * cualquier subclase: {@link Raton}, {@link Teclado}, {@link Pantalla},
 * {@link GraficaGPU}. Esto es POLIMORFISMO aplicado a colecciones.</p>
 */
public class Inventario {

    private ArrayList<Producto> catalogo;

    public Inventario() {
        this.catalogo = new ArrayList<>();
    }

    /**
     * Rellena el catálogo con productos de ejemplo listos para usar en clase.
     * En un proyecto real esto vendría de una base de datos o archivo.
     */
    public void inicializar() {
        // Ratones
        catalogo.add(new Raton("Logitech MX Master 3", 99.99, 5, true));
        catalogo.add(new Raton("Razer DeathAdder V3",  69.99, 3, true));
        catalogo.add(new Raton("Microsoft Classic",    19.99, 2, false));

        // Teclados
        catalogo.add(new Teclado("Keychron K2",         89.99, 4, "QWERTY"));
        catalogo.add(new Teclado("Logitech MK295",      49.99, 6, "ISO"));
        catalogo.add(new Teclado("HyperX Alloy Origins",119.99, 2, "QWERTY"));

        // Pantallas
        catalogo.add(new Pantalla("LG UltraWide 34",   349.99, 2, 34));
        catalogo.add(new Pantalla("Samsung Odyssey G5", 249.99, 3, 27));
        catalogo.add(new Pantalla("AOC 24G2",           149.99, 1, 24));

        // Gráficas
        catalogo.add(new GraficaGPU("RTX 4060",        299.99, 2, 8));
        catalogo.add(new GraficaGPU("RX 7600",         269.99, 1, 8));
    }

    /**
     * Muestra todos los productos del catálogo con su stock actual.
     */
    public void mostrarInventario() {
        System.out.println();
        System.out.println("  === INVENTARIO DE LA TIENDA ===");
        System.out.println("  # Nombre Precio Stock");
        System.out.println("  " + "-".repeat(50));
        for (int i = 0; i < catalogo.size(); i++) {
            Producto p = catalogo.get(i);
            System.out.println("  " + (i + 1) + ". " + p.toString());
        }
        System.out.println();
    }

    /**
     * Busca un producto por nombre (búsqueda parcial, sin distinguir mayúsculas).
     *
     * @param nombre Texto a buscar en el nombre del producto.
     * @return El primer {@link Producto} cuyo nombre contenga el texto, o {@code null} si no existe.
     */
    public Producto buscarProducto(String nombre) {
        for (Producto p : catalogo) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Cuenta cuántas unidades en total quedan de un tipo de producto.
     *
     * @param tipo Categoría a consultar ({@link TipoProducto}).
     * @return Total de unidades disponibles de ese tipo.
     */
    public int consultarStock(TipoProducto tipo) {
        int total = 0;
        // Recorremos todo el catálogo y sumamos el stock de los que coinciden
        for (Producto p : catalogo) {
            if (p.getTipo() == tipo) {
                total += p.getStock();
            }
        }
        return total;
    }

    /**
     * Devuelve todos los productos de un tipo cuyo precio no supera el máximo indicado.
     *
     * @param maxPrecio Precio máximo en euros (debe ser mayor que 0).
     * @param tipo      Categoría de producto a filtrar.
     * @return Lista de productos que cumplen ambos criterios.
     */
    public ArrayList<Producto> buscarPorPrecioMaximo(double maxPrecio, TipoProducto tipo) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p.getTipo() == tipo && p.getPrecio() <= maxPrecio) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public ArrayList<Producto> getCatalogo() { return catalogo; }
}
