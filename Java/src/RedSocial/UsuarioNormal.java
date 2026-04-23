package RedSocial;

// ============================================================
// CLASE: UsuarioNormal
// ------------------------------------------------------------
// Hereda de Usuario y representa a un miembro con rol basico.
// Solo necesita sobreescribir mostrarPerfil() para mostrar
// su nombre y edad. No tiene atributos propios adicionales.
// ============================================================

/**
 * Usuario con rol basico en la red social.
 *
 * <p>Hereda todos los atributos y metodos de {@link Usuario} y
 * sobreescribe {@link #mostrarPerfil()} para mostrar su nombre y edad.</p>
 */
public class UsuarioNormal extends Usuario {

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // Llama al constructor del padre con super() para inicializar
    // nombre, edad y rol. No necesitamos repetir ese codigo aqui.
    // ----------------------------------------------------------

    /**
     * Crea un usuario normal pasando los datos al constructor padre.
     *
     * @param nombre nombre completo del usuario
     * @param edad   edad del usuario en anos
     * @param rol    rol asignado (en la practica sera {@link Rol#USUARIO})
     */
    public UsuarioNormal(String nombre, int edad, Rol rol) {
        super(nombre, edad, rol); // delega la inicializacion a Usuario
    }

    // ----------------------------------------------------------
    // SOBREESCRITURA DE METODO ABSTRACTO (Polimorfismo)
    // @Override indica que este metodo reemplaza al de la clase padre.
    // Solo mostramos nombre y edad, que son los datos basicos.
    // ----------------------------------------------------------

    /**
     * Muestra por consola el nombre y la edad del usuario normal.
     */
    @Override
    public void mostrarPerfil() {
        System.out.println("===== PERFIL DE USUARIO =====");
        System.out.println("Nombre : " + getNombre());
        System.out.println("Edad   : " + getEdad() + " anos");
        System.out.println("Rol    : " + getRol());
    }
}
