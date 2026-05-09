> Ver el indice completo de teoria: [00_Indice.md](00_Indice.md)

# Los 4 Pilares de la Programacion Orientada a Objetos (POO)

La Programacion Orientada a Objetos organiza el codigo como **objetos** que tienen datos y comportamiento, igual que los objetos del mundo real. Se basa en 4 pilares:

1. **Encapsulacion** — proteger los datos internos
2. **Abstraccion** — ocultar la complejidad
3. **Herencia** — reutilizar codigo entre clases
4. **Polimorfismo** — mismo codigo, comportamientos diferentes

Estos pilares no son independientes: se usan juntos para construir sistemas bien diseñados.

---

## 1. Encapsulacion

### Que es

La encapsulacion consiste en **ocultar el estado interno** de un objeto y solo permitir acceder a el a traves de metodos controlados.

En Java se aplica marcando los atributos como `private` y exponiendo solo lo necesario con metodos `public`.

### Por que importa

Sin encapsulacion, cualquiera puede poner un valor incorrecto en tu objeto:

```java
// SIN encapsulacion: cualquier codigo puede hacer esto
public class CuentaBancaria {
    public double saldo;   // publico = sin proteccion
}

CuentaBancaria c = new CuentaBancaria();
c.saldo = -5000;    // no hay nada que lo impida — el objeto queda en estado invalido
```

Con encapsulacion, la clase controla que datos son validos:

```java
// CON encapsulacion: la clase protege sus datos
public class CuentaBancaria {
    private double saldo;   // privado = nadie accede directamente

    public CuentaBancaria(double saldoInicial) {
        if (saldoInicial < 0) throw new IllegalArgumentException("Saldo negativo");
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;   // getter: permite leer pero no escribir directamente
    }

    public void depositar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto invalido");
        saldo += monto;   // la clase controla como cambia el saldo
    }

    public void retirar(double monto) {
        if (monto <= 0 || monto > saldo) throw new IllegalArgumentException("Retiro invalido");
        saldo -= monto;
    }
}

// Uso: el estado interno siempre es valido
CuentaBancaria c = new CuentaBancaria(1000);
c.depositar(500);      // saldo = 1500
c.retirar(200);        // saldo = 1300
// c.saldo = -5000;    // ERROR de compilacion: saldo es private
```

### Getters y Setters

Los **getters** permiten leer un atributo. Los **setters** permiten modificarlo con validacion.

```java
public class Persona {
    private String nombre;
    private int    edad;

    // Getter: solo lee
    public String getNombre() { return nombre; }
    public int    getEdad()   { return edad; }

    // Setter: modifica con validacion
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        if (edad < 0 || edad > 150) {
            throw new IllegalArgumentException("Edad fuera de rango: " + edad);
        }
        this.edad = edad;
    }
}
```

> No siempre hace falta un setter para todo. Si un atributo no debe cambiar despues de crearse, simplemente no pongas el setter.

### Ventajas de la encapsulacion

- El objeto **siempre esta en un estado valido**
- Si cambias la implementacion interna, el codigo que usa la clase no se rompe
- Es mas facil encontrar errores: los datos solo cambian en un lugar controlado

---

## 2. Abstraccion

### Que es

La abstraccion consiste en **mostrar solo lo necesario** y ocultar los detalles de como funciona por dentro.

Cuando usas un metodo, no necesitas saber como esta implementado. Solo necesitas saber que hace y como llamarlo.

### Dos formas de abstraccion en Java

**Clases abstractas:** definen una plantilla parcial. Tienen metodos con implementacion y metodos abstractos (sin implementacion) que las subclases deben completar.

```java
public abstract class Figura {
    protected String color;

    public Figura(String color) {
        this.color = color;
    }

    // Metodo abstracto: la subclase DEBE implementarlo, aqui no sabemos como
    public abstract double calcularArea();

    // Metodo concreto: comportamiento compartido por todas las figuras
    public void mostrarInfo() {
        System.out.printf("Figura %s con area %.2f%n", color, calcularArea());
        // llamamos a calcularArea() sin saber como se implementa — eso es abstraccion
    }
}
```

```java
public class Circulo extends Figura {
    private double radio;

    public Circulo(String color, double radio) {
        super(color);
        this.radio = radio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;   // implementacion concreta para el circulo
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
    public double calcularArea() {
        return ancho * alto;   // implementacion concreta para el rectangulo
    }
}
```

```java
// Uso: llamamos a mostrarInfo() sin preocuparnos de como calcula el area cada figura
Figura[] figuras = { new Circulo("rojo", 5), new Rectangulo("azul", 4, 3) };
for (Figura f : figuras) {
    f.mostrarInfo();   // cada figura sabe como calcular su area
}
// Figura rojo con area 78.54
// Figura azul con area 12.00
```

**Interfaces:** definen un contrato puro de que metodos debe tener una clase, sin ninguna implementacion (o con metodos `default` opcionales).

```java
public interface MetodoPago {
    void pagar(double monto);       // contrato: cualquier MetodoPago debe poder pagar
    boolean validarMonto(double m); // contrato: debe poder validar el monto
}

public class PagoTarjeta implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pagando " + monto + " euros con tarjeta");
    }
    @Override
    public boolean validarMonto(double m) { return m > 0 && m < 5000; }
}

public class PagoPaypal implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pagando " + monto + " euros con PayPal");
    }
    @Override
    public boolean validarMonto(double m) { return m > 0 && m < 10000; }
}

// El codigo que usa el pago no necesita saber que tipo concreto es
public void procesarPago(MetodoPago metodo, double monto) {
    if (metodo.validarMonto(monto)) {
        metodo.pagar(monto);
    }
}
```

### Diferencia entre clase abstracta e interfaz

| Aspecto              | Clase abstracta                  | Interfaz                         |
|----------------------|----------------------------------|----------------------------------|
| Palabra clave        | `abstract class`                 | `interface`                      |
| Herencia             | `extends` (solo una clase)       | `implements` (varias interfaces) |
| Atributos            | Si, de cualquier tipo            | Solo constantes                  |
| Constructor          | Si                               | No                               |
| Metodos              | Abstractos y concretos           | Abstractos + `default`           |
| Usa cuando...        | Las hijas comparten estado/logica| Defines una capacidad o contrato |

### Ventajas de la abstraccion

- Puedes cambiar la implementacion sin afectar al codigo que la usa
- Obliga a definir contratos claros entre partes del sistema
- Permite trabajar con conceptos generales sin preocuparse de los detalles

---

## 3. Herencia

### Que es

La herencia permite crear una clase nueva que **reutiliza automaticamente** los atributos y metodos de otra clase existente, y puede ampliarlos o modificarlos.

En Java: `class Hijo extends Padre`

### El problema que resuelve

Sin herencia, tienes codigo duplicado:

```java
// SIN herencia: duplicacion masiva
class Empleado {
    String nombre;
    double salario;
    void trabajar() { System.out.println(nombre + " trabaja"); }
}

class Gerente {
    String nombre;      // duplicado
    double salario;     // duplicado
    void trabajar() { System.out.println(nombre + " trabaja"); }  // duplicado
    void dirigir()  { System.out.println(nombre + " dirige"); }
}
```

Con herencia, el codigo comun va en la superclase:

```java
// CON herencia: sin duplicacion
public class Empleado {
    protected String nombre;
    protected double salario;

    public Empleado(String nombre, double salario) {
        this.nombre  = nombre;
        this.salario = salario;
    }

    public void trabajar() {
        System.out.println(nombre + " trabaja");
    }
}

public class Gerente extends Empleado {
    private int equipo;   // solo lo que es especifico de Gerente

    public Gerente(String nombre, double salario, int equipo) {
        super(nombre, salario);   // reutiliza el constructor de Empleado
        this.equipo = equipo;
    }

    // Hereda trabajar() de Empleado — no hay que repetirlo
    // Añade lo propio:
    public void dirigir() {
        System.out.println(nombre + " dirige a " + equipo + " personas");
    }
}
```

### super — acceder a la clase padre

```java
public class Animal {
    protected String nombre;

    public Animal(String nombre) { this.nombre = nombre; }

    public String describir() {
        return "Animal: " + nombre;
    }
}

public class Perro extends Animal {
    private String raza;

    public Perro(String nombre, String raza) {
        super(nombre);     // llama al constructor de Animal — DEBE ser la primera linea
        this.raza = raza;
    }

    @Override
    public String describir() {
        return super.describir() + " | Raza: " + raza;   // reutiliza el metodo del padre
    }
}
```

### @Override — sobreescribir un metodo

```java
public class Animal {
    public void hacerSonido() { System.out.println("..."); }
}

public class Perro extends Animal {
    @Override                                        // avisa al compilador que sobreescribes
    public void hacerSonido() {
        System.out.println("Guau");                  // nueva implementacion
    }
}
```

La anotacion `@Override` no es obligatoria pero es muy recomendable: si te equivocas en el nombre del metodo, el compilador te lo dice.

### Ventajas de la herencia

- Evita la duplicacion de codigo (principio DRY: Don't Repeat Yourself)
- Los cambios en la superclase se aplican automaticamente a todas las subclases
- Organiza las clases en jerarquias naturales

> **Nota importante:** Java solo permite herencia **simple** (una clase solo puede extender una clase). Para combinar comportamientos de multiples fuentes, se usan interfaces.

---

## 4. Polimorfismo

### Que es

Polimorfismo significa "muchas formas". Permite que el **mismo codigo funcione con objetos de distintos tipos**, y que cada tipo responda de forma diferente segun su implementacion.

### Dos tipos de polimorfismo en Java

#### Polimorfismo estatico: sobrecarga de metodos (Overloading)

El compilador elige que metodo ejecutar segun los parametros, en tiempo de **compilacion**.

```java
public class Calculadora {
    // Tres metodos con el mismo nombre pero distintos parametros
    public int sumar(int a, int b)          { return a + b; }
    public double sumar(double a, double b) { return a + b; }
    public int sumar(int a, int b, int c)   { return a + b + c; }
}

Calculadora c = new Calculadora();
c.sumar(2, 3);        // llama a la primera version  -> 5
c.sumar(2.0, 3.5);    // llama a la segunda version  -> 5.5
c.sumar(1, 2, 3);     // llama a la tercera version  -> 6
```

La eleccion se hace en **tiempo de compilacion** segun los tipos de los argumentos.

#### Polimorfismo dinamico: sobreescritura de metodos (Overriding)

El mismo metodo se comporta diferente segun el **tipo real del objeto**, decidido en tiempo de **ejecucion**.

```java
public class Animal {
    public void hacerSonido() { System.out.println("..."); }
}

public class Perro extends Animal {
    @Override
    public void hacerSonido() { System.out.println("Guau"); }
}

public class Gato extends Animal {
    @Override
    public void hacerSonido() { System.out.println("Miau"); }
}

public class Pato extends Animal {
    @Override
    public void hacerSonido() { System.out.println("Cuac"); }
}
```

```java
// Clave: la referencia es de tipo Animal, pero los objetos son de tipos distintos
Animal a1 = new Perro();   // referencia Animal, objeto Perro
Animal a2 = new Gato();    // referencia Animal, objeto Gato
Animal a3 = new Pato();    // referencia Animal, objeto Pato

// El mismo codigo llama a hacerSonido() en todos, pero cada uno responde diferente
a1.hacerSonido();   // "Guau"  — Java sabe que el objeto real es Perro
a2.hacerSonido();   // "Miau"  — Java sabe que el objeto real es Gato
a3.hacerSonido();   // "Cuac"  — Java sabe que el objeto real es Pato
```

La decision de que metodo ejecutar se toma en **tiempo de ejecucion** segun el tipo real del objeto, no el tipo de la referencia. A esto se le llama **dynamic dispatch**.

### Polimorfismo con listas

El poder real del polimorfismo aparece cuando mezclas tipos diferentes en una coleccion:

```java
ArrayList<Animal> animales = new ArrayList<>();
animales.add(new Perro());
animales.add(new Gato());
animales.add(new Pato());
animales.add(new Perro());

// Un solo bucle, cada animal responde con su propio comportamiento
for (Animal a : animales) {
    a.hacerSonido();
}
// Guau
// Miau
// Cuac
// Guau

// Si mañana añadimos Loro, solo creamos la clase — el bucle funciona igual
animales.add(new Loro());   // "Habla"
```

### Sobreescritura vs Sobrecarga — comparativa

| Aspecto             | Sobreescritura (Override)     | Sobrecarga (Overload)         |
|---------------------|-------------------------------|-------------------------------|
| Donde ocurre        | Entre clase padre e hija      | Dentro de la misma clase      |
| Nombre del metodo   | Identico                      | Identico                      |
| Parametros          | Identicos                     | Distintos (tipo o cantidad)   |
| Tipo de retorno     | Igual o compatible            | Puede ser diferente           |
| Cuando se decide    | En ejecucion (dinamico)       | En compilacion (estatico)     |
| Anotacion           | @Override (recomendado)       | No aplica                     |

### Ventajas del polimorfismo

- Escribe codigo **general** que funciona con muchos tipos distintos
- Anadir un nuevo tipo no requiere cambiar el codigo existente
- Facilita el mantenimiento y la extension del sistema

---

## Como se relacionan los 4 pilares

Los pilares no son conceptos aislados, trabajan juntos:

```
Encapsulacion  ->  protege el estado interno de cada objeto
       |
Abstraccion    ->  define contratos (que hace), oculta el como
       |
Herencia       ->  reutiliza e extiende comportamiento entre clases
       |
Polimorfismo   ->  permite tratar objetos distintos de forma uniforme
```

**Ejemplo de los 4 pilares juntos** — extraido del GestorDietas de esta practica:

```java
// ABSTRACCION: clase abstracta con metodo abstracto
// ENCAPSULACION: atributos private/protected, acceso solo por metodos
public abstract class Usuario {
    protected String nombre;              // encapsulacion: protected, no public
    private ArrayList<Receta> historial;  // encapsulacion: nadie accede directamente

    public void aniadirReceta(Receta r) { historial.add(r); }   // metodo controlado

    public abstract int evaluarReceta(Receta r);  // abstraccion: obliga a las hijas
}

// HERENCIA: reutiliza todo lo de Usuario sin repetir codigo
public class Deportista extends Usuario {
    private double minProteinas;           // encapsulacion: estado propio del deportista

    @Override
    public int evaluarReceta(Receta r) {   // sobreescritura: implementacion propia
        // formula del deportista: 50 + 2*prot - 0.5*grasas - 0.3*|kcal-500|
        return ...;
    }
}

public class Paciente extends Usuario {
    private int maxKcal;

    @Override
    public int evaluarReceta(Receta r) {   // sobreescritura: formula del paciente
        // formula del paciente: 100 - 0.2*kcal - 1.5*grasas + 0.8*prot
        return ...;
    }
}

// POLIMORFISMO: el mismo codigo evalua usuarios de distintos tipos
ArrayList<Usuario> usuarios = new ArrayList<>();
usuarios.add(new Deportista("Juan", "U001", 25));
usuarios.add(new Paciente("Ana",  "U002", 400));
usuarios.add(new UsuarioKeto("Maria", "U003", 20));

for (Usuario u : usuarios) {
    int puntuacion = u.evaluarReceta(receta);   // cada uno aplica su formula
    System.out.println(u.getNombre() + ": " + puntuacion);
}
```

---

## Resumen final

| Pilar          | Pregunta que responde              | Como se implementa                      |
|----------------|------------------------------------|-----------------------------------------|
| Encapsulacion  | Como protejo mis datos?            | `private` + getters/setters             |
| Abstraccion    | Que hace sin explicar el como?     | `abstract class` / `interface`          |
| Herencia       | Como reutilizo codigo entre clases?| `extends` + `super`                     |
| Polimorfismo   | Como trato distintos tipos igual?  | `@Override` + referencias de tipo padre |

> Dominar estos 4 pilares es la base para escribir codigo orientado a objetos de calidad: organizado, reutilizable y facil de mantener y ampliar.
