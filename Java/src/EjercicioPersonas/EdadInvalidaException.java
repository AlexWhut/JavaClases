package EjercicioPersonas;

/**
 * Excepción <b>checked</b> lanzada cuando se intenta crear una persona
 * con una edad fuera del rango válido (0–120 años).
 *
 * <p>Al ser checked, el compilador obliga a quien llame al constructor
 * de {@link Persona} a manejarla con {@code try/catch} o declararla
 * con {@code throws}.</p>
 *
 * @author EjercicioPersonas
 * @version 1.0
 */
public class EdadInvalidaException extends Exception {

    /** Valor de edad que provocó la excepción, útil para el mensaje de error. */
    private final int edadRecibida;

    /**
     * Crea la excepción con el valor de edad que la causó.
     *
     * @param edadRecibida valor de edad inválido que se intentó asignar
     */
    public EdadInvalidaException(int edadRecibida) {
        super("Edad inválida: " + edadRecibida + ". Debe estar entre 0 y 120.");
        this.edadRecibida = edadRecibida;
    }

    /**
     * Devuelve el valor de edad que provocó la excepción.
     *
     * @return edad inválida recibida
     */
    public int getEdadRecibida() {
        return edadRecibida;
    }
}
