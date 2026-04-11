package Herencia;

public class Usuario {
    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    private String telefono;

    public Usuario(String nombre, String apellido, int edad, String correo, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
