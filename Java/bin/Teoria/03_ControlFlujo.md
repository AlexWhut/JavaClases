# 03 - Control de flujo

El **control de flujo** determina el orden en que se ejecutan las instrucciones de un programa.
Sin el, el codigo siempre se ejecutaria linea a linea de arriba a abajo.

Hay tres tipos: **condicionales**, **bucles** y **saltos**.

---

## 1. Condicional `if / else if / else`

Ejecuta un bloque de codigo solo si se cumple una condicion.

### Estructura basica

```java
if (condicion) {
    // se ejecuta si condicion es true
}
```

### Con alternativa

```java
int nota = 6;

if (nota >= 9) {
    System.out.println("Sobresaliente");
} else if (nota >= 7) {
    System.out.println("Notable");
} else if (nota >= 5) {
    System.out.println("Aprobado");
} else {
    System.out.println("Suspenso");
}
```

> Solo se ejecuta **uno** de los bloques: el primero cuya condicion sea `true`.
> Una vez que entra en uno, los demas no se comprueban.

### If de una sola linea (sin llaves)

Si solo hay una instruccion, las llaves son opcionales. Pero **es mejor siempre ponerlas** para evitar errores:

```java
if (x > 0) System.out.println("Positivo");   // funciona
if (x > 0)                                    // trampa clasica:
    System.out.println("Positivo");           // solo esta en el if
    System.out.println("Siempre se imprime"); // esta SIEMPRE se ejecuta
```

---

## 2. Condicional `switch`

Compara una variable con varios valores posibles. Es mas legible que un `if/else if` largo cuando se compara siempre la misma variable.

### Switch clasico (Java 7+)

```java
String dia = "LUNES";

switch (dia) {
    case "LUNES":
        System.out.println("Inicio de semana");
        break;    // MUY IMPORTANTE: sin break, sigue ejecutando el siguiente case
    case "VIERNES":
        System.out.println("Por fin viernes");
        break;
    case "SABADO":
    case "DOMINGO":            // dos cases que hacen lo mismo
        System.out.println("Fin de semana");
        break;
    default:                   // si no coincide ningun case
        System.out.println("Dia normal");
}
```

> **El `break` es critico.** Sin el, despues de ejecutar un `case`, Java **cae** al siguiente (fall-through). Esto raramente es lo que quieres.

### Switch expression (Java 14+) — forma moderna

Mas concisa, sin riesgo de olvidar `break`:

```java
String resultado = switch (dia) {
    case "LUNES"             -> "Inicio de semana";
    case "VIERNES"           -> "Por fin viernes";
    case "SABADO", "DOMINGO" -> "Fin de semana";
    default                  -> "Dia normal";
};
System.out.println(resultado);
```

### ¿Que puede ir en el switch?

- `int`, `byte`, `short`, `char` y sus wrappers
- `String` (desde Java 7)
- `enum`
- **NO**: `double`, `float`, `long`, objetos en general

---

## 3. Bucle `for`

Repite un bloque un numero **conocido** de veces.

```java
// for (inicializacion; condicion; actualizacion)
for (int i = 0; i < 5; i++) {
    System.out.println("Iteracion " + i);
}
// Imprime: 0, 1, 2, 3, 4
```

- **Inicializacion:** se ejecuta una sola vez al empezar (`int i = 0`)
- **Condicion:** se comprueba antes de cada iteracion; si es `false`, el bucle para
- **Actualizacion:** se ejecuta al final de cada iteracion (`i++`)

```java
// Recorrer un array
int[] numeros = {10, 20, 30, 40, 50};
for (int i = 0; i < numeros.length; i++) {
    System.out.println("Posicion " + i + ": " + numeros[i]);
}

// Contar hacia atras
for (int i = 10; i >= 0; i--) {
    System.out.println(i);
}

// De dos en dos
for (int i = 0; i <= 20; i += 2) {
    System.out.println(i);  // 0, 2, 4, ..., 20
}
```

---

## 4. For-each (for mejorado)

Forma mas sencilla de recorrer arrays y colecciones cuando no necesitas el indice.

```java
// for (tipo elemento : coleccion)
int[] numeros = {10, 20, 30, 40};

for (int n : numeros) {
    System.out.println(n);
}

// Con ArrayList
ArrayList<String> nombres = new ArrayList<>();
nombres.add("Ana"); nombres.add("Luis");

for (String nombre : nombres) {
    System.out.println(nombre);
}
```

> Usa `for` normal cuando necesitas el indice o modificar elementos.
> Usa `for-each` cuando solo necesitas leer cada elemento.

---

## 5. Bucle `while`

Repite mientras una condicion sea `true`. Util cuando no sabes cuantas veces se repetira.

```java
// while (condicion) { bloque }
int n = 1;
while (n <= 5) {
    System.out.println(n);
    n++;    // sin esto → bucle infinito
}
```

### Patron tipico: menu interactivo

```java
Scanner sc = new Scanner(System.in);
int opcion = 0;

while (opcion != 4) {
    System.out.println("1. Nueva partida");
    System.out.println("4. Salir");
    opcion = sc.nextInt();
}
System.out.println("Hasta luego");
```

---

## 6. Bucle `do-while`

Como `while`, pero la condicion se comprueba **despues** de ejecutar el bloque.
Esto garantiza que el cuerpo se ejecuta **al menos una vez**.

```java
// do { bloque } while (condicion);
int n;
do {
    System.out.print("Escribe un numero positivo: ");
    n = sc.nextInt();
} while (n <= 0);    // si n <= 0, vuelve a pedir
// cuando llega aqui, n es garantidamente > 0
```

### Comparativa while vs do-while

```java
// while: puede no ejecutarse nunca si la condicion es false desde el principio
int x = 10;
while (x < 5) {        // false desde el principio
    System.out.println("nunca se imprime");
}

// do-while: siempre se ejecuta al menos una vez
do {
    System.out.println("se imprime una vez aunque x >= 5");
} while (x < 5);
```

---

## 7. Sentencias de salto: `break` y `continue`

### `break` — salir del bucle

Termina inmediatamente el bucle mas cercano.

```java
for (int i = 0; i < 100; i++) {
    if (i == 5) {
        break;  // sale del for cuando i vale 5
    }
    System.out.println(i);  // imprime 0, 1, 2, 3, 4
}
```

Uso tipico: buscar en un array y parar cuando encuentras lo que buscas:

```java
int[] datos = {3, 7, 1, 9, 4};
int buscado = 9;
boolean encontrado = false;

for (int d : datos) {
    if (d == buscado) {
        encontrado = true;
        break;  // ya no hace falta seguir buscando
    }
}
```

### `continue` — saltar iteracion

Salta el resto del cuerpo del bucle y pasa a la siguiente iteracion.

```java
for (int i = 0; i < 10; i++) {
    if (i % 2 == 0) {
        continue;  // salta los pares
    }
    System.out.println(i);  // imprime solo 1, 3, 5, 7, 9
}
```

---

## 8. Bucles anidados

Un bucle dentro de otro. El bucle interno se ejecuta completo por cada iteracion del externo.

```java
// Tabla de multiplicar del 1 al 3
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 10; j++) {
        System.out.printf("%d x %d = %2d%n", i, j, i * j);
    }
    System.out.println("---");
}
```

> Cuidado con la complejidad: si el bucle externo da N vueltas y el interno M, en total hay N×M iteraciones. Con N=1000 y M=1000 → 1.000.000 iteraciones.

---

## Resumen: ¿Que bucle usar?

| Situacion                                    | Bucle recomendado |
|----------------------------------------------|-------------------|
| Numero de repeticiones conocido              | `for`             |
| Recorrer array/coleccion sin necesitar indice| `for-each`        |
| Repetir mientras condicion sea true          | `while`           |
| Ejecutar al menos una vez (ej: validar input)| `do-while`        |
