package Vehiculos2;

public class Acuatico extends Vehiculo {
    protected String nombreAcuatico;

    public Acuatico(String nombreVehiculo, String nombreAcuatico) {
        super(nombreVehiculo);
        this.nombreAcuatico = nombreAcuatico;
    }

    public void navegar() {
        System.out.println(nombreAcuatico + " esta navegando.");
    }
}