package Vehiculos2;

public class Barco extends Acuatico {
    public Barco(String nombreVehiculo, String nombreAcuatico) {
        super(nombreVehiculo, nombreAcuatico);
    }

    public void prenderMotor() {
        System.out.println(nombreAcuatico + " encendio el motor.");
    }
}