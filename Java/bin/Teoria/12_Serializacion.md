# 12 - Serializacion de objetos

La **serializacion** es el proceso de convertir un objeto Java en una secuencia de bytes para **guardarlo en disco** o **enviarlo por red**. La **deserializacion** es el proceso inverso: reconstruir el objeto desde esos bytes.

Es el mecanismo que usa el `GestorUsuarios` para guardar y cargar todos los usuarios con sus recetas.

---

## ¿Por que es util?

Sin serializacion, cuando el programa termina, todos los objetos en memoria desaparecen. Con serializacion puedes:

- **Persistir** el estado del programa (guardar y cargar)
- **Transmitir** objetos por red
- **Copiar** objetos de forma profunda

---

## Requisitos para serializar

1. La clase debe implementar la interfaz `Serializable` de `java.io`
2. Todos sus atributos (y los objetos que contienen) tambien deben ser `Serializable`
3. Se recomienda declarar `serialVersionUID` para control de versiones

```java
import java.io.Serializable;

public class Persona implements Serializable {

    // Control de version: si cambias la estructura de la clase,
    // sube este numero para evitar cargar datos incompatibles
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad   = edad;
    }

    // getters, setters, toString...
}
```

> `Serializable` es una **interfaz marcadora** (sin metodos). Solo indica a Java que esta clase puede ser serializada.

---

## Guardar objetos: `ObjectOutputStream`

```java
import java.io.*;
import java.util.ArrayList;

public void guardarUsuarios(ArrayList<Persona> personas, String ruta) throws IOException {
    // try-with-resources: el stream se cierra automaticamente al terminar
    try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(ruta))) {

        // writeObject serializa el ArrayList COMPLETO con todos los objetos dentro
        oos.writeObject(personas);

        System.out.println("Guardado correctamente en: " + ruta);
    }
    // Si hay error (no se puede escribir, disco lleno...) → IOException
}
```

### Como funciona internamente

```
ArrayList<Persona>  →  ObjectOutputStream  →  FileOutputStream  →  archivo.dat (binario)
[Ana(25), Luis(30)]     convierte a bytes       escribe en disco
```

El archivo resultante es **binario** (no se puede leer con un editor de texto).

---

## Cargar objetos: `ObjectInputStream`

```java
@SuppressWarnings("unchecked")  // silencia el aviso del cast generico
public ArrayList<Persona> cargarUsuarios(String ruta) throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(ruta))) {

        // readObject devuelve Object → necesitamos castear al tipo original
        ArrayList<Persona> personas = (ArrayList<Persona>) ois.readObject();

        System.out.println("Cargados " + personas.size() + " usuarios");
        return personas;
    }
    // FileNotFoundException si el archivo no existe
    // ClassNotFoundException si la clase Persona ya no existe en el proyecto
    // IOException si hay error al leer
}
```

### Como funciona internamente

```
archivo.dat (binario)  →  FileInputStream  →  ObjectInputStream  →  ArrayList<Persona>
                            lee bytes          reconstruye objetos    [Ana(25), Luis(30)]
```

---

## Ejemplo completo: guardar y cargar

```java
import java.io.*;
import java.util.ArrayList;

public class GestorDatos {

    private static final String ARCHIVO = "datos.dat";

    public static void guardar(ArrayList<Persona> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO))) {
            oos.writeObject(lista);
            System.out.println("[OK] Datos guardados.");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo guardar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Persona> cargar() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO))) {
            return (ArrayList<Persona>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] No existe archivo previo. Empezando desde cero.");
            return new ArrayList<>();   // lista vacia si no hay archivo
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[ERROR] No se pudo cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

// Uso en main:
ArrayList<Persona> personas = new ArrayList<>();
personas.add(new Persona("Ana", 25));
personas.add(new Persona("Luis", 30));

GestorDatos.guardar(personas);           // guarda en "datos.dat"
// ... el programa puede cerrarse y volver a abrirse ...
ArrayList<Persona> cargadas = GestorDatos.cargar();  // recupera los mismos objetos
```

---

## La palabra clave `transient`

Si un atributo NO debe serializarse (por ejemplo, una contraseña en texto plano o un recurso que no tiene sentido guardar), marcalo como `transient`:

```java
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;            // SI se serializa
    private transient String password; // NO se serializa (valor sera null al deserializar)
    private transient Scanner sc;      // NO se serializa (los Scanners no son serializables)
}
```

---

## `serialVersionUID` — control de versiones

```java
private static final long serialVersionUID = 1L;
```

Cuando deserializas, Java compara el `serialVersionUID` del archivo con el de la clase actual.
Si son distintos → `InvalidClassException`.

**Cuando subir el numero:**
- Si añades/eliminas atributos de forma incompatible
- Si cambias el tipo de un atributo

**Cuando NO importa:**
- Si añades metodos (no afectan a la serializacion)
- Si añades atributos con valor por defecto aceptable

Si no declaras `serialVersionUID`, Java calcula uno automaticamente basado en la estructura de la clase. Si cambias la clase (aunque sea añadir un metodo), el numero cambia y no podras cargar datos viejos. **Declararlo explicitamente evita este problema.**

---

## Jerarquia de streams de I/O

```
Streams de bytes (binario):
  FileInputStream / FileOutputStream    ← leer/escribir bytes en archivo
  ObjectInputStream / ObjectOutputStream ← serializar sobre bytes

Streams de texto (caracteres):
  FileReader / FileWriter               ← leer/escribir texto en archivo
  BufferedReader / BufferedWriter       ← con buffer (mas eficiente)
  PrintWriter                           ← con metodos printf/println
  Scanner                               ← leer con tipos (nextInt, etc.)
```

---

## Archivos de texto (no serializacion)

Para informes y logs, se usan streams de texto en lugar de serializacion:

```java
// ESCRIBIR texto en un archivo
try (PrintWriter pw = new PrintWriter(new FileWriter("informe.txt"))) {
    pw.println("=== INFORME ===");
    pw.printf("Usuario: %s%n", nombre);
    pw.printf("Fecha: %s%n", fecha);
}

// LEER texto de un archivo linea a linea
try (BufferedReader br = new BufferedReader(new FileReader("informe.txt"))) {
    String linea;
    while ((linea = br.readLine()) != null) {
        System.out.println(linea);
    }
}

// Forma moderna con Files (Java 7+)
import java.nio.file.*;

// Escribir todas las lineas de una vez
List<String> lineas = Arrays.asList("Linea 1", "Linea 2");
Files.write(Path.of("archivo.txt"), lineas);

// Leer todo el contenido de una vez
String contenido = Files.readString(Path.of("archivo.txt"));

// Leer todas las lineas como lista
List<String> todasLineas = Files.readAllLines(Path.of("archivo.txt"));
```

---

## Resumen

```
Serializable    → interfaz marcadora (sin metodos) que habilita la serializacion
serialVersionUID → Long que controla la compatibilidad de versiones
transient       → atributo que NO se serializa

GUARDAR (serializar):
  ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
  oos.writeObject(objeto);

CARGAR (deserializar):
  ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
  Tipo obj = (Tipo) ois.readObject();

Excepciones posibles:
  IOException           → error de lectura/escritura
  FileNotFoundException → el archivo no existe
  ClassNotFoundException → la clase no existe al deserializar

Usar try-with-resources para cerrar streams automaticamente.
```
