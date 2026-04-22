package GestorDietas;

/**
 * Excepción comprobada para errores relacionados con usuarios.
 *
 * Se lanza cuando se intenta registrar un usuario con datos inválidos,
 * iniciar sesión con un ID que no existe, o pasar parámetros null/vacíos
 * a los métodos de Usuario y sus subclases.
 */
public class UsuarioException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param mensaje descripción del problema con el usuario
     */
    public UsuarioException(String mensaje) {
        super(mensaje);
    }
}
