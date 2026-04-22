package GestorDietas;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Usuario que sigue una dieta cetogénica (keto).
 *
 * La dieta keto es muy baja en carbohidratos y alta en grasas.
 * El cuerpo entra en estado de "cetosis" y quema grasa como combustible.
 *
 * Sus restricciones son:
 *  1. La receta no puede superar el máximo de carbohidratos del usuario
 *  2. La receta debe cumplir la condición keto (esKeto() en PerfilNutricional)
 *
 * Su fórmula de evaluación:
 *   70 + 1.5*grasas - 3*carbohidratos + 0.5*proteinas
 */
public class UsuarioKeto extends Usuario {

    private static final long serialVersionUID = 1L;

    /**
     * Máximo de carbohidratos (en gramos) permitidos por receta.
     * En dieta keto típicamente < 20-50g al día.
     */
    private double maxCarbohidratos;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Crea un usuario Keto con su límite de carbohidratos.
     *
     * @param nombre           nombre completo
     * @param id               identificador único
     * @param maxCarbohidratos límite máximo de carbs por receta (> 0)
     * @throws UsuarioException si nombre/id inválidos o maxCarbohidratos ≤ 0
     */
    public UsuarioKeto(String nombre, String id, double maxCarbohidratos)
            throws UsuarioException {
        super(nombre, id);
        if (maxCarbohidratos <= 0) {
            throw new UsuarioException(
                "El máximo de carbohidratos debe ser positivo. Recibido: " + maxCarbohidratos);
        }
        this.maxCarbohidratos = maxCarbohidratos;
    }

    // ---------------------------------------------------------------
    // IMPLEMENTACIONES DE LOS MÉTODOS ABSTRACTOS
    // ---------------------------------------------------------------

    /**
     * Una receta es apta para el usuario keto si:
     *  1. Carbohidratos totales <= maxCarbohidratos
     *  2. El perfil cumple la condición keto (definida en PerfilNutricional.esKeto())
     *
     * @param receta receta a evaluar
     * @return true si cumple ambas condiciones
     * @throws RecetaException si hay error al calcular el perfil
     */
    @Override
    public boolean puedeConsumir(Receta receta) throws RecetaException {
        if (receta == null) throw new RecetaException("La receta no puede ser null.");

        PerfilNutricional perfil = receta.getPerfilReceta();

        // Condición 1: carbohidratos dentro del límite
        boolean carbsOk = perfil.getCarbohidratos() <= maxCarbohidratos;

        // Condición 2: el perfil cumple criterios keto (carbs<10 y grasas>proteínas)
        boolean esKeto = perfil.esKeto();

        return carbsOk && esKeto;
    }

    /**
     * Puntuación keto para una receta individual.
     *
     * Fórmula: 70 + 1.5*grasas - 3*carbohidratos + 0.5*proteinas
     *
     * Análisis:
     *  - Base alta (70) porque una buena receta keto ya cumple bastantes criterios
     *  - +1.5 por cada gramo de grasa (la grasa es buena en keto)
     *  - -3 por cada gramo de carbohidrato (los carbs son el "enemigo" en keto)
     *  - +0.5 por cada gramo de proteína (las proteínas ayudan)
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
        double carbohidratos = perfil.getCarbohidratos();
        double proteinas = perfil.getProteinas();

        double puntuacion = 70 + 1.5 * grasas - 3 * carbohidratos + 0.5 * proteinas;

        return ajustarPuntuacion(puntuacion);
    }

    /**
     * Evalúa el menú diario sumando todos los perfiles del día y aplicando la fórmula keto.
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
            for (Receta r : recetasDia) {
                totalDia = totalDia.sumar(r.getPerfilReceta());
            }

            double puntuacion = 70
                + 1.5 * totalDia.getGrasas()
                - 3 * totalDia.getCarbohidratos()
                + 0.5 * totalDia.getProteinas();

            return ajustarPuntuacion(puntuacion);
        } catch (ValorNutricionalException e) {
            throw new RecetaException("Error al calcular el menú diario: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return máximo de carbohidratos permitido */
    public double getMaxCarbohidratos() { return maxCarbohidratos; }

    /**
     * @param maxCarbohidratos nuevo máximo (> 0)
     * @throws UsuarioException si el valor no es positivo
     */
    public void setMaxCarbohidratos(double maxCarbohidratos) throws UsuarioException {
        if (maxCarbohidratos <= 0) {
            throw new UsuarioException("El máximo de carbohidratos debe ser positivo.");
        }
        this.maxCarbohidratos = maxCarbohidratos;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString()
            + String.format(" | Tipo: UsuarioKeto | Max.Carbs: %.1fg", maxCarbohidratos);
    }
}
