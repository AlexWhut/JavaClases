package GestorDietas;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Usuario de tipo Deportista.
 *
 * Sus restricciones nutricionales son:
 *  - La receta debe alcanzar un mínimo de proteínas (minProteinas)
 *  - La receta debe tener al menos 200 kcal
 *
 * Su fórmula de evaluación es:
 *   50 + 2*proteinas - 0.5*grasas - 0.3*|kcal - 500|
 *
 * La idea es que el deportista busca alta proteína, controlando grasas
 * y apuntando a unas 500 kcal por comida como objetivo.
 */
public class Deportista extends Usuario {

    private static final long serialVersionUID = 1L;

    /**
     * Mínimo de proteínas (en gramos) que debe tener una receta
     * para que el deportista pueda consumirla.
     */
    private double minProteinas;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Crea un usuario de tipo Deportista.
     *
     * @param nombre       nombre completo
     * @param id           identificador único
     * @param minProteinas mínimo de proteínas requerido (> 0)
     * @throws UsuarioException si nombre/id inválidos o minProteinas ≤ 0
     */
    public Deportista(String nombre, String id, double minProteinas)
            throws UsuarioException {
        // El constructor del padre valida nombre e id
        super(nombre, id);
        if (minProteinas <= 0) {
            throw new UsuarioException(
                "El mínimo de proteínas debe ser positivo. Recibido: " + minProteinas);
        }
        this.minProteinas = minProteinas;
    }

    // ---------------------------------------------------------------
    // IMPLEMENTACIONES DE LOS MÉTODOS ABSTRACTOS
    // ---------------------------------------------------------------

    /**
     * Una receta es apta para el deportista si:
     *  1. Sus proteínas totales >= minProteinas
     *  2. Sus calorías totales >= 200 kcal
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

        // Ambas condiciones deben cumplirse
        return perfil.getProteinas() >= minProteinas && kcal >= 200;
    }

    /**
     * Puntuación del deportista para una receta individual.
     *
     * Fórmula: 50 + 2*proteinas - 0.5*grasas - 0.3*|kcal - 500|
     *
     * Análisis de la fórmula:
     *  - Base de 50 puntos
     *  - +2 por cada gramo de proteína (premia proteínas)
     *  - -0.5 por cada gramo de grasa (penaliza grasas)
     *  - -0.3 por cada kcal de desviación respecto a 500 (objetivo calórico)
     *
     * @param receta receta a puntuar
     * @return puntuación ajustada entre 0 y 100
     * @throws RecetaException si hay error al calcular el perfil
     */
    @Override
    public int evaluarReceta(Receta receta) throws RecetaException {
        if (receta == null) throw new RecetaException("La receta no puede ser null.");

        PerfilNutricional perfil = receta.getPerfilReceta();
        double proteinas = perfil.getProteinas();
        double grasas = perfil.getGrasas();
        int kcal = receta.getKcalReceta();

        double puntuacion = 50 + 2 * proteinas - 0.5 * grasas - 0.3 * Math.abs(kcal - 500);

        // ajustarPuntuacion() viene de Usuario: clamp al rango [0, 100]
        return ajustarPuntuacion(puntuacion);
    }

    /**
     * Evalúa el menú completo de un día sumando todos los perfiles de ese día
     * y aplicando la misma fórmula que evaluarReceta sobre el total.
     *
     * @param fecha día a evaluar
     * @return puntuación 0-100
     * @throws UsuarioException si fecha null o no hay recetas ese día
     * @throws RecetaException  si hay error en el cálculo
     */
    @Override
    public int evaluarMenuDiario(LocalDate fecha) throws UsuarioException, RecetaException {
        if (fecha == null) throw new UsuarioException("La fecha no puede ser null.");

        // Recuperamos todas las recetas de ese día del historial
        ArrayList<Receta> recetasDia = buscarPorFecha(fecha);
        if (recetasDia.isEmpty()) {
            throw new UsuarioException(
                "No hay recetas registradas para la fecha: " + fecha);
        }

        // Sumamos el perfil de todas las recetas del día
        try {
            PerfilNutricional totalDia = new PerfilNutricional();
            int kcalTotales = 0;
            for (Receta r : recetasDia) {
                totalDia = totalDia.sumar(r.getPerfilReceta());
                kcalTotales += r.getKcalReceta();
            }

            // Aplicamos la misma fórmula sobre los totales del día
            double puntuacion = 50
                + 2 * totalDia.getProteinas()
                - 0.5 * totalDia.getGrasas()
                - 0.3 * Math.abs(kcalTotales - 500);

            return ajustarPuntuacion(puntuacion);
        } catch (ValorNutricionalException e) {
            throw new RecetaException("Error al calcular el menú diario: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return mínimo de proteínas requerido */
    public double getMinProteinas() { return minProteinas; }

    /**
     * @param minProteinas nuevo mínimo de proteínas (> 0)
     * @throws UsuarioException si el valor no es positivo
     */
    public void setMinProteinas(double minProteinas) throws UsuarioException {
        if (minProteinas <= 0) {
            throw new UsuarioException("El mínimo de proteínas debe ser positivo.");
        }
        this.minProteinas = minProteinas;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Muestra los datos del deportista incluyendo su mínimo de proteínas.
     */
    @Override
    public String toString() {
        return super.toString()
            + String.format(" | Tipo: Deportista | Min.Proteínas: %.1fg", minProteinas);
    }
}
