# 04 - Arrays

Un **array** es una estructura que almacena **varios valores del mismo tipo** en posiciones contiguas de memoria, accesibles mediante un indice numerico.

---

## Caracteristicas clave

- Tamaño **fijo**: una vez creado, no puede crecer ni encogerse
- Los indices empiezan en **0** (el primer elemento es `[0]`, el ultimo es `[length-1]`)
- Todos los elementos son del **mismo tipo**
- Si accedes a un indice fuera de rango → `ArrayIndexOutOfBoundsException`

---

## Arrays unidimensionales

### Declarar y crear

```java
// Forma 1: declarar y crear por separado
int[] numeros;           // solo declara la referencia (es null)
numeros = new int[5];    // crea el array de 5 elementos (todos valen 0)

// Forma 2: declarar y crear a la vez (mas comun)
int[] edades = new int[10];

// Forma 3: declarar con valores iniciales (inicializacion directa)
int[] primos = {2, 3, 5, 7, 11};   // tamaño = 5, valores ya asignados
String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
```

### Valores por defecto al crear con `new`

| Tipo          | Valor por defecto |
|---------------|-------------------|
| `int`, `long` | `0`               |
| `double`      | `0.0`             |
| `boolean`     | `false`           |
| `String`      | `null`            |
| Objetos       | `null`            |

### Acceder y modificar elementos

```java
int[] nums = {10, 20, 30, 40, 50};

// Leer
System.out.println(nums[0]);   // 10 (primer elemento)
System.out.println(nums[4]);   // 50 (ultimo elemento)
System.out.println(nums.length); // 5 (propiedad, no metodo → sin parentesis)

// Escribir
nums[2] = 99;  // ahora el array es {10, 20, 99, 40, 50}

// Error clasico:
nums[5] = 1;   // ArrayIndexOutOfBoundsException: indice 5 no existe (0-4)
```

### Recorrer un array

```java
int[] datos = {3, 7, 1, 9, 4};

// Con for clasico (cuando necesitas el indice)
for (int i = 0; i < datos.length; i++) {
    System.out.println("datos[" + i + "] = " + datos[i]);
}

// Con for-each (cuando solo necesitas el valor)
for (int d : datos) {
    System.out.println(d);
}
```

### Operaciones comunes

```java
int[] nums = {5, 3, 8, 1, 7};

// Suma de todos
int suma = 0;
for (int n : nums) suma += n;

// Minimo y maximo
int min = nums[0], max = nums[0];
for (int n : nums) {
    if (n < min) min = n;
    if (n > max) max = n;
}

// Ordenar (usa la clase Arrays de java.util)
import java.util.Arrays;
Arrays.sort(nums);            // modifica el array original: {1, 3, 5, 7, 8}

// Convertir a String para imprimir
System.out.println(Arrays.toString(nums));  // [1, 3, 5, 7, 8]

// Buscar (el array debe estar ordenado)
int pos = Arrays.binarySearch(nums, 5);   // devuelve el indice de 5, o negativo si no existe

// Copiar
int[] copia = Arrays.copyOf(nums, nums.length);        // copia completa
int[] trozo = Arrays.copyOfRange(nums, 1, 4);          // copia desde [1] hasta [3]
```

---

## Arrays bidimensionales (matrices)

Un array de arrays. Util para representar tablas, imagenes, matrices matematicas...

```java
// Declarar una matriz de 3 filas x 4 columnas
int[][] matriz = new int[3][4];

// Con valores iniciales (3 filas, 3 columnas)
int[][] tablero = {
    {1, 2, 3},   // fila 0
    {4, 5, 6},   // fila 1
    {7, 8, 9}    // fila 2
};

// Acceder: [fila][columna]
System.out.println(tablero[0][0]);  // 1 (esquina superior izquierda)
System.out.println(tablero[1][2]);  // 6 (fila 1, columna 2)
System.out.println(tablero[2][2]);  // 9 (esquina inferior derecha)

// Dimensiones
System.out.println(tablero.length);     // 3 (numero de filas)
System.out.println(tablero[0].length);  // 3 (numero de columnas en fila 0)
```

### Recorrer una matriz

```java
int[][] m = {{1,2,3},{4,5,6},{7,8,9}};

for (int fila = 0; fila < m.length; fila++) {
    for (int col = 0; col < m[fila].length; col++) {
        System.out.printf("%3d", m[fila][col]);
    }
    System.out.println();  // salto de linea al acabar cada fila
}
// Salida:
//   1  2  3
//   4  5  6
//   7  8  9
```

---

## Arrays de objetos

Puedes tener arrays de cualquier tipo, incluyendo objetos:

```java
String[] nombres = new String[3];
nombres[0] = "Ana";
nombres[1] = "Luis";
nombres[2] = "Carlos";

// O directamente:
String[] colores = {"Rojo", "Verde", "Azul"};

// Con objetos propios
Persona[] personas = new Persona[2];
personas[0] = new Persona("Ana", 25);
personas[1] = new Persona("Luis", 30);

for (Persona p : personas) {
    System.out.println(p.getNombre());
}
```

---

## Pasar arrays a metodos

Los arrays se pasan **por referencia**: el metodo recibe la direccion del array, no una copia. Cualquier modificacion dentro del metodo afecta al array original.

```java
public static void duplicar(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        arr[i] *= 2;   // modifica el array ORIGINAL
    }
}

public static void main(String[] args) {
    int[] nums = {1, 2, 3};
    duplicar(nums);
    System.out.println(Arrays.toString(nums));  // [2, 4, 6]
}
```

Esto contrasta con los primitivos, que se pasan **por valor** (el metodo recibe una copia y no puede cambiar el original).

---

## Limitaciones de los arrays — cuando usar ArrayList

Los arrays son rapidos y simples, pero tienen limitaciones:

| Necesidad                        | Array | ArrayList |
|----------------------------------|-------|-----------|
| Tamaño fijo conocido             | ✓     | ✓         |
| Añadir/eliminar elementos        | ✗     | ✓         |
| Tamaño dinamico                  | ✗     | ✓         |
| Primitivos directamente          | ✓     | ✗ (wrappers) |
| Metodos utiles (.add, .remove..) | ✗     | ✓         |

Si necesitas una lista que cambia de tamaño, usa `ArrayList`. Ver [09 - Colecciones](09_Colecciones.md).

---

## Resumen

```java
int[]   arr  = new int[5];          // array de 5 enteros
int[]   arr2 = {1, 2, 3};           // con valores iniciales
arr[0]       = 10;                   // asignar
int x  = arr[0];                    // leer
int len = arr.length;                // longitud (sin parentesis)

// Arrays 2D
int[][] m = new int[3][4];
m[0][0] = 1;

// Clase Arrays (java.util)
Arrays.sort(arr);                    // ordenar
Arrays.toString(arr);               // imprimir
Arrays.copyOf(arr, arr.length);     // copiar
```
