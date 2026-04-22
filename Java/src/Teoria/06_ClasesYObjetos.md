# 06 - Clases y Objetos

## ¿Que es una clase?

Una **clase** es una **plantilla** (molde) que define:
- **Atributos**: los datos que tendra cada objeto (variables)
- **Metodos**: las acciones que puede realizar (funciones)

## ¿Que es un objeto?

Un **objeto** es una **instancia concreta** creada a partir de una clase.
La clase es el plano; el objeto es la casa construida con ese plano.

```
Clase Persona (plantilla)     →  Objeto "Ana" (instancia concreta)
- nombre: String              →  nombre = "Ana"
- edad: int                   →  edad = 25
- saludar()                   →  "Hola, soy Ana"
```

---

## Estructura de una clase

```java
public class Persona {

    // 1. ATRIBUTOS (estado del objeto)
    private String nombre;
    private int edad;

    // 2. CONSTRUCTOR (como se crea el objeto)
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad   = edad;
    }

    // 3. METODOS (comportamiento del objeto)
    public void saludar() {
        System.out.println("Hola, soy " + nombre);
    }

    // 4. GETTERS Y SETTERS (acceso controlado a atributos privados)
    public String getNombre() { return nombre; }
    public int getEdad()      { return edad; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    // 5. toString (representacion como texto)
    @Override
    public String toString() {
        return "Persona{nombre=" + nombre + ", edad=" + edad + "}";
    }
}
```

---

## Constructores

El constructor es un **metodo especial** que se llama automaticamente al crear el objeto con `new`.

### Reglas del constructor
- Mismo nombre que la clase (exacto, mayusculas incluidas)
- No tiene tipo de retorno (ni siquiera `void`)
- Puede lanzar excepciones

```java
// Constructor sin parametros (por defecto)
public Persona() {
    this.nombre = "Desconocido";
    this.edad   = 0;
}

// Constructor con parametros
public Persona(String nombre, int edad) {
    this.nombre = nombre;
    this.edad   = edad;
}

// Uso:
Persona p1 = new Persona();            // llama al constructor sin params
Persona p2 = new Persona("Ana", 25);   // llama al constructor con params
```

### Encadenamiento de constructores con `this(...)`

Un constructor puede llamar a otro de la misma clase con `this(...)`.
Debe ser la **primera linea** del constructor.

```java
public Persona(String nombre) {
    this(nombre, 0);   // llama al constructor (String, int) con edad=0
}

public Persona(String nombre, int edad) {
    this.nombre = nombre;
    this.edad   = edad;
}
```

### Si no defines ningun constructor

Java crea automaticamente un **constructor por defecto** (sin parametros, vacio).
Pero en cuanto defines uno propio, Java **deja de crearlo**. Si quieres uno sin parametros ademas, debes escribirlo tu.

---

## La palabra clave `this`

`this` hace referencia al **objeto actual** (la instancia sobre la que se esta ejecutando el metodo).

```java
public class Persona {
    private String nombre;  // atributo

    // Sin this: ambiguedad — ¿cual es el parametro y cual el atributo?
    public void setNombreMal(String nombre) {
        nombre = nombre;    // NO hace nada: asigna el parametro a si mismo
    }

    // Con this: distinguimos atributo (this.nombre) del parametro (nombre)
    public void setNombre(String nombre) {
        this.nombre = nombre;   // asigna el parametro al atributo del objeto
    }

    // this en metodos: pasar el objeto actual como argumento
    public void registrarse(Sistema s) {
        s.registrar(this);   // pasa este objeto al sistema
    }
}
```

---

## Modificador `static`

`static` significa que algo **pertenece a la CLASE**, no a un objeto concreto.

```java
public class Contador {
    // Atributo estatico: compartido por TODOS los objetos de la clase
    private static int totalCreados = 0;

    // Atributo de instancia: cada objeto tiene el suyo
    private int id;

    public Contador() {
        totalCreados++;          // suma 1 al contador global
        this.id = totalCreados;  // asigna el id a este objeto
    }

    // Metodo estatico: se puede llamar sin crear un objeto
    public static int getTotalCreados() {
        return totalCreados;
    }

    // Metodo de instancia: necesita un objeto
    public int getId() {
        return id;
    }
}

// Uso:
Contador c1 = new Contador();    // totalCreados = 1, c1.id = 1
Contador c2 = new Contador();    // totalCreados = 2, c2.id = 2

System.out.println(Contador.getTotalCreados());  // 2 (sin crear objeto)
System.out.println(c1.getId());                  // 1
```

> Los metodos estaticos **no pueden usar `this`** ni acceder a atributos de instancia directamente (porque no hay un objeto concreto).

---

## Modificador `final`

| Contexto     | Efecto de `final`                                      |
|--------------|--------------------------------------------------------|
| Variable     | Constante: su valor no puede cambiar                   |
| Atributo     | Debe asignarse en el constructor y no puede cambiar    |
| Metodo       | No puede ser sobreescrito en subclases                 |
| Clase        | No puede ser extendida (sin herencia posible)          |

```java
public class Circulo {
    private final double radio;   // atributo final: se asigna en constructor y ya no cambia

    public Circulo(double radio) {
        this.radio = radio;   // unica asignacion posible
    }

    // radio = 5.0;  // ERROR si intentas cambiarlo despues

    public final double calcularArea() {   // metodo que no se puede sobreescribir
        return Math.PI * radio * radio;
    }
}
```

---

## Creacion de objetos y memoria

```java
Persona p = new Persona("Ana", 25);
//       ↑        ↑
//   referencia   objeto en el heap (memoria dinamica)
```

- `p` es una **referencia** (como una direccion de memoria) almacenada en el stack
- El objeto real (`Persona("Ana", 25)`) esta en el **heap**
- Si haces `Persona q = p;`, `q` apunta al **mismo objeto** — no es una copia

```java
Persona p = new Persona("Ana", 25);
Persona q = p;    // q y p apuntan al mismo objeto
q.setNombre("Luis");
System.out.println(p.getNombre());  // "Luis" — porque son el mismo objeto
```

- Cuando ya no hay referencias a un objeto, el **Garbage Collector** de Java lo elimina automaticamente

---

## Encapsulacion en practica

```java
public class CuentaBancaria {
    private double saldo;     // private → solo accesible desde dentro de la clase

    public CuentaBancaria(double saldoInicial) {
        if (saldoInicial < 0) throw new IllegalArgumentException("Saldo negativo");
        this.saldo = saldoInicial;
    }

    // getter: permite leer pero no escribir directamente
    public double getSaldo() {
        return saldo;
    }

    // En lugar de un setter de saldo, exponemos operaciones con logica
    public void depositar(double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad invalida");
        saldo += cantidad;
    }

    public void retirar(double cantidad) {
        if (cantidad <= 0 || cantidad > saldo) throw new IllegalArgumentException("No puedes retirar");
        saldo -= cantidad;
    }
}

// Uso: el usuario de la clase no puede hacer saldo = -100 directamente
CuentaBancaria cuenta = new CuentaBancaria(1000);
cuenta.depositar(500);   // saldo = 1500
cuenta.retirar(200);     // saldo = 1300
// cuenta.saldo = -100;  // ERROR: saldo es private
```

---

## Resumen

```
Clase   = plantilla (atributos + metodos)
Objeto  = instancia creada con new

Constructor → mismo nombre que clase, sin tipo retorno, se llama con new
this        → referencia al objeto actual
static      → pertenece a la clase (no al objeto)
final       → no cambia (constante) / no se hereda / no se sobreescribe
private     → solo accesible dentro de la clase (encapsulacion)
```
