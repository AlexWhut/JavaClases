package GestorDietas;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Genera informes nutricionales en archivos de texto.
 *
 * Genera dos tipos de informes con el formato exacto del enunciado:
 *  1. Informe diario: recetas de un día concreto
 *  2. Informe semanal: recetas de 7 días consecutivos
 *
 * Usa PrintWriter con FileWriter para escribir texto formateado en ficheros.
 * PrintWriter tiene printf() que funciona igual que System.out.printf().
 */
public class GestorInformes {

    // Formato de fecha para mostrar en los informes (dd/MM/yyyy)
    private static final DateTimeFormatter FORMATO_FECHA =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Anchuras del separador según el tipo de informe
    private static final String SEP_DIARIO  = "----------------------------------------------------------------------";
    private static final String SEP_SEMANAL = "-------------------------------------------------------------------------------";
    private static final String SEP_DIARIO_DOBLE  = "======================================================================";
    private static final String SEP_SEMANAL_DOBLE = "===============================================================================";

    // Máximo de caracteres para el nombre de la receta en el informe
    private static final int MAX_NOMBRE_RECETA = 22;

    // ---------------------------------------------------------------
    // INFORME DIARIO
    // ---------------------------------------------------------------

    /**
     * Genera un informe diario en formato texto para un usuario y fecha dados.
     *
     * El formato sigue exactamente el ejemplo del enunciado:
     *  - Cabecera con datos del usuario
     *  - Tabla de recetas con columnas: Tipo | Receta | Prot | Carb | Grasas | Kcal
     *  - Totales del día
     *  - Evaluación nutricional
     *
     * @param usuario      usuario para el que generar el informe
     * @param fecha        fecha del informe
     * @param rutaArchivo  ruta del archivo de salida (ej: "informe_diario.txt")
     * @throws UsuarioException si usuario o fecha son null, o no hay recetas ese día
     * @throws RecetaException  si hay error al calcular perfiles nutricionales
     */
    public void generarInformeDiario(Usuario usuario, LocalDate fecha, String rutaArchivo)
            throws UsuarioException, RecetaException {

        if (usuario == null) throw new UsuarioException("El usuario no puede ser null.");
        if (fecha == null)   throw new UsuarioException("La fecha no puede ser null.");
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new UsuarioException("La ruta del archivo no puede estar vacía.");
        }

        ArrayList<Receta> recetasDia = usuario.buscarPorFecha(fecha);
        if (recetasDia.isEmpty()) {
            throw new UsuarioException(
                "No hay recetas para la fecha " + fecha.format(FORMATO_FECHA));
        }

        // Calculamos los totales del día para el pie del informe
        double totalProt = 0, totalCarb = 0, totalGrasas = 0;
        int totalKcal = 0;
        for (Receta r : recetasDia) {
            PerfilNutricional p = r.getPerfilReceta();
            totalProt   += p.getProteinas();
            totalCarb   += p.getCarbohidratos();
            totalGrasas += p.getGrasas();
            totalKcal   += r.getKcalReceta();
        }

        int puntuacion = usuario.evaluarMenuDiario(fecha);

        // PrintWriter envuelve FileWriter para escribir texto en el archivo
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {

            // --- Cabecera ---
            pw.println(SEP_DIARIO_DOBLE);
            pw.printf("%34s%n", "INFORME DE MENU DIARIO");
            pw.println(SEP_DIARIO_DOBLE);
            pw.printf("Usuario: %s%n", usuario.getNombre());
            pw.printf("ID      : %s%n", usuario.getId());
            pw.printf("Tipo    : %s%n", getTipoUsuario(usuario));
            pw.printf("Fecha   : %s%n", fecha.format(FORMATO_FECHA));

            // --- Tabla ---
            pw.println();
            pw.println(SEP_DIARIO);
            pw.printf("%-9s %-22s %6s %6s %6s %4s%n",
                "Hora/Tipo", "Receta", "Prot.", "Carb.", "Grasas", "Kcal");
            pw.println(SEP_DIARIO);

            for (Receta r : recetasDia) {
                PerfilNutricional p = r.getPerfilReceta();
                // Truncamos el nombre si es muy largo (como en el ejemplo del enunciado)
                String nombreCorto = truncar(r.getNombre(), MAX_NOMBRE_RECETA);
                pw.printf("%-9s %-22s %6.2f %6.2f %6.2f %4d%n",
                    r.getTipoComida(),
                    nombreCorto,
                    p.getProteinas(),
                    p.getCarbohidratos(),
                    p.getGrasas(),
                    r.getKcalReceta());
            }

            // --- Totales ---
            pw.println(SEP_DIARIO);
            pw.printf("%-32s %6.2f %6.2f %6.2f %4d%n",
                "TOTAL DIA", totalProt, totalCarb, totalGrasas, totalKcal);
            pw.printf("Evaluación del menú diario: %d/100%n", puntuacion);
            pw.println();

            // Verificamos si al menos una receta del día es apta para el usuario
            boolean algunaApta = false;
            for (Receta r : recetasDia) {
                try {
                    if (usuario.puedeConsumir(r)) { algunaApta = true; break; }
                } catch (RecetaException e) { /* ignoramos recetas sin ingredientes */ }
            }
            pw.printf("Apto para el perfil del usuario: %s%n", algunaApta ? "SI" : "NO");
            pw.println(SEP_DIARIO_DOBLE);

        } catch (IOException e) {
            throw new UsuarioException("Error al escribir el informe: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // INFORME SEMANAL
    // ---------------------------------------------------------------

    /**
     * Genera un informe semanal para los 7 días a partir de la fecha de inicio.
     *
     * @param usuario       usuario para el que generar el informe
     * @param inicioSemana  primer día de la semana
     * @param rutaArchivo   ruta del archivo de salida
     * @throws UsuarioException si parámetros inválidos o no hay recetas en la semana
     * @throws RecetaException  si hay error al calcular perfiles
     */
    public void generarInformeSemanal(Usuario usuario, LocalDate inicioSemana,
                                      String rutaArchivo)
            throws UsuarioException, RecetaException {

        if (usuario == null) throw new UsuarioException("El usuario no puede ser null.");
        if (inicioSemana == null) throw new UsuarioException("La fecha de inicio no puede ser null.");
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new UsuarioException("La ruta del archivo no puede estar vacía.");
        }

        // El intervalo de la semana: 7 días desde el inicio
        LocalDate finSemana = inicioSemana.plusDays(6);
        ArrayList<Receta> recetasSemana = usuario.buscarEntreFechas(inicioSemana, finSemana);

        if (recetasSemana.isEmpty()) {
            throw new UsuarioException("No hay recetas registradas en esa semana.");
        }

        // Calculamos totales y puntuación media de los días con recetas
        double totalProt = 0, totalCarb = 0, totalGrasas = 0;
        int totalKcal = 0;
        for (Receta r : recetasSemana) {
            PerfilNutricional p = r.getPerfilReceta();
            totalProt   += p.getProteinas();
            totalCarb   += p.getCarbohidratos();
            totalGrasas += p.getGrasas();
            totalKcal   += r.getKcalReceta();
        }

        // Puntuación media: promedio de evaluarMenuDiario para los días con recetas
        int sumaPuntuaciones = 0;
        int diasConRecetas = 0;
        for (int i = 0; i < 7; i++) {
            LocalDate dia = inicioSemana.plusDays(i);
            try {
                ArrayList<Receta> recetasDia = usuario.buscarPorFecha(dia);
                if (!recetasDia.isEmpty()) {
                    sumaPuntuaciones += usuario.evaluarMenuDiario(dia);
                    diasConRecetas++;
                }
            } catch (UsuarioException e) { /* día sin recetas, ignoramos */ }
        }
        int puntuacionMedia = diasConRecetas > 0 ? sumaPuntuaciones / diasConRecetas : 0;

        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {

            // --- Cabecera ---
            pw.println(SEP_SEMANAL_DOBLE);
            pw.printf("%38s%n", "INFORME DE MENU SEMANAL");
            pw.println(SEP_SEMANAL_DOBLE);
            pw.printf("%-14s: %s%n", "Usuario", usuario.getNombre());
            pw.printf("%-14s: %s%n", "ID", usuario.getId());
            pw.printf("%-14s: %s%n", "Tipo", getTipoUsuario(usuario));
            pw.printf("%-14s: %s%n", "Semana inicio", inicioSemana.format(FORMATO_FECHA));
            pw.printf("%-14s: %s%n", "Semana fin", finSemana.format(FORMATO_FECHA));

            // --- Tabla ---
            pw.println();
            pw.println(SEP_SEMANAL);
            pw.printf("%-10s %-9s %-20s %5s %5s %5s %4s%n",
                "Fecha", "Tipo", "Receta", "Prot.", "Carb.", "Grasa", "Kcal");
            pw.println(SEP_SEMANAL);

            for (Receta r : recetasSemana) {
                PerfilNutricional p = r.getPerfilReceta();
                String nombreCorto = truncar(r.getNombre(), 20);
                pw.printf("%-10s %-9s %-20s %5.2f %5.2f %5.2f %4d%n",
                    r.getFecha().format(FORMATO_FECHA),
                    r.getTipoComida(),
                    nombreCorto,
                    p.getProteinas(),
                    p.getCarbohidratos(),
                    p.getGrasas(),
                    r.getKcalReceta());
            }

            // --- Totales ---
            pw.println(SEP_SEMANAL);
            pw.printf("%-40s %5.2f %5.2f %5.2f %4d%n",
                "TOTAL SEMANA", totalProt, totalCarb, totalGrasas, totalKcal);
            pw.printf("Evaluación media semanal: %d/100%n", puntuacionMedia);
            pw.println(SEP_SEMANAL_DOBLE);

        } catch (IOException e) {
            throw new UsuarioException("Error al escribir el informe: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // MÉTODOS AUXILIARES PRIVADOS
    // ---------------------------------------------------------------

    /**
     * Devuelve el nombre del tipo de usuario como String legible.
     * Usa instanceof para detectar el subtipo concreto.
     *
     * @param usuario usuario a identificar
     * @return nombre del tipo ("Deportista", "UsuarioKeto", "Paciente")
     */
    private String getTipoUsuario(Usuario usuario) {
        if (usuario instanceof Deportista)  return "Deportista";
        if (usuario instanceof UsuarioKeto) return "UsuarioKeto";
        if (usuario instanceof Paciente)    return "Paciente";
        return "Desconocido";
    }

    /**
     * Trunca un String a la longitud máxima, añadiendo "..." si se ha cortado.
     * Útil para que los nombres largos no rompan el formato de la tabla.
     *
     * @param texto  texto original
     * @param maxLen longitud máxima
     * @return texto truncado con "..." o el original si cabe
     */
    private String truncar(String texto, int maxLen) {
        if (texto == null) return "";
        if (texto.length() <= maxLen) return texto;
        // Dejamos espacio para los tres puntos al final
        return texto.substring(0, maxLen - 3) + "...";
    }
}
