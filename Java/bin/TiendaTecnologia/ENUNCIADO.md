# Ejercicio Práctico — Tienda de Tecnología

**Programación II | Práctica de clase**

---

## Contexto

Vas a desarrollar el sistema de caja de una tienda de productos tecnológicos. El programa gestionará el inventario, permitirá al cliente seleccionar productos, acumularlos en un carrito y generar una factura al pagar.

---


Crea un package llamado `TiendaTecnologia` dentro de `src`.

---

## Apartados

### 1. [1 punto] Enum y clase abstracta

Crea el enum `TipoProducto` con los valores: `RATON`, `TECLADO`, `PANTALLA`, `GRAFICA`.

Crea la clase abstracta `Producto` con los atributos privados `nombre` (String), `precio` (double) y `stock` (int), más un atributo `tipo` de tipo `TipoProducto`. El constructor debe validar que el precio sea mayor que 0 y el stock no sea negativo; en caso contrario lanzará una `IllegalArgumentException`. Define el método abstracto `getDescripcion()` que cada subclase implementará a su manera. Incluye getters y setters con validación.

### 2. [1 punto] Subclases (herencia y polimorfismo)

Crea las cuatro subclases que heredan de `Producto`. Cada una añade un atributo propio e implementa `getDescripcion()` devolviendo una cadena con sus características específicas:

- `Raton` — atributo extra: `boolean inalambrico`
- `Teclado` — atributo extra: `String disposicion` (ej: "QWERTY")
- `Pantalla` — atributo extra: `int pulgadas` (debe ser > 0)
- `GraficaGPU` — atributo extra: `int memoriaGB` (debe ser > 0)

Cada constructor debe llamar a `super(...)` para inicializar los atributos comunes.

### 3. [0,5 puntos] Excepciones personalizadas

Crea dos excepciones que extiendan `Exception`:

- `StockInsuficienteException`: se lanzará cuando se intente añadir al carrito un producto sin unidades disponibles.
- `PagoInsuficienteException`: se lanzará cuando el efectivo entregado no cubra el total del carrito.

### 4. [1 punto] Clase `CarritoCompra`

Gestiona los productos seleccionados antes de pagar mediante un `ArrayList<Producto>`. Implementa:

- `agregarProducto(Producto p)` — lanza `StockInsuficienteException` si el stock es 0; en caso contrario descuenta 1 unidad del stock y añade el producto.
- `calcularTotal()` — suma los precios de todos los items.
- `estaVacio()`, `vaciar()`, `mostrarCarrito()`.

### 5. [1 punto] Clase `Factura`

Cada factura tiene un ID incremental con formato `"0001"`, `"0002"`... generado con un contador `static`. Al crearse una factura el contador se incrementa automáticamente. Implementa el método `imprimir()` que muestre el ID, el nombre y edad del cliente, los productos comprados (usando `getDescripcion()` de cada uno) y el total.

### 6. [1 punto] Clase `Inventario`

Almacena todos los productos en un `ArrayList<Producto>`. Implementa:

- `inicializar()` — añade al menos 8 productos de distintos tipos con stock y precios variados.
- `mostrarInventario()` — lista todos los productos.
- `buscarProducto(String nombre)` — búsqueda parcial por nombre, devuelve el `Producto` o `null`.
- `consultarStock(TipoProducto tipo)` — devuelve el total de unidades disponibles de ese tipo.
- `buscarPorPrecioMaximo(double maxPrecio, TipoProducto tipo)` — devuelve un `ArrayList` con los productos de ese tipo cuyo precio no supera el máximo indicado.

### 7. [2 puntos] Programa principal (`Main`)

Antes de entrar al menú, pide el nombre y la edad del cliente. Crea el objeto `Cliente` y muestra un saludo personalizado.

Implementa un menú en bucle con las siguientes opciones:

```
[1] Ver inventario
[2] Añadir producto al carrito
[3] Ver carrito
[4] Vaciar carrito
[5] Pagar
[6] Consultar stock por tipo de producto
[7] Buscar productos por precio máximo
[0] Salir
```

Para la opción **Pagar**, muestra un submenú:

- **Tarjeta**: el pago se procesa automáticamente y se genera la factura.
- **Efectivo**: el usuario introduce el importe; si es insuficiente se captura `PagoInsuficienteException` y se muestra el dinero que falta sin generar factura.

### 8. [0,5 puntos] Manejo defensivo de excepciones

El programa no debe romperse bajo ninguna entrada incorrecta del usuario. Captura al menos:

- `InputMismatchException` cuando se escribe texto donde se espera un número.
- `IllegalArgumentException` al crear el cliente con datos inválidos (edad negativa, nombre vacío).
- Validación manual: el precio máximo de la opción 7 no puede ser negativo o cero.

### 9. [1 punto] Javadoc

Documenta con comentarios Javadoc (incluyendo `@param`, `@return` y `@throws`) los métodos públicos de al menos dos clases: `Inventario` y `CarritoCompra`.

---

## Nota

El programa entregado debe compilar sin errores. Recuerda: el polimorfismo se aprecia cuando recorres un `ArrayList<Producto>` y llamas a `getDescripcion()` — Java ejecutará la versión correcta de cada subclase automáticamente.
