package EjemplosExcepciones;

/**
 * EJEMPLO 04 — throw vs throws
 *
 * throw  → LANZA una excepción desde dentro del código.
 * throws → DECLARA en la firma que un método puede lanzar una checked.
 *
 * Aquí usamos IllegalArgumentException (unchecked) con throw
 * y simulamos un método que declara throws con una checked propia sencilla.
 */
public class Ejemplo04_ThrowThrows {

    // --- throw con unchecked: no necesita declararse con throws ---
    public static void setEdad(int edad) {
        if (edad < 0 || edad > 150) {
            throw new IllegalArgumentException("Edad inválida: " + edad + ". Rango permitido: 0-150");
        }
        System.out.println("  Edad establecida: " + edad);
    }

    // --- throws con checked: el compilador obliga a declararlo ---
    public static void validarNombre(String nombre) throws NombreVacioException {
        if (nombre == null || nombre.isBlank()) {
            throw new NombreVacioException("El nombre no puede estar vacío o en blanco");
        }
        System.out.println("  Nombre válido: '" + nombre + "'");
    }

    // Excepción checked mínima (checked = extiende Exception, no RuntimeException)
    static class NombreVacioException extends Exception {
        NombreVacioException(String mensaje) {
            super(mensaje);
        }
    }

    public static void demoThrowThrows() {

        System.out.println("=== EJEMPLO 04: throw y throws ===\n");

        // --- throw con unchecked ---
        System.out.println("-- A. throw con IllegalArgumentException (unchecked) --");
        try {
            setEdad(25);   // válido
            setEdad(-5);   // lanza IllegalArgumentException
        } catch (IllegalArgumentException e) {
            System.out.println("  Capturada: " + e.getMessage());
        }
        System.out.println();

        // --- throws con checked ---
        System.out.println("-- B. throws con excepción checked propia --");
        String[] nombres = {"Ana", "", null, "Carlos"};
        for (String nombre : nombres) {
            try {
                validarNombre(nombre);          // puede lanzar NombreVacioException
            } catch (NombreVacioException e) {  // el compilador obliga a capturarla
                System.out.println("  Error: " + e.getMessage());
            }
        }
        System.out.println();
    }
}
