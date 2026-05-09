package SeguimientoValoraciones;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Capitulo implements Serializable {

    private int temporada;
    private int numeroCapitulo;
    private int anio;
    private String titulo;
    private ArrayList<Valoracion> valoraciones;

    public Capitulo(int temporada, int numeroCapitulo, int anio, String titulo) {
        if (temporada <= 0 || numeroCapitulo <= 0)
            throw new IllegalArgumentException("Temporada y número de capítulo deben ser positivos.");
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("El título no puede estar vacío.");
        this.temporada = temporada;
        this.numeroCapitulo = numeroCapitulo;
        this.anio = anio;
        this.titulo = titulo;
        this.valoraciones = new ArrayList<>();
    }

    public void addValoracion(LocalDate fecha, int valor) {
        if (fecha.getYear() < anio)
            throw new IllegalArgumentException("La fecha de valoración no puede ser anterior al año del capítulo.");
        valoraciones.add(new Valoracion(fecha, valor));
    }

    public double valoracionMedia() {
        if (valoraciones.isEmpty()) return 0;
        return valoraciones.stream().mapToInt(Valoracion::getValoracion).average().orElse(0);
    }

    public double valoracionMax() {
        if (valoraciones.isEmpty()) return 0;
        return valoraciones.stream().mapToInt(Valoracion::getValoracion).max().orElse(0);
    }

    public int getTemporada() { return temporada; }
    public int getNumeroCapitulo() { return numeroCapitulo; }
    public int getAnio() { return anio; }
    public String getTitulo() { return titulo; }
    public ArrayList<Valoracion> getValoraciones() { return valoraciones; }
}
