# Random — Numeros aleatorios en Java

Java ofrece varias formas de generar numeros aleatorios. La mas comun es la clase `Random` del paquete `java.util`.

---

## Importar y crear un objeto Random

```java
import java.util.Random;

Random rand = new Random();  // genera semilla automaticamente
```

Cada vez que ejecutas el programa, la semilla cambia y obtienes secuencias distintas.

---

## Metodos principales

### `nextInt()`

```java
Random rand = new Random();

int n1 = rand.nextInt();        // cualquier int (positivo o negativo)
int n2 = rand.nextInt(10);      // entre 0 y 9  (0 incluido, 10 excluido)
int n3 = rand.nextInt(6) + 1;   // entre 1 y 6  (simula un dado)
```

> El parametro de `nextInt(n)` es el **limite superior excluido**. Siempre empieza en 0.

### `nextDouble()` y `nextFloat()`

```java
double d = rand.nextDouble();   // entre 0.0 (incluido) y 1.0 (excluido)
float  f = rand.nextFloat();    // igual, pero de tipo float
```

Para obtener un decimal en otro rango:

```java
// entre 0.0 y 10.0
double aleatorio = rand.nextDouble() * 10;

// entre 5.0 y 15.0
double aleatorio2 = 5 + rand.nextDouble() * 10;
```

### `nextBoolean()`

```java
boolean resultado = rand.nextBoolean();  // true o false con 50% de probabilidad
```

### `nextLong()`

```java
long l = rand.nextLong();  // cualquier long (rango muy grande)
```

---

## Generar un numero en un rango concreto

Formula general para obtener un entero entre `min` y `max` (ambos incluidos):

```java
int min = 10;
int max = 20;
int aleatorio = min + rand.nextInt(max - min + 1);
// min + rand.nextInt(11) → entre 10 y 20
```

Ejemplo practico — dado de 20 caras:

```java
int dado20 = 1 + rand.nextInt(20);  // entre 1 y 20
System.out.println("Sacaste: " + dado20);
```

---

## Semilla fija — resultados reproducibles

Si pasas una semilla al constructor, la secuencia de numeros sera **siempre la misma**. Util para pruebas y debugging.

```java
Random rand = new Random(42);  // semilla fija

System.out.println(rand.nextInt(100));  // siempre el mismo numero
System.out.println(rand.nextInt(100));  // siempre el mismo numero
// ejecuta el programa varias veces: los valores no cambian
```

Sin semilla: resultados distintos cada vez.  
Con semilla: resultados identicos en cada ejecucion.

---

## `Math.random()` — alternativa rapida

`Math.random()` es un metodo estatico que devuelve un `double` entre 0.0 y 1.0 sin necesidad de crear un objeto.

```java
double d = Math.random();               // entre 0.0 y 1.0

// entero entre 0 y 9
int n = (int)(Math.random() * 10);

// entero entre 1 y 6 (dado)
int dado = (int)(Math.random() * 6) + 1;
```

> Internamente usa un `Random` compartido. Para usos simples es suficiente; para control fino usa `new Random()`.

---

## `Random` con Java 8+ — `nextInt` con rango

Desde Java 8, `nextInt` acepta un rango directo con `origin` y `bound`:

```java
// entre 5 y 15 (5 incluido, 15 excluido)
int n = rand.nextInt(5, 15);    // equivale a: 5 + rand.nextInt(10)
```

---

## Elegir un elemento aleatorio de un array o lista

```java
String[] opciones = {"piedra", "papel", "tijera"};
Random rand = new Random();

String eleccion = opciones[rand.nextInt(opciones.length)];
System.out.println("El ordenador eligio: " + eleccion);
```

Con `ArrayList`:

```java
ArrayList<String> lista = new ArrayList<>();
lista.add("rojo");
lista.add("verde");
lista.add("azul");

String color = lista.get(rand.nextInt(lista.size()));
```

---

## Mezclar un array — Fisher-Yates shuffle

```java
int[] nums = {1, 2, 3, 4, 5};
Random rand = new Random();

for (int i = nums.length - 1; i > 0; i--) {
    int j = rand.nextInt(i + 1);     // indice aleatorio entre 0 e i
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
// nums esta ahora en orden aleatorio
```

Para `ArrayList`, Java lo hace por ti:

```java
import java.util.Collections;

ArrayList<Integer> lista = new ArrayList<>(List.of(1, 2, 3, 4, 5));
Collections.shuffle(lista);
Collections.shuffle(lista, new Random(42));  // con semilla fija
```

---

## Resumen

```java
Random rand = new Random();

rand.nextInt(n)           // entero entre 0 y n-1
rand.nextInt(min, max)    // entero entre min y max-1 (Java 8+)
rand.nextDouble()         // decimal entre 0.0 y 1.0
rand.nextBoolean()        // true o false
rand.nextLong()           // long aleatorio

// rango personalizado [min, max] incluidos
int n = min + rand.nextInt(max - min + 1);

// alternativa rapida sin objeto
double d = Math.random();

// reproducible (misma secuencia siempre)
Random fijo = new Random(semilla);
```
