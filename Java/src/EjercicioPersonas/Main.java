package EjercicioPersonas;

import java.util.List;

/**
 * Punto de entrada del ejercicio de gestión de personas.
 *
 * <p>Demuestra los <b>4 pilares de la POO</b> juntos en un caso real:</p>
 * <ol>
 *   <li><b>Encapsulación</b>   – campos privados de {@link Persona} solo accesibles por getters/setters.</li>
 *   <li><b>Abstracción</b>     – {@link Persona} es abstracta; no se puede instanciar directamente.</li>
 *   <li><b>Herencia</b>        – {@link Estudiante} y {@link Empleado} extienden {@link Persona}.</li>
 *   <li><b>Polimorfismo</b>    – {@link GestorPersonas} trabaja con {@code List<Persona>} sin saber
 *                                si cada elemento es Estudiante o Empleado.</li>
 * </ol>
 *
 * <p>También muestra el uso de {@link EdadInvalidaException} (excepción checked personalizada).</p>
 *
 * @author EjercicioPersonas
 * @version 1.0
 */
public class Main {

    /**
     * Método principal. Ejecuta todas las demostraciones.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {

        GestorPersonas gestor = new GestorPersonas();

        // -------------------------------------------------------
        // 1. Poblar la lista (HERENCIA: mezclamos Estudiante y Empleado)
        // -------------------------------------------------------
        System.out.println("=== Creando personas ===\n");

        try {
            gestor.agregar(new Estudiante("Ana García",    19, "ana@uni.es",        "Informática", 1));
            gestor.agregar(new Estudiante("Alberto López", 22, "alberto@uni.es",     "Derecho",     3));
            gestor.agregar(new Estudiante("Marta Ruiz",    17, "marta@uni.es",       "Medicina",    1));
            gestor.agregar(new Empleado("Carlos Díaz",     35, "carlos@empresa.com", "Accenture",   42000));
            gestor.agregar(new Empleado("Alicia Torres",   28, "alicia@empresa.com", "Indra",       38000));
            gestor.agregar(new Empleado("Beatriz Sanz",    20, "bea@empresa.com",    "Google",      55000));
            gestor.agregar(new Estudiante("David Mora",    25, "david@uni.es",       "Historia",    4));

        } catch (EdadInvalidaException e) {
            // En el bloque de inicialización no debería ocurrir,
            // pero el compilador obliga a manejarla por ser checked.
            System.out.println("Error al crear persona: " + e.getMessage());
        }

        // -------------------------------------------------------
        // 2. Mostrar todas (POLIMORFISMO: describirse() del tipo real)
        // -------------------------------------------------------
        System.out.println();
        gestor.mostrarTodas();

        // -------------------------------------------------------
        // 3. Filtro por edad mínima
        // -------------------------------------------------------
        System.out.println("\n=== Personas de 20 años o más ===");
        List<Persona> mayores = gestor.filtrarPorEdadMinima(20);
        if (mayores.isEmpty()) {
            System.out.println("Ninguna persona cumple el criterio.");
        } else {
            for (Persona p : mayores) {
                System.out.println(p.describirse());
            }
        }

        // -------------------------------------------------------
        // 4. Filtro por letra inicial del nombre
        // -------------------------------------------------------
        char letra = 'A';
        System.out.println("\n=== Personas cuyo nombre empieza por '" + letra + "' ===");
        List<Persona> porLetra = gestor.filtrarPorLetra(letra);
        if (porLetra.isEmpty()) {
            System.out.println("Ninguna persona empieza por '" + letra + "'.");
        } else {
            for (Persona p : porLetra) {
                System.out.println(p.describirse());
            }
        }

        // -------------------------------------------------------
        // 5. Búsqueda por nombre
        // -------------------------------------------------------
        System.out.println("\n=== Buscar 'Carlos Díaz' ===");
        Persona encontrada = gestor.buscarPorNombre("Carlos Díaz");
        if (encontrada != null) {
            System.out.println("Encontrada: " + encontrada.describirse());
        } else {
            System.out.println("Persona no encontrada.");
        }

        // -------------------------------------------------------
        // 6. Demostración de EdadInvalidaException
        // -------------------------------------------------------
        System.out.println("\n=== Intentando crear persona con edad inválida (-5) ===");
        try {
            Persona invalida = new Estudiante("Test", -5, "test@test.com", "Física", 2);
            gestor.agregar(invalida);
        } catch (EdadInvalidaException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
            System.out.println("Edad recibida: " + e.getEdadRecibida());
        }

        System.out.println("\nTotal de personas en el sistema: " + gestor.getTotalPersonas());
    }
}
