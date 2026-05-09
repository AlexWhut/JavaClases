package EscuelaInfantilConFicheros;

public class Pintura extends Actividad {

    private String materiales;
    private double costeMateriales;

    public Pintura(String nombre, Nivel nivel, int edadMinima, double costeActividad,
                   int numMaxNinnos, int mes, int anno,
                   String materiales, double costeMateriales) {
        super(nombre, nivel, edadMinima, costeActividad, numMaxNinnos, mes, anno);
        if (materiales == null || materiales.isEmpty())
            throw new IllegalArgumentException("Materiales inválidos");
        if (costeMateriales < 0)
            throw new IllegalArgumentException("Coste materiales inválido");
        this.materiales = materiales;
        this.costeMateriales = costeMateriales;
    }

    @Override
    public double costeActividad() {
        return getCosteActividad() + costeMateriales;
    }

    public String getMateriales()       { return materiales; }
    public double getCosteMateriales()  { return costeMateriales; }
}
