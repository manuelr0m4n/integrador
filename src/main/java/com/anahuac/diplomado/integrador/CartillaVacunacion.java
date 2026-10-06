package com.anahuac.diplomado.integrador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * CONCEPTO POO: COLECCIONES (Map / HashMap) Y MANEJO DE ARCHIVOS (I/O)
 * ============================================================================
 * La cartilla de vacunación almacena pares clave-valor:
 *   Clave (Key):   Nombre de la vacuna (String), ej: "Rabia"
 *   Valor (Value): Fecha en que se aplicó (LocalDate), ej: 2026-10-02
 * 
 * Un Map es ideal aquí porque buscar si una vacuna ya fue aplicada (containsKey)
 * es una operación directa e instantánea O(1).
 */

public class CartillaVacunacion {

    // TODO 23: Declarar e inicializar el mapa de vacunas.
    // Tipo: Map<String, LocalDate>
    // Inicialización: new HashMap<>()
    private Map<String, LocalDate> vacunas = new HashMap<>();

    /**
     * TODO 24: Implementar registrar().
     * Agrega o actualiza una entrada en el mapa 'vacunas'.
     * 
     * @param vacuna Nombre de la vacuna (clave).
     * @param fecha  Fecha de aplicación (valor).
     * Pista: Usa el método .put(clave, valor) del mapa.
     */
    public void registrar(String vacuna, LocalDate fecha) {
        vacunas.put(vacuna, fecha);
    }

    /**
     * TODO 25: Implementar tieneVacuna().
     * Verifica si una vacuna ya se encuentra registrada en el mapa.
     * 
     * @param vacuna Nombre de la vacuna a buscar.
     * @return true si la vacuna ya fue aplicada, false si no.
     * Pista: Usa el método .containsKey(...) del mapa.
     */
    public boolean tieneVacuna(String vacuna) {
       if (vacunas.containsKey(vacuna)) {
            return true;
        } 
        else {
            return false;
        }
    }

    /**
     * TODO 26: Implementar mostrar().
     * Imprime por consola el contenido de la cartilla.
     * 
     * Requisitos:
     * 1. Si el mapa está vacío (.isEmpty()):
     *    Imprimir exactamente: "    (cartilla vacía)"
     * 2. Si tiene vacunas:
     *    Recorrer las entradas del mapa (.entrySet()) e imprimir cada una con el formato:
     *    "    💉 " + clave + " -> " + valor
     *    Ejemplo: "    💉 Rabia -> 2026-10-02"
     */
    public void mostrar() {
        if (vacunas.isEmpty()) {
            System.out.println("    (cartilla vacía)");
        } 
        else {
            for (Map.Entry<String, LocalDate> entry : vacunas.entrySet()) {
                String vacuna = entry.getKey();
                LocalDate fecha = entry.getValue();
                System.out.println(" " + vacuna + " -> " + fecha);
            }
        }

    }

    // ========================================================================
    // ENTRADA Y SALIDA (I/O) - PERSISTENCIA DE ARCHIVOS
    // ========================================================================

    /**
     * TODO 27: Implementar guardar().
     * Guarda el contenido de la cartilla en un archivo de texto plano.
     * 
     * Requisitos:
     * 1. Crear una lista de cadenas: List<String> lineas = new ArrayList<>();
     * 2. Recorrer el mapa (vacunas.entrySet()) y agregar a la lista una línea por cada vacuna
     *    con el formato: "vacuna,fecha" (ejemplo: "Rabia,2026-10-02").
     * 3. Escribir las líneas en el archivo recibido usando:
     *    Files.write(archivo, lineas);
     * 
     * @param archivo Ruta (Path) del archivo donde se guardará la cartilla.
     * @throws IOException Si ocurre un error de escritura en disco.
     */
    public void guardar(Path archivo) throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Map.Entry<String, LocalDate> entry : vacunas.entrySet()) {
            String vacuna = entry.getKey();
            LocalDate fecha = entry.getValue();
            lineas.add(vacuna + "," + fecha);
        }
        Files.write(archivo, lineas);
    }

    /**
     * TODO 28: Implementar cargar().
     * Reconstruye el mapa de vacunas leyendo un archivo guardado previamente.
     * 
     * Requisitos:
     * 1. Verificar si el archivo existe con Files.exists(archivo). Si NO existe, retornar sin hacer nada.
     * 2. Leer todas las líneas del archivo con: Files.readAllLines(archivo).
     * 3. Por cada línea:
     *    a. Separar la vacuna y la fecha usando split(",").
     *    b. El primer elemento partes[0] es el nombre de la vacuna.
     *    c. El segundo elemento partes[1] debe parsearse a LocalDate usando LocalDate.parse(partes[1]).
     *    d. Guardar en el mapa: vacunas.put(vacuna, fecha).
     * 
     * @param archivo Ruta (Path) del archivo a leer.
     * @throws IOException Si ocurre un error de lectura.
     */
    public void cargar(Path archivo) throws IOException {
        if (!Files.exists(archivo)) {
            return;
        }
        List<String> lineas = Files.readAllLines(archivo);
        for (String linea : lineas) {
            if (linea == null || linea.trim().isEmpty() || !linea.contains(",")) {
                continue;
            }
            
            String[] partes = linea.split(",");
            
            if (partes.length >= 2) {
                String vacuna = partes[0];
                LocalDate fecha = LocalDate.parse(partes[1]);
                vacunas.put(vacuna, fecha);
            }
        }
    }

    public void vaciar() {
    vacunas.clear();
    }
}
