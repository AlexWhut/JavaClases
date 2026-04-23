package RedSocial;

// ============================================================
// CLASE ABSTRACTA: Usuario
// ------------------------------------------------------------
// Al ser abstracta no se puede crear con "new Usuario(...)".
// Solo sirve como plantilla comun para UsuarioNormal y
// Administrador. Aplica ENCAPSULACION (atributos private +
// getters/setters) y define el contrato del metodo abstracto
// mostrarPerfil() que cada subclase debe implementar.
// ============================================================

/**
 * Plantilla base para todos los usuarios de la red social.
 *
 * <p>Aplica los pilares de OOP:</p>
 * <ul>
 *   <li><b>Encapsulacion</b>  – atributos {@code private}, acceso via getters/setters.</li>
 *   <li><b>Herencia</b>       – {@link UsuarioNormal} y {@link Administrador} la extienden.</li>
 *   <li><b>Abstraccion</b>    – define el contrato {@link #mostrarPerfil()} sin implementarlo.</li>
 *   <li><b>Polimorfismo</b>   – cada subclase sobreescribe {@link #mostrarPerfil()} a su manera.</li>
 * </ul>
 */
public abstract class Usuario {

    // ----------------------------------------------------------
    // ATRIBUTOS PRIVADOS (Encapsulacion)
    // Nadie fuera de esta clase puede leerlos ni cambiarlos
    // directamente; deben pasar siempre por los getters/setters.
    // ----------------------------------------------------------

    /** Nombre completo del usuario. */
    private String nombre;

    /** Edad del usuario en anos. */
    private int    edad;

    /** Rol asignado segun la edad al registrarse. */
    private Rol    rol;

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // Inicializa los tres atributos basicos de cualquier usuario.
    // ----------------------------------------------------------

    /**
     * Crea un usuario con sus datos fundamentales.
     *
     * @param nombre nombre completo del usuario
     * @param edad   edad del usuario en anos
     * @param rol    rol asignado ({@link Rol#USUARIO} o {@link Rol#ADMINISTRADOR})
     */
    public Usuario(String nombre, int edad, Rol rol) {
        this.nombre = nombre;
        this.edad   = edad;
        this.rol    = rol;
    }

    // ----------------------------------------------------------
    // GETTERS: permiten leer los atributos desde fuera.
    // ----------------------------------------------------------

    /**
     * @return nombre completo del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return edad del usuario en anos
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @return rol asignado al usuario
     */
    public Rol getRol() {
        return rol;
    }

    // ----------------------------------------------------------
    // SETTERS: permiten modificar los atributos desde fuera.
    // ----------------------------------------------------------

    /**
     * @param nombre nuevo nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param edad nueva edad del usuario
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @param rol nuevo rol del usuario
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // ----------------------------------------------------------
    // METODO ABSTRACTO (Abstraccion + Polimorfismo)
    // No tiene cuerpo aqui; cada subclase DEBE sobreescribirlo.
    // Esto garantiza que todo usuario sabe mostrarse, pero cada
    // tipo lo hace a su propia manera.
    // ----------------------------------------------------------

    /**
     * Muestra por consola la informacion del perfil del usuario.
     * Cada subclase implementa este metodo con sus propios datos.
     */
    public abstract void mostrarPerfil();
}
