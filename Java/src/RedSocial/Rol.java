package RedSocial;

// ============================================================
// ENUM: Rol
// ------------------------------------------------------------
// Un enum es un tipo especial que define un conjunto fijo de
// constantes. Aqui solo existen dos roles posibles, asi nunca
// podremos asignar un valor invalido por error.
// ============================================================

/**
 * Define los dos roles posibles dentro de la red social.
 *
 * <ul>
 *   <li>{@link #USUARIO}        – usuario normal sin privilegios extra.</li>
 *   <li>{@link #ADMINISTRADOR}  – usuario con permisos de gestion.</li>
 * </ul>
 */
public enum Rol {
    /** Rol basico: puede navegar y publicar en la red social. */
    USUARIO,

    /** Rol privilegiado: puede ademas gestionar otros usuarios. */
    ADMINISTRADOR
}
