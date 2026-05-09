# 09 - Colecciones

Las **colecciones** son estructuras que almacenan grupos de objetos. A diferencia de los arrays, su tamaño es **dinamico** (crece y encoge segun necesites).

Todas estan en el paquete `java.util`. Necesitas importarlas.

---

## Jerarquia basica de colecciones

```
Collection
  ├── List (ordenada, permite duplicados)
  │     ├── ArrayList   ← MAS USADA
  │     └── LinkedList
  └── Set (sin duplicados)
        ├── HashSet
        └── TreeSet (ordenado)

Map (pares clave-valor, no es Collection)
  ├── HashMap   ← MAS USADA
  └── TreeMap (ordenado por clave)
```

---

## ArrayList

La coleccion mas usada. Lista ordenada que permite duplicados y crece automaticamente.

```java
import java.util.ArrayList;

// Declaracion con generics: <Tipo> indica que tipo de objetos guarda
ArrayList<String> nombres = new ArrayList<>();

// Anadir elementos
nombres.add("Ana");
nombres.add("Luis");
nombres.add("Carlos");
nombres.add("Ana");      // permite duplicados

// Tamaño
System.out.println(nombres.size());   // 4 (no .length como en arrays)

// Acceder por indice
System.out.println(nombres.get(0));   // "Ana"
System.out.println(nombres.get(2));   // "Carlos"

// Modificar
nombres.set(1, "Laura");   // sustituye "Luis" por "Laura"

// Eliminar por indice
nombres.remove(0);         // elimina "Ana" (la primera)

// Eliminar por valor
nombres.remove("Carlos");  // elimina la primera ocurrencia de "Carlos"

// Buscar
boolean existe = nombres.contains("Laura");  // true
int posicion = nombres.indexOf("Laura");     // 0 (o -1 si no existe)

// Recorrer con for-each
for (String nombre : nombres) {
    System.out.println(nombre);
}

// Limpiar
nombres.clear();   // borra todos los elementos
System.out.println(nombres.isEmpty());  // true
```

### ArrayList con objetos propios

```java
ArrayList<Persona> personas = new ArrayList<>();
personas.add(new Persona("Ana", 25));
personas.add(new Persona("Luis", 30));

for (Persona p : personas) {
    System.out.println(p.getNombre());
}
```

### Ordenar un ArrayList

```java
import java.util.Collections;

ArrayList<Integer> numeros = new ArrayList<>();
numeros.add(5); numeros.add(2); numeros.add(8); numeros.add(1);

Collections.sort(numeros);   // orden natural: [1, 2, 5, 8]
Collections.reverse(numeros); // invierte: [8, 5, 2, 1]

// Para objetos propios, la clase debe implementar Comparable, o usar Comparator:
ArrayList<Persona> personas = new ArrayList<>();
// ...
personas.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre())); // alfabetico por nombre
```

---

## LinkedList

Lista doblemente enlazada. Util cuando haces muchas inserciones/eliminaciones en el medio de la lista. La interfaz es igual que ArrayList.

```java
import java.util.LinkedList;

LinkedList<String> lista = new LinkedList<>();
lista.add("B");
lista.addFirst("A");    // añade al principio
lista.addLast("C");     // añade al final

lista.removeFirst();    // elimina el primero
lista.removeLast();     // elimina el ultimo

System.out.println(lista.getFirst());  // primer elemento
System.out.println(lista.getLast());   // ultimo elemento
```

> En la practica, `ArrayList` es mas rapido para acceso por indice (`get(i)`).
> `LinkedList` es mas rapido para inserciones/eliminaciones en cualquier posicion.

---

## HashSet

Conjunto sin duplicados y sin orden garantizado. Muy rapido para buscar si un elemento existe.

```java
import java.util.HashSet;

HashSet<String> paises = new HashSet<>();
paises.add("España");
paises.add("Francia");
paises.add("España");   // no se añade: ya existe

System.out.println(paises.size());           // 2 (no 3)
System.out.println(paises.contains("Francia")); // true

// Recorrer (el orden puede variar)
for (String pais : paises) {
    System.out.println(pais);
}

// Eliminar
paises.remove("Francia");
```

> Si necesitas un set **ordenado**, usa `TreeSet` (ordena los elementos automaticamente).

---

## HashMap

Estructura de **clave → valor** (como un diccionario). Cada clave es unica, el valor puede repetirse.

```java
import java.util.HashMap;

HashMap<String, Integer> edades = new HashMap<>();

// Añadir pares clave-valor
edades.put("Ana", 25);
edades.put("Luis", 30);
edades.put("Carlos", 28);
edades.put("Ana", 26);   // sobreescribe el valor de "Ana"

// Acceder por clave
System.out.println(edades.get("Luis"));     // 30
System.out.println(edades.get("Pedro"));    // null (no existe)

// Con valor por defecto si no existe
System.out.println(edades.getOrDefault("Pedro", 0));  // 0

// Comprobar
System.out.println(edades.containsKey("Ana"));      // true
System.out.println(edades.containsValue(30));        // true

// Tamaño
System.out.println(edades.size());  // 3

// Eliminar
edades.remove("Carlos");

// Recorrer: tres formas
// 1. Solo las claves
for (String nombre : edades.keySet()) {
    System.out.println(nombre);
}

// 2. Solo los valores
for (int edad : edades.values()) {
    System.out.println(edad);
}

// 3. Pares clave-valor (lo mas comun)
for (Map.Entry<String, Integer> entrada : edades.entrySet()) {
    System.out.println(entrada.getKey() + " → " + entrada.getValue());
}
```

---

## Generics (tipos genericos)

Los `<Tipo>` que aparecen en las colecciones son **generics**. Sirven para indicar que tipo de objetos puede guardar la coleccion.

```java
ArrayList<String>  lista1;  // solo Strings
ArrayList<Integer> lista2;  // solo Integers (int con wrapper)
ArrayList<Persona> lista3;  // solo objetos Persona
```

**Por que se usan:**
- El compilador verifica los tipos → evita errores en tiempo de ejecucion
- No necesitas castear al recuperar elementos
- Codigo mas claro y seguro

```java
// Sin generics (antiguo, NO hacer):
ArrayList lista = new ArrayList();
lista.add("hola");
lista.add(42);           // mezcla tipos → confusion
String s = (String) lista.get(0);  // necesitas castear → puede fallar

// Con generics (correcto):
ArrayList<String> lista = new ArrayList<>();
lista.add("hola");
// lista.add(42);  // ERROR de compilacion: solo puede contener Strings
String s = lista.get(0);  // no necesitas castear
```

---

## Comparativa rapida

| Coleccion    | Orden     | Duplicados | Clave-Valor | Uso tipico                              |
|--------------|-----------|------------|-------------|----------------------------------------|
| `ArrayList`  | Insercion | Si         | No          | Lista general, acceso por indice       |
| `LinkedList` | Insercion | Si         | No          | Muchas inserciones/borrados            |
| `HashSet`    | Ninguno   | No         | No          | Verificar existencia, eliminar dupl.   |
| `TreeSet`    | Natural   | No         | No          | Set ordenado automaticamente           |
| `HashMap`    | Ninguno   | (claves no)| Si          | Buscar por clave, contar ocurrencias   |
| `TreeMap`    | Por clave | (claves no)| Si          | Mapa ordenado por clave                |

---

## Resumen

```java
// Lista dinamica (mas comun)
ArrayList<Tipo> lista = new ArrayList<>();
lista.add(x);          // añadir
lista.get(i);          // leer por indice
lista.set(i, x);       // modificar
lista.remove(i);       // eliminar por indice
lista.size();          // tamaño
lista.contains(x);     // buscar

// Mapa clave → valor
HashMap<Clave, Valor> mapa = new HashMap<>();
mapa.put(clave, valor);   // insertar
mapa.get(clave);          // obtener valor
mapa.containsKey(clave);  // comprobar clave
mapa.entrySet()           // recorrer pares

// Set sin duplicados
HashSet<Tipo> conjunto = new HashSet<>();
conjunto.add(x);
conjunto.contains(x);
```
