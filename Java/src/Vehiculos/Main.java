package Vehiculos;

public class Main {
    public static void main(String[] args) {
        Coche coche1 = new Coche("Carro de Carlos", 40, "Toyota");
        Moto moto1 = new Moto("Honda", 200);
        Tren tren1 = new Tren("Renfe", 300);
        Avion avion1 = new Avion("Boeing", 900);
        
        coche1.moverse(); 
        moto1.moverse();       
        tren1.moverse();
        avion1.moverse();
        
        coche1.acelerar(100);
    
        coche1.moverse();   


        System.out.println(coche1);
        System.out.println(moto1);
        System.out.println(tren1);
        System.out.println(avion1);
        
    }
}
