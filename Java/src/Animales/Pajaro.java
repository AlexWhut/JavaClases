package Animales;

public class Pajaro extends Animal {

    private double envergaduraAlas;

    public Pajaro(String nombre, int edad, double envergaduraAlas) {
        super(nombre, edad);
        this.envergaduraAlas = envergaduraAlas;
    }

    public double getEnvergaduraAlas() {
        return envergaduraAlas;
    }

    public void setEnvergaduraAlas(double envergaduraAlas) {
        if (envergaduraAlas < 0) {
            this.envergaduraAlas = 0;
            return;
        }
        this.envergaduraAlas = envergaduraAlas;
    }

    @Override
    public void hacerSonido() {
        System.out.println(getNombre() + " dice: Pio pio!");
    }
}
