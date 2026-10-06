package com.anahuac.diplomado.integrador;

import java.io.IOException;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.nio.file.Files;
import java.time.LocalDate;

public class MenuVeterinaria {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Veterinaria clinica = new Veterinaria("Patitas Felices");
        Veterinario doctor = new Veterinario("Roberto Carlos");

        // Selecciono la carpeta de datos de la Veterinaria
        Path carpeta = Path.of("datos_veterinaria");

        // Cargar datos existentes al iniciar el programa
        try {
            clinica.cargarTodo(carpeta);
            System.out.println("Datos cargados correctamente desde la carpeta: " + carpeta);
        } catch (IOException e) {
            System.out.println("No se encontraron datos previos o hubo un error al leerlos.");
            System.out.println("Se iniciará en blanco la base de datos.");
        }

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- VETERINARIA 'PATITAS FELICES' ---");
            System.out.println("1. Agregar una nueva mascota (Perro o Gato)");
            System.out.println("2. Ver todas las mascotas");
            System.out.println("3. Eliminar una mascota desde el ID");
            System.out.println("4. Ver cartilla de una mascota");
            System.out.println("5. Eliminar cartilla de una mascota");
            System.out.println("6. Actualizar cartilla - agregar vacuna");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");

            int opcion = 0;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpieza de búfer
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, ingresa un número válido del menú.");
                scanner.nextLine(); // Limpiar el búfer para evitar bucles infinitos
                continue;
            }

            switch (opcion) {
                case 1:
                    try {
                        System.out.println("\n¿Qué tipo de mascota deseas agregar?");
                        System.out.println("1. Perro");
                        System.out.println("2. Gato");
                        System.out.print("Elige una opción: ");
                        
                        int tipoMascota = scanner.nextInt();
                        scanner.nextLine();

                        if (tipoMascota != 1 && tipoMascota != 2) {
                            System.out.println("Opción no válida.");
                            System.out.println("Cancelando registro.");
                            break;
                        }

                        String nombre = leerTextoNoVacio(scanner, "Ingresar nombre de la mascota: ");
                        
                        System.out.print("Ingresar los años de la mascota: ");
                        int edad = scanner.nextInt();
                        scanner.nextLine();
                        
                        String dueno = leerTextoNoVacio(scanner, "Ingresar nombre del dueño: ");

                        if (tipoMascota == 1) {
                            String raza = leerTextoNoVacio(scanner, "Ingresar raza del perro: ");
                            clinica.registrarPaciente(new Perro(nombre, edad, dueno, raza));
                            System.out.println("¡Perro registrado exitosamente!");
                        } else {
                            System.out.print("¿Es un gato exclusivo de interior? (true/false): ");
                            boolean interior = scanner.nextBoolean();
                            scanner.nextLine();
                            clinica.registrarPaciente(new Gato(nombre, edad, dueno, interior));
                            System.out.println("¡Gato registrado exitosamente!");
                        }

                        clinica.guardarTodo(carpeta);
                        System.out.println("Archivos de datos actualizados.");

                    } catch (InputMismatchException e) {
                        System.out.println("Error de formato: Debes ingresar un número válido.");
                        System.out.println("Cancelando registro.");
                        scanner.nextLine(); 
                    } catch (IOException e) {
                        System.out.println("Error al guardar los archivos en la base de datos: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- LISTA DE MASCOTAS ---");
                    if (clinica.getPacientes().isEmpty()) {
                        System.out.println("No hay mascotas registradas.");
                    } else {
                        for (Mascota m : clinica.getPacientes()) {
                            System.out.println("Nombre: " + m.getNombre() + " | Tipo: " + m.getTipo());
                            System.out.println("Dueño: " + m.getDueno() + " | Edad: " + m.getEdad() + " años");
                            System.out.println("Detalle: " + m.getDetalle());
                            System.out.println("-------------------------");
                            System.out.println("");
                        }
                    }
                    break;

                case 3:
                    if (clinica.getPacientes().isEmpty()) {
                        System.out.println("\nNo hay mascotas registradas para eliminar.");
                        break;
                    }

                    System.out.println("\n--- LISTA ACTUAL DE MASCOTAS ---");
                    for (int i = 0; i < clinica.getPacientes().size(); i++) {
                        System.out.println("ID [" + i + "] - " + clinica.getPacientes().get(i).getNombre() + " (" + clinica.getPacientes().get(i).getTipo() + ")");
                    }

                    System.out.print("\nIngresa el ID de la mascota que deseas eliminar: ");
                    try {
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();

                        if (idEliminar >= 0 && idEliminar < clinica.getPacientes().size()) {
                            Mascota mascotaEliminada = clinica.getPacientes().remove(idEliminar);
                            System.out.println("La mascota \"" + mascotaEliminada.getNombre() + "\" fue eliminada de la lista.");
                            
                            // Sincronización inmediata y guardar tras eliminar
                            clinica.guardarTodo(carpeta);
                            System.out.println("Archivos de datos actualizados.");
                        } else {
                            System.out.println("Error: ID no válido. Debe estar entre 0 y " + (clinica.getPacientes().size() - 1) + ".");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Error: El ID debe ser un número entero.");
                        scanner.nextLine();
                    } catch (IOException e) {
                        System.out.println("Error al actualizar los archivos en la base de datos: " + e.getMessage());
                    }
                    break;

                case 4:
                    if (clinica.getPacientes().isEmpty()) {
                        System.out.println("No hay mascotas registradas.");
                        break;
                    }
                    System.out.println("\n--- VER CARTILLA ---");
                    for (int i = 0; i < clinica.getPacientes().size(); i++) {
                        System.out.println("ID [" + i + "] - " + clinica.getPacientes().get(i).getNombre());
                    }
                    System.out.print("\nIngresa el ID de la mascota para ver su cartilla: ");
                    try {
                    int idCartilla = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (idCartilla >= 0 && idCartilla < clinica.getPacientes().size()) {
                        Mascota m = clinica.getPacientes().get(idCartilla);
                        System.out.println("\nCartilla de " + m.getNombre() + ":");
                        m.getCartilla().mostrar();
                        System.out.println("Vacunas pendientes: " + m.vacunasPendientes());
                    } else {
                        System.out.println("Error: ID no válido.");
                    }
                    } catch (InputMismatchException e) {
                    System.out.println("Error: Ingresa un número válido.");
                    scanner.nextLine();
                    }
                    break;  
                
                case 5:
                    if (clinica.getPacientes().isEmpty()) {
                        System.out.println("No hay mascotas registradas.");
                        break;
                    }
                    System.out.println("\n--- ELIMINAR CARTILLA ---");
                    for (int i = 0; i < clinica.getPacientes().size(); i++) {
                        System.out.println("ID [" + i + "] - " + clinica.getPacientes().get(i).getNombre());
                    }
                    System.out.print("\nIngresa el ID de la mascota para eliminar su cartilla: ");
                    try {
                        int idEliminarCartilla = scanner.nextInt();
                        scanner.nextLine();
                        
                        if (idEliminarCartilla >= 0 && idEliminarCartilla < clinica.getPacientes().size()) {
                            Mascota m = clinica.getPacientes().get(idEliminarCartilla);
                            
                            
                            m.getCartilla().vaciar(); 
                            Files.deleteIfExists(carpeta.resolve("cartilla_" + m.getNombre() + ".txt"));
                            
                            System.out.println("La cartilla de " + m.getNombre() + " fue eliminada exitosamente.");
                        } else {
                            System.out.println("Error: ID no válido.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Ingresa un número válido.");
                        scanner.nextLine();
                    } catch (IOException e) {
                        System.out.println("Error al borrar el archivo fisico de la cartilla.");
                    }
                    break;
                

                case 6:
                    if (clinica.getPacientes().isEmpty()) {
                        System.out.println("No hay mascotas registradas.");
                        break;
                    }
                    System.out.println("\n--- ACTUALIZAR CARTILLA - AGREGAR VACUNA ---");
                    for (int i = 0; i < clinica.getPacientes().size(); i++) {
                        System.out.println("ID [" + i + "] - " + clinica.getPacientes().get(i).getNombre() + " (" + clinica.getPacientes().get(i).getTipo() + ")");
                    }
                    System.out.print("\nIngresa el ID de la mascota para actualizar su cartilla: ");
                    try {
                        int idActualizar = scanner.nextInt();
                        scanner.nextLine();
                        
                        if (idActualizar >= 0 && idActualizar < clinica.getPacientes().size()) {
                            Mascota m = clinica.getPacientes().get(idActualizar);
                            
                            System.out.println("Vacunas pendientes para " + m.getNombre() + ": " + m.vacunasPendientes());
                            String nombreVacuna = leerTextoNoVacio(scanner, "Ingresa el nombre de la vacuna que deseas registrar: ");
                            
                            if (doctor.aplicarVacuna(m, nombreVacuna)) {
                                clinica.guardarTodo(carpeta);
                                System.out.println("La vacuna '" + nombreVacuna + "' fue registrada correctamente.");
                                System.out.println("Cartilla actualizada.");
                            } else {
                                System.out.println("Registro cancelado. No se modificaron los archivos.");
                            }
                        } else {
                            System.out.println("Error: ID no valido.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Ingresa un numero valido.");
                        scanner.nextLine();
                    } catch (IOException e) {
                        System.out.println("Error al guardar la cartilla actualizada.");
                    }
                    break;

                case 7:
                    salir = true;
                    try {
                        clinica.guardarTodo(carpeta);
                        System.out.println("Saliendo del programa.");
                        System.out.println("Todos los datos fueron guardados exitosamente.");
                    } catch (IOException e) {
                        System.out.println("Error al realizar el guardado final.");
                    }
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
        scanner.close();
    }
    private static String leerTextoNoVacio(Scanner scanner, String mensaje) {
        String entrada = "";
        while (entrada.trim().isEmpty()) {
            System.out.print(mensaje);
            entrada = scanner.nextLine();
            if (entrada.trim().isEmpty()) {
                System.out.println("El campo no puede estar vacío. Intenta de nuevo.");
            }
        }
        return entrada.trim();
    }
}