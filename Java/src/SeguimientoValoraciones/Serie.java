package SeguimientoValoraciones;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa una serie de streaming compuesta por capítulos organizados en
 * temporadas.
 * Extiende {@link Streaming} e implementa {@link java.io.Serializable}.
 */
public class Serie extends Streaming {

    private ArrayList<Capitulo> capitulos;

    /**
     * Crea una nueva Serie.
     *
     * @param titulo        título de la serie
     * @param director      director o creadores de la serie
     * @param clasificacion clasificación por edades
     */
    public Serie(String titulo, String director, TipoClasificacion clasificacion) {
        super(titulo, director, clasificacion);
        this.capitulos = new ArrayList<>();
    }

    /**
     * Añade un capítulo a la serie.
     * Solo se permite si el año del capítulo es igual o posterior al año de inicio
     * de la serie.
     *
     * @param temporada   número de temporada (positivo)
     * @param numCapitulo número de capítulo dentro de la temporada (positivo)
     * @param anio        año de realización del capítulo
     * @param titulo      título del capítulo
     */
    public void annadirCapitulo(int temporada, int numCapitulo, int anio, String titulo) {
        if (!capitulos.isEmpty() && anio < annoInicio())
            throw new IllegalArgumentException("El año del capítulo no puede ser anterior al inicio de la serie.");
        capitulos.add(new Capitulo(temporada, numCapitulo, anio, titulo));
    }

    /**
     * Añade una valoración a un capítulo concreto de una temporada.
     * La fecha de valoración no puede ser anterior al año de realización del
     * capítulo.
     *
     * @param temporada   número de temporada
     * @param numCapitulo número de capítulo
     * @param fecha       fecha en que se realiza la valoración
     * @param valoracion  valor entero entre 0 y 10
     * @return {@code true} si la valoración se añadió correctamente, {@code false}
     *         en caso contrario
     */
    public boolean annadirValoracion(int temporada, int numCapitulo, LocalDate fecha, int valoracion) {
        Capitulo c = buscarCapitulo(temporada, numCapitulo);
        if (c == null)
            return false;
        try {
            c.addValoracion(fecha, valoracion);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Devuelve el año de inicio de la serie (año del capítulo más antiguo).
     *
     * @return año de inicio, o {@code Integer.MAX_VALUE} si no hay capítulos
     */
    public int annoInicio() {
        return capitulos.stream().mapToInt(Capitulo::getAnio).min().orElse(Integer.MAX_VALUE);
    }

    /**
     * Devuelve el número total de capítulos de la serie.
     *
     * @return número de capítulos
     */
    public int numeroDeCapitulos() {
        return capitulos.size();
    }

    /**
     * Calcula la valoración media de todos los capítulos de una temporada.
     * Solo tiene en cuenta capítulos que tengan al menos una valoración.
     *
     * @param temporada número de temporada
     * @return valoración media de la temporada
     * @throws IllegalArgumentException si la temporada no existe o no tiene
     *                                  valoraciones
     */
    public double valoracionMediaTemporada(int temporada) {
        double suma = 0;
        int total = 0;
        for (Capitulo c : capitulos) {
            if (c.getTemporada() == temporada && !c.getValoraciones().isEmpty()) {
                for (Valoracion v : c.getValoraciones()) {
                    suma += v.getValoracion();
                    total++;
                }
            }
        }
        if (total == 0)
            throw new IllegalArgumentException("La temporada no existe o no tiene valoraciones.");
        return suma / total;
    }

    /**
     * Devuelve la lista de capítulos de la serie.
     *
     * @return ArrayList de {@link Capitulo}
     */
    public ArrayList<Capitulo> getCapitulos() {
        return capitulos;
    }

    // --- auxiliar ---

    private Capitulo buscarCapitulo(int temporada, int numCapitulo) {
        for (Capitulo c : capitulos) {
            if (c.getTemporada() == temporada && c.getNumeroCapitulo() == numCapitulo)
                return c;
        }
        return null;
    }

    @Override
    public String toString() {
        return getTitulo() + ". Dirigida por " + getDirector() + ".";
    }
}
