package SeguimientoSinFicheros;

import java.time.LocalDate;
import java.util.ArrayList;

public class Serie extends Streaming {

    private ArrayList<Capitulo> capitulos;

    public Serie(String titulo, String director, TipoClasificacion clasificacion) {
        super(titulo, director, clasificacion);
        this.capitulos = new ArrayList<>();
    }

    public void annadirCapitulo(int temporada, int numCapitulo, int anio, String titulo) {
        if (!capitulos.isEmpty() && anio < annoInicio())
            throw new IllegalArgumentException("El año del capítulo no puede ser anterior al inicio de la serie.");
        capitulos.add(new Capitulo(temporada, numCapitulo, anio, titulo));
    }

    public boolean annadirValoracion(int temporada, int numCapitulo, LocalDate fecha, int valoracion) {
        Capitulo c = buscarCapitulo(temporada, numCapitulo);
        if (c == null) return false;
        try {
            c.addValoracion(fecha, valoracion);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public int annoInicio() {
        return capitulos.stream().mapToInt(Capitulo::getAnio).min().orElse(Integer.MAX_VALUE);
    }

    public int numeroDeCapitulos() {
        return capitulos.size();
    }

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

    public ArrayList<Capitulo> getCapitulos() {
        return capitulos;
    }

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
