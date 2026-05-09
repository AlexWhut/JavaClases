package EjemplosExcepciones;

/**
 * EJEMPLO 03 — finally: el bloque que SIEMPRE se ejecuta
 *
 * finally se usa para liberar recursos (cerrar archivos, conexiones...)
 * independientemente de si hubo o no excepción.
 *
 * Aquí simulamos un "recurso" con un boolean para no necesitar archivos reales.
 */
public class Ejemplo03_Finally {

    static boolean recursoAbierto = false;

    public static void demoFinally() {

        System.out.println("=== EJEMPLO 03: try / catch / finally ===\n");

        // --- Caso A: finally cuando no hay excepción ---
        System.out.println("-- A. Sin excepción: finally igual se ejecuta --");
        try {
            abrirRecurso();
            System.out.println("  Procesando datos... OK");
        } catch (ArithmeticException e) {
            System.out.println("  Error aritmético: " + e.getMessage());
        } finally {
            cerrarRecurso(); // se ejecuta aunque no haya habido excepción
        }
        System.out.println();

        // --- Caso B: finally cuando HAY excepción ---
        System.out.println("-- B. Con excepción: finally también se ejecuta --");
        try {
            abrirRecurso();
            int resultado = 10 / 0; // lanza ArithmeticException
            System.out.println("  resultado = " + resultado); // nunca llega aquí
        } catch (ArithmeticException e) {
            System.out.println("  Error aritmético capturado: " + e.getMessage());
        } finally {
            cerrarRecurso(); // se ejecuta aunque haya habido excepción
        }
        System.out.println();

        // --- Caso C: finally incluso sin catch ---
        System.out.println("-- C. try/finally sin catch (para garantizar cierre) --");
        try {
            abrirRecurso();
            System.out.println("  Operación completada");
        } finally {
            cerrarRecurso();
        }
        System.out.println();
    }

    private static void abrirRecurso() {
        recursoAbierto = true;
        System.out.println("  [Recurso ABIERTO]");
    }

    private static void cerrarRecurso() {
        if (recursoAbierto) {
            recursoAbierto = false;
            System.out.println("  [Recurso CERRADO — finally garantiza esto]");
        }
    }
}
