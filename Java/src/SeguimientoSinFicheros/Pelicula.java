package SeguimientoSinFicheros;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pelicula extends Streaming {

    private int anio;
    private ArrayList<Valoracion> valoraciones;

    public Pelicula(String titulo, String director, TipoClasificacion clasificacion, int anio) {
        super(titulo, director, clasificacion);
        this.anio = anio;
        this.valoraciones = new ArrayList<>();
    }

    public void annadirValoracion(LocalDate fecha, int valoracion) {
        valoraciones.add(new Valoracion(fecha, valoracion));
    }

    public int getAnio() { return anio; }
    public ArrayList<Valoracion> getValoraciones() { return valoraciones; }

    @Override
    public String toString() {
        String val = valoraciones.isEmpty() ? "sin valorar" : String.valueOf(valoraciones.get(valoraciones.size() - 1).getValoracion());
        return getTitulo() + ", dirigida por " + getDirector() + " (" + anio + ") - " + val;
    }
}
