package Videojuego;

// Contrato comun: todo personaje que combate debe atacar y recibir dano.
public interface Atacable {
    void atacar();

    void recibirDanio(int cantidad);
}
