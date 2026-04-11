package Clases;

public class Bucles {

	// Bucle for
	public static void ejemploFor() {
		System.out.println("Bucle for:");
		for (int i = 1; i <= 5; i++) {
			System.out.println(i);
		}
	}

	// Bucle while
	public static void ejemploWhile() {
		System.out.println("Bucle while:");
		int i = 1;
		while (i <= 5) {
			System.out.println(i);
			i++;
		}
	}

	// Bucle do-while
	public static void ejemploDoWhile() {
		System.out.println("Bucle do-while:");
		int i = 1;
		do {
			System.out.println(i);
			i++;
		} while (i <= 5);
	}

	// Bucle for-each (para arrays)
	public static void ejemploForEach() {
		System.out.println("Bucle for-each:");
		String[] frutas = {"Manzana", "Banana", "Naranja"};
		for (String fruta : frutas) {
			System.out.println(fruta);
		}
		// Imprimir un elemento específico (por ejemplo, el primero)
		System.out.println("Primer elemento: " + frutas[0]);
	}

	// Método main para probar los bucles
	public static void main(String[] args) {
		ejemploFor();
		ejemploWhile();
		ejemploDoWhile();
		ejemploForEach();
	}
}
