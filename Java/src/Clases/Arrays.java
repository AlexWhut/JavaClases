package Clases;

import java.util.ArrayList;

public class Arrays {

	// Array de enteros
	public static void ejemploArrayInt() {
		int[] numeros = {1, 2, 3, 4, 5}; // array fijo de tamaño 5
		System.out.println("Array de enteros:");
		for (int i = 0; i < numeros.length; i++) {
			System.out.println(numeros[i]);
		}
	}

	// Array de Strings
	public static void ejemploArrayString() {
		String[] palabras = {"uno", "dos", "tres", "cuatro", "cinco"};
		System.out.println("Array de Strings:");
		for (String palabra : palabras) {
			System.out.println(palabra);
		}
	}

	// Array bidimensional
	public static void ejemploArrayBidimensional() {
		int[][] matriz = {
			{1, 2, 3},
			{4, 5, 6}
		};
		System.out.println("Array bidimensional:");
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}

    // Métodos para agregar y eliminar en arrays primitivos (simulado)
	public static void agregarElementoArray() {
		int[] numeros = {1, 2, 3, 4, 5};
		System.out.println("Array original:");
		for (int n : numeros) System.out.print(n + " ");
		System.out.println();

		// Agregar un elemento (por ejemplo, 6)
		int nuevoValor = 6;
		int[] nuevoArray = new int[numeros.length + 1];
		for (int i = 0; i < numeros.length; i++) {
			nuevoArray[i] = numeros[i];
		}
		nuevoArray[numeros.length] = nuevoValor;
		System.out.println("Array después de agregar 6:");
		for (int n : nuevoArray) System.out.print(n + " ");
		System.out.println();

		// Eliminar un elemento (por ejemplo, el 3)
		int eliminar = 3;
		int[] arrayEliminado = new int[nuevoArray.length - 1];
		int idx = 0;
		for (int i = 0; i < nuevoArray.length; i++) {
			if (nuevoArray[i] != eliminar) {
				arrayEliminado[idx++] = nuevoArray[i];
			}
		}
		System.out.println("Array después de eliminar 3:");
		for (int n : arrayEliminado) System.out.print(n + " ");
		System.out.println();
	}
    // fin de lo primitivos

	// Ejemplo de ArrayList y métodos más usados
	public static void ejemploArrayList() {
		ArrayList<Integer> lista = new ArrayList<>();
		// add: agregar elementos
		for (int i = 1; i <= 5; i++) {
			lista.add(i);
		}
		System.out.println("ArrayList de enteros:");
		for (int num : lista) {
			System.out.println(num);
		}

		// Métodos más usados           
		lista.add(6); // agrega al final
		lista.add(0, 0); // agrega en la posición 0
		lista.remove(Integer.valueOf(3)); // elimina el elemento 3 (por valor)
		lista.remove(2); // elimina el elemento en la posición 2
		boolean contieneCinco = lista.contains(5); // verifica si contiene 5
		int tamano = lista.size(); // tamaño de la lista
		int primerElemento = lista.get(0); // obtiene el primer elemento

		System.out.println("\nArrayList después de operaciones:");
		for (int num : lista) {
			System.out.print(num + " ");
		}
		System.out.println();
		System.out.println("¿Contiene 5?: " + contieneCinco);
		System.out.println("Tamaño: " + tamano);
		System.out.println("Primer elemento: " + primerElemento);
	}

	// Método main para probar los ejemplos
	public static void main(String[] args) {
		ejemploArrayInt();
		ejemploArrayString();
		ejemploArrayBidimensional();
		ejemploArrayList();
		agregarElementoArray();
	}
}
