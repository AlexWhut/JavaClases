package EscuelaInfantilSinFicheros;

public class Ninno {

    private String nombre;
    private int annoNacimiento;

    public Ninno(String nombre, int annoNacimiento) {
        if (nombre == null || nombre.isEmpty())
            throw new IllegalArgumentException("Nombre inválido");
        if (annoNacimiento <= 0)
            throw new IllegalArgumentException("Año de nacimiento inválido");
        this.nombre = nombre;
        this.annoNacimiento = annoNacimiento;
    }

    public String getNombre()       { return nombre; }
    public int getAnnoNacimiento()  { return annoNacimiento; }

    @Override
    public String toString() {
        return nombre;
    }
}
