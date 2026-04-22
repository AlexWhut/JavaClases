# 05 - Metodos

Un **metodo** es un bloque de codigo con nombre que realiza una tarea especifica.
Los metodos te permiten **organizar el codigo**, **reutilizarlo** y **evitar repeticion**.

---

## Estructura de un metodo

```
[modificadores] tipo_retorno nombre(parametros) {
    cuerpo del metodo
    return valor;    // si tipo_retorno != void
}
```

```java
// Ejemplo completo:
public static int sumar(int a, int b) {
    int resultado = a + b;
    return resultado;
}
//  ↑       ↑      ↑     ↑↑↑ parametros
// acceso  retorno nombre
```

### Modificadores de acceso

| Modificador | Visible desde...                    |
|-------------|-------------------------------------|
| `public`    | cualquier clase                     |
| `private`   | solo la propia clase                |
| `protected` | la clase y sus subclases/paquete    |
| (nada)      | solo el mismo paquete               |

---

## Tipos de retorno

### `void` — no devuelve nada

```java
public void saludar(String nombre) {
    System.out.println("Hola, " + nombre);
    // no hay return (o return; vacio para salir antes)
}
```

### Con retorno

```java
public int cuadrado(int n) {
    return n * n;   // devuelve un int
}

public String mayusculas(String texto) {
    return texto.toUpperCase();  // devuelve un String
}

public boolean esPositivo(double n) {
    return n > 0;   // devuelve boolean
}
```

> El `return` **termina el metodo inmediatamente**. Nada despues de el se ejecuta.

### Varios puntos de retorno

```java
public String clasificar(int nota) {
    if (nota >= 9)  return "Sobresaliente";
    if (nota >= 7)  return "Notable";
    if (nota >= 5)  return "Aprobado";
    return "Suspenso";    // Java exige que TODOS los caminos devuelvan algo
}
```

---

## Parametros

Los parametros son las "entradas" del metodo. Se definen como variables en la cabecera.

```java
public void mostrarInfo(String nombre, int edad, double altura) {
    System.out.printf("%s tiene %d años y mide %.2fm%n", nombre, edad, altura);
}

// Llamada: los argumentos deben coincidir en tipo y orden
mostrarInfo("Ana", 25, 1.68);
```

### Paso por valor vs paso por referencia

```java
// PRIMITIVOS: se pasa una COPIA → el metodo no puede cambiar el original
public void intentaCambiar(int x) {
    x = 99;    // solo cambia la copia local
}
int n = 5;
intentaCambiar(n);
System.out.println(n);  // sigue siendo 5

// OBJETOS/ARRAYS: se pasa la REFERENCIA → si modificas el contenido, afecta al original
public void cambiarPrimero(int[] arr) {
    arr[0] = 99;   // modifica el array ORIGINAL
}
int[] nums = {1, 2, 3};
cambiarPrimero(nums);
System.out.println(nums[0]);  // 99
```

---

## Metodos estaticos vs metodos de instancia

```java
public class Calculadora {

    // METODO ESTATICO: pertenece a la CLASE, no a un objeto concreto
    // Se llama con: Calculadora.sumar(3, 4)
    public static int sumar(int a, int b) {
        return a + b;
    }

    // METODO DE INSTANCIA: pertenece al OBJETO (necesitas crear uno primero)
    // Puede acceder a los atributos del objeto con this
    private double resultado;

    public void guardar(double valor) {
        this.resultado = valor;   // accede al atributo del objeto
    }
}

// Uso:
int suma = Calculadora.sumar(3, 4);   // estatico: sin crear objeto

Calculadora c = new Calculadora();    // instancia de instancia: crear objeto primero
c.guardar(42.0);
```

---

## Sobrecarga de metodos (Overloading)

En Java puedes tener **varios metodos con el mismo nombre** si tienen diferentes parametros.
El compilador elige cual usar segun los argumentos que le pasas.

```java
public class Impresora {

    // Version 1: imprime un int
    public void imprimir(int n) {
        System.out.println("Int: " + n);
    }

    // Version 2: imprime un double
    public void imprimir(double d) {
        System.out.println("Double: " + d);
    }

    // Version 3: imprime dos ints
    public void imprimir(int a, int b) {
        System.out.println("Dos ints: " + a + ", " + b);
    }
}

Impresora p = new Impresora();
p.imprimir(5);        // llama a version 1
p.imprimir(3.14);     // llama a version 2
p.imprimir(1, 2);     // llama a version 3
```

> Lo que distingue a los metodos sobrecargados es la **firma**: nombre + tipos y numero de parametros.
> El tipo de retorno **no** puede ser lo unico diferente.

---

## Recursion

Un metodo es **recursivo** cuando se llama a si mismo. Necesita obligatoriamente:
1. **Caso base**: condicion que detiene la recursion
2. **Caso recursivo**: llamada a si mismo con un problema mas pequeño

```java
// Factorial: n! = n * (n-1) * (n-2) * ... * 1
public static int factorial(int n) {
    // Caso base: si n es 0 o 1, el factorial es 1
    if (n <= 1) return 1;

    // Caso recursivo: n! = n * (n-1)!
    return n * factorial(n - 1);
}

System.out.println(factorial(5));
// factorial(5) = 5 * factorial(4)
//              = 5 * 4 * factorial(3)
//              = 5 * 4 * 3 * factorial(2)
//              = 5 * 4 * 3 * 2 * factorial(1)
//              = 5 * 4 * 3 * 2 * 1 = 120
```

> Sin el caso base → recursion infinita → `StackOverflowError`.
> La recursion es elegante pero puede ser mas lenta que la iteracion. Usala cuando el problema sea naturalmente recursivo (arboles, directorios, etc.).

---

## Varargs (numero variable de argumentos)

Permite pasar cualquier numero de argumentos del mismo tipo. Se accede como un array.

```java
public static int sumarTodos(int... numeros) {
    // numeros es un int[] internamente
    int total = 0;
    for (int n : numeros) total += n;
    return total;
}

sumarTodos(1, 2);           // 3
sumarTodos(1, 2, 3, 4, 5);  // 15
sumarTodos();               // 0
```

> El varargs debe ser siempre el **ultimo parametro** de la lista.

---

## Buenas practicas

```java
// MAL: un metodo que hace demasiadas cosas
public void procesarPedido(Pedido p) {
    // valida, calcula precio, envia email, actualiza bbdd, imprime ticket...
}

// BIEN: metodos pequeños con una sola responsabilidad
public boolean validarPedido(Pedido p) { ... }
public double calcularTotal(Pedido p) { ... }
public void enviarConfirmacion(Pedido p) { ... }
```

- Un metodo debe hacer **una sola cosa** (principio de responsabilidad unica)
- Nombre descriptivo con **verbo**: `calcular`, `obtener`, `validar`, `mostrar`
- Si el metodo es largo, probablemente se puede dividir en metodos mas pequeños
- Maximo ~20-30 lineas por metodo (orientativo)

---

## Resumen

```
[acceso] [static] tipo_retorno nombre(tipo param, ...) {
    ...
    return valor;
}

void       → no devuelve nada
static     → se llama con NombreClase.metodo()
Sobrecarga → mismo nombre, distintos parametros
Recursion  → se llama a si mismo (necesita caso base)
```
