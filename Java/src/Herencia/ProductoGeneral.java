package Herencia;

public class ProductoGeneral extends Producto {
    public ProductoGeneral(String nombre, double precio) {
        super(nombre, precio);
    }

    @Override
    public boolean puedeSerCompradoPor(Usuario usuario) {
        // Productos generales: no tienen restriccion por edad.
        return true;
    }
}
