package Ejercicios.ArrayList;

public class Temperaturas {

	public static void main(String[] args) {
		double[] temps = {36.5, 38.2, 37.1, 39.0, 35.8};
		double max = temps[0], min = temps[0], suma = 0;

		for (double t : temps) {
			if (t > max) max = t;
			if (t < min) min = t;
			suma += t;
		}

		double promedio = suma / temps.length;

		System.out.println("Temperatura más alta: " + max);
		System.out.println("Temperatura más baja: " + min);
		System.out.println("Promedio: " + promedio);
	}
}
