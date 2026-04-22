> Ver el indice completo de teoria: [00_Indice.md](00_Indice.md)

# Los 4 Pilares de la Programacion Orientada a Objetos en Java

La Programacion Orientada a Objetos (POO) en Java se basa en 4 pilares fundamentales:

1. Encapsulacion
2. Abstraccion
3. Herencia
4. Polimorfismo

Estos pilares ayudan a crear codigo mas organizado, reutilizable, mantenible y cercano a como pensamos los problemas del mundo real.

## 1) Encapsulacion

La encapsulacion consiste en proteger los datos internos de una clase y controlar el acceso a ellos.

En Java se aplica usando:

- Atributos privados (`private`)
- Metodos publicos para consultar o modificar datos (getters y setters)

### Idea principal

Los objetos no deberian exponer su estado interno libremente. En su lugar, la propia clase define reglas para mantener los datos validos.

### Ejemplo en Java

```java
public class CuentaBancaria {
	private double saldo;

	public CuentaBancaria(double saldoInicial) {
		if (saldoInicial < 0) {
			throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
		}
		this.saldo = saldoInicial;
	}

	public double getSaldo() {
		return saldo;
	}

	public void depositar(double monto) {
		if (monto <= 0) {
			throw new IllegalArgumentException("El deposito debe ser mayor que 0");
		}
		saldo += monto;
	}

	public void retirar(double monto) {
		if (monto <= 0 || monto > saldo) {
			throw new IllegalArgumentException("Retiro invalido");
		}
		saldo -= monto;
	}
}
```

### Ventajas

- Evita modificaciones incorrectas del estado interno
- Mejora la seguridad de los datos
- Facilita el mantenimiento y la depuracion

## 2) Abstraccion

La abstraccion consiste en mostrar solo lo esencial de un objeto y ocultar los detalles internos de implementacion.

En Java se logra con:

- Interfaces (`interface`)
- Clases abstractas (`abstract class`)

### Idea principal

El usuario de una clase sabe que puede hacer, sin necesitar saber exactamente como esta implementado por dentro.

### Ejemplo en Java con interfaz

```java
public interface MetodoPago {
	void pagar(double monto);
}
```

```java
public class PagoTarjeta implements MetodoPago {
	@Override
	public void pagar(double monto) {
		System.out.println("Pago con tarjeta por: " + monto);
	}
}
```

```java
public class PagoPaypal implements MetodoPago {
	@Override
	public void pagar(double monto) {
		System.out.println("Pago con PayPal por: " + monto);
	}
}
```

### Ventajas

- Reduce la complejidad visible
- Permite cambiar implementaciones sin romper el resto del sistema
- Hace el codigo mas flexible y escalable

## 3) Herencia

La herencia permite crear una clase nueva a partir de otra existente.

La clase hija hereda atributos y metodos de la clase padre, y puede agregar o modificar comportamientos.

En Java se usa la palabra clave `extends`.

### Idea principal

Si varias clases comparten caracteristicas comunes, se colocan en una superclase para reutilizar codigo y evitar duplicacion.

### Ejemplo en Java

```java
public class Animal {
	protected String nombre;

	public Animal(String nombre) {
		this.nombre = nombre;
	}

	public void hacerSonido() {
		System.out.println("Sonido generico");
	}
}
```

```java
public class Perro extends Animal {
	public Perro(String nombre) {
		super(nombre);
	}

	@Override
	public void hacerSonido() {
		System.out.println(nombre + " dice: Guau");
	}
}
```

### Ventajas

- Reutilizacion de codigo
- Mejor organizacion de clases por jerarquias
- Facilita extender funcionalidades

Nota: aunque la herencia es poderosa, no siempre es la mejor opcion. En muchos casos, la composicion (tener objetos dentro de otros) puede ser una mejor alternativa.

## 4) Polimorfismo

Polimorfismo significa "muchas formas". Permite que una referencia de tipo padre o interfaz apunte a objetos de distintas clases hijas, y que cada objeto responda segun su propia implementacion.

### Idea principal

Se puede usar una misma operacion sobre distintos objetos, obteniendo comportamientos diferentes segun el tipo real del objeto.

### Ejemplo en Java

```java
public class Gato extends Animal {
	public Gato(String nombre) {
		super(nombre);
	}

	@Override
	public void hacerSonido() {
		System.out.println(nombre + " dice: Miau");
	}
}
```

```java
public class Main {
	public static void main(String[] args) {
		Animal a1 = new Perro("Firulais");
		Animal a2 = new Gato("Michi");

		a1.hacerSonido(); // Firulais dice: Guau
		a2.hacerSonido(); // Michi dice: Miau
	}
}
```

### Ventajas

- Permite escribir codigo mas general y reutilizable
- Facilita agregar nuevas clases sin modificar mucho codigo existente
- Es clave para arquitecturas limpias y patrones de diseno

## Resumen Final

- Encapsulacion: protege datos y controla el acceso
- Abstraccion: muestra lo esencial y oculta detalles internos
- Herencia: reutiliza y extiende comportamiento entre clases
- Polimorfismo: misma interfaz, diferentes comportamientos

Si dominas estos 4 pilares, tendras una base muy solida para programar en Java con buenas practicas.
