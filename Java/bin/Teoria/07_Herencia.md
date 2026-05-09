# 07 - Herencia

La **herencia** es el mecanismo por el que una clase (hija/subclase) puede **reutilizar** atributos y metodos de otra clase (padre/superclase), y ademas **extender** o **modificar** su comportamiento.

En Java se usa la palabra clave `extends`.

---

## ¿Por que herencia?

Sin herencia, si tienes `Perro`, `Gato` y `Pajaro`, duplicarias codigo:

```java
// Sin herencia: codigo repetido en cada clase
class Perro {
    String nombre;
    int edad;
    void comer() { System.out.println(nombre + " come"); }
    void hacerSonido() { System.out.println("Guau"); }
}

class Gato {
    String nombre;   // duplicado
    int edad;        // duplicado
    void comer() { System.out.println(nombre + " come"); }  // duplicado
    void hacerSonido() { System.out.println("Miau"); }
}
```

Con herencia, el codigo comun va en `Animal` y cada subclase solo define lo que la diferencia:

```java
class Animal {
    String nombre;
    int edad;
    void comer() { System.out.println(nombre + " come"); }
    void hacerSonido() { System.out.println("..."); }   // comportamiento generico
}

class Perro extends Animal {
    @Override
    void hacerSonido() { System.out.println("Guau"); }   // solo lo diferente
}

class Gato extends Animal {
    @Override
    void hacerSonido() { System.out.println("Miau"); }
}
```

---

## La palabra clave `super`

`super` hace referencia a la **clase padre**. Tiene dos usos principales:

### 1. Llamar al constructor del padre

Debe ser la **primera linea** del constructor hijo.
Si no la pones, Java intenta llamar automaticamente a `super()` (sin parametros).

```java
class Animal {
    protected String nombre;
    protected int edad;

    public Animal(String nombre, int edad) {
        this.nombre = nombre;
        this.edad   = edad;
    }
}

class Perro extends Animal {
    private String raza;

    public Perro(String nombre, int edad, String raza) {
        super(nombre, edad);  // PRIMERO: inicializa la parte heredada de Animal
        this.raza = raza;     // LUEGO: inicializa lo propio de Perro
    }
}
```

### 2. Llamar a un metodo del padre

```java
class Animal {
    public String describir() {
        return nombre + " (edad: " + edad + ")";
    }
}

class Perro extends Animal {
    @Override
    public String describir() {
        // Reutilizamos la descripcion del padre y añadimos la raza
        return super.describir() + " [raza: " + raza + "]";
    }
}
```

---

## Sobreescritura de metodos (`@Override`)

Sobreescribir (override) es redefinir en la subclase un metodo heredado del padre.

```java
class Animal {
    public void hacerSonido() {
        System.out.println("Sonido generico");
    }
}

class Perro extends Animal {
    @Override                           // ← anotacion (no obligatoria, pero recomendada)
    public void hacerSonido() {
        System.out.println("Guau");     // nueva implementacion
    }
}
```

### ¿Por que poner `@Override`?

- El compilador **verifica** que realmente estas sobreescribiendo algo del padre
- Si te equivocas en el nombre (`hacerSonidoO` en lugar de `hacerSonido`), el compilador te avisa
- Hace el codigo mas facil de entender

### Reglas de sobreescritura

```java
class Padre {
    public void metodo() { }
    protected void otro() { }
}

class Hijo extends Padre {
    @Override
    public void metodo() { }     // OK: mismo modificador o mas permisivo

    @Override
    public void otro() { }       // OK: protected → public (mas permisivo)

    // PROHIBIDO: no puedes hacer el metodo mas restrictivo
    // @Override
    // private void metodo() { } // ERROR: public → private
}
```

---

## Modificadores de acceso y herencia

| Modificador | Misma clase | Subclase | Mismo paquete | Otra clase |
|-------------|:-----------:|:--------:|:-------------:|:----------:|
| `public`    | ✓           | ✓        | ✓             | ✓          |
| `protected` | ✓           | ✓        | ✓             | ✗          |
| (paquete)   | ✓           | ✗        | ✓             | ✗          |
| `private`   | ✓           | ✗        | ✗             | ✗          |

> **Regla practica para herencia:**
> - Atributos: `private` con getters/setters, o `protected` si la subclase los necesita directamente
> - Metodos que las subclases deben heredar: `public` o `protected`

---

## Jerarquia y cadena de herencia

Java permite herencia en cadena (una subclase puede ser superclase de otra):

```
Object (toda clase hereda de Object implicitamente)
  └── Animal
        ├── Perro
        │     └── Labrador
        └── Gato
```

```java
class Labrador extends Perro {
    // hereda de Perro y de Animal
}
```

> Java solo permite **herencia simple**: una clase solo puede extender una sola clase.
> (Para heredar "comportamiento" de varias fuentes, se usan interfaces — ver [08](08_ClasesAbstractasInterfaces.md))

---

## Polimorfismo y referencias

Una referencia de tipo padre puede apuntar a un objeto hijo:

```java
Animal a = new Perro("Rex", 3, "Pastor");   // referencia Animal, objeto Perro

a.hacerSonido();    // llama a hacerSonido() de PERRO (no de Animal)
                    // → "Guau"
```

Esto es el **polimorfismo**: el metodo que se ejecuta depende del tipo real del objeto, no del tipo de la referencia.

```java
// Lista de animales mixta
Animal[] animales = {
    new Perro("Rex", 3, "Pastor"),
    new Gato("Misu", 2),
    new Perro("Toby", 5, "Labrador")
};

for (Animal a : animales) {
    a.hacerSonido();   // cada uno hace su propio sonido (polimorfismo)
}
// Guau
// Miau
// Guau
```

---

## Casting entre clases relacionadas

### Upcasting (siempre seguro, implicito)

De hijo a padre: automatico, no hay riesgo.

```java
Perro p = new Perro("Rex", 3, "Pastor");
Animal a = p;    // upcasting implicito: Perro → Animal
```

### Downcasting (puede fallar, explicito)

De padre a hijo: hay que indicarlo explicitamente y puede lanzar `ClassCastException`.

```java
Animal a = new Perro("Rex", 3, "Pastor");
Perro p = (Perro) a;    // downcasting: le dices al compilador "confía en mi"

Animal a2 = new Gato("Misu", 2);
Perro p2 = (Perro) a2;  // EXCEPCION en tiempo de ejecucion: un Gato no es un Perro
```

Siempre comprueba con `instanceof` antes de hacer downcasting:

```java
if (a instanceof Perro p) {
    p.ladrar();    // seguro: ya sabemos que es un Perro
}
```

---

## La clase `Object`

Toda clase en Java extiende implicitamente de `Object`. Por eso todos los objetos tienen estos metodos:

| Metodo             | Descripcion                                   |
|--------------------|-----------------------------------------------|
| `toString()`       | Representacion en texto del objeto            |
| `equals(Object o)` | Comparacion de contenido (hay que sobreescribir) |
| `hashCode()`       | Codigo hash (related con equals)              |
| `getClass()`       | Devuelve la clase del objeto                  |

```java
Object obj = new Perro("Rex", 3, "Pastor");
System.out.println(obj.getClass().getName());  // "Perro"
System.out.println(obj.toString());             // llama al toString de Perro
```

---

## Resumen

```
extends     → herencia de clase
super(...)  → llama al constructor del padre (primera linea)
super.xxx() → llama al metodo del padre
@Override   → sobreescribe un metodo heredado

Upcasting:   Padre ref = new Hijo()  → automatico, siempre seguro
Downcasting: Hijo ref = (Hijo) padre → manual, puede fallar → usar instanceof

Java: herencia SIMPLE (solo un padre)
Todo hereda de Object
```
