package Vehiculos;

public class Tren extends Vehiculo { // Tren es un tipo de Vehiculo, por eso extiende de Vehiculo

    public Tren(String nombre, int velocidad) {
        super(nombre, velocidad); // Llama al constructor de la clase padre (Vehiculo)
    }

    @Override
    public void moverse() { // Implementa el metodo abstracto moverse de la clase Vehiculo
        System.out.println(getNombre() + " se esta moviendo a " + getVelocidad() + " km/h");
    }

    
}
