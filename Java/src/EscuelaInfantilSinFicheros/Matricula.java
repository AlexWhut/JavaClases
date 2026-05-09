package EscuelaInfantilSinFicheros;

public class Matricula {

    private Ninno ninno;
    private Actividad actividad;

    public Matricula(Ninno ninno, Actividad actividad) {
        if (ninno == null) throw new IllegalArgumentException("Niño nulo");
        if (actividad == null) throw new IllegalArgumentException("Actividad nula");
        this.ninno = ninno;
        this.actividad = actividad;
    }

    public Ninno getNinno()         { return ninno; }
    public Actividad getActividad() { return actividad; }

    @Override
    public String toString() {
        int edad = actividad.getAnno() - ninno.getAnnoNacimiento();
        return ninno.getNombre() + " (" + edad + " años) - "
                + actividad.getNombre() + ", nivel: " + actividad.getNivel()
                + ", coste: " + actividad.costeActividad() + "€";
    }
}
