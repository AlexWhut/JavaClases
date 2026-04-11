package Videojuego;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("       MUNDO RPG - DEMO POO JAVA");
        System.out.println("============================================\n");

        Guerrero g = new Guerrero("Arthas", 5, 45);
        Mago m = new Mago("Gandalf", 8, 100, "Bola de Fuego");
        Arquero a = new Arquero("Legolas", 6, 10);

        System.out.println("=== INFORMACION DE PERSONAJES ===");
        g.mostrarInfo();
        m.mostrarInfo();
        a.mostrarInfo();

        System.out.println("\n=== ATAQUES INDIVIDUALES ===");
        g.atacar();
        m.atacar();
        a.atacar();

        System.out.println("\n=== HABILIDADES ESPECIALES ===");
        g.usarEscudo();
        m.recuperarMana(40);
        a.recargarFlechas(5);

        // Polimorfismo: una lista de Personaje guarda subtipos distintos.
        System.out.println("\n=== POLIMORFISMO: TURNO DE TODOS ===");
        List<Personaje> equipo = new ArrayList<>();
        equipo.add(g);
        equipo.add(m);
        equipo.add(a);

        for (Personaje personaje : equipo) {
            personaje.atacar();
        }

        System.out.println("\n=== RECIBIENDO DANO ===");
        g.recibirDanio(30);
        m.recibirDanio(90);

        // Uso de la interfaz como tipo.
        System.out.println("\n=== USANDO LA INTERFAZ Atacable ===");
        Atacable atacante = new Guerrero("Spawn", 3, 35);
        atacante.atacar();
        atacante.recibirDanio(20);

        System.out.println("\nFin de la demo.");
    }
}
