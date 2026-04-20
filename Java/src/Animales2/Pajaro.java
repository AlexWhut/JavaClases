package Animales2;

// ============================================================
// CLASE: Pajaro
// ------------------------------------------------------------
// Hereda de Animal (tiene nombre, edad, peso).
// Anade su propio atributo: alturaMaxVuelo (en metros).
// ============================================================
public class Pajaro extends Animal {

    // Atributo propio de Pajaro: altura maxima que puede alcanzar volando
    private double alturaMaxVuelo; // en metros

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------------------------
    public Pajaro(String nombre, int edad, double peso, double alturaMaxVuelo) {
        super(nombre, edad, peso);
        setAlturaMaxVuelo(alturaMaxVuelo);
    }

    // ----------------------------------------------------------
    // GETTER y SETTER de alturaMaxVuelo
    // ----------------------------------------------------------
    public double getAlturaMaxVuelo() {
        return alturaMaxVuelo;
    }

    /** La altura no puede ser negativa. */
    public void setAlturaMaxVuelo(double alturaMaxVuelo) {
        this.alturaMaxVuelo = (alturaMaxVuelo < 0) ? 0.0 : alturaMaxVuelo;
    }

    // ----------------------------------------------------------
    // POLIMORFISMO: sobreescribimos hacerSonido()
    // ----------------------------------------------------------
    @Override
    public String hacerSonido() {
        return getNombre() + " dice: Pio pio!";
    }

    // ----------------------------------------------------------
    // toString: muestra info completa del Pajaro.
    // ----------------------------------------------------------
    @Override
    public String toString() {
        return "Pajaro[" + super.toString() + ", alturaMaxVuelo=" + alturaMaxVuelo + " m]";
    }
}
