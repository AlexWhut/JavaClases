package EjemplosExcepciones;

/**
 * EJEMPLO 01 — Excepciones Unchecked (RuntimeException)
 *
 * Son las 4 más comunes. El compilador NO obliga a capturarlas,
 * pero si no lo hacemos el programa se detiene abruptamente.
 *
 * Ejecuta demoUnchecked() para verlas en acción.
 */
public class Ejemplo01_Unchecked {

    public static void demoUnchecked() {

        System.out.println("=== EJEMPLO 01: Excepciones Unchecked ===\n");

        // --- 1. ArithmeticException: división entre cero ---
        System.out.println("-- 1. ArithmeticException --");
        try {
            int resultado = 10 / 0;
            System.out.println("resultado = " + resultado); // nunca llega aquí
        } catch (ArithmeticException e) {
            System.out.println("Capturada: " + e.getClass().getSimpleName());
            System.out.println("Mensaje  : " + e.getMessage());
        }
        System.out.println();

        // --- 2. NullPointerException: usar referencia null ---
        System.out.println("-- 2. NullPointerException --");
        try {
            String texto = null;
            int longitud = texto.length(); // null no tiene métodos
            System.out.println("longitud = " + longitud); // nunca llega aquí
        } catch (NullPointerException e) {
            System.out.println("Capturada: " + e.getClass().getSimpleName());
            System.out.println("Mensaje  : la variable 'texto' apunta a null");
        }
        System.out.println();

        // --- 3. ArrayIndexOutOfBoundsException: índice fuera de rango ---
        System.out.println("-- 3. ArrayIndexOutOfBoundsException --");
        try {
            int[] numeros = {10, 20, 30};
            System.out.println(numeros[5]); // el array solo tiene índices 0, 1, 2
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Capturada: " + e.getClass().getSimpleName());
            System.out.println("Mensaje  : " + e.getMessage());
        }
        System.out.println();

        // --- 4. NumberFormatException: convertir texto no numérico ---
        System.out.println("-- 4. NumberFormatException --");
        try {
            int numero = Integer.parseInt("abc"); // "abc" no es un número
            System.out.println("numero = " + numero); // nunca llega aquí
        } catch (NumberFormatException e) {
            System.out.println("Capturada: " + e.getClass().getSimpleName());
            System.out.println("Mensaje  : " + e.getMessage());
        }
        System.out.println();

        System.out.println(">>> Programa sigue ejecutándose gracias al try/catch <<<\n");
    }
}
