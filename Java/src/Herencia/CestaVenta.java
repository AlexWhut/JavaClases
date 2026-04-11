package Herencia;

import java.util.ArrayList;
import java.util.List;

public class CestaVenta {
    private List<ProductoVendible> productos;
    private List<Usuario> usuarios;

    public CestaVenta() {
        this.productos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void agregarProducto(ProductoVendible producto) {
        productos.add(producto);
    }

    public void mostrarProductosDisponiblesPara(Usuario usuario) {
        System.out.println("\nProductos disponibles para " + usuario.getNombreCompleto() + " (" + usuario.getEdad() + " años):");

        for (ProductoVendible producto : productos) {
            // Polimorfismo: cada producto aplica su propia regla de compra.
            if (producto.puedeSerCompradoPor(usuario)) {
                System.out.println("- " + producto.getNombre() + " | Precio: $" + producto.getPrecio());
            } else {
                System.out.println("- " + producto.getNombre() + " | BLOQUEADO por restricción de edad");
            }
        }
    }

    public double calcularPromedioEdades() {
        if (usuarios.isEmpty()) {
            return 0;
        }

        int sumaEdades = 0;
        for (Usuario usuario : usuarios) {
            sumaEdades += usuario.getEdad();
        }

        return (double) sumaEdades / usuarios.size();
    }

    public void mostrarUsuariosRegistrados() {
        System.out.println("\nUsuarios registrados:");
        for (Usuario usuario : usuarios) {
            System.out.println("- " + usuario.getNombreCompleto() + " | Edad: " + usuario.getEdad()
                    + " | Correo: " + usuario.getCorreo() + " | Teléfono: " + usuario.getTelefono());
        }
    }
}
