# 08 - Clases abstractas e Interfaces

Ambas son herramientas para definir **contratos** y **abstracciones** en Java.
La pregunta clave siempre es: ¿uso una clase abstracta o una interfaz?

---

## Clases abstractas

Una **clase abstracta** es una clase que **no puede instanciarse** (no puedes hacer `new ClaseAbstracta()`).
Se usa como **clase base** para otras clases que si se pueden instanciar.

### ¿Cuando usar una clase abstracta?

Cuando quieres definir **comportamiento comun** (codigo compartido) y **forzar** a las subclases a implementar ciertos metodos.

```java
// No puedes hacer: new Figura()
// Pero si: new Circulo(), new Rectangulo()
public abstract class Figura {

    protected String color;   // atributo compartido por todas las figuras

    public Figura(String color) {
        this.color = color;
    }

    // METODO ABSTRACTO: sin implementacion, las subclases DEBEN implementarlo
    public abstract double calcularArea();
    public abstract double calcularPerimetro();

    // METODO CONCRETO: implementacion compartida, las subclases pueden heredarla o sobreescribirla
    public void mostrarInfo() {
        System.out.printf("Figura %s: area=%.2f, perimetro=%.2f%n",
            color, calcularArea(), calcularPerimetro());
    }
}
```

```java
public class Circulo extends Figura {

    private double radio;

    public Circulo(String color, double radio) {
        super(color);       // llama al constructor de Figura
        this.radio = radio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;   // implementacion obligatoria
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * radio;
    }
}

public class Rectangulo extends Figura {

    private double ancho, alto;

    public Rectangulo(String color, double ancho, double alto) {
        super(color);
        this.ancho = ancho;
        this.alto  = alto;
    }

    @Override
    public double calcularArea() { return ancho * alto; }

    @Override
    public double calcularPerimetro() { return 2 * (ancho + alto); }
}
```

```java
// Uso con polimorfismo:
Figura[] figuras = {
    new Circulo("rojo", 5),
    new Rectangulo("azul", 4, 3)
};

for (Figura f : figuras) {
    f.mostrarInfo();   // cada una calcula su area correctamente
}
// Figura rojo: area=78.54, perimetro=31.42
// Figura azul: area=12.00, perimetro=14.00
```

---

## Interfaces

Una **interfaz** define un **contrato**: que metodos debe tener una clase que la implemente.
Es como decir "toda clase que sea MetodoPago debe poder `pagar()`".

### Caracteristicas clave de las interfaces

- No tienen estado (no atributos de instancia, solo constantes)
- Todos los metodos son por defecto `public abstract` (puedes omitirlo)
- Una clase puede implementar **multiples interfaces** (resuelve la limitacion de herencia simple)
- Desde Java 8: pueden tener metodos `default` (con implementacion) y `static`

```java
// Definicion de la interfaz
public interface MetodoPago {

    // Constante (implicita public static final)
    int LIMITE_DIARIO = 5000;

    // Metodo abstracto: TODAS las clases que implementen MetodoPago deben definir esto
    void pagar(double monto);

    // Metodo con valor por defecto para validacion (desde Java 8)
    default boolean validarMonto(double monto) {
        return monto > 0 && monto <= LIMITE_DIARIO;
    }
}
```

```java
// Implementacion: una clase puede implementar varias interfaces
public class PagoTarjeta implements MetodoPago {

    private String numeroTarjeta;

    public PagoTarjeta(String numero) {
        this.numeroTarjeta = numero;
    }

    @Override
    public void pagar(double monto) {
        if (validarMonto(monto)) {   // usa el metodo default de la interfaz
            System.out.println("Pagando " + monto + "€ con tarjeta " + numeroTarjeta);
        } else {
            System.out.println("Monto invalido");
        }
    }
}
```

### Implementar multiples interfaces

```java
public interface Serializable {
    byte[] serializar();
}

public interface Loggable {
    void log(String mensaje);
}

// Una clase puede implementar las dos a la vez
public class PagoOnline implements MetodoPago, Serializable, Loggable {

    @Override
    public void pagar(double monto) { ... }

    @Override
    public byte[] serializar() { ... }

    @Override
    public void log(String mensaje) { ... }
}
```

---

## Diferencias clave

| Caracteristica              | Clase abstracta                | Interfaz                        |
|-----------------------------|--------------------------------|---------------------------------|
| Instanciacion               | No                             | No                              |
| Herencia/implementacion     | `extends` (solo una)           | `implements` (multiples)        |
| Atributos de instancia      | Si (cualquier modificador)     | No (solo `public static final`) |
| Metodos con implementacion  | Si (normal + abstract)         | Solo `default` y `static`       |
| Constructores               | Si                             | No                              |
| Modificadores de metodos    | Cualquiera                     | Solo `public`                   |

---

## ¿Cuando usar cada una?

### Usa clase abstracta cuando...

- Las subclases **comparten codigo** (atributos, metodos concretos)
- Existe una relacion **"es un"** clara: `Perro es un Animal`
- Necesitas **constructores** para inicializar estado comun

```java
// Ejemplo adecuado para clase abstracta:
// Deportista, UsuarioKeto y Paciente COMPARTEN historial, busquedas, etc.
abstract class Usuario {
    protected ArrayList<Receta> historial;   // estado compartido
    public void añadirReceta(Receta r) { historial.add(r); }  // comportamiento compartido
    public abstract int evaluarReceta(Receta r);  // forzado en subclases
}
```

### Usa interfaz cuando...

- Defines un **contrato de capacidad** sin importar la jerarquia: `puede hacer X`
- Necesitas que **clases no relacionadas** compartan un comportamiento
- Quieres simular herencia multiple

```java
// Ejemplo adecuado para interfaz:
// Un Coche y un Avion son muy distintos, pero ambos pueden ser Conducible
interface Conducible {
    void acelerar();
    void frenar();
}

class Coche   implements Conducible { ... }
class Avion   implements Conducible { ... }
class Barco   implements Conducible { ... }
```

---

## Diferencia entre `extends` e `implements`

```java
// extends: hereda de otra CLASE (solo una)
class Perro extends Animal { }

// implements: implementa una INTERFAZ (puede ser varias)
class Perro extends Animal implements Comparable<Perro>, Serializable { }
//                        ↑ herencia de clase    ↑ multiples interfaces
```

---

## Interfaz `Comparable` — ejemplo real

Muy usada para definir el orden natural de los objetos:

```java
public class Alumno implements Comparable<Alumno> {

    private String nombre;
    private double nota;

    public Alumno(String nombre, double nota) {
        this.nombre = nombre;
        this.nota   = nota;
    }

    @Override
    public int compareTo(Alumno otro) {
        // Ordena por nota de mayor a menor
        return Double.compare(otro.nota, this.nota);
        // Si devuelve negativo: this va antes que otro
        // Si devuelve positivo: this va despues que otro
        // Si devuelve 0: son iguales en orden
    }
}

// Uso:
List<Alumno> alumnos = new ArrayList<>();
alumnos.add(new Alumno("Ana", 8.5));
alumnos.add(new Alumno("Luis", 6.0));
alumnos.add(new Alumno("Mar", 9.2));

Collections.sort(alumnos);  // usa compareTo para ordenar
// Mar (9.2), Ana (8.5), Luis (6.0)
```

---

## Resumen

```
Clase abstracta:
  - abstract class Nombre { }
  - Metodos abstractos (sin cuerpo) + concretos (con cuerpo)
  - Tiene constructores, atributos de instancia
  - Se hereda con extends (solo una)
  - Usa cuando: clases hijas comparten estado y comportamiento

Interfaz:
  - interface Nombre { }
  - Metodos publicos abstractos (+ default y static desde Java 8)
  - Sin constructores, sin atributos de instancia (solo constantes)
  - Se implementa con implements (puede ser varias)
  - Usa cuando: defines contratos de capacidad entre clases no relacionadas
```
