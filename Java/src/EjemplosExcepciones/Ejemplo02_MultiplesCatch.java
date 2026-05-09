package EjemplosExcepciones;

/**
 * EJEMPLO 02 — Múltiples catch y multi-catch (Java 7+)
 *
 * Regla clave: los catch más ESPECÍFICOS van PRIMERO.
 * Si pones Exception al principio nunca se llega a los específicos.
 */
public class Ejemplo02_MultiplesCatch {

    public static void demoMultiplesCatch() {

        System.out.println("=== EJEMPLO 02: Múltiples catch ===\n");

        // --- Caso A: cada excepción tiene su propio catch ---
        System.out.println("-- A. Catch específicos en orden --");
        String[] valores = {"42", null, "abc"};

        for (String v : valores) {
            try {
                int n = Integer.parseInt(v);    // puede lanzar NumberFormatException o NPE
                int[] arr = new int[3];
                arr[n] = 1;                     // puede lanzar ArrayIndexOutOfBoundsException
                System.out.println("OK -> " + v + " procesado sin errores");
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException con '" + v + "': " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("NullPointerException: el valor era null");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ArrayIndexOutOfBoundsException: índice " + v + " fuera de rango");
            } catch (Exception e) {
                // atrapa cualquier otra excepción no prevista — siempre al final
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        System.out.println();

        // --- Caso B: multi-catch — mismo bloque para dos excepciones ---
        System.out.println("-- B. Multi-catch (mismo tratamiento para dos excepciones) --");
        String[] entradas = {"hola", null, "99"};

        for (String entrada : entradas) {
            try {
                int resultado = Integer.parseInt(entrada.trim());
                System.out.println("Número válido: " + resultado);
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("No se pudo procesar '" + entrada + "': " + e.getClass().getSimpleName());
            }
        }
        System.out.println();
    }
}
