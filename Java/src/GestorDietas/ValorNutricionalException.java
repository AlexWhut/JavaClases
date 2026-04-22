package GestorDietas;

/**
 * Excepción comprobada (checked) para errores en valores nutricionales.
 *
 * Se lanza cuando se intenta crear o modificar un PerfilNutricional
 * con datos inválidos (negativos, null, etc.).
 *
 * Al ser checked (extiende Exception, NO RuntimeException), el compilador
 * nos obliga a capturarla o declararla en la cabecera del método.
 * Esto es lo que pide el enunciado: excepciones controladas.
 */
public class ValorNutricionalException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción con un mensaje descriptivo del error.
     *
     * @param mensaje descripción del problema encontrado
     */
    public ValorNutricionalException(String mensaje) {
        // Llamamos al constructor de Exception con el mensaje
        super(mensaje);
    }
}
