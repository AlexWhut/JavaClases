package Vehiculos2;

public class Velero extends Acuatico {
    public Velero(String nombreVehiculo, String nombreAcuatico) {
        super(nombreVehiculo, nombreAcuatico);
    }

    public void izarVelas() {
        System.out.println(nombreAcuatico + " izo las velas.");
    }
}