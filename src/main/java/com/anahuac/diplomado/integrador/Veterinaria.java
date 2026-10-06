package com.anahuac.diplomado.integrador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * CONCEPTO POO: AGREGACIÓN, COLECCIONES (List) Y PERSISTENCIA (I/O)
 * ============================================================================
 * La Veterinaria gestiona el registro de pacientes en una lista (List<Mascota>).
 * Permite buscar mascotas, listar pacientes y persistir todo el estado en disco
 * (formato CSV para mascotas y archivos TXT individuales para cada cartilla).
 */
public class Veterinaria {

    // TODO 32: Atributos privados
    // - nombre (String)
    // - pacientes (List<Mascota>): inicializado como un new ArrayList<>()
    private String nombre;
    private List<Mascota> pacientes = new ArrayList<>();

    public Veterinaria(String nombre) {
        this.nombre = nombre;
    }

    /**
     * TODO 33: Implementar registrarPaciente().
     * Agrega la mascota recibida a la lista de 'pacientes'.
     * 💡 Pista: Usa el método .add(m) de la lista.
     */
    public void registrarPaciente(Mascota m) {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        pacientes.add(m);
    }

    /**
     * TODO 34: Implementar buscar().
     * Busca en la lista 'pacientes' una mascota cuyo nombre coincida con 'nombreMascota',
     * sin distinguir entre mayúsculas y minúsculas.
     * 
     * @param nombreMascota Nombre de la mascota a buscar.
     * @return La instancia de Mascota si se encuentra, o null si no existe.
     * 💡 Pista: Recorre la lista con un for-each y usa m.getNombre().equalsIgnoreCase(nombreMascota)
     */
    public Mascota buscar(String nombreMascota) {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        for (Mascota m : pacientes) {
            if (m.getNombre().equalsIgnoreCase(nombreMascota)) {
                return m;
            }
        }
        return null;
    }

    public List<Mascota> getPacientes() {
        return pacientes;
    }

    /**
     * TODO 35: Implementar mostrarPacientes().
     * Imprime en consola la lista de pacientes registrados.
     * 
     * Requisitos:
     * 1. Imprimir la cabecera: "📋 Pacientes de " + nombre + ":"
     * 2. Recorrer la lista de pacientes e imprimir cada uno con el formato: "  • " + m
     *    (Observa cómo Java llamará automáticamente al método toString() de cada Mascota).
     */
    public void mostrarPacientes() {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        System.out.println("  Pacientes de " + nombre + ":");
        for (Mascota m : pacientes) {
            System.out.println("  • " + m);
        }
    }

    // ========================================================================
    // ENTRADA Y SALIDA (I/O) - PERSISTENCIA GLOBAL
    // ========================================================================

    /**
     * TODO 36: Implementar guardarTodo().
     * Guarda la información completa de la veterinaria en la carpeta especificada:
     * 1. Un archivo 'pacientes.csv' con todos los animales registrados.
     * 2. Un archivo 'cartilla_<nombre>.txt' para cada mascota con su historial de vacunas.
     * 
     * Requisitos:
     * a. Asegurar que la carpeta exista: Files.createDirectories(carpeta);
     * b. Crear una lista de String para las líneas del CSV: List<String> lineas = new ArrayList<>();
     * c. Por cada mascota 'm' en 'pacientes':
     *    - Construir una línea con formato:
     *      m.getTipo() + ";" + m.getNombre() + ";" + m.getEdad() + ";" + m.getDueno() + ";" + m.getDetalle()
     *    - Agregar la línea a la lista 'lineas'.
     *    - Pedirle a la cartilla de la mascota que se guarde en:
     *      carpeta.resolve("cartilla_" + m.getNombre() + ".txt")
     * d. Escribir todas las líneas en el archivo 'pacientes.csv':
     *    Files.write(carpeta.resolve("pacientes.csv"), lineas);
     * 
     * @param carpeta Carpeta destino (Path).
     * @throws IOException Si ocurre un error al escribir los archivos.
     */
    public void guardarTodo(Path carpeta) throws IOException {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        Files.createDirectories(carpeta);
        List<String> lineas = new ArrayList<>();
        
        for (Mascota m : pacientes) {
            lineas.add(m.getTipo() + ";" + m.getNombre() + ";" + m.getEdad() + ";" + m.getDueno() + ";" + m.getDetalle());
            if (m.getCartilla() != null) {
                m.getCartilla().guardar(carpeta.resolve("cartilla_" + m.getNombre() + ".txt"));
            }
        }
        
        Files.write(carpeta.resolve("pacientes.csv"), lineas);
    }

    /**
     * TODO 37: Implementar cargarTodo().
     * Reconstruye todos los pacientes y sus cartillas leyendo los archivos desde la carpeta indicada.
     * 
     * Requisitos:
     * a. Obtener la ruta del archivo CSV: Path archivo = carpeta.resolve("pacientes.csv");
     * b. Si el archivo NO existe (!Files.exists(archivo)), terminar el método con return.
     * c. Leer todas las líneas de 'pacientes.csv' con Files.readAllLines(archivo).
     * d. Para cada línea del CSV:
     *    - Separar por punto y coma: String[] p = linea.split(";");
     *      Donde:
     *        p[0] = tipo ("PERRO" o "GATO")
     *        p[1] = nombre
     *        p[2] = edad (String -> convertir a int con Integer.parseInt)
     *        p[3] = dueño
     *        p[4] = detalle (raza en Perro, o "interior"/"exterior" en Gato)
     *    - Instanciar la subclase adecuada:
     *        Si p[0].equals("PERRO"):
     *            m = new Perro(p[1], edad, p[3], p[4]);
     *        Si no:
     *            m = new Gato(p[1], edad, p[3], p[4].equals("interior"));
     *    - Cargar la cartilla de la mascota desde:
     *        carpeta.resolve("cartilla_" + p[1] + ".txt")
     *    - Agregar la mascota reconstruida a la lista: pacientes.add(m);
     * 
     * @param carpeta Carpeta donde se encuentran los archivos (Path).
     * @throws IOException Si ocurre un error de lectura.
     */
    public void cargarTodo(Path carpeta) throws IOException {
        // --- ESCRIBE TU CÓDIGO AQUÍ ---
        Path archivo = carpeta.resolve("pacientes.csv");
        if (!Files.exists(archivo)) {
            return;
        }
        
        List<String> lineas = Files.readAllLines(archivo);
        for (String linea : lineas) {
            String[] p = linea.split(";");
            Mascota m;
            int edad = Integer.parseInt(p[2]);
            
            if (p[0].equals("PERRO")) {
                m = new Perro(p[1], edad, p[3], p[4]);
            } else {
                m = new Gato(p[1], edad, p[3], p[4].equals("interior"));
            }
            
            if (m.getCartilla() != null) {
                m.getCartilla().cargar(carpeta.resolve("cartilla_" + p[1] + ".txt"));
            }
            
            pacientes.add(m);
        }
    }
}
