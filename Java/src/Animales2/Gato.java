package Animales2;

// ============================================================
// CLASE: Gato
// ------------------------------------------------------------
// Hereda de Animal (tiene nombre, edad, peso).
// Anade su propio atributo especifico: vidasRestantes.
// ============================================================
public class Gato extends Animal {

    // Atributo propio de Gato: los gatos tienen 7 vidas (segun la leyenda)
    private int vidasRestantes;

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------------------------
    public Gato(String nombre, int edad, double peso, int vidasRestantes) {
        super(nombre, edad, peso);
        setVidasRestantes(vidasRestantes);
    }

    // ----------------------------------------------------------
    // GETTER y SETTER de vidasRestantes
    // ----------------------------------------------------------
    public int getVidasRestantes() {
        return vidasRestantes;
    }

    /** Las vidas deben estar entre 0 y 7. */
    public void setVidasRestantes(int vidasRestantes) {
        if (vidasRestantes < 0) {
            this.vidasRestantes = 0;
        } else if (vidasRestantes > 7) {
            this.vidasRestantes = 7;
        } else {
            this.vidasRestantes = vidasRestantes;
        }
    }

    // ----------------------------------------------------------
    // POLIMORFISMO: sobreescribimos hacerSonido()
    // ----------------------------------------------------------
    @Override
    public String hacerSonido() {
        return getNombre() + " dice: Miau miau!";
    }

    // ----------------------------------------------------------
    // toString: muestra info completa del Gato.
    // ----------------------------------------------------------
    @Override
    public String toString() {
        return "Gato[" + super.toString() + ", vidasRestantes=" + vidasRestantes + "]";
    }
}
