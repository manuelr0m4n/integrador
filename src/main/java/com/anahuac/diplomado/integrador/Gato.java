package com.anahuac.diplomado.integrador;

import java.util.List;

/**
 * ============================================================================
 * CONCEPTO POO: HERENCIA Y POLIMORFISMO (CLASE HIJA)
 * ============================================================================
 * Gato es una subclase concreta que HEREDA de Mascota.
 * Al igual que Perro:
 * 1. Invoca al constructor de Mascota usando 'super(...)'.
 * 2. Sobrescribe los métodos abstractos con la lógica específica de un gato.
 */
// TODO 16: Indicar que Gato hereda de Mascota usando la palabra clave adecuada ('extends').
public class Gato extends Mascota {

    // TODO 17: Declarar el atributo privado específico de Gato: 'interior' (boolean).
    // Indica si el gato vive exclusivamente dentro de casa (true) o también sale (false).
    private boolean interior;

    /**
     * TODO 18: Implementar el constructor de Gato.
     * Parámetros: nombre (String), edad (int), dueno (String), interior (boolean).
     * 
     * 💡 Pista:
     * - Llamar a super(nombre, edad, dueno) en la primera línea.
     * - Inicializar this.interior = interior;
     */
    public Gato(String nombre, int edad, String dueno, boolean interior) {
        super(nombre, edad, dueno);
        this.interior = interior;
    }

    /**
     * TODO 19: Implementar hacerSonido().
     * Debe retornar el maullido: "¡Miau!"
     */
    @Override
    public String hacerSonido() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return "MIAU";
    }

    /**
     * TODO 20: Implementar getTipo().
     * Debe retornar: "GATO"
     */
    @Override
    public String getTipo() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return "GATO";
    }

    /**
     * TODO 21: Implementar getDetalle().
     * Si el gato es de interior (interior == true), debe retornar el texto "interior".
     * Si no, debe retornar "exterior".
     * 
     * 💡 Pista: Puedes usar un if-else o el operador ternario: interior ? "interior" : "exterior"
     */
    @Override
    public String getDetalle() {
        String detalle = "";
        if (interior) {
            detalle = "interior";
        } else {
            detalle = "exterior";
        }
        return detalle;
    }

    /**
     * TODO 22: Implementar vacunasRecomendadas().
     * Las vacunas recomendadas para los gatos son: "Rabia", "Triple felina" y "Leucemia felina".
     * 💡 Pista: Retornar una lista usando List.of("Rabia", "Triple felina", "Leucemia felina");
     */
    @Override
    public List<String> vacunasRecomendadas() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return List.of("Rabia", "Triple felina", "Leucemia felina");
    }
}
