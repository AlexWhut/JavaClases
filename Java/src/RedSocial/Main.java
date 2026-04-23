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
 *   <li>Pide nombre y edad por consola.</li>
 *   <li>Si la edad es menor de 16, lanza {@link RegistroException}.</li>
 *   <li>Si la edad esta entre 16 y 17, crea un {@link UsuarioNormal}.</li>
 *   <li>Si la edad es 18 o mas, crea un {@link Administrador}.</li>
 *   <li>Llama a {@code mostrarPerfil()} del objeto creado.</li>
 *   <li>El bloque {@code finally} siempre cierra el Scanner.</li>
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

        // La variable se declara fuera del try para que sea accesible
        // en todo el bloque (try, catch y finally).
        Usuario usuario = null;

        // ==========================================================
        // TRY: codigo que puede lanzar excepciones
        // ----------------------------------------------------------
        // Si algo falla dentro del try, Java salta directamente al
        // bloque catch correspondiente.
        // ==========================================================
        try {

            // --- Pedimos el nombre ---
            System.out.print("Introduce tu nombre: ");
            String nombre = scanner.nextLine().trim();

            // --- Pedimos la edad ---
            System.out.print("Introduce tu edad: ");
            int edad = Integer.parseInt(scanner.nextLine().trim());

            // --- Validacion de edad minima ---
            // Si la edad es menor de 16 lanzamos nuestra excepcion personalizada.
            // "throw" detiene el flujo normal y pasa al bloque catch.
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
        // "e" contiene el objeto excepcion con el mensaje de error.
        // ==========================================================
        } catch (RegistroException e) {
            System.out.println("Error de registro: " + e.getMessage());

        // ==========================================================
        // CATCH: capturamos errores de formato al leer la edad
        // ----------------------------------------------------------
        // Si el usuario escribe letras en lugar de un numero,
        // Integer.parseInt() lanza NumberFormatException.
        // ==========================================================
        } catch (NumberFormatException e) {
            System.out.println("Error: la edad debe ser un numero entero.");

        // ==========================================================
        // FINALLY: se ejecuta SIEMPRE, haya excepcion o no
        // ----------------------------------------------------------
        // Es el lugar ideal para liberar recursos como el Scanner.
        // Si no lo cerramos podria haber fugas de memoria.
        // ==========================================================
        } finally {
            scanner.close();
            System.out.println("Scanner cerrado.");
        }

        // ==========================================================
        // MOSTRAMOS EL PERFIL si el registro fue exitoso
        // ----------------------------------------------------------
        // Si hubo excepcion, usuario sigue siendo null y no llamamos
        // a mostrarPerfil() para evitar un NullPointerException.
        // Polimorfismo en accion: el mismo metodo mostrarPerfil()
        // produce salidas distintas segun el tipo real del objeto.
        // ==========================================================
        if (usuario != null) {
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
}
