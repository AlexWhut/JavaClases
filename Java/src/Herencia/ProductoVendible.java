package Herencia;

public interface ProductoVendible {
    String getNombre();
    double getPrecio();
    boolean puedeSerCompradoPor(Usuario usuario);
}
