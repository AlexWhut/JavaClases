package SeguimientoValoraciones;

import java.io.Serializable;

/**
 * TAD Streaming
 *
 * Representa un contenido de streaming con:
 * - título
 * - director
 * - clasificación
 *
 * Operaciones:
 * - obtener título
 * - modificar título
 * - obtener director
 * - modificar director
 * - obtener clasificación
 * - modificar clasificación
 */

public abstract class Streaming implements Serializable {

    private String titulo;
    private String director;
    private TipoClasificacion clasificacion;

 
    public Streaming(String titulo, String director, TipoClasificacion clasificacion) {

        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("El título no puede estar vacío.");
        if (director == null || director.isBlank())
            throw new IllegalArgumentException("El director no puede estar vacío.");
        this.titulo = titulo;
        this.director = director;
        this.clasificacion = clasificacion;
    }

    public String getTitulo() { return titulo; }
    public String getDirector() { return director; }
    public TipoClasificacion getClasificacion() { return clasificacion; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDirector(String director) { this.director = director; }
    public void setClasificacion(TipoClasificacion clasificacion) { this.clasificacion = clasificacion; }
}
