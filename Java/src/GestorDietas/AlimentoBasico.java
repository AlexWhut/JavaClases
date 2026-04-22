package GestorDietas;

/**
 * Ingrediente básico sin procesado: frutas, verduras, carnes, pescados, etc.
 *
 * Extiende Ingrediente añadiendo únicamente el tipo de ingrediente
 * (FRUTA, VERDURA, CARNE, PESCADO u OTROS).
 *
 * Es la subclase más sencilla de Ingrediente.
 */
public class AlimentoBasico extends Ingrediente {

    private static final long serialVersionUID = 1L;

    /**
     * Categoría del alimento según TipoIngrediente (enum).
     * Ejemplo: una manzana sería TipoIngrediente.FRUTA.
     */
    private TipoIngrediente tipoIngrediente;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Crea un alimento básico con su categoría.
     *
     * Usamos super(...) para reutilizar las validaciones de la clase padre.
     * Así no duplicamos código de validación.
     *
     * @param nombre           nombre del alimento (ej: "Manzana")
     * @param perfilNutricional valores nutricionales por 100g
     * @param cantidad         gramos usados en la receta
     * @param tipoIngrediente  categoría del alimento
     * @throws RecetaException si nombre vacío, perfil null, cantidad ≤ 0, o tipo null
     */
    public AlimentoBasico(String nombre, PerfilNutricional perfilNutricional,
                          double cantidad, TipoIngrediente tipoIngrediente)
            throws RecetaException {
        // Llamamos al constructor de Ingrediente — él hace las validaciones comunes
        super(nombre, perfilNutricional, cantidad);

        if (tipoIngrediente == null) {
            throw new RecetaException("El tipo de ingrediente no puede ser null.");
        }
        this.tipoIngrediente = tipoIngrediente;
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return categoría del alimento (enum TipoIngrediente) */
    public TipoIngrediente getTipoIngrediente() { return tipoIngrediente; }

    /**
     * @param tipoIngrediente nueva categoría (no null)
     * @throws RecetaException si el tipo es null
     */
    public void setTipoIngrediente(TipoIngrediente tipoIngrediente) throws RecetaException {
        if (tipoIngrediente == null) {
            throw new RecetaException("El tipo de ingrediente no puede ser null.");
        }
        this.tipoIngrediente = tipoIngrediente;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Añade el tipo de ingrediente a la representación del padre.
     */
    @Override
    public String toString() {
        return "[" + tipoIngrediente + "] " + super.toString();
    }
}
