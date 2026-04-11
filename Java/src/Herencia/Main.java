package Herencia;

public class Main {
	public static void main(String[] args) {
		// 1) Creamos la cesta de venta donde se registran usuarios y productos.
		CestaVenta cesta = new CestaVenta();

		// 2) Creamos usuarios con datos completos.
		Usuario usuario1 = new Usuario("Ana", "Lopez", 15, "ana@email.com", "3001112233");
		Usuario usuario2 = new Usuario("Carlos", "Martinez", 16, "carlos@email.com", "3004445566");
		Usuario usuario3 = new Usuario("Laura", "Gomez", 22, "laura@email.com", "3007778899");

		// 3) Registramos los usuarios para poder calcular estadísticas.
		cesta.registrarUsuario(usuario1);
		cesta.registrarUsuario(usuario2);
		cesta.registrarUsuario(usuario3);

		// 4) Agregamos productos de distintos tipos (herencia + polimorfismo).
		// ProductoGeneral no restringe por edad.
		cesta.agregarProducto(new ProductoGeneral("Palomitas", 5.50));
		cesta.agregarProducto(new ProductoGeneral("Gaseosa", 3.25));

		// Pelicula usa regla de validación por edad.
		cesta.agregarProducto(new Pelicula("Aventura Familiar", 12.00, 7));
		cesta.agregarProducto(new Pelicula("Terror Extremo +16", 15.00, 16));

		// 5) Mostramos usuarios y productos disponibles para cada uno.
		cesta.mostrarUsuariosRegistrados();
		cesta.mostrarProductosDisponiblesPara(usuario1);
		cesta.mostrarProductosDisponiblesPara(usuario2);
		cesta.mostrarProductosDisponiblesPara(usuario3);

		// 6) Calculamos y mostramos el promedio de edad de usuarios registrados.
		double promedio = cesta.calcularPromedioEdades();
		System.out.println("\nPromedio de edad de usuarios: " + promedio);
	}
}
