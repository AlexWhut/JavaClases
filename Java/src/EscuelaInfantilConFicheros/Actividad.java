package EscuelaInfantilConFicheros;

import java.io.Serializable;

/**
 * Clase abstracta que representa una actividad ofertada en la escuela infantil.
 * <p>
 * Cada actividad se identifica por su nombre y nivel, y se oferta en un mes y año concretos.
 * Las subclases implementan {@link #costeActividad()} para devolver el coste total
 * (coste base + coste específico del tipo de actividad).
 * </p>
 *
 * @author AlexWhut
 * @version 1.0
 */
public abstract class Actividad implements Serializable {

    /** Nombre descriptivo de la actividad (p.ej. "Música con violín"). */
    private String nombre;

    /** Nivel de la actividad: BASICO, MEDIO o AVANZADO. */
    private Nivel nivel;

    /** Edad mínima requerida para poder matricularse. */
    private int edadMinima;

    /** Coste base de la actividad, sin incluir el coste específico de la subclase. */
    private double costeActividad;

    /** Número máximo de niños que pueden matricularse en esta actividad. */
    private int numMaxNinnos;

    /** Mes en que se oferta la actividad (1-12). */
    private int mes;

    /** Año en que se oferta la actividad. */
    private int anno;

    /**
     * Construye una actividad con los parámetros indicados.
     *
     * @param nombre          nombre de la actividad (no puede ser nulo ni vacío)
     * @param nivel           nivel de la actividad (no puede ser nulo)
     * @param edadMinima      edad mínima requerida (debe ser &gt;= 0)
     * @param costeActividad  coste base de la actividad (debe ser &gt;= 0)
     * @param numMaxNinnos    número máximo de niños (debe ser &gt; 0)
     * @param mes             mes de oferta (1-12)
     * @param anno            año de oferta (debe ser &gt; 0)
     * @throws IllegalArgumentException si algún parámetro no cumple las restricciones
     */
    public Actividad(String nombre, Nivel nivel, int edadMinima, double costeActividad,
                     int numMaxNinnos, int mes, int anno) {
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("Nombre inválido");
        if (nivel == null) throw new IllegalArgumentException("Nivel nulo");
        if (edadMinima < 0) throw new IllegalArgumentException("Edad mínima inválida");
        if (costeActividad < 0) throw new IllegalArgumentException("Coste inválido");
        if (numMaxNinnos <= 0) throw new IllegalArgumentException("Número máximo inválido");
        if (mes < 1 || mes > 12) throw new IllegalArgumentException("Mes inválido: " + mes);
        if (anno <= 0) throw new IllegalArgumentException("Año inválido");
        this.nombre = nombre;
        this.nivel = nivel;
        this.edadMinima = edadMinima;
        this.costeActividad = costeActividad;
        this.numMaxNinnos = numMaxNinnos;
        this.mes = mes;
        this.anno = anno;
    }

    /**
     * Devuelve el coste total de la actividad para el niño.
     * <p>
     * Incluye el coste base más el coste específico de la subclase
     * (alquiler de instrumento para {@link Musica}, materiales para {@link Pintura}).
     * </p>
     *
     * @return coste total en euros
     */
    public abstract double costeActividad();

    /**
     * Devuelve el nombre de la actividad.
     * @return nombre de la actividad
     */
    public String getNombre()         { return nombre; }

    /**
     * Devuelve el nivel de la actividad.
     * @return nivel (BASICO, MEDIO o AVANZADO)
     */
    public Nivel getNivel()           { return nivel; }

    /**
     * Devuelve la edad mínima requerida.
     * @return edad mínima en años
     */
    public int getEdadMinima()        { return edadMinima; }

    /**
     * Devuelve el coste base de la actividad.
     * @return coste base en euros
     */
    public double getCosteActividad() { return costeActividad; }

    /**
     * Devuelve el número máximo de niños matriculables.
     * @return número máximo de niños
     */
    public int getNumMaxNinnos()      { return numMaxNinnos; }

    /**
     * Devuelve el mes en que se oferta la actividad.
     * @return mes (1-12)
     */
    public int getMes()               { return mes; }

    /**
     * Devuelve el año en que se oferta la actividad.
     * @return año de oferta
     */
    public int getAnno()              { return anno; }

    /**
     * Representación textual de la actividad.
     * @return cadena con nombre, nivel y coste total
     */
    @Override
    public String toString() {
        return nombre + ", nivel: " + nivel + ", coste: " + costeActividad() + "€";
    }
}
