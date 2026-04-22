package GestorDietas;

import java.io.Serializable;

/**
 * Clase abstracta que representa un ingrediente de una receta.
 *
 * Por qué abstracta: "Ingrediente" por sí solo es un concepto genérico.
 * En la práctica todo ingrediente es o un AlimentoBasico o un AlimentoProcesado.
 * Al hacerla abstracta, obligamos a usar una de las subclases concretas.
 *
 * Cada ingrediente tiene:
 *  - nombre: identificador del ingrediente
 *  - perfilNutricional: nutrientes por cada 100g
 *  - cantidad: gramos que se usan en la receta concreta
 */
public abstract class Ingrediente implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nombre del ingrediente (ej: "Pechuga de pollo") */
    protected String nombre;

    /** Perfil nutricional base: valores por cada 100 gramos */
    protected PerfilNutricional perfilNutricional;

    /** Cantidad en gramos usada en la receta */
    protected double cantidad;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Constructor base llamado por las subclases con super(...).
     *
     * @param nombre           nombre del ingrediente (no vacío)
     * @param perfilNutricional perfil por 100g (no null)
     * @param cantidad         gramos usados en la receta (> 0)
     * @throws RecetaException si algún parámetro es inválido
     */
    public Ingrediente(String nombre, PerfilNutricional perfilNutricional, double cantidad)
            throws RecetaException {
        if (nombre == null || nombre.isBlank()) {
            throw new RecetaException("El nombre del ingrediente no puede estar vacío.");
        }
        if (perfilNutricional == null) {
            throw new RecetaException("El perfil nutricional no puede ser null.");
        }
        if (cantidad <= 0) {
            throw new RecetaException(
                "La cantidad debe ser mayor que 0. Recibido: " + cantidad);
        }
        this.nombre = nombre;
        this.perfilNutricional = perfilNutricional;
        this.cantidad = cantidad;
    }

    // ---------------------------------------------------------------
    // MÉTODO PRINCIPAL: getPerfilIngrediente
    // ---------------------------------------------------------------

    /**
     * Calcula el perfil nutricional REAL que este ingrediente aporta a la receta.
     *
     * Como perfilNutricional es por 100g, hay que escalarlo según la cantidad real:
     *   perfil_real = perfil_base * (cantidad / 100)
     *
     * Ejemplo: 150g de pollo con 25g prot/100g → 25 * (150/100) = 37.5g proteína
     *
     * @return PerfilNutricional ajustado a la cantidad real
     * @throws RecetaException si hay un error al escalar el perfil
     */
    public PerfilNutricional getPerfilIngrediente() throws RecetaException {
        try {
            // Dividimos entre 100 porque el perfil base es "por 100 gramos"
            return perfilNutricional.escalar(cantidad / 100.0);
        } catch (ValorNutricionalException e) {
            throw new RecetaException(
                "Error al calcular el perfil de " + nombre + ": " + e.getMessage());
        }
    }

    /**
     * Calorías totales que aporta este ingrediente (según su cantidad real).
     *
     * @return kcal como entero
     * @throws RecetaException si hay error al calcular el perfil
     */
    public int getKcalIngrediente() throws RecetaException {
        return getPerfilIngrediente().kcalTotales();
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return nombre del ingrediente */
    public String getNombre() { return nombre; }

    /** @return perfil nutricional base (por 100g) */
    public PerfilNutricional getPerfilNutricional() { return perfilNutricional; }

    /** @return cantidad en gramos usada en la receta */
    public double getCantidad() { return cantidad; }

    /**
     * @param nombre nuevo nombre (no vacío)
     * @throws RecetaException si el nombre es vacío o null
     */
    public void setNombre(String nombre) throws RecetaException {
        if (nombre == null || nombre.isBlank()) {
            throw new RecetaException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * @param cantidad nueva cantidad en gramos (> 0)
     * @throws RecetaException si la cantidad no es positiva
     */
    public void setCantidad(double cantidad) throws RecetaException {
        if (cantidad <= 0) {
            throw new RecetaException("La cantidad debe ser mayor que 0.");
        }
        this.cantidad = cantidad;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Representación textual del ingrediente.
     * Las subclases pueden sobrescribir esto para añadir sus propios campos.
     */
    @Override
    public String toString() {
        String perfil;
        try {
            perfil = getPerfilIngrediente().toString();
        } catch (RecetaException e) {
            perfil = "(error al calcular perfil)";
        }
        return String.format("%s (%.0fg) → %s", nombre, cantidad, perfil);
    }
}
