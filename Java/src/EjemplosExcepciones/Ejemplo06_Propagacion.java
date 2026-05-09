package EjemplosExcepciones;

/**
 * EJEMPLO 06 — Propagación de excepciones
 *
 * Si un método no captura una excepción, SUBE a quien lo llamó.
 * La cadena continúa hasta main(). Si main tampoco la captura,
 * el programa termina con error.
 *
 * Cadena: main → nivelA → nivelB → nivelC → lanza excepción
 */
public class Ejemplo06_Propagacion {

    // nivelC lanza la excepción pero NO la captura → sube a nivelB
    static void nivelC() {
        System.out.println("    [nivelC] Ejecutando...");
        System.out.println("    [nivelC] ¡División por cero! Lanzando excepción...");
        int resultado = 10 / 0; // ArithmeticException
        System.out.println("    [nivelC] Esta línea nunca se imprime: " + resultado);
    }

    // nivelB tampoco la captura → sube a nivelA
    static void nivelB() {
        System.out.println("   [nivelB] Llamando a nivelC...");
        nivelC();
        System.out.println("   [nivelB] Esta línea nunca se imprime");
    }

    // nivelA tampoco la captura → sube a quien llame a nivelA
    static void nivelA() {
        System.out.println("  [nivelA] Llamando a nivelB...");
        nivelB();
        System.out.println("  [nivelA] Esta línea nunca se imprime");
    }

    public static void demoPropagacion() {

        System.out.println("=== EJEMPLO 06: Propagación de excepciones ===\n");

        // --- Caso A: excepción capturada en el llamador (main/demo) ---
        System.out.println("-- A. Captura en el nivel más alto --");
        System.out.println("[demo] Llamando a nivelA...");
        try {
            nivelA();
        } catch (ArithmeticException e) {
            System.out.println("[demo] Capturada aquí arriba: " + e.getMessage());
            System.out.println("[demo] El programa puede continuar");
        }
        System.out.println();

        // --- Caso B: printStackTrace muestra la traza completa de llamadas ---
        System.out.println("-- B. printStackTrace: ver toda la cadena de llamadas --");
        try {
            nivelA();
        } catch (ArithmeticException e) {
            System.out.println("[demo] Traza completa (útil para depurar):");
            e.printStackTrace(); // imprime cada método de la cadena
        }
        System.out.println();
    }
}
