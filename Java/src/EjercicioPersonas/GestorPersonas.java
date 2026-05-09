package EjercicioPersonas;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona una colección de {@link Persona}s y ofrece operaciones
 * de búsqueda y filtrado.
 *
 * <p>El ArrayList interno almacena tanto {@link Estudiante}s como
 * {@link Empleado}s usando el tipo base {@code Persona}
 * (POLIMORFISMO de colección).</p>
 *
 * <p>Los métodos {@code filtrar*} nunca devuelven {@code null}:
 * si no hay resultados retornan una lista vacía.</p>
 *
 * @author EjercicioPersonas
 * @version 1.0
 */
public class GestorPersonas {

    /** Almacén interno de personas. */
    private ArrayList<Persona> personas;

    /**
     * Crea un gestor vacío.
     */
    public GestorPersonas() {
        this.personas = new ArrayList<>();
    }

    // -------------------------
    //  Alta
    // -------------------------

    /**
     * Añade una persona a la colección.
     *
     * @param persona persona a agregar (puede ser Estudiante o Empleado)
     */
    public void agregar(Persona persona) {
        personas.add(persona);
    }

    // -------------------------
    //  Consultas
    // -------------------------

    /**
     * Devuelve el número total de personas registradas.
     *
     * @return tamaño de la colección
     */
    public int getTotalPersonas() {
        return personas.size();
    }

    /**
     * Muestra por consola la descripción de todas las personas.
     *
     * <p>Llama a {@link Persona#describirse()} en cada elemento:
     * Java decide en tiempo de ejecución qué versión usar
     * según el tipo real del objeto (POLIMORFISMO dinámico).</p>
     */
    public void mostrarTodas() {
        System.out.println("=== Lista completa (" + personas.size() + " personas) ===");
        for (Persona p : personas) {
            System.out.println(p.describirse());
        }
    }

    // -------------------------
    //  Filtros
    // -------------------------

    /**
     * Filtra las personas cuya edad sea mayor o igual a la indicada.
     *
     * <p>Ejemplo de uso:
     * <pre>{@code
     * List<Persona> mayores = gestor.filtrarPorEdadMinima(20);
     * }</pre>
     * </p>
     *
     * @param edadMinima edad mínima (inclusive) para incluir en el resultado
     * @return lista (puede estar vacía) con las personas que cumplen el criterio
     */
    public List<Persona> filtrarPorEdadMinima(int edadMinima) {
        List<Persona> resultado = new ArrayList<>();
        for (Persona p : personas) {
            if (p.getEdad() >= edadMinima) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Filtra las personas cuyo nombre empiece por la letra indicada
     * sin distinguir mayúsculas de minúsculas.
     *
     * <p>Ejemplo: {@code filtrarPorLetra('a')} devuelve "Ana", "Alvaro", "alberto"…</p>
     *
     * @param letra primera letra del nombre a buscar
     * @return lista (puede estar vacía) con las personas que cumplen el criterio
     */
    public List<Persona> filtrarPorLetra(char letra) {
        List<Persona> resultado = new ArrayList<>();
        char letraBuscada = Character.toLowerCase(letra);
        for (Persona p : personas) {
            char primeraLetra = Character.toLowerCase(p.getNombre().charAt(0));
            if (primeraLetra == letraBuscada) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Busca una persona por nombre exacto (sin distinguir mayúsculas).
     *
     * @param nombre nombre a buscar
     * @return la primera {@link Persona} encontrada, o {@code null} si no existe
     */
    public Persona buscarPorNombre(String nombre) {
        for (Persona p : personas) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null; // el llamador debe comprobar si es null
    }
}
