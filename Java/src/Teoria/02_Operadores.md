# 02 - Operadores y expresiones

Un **operador** es un simbolo que realiza una operacion sobre uno o mas valores (operandos).
Una **expresion** es cualquier combinacion de valores, variables y operadores que produce un resultado.

---

## 1. Operadores aritmeticos

Para hacer calculos matematicos basicos.

| Operador | Operacion        | Ejemplo         | Resultado |
|----------|-----------------|-----------------|-----------|
| `+`      | Suma            | `5 + 3`         | `8`       |
| `-`      | Resta           | `5 - 3`         | `2`       |
| `*`      | Multiplicacion  | `5 * 3`         | `15`      |
| `/`      | Division        | `7 / 2`         | `3` (!)   |
| `%`      | Modulo (resto)  | `7 % 2`         | `1`       |

> **Atencion con la division entera:** si ambos operandos son `int`, el resultado es `int` (se descarta el decimal).
> `7 / 2 = 3` (no 3.5). Para obtener decimales, usa `7.0 / 2` o `(double) 7 / 2`.

```java
int a = 7, b = 2;
System.out.println(a / b);           // 3  (division entera)
System.out.println((double) a / b);  // 3.5 (division real)
System.out.println(a % b);           // 1  (7 = 2*3 + 1)
```

### Uso tipico del modulo `%`

```java
// Saber si un numero es par o impar
if (n % 2 == 0) System.out.println("Par");
else            System.out.println("Impar");

// Convertir segundos a minutos y segundos
int totalSegundos = 137;
int minutos  = totalSegundos / 60;  // 2
int segundos = totalSegundos % 60;  // 17
```

---

## 2. Operadores de asignacion

| Operador | Equivale a       | Ejemplo            |
|----------|------------------|--------------------|
| `=`      | asignar          | `x = 5`            |
| `+=`     | `x = x + n`      | `x += 3`           |
| `-=`     | `x = x - n`      | `x -= 3`           |
| `*=`     | `x = x * n`      | `x *= 2`           |
| `/=`     | `x = x / n`      | `x /= 2`           |
| `%=`     | `x = x % n`      | `x %= 3`           |

```java
int x = 10;
x += 5;   // x es ahora 15
x *= 2;   // x es ahora 30
x -= 10;  // x es ahora 20
```

---

## 3. Operadores de incremento y decremento

| Operador | Nombre         | Efecto              |
|----------|----------------|---------------------|
| `++x`    | Pre-incremento | suma 1 y luego usa  |
| `x++`    | Post-incremento| usa y luego suma 1  |
| `--x`    | Pre-decremento | resta 1 y luego usa |
| `x--`    | Post-decremento| usa y luego resta 1 |

```java
int x = 5;
System.out.println(x++);  // muestra 5, luego x queda en 6
System.out.println(++x);  // x se hace 7, luego muestra 7

int y = 5;
System.out.println(y--);  // muestra 5, luego y queda en 4
System.out.println(--y);  // y se hace 3, luego muestra 3
```

> En bucles se usa casi siempre `i++` (post-incremento), pero el efecto es el mismo porque el resultado no se usa directamente.

---

## 4. Operadores relacionales (de comparacion)

Devuelven siempre `true` o `false`.

| Operador | Significado       | Ejemplo     | Resultado |
|----------|-------------------|-------------|-----------|
| `==`     | igual a           | `5 == 5`    | `true`    |
| `!=`     | distinto de       | `5 != 3`    | `true`    |
| `>`      | mayor que         | `5 > 3`     | `true`    |
| `<`      | menor que         | `5 < 3`     | `false`   |
| `>=`     | mayor o igual     | `5 >= 5`    | `true`    |
| `<=`     | menor o igual     | `3 <= 5`    | `true`    |

> **Trampa clasica:** `==` en objetos compara **referencias** (si apuntan al mismo objeto en memoria), NO el contenido. Para Strings usa siempre `.equals()`.

```java
String a = "hola";
String b = "hola";

System.out.println(a == b);       // puede ser false (depende de la JVM)
System.out.println(a.equals(b));  // siempre true — compara el contenido

// Para ignorar mayusculas/minusculas:
System.out.println(a.equalsIgnoreCase("HOLA"));  // true
```

---

## 5. Operadores logicos

Combinan condiciones booleanas.

| Operador | Nombre | Descripcion                           | Ejemplo               |
|----------|--------|---------------------------------------|-----------------------|
| `&&`     | AND    | true solo si AMBAS son true           | `a > 0 && b > 0`     |
| `\|\|`   | OR     | true si AL MENOS UNA es true          | `a > 0 \|\| b > 0`   |
| `!`      | NOT    | invierte el valor booleano            | `!esActivo`           |

```java
int edad = 25;
boolean tieneCarnet = true;

// AND: las dos deben ser verdad
if (edad >= 18 && tieneCarnet) {
    System.out.println("Puede conducir");
}

// OR: basta con una
boolean esFinde = dia.equals("Sabado") || dia.equals("Domingo");

// NOT: invierte
if (!esFinde) {
    System.out.println("Es dia de semana");
}
```

### Evaluacion en cortocircuito (short-circuit)

Java evalua los operadores logicos de izquierda a derecha y **para en cuanto puede determinar el resultado**:

```java
// Si la primera es false, && no evalua la segunda (ya sabe que es false)
if (lista != null && lista.size() > 0) {  // seguro: si lista es null, para aqui
    // ...
}

// Si la primera es true, || no evalua la segunda (ya sabe que es true)
if (usuario != null || crearUsuarioPorDefecto()) {
    // crearUsuarioPorDefecto() solo se llama si usuario es null
}
```

---

## 6. Operador ternario

Una forma compacta de escribir un `if/else` cuando el resultado es un valor.

```
condicion ? valor_si_true : valor_si_false
```

```java
int edad = 20;
String mensaje = (edad >= 18) ? "Mayor de edad" : "Menor de edad";
System.out.println(mensaje);  // Mayor de edad

// Equivale exactamente a:
String mensaje2;
if (edad >= 18) {
    mensaje2 = "Mayor de edad";
} else {
    mensaje2 = "Menor de edad";
}
```

> Usalo para expresiones simples. Si la logica es compleja, es mejor un `if/else` normal.

---

## 7. Precedencia de operadores

Cuando hay varios operadores en una expresion, Java los evalua en este orden (de mayor a menor prioridad):

| Prioridad | Operadores                          |
|-----------|-------------------------------------|
| 1 (mayor) | `++`, `--`, `!` (unarios)           |
| 2         | `*`, `/`, `%`                       |
| 3         | `+`, `-`                            |
| 4         | `<`, `>`, `<=`, `>=`               |
| 5         | `==`, `!=`                          |
| 6         | `&&`                                |
| 7         | `\|\|`                              |
| 8 (menor) | `=`, `+=`, `-=`, ...               |

```java
int resultado = 2 + 3 * 4;   // = 2 + 12 = 14 (no 20)
int resultado2 = (2 + 3) * 4; // = 5 * 4 = 20

boolean ok = 5 > 3 && 2 < 4;  // = true && true = true
```

> **Consejo:** cuando tengas dudas, usa **parentesis**. Hacen el codigo mas claro y evitan errores.

---

## Resumen

```
Aritmeticos: + - * / %
Asignacion:  = += -= *= /= %=
Incremento:  ++ --
Relacionales: == != > < >= <=     ← con Strings usa .equals()
Logicos:     && || !
Ternario:    condicion ? a : b
```
