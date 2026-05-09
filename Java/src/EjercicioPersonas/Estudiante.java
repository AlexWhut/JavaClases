package EjercicioPersonas;

/**
 * Representa a un estudiante universitario.
 *
 * <p>Extiende {@link Persona} (HERENCIA) y sobrescribe {@link #describirse()}
 * para personalizar su presentación (POLIMORFISMO).</p>
 *
 * <p>Añade dos atributos propios del subtipo: {@code carrera} y {@code curso}.</p>
 *
 * @author EjercicioPersonas
 * @version 1.0
 */
public class Estudiante extends Persona {

    private String carrera;
    private int    curso;   // 1 al 4

    /**
     * Construye un estudiante con todos sus datos.
     *
     * @param nombre  nombre completo
     * @param edad    edad en años (0–120)
     * @param email   correo de contacto
     * @param carrera nombre de la carrera que estudia
     * @param curso   año de carrera actual (1–4)
     * @throws EdadInvalidaException si la edad está fuera de rango
     */
    public Estudiante(String nombre, int edad, String email,
                      String carrera, int curso) throws EdadInvalidaException {
        super(nombre, edad, email);
        this.carrera = carrera;
        this.curso   = curso;
    }

    /**
     * Devuelve la carrera que estudia.
     *
     * @return nombre de la carrera
     */
    public String getCarrera() { return carrera; }

    /**
     * Devuelve el año de carrera actual.
     *
     * @return curso (1–4)
     */
    public int getCurso() { return curso; }

    /**
     * Descripción específica del estudiante: incluye carrera y curso.
     *
     * <p>Sobrescribe el método abstracto de {@link Persona} (POLIMORFISMO).</p>
     *
     * @return cadena con los datos del estudiante
     */
    @Override
    public String describirse() {
        return "[Estudiante] " + getNombre()
                + " | " + getEdad() + " años"
                + " | " + carrera + " - " + curso + "º curso";
    }
}
