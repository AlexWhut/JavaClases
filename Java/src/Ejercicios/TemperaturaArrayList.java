package Ejercicios;

import java.util.ArrayList;

public class TemperaturaArrayList {

	public static void main(String[] args) {
		ArrayList<Double> temps = new ArrayList<>();
		temps.add(36.5);
		temps.add(38.2);
		temps.add(37.1);
		temps.add(39.0);
		temps.add(35.8);

		double max = temps.get(0), min = temps.get(0), suma = 0;

		for (double t : temps) {
			if (t > max) max = t;
			if (t < min) min = t;
			suma += t;
		}

		double promedio = suma / temps.size();

		System.out.println("Temperatura más alta: " + max);
		System.out.println("Temperatura más baja: " + min);
		System.out.println("Promedio: " + promedio);
	}
}
