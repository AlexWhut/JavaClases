package Videojuego;

public class Guerrero extends Personaje {
    private int fuerza;
    private boolean tieneEscudo;

    public Guerrero(String nombre, int nivel, int fuerza) {
        super(nombre, 150, nivel);
        this.fuerza = fuerza;
        this.tieneEscudo = true;
    }

    @Override
    public void atacar() {
        System.out.println(nombre
                + " golpea con su espada y causa " + fuerza + " de dano.");
    }

    public void usarEscudo() {
        if (tieneEscudo) {
            System.out.println(nombre + " bloquea el ataque con su escudo.");
        } else {
            System.out.println(nombre + " no tiene escudo equipado.");
        }
    }
}
