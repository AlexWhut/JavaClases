package Animales;

// ABSTRACCION:
// Esta clase representa la idea general de "Animal".
// No tiene sentido crear un animal "generico", por eso es abstract.
public abstract class Animal {

    // ENCAPSULACION:
    // Los atributos son privados para proteger su estado.
    private String nombre;
    private int edad;

    public Animal(String nombre, int edad) {
        setNombre(nombre);
        setEdad(edad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            this.nombre = "Sin nombre";
            return;
        }
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 0) {
            this.edad = 0;
            return;
        }
        this.edad = edad;
    }

    // Metodo comun para todos los animales.
    public void dormir() {
        System.out.println(nombre + " esta durmiendo...");
    }

    // ABSTRACCION + POLIMORFISMO:
    // Cada tipo de animal debe implementar su propio sonido.
    public abstract void hacerSonido();
}
