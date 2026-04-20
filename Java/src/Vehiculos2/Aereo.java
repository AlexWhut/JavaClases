package Vehiculos2;

public class Aereo extends Vehiculo {
    protected String nombreAereo;

    public Aereo(String nombreVehiculo, String nombreAereo) {
        super(nombreVehiculo);
        this.nombreAereo = nombreAereo;
    }

    public void volar() {
        System.out.println(nombreAereo + " esta volando.");
    }
}