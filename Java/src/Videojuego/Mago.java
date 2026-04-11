package Videojuego;

public class Mago extends Personaje {
    private int mana;
    private String hechizo;

    public Mago(String nombre, int nivel, int mana, String hechizo) {
        super(nombre, 80, nivel);
        this.mana = mana;
        this.hechizo = hechizo;
    }

    @Override
    public void atacar() {
        if (mana >= 20) {
            System.out.println(nombre + " lanza " + hechizo
                    + " y causa 60 de dano magico. (Mana: "
                    + mana + " -> " + (mana - 20) + ")");
            mana -= 20;
        } else {
            System.out.println(nombre
                    + " no tiene mana suficiente para lanzar " + hechizo);
        }
    }

    public void recuperarMana(int cantidad) {
        mana += cantidad;
        System.out.println(nombre + " recupera " + cantidad
                + " de mana. Mana actual: " + mana);
    }
}
