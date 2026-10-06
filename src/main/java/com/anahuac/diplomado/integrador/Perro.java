package com.anahuac.diplomado.integrador;

import java.util.List;

/**
 * ============================================================================
 * CONCEPTO POO: HERENCIA Y POLIMORFISMO (CLASE HIJA)
 * ============================================================================
 * Perro es una subclase concreta que HEREDA de Mascota.
 * Al heredar:
 * 1. Reutiliza los atributos y métodos de Mascota (nombre, edad, dueno, cartilla).
 * 2. Debe invocar al constructor padre mediante 'super(...)'.
 * 3. Debe SOBREESCRIBIR (@Override) todos los métodos abstractos de Mascota.
 */
// TODO 9: Indicar que Perro hereda de Mascota usando la palabra clave adecuada ('extends').
public class Perro extends Mascota {

    // TODO 10: Declarar el atributo privado específico de Perro: 'raza' (String).
    private String raza;

    /**
     * TODO 11: Implementar el constructor de Perro.
     * Parámetros: nombre (String), edad (int), dueno (String), raza (String).
     * 
     * 💡 Pista:
     * - La primera línea del constructor debe llamar al constructor de la clase padre
     *   usando super(nombre, edad, dueno).
     * - Luego, inicializar el atributo propio 'raza' con this.raza = raza;
     */
    public Perro(String nombre, int edad, String dueno, String raza) {
        super(nombre, edad, dueno);
        this.raza = raza;

    }

    /**
     * TODO 12: Implementar hacerSonido().
     * Debe retornar el ladrido: "¡Guau!"
     */
    @Override
    public String hacerSonido() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return "GUAU";
    }

    /**
     * TODO 13: Implementar getTipo().
     * Debe retornar: "PERRO"
     */
    @Override
    public String getTipo() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return "PERRO";
    }

    /**
     * TODO 14: Implementar getDetalle().
     * Para el perro, su detalle distintivo es su raza.
     */
    @Override
    public String getDetalle() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        return raza;
    }

    /**
     * TODO 15: Implementar vacunasRecomendadas().
     * Las vacunas recomendadas para los perros son: "Rabia", "Parvovirus" y "Moquillo".
     * 💡 Pista: Retornar una lista usando List.of("Rabia", "Parvovirus", "Moquillo");
     */
    @Override
    public List<String> vacunasRecomendadas() {
        return List.of("Rabia", "Parvovirus", "Moquillo");
    }
}
