package GestorDietas;

/**
 * Ingrediente procesado industrialmente: pasta, conservas, embutidos, etc.
 *
 * A diferencia de AlimentoBasico, un alimento procesado puede tener:
 *  - una descripción del proceso de preparación ("hervido", "horneado"...)
 *  - un indicador de si necesita cocinado antes de consumirse
 *
 * Esta distinción permite, por ejemplo, listar todos los ingredientes
 * que aún necesitan ser cocinados (funcionalidad opcional del enunciado).
 */
public class AlimentoProcesado extends Ingrediente {

    private static final long serialVersionUID = 1L;

    /** Descripción breve del proceso (ej: "hervido", "al vapor", "frito") */
    private String preparacion;

    /** true si el alimento necesita cocinarse antes de ser consumido */
    private boolean necesitaCocinado;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Crea un alimento procesado con su información de preparación.
     *
     * @param nombre           nombre del alimento (ej: "Pasta integral")
     * @param perfilNutricional valores nutricionales por 100g
     * @param cantidad         gramos usados en la receta
     * @param preparacion      descripción del proceso de preparación (no vacía)
     * @param necesitaCocinado true si hay que cocinarlo antes de consumir
     * @throws RecetaException si nombre vacío, perfil null, cantidad ≤ 0 o preparacion vacía
     */
    public AlimentoProcesado(String nombre, PerfilNutricional perfilNutricional,
                             double cantidad, String preparacion, boolean necesitaCocinado)
            throws RecetaException {
        // El constructor padre valida nombre, perfil y cantidad
        super(nombre, perfilNutricional, cantidad);

        if (preparacion == null || preparacion.isBlank()) {
            throw new RecetaException("La preparación no puede estar vacía.");
        }
        this.preparacion = preparacion;
        this.necesitaCocinado = necesitaCocinado;
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return descripción de la preparación */
    public String getPreparacion() { return preparacion; }

    /** @return true si necesita cocinado */
    public boolean isNecesitaCocinado() { return necesitaCocinado; }

    /**
     * @param preparacion nueva descripción (no vacía)
     * @throws RecetaException si la descripción es vacía o null
     */
    public void setPreparacion(String preparacion) throws RecetaException {
        if (preparacion == null || preparacion.isBlank()) {
            throw new RecetaException("La preparación no puede estar vacía.");
        }
        this.preparacion = preparacion;
    }

    /** @param necesitaCocinado nuevo valor del indicador */
    public void setNecesitaCocinado(boolean necesitaCocinado) {
        this.necesitaCocinado = necesitaCocinado;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Muestra la información del ingrediente más la preparación y si necesita cocinado.
     */
    @Override
    public String toString() {
        return super.toString()
            + String.format(" | Preparación: %s | Necesita cocinado: %s",
                preparacion, necesitaCocinado ? "Sí" : "No");
    }
}
