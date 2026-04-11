package Videojuego;

public class Arquero extends Personaje {
    private int flechas;
    private double precision;

    public Arquero(String nombre, int nivel, int flechas) {
        super(nombre, 100, nivel);
        this.flechas = flechas;
        this.precision = 0.85;
    }

    @Override
    public void atacar() {
        if (flechas > 0) {
            boolean impacta = Math.random() < precision;
            flechas--;
            if (impacta) {
                System.out.println(nombre + " dispara y acierta. Dano: 40. Flechas: " + flechas);
            } else {
                System.out.println(nombre + " dispara pero falla. Flechas: " + flechas);
            }
        } else {
            System.out.println(nombre + " no tiene mas flechas.");
        }
    }

    public void recargarFlechas(int cantidad) {
        flechas += cantidad;
        System.out.println(nombre + " recargo " + cantidad
                + " flechas. Total: " + flechas);
    }
}
