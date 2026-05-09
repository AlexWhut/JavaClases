package SeguimientoConFicheros;

import java.io.Serializable;
import java.time.LocalDate;

public class Valoracion implements Serializable {

    private LocalDate fecha;
    private int valoracion;

    public Valoracion(LocalDate fecha, int valoracion) {
        if (valoracion < 0 || valoracion > 10)
            throw new IllegalArgumentException("La valoración debe estar entre 0 y 10.");
        this.fecha = fecha;
        this.valoracion = valoracion;
    }

    public LocalDate getFecha() { return fecha; }
    public int getValoracion() { return valoracion; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public void setValoracion(int valoracion) {
        if (valoracion < 0 || valoracion > 10)
            throw new IllegalArgumentException("La valoración debe estar entre 0 y 10.");
        this.valoracion = valoracion;
    }
}
