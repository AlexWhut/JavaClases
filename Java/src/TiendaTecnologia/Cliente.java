package TiendaTecnologia;

/**
 * Datos del cliente que está realizando la compra.
 * Encapsula nombre y edad con validación en el constructor.
 */
public class Cliente {

    private String nombre;
    private int edad;

    /**
     * @param nombre Nombre del cliente (no puede estar vacío).
     * @param edad   Edad del cliente (entre 1 y 119 años).
     * @throws IllegalArgumentException si los datos no son válidos.
     */
    public Cliente(String nombre, int edad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        if (edad <= 0 || edad >= 120) {
            throw new IllegalArgumentException("La edad debe estar entre 1 y 119 años.");
        }
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }

    @Override
    public String toString() {
        return String.format("%s (%d años)", nombre, edad);
    }
}
