package Vehiculos2;

public class Vehiculo {
    protected String nombreVehiculo;

    public Vehiculo(String nombreVehiculo) {
        this.nombreVehiculo = nombreVehiculo;
    }

    public void transportar() {
        System.out.println(nombreVehiculo + " se usa para transportar.");
    }
}