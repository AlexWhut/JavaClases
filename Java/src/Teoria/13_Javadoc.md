# 13 - Javadoc: Documentacion de codigo en Java

**Javadoc** es el sistema oficial de Java para documentar codigo fuente. Genera paginas HTML a partir de comentarios especiales escritos directamente en el codigo.

Un comentario Javadoc empieza con `/**` (dos asteriscos) y termina con `*/`. Se coloca **justo antes** de la clase, metodo o campo que documenta.

```java
/**
 * Descripcion de lo que hace esto.
 */
public class MiClase { ... }
```

---

## Tipos de comentarios en Java

| Tipo | Sintaxis | Uso |
|------|----------|-----|
| Comentario de linea | `// texto` | Aclaracion rapida dentro del codigo |
| Comentario de bloque | `/* texto */` | Bloque de texto libre, no genera documentacion |
| Comentario Javadoc | `/** texto */` | Documenta clases, metodos y campos; genera HTML |

---

## Estructura de un comentario Javadoc

```java
/**
 * Primera linea: descripcion breve (aparece en el resumen).
 *
 * Parrafo adicional con mas detalle (opcional).
 * Puede ocupar varias lineas.
 *
 * @etiqueta valor   ← etiquetas de documentacion
 */
```

---

## Etiquetas Javadoc

### `@param`
Documenta **un parametro** de un metodo o constructor.
Una etiqueta por cada parametro.

```java
/**
 * Suma dos numeros enteros.
 *
 * @param a primer sumando
 * @param b segundo sumando
 */
public int sumar(int a, int b) {
    return a + b;
}
```

---

### `@return`
Describe el **valor de retorno** de un metodo.
No se usa en metodos `void`.

```java
/**
 * Calcula el area de un rectangulo.
 *
 * @param base   longitud de la base
 * @param altura longitud de la altura
 * @return el area del rectangulo (base * altura)
 */
public double calcularArea(double base, double altura) {
    return base * altura;
}
```

---

### `@throws` / `@exception`
Documenta una **excepcion** que el metodo puede lanzar.
Usa uno por cada excepcion distinta. `@throws` y `@exception` son sinonimos.

```java
/**
 * Divide dos numeros.
 *
 * @param dividendo numero a dividir
 * @param divisor   numero por el que se divide
 * @return resultado de la division
 * @throws ArithmeticException si el divisor es cero
 */
public double dividir(double dividendo, double divisor) {
    if (divisor == 0) throw new ArithmeticException("Division por cero");
    return dividendo / divisor;
}
```

---

### `@author`
Indica el **autor** de la clase o fichero.
Solo se usa en comentarios de clase, no de metodo.

```java
/**
 * Representa una cuenta bancaria.
 *
 * @author Alex
 */
public class CuentaBancaria { ... }
```

---

### `@version`
Indica la **version** actual de la clase.

```java
/**
 * Gestor de usuarios del sistema.
 *
 * @author Alex
 * @version 2.1
 */
public class GestorUsuarios { ... }
```

---

### `@since`
Indica desde **que version** del proyecto existe esta clase o metodo.

```java
/**
 * Metodo que exporta datos a CSV.
 *
 * @since 1.5
 */
public void exportarCSV() { ... }
```

---

### `@deprecated`
Marca un metodo o clase como **obsoleto**: sigue funcionando pero no deberia usarse.
El compilador muestra una advertencia cuando alguien lo llama.
Acompanarlo siempre de `@see` o explicacion de la alternativa.

```java
/**
 * Calcula el salario bruto.
 *
 * @deprecated Usa {@link #calcularSalarioNeto()} en su lugar.
 */
@Deprecated
public double calcularSalario() {
    return sueldo;
}
```

---

### `@see`
Añade una **referencia cruzada** a otra clase, metodo o URL.
Puede aparecer varias veces.

```java
/**
 * Clase que representa un empleado.
 *
 * @see Departamento
 * @see GestorEmpleados#contratar(Empleado)
 * @see <a href="https://docs.oracle.com/javase/8/docs/">Docs oficiales</a>
 */
public class Empleado { ... }
```

---

### `{@link}` y `{@linkplain}`
Insertan un **enlace clicable** a otra clase o metodo dentro del texto de la descripcion.
`{@link}` usa fuente de codigo (monospace); `{@linkplain}` usa texto normal.

```java
/**
 * Convierte el objeto a String usando {@link Object#toString()}.
 * Consulta tambien {@linkplain CuentaBancaria la clase CuentaBancaria}.
 */
public String serializar() { ... }
```

---

### `{@code}`
Muestra un fragmento de **codigo** con fuente monospace sin interpretar HTML ni etiquetas Javadoc.
Se usa dentro del texto de la descripcion.

```java
/**
 * Lanza una excepcion si el valor es negativo.
 * Ejemplo de uso: {@code setEdad(-1)} lanzara {@link IllegalArgumentException}.
 *
 * @param edad valor entero, debe ser >= 0
 * @throws IllegalArgumentException si {@code edad < 0}
 */
public void setEdad(int edad) {
    if (edad < 0) throw new IllegalArgumentException("Edad negativa");
    this.edad = edad;
}
```

---

### `{@value}`
Muestra el **valor literal** de una constante estatica `static final`.

```java
/**
 * Numero maximo de intentos permitidos. Valor: {@value}.
 */
public static final int MAX_INTENTOS = 3;
```

---

### `{@inheritDoc}`
**Hereda** automaticamente el comentario Javadoc de la clase padre o interfaz implementada.
Muy util al sobreescribir metodos para no repetir la documentacion.

```java
public class Gato extends Animal {

    /**
     * {@inheritDoc}
     * En el caso del gato, emite un "Miau".
     */
    @Override
    public String hacerSonido() {
        return "Miau";
    }
}
```

---

### `@param <T>` (tipos genericos)
Para documentar **parametros de tipo** en clases o metodos genericos.

```java
/**
 * Contenedor generico que almacena un unico elemento.
 *
 * @param <T> el tipo del elemento almacenado
 */
public class Caja<T> {

    private T elemento;

    /**
     * Guarda un elemento en la caja.
     *
     * @param elemento el objeto a almacenar, no puede ser null
     */
    public void guardar(T elemento) {
        this.elemento = elemento;
    }
}
```

---

## Ejemplo completo

El siguiente ejemplo muestra una clase con todos los elementos de documentacion aplicados:

```java
/**
 * Representa una cuenta bancaria con operaciones basicas de ingreso y retirada.
 *
 * <p>Permite gestionar el saldo de un cliente de forma segura,
 * lanzando excepciones ante operaciones invalidas.</p>
 *
 * @author Alex
 * @version 1.0
 * @since 1.0
 * @see Cliente
 */
public class CuentaBancaria {

    /** Saldo minimo permitido en cualquier cuenta. Valor: {@value}. */
    public static final double SALDO_MINIMO = 0.0;

    private String titular;
    private double saldo;

    /**
     * Crea una cuenta bancaria con saldo inicial.
     *
     * @param titular nombre del propietario de la cuenta
     * @param saldoInicial cantidad inicial, debe ser >= {@value #SALDO_MINIMO}
     * @throws IllegalArgumentException si el saldo inicial es negativo
     */
    public CuentaBancaria(String titular, double saldoInicial) {
        if (saldoInicial < SALDO_MINIMO) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    /**
     * Ingresa dinero en la cuenta.
     *
     * @param cantidad importe a ingresar, debe ser mayor que 0
     * @throws IllegalArgumentException si {@code cantidad <= 0}
     */
    public void ingresar(double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        saldo += cantidad;
    }

    /**
     * Retira dinero de la cuenta si hay saldo suficiente.
     *
     * @param cantidad importe a retirar, debe ser mayor que 0
     * @return el nuevo saldo tras la retirada
     * @throws IllegalArgumentException si {@code cantidad <= 0}
     * @throws IllegalStateException    si el saldo es insuficiente
     * @see #ingresar(double)
     */
    public double retirar(double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        if (cantidad > saldo) throw new IllegalStateException("Saldo insuficiente");
        saldo -= cantidad;
        return saldo;
    }

    /**
     * Devuelve el saldo actual de la cuenta.
     *
     * @return saldo disponible en euros
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Cuenta[" + titular + ", saldo=" + saldo + "]";
    }
}
```

---

## Resumen de etiquetas

```
@param nombre        → documenta un parametro de entrada
@return              → documenta el valor devuelto
@throws Clase        → documenta una excepcion que puede lanzarse
@author              → autor de la clase
@version             → version actual de la clase
@since               → version desde la que existe
@deprecated          → marca algo como obsoleto
@see                 → referencia cruzada a otra clase/metodo/URL
{@link Clase#metodo} → enlace clicable dentro del texto (fuente codigo)
{@linkplain ...}     → enlace clicable dentro del texto (fuente normal)
{@code expresion}    → fragmento de codigo dentro del texto
{@value}             → valor de una constante static final
{@inheritDoc}        → hereda el comentario del padre o interfaz
@param <T>           → documenta parametros de tipo generico
```

> Las etiquetas que van entre llaves `{@...}` se usan **dentro** del texto de la descripcion.
> Las etiquetas que empiezan con `@` (sin llaves) van **en su propia linea**, despues de la descripcion.
