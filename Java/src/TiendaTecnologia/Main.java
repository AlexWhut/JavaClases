package TiendaTecnologia;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Punto de entrada de la aplicacion "Tienda de Tecnologia".
 *
 * Conceptos que se practican aqui:
 *  - Herencia y polimorfismo (Producto -> Raton, Teclado, Pantalla, GraficaGPU)
 *  - Abstraccion (clase abstracta Producto, metodo abstracto getDescripcion)
 *  - Encapsulacion (getters/setters con validacion en todas las clases)
 *  - Excepciones personalizadas (StockInsuficienteException, PagoInsuficienteException)
 *  - Manejo defensivo de excepciones (InputMismatchException, IllegalArgumentException)
 *  - Colecciones ArrayList<Producto> (polimorfismo con tipos genericos)
 *  - Metodos de busqueda sobre colecciones
 */
public class Main {

    public static void main(String[] args) {

        // NoSuchElementException ocurre en algunos IDEs cuando el terminal
        // integrado no conecta stdin correctamente. Usar el terminal nativo:
        //   java TiendaTecnologia.Main
        Scanner sc = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("      BIENVENIDO A TIENDA TECH          ");
        System.out.println("========================================");

        // ----------------------------------------------------------------
        // PASO 1 - Registrar al cliente antes de entrar al menu.
        // Pedimos nombre y edad. Si el usuario escribe una letra donde
        // se espera un numero, capturamos InputMismatchException para que
        // el programa NO se rompa y muestre un mensaje comprensible.
        // ----------------------------------------------------------------
        Cliente cliente = pedirDatosCliente(sc);

        System.out.println("\nHola, " + cliente.getNombre() + "! En que podemos ayudarte hoy?\n");

        // ----------------------------------------------------------------
        // PASO 2 - Preparar el inventario y el carrito.
        // ----------------------------------------------------------------
        Inventario inventario = new Inventario();
        inventario.inicializar();   // carga los productos de ejemplo

        CarritoCompra carrito = new CarritoCompra();

        // ----------------------------------------------------------------
        // PASO 3 - Bucle principal del menu.
        // Se repite hasta que el usuario elige la opcion 0 (salir).
        // ----------------------------------------------------------------
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();

            // Capturamos InputMismatchException: si el usuario escribe "abc"
            // donde se espera un int, no queremos que el programa falle.
            try {
                opcion = sc.nextInt();
                sc.nextLine(); // limpiar el salto de linea que queda en el buffer
            } catch (InputMismatchException e) {
                System.out.println("  [!] Introduce un numero del menu, por favor.");
                sc.nextLine(); // limpiar el buffer del scanner
                continue;      // volver al inicio del bucle
            } catch (java.util.NoSuchElementException e) {
                System.out.println("  [!] No se recibio entrada. Saliendo del programa.");
                System.exit(1);
            }

            // Procesamos cada opcion del menu con un switch
            switch (opcion) {

                // ------------------------------------------------------------
                // OPCION 1 - Ver inventario
                // ------------------------------------------------------------
                case 1:
                    inventario.mostrarInventario();
                    break;

                // ------------------------------------------------------------
                // OPCION 2 - Anadir producto al carrito
                // Buscamos el producto por nombre en el inventario.
                // Si no hay stock, capturamos StockInsuficienteException.
                // ------------------------------------------------------------
                case 2:
                    inventario.mostrarInventario();
                    System.out.print("  Escribe el nombre del producto a anadir: ");
                    String nombreBuscado = sc.nextLine();

                    Producto encontrado = inventario.buscarProducto(nombreBuscado);

                    if (encontrado == null) {
                        System.out.println("  [!] Producto no encontrado. Revisa el nombre.");
                    } else {
                        try {
                            // agregarProducto puede lanzar StockInsuficienteException
                            carrito.agregarProducto(encontrado);
                        } catch (StockInsuficienteException e) {
                            // Capturamos la excepcion personalizada y mostramos su mensaje
                            System.out.println("  [X] Error de stock: " + e.getMessage());
                        }
                    }
                    break;

                // ------------------------------------------------------------
                // OPCION 3 - Ver carrito
                // ------------------------------------------------------------
                case 3:
                    carrito.mostrarCarrito();
                    break;

                // ------------------------------------------------------------
                // OPCION 4 - Vaciar carrito
                // Devolvemos el stock al inventario antes de vaciar.
                // ------------------------------------------------------------
                case 4:
                    if (carrito.estaVacio()) {
                        System.out.println("  El carrito ya esta vacio.");
                    } else {
                        // Devolver el stock de cada producto antes de vaciar
                        for (Producto p : carrito.getItems()) {
                            p.setStock(p.getStock() + 1);
                        }
                        carrito.vaciar();
                        System.out.println("  Carrito vaciado. Stock restaurado.");
                    }
                    break;

                // ------------------------------------------------------------
                // OPCION 5 - Pagar
                // Submenu: tarjeta (automatico) o efectivo (pide importe).
                // Si el efectivo es insuficiente -> PagoInsuficienteException.
                // ------------------------------------------------------------
                case 5:
                    if (carrito.estaVacio()) {
                        System.out.println("  [!] El carrito esta vacio. Anade productos primero.");
                        break;
                    }
                    carrito.mostrarCarrito();
                    procesarPago(sc, carrito, cliente);
                    break;

                // ------------------------------------------------------------
                // OPCION 6 - Consultar stock de un tipo de producto
                // Usamos el metodo consultarStock(TipoProducto) del Inventario.
                // ------------------------------------------------------------
                case 6:
                    System.out.println("  Que tipo de producto quieres consultar?");
                    System.out.println("  [1] Ratones  [2] Teclados  [3] Pantallas  [4] Graficas");
                    int tipoOpcion = leerEnteroSeguro(sc, "  Opcion: ");
                    TipoProducto tipoConsulta = opcionATipo(tipoOpcion);
                    if (tipoConsulta == null) {
                        System.out.println("  Opcion no valida.");
                    } else {
                        int stockTotal = inventario.consultarStock(tipoConsulta);
                        System.out.printf("  Quedan %d unidades de tipo %s en total.%n",
                                stockTotal, tipoConsulta);
                    }
                    break;

                // ------------------------------------------------------------
                // OPCION 7 - Buscar productos de un tipo hasta precio maximo
                // Ejemplo tipico de funcion de busqueda con filtros en arrays.
                // ------------------------------------------------------------
                case 7:
                    System.out.println("  Que tipo de producto quieres filtrar?");
                    System.out.println("  [1] Ratones  [2] Teclados  [3] Pantallas  [4] Graficas");
                    int tipoFiltro = leerEnteroSeguro(sc, "  Opcion: ");
                    TipoProducto tipoFiltrado = opcionATipo(tipoFiltro);
                    if (tipoFiltrado == null) {
                        System.out.println("  Opcion no valida.");
                        break;
                    }

                    // Validacion manual: el precio maximo no puede ser negativo
                    double maxPrecio = leerDecimalSeguro(sc, "  Precio maximo (euros): ");
                    if (maxPrecio <= 0) {
                        System.out.println("  [!] El precio maximo debe ser mayor que 0.");
                        break;
                    }

                    ArrayList<Producto> filtrados = inventario.buscarPorPrecioMaximo(maxPrecio, tipoFiltrado);

                    if (filtrados.isEmpty()) {
                        System.out.printf("  No hay productos de tipo %s por debajo de %.2f euros.%n",
                                tipoFiltrado, maxPrecio);
                    } else {
                        System.out.printf("  Productos de tipo %s hasta %.2f euros:%n", tipoFiltrado, maxPrecio);
                        for (Producto p : filtrados) {
                            // POLIMORFISMO: getDescripcion() ejecuta la version correcta
                            System.out.println("    -> " + p.getDescripcion());
                        }
                    }
                    break;

                // ------------------------------------------------------------
                // OPCION 0 - Salir
                // ------------------------------------------------------------
                case 0:
                    if (!carrito.estaVacio()) {
                        System.out.println("  [!] Tienes productos en el carrito sin pagar.");
                        System.out.println("  Vuelve a entrar para completar la compra.");
                    }
                    System.out.println("\nHasta pronto, " + cliente.getNombre() + "!");
                    break;

                default:
                    System.out.println("  [!] Opcion no reconocida. Elige un numero del menu.");
            }
        }

        sc.close();
    }

    // ========================================================================
    // METODOS AUXILIARES
    // Los metodos auxiliares permiten dividir el Main en partes mas pequenas
    // y reutilizables. Cada uno hace UNA sola cosa.
    // ========================================================================

/**
 * Pide nombre y edad al usuario hasta obtener datos validos.
 * Demuestra validacion de entrada y manejo de excepciones.
 *
 * @param sc Scanner abierto para leer la entrada.
 * @return Un objeto Cliente valido.
 */
private static Cliente pedirDatosCliente(Scanner sc) {

    Cliente cliente = null;

    // Repetimos hasta conseguir un cliente valido
    while (cliente == null) {

        try {

            System.out.print("Nombre del cliente: ");
            String nombre = sc.nextLine();

            // leerEnteroSeguro ya maneja errores internamente
            int edad = leerEnteroSeguro(sc, "Edad del cliente: ");

            // Crear cliente
            cliente = new Cliente(nombre, edad);

        } catch (IllegalArgumentException e) {

            System.out.println("  [!] Datos invalidos: "
                    + e.getMessage()
                    + " Intentalo de nuevo.\n");

        } catch (Exception e) {

            // El terminal del IDE cerro el stream de entrada
            System.out.println("  [!] No se recibio entrada. Saliendo del programa.");

            System.exit(1);
                sc.close();
        }
    }

    return cliente;
}

    /**
     * Muestra el submenu de pago y lo procesa.
     * Demuestra: try-catch con PagoInsuficienteException.
     *
     * @param sc      Scanner abierto.
     * @param carrito Carrito con los productos a pagar.
     * @param cliente Cliente que realiza el pago.
     */
    private static void procesarPago(Scanner sc, CarritoCompra carrito, Cliente cliente) {
        System.out.println("\n  Como desea pagar?");
        System.out.println("  [1] Tarjeta bancaria");
        System.out.println("  [2] Efectivo");
        int metodoPago = leerEnteroSeguro(sc, "  Opcion: ");

        double total = carrito.calcularTotal();
        String metodoTexto;

        if (metodoPago == 1) {
            // Tarjeta: pago automatico, siempre exitoso
            metodoTexto = "Tarjeta";
            System.out.println("  Procesando pago con tarjeta...");
            System.out.println("  [OK] Pago exitoso.");

        } else if (metodoPago == 2) {
            // Efectivo: el cliente introduce el importe
            metodoTexto = "Efectivo";
            double efectivo = leerDecimalSeguro(sc, "  Introduzca el efectivo (euros): ");

            try {
                // Validamos que el efectivo cubre el total
                if (efectivo < total) {
                    // Lanzamos nuestra excepcion personalizada con un mensaje claro
                    throw new PagoInsuficienteException(
                        String.format("Efectivo insuficiente. Faltan %.2f euros.", total - efectivo)
                    );
                }
                double cambio = efectivo - total;
                System.out.printf("  [OK] Pago aceptado. Cambio: %.2f euros%n", cambio);

            } catch (PagoInsuficienteException e) {
                System.out.println("  [X] " + e.getMessage());
                return; // Salimos sin generar factura
            }

        } else {
            System.out.println("  Opcion no valida. Cancelando pago.");
            return;
        }

        // Generamos la factura y la imprimimos
        Factura factura = new Factura(cliente, carrito.getItems(), total, metodoTexto);
        factura.imprimir();

        // Vaciamos el carrito (el stock ya se descontio al agregar los productos)
        carrito.vaciar();
    }

    /**
     * Muestra el menu principal de opciones.
     */
    private static void mostrarMenu() {
        System.out.println("-------- MENU PRINCIPAL --------");
        System.out.println("  [1] Ver inventario");
        System.out.println("  [2] Anadir producto al carrito");
        System.out.println("  [3] Ver carrito");
        System.out.println("  [4] Vaciar carrito");
        System.out.println("  [5] Pagar");
        System.out.println("  [6] Consultar stock por tipo");
        System.out.println("  [7] Buscar por precio maximo");
        System.out.println("  [0] Salir");
        System.out.print("  Elige una opcion: ");
    }

    /**
     * Lee un entero del Scanner de forma segura.
     * Si el usuario escribe texto, captura InputMismatchException y
     * devuelve -1 para que el codigo llamante lo gestione.
     *
     * @param sc     Scanner abierto.
     * @param prompt Mensaje que se muestra antes de leer.
     * @return El entero introducido, o -1 si la entrada no era numerica.
     */
    private static int leerEnteroSeguro(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            int valor = sc.nextInt();
            sc.nextLine(); // limpiar buffer
            return valor;
        } catch (InputMismatchException e) {
            sc.nextLine(); // limpiar buffer con la entrada incorrecta
            System.out.println("  [!] Se esperaba un numero entero.");
            return -1;
        }
        // NoSuchElementException NO se captura aqui: se deja subir al llamante
    }

    /**
     * Lee un numero decimal (double) del Scanner de forma segura.
     *
     * @param sc     Scanner abierto.
     * @param prompt Mensaje que se muestra antes de leer.
     * @return El decimal introducido, o -1.0 si la entrada no era numerica.
     */
    private static double leerDecimalSeguro(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            double valor = sc.nextDouble();
            sc.nextLine(); // limpiar buffer
            return valor;
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("  [!] Se esperaba un numero decimal.");
            return -1.0;
        }
        // NoSuchElementException NO se captura aqui: se deja subir al llamante
    }

    /**
     * Convierte la opcion numerica del submenu de tipos en el enum {@link TipoProducto}.
     *
     * @param opcion Numero elegido por el usuario (1-4).
     * @return El {@link TipoProducto} correspondiente, o {@code null} si la opcion no es valida.
     */
    private static TipoProducto opcionATipo(int opcion) {
        switch (opcion) {
            case 1: return TipoProducto.RATON;
            case 2: return TipoProducto.TECLADO;
            case 3: return TipoProducto.PANTALLA;
            case 4: return TipoProducto.GRAFICA;
            default: return null;
        }
    }
}
