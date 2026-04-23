package RedSocial;

// ============================================================
// CLASE: Administrador
// ------------------------------------------------------------
// Hereda de Usuario y representa a un miembro con privilegios.
// Ademas de sobreescribir mostrarPerfil(), agrega el metodo
// propio gestionarUsuarios() que solo tienen los admins.
// ============================================================

/**
 * Usuario con rol de administrador en la red social.
 *
 * <p>Hereda de {@link Usuario} y extiende su comportamiento con:</p>
 * <ul>
 *   <li>{@link #mostrarPerfil()} – muestra nombre, edad y mensaje de permisos.</li>
 *   <li>{@link #gestionarUsuarios()} – accion exclusiva del administrador.</li>
 * </ul>
 */
public class Administrador extends Usuario {

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // Igual que en UsuarioNormal: delegamos al padre con super().
    // ----------------------------------------------------------

    /**
     * Crea un administrador pasando los datos al constructor padre.
     *
     * @param nombre nombre completo del administrador
     * @param edad   edad del administrador en anos
     * @param rol    rol asignado (en la practica sera {@link Rol#ADMINISTRADOR})
     */
    public Administrador(String nombre, int edad, Rol rol) {
        super(nombre, edad, rol); // inicializa los atributos heredados
    }

    // ----------------------------------------------------------
    // SOBREESCRITURA DE METODO ABSTRACTO (Polimorfismo)
    // El administrador muestra mas informacion que un usuario normal:
    // tambien indica que tiene permisos de administracion.
    // ----------------------------------------------------------

    /**
     * Muestra por consola el nombre, la edad y los permisos del administrador.
     */
    @Override
    public void mostrarPerfil() {
        System.out.println("===== PERFIL DE ADMINISTRADOR =====");
        System.out.println("Nombre  : " + getNombre());
        System.out.println("Edad    : " + getEdad() + " anos");
        System.out.println("Rol     : " + getRol());
        System.out.println("Permisos: Tienes permisos de administrador.");
    }

    // ----------------------------------------------------------
    // METODO PROPIO DEL ADMINISTRADOR
    // Este metodo NO existe en Usuario ni en UsuarioNormal.
    // Solo los Administrador pueden llamarlo. Esto es un ejemplo
    // de como las subclases pueden ampliar el comportamiento heredado.
    // ----------------------------------------------------------

    /**
     * Accion exclusiva del administrador: gestionar los usuarios del sistema.
     */
    public void gestionarUsuarios() {
        System.out.println("Gestionando usuarios del sistema...");
    }
}
