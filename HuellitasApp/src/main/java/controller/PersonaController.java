package controller;

import java.util.List;
import java.util.Scanner;

import modelo.Persona;
import servicio.PersonaServicio;

/*
 PersonaController:
 Se encarga de la interacción con el usuario por consola.
 NO accede a base de datos directamente.
 Solo llama a PersonaServicio.
*/

public class PersonaController {

	
    private PersonaServicio servicio = new PersonaServicio();
    private Scanner sc = new Scanner(System.in);

    public void menu() {    	
    	

        int opcion;

        do {
            System.out.println("\n--- MENU PERSONAS ---");
            System.out.println("1. Crear persona");
            System.out.println("2. Listar personas");
            System.out.println("3. Buscar persona por id");
            System.out.println("4. Modificar persona");
            System.out.println("5. Borrar persona");
            System.out.println("0. Salir");

            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> crearP();
                case 2 -> listarP();
                case 3 -> buscarP();
                case 4 -> modificarP();
                case 5 -> borrarP();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }

    // =========================
    // MÉTODOS CRUD
    // =========================

    private void crearP() {
        try {
            Persona p = new Persona();

            System.out.print("Nombre: ");
            p.setNombre(sc.nextLine());

            System.out.print("Apellidos: ");
            p.setApellidos(sc.nextLine());

            System.out.print("Teléfono: ");
            p.setTelefono(sc.nextLine());

            System.out.print("Email: ");
            p.setEmail(sc.nextLine());

           
            int id = servicio.crearPersonaYDevolverId(p);

            boolean ok = id != -1;

            if (ok)
                System.out.println(" Persona creada correctamente");
            else
                System.out.println(" No se pudo crear (ya existe)");

        } catch (Exception e) {
            System.out.println(" Error al crear persona: " + e.getMessage());
        }
    }

    private void listarP() {
        try {
            List<Persona> lista = servicio.listarPersona();

            for (Persona p : lista) {
                System.out.println(p);
            }

        } catch (Exception e) {
            System.out.println(" Error al listar personas: " + e.getMessage());
        }
    }

    private void buscarP() {
        try {
            System.out.print("Id persona: ");
            int id = sc.nextInt();

            Persona p = servicio.buscarPersonaPorId(id);

            if (p == null)
                System.out.println(" No existe esa persona");
            else
                System.out.println(p);

        } catch (Exception e) {
            System.out.println(" Error al buscar persona: " + e.getMessage());
        }
    }

    private void modificarP() {
        try {
            System.out.print("Id persona: ");
            int id = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            Persona p = servicio.buscarPersonaPorId(id);

            if (p == null) {
                System.out.println(" No existe");
                return;
            }

            System.out.print("Nuevo nombre: ");
            p.setNombre(sc.nextLine());

            System.out.print("Nuevos apellidos: ");
            p.setApellidos(sc.nextLine());

            System.out.print("Nuevo teléfono: ");
            p.setTelefono(sc.nextLine());

            System.out.print("Nuevo email: ");
            p.setEmail(sc.nextLine());

            boolean ok = servicio.modificarPersona(p);

            if (ok)
                System.out.println(" Persona modificada");
            else
                System.out.println(" No se pudo modificar");

        } catch (Exception e) {
            System.out.println(" Error al modificar persona: " + e.getMessage());
        }
    }

    private void borrarP() {
        try {
            System.out.print("Id persona: ");
            int id = sc.nextInt();

            boolean ok = servicio.borrarPersona(id);

            if (ok)
                System.out.println(" Persona borrada");
            else
                System.out.println(" No se pudo borrar");

        } catch (Exception e) {
            System.out.println(" Error al borrar persona: " + e.getMessage());
        }
    }
}
