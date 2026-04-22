package GestorDietas;

import java.io.*;
import java.util.ArrayList;

/**
 * Gestiona la lista global de usuarios del sistema y su persistencia.
 *
 * Responsabilidades:
 *  - Registrar nuevos usuarios
 *  - Buscar usuarios por ID
 *  - Listar todos los usuarios
 *  - Guardar la lista de usuarios en disco (serialización)
 *  - Cargar la lista de usuarios desde disco (deserialización)
 *
 * La serialización de objetos Java guarda el estado completo de los objetos
 * (incluyendo sus colecciones y objetos anidados) en un archivo binario.
 * Para que funcione, todas las clases deben implementar Serializable.
 */
public class GestorUsuarios {

    /** Lista de todos los usuarios registrados en el sistema */
    private ArrayList<Usuario> usuarios;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Crea un gestor vacío sin usuarios.
     */
    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    // ---------------------------------------------------------------
    // GESTIÓN DE USUARIOS
    // ---------------------------------------------------------------

    /**
     * Registra un nuevo usuario en el sistema.
     * Comprueba que no exista ya un usuario con el mismo ID.
     *
     * @param usuario usuario a registrar (no null)
     * @throws UsuarioException si el usuario es null o ya existe el ID
     */
    public void registrarUsuario(Usuario usuario) throws UsuarioException {
        if (usuario == null) {
            throw new UsuarioException("No se puede registrar un usuario null.");
        }
        // Comprobamos que el ID no esté ya en uso
        if (buscarPorId(usuario.getId()) != null) {
            throw new UsuarioException(
                "Ya existe un usuario con el ID: " + usuario.getId());
        }
        usuarios.add(usuario);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id identificador a buscar (no vacío)
     * @return el usuario encontrado, o null si no existe
     * @throws UsuarioException si el ID es vacío o null
     */
    public Usuario buscarPorId(String id) throws UsuarioException {
        if (id == null || id.isBlank()) {
            throw new UsuarioException("El ID de búsqueda no puede estar vacío.");
        }
        for (Usuario u : usuarios) {
            if (u.getId().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null; // No encontrado
    }

    /**
     * Devuelve la lista completa de usuarios registrados.
     *
     * @return lista de usuarios
     */
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Devuelve cuántos usuarios hay registrados.
     *
     * @return número de usuarios
     */
    public int getNumeroUsuarios() {
        return usuarios.size();
    }

    // ---------------------------------------------------------------
    // SERIALIZACIÓN: GUARDAR Y CARGAR
    // ---------------------------------------------------------------

    /**
     * Guarda la lista completa de usuarios en un archivo binario.
     *
     * Cómo funciona la serialización:
     *  1. ObjectOutputStream envuelve un FileOutputStream (escritura a fichero)
     *  2. writeObject() serializa el ArrayList completo, incluyendo todos los
     *     usuarios, sus historiales, recetas e ingredientes
     *  3. El archivo resultante es binario (no legible con un editor de texto)
     *
     * @param rutaArchivo ruta del archivo donde guardar (ej: "usuarios.dat")
     * @throws UsuarioException si hay error al escribir el archivo
     */
    public void guardar(String rutaArchivo) throws UsuarioException {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new UsuarioException("La ruta del archivo no puede estar vacía.");
        }
        // try-with-resources: cierra automáticamente los streams al terminar
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            throw new UsuarioException("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de usuarios desde un archivo binario previamente guardado.
     *
     * Cómo funciona la deserialización:
     *  1. ObjectInputStream envuelve un FileInputStream (lectura desde fichero)
     *  2. readObject() reconstruye el ArrayList con todos los objetos
     *  3. El cast a ArrayList<Usuario> es necesario porque readObject devuelve Object
     *
     * @param rutaArchivo ruta del archivo a leer
     * @throws UsuarioException si el archivo no existe o hay error al leerlo
     */
    @SuppressWarnings("unchecked") // El cast es seguro porque sabemos lo que guardamos
    public void cargar(String rutaArchivo) throws UsuarioException {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new UsuarioException("La ruta del archivo no puede estar vacía.");
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(rutaArchivo))) {
            usuarios = (ArrayList<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new UsuarioException("Archivo no encontrado: " + rutaArchivo);
        } catch (IOException | ClassNotFoundException e) {
            throw new UsuarioException("Error al cargar usuarios: " + e.getMessage());
        }
    }
}
