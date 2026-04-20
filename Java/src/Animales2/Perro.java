package Animales2;

// ============================================================
// CLASE: Perro
// ------------------------------------------------------------
// Hereda de Animal (tiene nombre, edad, peso).
// Anade su propio atributo especifico: raza.
// ============================================================
public class Perro extends Animal {

    // Atributo propio de Perro
    private String raza;

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // Llama a super() para inicializar los atributos de Animal,
    // luego inicializa el atributo propio.
    // ----------------------------------------------------------
    public Perro(String nombre, int edad, double peso, String raza) {
        super(nombre, edad, peso); // Llama al constructor de Animal
        setRaza(raza);
    }

    // ----------------------------------------------------------
    // GETTER y SETTER de raza
    // ----------------------------------------------------------
    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        if (raza == null || raza.isBlank()) {
            this.raza = "Desconocida";
        } else {
            this.raza = raza;
        }
    }

    // ----------------------------------------------------------
    // POLIMORFISMO: sobreescribimos hacerSonido()
    // Devuelve el sonido como String para que Main decida
    // si imprimirlo, guardarlo o usarlo de otra forma.
    // ----------------------------------------------------------
    @Override
    public String hacerSonido() {
        return getNombre() + " dice: Guau guau!";
    }

    // ----------------------------------------------------------
    // toString: muestra info completa del Perro.
    // Llamamos a super.toString() para no repetir codigo.
    // ----------------------------------------------------------
    @Override
    public String toString() {
        return "Perro[" + super.toString() + ", raza=" + raza + "]";
    }
}
