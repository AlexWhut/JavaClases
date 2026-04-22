package GestorDietas;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa una receta compuesta por varios ingredientes.
 *
 * Una receta:
 *  - tiene nombre, fecha y tipo de comida (desayuno, comida, etc.)
 *  - contiene una lista de ingredientes
 *  - puede calcular su perfil nutricional total sumando el de cada ingrediente
 *  - puede calcular sus calorías totales
 *
 * La fecha y el tipo de comida permiten clasificarla y hacer búsquedas
 * (ej: "todas las CENAS del 24/03/2026").
 */
public class Receta implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nombre de la receta (ej: "Pollo con arroz") */
    private String nombre;

    /** Fecha en la que se consume/registra la receta */
    private LocalDate fecha;

    /** Momento del día: DESAYUNO, COMIDA, MERIENDA o CENA */
    private TipoComida tipoComida;

    /**
     * Lista de ingredientes de la receta.
     * ArrayList porque el tamaño varía (añadimos/eliminamos ingredientes).
     */
    private ArrayList<Ingrediente> ingredientes;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Crea una nueva receta vacía (sin ingredientes aún).
     *
     * @param nombre    nombre de la receta (no vacío)
     * @param fecha     fecha de la receta (no null)
     * @param tipoComida momento del día (no null)
     * @throws RecetaException si algún parámetro es inválido
     */
    public Receta(String nombre, LocalDate fecha, TipoComida tipoComida)
            throws RecetaException {
        if (nombre == null || nombre.isBlank()) {
            throw new RecetaException("El nombre de la receta no puede estar vacío.");
        }
        if (fecha == null) {
            throw new RecetaException("La fecha de la receta no puede ser null.");
        }
        if (tipoComida == null) {
            throw new RecetaException("El tipo de comida no puede ser null.");
        }
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipoComida = tipoComida;
        // Inicializamos la lista vacía — se añaden ingredientes después
        this.ingredientes = new ArrayList<>();
    }

    // ---------------------------------------------------------------
    // GESTIÓN DE INGREDIENTES
    // ---------------------------------------------------------------

    /**
     * Añade un ingrediente a la receta.
     *
     * @param ingrediente ingrediente a añadir (no null)
     * @throws RecetaException si el ingrediente es null
     */
    public void añadirIngrediente(Ingrediente ingrediente) throws RecetaException {
        if (ingrediente == null) {
            throw new RecetaException("No se puede añadir un ingrediente null.");
        }
        ingredientes.add(ingrediente);
    }

    /**
     * Elimina un ingrediente de la receta buscándolo por nombre.
     *
     * Iteramos la lista con un iterador explícito para poder eliminar
     * mientras recorremos sin ConcurrentModificationException.
     *
     * @param nombreIngrediente nombre del ingrediente a eliminar (no vacío)
     * @return true si se encontró y eliminó, false si no existía
     * @throws RecetaException si el nombre es vacío o null
     */
    public boolean eliminarIngrediente(String nombreIngrediente) throws RecetaException {
        if (nombreIngrediente == null || nombreIngrediente.isBlank()) {
            throw new RecetaException("El nombre del ingrediente no puede estar vacío.");
        }
        // removeIf elimina todos los elementos que cumplan la condición
        return ingredientes.removeIf(
            ing -> ing.getNombre().equalsIgnoreCase(nombreIngrediente)
        );
    }

    // ---------------------------------------------------------------
    // CÁLCULOS NUTRICIONALES
    // ---------------------------------------------------------------

    /**
     * Calcula el perfil nutricional total de la receta.
     *
     * Suma el perfil real (ajustado a la cantidad) de cada ingrediente.
     * Empezamos con un perfil vacío (0,0,0) y vamos acumulando.
     *
     * @return PerfilNutricional total de todos los ingredientes
     * @throws RecetaException si la receta no tiene ingredientes o hay error de cálculo
     */
    public PerfilNutricional getPerfilReceta() throws RecetaException {
        if (ingredientes.isEmpty()) {
            throw new RecetaException(
                "La receta '" + nombre + "' no tiene ingredientes.");
        }
        try {
            // Perfil acumulador iniciado a 0
            PerfilNutricional total = new PerfilNutricional();
            for (Ingrediente ing : ingredientes) {
                // getPerfilIngrediente() ya aplica la escala por cantidad
                total = total.sumar(ing.getPerfilIngrediente());
            }
            return total;
        } catch (ValorNutricionalException e) {
            throw new RecetaException("Error al calcular el perfil total: " + e.getMessage());
        }
    }

    /**
     * Calcula las calorías totales de la receta.
     *
     * @return kcal totales como entero
     * @throws RecetaException si hay error al calcular el perfil
     */
    public int getKcalReceta() throws RecetaException {
        return getPerfilReceta().kcalTotales();
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return nombre de la receta */
    public String getNombre() { return nombre; }

    /** @return fecha de la receta */
    public LocalDate getFecha() { return fecha; }

    /** @return tipo de comida (DESAYUNO, COMIDA, MERIENDA, CENA) */
    public TipoComida getTipoComida() { return tipoComida; }

    /** @return lista de ingredientes (copia de referencia) */
    public ArrayList<Ingrediente> getIngredientes() { return ingredientes; }

    /**
     * @param nombre nuevo nombre (no vacío)
     * @throws RecetaException si el nombre es vacío o null
     */
    public void setNombre(String nombre) throws RecetaException {
        if (nombre == null || nombre.isBlank()) {
            throw new RecetaException("El nombre de la receta no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * @param fecha nueva fecha (no null)
     * @throws RecetaException si la fecha es null
     */
    public void setFecha(LocalDate fecha) throws RecetaException {
        if (fecha == null) throw new RecetaException("La fecha no puede ser null.");
        this.fecha = fecha;
    }

    /**
     * @param tipoComida nuevo tipo de comida (no null)
     * @throws RecetaException si el tipo es null
     */
    public void setTipoComida(TipoComida tipoComida) throws RecetaException {
        if (tipoComida == null) throw new RecetaException("El tipo de comida no puede ser null.");
        this.tipoComida = tipoComida;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Resumen de la receta: nombre, fecha, tipo y número de ingredientes.
     */
    @Override
    public String toString() {
        return String.format("Receta: %-25s | Fecha: %s | Tipo: %-9s | Ingredientes: %d",
            nombre, fecha, tipoComida, ingredientes.size());
    }
}
