package SeguimientoConFicheros;

import java.io.*;
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

        // Serializar a fichero binario
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("miMediateca.med"))) {
            oos.writeObject(mediateca);
            System.out.println("\nMediateca serializada en miMediateca.med");
        } catch (IOException e) {
            System.err.println("Error al serializar: " + e.getMessage());
        }

        // Exportar informe de texto
        try (PrintWriter pw = new PrintWriter(new FileWriter("Informe medios.txt"))) {

            pw.println("Peliculas");
            for (Streaming item : mediateca) {
                if (item instanceof Pelicula pelicula) {
                    pw.println("  " + pelicula);
                }
            }

            pw.println();
            pw.println("Series");
            for (Streaming item : mediateca) {
                if (item instanceof Serie serie) {
                    pw.println("  " + serie);
                    for (Capitulo cap : serie.getCapitulos()) {
                        pw.println("    Temporada " + cap.getTemporada() + ", Capitulo "
                                + cap.getNumeroCapitulo() + ": " + cap.getTitulo()
                                + ". (" + cap.getAnio() + ")");
                    }
                }
            }

            System.out.println("Informe escrito en 'Informe medios.txt'");
        } catch (IOException e) {
            System.err.println("Error al escribir informe: " + e.getMessage());
        }
    }
}
