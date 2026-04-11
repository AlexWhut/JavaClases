package Vehiculos;

public class Moto extends Vehiculo { // Moto es un tipo de Vehiculo, por eso extiende de Vehiculo

    public Moto(String nombre, int velocidad) {
        super(nombre, velocidad); // Llama al constructor de la clase padre (Vehiculo)
    }

    @Override
    public void moverse() { // Implementa el metodo abstracto moverse de la clase Vehiculo
        System.out.println(getNombre() + " se esta moviendo a " + getVelocidad() + " km/h");
    }
    
}