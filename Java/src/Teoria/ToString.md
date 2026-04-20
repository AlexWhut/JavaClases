## `toString()` en Java

Es un método de la clase `Object` que **todos los objetos heredan**. Devuelve una representación en texto del objeto.

**Por defecto** devuelve algo como `Perro@1b6d3586` (clase + dirección de memoria) — poco útil.

**Se sobreescribe** con `@Override` para que devuelva información útil:

```java
public class Perro {
    String nombre;
    int edad;

    @Override
    public String toString() {
        return "Perro{nombre=" + nombre + ", edad=" + edad + "}";
    }
}
```

**Se llama automáticamente** cuando usas el objeto en un `System.out.println()`:

```java
Perro p = new Perro("Rex", 3);
System.out.println(p); // → Perro{nombre=Rex, edad=3}
```

En resumen: sobreescribir `toString()` te permite controlar cómo se muestra un objeto como texto.
