package Animales2;

// ============================================================
// CLASE ABSTRACTA: Animal
// ------------------------------------------------------------
// Una clase abstracta NO se puede instanciar directamente.
// Sirve como "molde" comun para todos los animales.
// Cada subclase (Perro, Gato, Pajaro) hereda sus atributos
// y metodos, y DEBE implementar los metodos abstractos.
// ============================================================
public abstract class Animal {

    // ----------------------------------------------------------
    // ATRIBUTOS PRIVADOS (Encapsulacion)
    // Se acceden desde fuera SOLO mediante getters y setters.
    // ----------------------------------------------------------
    private String nombre; // nombre del animal
    private int    edad;   // edad en anios
    private double peso;   // peso en kilogramos

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // Recibe los tres datos basicos y los valida via setters.
    // ----------------------------------------------------------
    public Animal(String nombre, int edad, double peso) {
        setNombre(nombre);
        setEdad(edad);
        setPeso(peso);
    }

    // ----------------------------------------------------------
    // GETTERS: permiten leer los atributos desde fuera.
    // ----------------------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getPeso() {
        return peso;
    }

    // ----------------------------------------------------------
    // SETTERS: permiten modificar los atributos con validacion.
    // ----------------------------------------------------------

    /** Valida que el nombre no este vacio ni sea nulo. */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            this.nombre = "Sin nombre";
        } else {
            this.nombre = nombre;
        }
    }

    /** La edad no puede ser negativa. */
    public void setEdad(int edad) {
        this.edad = (edad < 0) ? 0 : edad;
    }

    /** El peso debe ser mayor que cero. */
    public void setPeso(double peso) {
        this.peso = (peso <= 0) ? 0.0 : peso;
    }

    // ----------------------------------------------------------
    // METODO COMUN (Herencia)
    // Todas las subclases heredan este metodo tal cual.
    // ----------------------------------------------------------
    public void dormir() {
        System.out.println(nombre + " esta durmiendo... Zzzz");
    }

    // ----------------------------------------------------------
    // METODO ABSTRACTO (Polimorfismo)
    // Devuelve el sonido como String en lugar de imprimirlo.
    // Asi el metodo solo tiene UNA responsabilidad: producir
    // el sonido. Quien llame decide si lo imprime, lo guarda, etc.
    // ----------------------------------------------------------
    public abstract String hacerSonido();

    // ----------------------------------------------------------
    // toString
    // Se ejecuta automaticamente cuando se imprime el objeto
    // con System.out.println(animal).
    // ----------------------------------------------------------
    @Override
    public String toString() {
        return "Animal[nombre=" + nombre +
               ", edad=" + edad + " anios" +
               ", peso=" + peso + " kg]";
    }
}
