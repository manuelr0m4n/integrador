package com.anahuac.diplomado.integrador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * CONCEPTO POO: CLASE ABSTRACTA, ENCAPSULAMIENTO Y COMPOSICIÓN
 * ============================================================================
 * Mascota es una clase abstracta porque representa el concepto general de mascota.
 * En la vida real no existe "una mascota genérica", sino instancias específicas
 * (un Perro, un Gato, etc.).
 * 
 * Además:
 * 1. Implementa el contrato 'Vacunable'.
 * 2. Tiene una relación de COMPOSICIÓN con 'CartillaVacunacion' (la mascota TIENE una cartilla).
 */
public abstract class Mascota implements Vacunable {

    // ========================================================================
    // ATRIBUTOS PRIVADOS (ENCAPSULAMIENTO)
    // ========================================================================
    // TODO 3: Declarar los siguientes atributos con visibilidad privada (private):
    // - nombre (String): nombre de la mascota
    // - edad (int): edad en años
    // - dueno (String): nombre del dueño
    // - cartilla (CartillaVacunacion): inicializada con una nueva instancia 'new CartillaVacunacion()'
    private String nombre;
    private int edad;
    private String dueno;
    private CartillaVacunacion cartilla = new CartillaVacunacion();

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    /**
     * TODO 4: Implementar el constructor de Mascota.
     * Debe recibir nombre, edad y dueno, y asignarlos a las variables de instancia correspondientes.
     * 💡 Pista: Usa la palabra reservada 'this' para distinguir el parámetro del atributo.
     */
    public Mascota(String nombre, int edad, String dueno) {
        this.nombre = nombre;
        this.edad = edad;
        this.dueno = dueno;

    }

    // ========================================================================
    // MÉTODOS ABSTRACTOS (POLIMORFISMO Y ABSTRACCIÓN)
    // ========================================================================
    // Cada subclase (Perro, Gato) está OBLIGADA a definir estos métodos a su manera.
    
    /** Retorna la onomatopeya del sonido característico del animal (ej: "¡Guau!", "¡Miau!"). */
    public abstract String hacerSonido();

    /** Retorna el tipo de mascota en mayúsculas (ej: "PERRO", "GATO"). */
    public abstract String getTipo();

    /** Retorna el detalle distintivo (para Perro: su raza; para Gato: si es "interior" o "exterior"). */
    public abstract String getDetalle();

    /** Retorna la lista de vacunas recomendadas para este tipo de mascota. */
    public abstract List<String> vacunasRecomendadas();

    // ========================================================================
    // IMPLEMENTACIÓN DEL CONTRATO 'Vacunable'
    // ========================================================================

    /**
     * TODO 5: Implementar el método 'vacunar'.
     * La mascota no gestiona la fecha directamente, sino que DELEGA esta tarea a su cartilla.
     * 💡 Pista: Llama al método registrar de la cartilla: cartilla.registrar(vacuna, fecha);
     */
    @Override
    public void vacunar(String vacuna, LocalDate fecha) {
        cartilla.registrar(vacuna, fecha);

    }

    /**
     * TODO 6: Implementar el método 'vacunasPendientes'.
     * Retorna una lista con las vacunas recomendadas que la mascota AÚN NO tiene aplicadas.
     * 
     * 💡 Pistas para la lógica:
     * 1. Crear una nueva lista: List<String> pendientes = new ArrayList<>();
     * 2. Recorrer con un for-each las vacunas recomendadas devueltas por vacunasRecomendadas().
     * 3. Para cada vacuna recomendada, verificar si la cartilla NO la tiene (!cartilla.tieneVacuna(vacuna)).
     * 4. Si no la tiene, agregarla a la lista de pendientes.
     * 5. Retornar la lista 'pendientes'.
     */
    @Override
    public List<String> vacunasPendientes() {
        List<String> pendientes = new ArrayList<>();
        List<String> recomendadas = vacunasRecomendadas();
        for (String vacuna : recomendadas) {
            if (!cartilla.tieneVacuna(vacuna)) {
                pendientes.add(vacuna);
            }
        }
        return pendientes;
    }

    // ========================================================================
    // MÉTODOS DE ACCESO (GETTERS)
    // ========================================================================
    // TODO 7: Completar los métodos getter para dar acceso de solo lectura a los atributos privados.
    public String getNombre() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return nombre;
    }

    public int getEdad() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return edad;
    }

    public String getDueno() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return dueno;
    }

    public CartillaVacunacion getCartilla() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return cartilla;
    }

    // ========================================================================
    // SOBRESCRITURA (OVERRIDE) DE toString()
    // ========================================================================
    /**
     * TODO 8: Implementar toString() para representar a la mascota como texto.
     * Formato requerido:
     *   "TIPO Nombre (Edad años, dueño: Dueño, Detalle)"
     * 
     * Ejemplo para Perro:
     *   "PERRO Firulais (3 años, dueño: Ana, Labrador)"
     * Ejemplo para Gato:
     *   "GATO Michi (2 años, dueño: Luis, interior)"
     * 
     * 💡 Pistas:
     * - Usa getTipo() para obtener el tipo en mayúsculas de forma polimórfica.
     * - Usa getDetalle() para obtener la raza o interior/exterior de forma polimórfica.
     */
    @Override
    public String toString() {
        return getTipo() + " " + nombre + " (" + edad + " años, dueño: " + dueno + ", " + getDetalle() + ")";
    }
}
