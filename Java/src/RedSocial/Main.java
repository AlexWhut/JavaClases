package RedSocial;

import java.util.Scanner;

// ============================================================
// CLASE: Main  (RedSocial)
// ------------------------------------------------------------
// Punto de entrada del programa. Pide nombre y edad al usuario
// por consola y decide si puede registrarse y con que rol.
//
// Conceptos que practica:
//   - Scanner      : leer datos del teclado
//   - Excepciones  : try-catch-finally + excepcion personalizada
//   - Polimorfismo : la variable es de tipo Usuario pero el
//                    objeto real es UsuarioNormal o Administrador
// ============================================================

/**
 * Clase principal del modulo RedSocial.
 *
 * <p>Flujo del programa:</p>
 * <ol>
 *   <li>Repite el formulario hasta que el registro sea exitoso.</li>
 *   <li>Si la edad es menor de 16, lanza {@link RegistroException} y vuelve a pedir datos.</li>
 *   <li>Si el usuario escribe letras en la edad, captura el error y vuelve a intentarlo.</li>
 *   <li>Si la edad esta entre 16 y 17, crea un {@link UsuarioNormal}.</li>
 *   <li>Si la edad es 18 o mas, crea un {@link Administrador}.</li>
 *   <li>Al salir del bucle, el bloque {@code finally} cierra el Scanner.</li>
 *   <li>Llama a {@code mostrarPerfil()} del objeto creado.</li>
 * </ol>
 */
public class Main {

    /**
     * Metodo de entrada del programa.
     *
     * @param args argumentos de linea de comandos (no se usan)
     */
    public static void main(String[] args) {

        // Creamos el Scanner fuera del try para poder cerrarlo en finally
        Scanner scanner = new Scanner(System.in);

        // Guardara el usuario una vez que el registro sea valido.
        // Mientras sea null el bucle seguira pidiendo datos.
        Usuario usuario = null;

        // ==========================================================
        // WHILE: el programa no termina hasta registrarse con exito
        // ----------------------------------------------------------
        // Cada vez que hay un error (edad invalida o formato incorrecto)
        // el catch muestra el mensaje, usuario sigue siendo null y el
        // bucle vuelve a pedir los datos desde el principio.
        // Cuando el try completa sin excepcion, usuario ya no es null
        // y el bucle termina.
        // ==========================================================
        while (usuario == null) {
            try {

                // --- Pedimos el nombre ---
                System.out.print("Introduce tu nombre: ");
                String nombre = scanner.nextLine().trim();

                // --- Pedimos la edad ---
                System.out.print("Introduce tu edad: ");
                int edad = Integer.parseInt(scanner.nextLine().trim());

                // --- Validacion de edad minima ---
                // Si la edad es menor de 16 lanzamos nuestra excepcion personalizada.
                // "throw" detiene el flujo del try y pasa al bloque catch.
                if (edad < 16) {
                    throw new RegistroException("Debes tener al menos 16 anos para registrarte.");
                }

                // --- Asignacion de rol segun edad ---
                // Con 18 anos o mas el usuario tiene mas responsabilidad -> ADMINISTRADOR.
                // Con 16 o 17 anos el rol es el basico -> USUARIO.
                if (edad >= 18) {
                    // Polimorfismo: la variable es Usuario pero el objeto es Administrador
                    usuario = new Administrador(nombre, edad, Rol.ADMINISTRADOR);
                } else {
                    // edad esta entre 16 y 17 (menores de 18 pero mayores de 15)
                    usuario = new UsuarioNormal(nombre, edad, Rol.USUARIO);
                }

            // ==========================================================
            // CATCH: capturamos la excepcion de registro
            // ----------------------------------------------------------
            // Solo se ejecuta si se lanzo RegistroException dentro del try.
            // Tras mostrar el mensaje, el while vuelve a iterar porque
            // usuario sigue siendo null.
            // ==========================================================
            } catch (RegistroException e) {
                System.out.println("Error de registro: " + e.getMessage());
                System.out.println("Por favor, intentalo de nuevo.");

            // ==========================================================
            // CATCH: capturamos errores de formato al leer la edad
            // ----------------------------------------------------------
            // Si el usuario escribe letras en lugar de un numero,
            // Integer.parseInt() lanza NumberFormatException.
            // Igual que antes: el while reintenta porque usuario es null.
            // ==========================================================
            } catch (NumberFormatException e) {
                System.out.println("Error: la edad debe ser un numero entero.");
                System.out.println("Por favor, intentalo de nuevo.");
            }
        }

        // ==========================================================
        // FINALLY: cerramos el Scanner al salir del bucle
        // ----------------------------------------------------------
        // Lo ponemos fuera del while para cerrarlo una sola vez,
        // justo cuando ya no necesitamos leer mas datos del teclado.
        // ==========================================================
        scanner.close();
        System.out.println("Scanner cerrado.");

        // ==========================================================
        // MOSTRAMOS EL PERFIL
        // ----------------------------------------------------------
        // Aqui usuario NUNCA es null: el while garantiza que solo
        // llegamos a este punto con un registro exitoso.
        // Polimorfismo en accion: el mismo metodo mostrarPerfil()
        // produce salidas distintas segun el tipo real del objeto.
        // ==========================================================
        System.out.println();
        usuario.mostrarPerfil();

        // Si ademas es Administrador, mostramos su accion exclusiva.
        // Usamos instanceof para comprobar el tipo real en tiempo de ejecucion.
        if (usuario instanceof Administrador admin) {
            System.out.println();
            admin.gestionarUsuarios();
        }
    }
}
