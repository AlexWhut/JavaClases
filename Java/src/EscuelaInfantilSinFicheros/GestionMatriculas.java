package EscuelaInfantilSinFicheros;

import java.util.ArrayList;

public class GestionMatriculas {

    private ArrayList<Matricula> listaMatriculas;
    private ArrayList<Actividad> listaActividades;

    public GestionMatriculas() {
        listaMatriculas = new ArrayList<>();
        listaActividades = new ArrayList<>();
    }

    public boolean annadirActividad(Actividad actividad) {
        for (Actividad a : listaActividades) {
            if (a.getNombre().equals(actividad.getNombre())
                    && a.getNivel() == actividad.getNivel()
                    && a.getMes() == actividad.getMes()
                    && a.getAnno() == actividad.getAnno()) {
                return false;
            }
        }
        listaActividades.add(actividad);
        return true;
    }

    public boolean matricular(Ninno ninno, Actividad actividad) {
        if (!listaActividades.contains(actividad)) return false;

        if (contarMatriculas(actividad) >= actividad.getNumMaxNinnos()) return false;

        int edad = actividad.getAnno() - ninno.getAnnoNacimiento();
        if (edad < actividad.getEdadMinima()) return false;

        for (Matricula m : listaMatriculas) {
            if (m.getNinno().getNombre().equals(ninno.getNombre())
                    && m.getActividad().getNombre().equals(actividad.getNombre())
                    && m.getActividad().getNivel() != actividad.getNivel()) {
                return false;
            }
        }

        listaMatriculas.add(new Matricula(ninno, actividad));
        return true;
    }

    public double ingresosPorMesAnno(int mes, int anno) {
        double total = 0;
        for (Matricula m : listaMatriculas) {
            if (m.getActividad().getMes() == mes && m.getActividad().getAnno() == anno) {
                total += m.getActividad().costeActividad();
            }
        }
        return total;
    }

    public int plazasLibres(String nombreActividad, Nivel nivel, int mes, int anno)
            throws ActividadNoExisteException {
        for (Actividad a : listaActividades) {
            if (a.getNombre().equals(nombreActividad)
                    && a.getNivel() == nivel
                    && a.getMes() == mes
                    && a.getAnno() == anno) {
                return a.getNumMaxNinnos() - contarMatriculas(a);
            }
        }
        throw new ActividadNoExisteException(
                "Actividad no encontrada: " + nombreActividad + " - " + nivel);
    }

    public ArrayList<Actividad> actividadesSinMatriculas(int mes, int anno) {
        ArrayList<Actividad> resultado = new ArrayList<>();
        for (Actividad a : listaActividades) {
            if (a.getMes() == mes && a.getAnno() == anno && contarMatriculas(a) == 0) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public ArrayList<Matricula> getListaMatriculas() { return listaMatriculas; }
    public ArrayList<Actividad> getListaActividades() { return listaActividades; }

    private int contarMatriculas(Actividad actividad) {
        int count = 0;
        for (Matricula m : listaMatriculas) {
            if (m.getActividad() == actividad) count++;
        }
        return count;
    }
}
