package GestorDietas;

/**
 * Enum que representa los tipos de comida del día.
 *
 * Un enum es una clase especial con un número fijo de instancias constantes.
 * Así evitamos usar Strings como "DESAYUNO" (que pueden tener erratas).
 * Con un enum, el compilador garantiza que solo existen estos valores.
 */
public enum TipoComida {

    /** Primera comida del día */
    DESAYUNO,

    /** Comida principal del mediodía */
    COMIDA,

    /** Merienda de la tarde */
    MERIENDA,

    /** Comida de la noche */
    CENA
}
