package EscuelaInfantilSinFicheros;

public class ActividadNoExisteException extends Exception {
    public ActividadNoExisteException(String mensaje) {
        super(mensaje);
    }
}
