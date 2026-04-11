package Animales;

public class Gato extends Animal {

    private boolean leGustaLaCaja;

    public Gato(String nombre, int edad, boolean leGustaLaCaja) {
        super(nombre, edad);
        this.leGustaLaCaja = leGustaLaCaja;
    }

    public boolean isLeGustaLaCaja() {
        return leGustaLaCaja;
    }

    public void setLeGustaLaCaja(boolean leGustaLaCaja) {
        this.leGustaLaCaja = leGustaLaCaja;
    }

    @Override
    public void hacerSonido() {
        System.out.println(getNombre() + " dice: Miau miau!");
    }
}
