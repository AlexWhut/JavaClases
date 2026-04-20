package Vehiculos2;

public class Avion extends Aereo {
    public Avion(String nombreVehiculo, String nombreAereo) {
        super(nombreVehiculo, nombreAereo);
    }

    public void bajarTrenDeAterrizaje() {
        System.out.println(nombreAereo + " bajo el tren de aterrizaje.");
    }
}