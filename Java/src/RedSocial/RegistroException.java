package RedSocial;

// ============================================================
// EXCEPCION PERSONALIZADA: RegistroException
// ------------------------------------------------------------
// Extiende Exception, lo que la convierte en una excepcion
// "comprobada" (checked): el compilador obliga a capturarla
// con try-catch o declararla con throws.
// La usamos para comunicar errores propios del registro,
// como que el usuario sea demasiado joven.
// ============================================================

/**
 * Excepcion que se lanza cuando el proceso de registro falla
 * por datos invalidos del usuario (por ejemplo, edad insuficiente).
 *
 * <p>Al extender {@link Exception} es una excepcion comprobada:
 * el compilador exige que se capture o se declare en la firma.</p>
 */
public class RegistroException extends Exception {

    // serialVersionUID es requerido por Java cuando una clase
    // implementa Serializable (Exception lo hace internamente).
    // Con este valor fijo evitamos advertencias del compilador.
    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepcion con un mensaje descriptivo del problema.
     *
     * @param mensaje explicacion del motivo del fallo en el registro
     */
    public RegistroException(String mensaje) {
        super(mensaje); // pasa el mensaje a la clase padre Exception
    }
}
