package Animales2;

import java.util.ArrayList;

// ============================================================
// CLASE: Main  (Animales2)
// ------------------------------------------------------------
// Aqui practicamos:
//   - ArrayList para guardar una coleccion de animales
//   - toString para imprimir objetos facilmente
//   - instanceof para saber el tipo real de cada animal
//   - Bucles (for-each) para recorrer la lista y calcular:
//       * Peso promedio de los Perros
//       * Peso promedio de los Gatos
//       * Altura maxima de vuelo entre todos los Pajaros
// ============================================================
public class Main {

    public static void main(String[] args) {

        // ==========================================================
        // 1) ARRAYLIST
        // ----------------------------------------------------------
        // ArrayList es una lista dinamica: puede crecer o reducirse.
        // Usamos el tipo Animal (clase padre) para guardar cualquier
        // subclase: Perro, Gato o Pajaro. Esto es POLIMORFISMO.
        // ==========================================================
        ArrayList<Animal> animales = new ArrayList<>();

        // Agregamos varios animales con add()
        animales.add(new Perro("Toby",   3, 25.0, "Labrador"));
        animales.add(new Perro("Rex",    5, 30.5, "Pastor Aleman"));
        animales.add(new Perro("Bobby",  2,  8.0, "Chihuahua"));
        animales.add(new Gato("Mishi",   2,  4.2, 7));
        animales.add(new Gato("Luna",    4,  3.8, 6));
        animales.add(new Gato("Pelusa",  1,  5.0, 7));
        animales.add(new Pajaro("Piolin",   1,  0.05, 15.0));
        animales.add(new Pajaro("Condor",   6,  9.50, 4500.0));
        animales.add(new Pajaro("Canario",  2,  0.02, 10.0));

        // ==========================================================
        // 2) toString + BUCLE
        // ----------------------------------------------------------
        // Al imprimir un objeto con println, Java llama automaticamente
        // a su metodo toString(). Aqui mostramos todos los animales.
        // ==========================================================
        System.out.println("========== LISTA DE ANIMALES ==========");
        for (Animal animal : animales) {
            // Esto llama a toString() del Perro, Gato o Pajaro segun sea.
            System.out.println(animal);
        }

        // ==========================================================
        // 3) POLIMORFISMO: cada animal hace su propio sonido
        // ==========================================================
        System.out.println("\n========== SONIDOS ==========");
        for (Animal animal : animales) {
            // hacerSonido() devuelve un String: aqui decidimos imprimirlo.
            // Polimorfismo: mismo metodo, distinto resultado segun el tipo real.
            System.out.println(animal.hacerSonido());
        }

        // ==========================================================
        // 4) instanceof + ESTADISTICAS CON BUCLES
        // ----------------------------------------------------------
        // instanceof comprueba si un objeto es de un tipo concreto.
        // Lo usamos para separar Perros, Gatos y Pajaros y calcular
        // estadisticas especificas de cada tipo.
        // ==========================================================

        // --- Variables acumuladoras ---
        double sumaPesoPerros = 0;
        int    contadorPerros = 0;

        double sumaPesoGatos = 0;
        int    contadorGatos = 0;

        double alturaMaxPajaros = 0;
        String nombrePajaroMasAlto = "";

        // --- Recorremos TODA la lista UNA sola vez ---
        for (Animal animal : animales) {

            // ¿Es un Perro?
            if (animal instanceof Perro perro) {
                // Hacemos casting automatico con "instanceof Perro perro"
                // (pattern matching, disponible desde Java 16)
                sumaPesoPerros += perro.getPeso();
                contadorPerros++;
            }

            // ¿Es un Gato?
            else if (animal instanceof Gato gato) {
                sumaPesoGatos += gato.getPeso();
                contadorGatos++;
            }

            // ¿Es un Pajaro?
            else if (animal instanceof Pajaro pajaro) {
                // Buscamos la altura maxima
                if (pajaro.getAlturaMaxVuelo() > alturaMaxPajaros) {
                    alturaMaxPajaros      = pajaro.getAlturaMaxVuelo();
                    nombrePajaroMasAlto   = pajaro.getNombre();
                }
            }
        }

        // ==========================================================
        // 5) MOSTRAMOS RESULTADOS
        // ==========================================================
        System.out.println("\n========== ESTADISTICAS ==========");

        // Peso promedio de los Perros
        if (contadorPerros > 0) {
            double promedioPesoPerros = sumaPesoPerros / contadorPerros;
            System.out.printf("Peso promedio de los %d Perros : %.2f kg%n",
                    contadorPerros, promedioPesoPerros);
        }

        // Peso promedio de los Gatos
        if (contadorGatos > 0) {
            double promedioPesoGatos = sumaPesoGatos / contadorGatos;
            System.out.printf("Peso promedio de los %d Gatos  : %.2f kg%n",
                    contadorGatos, promedioPesoGatos);
        }

        // Altura maxima de vuelo entre todos los Pajaros
        if (!nombrePajaroMasAlto.isEmpty()) {
            System.out.printf("Altura maxima de vuelo        : %.1f m (%s)%n",
                    alturaMaxPajaros, nombrePajaroMasAlto);
        }

        // ==========================================================
        // 6) MODIFICAR CON SETTERS (demostracion)
        // ----------------------------------------------------------
        // Podemos cambiar un dato individual y la validacion
        // del setter rechaza valores incorrectos.
        // ==========================================================
        System.out.println("\n========== MODIFICACION CON SETTERS ==========");
        Animal primero = animales.get(0); // Toby
        System.out.println("Antes : " + primero);
        primero.setEdad(-3);              // Valor invalido -> se corrige a 0
        primero.setPeso(26.5);            // Valor valido
        System.out.println("Despues: " + primero);
    }
}

