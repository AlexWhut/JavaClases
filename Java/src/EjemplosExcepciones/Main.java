package EjemplosExcepciones;

/**
 * Punto de entrada del package EjemplosExcepciones.
 *
 * Ejecuta los 6 ejemplos en orden, cada uno separado visualmente,
 * para poder pausar y explicar entre ellos en clase.
 *
 * Orden de los ejemplos:
 *   01 — Excepciones unchecked más comunes
 *   02 — Múltiples catch y multi-catch
 *   03 — finally (bloque que siempre se ejecuta)
 *   04 — throw vs throws
 *   05 — Excepción propia (checked)
 *   06 — Propagación por la cadena de llamadas
 */
public class Main {

    public static void main(String[] args) {

        separador();
        Ejemplo01_Unchecked.demoUnchecked();

        separador();
        Ejemplo02_MultiplesCatch.demoMultiplesCatch();

        separador();
        Ejemplo03_Finally.demoFinally();

        separador();
        Ejemplo04_ThrowThrows.demoThrowThrows();

        separador();
        Ejemplo05_ExcepcionPropia.demoExcepcionPropia();

        separador();
        Ejemplo06_Propagacion.demoPropagacion();

        separador();
        System.out.println("Fin de los ejemplos de excepciones.");
    }

    private static void separador() {
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
}
