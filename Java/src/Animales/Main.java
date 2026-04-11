package Animales;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		// 1) ABSTRACCION + HERENCIA
		// Creamos objetos de clases hijas, no de Animal, porque Animal es abstracta.
		Perro perro = new Perro("Toby", 3, "Labrador");
		Gato gato = new Gato("Mishi", 2, true);
		Pajaro pajaro = new Pajaro("Piolin", 1, 0.35);

		// 2) ENCAPSULACION
		// Usamos setters para modificar datos de forma controlada.
		perro.setEdad(-5); // Se corrige a 0 por validacion.
		System.out.println("Edad validada de " + perro.getNombre() + ": " + perro.getEdad());

		// 3) POLIMORFISMO
		// Una lista de tipo Animal puede guardar distintos tipos concretos.
		List<Animal> animales = new ArrayList<>();
		animales.add(perro);
		animales.add(gato);
		animales.add(pajaro);

		System.out.println("\n--- POLIMORFISMO EN ACCION ---");
		for (Animal animal : animales) {
			// Mismo metodo, comportamiento distinto segun el objeto real.
			animal.hacerSonido();
			animal.dormir();
			System.out.println();
		}

		// 4) DETALLE DE HERENCIA
		// Perro hereda nombre/edad y agrega su propio atributo: raza.
		System.out.println("Raza de " + perro.getNombre() + ": " + perro.getRaza());
	}
}
