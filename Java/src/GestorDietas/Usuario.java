package GestorDietas;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase abstracta base para todos los tipos de usuario del sistema.
 *
 * Por qué abstracta: cada tipo de usuario (Deportista, UsuarioKeto, Paciente)
 * tiene reglas distintas para evaluar recetas. No tiene sentido crear un
 * "Usuario genérico" — siempre será uno de los tres subtipos.
 *
 * Comportamiento COMÚN que implementa esta clase (heredado por todos):
 *  - Mantener un historial de recetas
 *  - Añadir recetas al historial
 *  - Buscar recetas por fecha, intervalo o tipo de comida
 *
 * Comportamiento ESPECÍFICO que cada subtipo debe implementar (abstract):
 *  - puedeConsumir(Receta): ¿puede este usuario comer esta receta?
 *  - evaluarReceta(Receta): puntuación 0-100 según el tipo de usuario
 *  - evaluarMenuDiario(LocalDate): puntuación del menú de un día completo
 */
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nombre completo del usuario (ej: "Juan Pérez") */
    protected String nombre;

    /** Identificador único (ej: "U001") */
    protected String id;

    /**
     * Historial de recetas del usuario.
     * Se va construyendo con añadirReceta() a lo largo del tiempo.
     */
    protected ArrayList<Receta> historial;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Constructor base llamado por las subclases con super(nombre, id).
     *
     * @param nombre nombre del usuario (no vacío)
     * @param id     identificador único (no vacío)
     * @throws UsuarioException si nombre o id son inválidos
     */
    public Usuario(String nombre, String id) throws UsuarioException {
        if (nombre == null || nombre.isBlank()) {
            throw new UsuarioException("El nombre del usuario no puede estar vacío.");
        }
        if (id == null || id.isBlank()) {
            throw new UsuarioException("El ID del usuario no puede estar vacío.");
        }
        this.nombre = nombre;
        this.id = id;
        // Inicializamos el historial vacío
        this.historial = new ArrayList<>();
    }

    // ---------------------------------------------------------------
    // GESTIÓN DEL HISTORIAL
    // ---------------------------------------------------------------

    /**
     * Añade una receta al historial del usuario.
     *
     * @param receta receta a registrar (no null)
     * @throws UsuarioException si la receta es null
     */
    public void añadirReceta(Receta receta) throws UsuarioException {
        if (receta == null) {
            throw new UsuarioException("No se puede añadir una receta null al historial.");
        }
        historial.add(receta);
    }

    /**
     * Devuelve el total de recetas almacenadas en el historial.
     *
     * @return número de recetas
     */
    public int getNumeroRecetas() {
        return historial.size();
    }

    // ---------------------------------------------------------------
    // BÚSQUEDAS EN EL HISTORIAL
    // ---------------------------------------------------------------

    /**
     * Busca todas las recetas del historial que correspondan a una fecha exacta.
     *
     * @param fecha fecha a buscar (no null)
     * @return lista de recetas de ese día (puede estar vacía)
     * @throws UsuarioException si la fecha es null
     */
    public ArrayList<Receta> buscarPorFecha(LocalDate fecha) throws UsuarioException {
        if (fecha == null) {
            throw new UsuarioException("La fecha de búsqueda no puede ser null.");
        }
        ArrayList<Receta> resultado = new ArrayList<>();
        for (Receta r : historial) {
            // equals() en LocalDate compara año, mes y día
            if (r.getFecha().equals(fecha)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    /**
     * Busca las recetas del historial dentro de un intervalo de fechas [inicio, fin].
     * Ambos extremos son INCLUSIVOS.
     *
     * @param inicio fecha inicial del intervalo (no null)
     * @param fin    fecha final del intervalo (no null, no anterior a inicio)
     * @return lista de recetas en ese rango
     * @throws UsuarioException si las fechas son inválidas
     */
    public ArrayList<Receta> buscarEntreFechas(LocalDate inicio, LocalDate fin)
            throws UsuarioException {
        if (inicio == null || fin == null) {
            throw new UsuarioException("Las fechas del intervalo no pueden ser null.");
        }
        if (fin.isBefore(inicio)) {
            throw new UsuarioException(
                "La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        ArrayList<Receta> resultado = new ArrayList<>();
        for (Receta r : historial) {
            LocalDate fecha = r.getFecha();
            // isAfter/isBefore son exclusivos, por eso usamos !isBefore y !isAfter
            // para tener comparación inclusiva en ambos extremos
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    /**
     * Busca las recetas del historial de un tipo de comida concreto.
     *
     * @param tipo tipo de comida a filtrar (no null)
     * @return lista de recetas de ese tipo
     * @throws UsuarioException si el tipo es null
     */
    public ArrayList<Receta> buscarPorTipo(TipoComida tipo) throws UsuarioException {
        if (tipo == null) {
            throw new UsuarioException("El tipo de comida no puede ser null.");
        }
        ArrayList<Receta> resultado = new ArrayList<>();
        for (Receta r : historial) {
            if (r.getTipoComida() == tipo) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    // ---------------------------------------------------------------
    // MÉTODOS ABSTRACTOS (cada subtipo los implementa a su manera)
    // ---------------------------------------------------------------

    /**
     * Determina si el usuario puede consumir la receta dada según sus restricciones.
     *
     * Deportista: mínimo de proteínas y ≥ 200 kcal
     * UsuarioKeto: no superar máximo de carbohidratos y condición keto
     * Paciente: no superar máximo de kcal y ≤ 30g de grasa
     *
     * @param receta receta a evaluar
     * @return true si la receta es apta para este usuario
     * @throws RecetaException si hay error al calcular el perfil de la receta
     */
    public abstract boolean puedeConsumir(Receta receta) throws RecetaException;

    /**
     * Evalúa la receta con una puntuación entre 0 y 100.
     * La fórmula es diferente para cada subtipo de usuario.
     *
     * @param receta receta a puntuar
     * @return puntuación entera entre 0 y 100
     * @throws RecetaException si hay error al calcular el perfil
     */
    public abstract int evaluarReceta(Receta receta) throws RecetaException;

    /**
     * Evalúa el menú completo de un día: suma todos los perfiles de las
     * recetas de esa fecha y aplica la fórmula del subtipo.
     *
     * @param fecha fecha del menú a evaluar
     * @return puntuación entera entre 0 y 100
     * @throws UsuarioException si la fecha es null o no hay recetas ese día
     * @throws RecetaException  si hay error al calcular perfiles
     */
    public abstract int evaluarMenuDiario(LocalDate fecha)
            throws UsuarioException, RecetaException;

    // ---------------------------------------------------------------
    // MÉTODO AUXILIAR PROTEGIDO
    // ---------------------------------------------------------------

    /**
     * Ajusta una puntuación al rango [0, 100].
     * Si la fórmula da -20, devolvemos 0. Si da 120, devolvemos 100.
     *
     * Protegido (protected) porque solo lo usan las subclases.
     *
     * @param puntuacion valor a ajustar
     * @return valor entre 0 y 100
     */
    protected int ajustarPuntuacion(double puntuacion) {
        // Math.max y Math.min para "clamp" del valor
        return (int) Math.max(0, Math.min(100, Math.round(puntuacion)));
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return nombre del usuario */
    public String getNombre() { return nombre; }

    /** @return ID del usuario */
    public String getId() { return id; }

    /** @return historial completo de recetas */
    public ArrayList<Receta> getHistorial() { return historial; }

    /**
     * @param nombre nuevo nombre (no vacío)
     * @throws UsuarioException si el nombre es vacío o null
     */
    public void setNombre(String nombre) throws UsuarioException {
        if (nombre == null || nombre.isBlank()) {
            throw new UsuarioException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Representación base del usuario: nombre, ID y número de recetas.
     * Las subclases añadirán sus datos específicos sobreescribiendo esto.
     */
    @Override
    public String toString() {
        return String.format("Usuario: %-20s | ID: %-6s | Recetas: %d",
            nombre, id, historial.size());
    }
}
