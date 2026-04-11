package Herencia;

public class Pelicula extends Producto {
    private int clasificacionEdad;

    public Pelicula(String nombre, double precio, int clasificacionEdad) {
        super(nombre, precio);
        this.clasificacionEdad = clasificacionEdad;
    }

    public int getClasificacionEdad() {
        return clasificacionEdad;
    }

    @Override
    public boolean puedeSerCompradoPor(Usuario usuario) {
        // Regla de negocio: solo puede comprar esta pelicula si cumple la edad minima.
        return usuario.getEdad() >= clasificacionEdad;
    }
}
