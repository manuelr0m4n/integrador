package com.anahuac.diplomado.integrador;

import java.time.LocalDate;

/**
 * ============================================================================
 * CONCEPTO POO: COLABORACIÓN ENTRE OBJETOS Y REGLAS DE NEGOCIO
 * ============================================================================
 * En la POO los objetos no viven aislados; colaboran entre sí enviándose mensajes
 * (invocando métodos de otros objetos).
 * 
 * En este caso:
 * El Veterinario recibe una Mascota y una vacuna.
 * 1. Valida si la vacuna es adecuada para la especie de la mascota.
 * 2. Si es adecuada, le pide a la mascota que se vacune (Veterinario -> Mascota -> Cartilla).
 */
public class Veterinario {

    // TODO 29: Atributo privado 'nombre' (String)
    private String nombre;

    /**
     * TODO 30: Constructor de Veterinario
     * Recibe el nombre del veterinario y lo asigna al atributo.
     */
    public Veterinario(String nombre) {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        this.nombre = nombre;
    }

    /**
     * TODO 31: Implementar aplicarVacuna().
     * 
     * 💡 Pistas para la implementación:
     * 0. Si mascota == null, imprimir advertencia y retornar.
     * 1. Consultar si la vacuna está dentro de las recomendadas para esta mascota:
     *    mascota.vacunasRecomendadas().contains(vacuna)
     *    
     *    Si NO está recomendada:
     *    - Imprimir: "⚠️  " + vacuna + " no es una vacuna recomendada para " + mascota.getNombre()
     *    - Terminar la ejecución del método (return;).
     * 
     * 2. Si la vacuna SÍ es recomendada:
     *    - Invocar el método vacunar de la mascota pasando la vacuna y la fecha actual:
     *      mascota.vacunar(vacuna, LocalDate.now());
     *    - Imprimir: "🩺 Dr(a). " + nombre + " aplicó " + vacuna + " a " + mascota.getNombre()
     */
    public boolean aplicarVacuna(Mascota mascota, String vacunaIngresada) {
        if (mascota == null) {
            System.out.println("La mascota no existe.");
            return false;
        }

        String vacunaCorrecta = null;
        for (String recomendada : mascota.vacunasRecomendadas()) {
            if (recomendada.equalsIgnoreCase(vacunaIngresada.trim())) {
                vacunaCorrecta = recomendada;
                break;
            }
        }

        if (vacunaCorrecta == null) {
            System.out.println(" '" + vacunaIngresada.trim() + "' no es una vacuna recomendada para " + mascota.getNombre());
            return false;
        }

        mascota.vacunar(vacunaCorrecta, LocalDate.now());
        System.out.println(" Dr(a). " + nombre + " aplicó " + vacunaCorrecta + " a " + mascota.getNombre());
        return true;
    }
}
