# Diagramas de Clases UML

Los **diagramas de clases** son la forma visual de representar la estructura de un programa orientado a objetos: qué clases existen, qué atributos y métodos tienen, y cómo se relacionan entre sí.

Son parte del estándar **UML** (Unified Modeling Language).

---

## Anatomía de una clase en UML

Cada clase se dibuja como un rectángulo con tres secciones:

```
┌──────────────────────┐
│      NombreClase     │  ← Nombre (negrita, centrado)
├──────────────────────┤
│  - atributo: tipo    │  ← Atributos (campos)
│  - otro: tipo        │
├──────────────────────┤
│  + metodo(): tipo    │  ← Métodos
│  + otro(p: tipo)     │
└──────────────────────┘
```

### Visibilidad (modificadores de acceso)

| Símbolo | Significado  | Java equivalente |
|---------|-------------|------------------|
| `+`     | público     | `public`         |
| `-`     | privado     | `private`        |
| `#`     | protegido   | `protected`      |
| `~`     | paquete     | *(sin modificador)* |

### Ejemplo

```
┌────────────────────────────┐
│           Persona          │
├────────────────────────────┤
│  - nombre: String          │
│  - edad: int               │
├────────────────────────────┤
│  + getNombre(): String     │
│  + saludar(): void         │
└────────────────────────────┘
```

```java
public class Persona {
    private String nombre;
    private int edad;

    public String getNombre() { return nombre; }
    public void saludar() { System.out.println("Hola, soy " + nombre); }
}
```

---

## Tipos de relaciones entre clases

Existen varias formas en que una clase puede relacionarse con otra. La diferencia clave está en **quién crea a quién** y **quién depende de quién para existir**.

---

## 1. Herencia (Generalización)

> "ES UN" — una clase hija **es un tipo** de la clase padre.

```
        Animal
           ▲
     ______|______
    |             |
  Perro          Gato
```

En UML se representa con una **flecha de punta triangular hueca** que apunta al padre.

```java
class Perro extends Animal { ... }
class Gato extends Animal { ... }
```

**Cuándo usarla:** cuando existe una jerarquía real de tipos. `Perro` ES UN `Animal`.

---

## 2. Implementación (Realización)

> Una clase **implementa** una interfaz.

Similar a herencia pero con línea discontinua y punta triangular hueca.

```
  <<interface>>
    Volador
       ▲
   - - - - -
       |
    Pajaro
```

```java
interface Volador { void volar(); }
class Pajaro implements Volador { ... }
```

---

## 3. Asociación

> "USA UN" — una clase **conoce** a otra y puede llamar a sus métodos.

Se representa con una **línea simple** (con o sin flecha según si es unidireccional o bidireccional).

```
  Profesor ──────────── Alumno
```

```java
class Profesor {
    Alumno alumno;   // conoce a Alumno, pero no lo crea ni destruye
}
```

**Clave:** la relación es débil, los objetos son independientes entre sí.

---

## 4. Dependencia

> "DEPENDE DE" — una clase usa a otra **temporalmente** (por ejemplo, como parámetro de un método).

Se representa con una **línea discontinua con flecha**.

```
  Calculadora - - - -> Numero
```

```java
class Calculadora {
    public int sumar(Numero a, Numero b) { ... }   // Numero aparece solo en el método
}
```

**Clave:** la dependencia es la relación más débil. `Calculadora` no guarda ningún `Numero`.

---

## 5. Agregación

> "TIENE UN" — una clase contiene a otra, pero ambas pueden **existir de forma independiente**.

Se representa con una **línea con rombo hueco** en el lado del contenedor.

```
  Universidad ◇──────── Estudiante
```

```java
class Universidad {
    List<Estudiante> estudiantes;   // los estudiantes existen fuera de la universidad

    public void agregar(Estudiante e) { estudiantes.add(e); }
}
```

**Clave:** si la `Universidad` desaparece, los `Estudiante` siguen existiendo. Son objetos que se **pasan desde fuera**.

### Ejemplo real

```java
public class Departamento {
    private String nombre;
    private List<Empleado> empleados;   // agregación

    public Departamento(String nombre) {
        this.nombre = nombre;
        this.empleados = new ArrayList<>();
    }

    public void contratar(Empleado e) {   // el Empleado se crea fuera y se pasa
        empleados.add(e);
    }
}

// Uso:
Empleado emp = new Empleado("Ana");   // creado fuera del Departamento
Departamento dep = new Departamento("IT");
dep.contratar(emp);   // emp sigue existiendo si dep se destruye
```

---

## 6. Composición

> "PARTE DE" — una clase contiene a otra y la **parte no puede existir sin el todo**.

Se representa con una **línea con rombo relleno** en el lado del contenedor.

```
  Casa ◆──────── Habitacion
```

```java
class Casa {
    private Habitacion salon;
    private Habitacion cocina;

    public Casa() {
        salon = new Habitacion("salón");     // Casa crea sus propias Habitaciones
        cocina = new Habitacion("cocina");   // si Casa muere, Habitacion también
    }
}
```

**Clave:** el contenedor **crea** las partes en su constructor. Si el objeto padre es destruido, las partes también lo son.

### Ejemplo real

```java
public class Pedido {
    private List<LineaPedido> lineas;   // composición

    public Pedido() {
        this.lineas = new ArrayList<>();
    }

    public void agregarProducto(String producto, int cantidad) {
        lineas.add(new LineaPedido(producto, cantidad));   // Pedido crea sus propias líneas
    }
}

// LineaPedido no tiene sentido sin Pedido — no se crea fuera de él
```

---

## Agregación vs Composición — La diferencia clave

| Característica         | Agregación ◇                       | Composición ◆                        |
|------------------------|-------------------------------------|--------------------------------------|
| ¿Quién crea la parte?  | Se crea **fuera** y se pasa         | El contenedor la **crea internamente** |
| Ciclo de vida          | Independiente                       | La parte muere con el todo            |
| Relación               | "tiene una referencia a"            | "es dueño de"                         |
| Ejemplo                | Universidad → Estudiante            | Casa → Habitación                     |
| En código              | Se recibe por parámetro / setter    | Se instancia con `new` en constructor |

---

## Multiplicidad

Indica **cuántos** objetos de cada lado participan en la relación.

```
  Profesor 1──────────* Alumno
```

| Notación | Significado             |
|----------|------------------------|
| `1`      | exactamente uno         |
| `0..1`   | cero o uno (opcional)   |
| `*`      | cero o más             |
| `1..*`   | uno o más              |
| `2..5`   | entre dos y cinco      |

```
  Empresa 1 ◇──────── * Empleado
  (una empresa tiene muchos empleados)

  Persona 1 ◆──────── 2 Mano
  (una persona tiene exactamente dos manos)
```

---

## Diagrama completo — Ejemplo

Un sistema de gestión de un colegio:

```
        <<interface>>
          Evaluable
              ▲
          - - - - -
              |
┌─────────────────────────┐
│         Alumno          │
├─────────────────────────┤
│  - nombre: String       │
│  - matricula: int       │
├─────────────────────────┤
│  + evaluar(): double    │
└─────────────────────────┘
           *
           |   (agregación — alumnos existen fuera del curso)
           ◇
┌─────────────────────────┐       ◆ 1..*     ┌──────────────────┐
│         Curso           │──────────────────│    Modulo        │
├─────────────────────────┤  (composición)   ├──────────────────┤
│  - nombre: String       │                  │  - titulo: String│
│  - codigo: String       │                  ├──────────────────┤
├─────────────────────────┤                  │  + getDuracion() │
│  + agregar(a: Alumno)   │                  └──────────────────┘
│  + iniciar(): void      │
└─────────────────────────┘
           |
           ▼ (asociación — Curso conoce al Profesor)
┌─────────────────────────┐
│        Profesor         │
├─────────────────────────┤
│  - nombre: String       │
│  - especialidad: String │
├─────────────────────────┤
│  + impartir(): void     │
└─────────────────────────┘
```

- `Curso` **agrega** `Alumno` — los alumnos existen fuera del curso.
- `Curso` **compone** `Modulo` — los módulos no tienen sentido sin el curso.
- `Curso` **asocia** `Profesor` — el curso conoce al profesor pero no lo posee.
- `Alumno` **implementa** `Evaluable` — cumple el contrato de la interfaz.

---

## Resumen visual de todas las relaciones

```
A ──────────────── B    Asociación       (A usa/conoce a B)
A - - - - - - - -> B    Dependencia      (A depende de B temporalmente)
A ◇──────────────── B    Agregación       (A tiene B, B puede existir solo)
A ◆──────────────── B    Composición      (A posee B, B no existe sin A)
A ────────────────▷ B    Herencia         (A es un B)
A - - - - - - - -▷ B    Realización      (A implementa B)
```

> **Regla práctica:** pregúntate siempre "¿puede la parte existir sin el todo?".  
> Si sí → **Agregación**. Si no → **Composición**.
