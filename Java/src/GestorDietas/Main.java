package GestorDietas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programa principal del Gestor de Dietas.
 *
 * Contiene el menú de inicio y el menú de sesión.
 * IMPORTANTE: main NO lanza excepciones en su cabecera (requisito del enunciado).
 * Todas las excepciones se capturan internamente y se muestran al usuario.
 *
 * Reglas de robustez aplicadas:
 *  - Usamos leerEntero() y leerDouble() para evitar crashes con entradas inválidas
 *  - Usamos leerFecha() para validar fechas con el formato dd/MM/yyyy
 *  - Nunca llamamos a nextLine() sin control para evitar el bug del buffer de Scanner
 */
public class Main {

    // Scanner compartido por todos los métodos (una sola instancia)
    private static final Scanner sc = new Scanner(System.in);

    // Archivo donde se guardan/cargan los usuarios
    private static final String ARCHIVO_USUARIOS = "usuarios.dat";

    // Formato de fecha estándar usado en toda la aplicación
    private static final DateTimeFormatter FORMATO_FECHA =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // El gestor de usuarios es el núcleo del sistema
    private static GestorUsuarios gestorUsuarios = new GestorUsuarios();

    // ---------------------------------------------------------------
    // MAIN
    // ---------------------------------------------------------------

    /**
     * Punto de entrada del programa.
     * La cabecera NO lanza excepciones (requisito del enunciado).
     * Se intenta cargar la base de datos al inicio si existe.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== Gestor de Dietas ===");

        // Intentamos cargar usuarios guardados previamente
        cargarUsuariosAlInicio();

        // Si no hay usuarios, creamos la base de datos de ejemplo
        if (gestorUsuarios.getNumeroUsuarios() == 0) {
            cargarDatosEjemplo();
        }

        // Bucle del menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenuInicio();
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1 -> registrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> iniciarSesion();
                case 4 -> guardarYSalir();
                default -> System.out.println("[!] Opción no válida. Elige entre 1 y 4.");
            }
            if (opcion == 4) salir = true;
        }
        sc.close();
    }

    // ---------------------------------------------------------------
    // MENÚ DE INICIO
    // ---------------------------------------------------------------

    /** Muestra las opciones del menú principal */
    private static void mostrarMenuInicio() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Listar usuarios");
        System.out.println("3. Iniciar sesión");
        System.out.println("4. Guardar y salir");
        System.out.print("→ ");
    }

    /**
     * Registra un nuevo usuario según el tipo elegido.
     * Captura UsuarioException si los datos son inválidos.
     */
    private static void registrarUsuario() {
        System.out.println("\n--- REGISTRAR USUARIO ---");
        System.out.println("Tipo de usuario:");
        System.out.println("  1. Deportista");
        System.out.println("  2. Usuario Keto");
        System.out.println("  3. Paciente");
        int tipo = leerEntero("Tipo: ");
        if (tipo < 1 || tipo > 3) {
            System.out.println("[!] Tipo no válido.");
            return;
        }

        String nombre = leerTexto("Nombre: ");
        String id     = leerTexto("ID: ");

        try {
            Usuario nuevo;
            switch (tipo) {
                case 1 -> {
                    double minProt = leerDouble("Mínimo de proteínas (g): ");
                    nuevo = new Deportista(nombre, id, minProt);
                }
                case 2 -> {
                    double maxCarbs = leerDouble("Máximo de carbohidratos (g): ");
                    nuevo = new UsuarioKeto(nombre, id, maxCarbs);
                }
                default -> {
                    int maxKcal = leerEntero("Máximo de kcal por receta: ");
                    nuevo = new Paciente(nombre, id, maxKcal);
                }
            }
            gestorUsuarios.registrarUsuario(nuevo);
            System.out.println("[✓] Usuario registrado: " + nuevo.getNombre());
        } catch (UsuarioException e) {
            System.out.println("[!] Error al registrar: " + e.getMessage());
        }
    }

    /** Muestra todos los usuarios registrados */
    private static void listarUsuarios() {
        System.out.println("\n--- USUARIOS REGISTRADOS ---");
        ArrayList<Usuario> lista = gestorUsuarios.getUsuarios();
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, lista.get(i));
        }
    }

    /**
     * Inicia sesión con un usuario existente y abre el menú de sesión.
     */
    private static void iniciarSesion() {
        System.out.println("\n--- INICIAR SESIÓN ---");
        String id = leerTexto("ID del usuario: ");
        try {
            Usuario usuario = gestorUsuarios.buscarPorId(id);
            if (usuario == null) {
                System.out.println("[!] No existe un usuario con ID: " + id);
                return;
            }
            System.out.println("[✓] Sesión iniciada como: " + usuario.getNombre());
            menuSesion(usuario);
        } catch (UsuarioException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Guarda los usuarios en disco y termina */
    private static void guardarYSalir() {
        try {
            gestorUsuarios.guardar(ARCHIVO_USUARIOS);
            System.out.println("[✓] Datos guardados correctamente. ¡Hasta pronto!");
        } catch (UsuarioException e) {
            System.out.println("[!] Error al guardar: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // MENÚ DE SESIÓN (usuario ya autenticado)
    // ---------------------------------------------------------------

    /**
     * Menú principal de la sesión de un usuario.
     * Todas las operaciones sobre recetas e ingredientes están aquí.
     *
     * @param usuario el usuario que ha iniciado sesión
     */
    private static void menuSesion(Usuario usuario) {
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            mostrarMenuSesion(usuario.getNombre());
            int op = leerEntero("Elige una opción: ");
            switch (op) {
                case 1  -> registrarReceta(usuario);
                case 2  -> añadirIngrediente(usuario);
                case 3  -> eliminarIngrediente(usuario);
                case 4  -> mostrarRecetas(usuario);
                case 5  -> buscarPorFecha(usuario);
                case 6  -> buscarEntreFechas(usuario);
                case 7  -> buscarPorTipo(usuario);
                case 8  -> consultarPerfilReceta(usuario);
                case 9  -> comprobarPuedeConsumir(usuario);
                case 10 -> evaluarReceta(usuario);
                case 11 -> evaluarMenuDiario(usuario);
                case 12 -> generarInformeDiario(usuario);
                case 13 -> generarInformeSemanal(usuario);
                case 14 -> listarNecesitanCocinado(usuario);
                case 0  -> cerrarSesion = true;
                default -> System.out.println("[!] Opción no válida.");
            }
        }
        System.out.println("[✓] Sesión cerrada.");
    }

    /** Muestra las opciones del menú de sesión */
    private static void mostrarMenuSesion(String nombreUsuario) {
        System.out.println("\n--- SESIÓN: " + nombreUsuario + " ---");
        System.out.println(" 1. Registrar nueva receta");
        System.out.println(" 2. Añadir ingrediente a una receta");
        System.out.println(" 3. Eliminar ingrediente de una receta");
        System.out.println(" 4. Ver todas las recetas");
        System.out.println(" 5. Buscar recetas por fecha");
        System.out.println(" 6. Buscar recetas entre fechas");
        System.out.println(" 7. Buscar recetas por tipo de comida");
        System.out.println(" 8. Ver perfil nutricional de una receta");
        System.out.println(" 9. ¿Puedo consumir esta receta?");
        System.out.println("10. Evaluar una receta (puntuación)");
        System.out.println("11. Evaluar menú diario");
        System.out.println("12. Generar informe diario (txt)");
        System.out.println("13. Generar informe semanal (txt)");
        System.out.println("14. Listar ingredientes que necesitan cocinado");
        System.out.println(" 0. Cerrar sesión");
        System.out.print("→ ");
    }

    // ---------------------------------------------------------------
    // OPERACIONES DE RECETAS
    // ---------------------------------------------------------------

    /** Registra una nueva receta y la añade al historial del usuario */
    private static void registrarReceta(Usuario usuario) {
        System.out.println("\n--- NUEVA RECETA ---");
        String nombre = leerTexto("Nombre de la receta: ");
        LocalDate fecha = leerFecha("Fecha (dd/MM/yyyy): ");
        if (fecha == null) return;

        TipoComida tipo = leerTipoComida();
        if (tipo == null) return;

        try {
            Receta receta = new Receta(nombre, fecha, tipo);
            usuario.añadirReceta(receta);
            System.out.println("[✓] Receta '" + nombre + "' registrada.");
        } catch (RecetaException | UsuarioException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /**
     * Añade un ingrediente (básico o procesado) a una receta existente.
     * El usuario elige la receta por número de la lista.
     */
    private static void añadirIngrediente(Usuario usuario) {
        System.out.println("\n--- AÑADIR INGREDIENTE ---");
        Receta receta = elegirReceta(usuario);
        if (receta == null) return;

        System.out.println("Tipo de ingrediente:");
        System.out.println("  1. Alimento básico");
        System.out.println("  2. Alimento procesado");
        int tipo = leerEntero("Tipo: ");

        String nombre  = leerTexto("Nombre del ingrediente: ");
        double proteinas = leerDouble("Proteínas por 100g: ");
        double carbs     = leerDouble("Carbohidratos por 100g: ");
        double grasas    = leerDouble("Grasas por 100g: ");
        double cantidad  = leerDouble("Cantidad en la receta (g): ");

        try {
            // Creamos el perfil nutricional base (por 100g)
            PerfilNutricional perfil = new PerfilNutricional(proteinas, carbs, grasas);
            Ingrediente ing;

            if (tipo == 1) {
                // Alimento básico: pedimos el tipo de ingrediente (FRUTA, CARNE, etc.)
                TipoIngrediente tipoIng = leerTipoIngrediente();
                if (tipoIng == null) return;
                ing = new AlimentoBasico(nombre, perfil, cantidad, tipoIng);
            } else if (tipo == 2) {
                // Alimento procesado: pedimos preparación y si necesita cocinado
                String preparacion = leerTexto("Preparación (ej: hervido): ");
                boolean cocinado = leerBoolean("¿Necesita cocinado? (s/n): ");
                ing = new AlimentoProcesado(nombre, perfil, cantidad, preparacion, cocinado);
            } else {
                System.out.println("[!] Tipo no válido.");
                return;
            }

            receta.añadirIngrediente(ing);
            System.out.println("[✓] Ingrediente '" + nombre + "' añadido.");
        } catch (ValorNutricionalException | RecetaException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Elimina un ingrediente de una receta buscándolo por nombre */
    private static void eliminarIngrediente(Usuario usuario) {
        System.out.println("\n--- ELIMINAR INGREDIENTE ---");
        Receta receta = elegirReceta(usuario);
        if (receta == null) return;

        String nombre = leerTexto("Nombre del ingrediente a eliminar: ");
        try {
            boolean eliminado = receta.eliminarIngrediente(nombre);
            if (eliminado) {
                System.out.println("[✓] Ingrediente eliminado.");
            } else {
                System.out.println("[!] No se encontró el ingrediente: " + nombre);
            }
        } catch (RecetaException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Muestra todas las recetas del historial del usuario */
    private static void mostrarRecetas(Usuario usuario) {
        System.out.println("\n--- RECETAS DEL HISTORIAL ---");
        ArrayList<Receta> historial = usuario.getHistorial();
        if (historial.isEmpty()) {
            System.out.println("No tienes recetas registradas.");
            return;
        }
        for (int i = 0; i < historial.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, historial.get(i));
        }
    }

    /** Busca recetas por fecha exacta */
    private static void buscarPorFecha(Usuario usuario) {
        LocalDate fecha = leerFecha("Fecha a buscar (dd/MM/yyyy): ");
        if (fecha == null) return;
        try {
            ArrayList<Receta> resultado = usuario.buscarPorFecha(fecha);
            mostrarListaRecetas(resultado, "fecha " + fecha.format(FORMATO_FECHA));
        } catch (UsuarioException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Busca recetas en un intervalo de fechas */
    private static void buscarEntreFechas(Usuario usuario) {
        LocalDate inicio = leerFecha("Fecha inicio (dd/MM/yyyy): ");
        if (inicio == null) return;
        LocalDate fin = leerFecha("Fecha fin (dd/MM/yyyy): ");
        if (fin == null) return;
        try {
            ArrayList<Receta> resultado = usuario.buscarEntreFechas(inicio, fin);
            mostrarListaRecetas(resultado, "ese intervalo de fechas");
        } catch (UsuarioException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Busca recetas por tipo de comida */
    private static void buscarPorTipo(Usuario usuario) {
        TipoComida tipo = leerTipoComida();
        if (tipo == null) return;
        try {
            ArrayList<Receta> resultado = usuario.buscarPorTipo(tipo);
            mostrarListaRecetas(resultado, "tipo " + tipo);
        } catch (UsuarioException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Muestra el perfil nutricional completo de una receta elegida */
    private static void consultarPerfilReceta(Usuario usuario) {
        System.out.println("\n--- PERFIL NUTRICIONAL ---");
        Receta receta = elegirReceta(usuario);
        if (receta == null) return;
        try {
            PerfilNutricional perfil = receta.getPerfilReceta();
            System.out.println("Receta: " + receta.getNombre());
            System.out.println("Perfil: " + perfil);
            System.out.println("Kcal totales: " + receta.getKcalReceta());

            // También mostramos el desglose por ingrediente
            System.out.println("\nDesglose por ingrediente:");
            for (Ingrediente ing : receta.getIngredientes()) {
                System.out.println("  - " + ing);
            }
        } catch (RecetaException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Comprueba si el usuario puede consumir una receta */
    private static void comprobarPuedeConsumir(Usuario usuario) {
        System.out.println("\n--- ¿PUEDO CONSUMIR? ---");
        Receta receta = elegirReceta(usuario);
        if (receta == null) return;
        try {
            boolean puede = usuario.puedeConsumir(receta);
            System.out.printf("¿Puede consumir '%s'? %s%n",
                receta.getNombre(), puede ? "SÍ" : "NO");
        } catch (RecetaException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Evalúa una receta y muestra la puntuación */
    private static void evaluarReceta(Usuario usuario) {
        System.out.println("\n--- EVALUAR RECETA ---");
        Receta receta = elegirReceta(usuario);
        if (receta == null) return;
        try {
            int puntuacion = usuario.evaluarReceta(receta);
            System.out.printf("Puntuación de '%s': %d/100%n",
                receta.getNombre(), puntuacion);
        } catch (RecetaException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Evalúa el menú completo de un día */
    private static void evaluarMenuDiario(Usuario usuario) {
        System.out.println("\n--- EVALUAR MENÚ DIARIO ---");
        LocalDate fecha = leerFecha("Fecha del menú (dd/MM/yyyy): ");
        if (fecha == null) return;
        try {
            int puntuacion = usuario.evaluarMenuDiario(fecha);
            System.out.printf("Puntuación del menú del %s: %d/100%n",
                fecha.format(FORMATO_FECHA), puntuacion);
        } catch (UsuarioException | RecetaException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // INFORMES
    // ---------------------------------------------------------------

    /** Genera un informe diario en archivo de texto */
    private static void generarInformeDiario(Usuario usuario) {
        System.out.println("\n--- INFORME DIARIO ---");
        LocalDate fecha = leerFecha("Fecha del informe (dd/MM/yyyy): ");
        if (fecha == null) return;
        String ruta = leerTexto("Nombre del archivo (ej: informe.txt): ");
        try {
            GestorInformes gestor = new GestorInformes();
            gestor.generarInformeDiario(usuario, fecha, ruta);
            System.out.println("[✓] Informe generado: " + ruta);
        } catch (UsuarioException | RecetaException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Genera un informe semanal en archivo de texto */
    private static void generarInformeSemanal(Usuario usuario) {
        System.out.println("\n--- INFORME SEMANAL ---");
        LocalDate inicio = leerFecha("Inicio de semana (dd/MM/yyyy): ");
        if (inicio == null) return;
        String ruta = leerTexto("Nombre del archivo (ej: informe_semana.txt): ");
        try {
            GestorInformes gestor = new GestorInformes();
            gestor.generarInformeSemanal(usuario, inicio, ruta);
            System.out.println("[✓] Informe semanal generado: " + ruta);
        } catch (UsuarioException | RecetaException e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    /** Lista todos los ingredientes procesados que necesitan cocinado (opción extra) */
    private static void listarNecesitanCocinado(Usuario usuario) {
        System.out.println("\n--- INGREDIENTES QUE NECESITAN COCINADO ---");
        boolean hayAlguno = false;
        for (Receta r : usuario.getHistorial()) {
            for (Ingrediente ing : r.getIngredientes()) {
                // Solo los AlimentoProcesado pueden necesitar cocinado
                if (ing instanceof AlimentoProcesado ap && ap.isNecesitaCocinado()) {
                    System.out.printf("Receta: %-20s | Ingrediente: %s (prep: %s)%n",
                        r.getNombre(), ap.getNombre(), ap.getPreparacion());
                    hayAlguno = true;
                }
            }
        }
        if (!hayAlguno) {
            System.out.println("Ningún ingrediente necesita cocinado.");
        }
    }

    // ---------------------------------------------------------------
    // UTILIDADES DE MENÚ
    // ---------------------------------------------------------------

    /**
     * Muestra el historial y permite elegir una receta por número.
     * Devuelve null si el historial está vacío o la selección es inválida.
     *
     * @param usuario usuario cuyo historial se muestra
     * @return la receta elegida, o null
     */
    private static Receta elegirReceta(Usuario usuario) {
        ArrayList<Receta> historial = usuario.getHistorial();
        if (historial.isEmpty()) {
            System.out.println("[!] No tienes recetas en tu historial.");
            return null;
        }
        System.out.println("Elige una receta:");
        for (int i = 0; i < historial.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, historial.get(i).getNombre());
        }
        int eleccion = leerEntero("Número de receta: ");
        if (eleccion < 1 || eleccion > historial.size()) {
            System.out.println("[!] Número fuera de rango.");
            return null;
        }
        return historial.get(eleccion - 1);
    }

    /** Muestra una lista de recetas con mensaje si está vacía */
    private static void mostrarListaRecetas(ArrayList<Receta> lista, String criterio) {
        if (lista.isEmpty()) {
            System.out.println("[i] No hay recetas para " + criterio + ".");
            return;
        }
        System.out.println("Recetas encontradas (" + lista.size() + "):");
        for (Receta r : lista) {
            System.out.println("  - " + r);
        }
    }

    // ---------------------------------------------------------------
    // LECTURA ROBUSTA DE DATOS
    // ---------------------------------------------------------------

    /**
     * Lee un entero del teclado de forma segura.
     * Si el usuario escribe texto o deja vacío, devuelve -1 sin crashear.
     *
     * @param mensaje texto que se muestra antes de leer
     * @return entero leído, o -1 si la entrada es inválida
     */
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        String linea = sc.nextLine().trim();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            // El usuario escribió algo que no es un entero
            return -1;
        }
    }

    /**
     * Lee un double del teclado de forma segura.
     *
     * @param mensaje texto que se muestra antes de leer
     * @return double leído, o -1.0 si la entrada es inválida
     */
    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        String linea = sc.nextLine().trim();
        try {
            // Reemplazamos coma por punto para aceptar "12,5" y "12.5"
            return Double.parseDouble(linea.replace(',', '.'));
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    /**
     * Lee una línea de texto no vacía.
     *
     * @param mensaje texto que se muestra antes de leer
     * @return String leído (puede ser vacío si el usuario pulsa Enter)
     */
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    /**
     * Lee una fecha en formato dd/MM/yyyy de forma segura.
     * Muestra un mensaje de error y devuelve null si el formato es incorrecto.
     *
     * @param mensaje texto que se muestra antes de leer
     * @return LocalDate válido, o null si el formato no es correcto
     */
    private static LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        String texto = sc.nextLine().trim();
        try {
            return LocalDate.parse(texto, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            System.out.println("[!] Fecha inválida. Usa el formato dd/MM/yyyy.");
            return null;
        }
    }

    /**
     * Muestra el menú de TipoComida y devuelve el elegido.
     *
     * @return TipoComida elegido, o null si la opción es inválida
     */
    private static TipoComida leerTipoComida() {
        System.out.println("Tipo de comida:");
        System.out.println("  1. DESAYUNO");
        System.out.println("  2. COMIDA");
        System.out.println("  3. MERIENDA");
        System.out.println("  4. CENA");
        int op = leerEntero("Tipo: ");
        return switch (op) {
            case 1 -> TipoComida.DESAYUNO;
            case 2 -> TipoComida.COMIDA;
            case 3 -> TipoComida.MERIENDA;
            case 4 -> TipoComida.CENA;
            default -> { System.out.println("[!] Tipo no válido."); yield null; }
        };
    }

    /**
     * Muestra el menú de TipoIngrediente y devuelve el elegido.
     *
     * @return TipoIngrediente elegido, o null si la opción es inválida
     */
    private static TipoIngrediente leerTipoIngrediente() {
        System.out.println("Categoría del ingrediente:");
        System.out.println("  1. FRUTA");
        System.out.println("  2. VERDURA");
        System.out.println("  3. CARNE");
        System.out.println("  4. PESCADO");
        System.out.println("  5. OTROS");
        int op = leerEntero("Categoría: ");
        return switch (op) {
            case 1 -> TipoIngrediente.FRUTA;
            case 2 -> TipoIngrediente.VERDURA;
            case 3 -> TipoIngrediente.CARNE;
            case 4 -> TipoIngrediente.PESCADO;
            case 5 -> TipoIngrediente.OTROS;
            default -> { System.out.println("[!] Categoría no válida."); yield null; }
        };
    }

    /**
     * Lee una respuesta sí/no del usuario.
     *
     * @param mensaje pregunta a mostrar
     * @return true si responde "s" o "S", false en cualquier otro caso
     */
    private static boolean leerBoolean(String mensaje) {
        System.out.print(mensaje);
        String resp = sc.nextLine().trim().toLowerCase();
        return resp.equals("s") || resp.equals("si") || resp.equals("sí");
    }

    // ---------------------------------------------------------------
    // PERSISTENCIA Y DATOS DE EJEMPLO
    // ---------------------------------------------------------------

    /**
     * Intenta cargar los usuarios desde disco al inicio del programa.
     * Si el archivo no existe o hay error, continúa sin datos (silencioso).
     */
    private static void cargarUsuariosAlInicio() {
        try {
            gestorUsuarios.cargar(ARCHIVO_USUARIOS);
            System.out.println("[✓] Datos cargados: "
                + gestorUsuarios.getNumeroUsuarios() + " usuarios.");
        } catch (UsuarioException e) {
            // El archivo aún no existe en la primera ejecución — es normal
            System.out.println("[i] Base de datos no encontrada. Cargando datos de ejemplo...");
        }
    }

    /**
     * Crea una base de datos de ejemplo con los tres tipos de usuario y varias recetas.
     *
     * Esta base de datos cumple el requisito del enunciado de incluir usuarios
     * de los tres tipos con recetas para distintos días.
     *
     * Si alguna creación falla (lo cual no debería ocurrir con datos correctos),
     * imprimimos el error pero continuamos — el programa no debe crashear aquí.
     */
    private static void cargarDatosEjemplo() {
        System.out.println("[i] Creando base de datos de ejemplo...");
        try {
            // ---- DEPORTISTA: Juan Pérez ----
            Deportista juan = new Deportista("Juan Perez", "U001", 25.0);
            gestorUsuarios.registrarUsuario(juan);

            LocalDate d1 = LocalDate.of(2026, 3, 24);

            // Receta 1: Tostadas con aguacate (DESAYUNO)
            Receta tostadas = new Receta("Tostadas con aguacate", d1, TipoComida.DESAYUNO);
            tostadas.añadirIngrediente(new AlimentoBasico("Pan integral",
                new PerfilNutricional(8, 45, 2), 80, TipoIngrediente.OTROS));
            tostadas.añadirIngrediente(new AlimentoBasico("Aguacate",
                new PerfilNutricional(2, 9, 15), 100, TipoIngrediente.FRUTA));
            juan.añadirReceta(tostadas);

            // Receta 2: Pollo con arroz (COMIDA)
            Receta polloArroz = new Receta("Pollo con arroz", d1, TipoComida.COMIDA);
            polloArroz.añadirIngrediente(new AlimentoBasico("Pechuga de pollo",
                new PerfilNutricional(31, 0, 3.6), 150, TipoIngrediente.CARNE));
            polloArroz.añadirIngrediente(new AlimentoProcesado("Arroz cocido",
                new PerfilNutricional(2.5, 28, 0.3), 150, "hervido", true));
            juan.añadirReceta(polloArroz);

            // Receta 3: Ensalada templada (CENA)
            Receta ensalada = new Receta("Ensalada templada", d1, TipoComida.CENA);
            ensalada.añadirIngrediente(new AlimentoBasico("Lechuga",
                new PerfilNutricional(1.4, 2.2, 0.2), 100, TipoIngrediente.VERDURA));
            ensalada.añadirIngrediente(new AlimentoBasico("Atún en aceite",
                new PerfilNutricional(26, 0, 8), 80, TipoIngrediente.PESCADO));
            juan.añadirReceta(ensalada);

            // Segundo día para Juan (para el informe semanal)
            LocalDate d2 = LocalDate.of(2026, 3, 25);
            Receta tortilla = new Receta("Tortilla francesa", d2, TipoComida.DESAYUNO);
            tortilla.añadirIngrediente(new AlimentoBasico("Huevo",
                new PerfilNutricional(13, 0.7, 11), 120, TipoIngrediente.OTROS));
            juan.añadirReceta(tortilla);

            Receta salmon = new Receta("Salmon con verduras", d2, TipoComida.COMIDA);
            salmon.añadirIngrediente(new AlimentoBasico("Salmón",
                new PerfilNutricional(20, 0, 13), 180, TipoIngrediente.PESCADO));
            salmon.añadirIngrediente(new AlimentoBasico("Brócoli",
                new PerfilNutricional(3.7, 7, 0.4), 150, TipoIngrediente.VERDURA));
            juan.añadirReceta(salmon);

            // ---- USUARIO KETO: María López ----
            UsuarioKeto maria = new UsuarioKeto("Maria Lopez", "U002", 20.0);
            gestorUsuarios.registrarUsuario(maria);

            LocalDate d3 = LocalDate.of(2026, 3, 24);

            // Receta keto: huevos con bacon (DESAYUNO)
            Receta huevosBacon = new Receta("Huevos con bacon", d3, TipoComida.DESAYUNO);
            huevosBacon.añadirIngrediente(new AlimentoBasico("Huevo",
                new PerfilNutricional(13, 0.7, 11), 150, TipoIngrediente.OTROS));
            huevosBacon.añadirIngrediente(new AlimentoProcesado("Bacon",
                new PerfilNutricional(14, 1.4, 42), 80, "frito", true));
            maria.añadirReceta(huevosBacon);

            // Receta keto: ensalada con queso (COMIDA)
            Receta ensaladaQueso = new Receta("Ensalada con queso", d3, TipoComida.COMIDA);
            ensaladaQueso.añadirIngrediente(new AlimentoBasico("Lechuga",
                new PerfilNutricional(1.4, 2.2, 0.2), 100, TipoIngrediente.VERDURA));
            ensaladaQueso.añadirIngrediente(new AlimentoBasico("Queso curado",
                new PerfilNutricional(25, 1.3, 33), 60, TipoIngrediente.OTROS));
            maria.añadirReceta(ensaladaQueso);

            LocalDate d4 = LocalDate.of(2026, 3, 25);
            Receta aguacateSalmon = new Receta("Aguacate con salmon", d4, TipoComida.COMIDA);
            aguacateSalmon.añadirIngrediente(new AlimentoBasico("Aguacate",
                new PerfilNutricional(2, 9, 15), 120, TipoIngrediente.FRUTA));
            aguacateSalmon.añadirIngrediente(new AlimentoBasico("Salmón ahumado",
                new PerfilNutricional(18, 0, 12), 100, TipoIngrediente.PESCADO));
            maria.añadirReceta(aguacateSalmon);

            // ---- PACIENTE: Carlos Ruiz ----
            Paciente carlos = new Paciente("Carlos Ruiz", "U003", 400);
            gestorUsuarios.registrarUsuario(carlos);

            LocalDate d5 = LocalDate.of(2026, 3, 24);

            // Receta ligera: yogur con fruta (DESAYUNO)
            Receta yogurFruta = new Receta("Yogur con fruta", d5, TipoComida.DESAYUNO);
            yogurFruta.añadirIngrediente(new AlimentoBasico("Yogur desnatado",
                new PerfilNutricional(5.7, 7.8, 0.1), 150, TipoIngrediente.OTROS));
            yogurFruta.añadirIngrediente(new AlimentoBasico("Fresas",
                new PerfilNutricional(0.7, 7.7, 0.3), 100, TipoIngrediente.FRUTA));
            carlos.añadirReceta(yogurFruta);

            // Receta ligera: pechuga a la plancha con verdura (COMIDA)
            Receta pechugaVerdura = new Receta("Pechuga con verdura", d5, TipoComida.COMIDA);
            pechugaVerdura.añadirIngrediente(new AlimentoBasico("Pechuga de pollo",
                new PerfilNutricional(31, 0, 3.6), 120, TipoIngrediente.CARNE));
            pechugaVerdura.añadirIngrediente(new AlimentoBasico("Judías verdes",
                new PerfilNutricional(2, 4, 0.2), 150, TipoIngrediente.VERDURA));
            carlos.añadirReceta(pechugaVerdura);

            LocalDate d6 = LocalDate.of(2026, 3, 25);
            Receta sopa = new Receta("Sopa de verduras", d6, TipoComida.CENA);
            sopa.añadirIngrediente(new AlimentoBasico("Zanahoria",
                new PerfilNutricional(0.9, 10, 0.2), 100, TipoIngrediente.VERDURA));
            sopa.añadirIngrediente(new AlimentoProcesado("Caldo de pollo",
                new PerfilNutricional(1.5, 0.5, 0.5), 200, "cocido", false));
            carlos.añadirReceta(sopa);

            System.out.println("[✓] Base de datos de ejemplo creada con 3 usuarios y "
                + (juan.getNumeroRecetas() + maria.getNumeroRecetas() + carlos.getNumeroRecetas())
                + " recetas.");

        } catch (UsuarioException | RecetaException | ValorNutricionalException e) {
            // Esto solo ocurriría si los datos de ejemplo están mal escritos
            System.out.println("[!] Error creando datos de ejemplo: " + e.getMessage());
        }
    }
}
