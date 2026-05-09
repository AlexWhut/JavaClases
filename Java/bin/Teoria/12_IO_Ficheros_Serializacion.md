# 12 - I/O, Ficheros y Serialización

---

## ¿Por qué necesitamos guardar datos?

Cuando un programa Java termina, **toda la memoria se borra**. Los objetos desaparecen.
Si quieres que los datos sobrevivan entre ejecuciones (o que otro programa los lea), necesitas **persistirlos**: guardarlos en disco.

Java ofrece dos grandes enfoques:

| Enfoque | Cuándo usarlo | Resultado |
|---|---|---|
| **Archivos de texto** | Informes, logs, CSV | Legible por humanos |
| **Serialización binaria** | Guardar/cargar objetos | No legible, pero perfecto para el programa |

En el proyecto `SeguimientoValoraciones` usamos **ambos**:
- Serialización → `miMediateca.med` (guarda el ArrayList completo)
- Texto → `Informe medios.txt` (informe legible)

---

## Analogía: la tubería

Piensa en un **stream** (flujo) como una tubería de agua:

```
ORIGEN  ──►  [tubería]  ──►  DESTINO
```

- La **tubería** transporta datos (bytes o caracteres)
- Puedes encadenar tuberías para añadir funcionalidad (buffers, conversión, formato)
- Siempre hay que **cerrar** la tubería al terminar (`close()`)

---

## Jerarquía de streams en Java

```
java.io
│
├── Streams de BYTES (datos binarios: imágenes, objetos serializados...)
│     InputStream / OutputStream  ← abstractos, base de todo
│       ├── FileInputStream / FileOutputStream       ← leer/escribir bytes de/a archivo
│       ├── ObjectInputStream / ObjectOutputStream   ← serializar objetos
│       └── BufferedInputStream / BufferedOutputStream  ← añadir buffer de eficiencia
│
└── Streams de CARACTERES (texto legible)
      Reader / Writer  ← abstractos, base
        ├── FileReader / FileWriter          ← leer/escribir texto de/a archivo
        ├── BufferedReader / BufferedWriter  ← con buffer (más eficiente)
        ├── InputStreamReader               ← convierte bytes → caracteres (con encoding)
        └── PrintWriter                     ← con println/printf (cómodo para informes)
```

> **Regla general:** usa streams de **bytes** para datos binarios (imágenes, objetos).
> Usa streams de **caracteres** para texto humano legible.

---

## Texto vs Binario — diferencia visual

Imagina que guardas el objeto `Pelicula("Fast & Furious X", "Louis Leterrier", MAYORES, 2023)`:

**Archivo de texto** (`informe.txt`):
```
Fast & Furious X, dirigida por Louis Leterrier (2023)
```

**Archivo binario serializado** (`mediateca.med` — representación aproximada):
```
aced 0005 7372 0026 5365 6775 696d 6965 6e74...
```
→ ilegible, pero Java puede reconstruir el objeto exacto.

---

## 1. Escritura de texto: `FileWriter` + `PrintWriter`

### ¿Qué son?

- `FileWriter` abre/crea un archivo de texto y escribe caracteres
- `PrintWriter` envuelve a `FileWriter` y añade `println()`, `printf()` (como `System.out` pero hacia un archivo)

### Sintaxis básica

```java
import java.io.*;

try (PrintWriter pw = new PrintWriter(new FileWriter("informe.txt"))) {
    pw.println("=== INFORME ===");             // escribe línea con salto
    pw.printf("Título: %s (%d)%n", titulo, anio); // formato como System.out.printf
    pw.print("sin salto de línea");            // escribe sin salto
}
// el archivo se cierra automáticamente al salir del try
```

### FileWriter: ¿sobrescribir o añadir?

```java
// Por defecto: SOBRESCRIBE el archivo si ya existe
new FileWriter("informe.txt")

// Con true: AÑADE al final del archivo (append mode)
new FileWriter("informe.txt", true)
```

### Del proyecto SeguimientoValoraciones (Main.java línea 57)

```java
try (PrintWriter pw = new PrintWriter(new FileWriter("Informe medios.txt"))) {

    pw.println("Peliculas");
    for (Streaming item : mediateca) {
        if (item instanceof Pelicula pelicula) {
            pw.println("  " + pelicula);          // usa el toString() de Pelicula
        }
    }

    pw.println();
    pw.println("Series");
    for (Streaming item : mediateca) {
        if (item instanceof Serie serie) {
            pw.println("  " + serie);
            for (Capitulo cap : serie.getCapitulos()) {
                pw.printf("    Temporada %d, Capitulo %d: %s. (%d)%n",
                        cap.getTemporada(), cap.getNumeroCapitulo(),
                        cap.getTitulo(), cap.getAnio());
            }
        }
    }
}
```

Resultado en `Informe medios.txt`:
```
Peliculas
  Fast & Furious X, dirigida por Louis Leterrier (2023)
  West Side Story, dirigida por Steven Spielberg (2021)

Series
  Juego de Tronos. Dirigida por David Benioff y otros.
    Temporada 1, Capitulo 1: Se acerca el invierno. (2011)
    Temporada 1, Capitulo 2: El camino real. (2011)
    ...
```

---

## 2. Lectura de texto: `FileReader` + `BufferedReader`

### ¿Por qué `BufferedReader`?

`FileReader` solo lee **un carácter a la vez** → muy lento en archivos grandes.
`BufferedReader` añade un **buffer interno** (como leer una página entera en lugar de letra a letra) y ofrece `readLine()`.

```java
import java.io.*;

try (BufferedReader br = new BufferedReader(new FileReader("informe.txt"))) {
    String linea;
    while ((linea = br.readLine()) != null) {  // null → fin de archivo
        System.out.println(linea);
    }
}
```

### Con `Scanner` (alternativa más cómoda)

```java
import java.io.*;
import java.util.Scanner;

try (Scanner sc = new Scanner(new File("informe.txt"))) {
    while (sc.hasNextLine()) {
        String linea = sc.nextLine();
        System.out.println(linea);
    }
}
```

> `Scanner` es más familiar (lo usas con `System.in`) y tiene métodos como `nextInt()`, `nextDouble()`.
> `BufferedReader` es más eficiente para archivos muy grandes.

### Con `Files` (Java 11+, la forma más moderna)

```java
import java.nio.file.*;
import java.util.List;

// Leer todas las líneas como lista
List<String> lineas = Files.readAllLines(Path.of("informe.txt"));

// Leer todo el contenido como un String
String contenido = Files.readString(Path.of("informe.txt"));

// Escribir una lista de líneas
Files.write(Path.of("informe.txt"), lineas);
```

---

## 3. Serialización binaria

### ¿Qué es?

**Serializar** = convertir un objeto Java completo (con todos sus atributos, y los objetos que contiene) en una **secuencia de bytes** que puedes guardar en disco.

**Deserializar** = el proceso inverso: reconstruir el objeto exacto desde esos bytes.

```
Objeto Java  →  [ObjectOutputStream]  →  archivo.dat (binario)
archivo.dat  →  [ObjectInputStream]   →  Objeto Java (idéntico al original)
```

### Requisito 1: implementar `Serializable`

Toda clase cuyos objetos quieras serializar **debe implementar `java.io.Serializable`**.

`Serializable` es una **interfaz marcadora** — no tiene métodos. Solo le dice a Java "esta clase puede convertirse a bytes".

```java
import java.io.Serializable;

public class Valoracion implements Serializable {   // ← marca la clase
    private LocalDate fecha;
    private int valoracion;
    // ...
}
```

En el proyecto, **todas las clases** de la jerarquía implementan `Serializable`:

```
Streaming (implements Serializable)
  ├── Pelicula
  └── Serie
        └── usa Capitulo (implements Serializable)
                └── usa Valoracion (implements Serializable)
```

> Si cualquier clase de la jerarquía NO implementara `Serializable`, obtendríamos
> `java.io.NotSerializableException` al intentar guardar.

### Requisito 2: la cadena completa debe ser serializable

Si `Capitulo` contiene un `ArrayList<Valoracion>`, entonces `Valoracion` también debe ser `Serializable`. Si contiene un `LocalDate` — afortunadamente `LocalDate` ya implementa `Serializable` en Java moderno.

### Guardar: `ObjectOutputStream`

```java
import java.io.*;
import java.util.ArrayList;

// Guardar el ArrayList completo con una sola llamada
try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("miMediateca.med"))) {

    oos.writeObject(mediateca);   // serializa TODO el ArrayList y sus objetos
    System.out.println("Guardado correctamente.");

} catch (IOException e) {
    System.err.println("Error al guardar: " + e.getMessage());
}
```

**Cómo funciona internamente:**
```
ArrayList<Streaming>  ──►  ObjectOutputStream  ──►  FileOutputStream  ──►  archivo.med
[p1, p2, s1]               convierte a bytes         escribe en disco        (binario)
```

Del proyecto (Main.java línea 48):
```java
try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("miMediateca.med"))) {
    oos.writeObject(mediateca);
    System.out.println("\nMediateca serializada en miMediateca.med");
} catch (IOException e) {
    System.err.println("Error al serializar: " + e.getMessage());
}
```

### Cargar: `ObjectInputStream`

```java
import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")   // silencia el aviso del cast genérico
public static ArrayList<Streaming> cargarMediateca(String ruta) {
    try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(ruta))) {

        // readObject() devuelve Object → hay que hacer cast
        ArrayList<Streaming> mediateca = (ArrayList<Streaming>) ois.readObject();
        System.out.println("Cargados " + mediateca.size() + " elementos.");
        return mediateca;

    } catch (FileNotFoundException e) {
        System.out.println("[INFO] No hay archivo previo. Empezando desde cero.");
        return new ArrayList<>();

    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Error al cargar: " + e.getMessage());
        return new ArrayList<>();
    }
}
```

**Cómo funciona internamente:**
```
archivo.med (binario)  ──►  FileInputStream  ──►  ObjectInputStream  ──►  ArrayList<Streaming>
                             lee bytes             reconstruye objetos      [p1, p2, s1]
```

---

## 4. `serialVersionUID` — control de versiones

```java
public class Streaming implements Serializable {
    private static final long serialVersionUID = 1L;
    // ...
}
```

Cuando deserializas, Java compara el `serialVersionUID` del **archivo guardado** con el de la **clase actual**.
Si no coinciden → `InvalidClassException` (los datos guardados son incompatibles).

**¿Cuándo cambiar el número?**
- Añades/eliminas atributos de forma que los datos viejos ya no tienen sentido
- Cambias el tipo de un atributo

**¿Cuándo NO importa?**
- Añades métodos nuevos (no afectan a la serialización)
- Añades atributos opcionales con valor por defecto aceptable

> Si no declaras `serialVersionUID`, Java calcula uno automáticamente. Si modificas
> cualquier cosa de la clase (incluso un método), el número cambia y no podrás
> cargar archivos viejos. **Siempre declárralo explícitamente.**

---

## 5. `transient` — excluir atributos de la serialización

Si un atributo **no debe guardarse** (contraseña en texto plano, recursos del sistema, datos calculables), márcalo como `transient`:

```java
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;              // SÍ se serializa
    private transient String password;  // NO se serializa (será null al deserializar)
    private transient Scanner sc;       // NO se serializa (Scanner no es Serializable)
}
```

---

## 6. `try-with-resources` — cierre automático

Cualquier recurso que implemente `AutoCloseable` (todos los streams de I/O lo hacen) se puede usar en un `try-with-resources`. **Se cierra automáticamente** al salir del bloque, haya o no excepción.

```java
// SIN try-with-resources (manual, propenso a errores)
ObjectOutputStream oos = null;
try {
    oos = new ObjectOutputStream(new FileOutputStream("datos.dat"));
    oos.writeObject(lista);
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (oos != null) {
        try { oos.close(); } catch (IOException e) { e.printStackTrace(); }
    }
}

// CON try-with-resources (moderno, limpio)
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos.dat"))) {
    oos.writeObject(lista);
} catch (IOException e) {
    e.printStackTrace();
}
// oos.close() se llama automáticamente aquí
```

Puedes abrir **múltiples recursos** separados por `;`:

```java
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("entrada.dat"));
     PrintWriter pw = new PrintWriter(new FileWriter("salida.txt"))) {
    // usar ois y pw
}   // ambos se cierran aquí, en orden inverso al de apertura
```

---

## 7. Excepciones comunes de I/O

| Excepción | Cuándo ocurre |
|---|---|
| `FileNotFoundException` | El archivo que intentas abrir no existe |
| `IOException` | Error genérico de lectura/escritura (disco lleno, permisos...) |
| `ClassNotFoundException` | Al deserializar: la clase del objeto no existe en el proyecto |
| `InvalidClassException` | `serialVersionUID` del archivo ≠ el de la clase actual |
| `NotSerializableException` | Intentas serializar una clase que no implementa `Serializable` |

> `FileNotFoundException` es un subtipo de `IOException`. Si capturas `IOException`,
> también capturas `FileNotFoundException`.

---

## 8. Resumen visual: ¿qué uso cuándo?

```
Necesito guardar datos para recargarlos como objetos Java
  └─► Serialización  →  ObjectOutputStream / ObjectInputStream

Necesito generar un informe/log legible por humanos
  └─► Texto  →  PrintWriter + FileWriter

Necesito leer un archivo de texto línea a línea
  └─► BufferedReader + FileReader  (o Scanner con File)

Proyecto grande / Java 11+
  └─► java.nio.file.Files  (readAllLines, readString, write)
```

---

## Ejercicio — Mediateca con persistencia

Este ejercicio es la tarea completa del proyecto `SeguimientoValoraciones`.

### Contexto del dominio

Estás construyendo una **mediateca personal** para registrar películas y series de streaming con sus valoraciones.

### Diagrama de clases

```
             «Serializable»
             Streaming  (abstract)
            /           \
       Pelicula         Serie
                          └── ArrayList<Capitulo>  «Serializable»
                                    └── ArrayList<Valoracion>  «Serializable»

TipoClasificacion  (enum)  INFANTIL | JUVENIL | MAYORES
```

### Parte 1 — Modelo de datos (sin persistencia)

Implementa las clases:

**`Valoracion`** (implements Serializable)
- `fecha: LocalDate`
- `valoracion: int` (0-10, validar)

**`Capitulo`** (implements Serializable)
- `temporada: int`, `numeroCapitulo: int`, `anio: int`, `titulo: String`
- `valoraciones: ArrayList<Valoracion>`
- `addValoracion(fecha, valor)` — la fecha no puede ser anterior al año del capítulo
- `valoracionMedia(): double`

**`Streaming`** (abstract, implements Serializable)
- `titulo: String`, `director: String`, `clasificacion: TipoClasificacion`
- Validar que titulo y director no sean nulos ni en blanco

**`Pelicula`** extends `Streaming`
- `anio: int`
- `toString()` → `"Fast & Furious X, dirigida por Louis Leterrier (2023)"`

**`Serie`** extends `Streaming`
- `capitulos: ArrayList<Capitulo>`
- `annadirCapitulo(temporada, numCapitulo, anio, titulo)`
- `annadirValoracion(temporada, numCapitulo, fecha, valoracion): boolean`
- `valoracionMediaTemporada(temporada): double`

### Parte 2 — Filtrado con colecciones

En el `main`, crea este conjunto de datos:

```java
Pelicula p1 = new Pelicula("Fast & Furious X", "Louis Leterrier", TipoClasificacion.MAYORES, 2023);
Pelicula p2 = new Pelicula("West Side Story", "Steven Spielberg", TipoClasificacion.JUVENIL, 2021);

Serie s1 = new Serie("Juego de Tronos", "David Benioff y otros", TipoClasificacion.MAYORES);
s1.annadirCapitulo(1, 1, 2011, "Se acerca el invierno");
s1.annadirCapitulo(1, 2, 2011, "El camino real");
s1.annadirCapitulo(2, 1, 2012, "El norte no olvida");
s1.annadirCapitulo(2, 2, 2012, "Las tierras de los Lannister");

s1.annadirValoracion(1, 1, LocalDate.of(2011, 6, 1),  9);
s1.annadirValoracion(1, 1, LocalDate.of(2011, 7, 1),  8);
s1.annadirValoracion(1, 2, LocalDate.of(2011, 6, 15), 7);
s1.annadirValoracion(2, 1, LocalDate.of(2012, 4, 10), 6);
```

Imprime por consola los capítulos cuya **valoración media sea >= 7**.

Salida esperada:
```
=== Capítulos con valoración media >= 7 ===
  [Juego de Tronos] T1 E1: Se acerca el invierno (media: 8,5)
  [Juego de Tronos] T1 E2: El camino real (media: 7,0)
```

<details>
<summary>Pista — cómo iterar</summary>

```java
for (Streaming item : mediateca) {
    if (item instanceof Serie serie) {        // pattern matching Java 16+
        for (Capitulo cap : serie.getCapitulos()) {
            if (!cap.getValoraciones().isEmpty() && cap.valoracionMedia() >= 7) {
                System.out.printf("  [%s] T%d E%d: %s (media: %.1f)%n", ...);
            }
        }
    }
}
```
</details>

### Parte 3 — Serialización binaria

Serializa el `ArrayList<Streaming> mediateca` en el archivo `miMediateca.med`.

Requisitos:
- Usar `ObjectOutputStream` + `FileOutputStream`
- Usar `try-with-resources`
- Capturar `IOException` e imprimir mensaje de error si falla

Después, añade un método `cargarMediateca(String ruta)` que:
- Cargue el archivo con `ObjectInputStream`
- Devuelva `ArrayList<Streaming>`
- Si el archivo no existe, devuelva un `ArrayList` vacío (no lance excepción)

<details>
<summary>Pista — estructura del método cargar</summary>

```java
@SuppressWarnings("unchecked")
public static ArrayList<Streaming> cargarMediateca(String ruta) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
        return (ArrayList<Streaming>) ois.readObject();
    } catch (FileNotFoundException e) {
        return new ArrayList<>();    // primera vez que se ejecuta
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Error al cargar: " + e.getMessage());
        return new ArrayList<>();
    }
}
```
</details>

### Parte 4 — Informe de texto

Genera el archivo `Informe medios.txt` con este formato exacto:

```
Peliculas
  Fast & Furious X, dirigida por Louis Leterrier (2023)
  West Side Story, dirigida por Steven Spielberg (2021)

Series
  Juego de Tronos. Dirigida por David Benioff y otros.
    Temporada 1, Capitulo 1: Se acerca el invierno. (2011)
    Temporada 1, Capitulo 2: El camino real. (2011)
    Temporada 2, Capitulo 1: El norte no olvida. (2012)
    Temporada 2, Capitulo 2: Las tierras de los Lannister. (2012)
```

Requisitos:
- Usar `PrintWriter` + `FileWriter`
- Usar `try-with-resources`
- Capturar `IOException`

<details>
<summary>Pista — formato de capítulo</summary>

```java
pw.printf("    Temporada %d, Capitulo %d: %s. (%d)%n",
        cap.getTemporada(), cap.getNumeroCapitulo(),
        cap.getTitulo(), cap.getAnio());
```
</details>

---

## Resumen de la teoría

```
PERSISTENCIA EN JAVA
│
├── Texto (humano legible)
│     ESCRIBIR:  PrintWriter pw = new PrintWriter(new FileWriter("archivo.txt"))
│                pw.println(...)  /  pw.printf(...)
│     LEER:      BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))
│                br.readLine()  →  null cuando llega al final
│
└── Binario (serialización de objetos)
      REQUISITO:  la clase implementa Serializable
                  todos sus atributos también Serializable
      GUARDAR:    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))
                  oos.writeObject(objeto)
      CARGAR:     ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))
                  MiClase obj = (MiClase) ois.readObject()

SIEMPRE usar try-with-resources para cerrar streams automáticamente.

Excepciones clave:
  IOException           → error de lectura/escritura
  FileNotFoundException → archivo no encontrado (subtipo de IOException)
  ClassNotFoundException → la clase no existe al deserializar
  NotSerializableException → la clase no implementa Serializable

Palabras clave:
  transient        → el atributo NO se serializa
  serialVersionUID → controla la compatibilidad entre versiones del archivo
```
