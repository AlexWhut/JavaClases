package EjemplosExcepciones;

/**
 * EJEMPLO 05 — Crear una excepción propia (checked)
 *
 * Pasos:
 *   1. Crear clase que extiende Exception (checked) o RuntimeException (unchecked).
 *   2. Llamar a super(mensaje) para que getMessage() funcione.
 *   3. Opcionalmente guardar datos extra accesibles con getters.
 *
 * Aquí modelamos una cuenta bancaria con SaldoInsuficienteException.
 */
public class Ejemplo05_ExcepcionPropia {

    // ---- Excepción propia con datos extra ----
    static class SaldoInsuficienteException extends Exception {

        private final double saldoActual;
        private final double montoSolicitado;

        SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
            super(String.format(
                "Saldo insuficiente: tienes %.2f € pero intentas retirar %.2f €",
                saldoActual, montoSolicitado
            ));
            this.saldoActual     = saldoActual;
            this.montoSolicitado = montoSolicitado;
        }

        double getSaldoActual()     { return saldoActual; }
        double getMontoSolicitado() { return montoSolicitado; }
    }

    // ---- Clase que usa la excepción ----
    static class CuentaBancaria {

        private double saldo;

        CuentaBancaria(double saldoInicial) {
            this.saldo = saldoInicial;
        }

        void ingresar(double monto) {
            saldo += monto;
            System.out.printf("  Ingreso de %.2f €. Saldo actual: %.2f €%n", monto, saldo);
        }

        void retirar(double monto) throws SaldoInsuficienteException {
            if (monto > saldo) {
                throw new SaldoInsuficienteException(saldo, monto);
            }
            saldo -= monto;
            System.out.printf("  Retirada de %.2f €. Saldo actual: %.2f €%n", monto, saldo);
        }

        double getSaldo() { return saldo; }
    }

    public static void demoExcepcionPropia() {

        System.out.println("=== EJEMPLO 05: Excepción propia (checked) ===\n");

        CuentaBancaria cuenta = new CuentaBancaria(100.00);

        double[] operaciones = {30.00, 50.00, 80.00}; // la última supera el saldo

        for (double monto : operaciones) {
            try {
                cuenta.retirar(monto);
            } catch (SaldoInsuficienteException e) {
                System.out.println("  ERROR: " + e.getMessage());
                System.out.printf("  Datos extra → saldo: %.2f €, solicitado: %.2f €%n",
                        e.getSaldoActual(), e.getMontoSolicitado());
            }
        }
        System.out.println();
    }
}
