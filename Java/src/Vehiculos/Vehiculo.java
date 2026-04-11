package Vehiculos;

public abstract class Vehiculo { // clase incompleta, falta el cierre de la clase
    private String nombre;  //datos privados encapsulados
    private int velocidad;  //datos privados encapsulados

    public Vehiculo(String nombre, int velocidad) {
        this.nombre = nombre;
        this.velocidad = velocidad;
    }
    
    
    // getter y setter para nombre

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public abstract void moverse(); // metodo abstracto para que cada tipo de vehiculo implemente su propia forma de moverse

}
