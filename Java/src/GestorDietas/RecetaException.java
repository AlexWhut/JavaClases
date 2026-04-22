package GestorDietas;

/**
 * Excepción comprobada para errores relacionados con recetas.
 *
 * Se lanza cuando se intenta operar sobre una receta con datos incorrectos:
 * nombre vacío, fecha null, ingrediente inexistente, receta sin ingredientes, etc.
 */
public class RecetaException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param mensaje descripción del problema con la receta
     */
    public RecetaException(String mensaje) {
        super(mensaje);
    }
}
