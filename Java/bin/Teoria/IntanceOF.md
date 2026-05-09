## `instanceof` en Java

Es un operador que **comprueba si un objeto es instancia de una clase** (o interfaz). Devuelve `true` o `false`.

**Sintaxis:**
```java
objeto instanceof Clase
```

**Ejemplo:**
```java
Animal a = new Perro();

if (a instanceof Perro) {
    System.out.println("Es un perro"); // → Se ejecuta
}

if (a instanceof Gato) {
    System.out.println("Es un gato"); // → No se ejecuta
}
```

**Uso típico:** antes de hacer un casting para evitar `ClassCastException`:
```java
if (a instanceof Perro) {
    Perro p = (Perro) a;
    p.ladrar();
}
```

**Desde Java 16+** se puede combinar con pattern matching:
```java
if (a instanceof Perro p) {
    p.ladrar(); // p ya está casteado automáticamente
}
```

En resumen: `instanceof` permite comprobar el tipo real de un objeto en tiempo de ejecución.
