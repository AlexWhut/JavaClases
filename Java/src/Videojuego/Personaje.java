package Videojuego;

// Clase abstracta base para compartir estado y comportamiento comun.
public abstract class Personaje implements Atacable {
    protected String nombre;
    protected int vida;
    protected int nivel;

    public Personaje(String nombre, int vida, int nivel) {
        this.nombre = nombre;
        this.vida = vida;
        this.nivel = nivel;
    }

    // Cada subclase define su propia forma de atacar (polimorfismo).
    @Override
    public abstract void atacar();

    @Override
    public void recibirDanio(int cantidad) {
        this.vida -= cantidad;
        System.out.println(nombre + " recibio " + cantidad
                + " de dano. Vida restante: " + vida);
        if (vida <= 0) {
            System.out.println(nombre + " ha sido derrotado.");
        }
    }

    public void mostrarInfo() {
        System.out.println("--- " + nombre
                + " | Nivel " + nivel
                + " | Vida: " + vida + " ---");
    }

    public String getNombre() {
        return nombre;
    }
}
