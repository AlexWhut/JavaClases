package EscuelaInfantilConFicheros;

public class Musica extends Actividad {

    private String instrumento;
    private double costeAlquilerInstrumento;

    public Musica(String nombre, Nivel nivel, int edadMinima, double costeActividad,
                  int numMaxNinnos, int mes, int anno,
                  String instrumento, double costeAlquilerInstrumento) {
        super(nombre, nivel, edadMinima, costeActividad, numMaxNinnos, mes, anno);
        if (instrumento == null || instrumento.isEmpty())
            throw new IllegalArgumentException("Instrumento inválido");
        if (costeAlquilerInstrumento < 0)
            throw new IllegalArgumentException("Coste alquiler inválido");
        this.instrumento = instrumento;
        this.costeAlquilerInstrumento = costeAlquilerInstrumento;
    }

    @Override
    public double costeActividad() {
        return getCosteActividad() + costeAlquilerInstrumento;
    }

    public String getInstrumento()             { return instrumento; }
    public double getCosteAlquilerInstrumento() { return costeAlquilerInstrumento; }
}
