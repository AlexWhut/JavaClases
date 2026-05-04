# Enum (Enumeraciones)

Un **enum** es un tipo especial de clase que representa un conjunto fijo de constantes con nombre.

Cuando un valor solo puede ser uno de un grupo cerrado de opciones (dias de la semana, estados de un pedido, tipos de producto), un enum es la herramienta correcta.

---

## El problema que resuelve

Sin enum, podrías usar enteros o Strings para representar opciones:

```java
// Mal: usar enteros
int tipo = 1;   // ¿Que significa 1? ¿Raton? ¿Teclado? No esta claro.

// Mal: usar Strings
String tipo = "RATON";   // ¿Y si alguien escribe "raton" o "Raton"? Error silencioso.
```

Con enum, el compilador **solo acepta los valores que tú definiste**. Un error tipográfico es error de compilacion, no un bug en ejecucion:

```java
// Bien: usar enum
TipoProducto tipo = TipoProducto.RATON;   // claro, seguro, el compilador lo verifica
```

---

## Declaracion basica

```java
public enum TipoProducto {
    RATON,
    TECLADO,
    PANTALLA,
    GRAFICA
}
```

Cada valor (RATON, TECLADO...) es una **constante publica y estatica** de tipo `TipoProducto`.
Por convencion se escriben en MAYUSCULAS.

---

## Usar un enum

```java
// Declarar una variable de tipo enum
TipoProducto tipo = TipoProducto.TECLADO;

// Comparar con ==  (los enums son singletons, == es correcto)
if (tipo == TipoProducto.TECLADO) {
    System.out.println("Es un teclado");
}

// Usar en switch (muy comun)
switch (tipo) {
    case RATON:
        System.out.println("Periferico de puntero");
        break;
    case TECLADO:
        System.out.println("Periferico de entrada");
        break;
    case PANTALLA:
        System.out.println("Dispositivo de salida");
        break;
    case GRAFICA:
        System.out.println("Componente interno");
        break;
}
```

---

## Metodos que todo enum tiene

Java da estos metodos automaticamente a cualquier enum:

```java
// name() → devuelve el nombre de la constante como String
TipoProducto.RATON.name()        // "RATON"

// ordinal() → posicion en la declaracion (empieza en 0)
TipoProducto.RATON.ordinal()     // 0
TipoProducto.TECLADO.ordinal()   // 1
TipoProducto.PANTALLA.ordinal()  // 2
TipoProducto.GRAFICA.ordinal()   // 3

// values() → array con todos los valores del enum
TipoProducto[] todos = TipoProducto.values();
for (TipoProducto t : todos) {
    System.out.println(t);   // imprime: RATON, TECLADO, PANTALLA, GRAFICA
}

// valueOf() → convierte un String al valor del enum correspondiente
TipoProducto t = TipoProducto.valueOf("RATON");   // TipoProducto.RATON
// CUIDADO: lanza IllegalArgumentException si el String no coincide exactamente
```

---

## Enum con atributos y constructor

Un enum puede tener atributos propios, constructor y metodos como cualquier clase.

```java
public enum DiaSemana {

    LUNES("Laborable"),
    MARTES("Laborable"),
    MIERCOLES("Laborable"),
    JUEVES("Laborable"),
    VIERNES("Laborable"),
    SABADO("Fin de semana"),
    DOMINGO("Fin de semana");

    // Atributo propio de cada constante
    private String tipo;

    // Constructor (siempre privado en un enum)
    DiaSemana(String tipo) {
        this.tipo = tipo;
    }

    // Getter
    public String getTipo() {
        return tipo;
    }
}
```

```java
// Uso:
DiaSemana hoy = DiaSemana.SABADO;
System.out.println(hoy.getTipo());   // "Fin de semana"
System.out.println(hoy.name());      // "SABADO"

// Recorrer todos con sus atributos
for (DiaSemana d : DiaSemana.values()) {
    System.out.println(d + " → " + d.getTipo());
}
// LUNES → Laborable
// MARTES → Laborable
// ...
// SABADO → Fin de semana
```

---

## Enum con metodo abstracto

Cada constante puede implementar su propia version de un metodo:

```java
public enum Operacion {

    SUMA {
        @Override
        public double calcular(double a, double b) { return a + b; }
    },
    RESTA {
        @Override
        public double calcular(double a, double b) { return a - b; }
    },
    MULTIPLICACION {
        @Override
        public double calcular(double a, double b) { return a * b; }
    };

    // Metodo abstracto que cada constante debe implementar
    public abstract double calcular(double a, double b);
}
```

```java
// Uso: polimorfismo dentro del propio enum
Operacion op = Operacion.SUMA;
System.out.println(op.calcular(5, 3));   // 8.0

op = Operacion.RESTA;
System.out.println(op.calcular(5, 3));   // 2.0
```

---

## Enum como atributo de una clase

Es muy comun usar un enum como tipo de un atributo de clase:

```java
public abstract class Producto {
    protected String nombre;
    protected double precio;
    protected TipoProducto tipo;   // <-- atributo de tipo enum

    public Producto(String nombre, double precio, TipoProducto tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo   = tipo;
    }
}

// Las subclases pasan el valor del enum al super()
public class Raton extends Producto {
    public Raton(String nombre, double precio, int stock, boolean inalambrico) {
        super(nombre, precio, TipoProducto.RATON);   // fija el tipo para siempre
        this.inalambrico = inalambrico;
    }
}
```

Ahora puedes filtrar una coleccion de productos por tipo:

```java
ArrayList<Producto> catalogo = ...;

// Contar cuantos ratones hay
int totalRatones = 0;
for (Producto p : catalogo) {
    if (p.getTipo() == TipoProducto.RATON) {   // comparacion segura con ==
        totalRatones++;
    }
}
```

---

## Comparacion: constantes vs enum

```java
// Antes de los enums: constantes enteras (estilo C)
public class Direccion {
    public static final int NORTE = 0;
    public static final int SUR   = 1;
    public static final int ESTE  = 2;
    public static final int OESTE = 3;
}
// Problema: nada impide hacer   int d = 99;   y el compilador no avisa

// Con enum:
public enum Direccion { NORTE, SUR, ESTE, OESTE }
// Ahora:  Direccion d = 99;   → ERROR de compilacion. Imposible tener valor invalido.
```

| | Constantes int | Enum |
|---|---|---|
| El compilador verifica los valores | No | Si |
| Tiene metodos propios | No | Si |
| Funciona en switch | Si | Si |
| Se puede iterar con values() | No | Si |
| Legibilidad en depuracion | Muestra "2" | Muestra "ESTE" |

---

## Resumen

```
// Declaracion
public enum NombreEnum { VALOR1, VALOR2, VALOR3 }

// Uso
NombreEnum v = NombreEnum.VALOR1;

// Comparacion
v == NombreEnum.VALOR1          // true

// Metodos automaticos
v.name()                        // "VALOR1"
v.ordinal()                     // posicion (0, 1, 2...)
NombreEnum.values()             // array con todos los valores
NombreEnum.valueOf("VALOR1")    // convierte String a enum

// Enum con atributos
public enum MiEnum {
    A("descripcion A"),
    B("descripcion B");

    private String descripcion;
    MiEnum(String d) { this.descripcion = d; }
    public String getDescripcion() { return descripcion; }
}
```

> Un enum es una clase: puede tener atributos, constructor, metodos e incluso implementar interfaces.
> Usalo siempre que un valor solo pueda ser uno de un conjunto cerrado y conocido de opciones.
