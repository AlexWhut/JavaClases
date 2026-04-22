package GestorDietas;

import java.io.Serializable;

/**
 * Representa el perfil nutricional de un ingrediente o receta.
 * Siempre expresa cantidades por cada 100 gramos de ingrediente.
 *
 * Esta clase es la base del sistema: permite sumar, restar y escalar
 * perfiles, calcular calorías y comparar dos perfiles por similitud.
 *
 * Implementa Serializable para poder guardar/cargar usuarios con sus
 * recetas usando serialización de objetos.
 */
public class PerfilNutricional implements Serializable {

    // Número de versión para la serialización (si cambiamos la clase, subimos este número)
    private static final long serialVersionUID = 1L;

    // Macronutrientes en gramos
    private double proteinas;
    private double carbohidratos;
    private double grasas;

    // ---------------------------------------------------------------
    // CONSTRUCTORES
    // ---------------------------------------------------------------

    /**
     * Constructor vacío: crea un perfil con todos los nutrientes a 0.
     * Útil para empezar a acumular el perfil de una receta.
     */
    public PerfilNutricional() {
        this.proteinas = 0;
        this.carbohidratos = 0;
        this.grasas = 0;
    }

    /**
     * Constructor principal con los tres macronutrientes.
     *
     * @param proteinas      gramos de proteína (≥ 0)
     * @param carbohidratos  gramos de carbohidratos (≥ 0)
     * @param grasas         gramos de grasa (≥ 0)
     * @throws ValorNutricionalException si algún valor es negativo
     */
    public PerfilNutricional(double proteinas, double carbohidratos, double grasas)
            throws ValorNutricionalException {
        if (proteinas < 0 || carbohidratos < 0 || grasas < 0) {
            throw new ValorNutricionalException(
                "Los valores nutricionales no pueden ser negativos.");
        }
        this.proteinas = proteinas;
        this.carbohidratos = carbohidratos;
        this.grasas = grasas;
    }

    // ---------------------------------------------------------------
    // MÉTODOS PRINCIPALES DE OPERACIONES CON PERFILES
    // ---------------------------------------------------------------

    /**
     * Suma este perfil con otro y devuelve un NUEVO perfil resultado.
     * No modifica ninguno de los dos perfiles originales (inmutabilidad).
     *
     * Ejemplo de uso: para calcular el perfil total de una receta,
     * empezamos con un perfil vacío y vamos sumando el de cada ingrediente.
     *
     * @param otro el perfil a sumar
     * @return nuevo PerfilNutricional con la suma
     * @throws ValorNutricionalException si el parámetro es null
     */
    public PerfilNutricional sumar(PerfilNutricional otro) throws ValorNutricionalException {
        if (otro == null) {
            throw new ValorNutricionalException("El perfil a sumar no puede ser null.");
        }
        // Creamos un nuevo perfil con la suma de cada macronutriente
        return new PerfilNutricional(
            this.proteinas + otro.proteinas,
            this.carbohidratos + otro.carbohidratos,
            this.grasas + otro.grasas
        );
    }

    /**
     * Resta otro perfil al actual y devuelve un NUEVO perfil resultado.
     *
     * @param otro el perfil a restar
     * @return nuevo PerfilNutricional con la resta
     * @throws ValorNutricionalException si el resultado fuera negativo o si es null
     */
    public PerfilNutricional restar(PerfilNutricional otro) throws ValorNutricionalException {
        if (otro == null) {
            throw new ValorNutricionalException("El perfil a restar no puede ser null.");
        }
        double p = this.proteinas - otro.proteinas;
        double c = this.carbohidratos - otro.carbohidratos;
        double g = this.grasas - otro.grasas;
        // Comprobamos que la resta no produce negativos
        if (p < 0 || c < 0 || g < 0) {
            throw new ValorNutricionalException(
                "La resta produce valores nutricionales negativos.");
        }
        return new PerfilNutricional(p, c, g);
    }

    /**
     * Escala el perfil según un factor (cantidad en gramos / 100).
     * Así se adapta el perfil "por 100g" a la cantidad real usada.
     *
     * Ejemplo: si el ingrediente tiene 20g de proteína por 100g,
     * y usamos 150g, entonces factor = 1.5, resultado = 30g.
     *
     * @param factor multiplicador (debe ser > 0)
     * @return nuevo PerfilNutricional escalado
     * @throws ValorNutricionalException si el factor no es positivo
     */
    public PerfilNutricional escalar(double factor) throws ValorNutricionalException {
        if (factor <= 0) {
            throw new ValorNutricionalException(
                "El factor de escala debe ser positivo. Factor recibido: " + factor);
        }
        return new PerfilNutricional(
            this.proteinas * factor,
            this.carbohidratos * factor,
            this.grasas * factor
        );
    }

    /**
     * Calcula las calorías totales usando la fórmula estándar:
     *   Proteínas: 4 kcal/g
     *   Carbohidratos: 4 kcal/g
     *   Grasas: 9 kcal/g
     *
     * @return calorías totales como entero (redondeado)
     */
    public int kcalTotales() {
        double kcal = (proteinas * 4) + (carbohidratos * 4) + (grasas * 9);
        return (int) Math.round(kcal);
    }

    /**
     * Comprueba si este perfil es "keto".
     * Una dieta keto es muy baja en carbohidratos y alta en grasas.
     * Condición: carbohidratos < 10g Y grasas > proteínas.
     *
     * @return true si cumple criterio keto
     */
    public boolean esKeto() {
        return carbohidratos < 10 && grasas > proteinas;
    }

    /**
     * Calcula la similitud entre este perfil y otro usando distancia euclídea.
     * Cuanto más cercano a 0, más similares son los perfiles.
     *
     * Fórmula: sqrt((p1-p2)² + (c1-c2)² + (g1-g2)²)
     *
     * @param otro el perfil con el que comparar
     * @return distancia euclídea (0 = idénticos)
     * @throws ValorNutricionalException si el parámetro es null
     */
    public double similitudNutrientes(PerfilNutricional otro) throws ValorNutricionalException {
        if (otro == null) {
            throw new ValorNutricionalException("El perfil a comparar no puede ser null.");
        }
        double dp = this.proteinas - otro.proteinas;
        double dc = this.carbohidratos - otro.carbohidratos;
        double dg = this.grasas - otro.grasas;
        return Math.sqrt(dp * dp + dc * dc + dg * dg);
    }

    // ---------------------------------------------------------------
    // GETTERS Y SETTERS
    // ---------------------------------------------------------------

    /** @return gramos de proteína */
    public double getProteinas() { return proteinas; }

    /** @return gramos de carbohidratos */
    public double getCarbohidratos() { return carbohidratos; }

    /** @return gramos de grasa */
    public double getGrasas() { return grasas; }

    /**
     * @param proteinas gramos de proteína (≥ 0)
     * @throws ValorNutricionalException si el valor es negativo
     */
    public void setProteinas(double proteinas) throws ValorNutricionalException {
        if (proteinas < 0) throw new ValorNutricionalException("Las proteínas no pueden ser negativas.");
        this.proteinas = proteinas;
    }

    /**
     * @param carbohidratos gramos de carbohidratos (≥ 0)
     * @throws ValorNutricionalException si el valor es negativo
     */
    public void setCarbohidratos(double carbohidratos) throws ValorNutricionalException {
        if (carbohidratos < 0) throw new ValorNutricionalException("Los carbohidratos no pueden ser negativos.");
        this.carbohidratos = carbohidratos;
    }

    /**
     * @param grasas gramos de grasa (≥ 0)
     * @throws ValorNutricionalException si el valor es negativo
     */
    public void setGrasas(double grasas) throws ValorNutricionalException {
        if (grasas < 0) throw new ValorNutricionalException("Las grasas no pueden ser negativas.");
        this.grasas = grasas;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Representación legible del perfil nutricional.
     * Muestra cada macronutriente con 2 decimales y las kcal totales.
     */
    @Override
    public String toString() {
        return String.format("Proteínas: %.2fg | Carbohidratos: %.2fg | Grasas: %.2fg | Kcal: %d",
            proteinas, carbohidratos, grasas, kcalTotales());
    }
}
