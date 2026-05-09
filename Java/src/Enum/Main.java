package Enum;

public class Main {

    public static void main(String[] args) {
    Dia hoy = Dia.MARTES;
    String descripcion = "";

    switch (hoy) {
        case LUNES:
            descripcion = "Primer día de la semana laboral.";
        case MARTES:
            descripcion = "Segundo día de la semana laboral.";
        case MIERCOLES:
            descripcion = "Tercer día de la semana laboral.";
            break;
        case JUEVES:
            System.out.println("Hoy es jueves.");
            break;
        case VIERNES:
            System.out.println("Hoy es viernes.");
            break;
        case SABADO:
            System.out.println("Hoy es sábado.");
            break;
        case DOMINGO:
            System.out.println("Hoy es domingo.");
            break;
    }
    
    System.out.println("Hoy es " + hoy + ". Descripción: " + descripcion);

}
}
