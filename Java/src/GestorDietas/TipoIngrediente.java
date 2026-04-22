package GestorDietas;

/**
 * Enum que clasifica los ingredientes por su naturaleza alimentaria.
 *
 * Permite filtrar recetas o ingredientes por categoría.
 * Por ejemplo: buscar todas las recetas que usan PESCADO o FRUTA.
 */
public enum TipoIngrediente {

    /** Frutas frescas o secas */
    FRUTA,

    /** Verduras y hortalizas */
    VERDURA,

    /** Carnes de cualquier tipo */
    CARNE,

    /** Pescados y mariscos */
    PESCADO,

    /** Ingredientes que no encajan en las otras categorías */
    OTROS
}
