package EscuelaInfantilSinFicheros;

public abstract class Actividad {

    private String nombre;
    private Nivel nivel;
    private int edadMinima;
    private double costeActividad;
    private int numMaxNinnos;
    private int mes;
    private int anno;

    public Actividad(String nombre, Nivel nivel, int edadMinima, double costeActividad,
                     int numMaxNinnos, int mes, int anno) {
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("Nombre inválido");
        if (nivel == null) throw new IllegalArgumentException("Nivel nulo");
        if (edadMinima < 0) throw new IllegalArgumentException("Edad mínima inválida");
        if (costeActividad < 0) throw new IllegalArgumentException("Coste inválido");
        if (numMaxNinnos <= 0) throw new IllegalArgumentException("Número máximo inválido");
        if (mes < 1 || mes > 12) throw new IllegalArgumentException("Mes inválido: " + mes);
        if (anno <= 0) throw new IllegalArgumentException("Año inválido");
        this.nombre = nombre;
        this.nivel = nivel;
        this.edadMinima = edadMinima;
        this.costeActividad = costeActividad;
        this.numMaxNinnos = numMaxNinnos;
        this.mes = mes;
        this.anno = anno;
    }

    public abstract double costeActividad();

    public String getNombre()      { return nombre; }
    public Nivel getNivel()        { return nivel; }
    public int getEdadMinima()     { return edadMinima; }
    public double getCosteActividad() { return costeActividad; }
    public int getNumMaxNinnos()   { return numMaxNinnos; }
    public int getMes()            { return mes; }
    public int getAnno()           { return anno; }

    @Override
    public String toString() {
        return nombre + ", nivel: " + nivel + ", coste: " + costeActividad() + "€";
    }
}
