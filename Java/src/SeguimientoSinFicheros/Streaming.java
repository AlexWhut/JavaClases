package SeguimientoSinFicheros;

public abstract class Streaming {

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
