package Vehiculos;

public class Coche extends Vehiculo implements Conducible { // Coche es un tipo de Vehiculo, por eso extiende de Vehiculo
    private String marca;

    public Coche(String nombre, int velocidad, String marca) {
        super(nombre, velocidad); // Llama al constructor de la clase padre (Vehiculo)
        this.marca = marca;
    }

    @Override
    public void moverse() { // Implementa el metodo abstracto moverse de la clase Vehiculo
        System.out.println(getNombre() + " se esta moviendo a " + getVelocidad() + " km/h");
    }

    @Override
    public void acelerar(int velocidad) {
        setVelocidad(getVelocidad() + velocidad);
    }

    @Override
    public void frenar() {
        setVelocidad(0);
    }
}
