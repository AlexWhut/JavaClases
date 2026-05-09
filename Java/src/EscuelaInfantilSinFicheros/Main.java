package EscuelaInfantilSinFicheros;

import java.util.ArrayList;

/*
 * ============================================================
 * TAD GestionMatriculas
 * ============================================================
 * Tipos: GestionMatriculas, Actividad, Ninno, Matricula, Nivel, int, double, String
 *
 * OPERACIÓN: plazasLibres
 * ----------------------------------------------------------
 * Signatura:
 *   plazasLibres: GestionMatriculas × String × Nivel × int × int → int
 *
 * Semántica:
 *   plazasLibres(gm, nombreActividad, nivel, mes, anno) =
 *     Sea A = { a ∈ gm.listaActividades | a.nombre = nombreActividad
 *                                        ∧ a.nivel  = nivel
 *                                        ∧ a.mes    = mes
 *                                        ∧ a.anno   = anno }
 *     Si A = ∅  → lanza ActividadNoExisteException
 *     Si no     → a.numMaxNinnos - |{ m ∈ gm.listaMatriculas | m.actividad = a }|
 *
 * Precondición:  mes ∈ [1,12] ∧ anno > 0
 * Postcondición: devuelve un entero ≥ 0 con las plazas disponibles
 *
 * OPERACIÓN: ingresosPorMesAnno
 * ----------------------------------------------------------
 * Signatura:
 *   ingresosPorMesAnno: GestionMatriculas × int × int → double
 *
 * Semántica:
 *   ingresosPorMesAnno(gm, mes, anno) =
 *     Σ m.actividad.costeActividad()
 *       para todo m ∈ gm.listaMatriculas
 *       tal que m.actividad.mes = mes ∧ m.actividad.anno = anno
 *
 * Precondición:  mes ∈ [1,12] ∧ anno > 0
 * Postcondición: devuelve la suma total de ingresos (0.0 si no hay matrículas)
 * ============================================================
 */
public class Main {

    public static void main(String[] args) {

        GestionMatriculas gm = new GestionMatriculas();
        int mes = 7, anno = 2024;

        try {
            // --- Crear 4 actividades (2 Música, 2 Pintura) ---
            Musica musVolin = new Musica("Música con violín", Nivel.BASICO,  4, 25.0, 5, mes, anno, "violín", 10.0);
            Musica musPiano = new Musica("Música con piano",  Nivel.AVANZADO, 8, 30.0, 5, mes, anno, "piano",  10.0);
            Pintura pinBasico = new Pintura("Pintura en tela", Nivel.BASICO, 3, 15.0, 5, mes, anno, "tela y pinceles", 10.0);
            Pintura pinMedio  = new Pintura("Pintura en tela", Nivel.MEDIO,  6, 20.0, 5, mes, anno, "tela y óleos",   15.0);

            gm.annadirActividad(musVolin);
            gm.annadirActividad(musPiano);
            gm.annadirActividad(pinBasico);
            gm.annadirActividad(pinMedio);

            // --- Total plazas libres antes de matricular ---
            int totalPlazas = 0;
            totalPlazas += gm.plazasLibres("Música con violín", Nivel.BASICO,   mes, anno);
            totalPlazas += gm.plazasLibres("Música con piano",  Nivel.AVANZADO, mes, anno);
            totalPlazas += gm.plazasLibres("Pintura en tela",   Nivel.BASICO,   mes, anno);
            totalPlazas += gm.plazasLibres("Pintura en tela",   Nivel.MEDIO,    mes, anno);
            System.out.println("Total plazas libres para " + mes + "/" + anno + ": " + totalPlazas);

            // --- Matricular 4 niños ---
            Ninno pedro  = new Ninno("Pedro López",  2019);
            Ninno lucia  = new Ninno("Lucía López",  2015);
            Ninno pedroG = new Ninno("Pedro García", 2018);
            Ninno nuria  = new Ninno("Nuria Pérez",  2016);

            gm.matricular(pedro,  pinBasico);
            gm.matricular(pedro,  musVolin);
            gm.matricular(lucia,  pinMedio);
            gm.matricular(lucia,  musPiano);
            gm.matricular(pedroG, musVolin);
            gm.matricular(nuria,  musPiano);

            // --- Plazas libres por actividad tras matricular ---
            System.out.println("\nPlazas libres por actividad:");
            System.out.println("  Música con violín BASICO:   " + gm.plazasLibres("Música con violín", Nivel.BASICO,   mes, anno));
            System.out.println("  Música con piano AVANZADO:  " + gm.plazasLibres("Música con piano",  Nivel.AVANZADO, mes, anno));
            System.out.println("  Pintura en tela BASICO:     " + gm.plazasLibres("Pintura en tela",   Nivel.BASICO,   mes, anno));
            System.out.println("  Pintura en tela MEDIO:      " + gm.plazasLibres("Pintura en tela",   Nivel.MEDIO,    mes, anno));

            // --- 5b: Matrículas del próximo mes ---
            System.out.println("\nMes: " + mes + ", Año: " + anno);
            for (Matricula m : gm.getListaMatriculas()) {
                if (m.getActividad().getMes() == mes && m.getActividad().getAnno() == anno) {
                    System.out.println("  " + m);
                }
            }

            // --- Actividades sin matrículas ---
            ArrayList<Actividad> sinMat = gm.actividadesSinMatriculas(mes, anno);
            if (sinMat.isEmpty()) {
                System.out.println("\nTodas las actividades tienen al menos una matrícula.");
            } else {
                System.out.println("\nActividades sin matrículas:");
                for (Actividad a : sinMat) System.out.println("  " + a);
            }

        } catch (ActividadNoExisteException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Datos inválidos: " + e.getMessage());
        }
    }
}
