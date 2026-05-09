package EjercicioPersonas;

/**
 * Representa a un empleado en activo.
 *
 * <p>Extiende {@link Persona} (HERENCIA) y sobrescribe {@link #describirse()}
 * con información laboral propia (POLIMORFISMO).</p>
 *
 * @author EjercicioPersonas
 * @version 1.0
 */
public class Empleado extends Persona {

    private String empresa;
    private double salario;

    /**
     * Construye un empleado con todos sus datos.
     *
     * @param nombre  nombre completo
     * @param edad    edad en años (0–120)
     * @param email   correo de contacto
     * @param empresa nombre de la empresa donde trabaja
     * @param salario salario bruto anual en euros
     * @throws EdadInvalidaException si la edad está fuera de rango
     */
    public Empleado(String nombre, int edad, String email,
                    String empresa, double salario) throws EdadInvalidaException {
        super(nombre, edad, email);
        this.empresa = empresa;
        this.salario = salario;
    }

    /**
     * Devuelve el nombre de la empresa.
     *
     * @return empresa donde trabaja el empleado
     */
    public String getEmpresa() { return empresa; }

    /**
     * Devuelve el salario bruto anual.
     *
     * @return salario en euros
     */
    public double getSalario() { return salario; }

    /**
     * Descripción específica del empleado: incluye empresa y salario.
     *
     * <p>Sobrescribe el método abstracto de {@link Persona} (POLIMORFISMO).</p>
     *
     * @return cadena con los datos laborales del empleado
     */
    @Override
    public String describirse() {
        return "[Empleado]  " + getNombre()
                + " | " + getEdad() + " años"
                + " | " + empresa + " | " + salario + "€/año";
    }
}
