# Ejercicio: Jerarquía de Animales con Colecciones

## Descripción

Crea un programa en Java dentro del paquete `Animales2` que modele una pequeña colección de animales usando herencia, encapsulación y polimorfismo.

---

## Clases a crear

### `Animal` (clase abstracta)
- Atributos privados: `nombre` (String), `edad` (int), `peso` (double en kg)
- Constructor con los 3 atributos
- Getters y setters para cada atributo. Los setters deben validar:
  - `nombre`: si es nulo o vacío, asignar `"Sin nombre"`
  - `edad`: no puede ser negativa (mínimo 0)
  - `peso`: no puede ser cero ni negativo (mínimo 0.0)
- Método concreto `dormir()` que imprima un mensaje
- Método abstracto `String hacerSonido()` que devuelva el sonido como texto
- `toString()` que muestre todos sus datos

### `Perro` (hereda de Animal)
- Atributo propio: `raza` (String)
- Constructor con `nombre`, `edad`, `peso`, `raza`
- Getter y setter para `raza`
- Implementa `hacerSonido()` devolviendo `"[nombre] dice: Guau guau!"`
- `toString()` que muestre los datos de Animal más la raza

### `Gato` (hereda de Animal)
- Atributo propio: `vidasRestantes` (int)
- Constructor con `nombre`, `edad`, `peso`, `vidasRestantes`
- Getter y setter para `vidasRestantes`. El setter debe validar que el valor esté entre 0 y 7
- Implementa `hacerSonido()` devolviendo `"[nombre] dice: Miau miau!"`
- `toString()` que muestre los datos de Animal más las vidas

### `Pajaro` (hereda de Animal)
- Atributo propio: `alturaMaxVuelo` (double en metros)
- Constructor con `nombre`, `edad`, `peso`, `alturaMaxVuelo`
- Getter y setter para `alturaMaxVuelo`. El setter no permite valores negativos
- Implementa `hacerSonido()` devolviendo `"[nombre] dice: Pio pio!"`
- `toString()` que muestre los datos de Animal más la altura

---

## `Main` — lo que debe hacer el programa

1. **Crear un `ArrayList<Animal>`** y agregar al menos 3 Perros, 3 Gatos y 3 Pájaros
2. **Recorrer la lista con un bucle** e imprimir cada animal (usando `toString` automáticamente via `println`)
3. **Recorrer la lista de nuevo** y llamar a `hacerSonido()` en cada animal mostrando el resultado
4. **Recorrer la lista una sola vez** usando `instanceof` para separar los tipos y calcular:
   - Peso promedio de todos los **Perros**
   - Peso promedio de todos los **Gatos**
   - Altura máxima de vuelo entre todos los **Pájaros** (y el nombre de ese pájaro)
5. Mostrar los tres resultados al final
6. **Demostrar los setters**: tomar un animal de la lista, intentar asignarle un valor inválido (ej. edad negativa) e imprimir el objeto antes y después para ver la corrección

---

## Ejemplo de salida esperada

```
========== LISTA DE ANIMALES ==========
Perro[Animal[nombre=Toby, edad=3 anios, peso=25.0 kg], raza=Labrador]
Gato[Animal[nombre=Mishi, edad=2 anios, peso=4.2 kg], vidasRestantes=7]
Pajaro[Animal[nombre=Condor, edad=6 anios, peso=9.5 kg], alturaMaxVuelo=4500.0 m]
...

========== SONIDOS ==========
Toby dice: Guau guau!
Mishi dice: Miau miau!
...

========== ESTADISTICAS ==========
Peso promedio de los 3 Perros : 21.17 kg
Peso promedio de los 3 Gatos  : 4.33 kg
Altura maxima de vuelo        : 4500.0 m (Condor)

========== MODIFICACION CON SETTERS ==========
Antes : Perro[Animal[nombre=Toby, edad=3 anios, peso=25.0 kg], raza=Labrador]
Despues: Perro[Animal[nombre=Toby, edad=0 anios, peso=26.5 kg], raza=Labrador]
```

---

## Conceptos que practica este ejercicio

| Concepto | Dónde aparece |
|---|---|
| **Herencia** | `Perro`, `Gato`, `Pajaro` extienden `Animal` |
| **Encapsulación** | Atributos privados + getters/setters con validación |
| **Abstracción** | `Animal` es abstracta, `hacerSonido()` es abstracto |
| **Polimorfismo** | Lista `Animal` con objetos distintos, mismo método distinto resultado |
| **ArrayList** | Colección dinámica de animales |
| **instanceof** | Identificar el tipo real dentro del bucle |
| **toString** | Representación en texto de cada objeto |
| **Bucles** | Recorrer la lista y acumular estadísticas |
