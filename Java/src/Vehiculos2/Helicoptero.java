package Vehiculos2;

public class Helicoptero extends Aereo {
    public Helicoptero(String nombreVehiculo, String nombreAereo) {
        super(nombreVehiculo, nombreAereo);
    }

    public void encenderHelices() {
        System.out.println(nombreAereo + " encendio las helices.");
    }
}