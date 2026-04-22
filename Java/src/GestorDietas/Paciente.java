package GestorDietas;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Usuario de tipo Paciente (con restricciones médicas).
 *
 * Sus restricciones son más estrictas que las de otros usuarios:
 *  1. La receta no puede superar el máximo de calorías del paciente
 *  2. La receta no puede tener más de 30 gramos de grasa
 *
 * Su fórmula de evaluación:
 *   100 - 0.2*kcal - 1.5*grasas + 0.8*proteinas
 *
 * La idea es que el paciente debe controlar calorías y grasas,
 * pero se valora positivamente que la receta aporte proteínas.
 */
public class Paciente extends Usuario {

    private static final long serialVersionUID = 1L;

    /** Límite máximo de calorías (kcal) por receta para este paciente */
    private int maxKcal;

    // Constante para no tener "magic numbers" en el código
    private static final double MAX_GRASAS_PERMITIDAS = 30.0;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Crea un usuario de tipo Paciente con su límite calórico.
     *
     * @param nombre  nombre completo
     * @param id      identificador único
     * @param maxKcal límite máximo de kcal por receta (> 0)
     * @throws UsuarioException si nombre/id inválidos o maxKcal ≤ 0
     */
    public Paciente(String nombre, String id, int maxKcal) throws UsuarioException {
        super(nombre, id);
        if (maxKcal <= 0) {
            throw new UsuarioException(
                "El máximo de kcal debe ser positivo. Recibido: " + maxKcal);
        }
        this.maxKcal = maxKcal;
    }

    // ---------------------------------------------------------------
    // IMPLEMENTACIONES DE LOS MÉTODOS ABSTRACTOS
    // ---------------------------------------------------------------

    /**
     * Una receta es apta para el paciente si:
     *  1. Calorías totales <= maxKcal
     *  2. Grasas totales <= 30g
     *
     * @param receta receta a evaluar
     * @return true si cumple ambas condiciones
     * @throws RecetaException si hay error al calcular el perfil
     */
    @Override
    public boolean puedeConsumir(Receta receta) throws RecetaException {
        if (receta == null) throw new RecetaException("La receta no puede ser null.");

        PerfilNutricional perfil = receta.getPerfilReceta();
        int kcal = receta.getKcalReceta();

        boolean kcalOk = kcal <= maxKcal;
        boolean grasasOk = perfil.getGrasas() <= MAX_GRASAS_PERMITIDAS;

        return kcalOk && grasasOk;
    }

    /**
     * Puntuación para el paciente de una receta individual.
     *
     * Fórmula: 100 - 0.2*kcal - 1.5*grasas + 0.8*proteinas
     *
     * Análisis:
     *  - Base alta (100), se resta por calorías y grasas
     *  - -0.2 por cada kcal (penaliza las calorías)
     *  - -1.5 por cada gramo de grasa (penaliza las grasas fuertemente)
     *  - +0.8 por cada gramo de proteína (premia las proteínas)
     *
     * @param receta receta a puntuar
     * @return puntuación ajustada entre 0 y 100
     * @throws RecetaException si hay error al calcular el perfil
     */
    @Override
    public int evaluarReceta(Receta receta) throws RecetaException {
        if (receta == null) throw new RecetaException("La receta no puede ser null.");

        PerfilNutricional perfil = receta.getPerfilReceta();
        double grasas = perfil.getGrasas();
        double proteinas = perfil.getProteinas();
        int kcal = receta.getKcalReceta();

        double puntuacion = 100 - 0.2 * kcal - 1.5 * grasas + 0.8 * proteinas;

        return ajustarPuntuacion(puntuacion);
    }

    /**
     * Evalúa el menú diario del paciente.
     *
     * @param fecha día a evaluar
     * @return puntuación 0-100
     * @throws UsuarioException si fecha null o no hay recetas ese día
     * @throws RecetaException  si hay error en el cálculo
     */
    @Override
    public int evaluarMenuDiario(LocalDate fecha) throws UsuarioException, RecetaException {
        if (fecha == null) throw new UsuarioException("La fecha no puede ser null.");

        ArrayList<Receta> recetasDia = buscarPorFecha(fecha);
        if (recetasDia.isEmpty()) {
            throw new UsuarioException(
                "No hay recetas registradas para la fecha: " + fecha);
        }

        try {
            PerfilNutricional totalDia = new PerfilNutricional();
            int kcalTotales = 0;
            for (Receta r : recetasDia) {
                totalDia = totalDia.sumar(r.getPerfilReceta());
                kcalTotales += r.getKcalReceta();
            }

            double puntuacion = 100
                - 0.2 * kcalTotales
                - 1.5 * totalDia.getGrasas()
                + 0.8 * totalDia.getProteinas();

            return ajustarPuntuacion(puntuacion);
        } catch (ValorNutricionalException e) {
            throw new RecetaException("Error al calcular el menú diario: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return máximo de kcal permitido por receta */
    public int getMaxKcal() { return maxKcal; }

    /**
     * @param maxKcal nuevo límite calórico (> 0)
     * @throws UsuarioException si el valor no es positivo
     */
    public void setMaxKcal(int maxKcal) throws UsuarioException {
        if (maxKcal <= 0) {
            throw new UsuarioException("El máximo de kcal debe ser positivo.");
        }
        this.maxKcal = maxKcal;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString()
            + String.format(" | Tipo: Paciente | Max.Kcal: %d", maxKcal);
    }
}
