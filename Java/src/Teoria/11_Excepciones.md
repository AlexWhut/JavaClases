# 11 - Excepciones

Una **excepcion** es un evento que ocurre durante la ejecucion del programa y que **interrumpe su flujo normal**.

Java tiene un mecanismo para **detectar** (try/catch) y **lanzar** (throw) excepciones de forma controlada, evitando que el programa termine abruptamente con un error.

---

## ¿Por que ocurren las excepciones?

```java
int[] arr = {1, 2, 3};
System.out.println(arr[5]);         // ArrayIndexOutOfBoundsException

String s = null;
System.out.println(s.length());     // NullPointerException

int n = Integer.parseInt("abc");    // NumberFormatException

int resultado = 10 / 0;            // ArithmeticException
```

Sin manejo de excepciones, el programa **se detiene** y muestra un mensaje de error.
Con manejo correcto, el programa **responde** al error y puede continuar.

---

## Jerarquia de excepciones

```
Throwable
  ├── Error              ← Errores graves del sistema (no se deben capturar)
  │     ├── OutOfMemoryError
  │     └── StackOverflowError
  └── Exception          ← Excepciones que SI se gestionan
        ├── IOException  ← CHECKED (compilador obliga a tratarlas)
        ├── SQLException
        ├── ...
        └── RuntimeException  ← UNCHECKED (no obliga el compilador)
              ├── NullPointerException
              ├── ArrayIndexOutOfBoundsException
              ├── NumberFormatException
              ├── ClassCastException
              └── IllegalArgumentException
```

---

## Checked vs Unchecked

Esta es la distincion MAS IMPORTANTE del sistema de excepciones:

### Checked (comprobadas)
- Heredan de `Exception` (pero NO de `RuntimeException`)
- El **compilador obliga** a tratarlas: debes usar `try/catch` o declarar `throws` en el metodo
- Representan condiciones **esperables** pero externas: archivo no encontrado, error de red...

```java
// FileNotFoundException es checked → el compilador NO te deja ignorarla
FileReader f = new FileReader("archivo.txt");  // ERROR de compilacion sin try/catch o throws
```

### Unchecked (no comprobadas)
- Heredan de `RuntimeException`
- El compilador **no obliga** a tratarlas (puedes ignorarlas)
- Representan **errores de programacion**: indice fuera de rango, null, division por cero...

```java
// NullPointerException es unchecked → el compilador no avisa
String s = null;
s.length();  // compila, pero falla en ejecucion
```

> **La practica del enunciado del GestorDietas pide usar checked (no RuntimeException).**
> Esto obliga al programador a pensar en cada situacion de error.

---

## try / catch / finally

### Estructura basica

```java
try {
    // codigo que puede lanzar una excepcion
    int resultado = 10 / divisor;
} catch (ArithmeticException e) {
    // codigo que se ejecuta si ocurre ArithmeticException
    System.out.println("Error: " + e.getMessage());
}
```

### Multiples catch

```java
try {
    String s = null;
    int n = Integer.parseInt(s);     // puede lanzar NumberFormatException o NPE
    int[] arr = new int[5];
    arr[10] = n;                     // puede lanzar ArrayIndexOutOfBoundsException
} catch (NumberFormatException e) {
    System.out.println("Formato de numero invalido: " + e.getMessage());
} catch (NullPointerException e) {
    System.out.println("El valor es null");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Indice fuera de rango: " + e.getMessage());
} catch (Exception e) {
    // "atrapa todo": se ejecuta si ninguno de los anteriores coincide
    System.out.println("Error inesperado: " + e.getMessage());
}
```

> Los `catch` se comprueban en orden. Pon los mas especificos primero.
> Si pones `Exception` al principio, nunca se llegara a los mas especificos.

### Multi-catch (Java 7+)

Cuando dos excepciones se tratan igual:

```java
try {
    // ...
} catch (NumberFormatException | NullPointerException e) {
    System.out.println("Error de formato o null: " + e.getMessage());
}
```

### `finally` — siempre se ejecuta

El bloque `finally` se ejecuta **siempre**, haya o no excepcion. Ideal para liberar recursos.

```java
Scanner sc = null;
try {
    sc = new Scanner(new File("datos.txt"));
    // leer datos...
} catch (FileNotFoundException e) {
    System.out.println("Archivo no encontrado");
} finally {
    // se ejecuta siempre: con excepcion, sin excepcion, incluso con return
    if (sc != null) sc.close();   // cerramos el recurso pase lo que pase
    System.out.println("Fin del try");
}
```

### try-with-resources (Java 7+)

Alternativa mas moderna a `finally` para cerrar recursos automaticamente:

```java
// El recurso se cierra automaticamente al salir del try (haya o no excepcion)
try (Scanner sc = new Scanner(new File("datos.txt"));
     PrintWriter pw = new PrintWriter("salida.txt")) {
    // usar sc y pw
}   // aqui se cierran automaticamente
// El recurso debe implementar AutoCloseable o Closeable
```

---

## Lanzar excepciones: `throw` y `throws`

### `throw` — lanzar una excepcion desde el codigo

```java
public void setEdad(int edad) {
    if (edad < 0 || edad > 150) {
        throw new IllegalArgumentException("Edad invalida: " + edad);
        // throw crea el objeto excepcion y lo lanza
        // nada despues del throw se ejecuta
    }
    this.edad = edad;
}
```

### `throws` — declarar que un metodo puede lanzar excepciones

Solo necesario para excepciones **checked**. Le dice al que llama al metodo: "cuidado, este metodo puede lanzar esto".

```java
// Este metodo puede lanzar IOException (checked) → debo declararlo con throws
public void leerArchivo(String ruta) throws IOException {
    FileReader fr = new FileReader(ruta);  // puede lanzar FileNotFoundException (subtipo de IOException)
    // ...
}

// El que llama tiene que tratar la excepcion:
try {
    leerArchivo("datos.txt");
} catch (IOException e) {
    System.out.println("Error leyendo: " + e.getMessage());
}

// O propagar: declarar que el metodo que llama tambien puede lanzarla
public void procesarDatos() throws IOException {
    leerArchivo("datos.txt");   // no captura, propaga hacia arriba
}
```

---

## Crear excepciones propias

Puedes crear tus propias clases de excepcion para que los mensajes de error sean mas descriptivos.

```java
// Excepcion checked (extiende Exception)
public class SaldoInsuficienteException extends Exception {

    private double saldoActual;
    private double montoRetirar;

    public SaldoInsuficienteException(double saldoActual, double montoRetirar) {
        // Llamamos al constructor de Exception con un mensaje descriptivo
        super(String.format("Saldo insuficiente: tienes %.2f pero intentas retirar %.2f",
            saldoActual, montoRetirar));
        this.saldoActual  = saldoActual;
        this.montoRetirar = montoRetirar;
    }

    public double getSaldoActual()  { return saldoActual; }
    public double getMontoRetirar() { return montoRetirar; }
}
```

```java
// Uso:
public void retirar(double monto) throws SaldoInsuficienteException {
    if (monto > saldo) {
        throw new SaldoInsuficienteException(saldo, monto);
    }
    saldo -= monto;
}

// Captura:
try {
    cuenta.retirar(5000);
} catch (SaldoInsuficienteException e) {
    System.out.println(e.getMessage());
    System.out.println("Tienes disponible: " + e.getSaldoActual());
}
```

---

## Metodos utiles del objeto excepcion

```java
try {
    // ...
} catch (Exception e) {
    e.getMessage()       // mensaje de error (el String que pasaste al constructor)
    e.getClass().getName() // nombre de la clase de la excepcion
    e.printStackTrace()  // imprime la traza de llamadas (muy util para depurar)
}
```

---

## Propagacion de excepciones

Si un metodo no captura una excepcion, **sube** a quien lo llamo, y asi sucesivamente hasta el `main`. Si llega a `main` sin capturarse, el programa termina con error.

```java
// La excepcion sube la cadena de llamadas:
main() → metodA() → metodB() → metodC() → lanza excepcion

// Si metodC no la captura → sube a metodB
// Si metodB no la captura → sube a metodA
// Si metodA no la captura → sube a main
// Si main no la captura   → el programa termina con error
```

---

## Reglas de la practica (GestorDietas)

El enunciado impone estas reglas que resumen las buenas practicas:

1. **Todos los metodos deben lanzar excepcion si los parametros son invalidos** → siempre valida y lanza si es necesario
2. **No usar unchecked**: `IllegalArgumentException`, `RuntimeException`, etc. → crear clases propias checked
3. **main NO puede lanzar excepciones** → captura todo dentro del main con try/catch
4. **Documentacion Javadoc** → comentar con `@param`, `@return`, `@throws`
5. **Programa robusto** → controlar entradas de usuario (texto en lugar de numero, etc.)

```java
// Ejemplo de metodo bien escrito con excepcion propia:
public void setNombre(String nombre) throws UsuarioException {
    if (nombre == null || nombre.isBlank()) {
        throw new UsuarioException("El nombre no puede estar vacio");
    }
    this.nombre = nombre;
}
```

---

## Resumen

```
Throwable
  ├── Error              → no capturar
  └── Exception
        ├── checked      → extiende Exception, compilador obliga a tratar
        └── unchecked    → extiende RuntimeException, opcional tratarlas

try { }          → codigo que puede lanzar
catch (Tipo e) { }  → manejo del error
finally { }      → siempre se ejecuta (cerrar recursos)
try-with-resources → cierre automatico de recursos

throw  → lanzar una excepcion
throws → declarar que un metodo puede lanzar (solo checked)

Crear propia:
public class MiExcepcion extends Exception {
    public MiExcepcion(String msg) { super(msg); }
}
```
