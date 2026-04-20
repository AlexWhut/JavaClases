package Vehiculos2;

public class Main {
    public static void main(String[] args) {
        Barco barco = new Barco("Vehiculo marino", "Barco pesquero");
        Velero velero = new Velero("Vehiculo a vela", "Velero escolar");
        Avion avion = new Avion("Vehiculo de pasajeros", "Airbus A320");
        Helicoptero helicoptero = new Helicoptero("Vehiculo de rescate", "Helicoptero sanitario");

        barco.transportar();
        barco.navegar();
        barco.prenderMotor();

        velero.transportar();
        velero.navegar();
        velero.izarVelas();

        avion.transportar();
        avion.volar();
        avion.bajarTrenDeAterrizaje();

        helicoptero.transportar();
        helicoptero.volar();
        helicoptero.encenderHelices();
    }
}