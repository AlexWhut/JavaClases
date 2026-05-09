# 10 - Strings

`String` es una de las clases mas usadas en Java. Aunque se comporta como un tipo primitivo en algunos contextos, es un **objeto** con muchos metodos utiles.

---

## Caracteristica clave: los Strings son inmutables

Un `String` **nunca cambia**. Cuando "modificas" un String, en realidad creas uno nuevo.

```java
String s = "hola";
s.toUpperCase();           // NO modifica s — devuelve un nuevo String
System.out.println(s);     // sigue siendo "hola"

s = s.toUpperCase();       // correcto: asignas el resultado de vuelta
System.out.println(s);     // "HOLA"
```

---

## Crear Strings

```java
String s1 = "hola";          // forma literal (la mas comun)
String s2 = new String("hola"); // con new (evitar, menos eficiente)
String vacio = "";            // String vacio (longitud 0)
String nulo = null;           // sin objeto (¡cuidado! metodos lanzaran NullPointerException)
```

---

## Comparar Strings

```java
String a = "hola";
String b = "HOLA";

// MAL: == compara referencias (si son el mismo objeto en memoria), NO el contenido
System.out.println(a == b);          // puede ser false aunque el texto sea igual

// BIEN: equals() compara el contenido
System.out.println(a.equals(b));          // false (distingue mayusculas)
System.out.println(a.equalsIgnoreCase(b)); // true  (ignora mayusculas)

// Comparar orden alfabetico
System.out.println(a.compareTo(b));  // positivo: "hola" > "HOLA" en orden Unicode
```

---

## Metodos mas importantes

### Longitud e informacion basica

```java
String s = "Hola Mundo";

s.length()          // 10 — numero de caracteres
s.isEmpty()         // false — true si length() == 0
s.isBlank()         // false — true si vacio O solo espacios/tabs (Java 11+)
s.charAt(0)         // 'H' — caracter en posicion 0
s.indexOf('o')      // 1 — primera posicion de 'o'
s.lastIndexOf('o')  // 7 — ultima posicion de 'o'
s.indexOf("Mundo")  // 5 — posicion donde empieza "Mundo" (-1 si no existe)
```

### Subcadenas

```java
String s = "Hola Mundo";

s.substring(5)      // "Mundo" — desde posicion 5 hasta el final
s.substring(0, 4)   // "Hola" — desde 0 HASTA 4 (no incluye posicion 4)
//                   ↑ cuidado: [inicio, fin) — el fin es EXCLUSIVO
```

### Cambiar contenido (devuelve nuevo String)

```java
String s = "  Hola Mundo  ";

s.toUpperCase()         // "  HOLA MUNDO  "
s.toLowerCase()         // "  hola mundo  "
s.trim()                // "Hola Mundo" — elimina espacios al inicio y final
s.strip()               // igual que trim pero funciona con Unicode (Java 11+)
s.replace('o', '0')     // "  H0la Mund0  " — cambia caracter
s.replace("Hola", "Hi") // "  Hi Mundo  " — cambia subcadena
```

### Verificar contenido

```java
String s = "Hola Mundo";

s.startsWith("Hola")   // true
s.endsWith("Mundo")    // true
s.contains("la M")     // true
```

### Dividir y unir

```java
// split: divide por un patron (expresion regular)
String csv = "Ana,Luis,Carlos,Maria";
String[] partes = csv.split(",");    // ["Ana", "Luis", "Carlos", "Maria"]

for (String p : partes) {
    System.out.println(p);
}

// join: une elementos con un separador
String unido = String.join(" - ", "Ana", "Luis", "Carlos");
// "Ana - Luis - Carlos"

String unidoArray = String.join(", ", partes);
// "Ana, Luis, Carlos, Maria"
```

### Conversion a/desde otros tipos

```java
// Primitivo → String
String s1 = String.valueOf(42);       // "42"
String s2 = String.valueOf(3.14);     // "3.14"
String s3 = String.valueOf(true);     // "true"
String s4 = 42 + "";                  // "42" (concatenacion con vacio — funciona pero feo)

// String → primitivo
int n    = Integer.parseInt("42");    // 42
double d = Double.parseDouble("3.14"); // 3.14
boolean b = Boolean.parseBoolean("true"); // true
// Si el String no tiene el formato correcto → NumberFormatException
```

---

## Formatear Strings con `String.format` y `printf`

```java
String nombre = "Ana";
int edad = 25;
double altura = 1.68;

// String.format: crea un String formateado
String texto = String.format("Me llamo %s, tengo %d años y mido %.2f m",
    nombre, edad, altura);
System.out.println(texto);
// "Me llamo Ana, tengo 25 años y mido 1.68 m"

// printf: imprime directamente con formato
System.out.printf("%-10s %5d %8.2f%n", nombre, edad, altura);
// "Ana            25     1.68"
```

### Especificadores de formato mas usados

| Especificador | Tipo          | Ejemplo          | Resultado      |
|---------------|---------------|------------------|----------------|
| `%d`          | entero        | `%d`, 42         | `42`           |
| `%f`          | decimal       | `%.2f`, 3.14159  | `3.14`         |
| `%s`          | String        | `%s`, "hola"     | `hola`         |
| `%c`          | char          | `%c`, 'A'        | `A`            |
| `%b`          | boolean       | `%b`, true       | `true`         |
| `%n`          | salto linea   | `%n`             | (nueva linea)  |
| `%5d`         | ancho minimo  | `%5d`, 42        | `   42`        |
| `%-5d`        | alineado izq  | `%-5d`, 42       | `42   `        |
| `%05d`        | ceros a izq   | `%05d`, 42       | `00042`        |

---

## StringBuilder — cuando necesitas modificar mucho un String

Como `String` es inmutable, concatenar Strings en un bucle crea muchos objetos intermedios (ineficiente):

```java
// MAL: crea un nuevo String en cada iteracion
String resultado = "";
for (int i = 0; i < 1000; i++) {
    resultado += i;   // crea 1000 Strings temporales
}

// BIEN: StringBuilder modifica el mismo objeto
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);     // modifica el mismo buffer
}
String resultado = sb.toString();  // convierte a String al final
```

### Metodos de StringBuilder

```java
StringBuilder sb = new StringBuilder("Hola");

sb.append(" Mundo");        // sb: "Hola Mundo"
sb.insert(4, ",");          // sb: "Hola, Mundo"
sb.delete(4, 5);            // sb: "Hola Mundo" (elimina pos 4 hasta 4)
sb.replace(5, 10, "Java");  // sb: "Hola Java"
sb.reverse();               // sb: "avaJ aloH"
sb.length();                // longitud actual
sb.toString();              // convierte a String
```

---

## La clase `Character`

Para trabajar con caracteres individuales:

```java
char c = 'A';

Character.isLetter(c)    // true — es una letra
Character.isDigit(c)     // false — es un digito
Character.isUpperCase(c) // true — es mayuscula
Character.isLowerCase(c) // false
Character.toUpperCase(c) // 'A'
Character.toLowerCase(c) // 'a'
Character.isWhitespace(c)// false — es espacio/tab/newline
```

---

## Truco: convertir String a array de chars y viceversa

```java
String s = "hola";

// String → char[]
char[] chars = s.toCharArray();

// Procesar cada caracter
for (char c : chars) {
    System.out.print(Character.toUpperCase(c));  // HOLA
}

// char[] → String
String nuevo = new String(chars);
```

---

## Resumen de metodos mas usados

```java
s.length()                  // tamaño
s.charAt(i)                 // caracter en posicion i
s.equals(otro)              // comparar contenido
s.equalsIgnoreCase(otro)    // comparar ignorando mayusculas
s.contains("sub")           // contiene subcadena
s.startsWith("x")           // empieza por
s.endsWith("x")             // termina en
s.indexOf("x")              // posicion (-1 si no existe)
s.substring(ini, fin)       // trozo [ini, fin)
s.toUpperCase()             // mayusculas
s.toLowerCase()             // minusculas
s.trim()                    // sin espacios extremos
s.replace(a, b)             // sustituir
s.split(",")                // dividir en array
String.valueOf(n)           // numero/bool → String
Integer.parseInt(s)         // String → int
String.format(fmt, args...) // formatear
```
