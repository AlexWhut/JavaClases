package SeguimientoSinFicheros;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Streaming> mediateca = new ArrayList<>();

        Pelicula p1 = new Pelicula("Fast & Furious X", "Louis Leterrier", TipoClasificacion.MAYORES, 2023);
        Pelicula p2 = new Pelicula("West Side Story", "Steven Spielberg", TipoClasificacion.JUVENIL, 2021);

        Serie s1 = new Serie("Juego de Tronos", "David Benioff y otros", TipoClasificacion.MAYORES);
        s1.annadirCapitulo(1, 1, 2011, "Se acerca el invierno");
        s1.annadirCapitulo(1, 2, 2011, "El camino real");
        s1.annadirCapitulo(2, 1, 2012, "El norte no olvida");
        s1.annadirCapitulo(2, 2, 2012, "Las tierras de los Lannister");

        s1.annadirValoracion(1, 1, LocalDate.of(2011, 6, 1), 9);
        s1.annadirValoracion(1, 1, LocalDate.of(2011, 7, 1), 8);
        s1.annadirValoracion(1, 2, LocalDate.of(2011, 6, 15), 7);
        s1.annadirValoracion(2, 1, LocalDate.of(2012, 4, 10), 6);

        mediateca.add(p1);
        mediateca.add(p2);
        mediateca.add(s1);

        // Capítulos con valoración media >= 7
        System.out.println("=== Capítulos con valoración media >= 7 ===");
        for (Streaming item : mediateca) {
            if (item instanceof Serie serie) {
                for (Capitulo cap : serie.getCapitulos()) {
                    if (!cap.getValoraciones().isEmpty() && cap.valoracionMedia() >= 7) {
                        System.out.println("  [" + serie.getTitulo() + "] T" + cap.getTemporada()
                                + " E" + cap.getNumeroCapitulo() + ": " + cap.getTitulo()
                                + " (media: " + cap.valoracionMedia() + ")");
                    }
                }
            }
        }

        // Informe por consola
        System.out.println("\nPeliculas");
        for (Streaming item : mediateca) {
            if (item instanceof Pelicula pelicula) {
                System.out.println("  " + pelicula);
            }
        }

        System.out.println("\nSeries");
        for (Streaming item : mediateca) {
            if (item instanceof Serie serie) {
                System.out.println("  " + serie);
                for (Capitulo cap : serie.getCapitulos()) {
                    System.out.println("    Temporada " + cap.getTemporada() + ", Capitulo "
                            + cap.getNumeroCapitulo() + ": " + cap.getTitulo()
                            + ". (" + cap.getAnio() + ")");
                }
            }
        }
    }
}
