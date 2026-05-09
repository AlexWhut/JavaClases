package EjercicioPersonas;

/**
 * Clase <b>abstracta</b> que representa a una persona genérica.
 *
 * <p>Demuestra dos pilares de la POO:</p>
 * <ul>
 *   <li><b>Encapsulación</b>: campos privados accesibles solo por getters/setters.</li>
 *   <li><b>Abstracción</b>: define {@link #describirse()} como método abstracto;
 *       cada subclase decide cómo presentarse.</li>
 * </ul>
 *
 * @author EjercicioPersonas
 * @version 1.0
 */
public abstract class Persona {

    // --- ENCAPSULACIÓN: campos privados ---
    private String nombre;
    private int    edad;
    private String email;

    /**
     * Construye una persona validando la edad.
     *
     * @param nombre nombre completo de la persona
     * @param edad   edad en años (debe estar entre 0 y 120)
     * @param email  correo electrónico de contacto
     * @throws EdadInvalidaException si la edad está fuera del rango 0–120
     */
    public Persona(String nombre, int edad, String email) throws EdadInvalidaException {
        this.nombre = nombre;
        this.email  = email;
        setEdad(edad); // delega la validación al setter
    }

    // -------------------------
    //  Getters
    // -------------------------

    /**
     * Devuelve el nombre de la persona.
     *
     * @return nombre completo
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve la edad de la persona.
     *
     * @return edad en años
     */
    public int getEdad() { return edad; }

    /**
     * Devuelve el email de la persona.
     *
     * @return dirección de correo electrónico
     */
    public String getEmail() { return email; }

    // -------------------------
    //  Setters con validación
    // -------------------------

    /**
     * Actualiza la edad validando que esté en el rango permitido.
     *
     * @param edad nueva edad (0–120)
     * @throws EdadInvalidaException si el valor está fuera de rango
     */
    public void setEdad(int edad) throws EdadInvalidaException {
        if (edad < 0 || edad > 120) {
            throw new EdadInvalidaException(edad);
        }
        this.edad = edad;
    }

    // -------------------------
    //  Método abstracto (ABSTRACCIÓN)
    // -------------------------

    /**
     * Devuelve una descripción detallada de la persona según su tipo.
     *
     * <p>Cada subclase <b>debe</b> sobrescribir este método
     * (POLIMORFISMO por herencia).</p>
     *
     * @return cadena descriptiva con los datos relevantes del subtipo
     */
    public abstract String describirse();

    /**
     * Representación básica compartida por todos los subtipos.
     *
     * @return nombre, edad y email separados por coma
     */
    @Override
    public String toString() {
        return nombre + " | " + edad + " años | " + email;
    }
}
