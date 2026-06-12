
package controller;

import java.util.List;
import java.util.Scanner;

import modelo.Cliente;
import modelo.Persona;
import servicio.ClienteServicio;
/*
ClienteController:
Se encarga de la interacción con la persona por consola.
NO accede a base de datos directamente.
Solo llama a UsuarioServicio.
 */

public class ClienteController {

    private ClienteServicio servicio = new ClienteServicio();
    private Scanner sc = new Scanner(System.in);

    public void menu() {

        int opcion;

        do {
            System.out.println("\n--- MENU CLIENTES ---");
            System.out.println("1. Crear cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente por id");
            System.out.println("4. Modificar cliente");
            System.out.println("5. Borrar cliente");
            System.out.println("6. Buscar cliente por personaId");
            System.out.println("0. Salir");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> crear();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> modificar();
                case 5 -> borrar();
                case 6 -> buscarPorPersonaId();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }

 // =========================
 	// MÉTODOS CRUD
 	// =========================
    
    
    private void crear() {
        try {
            System.out.print("Persona id: ");
            int personaId = sc.nextInt();

            Cliente c = new Cliente();
            Persona p = new Persona();
            p.setPersonaId(personaId); 
            c.setPersona(p);

            boolean ok = servicio.crearCliente(c);

            if (ok) System.out.println(" Cliente creado");
            else System.out.println(" No se pudo crear");

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void listar() {
        try {
        	
            List<Cliente> lista = servicio.listarClientes();
            for (Cliente c : lista) {
                System.out.println(c);
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void buscar() {
        try {
            System.out.print("Id cliente: ");
            int id = sc.nextInt();

            Cliente c = servicio.buscarClientePorId(id);

            if (c == null)
                System.out.println(" No existe");
            else
                System.out.println(c);

        } catch (Exception e) {
            System.out.println(" Error cliente: " + e.getMessage());
        }
    }

    //Buscor persona por Id
    private void buscarPorPersonaId() {

        try {

            System.out.print("Persona id: ");
            int personaId = sc.nextInt();

            Cliente c =
                    servicio.buscarClientePorPersonaId(personaId);

            if (c == null) {

                System.out.println("No existe cliente para esa persona");

            } else {

                System.out.println("Cliente encontrado");

                System.out.println(
                        "ClienteId = "
                        + c.getClienteId());

                System.out.println(
                        "PersonaId = "
                        + c.getPersona().getPersonaId());

                System.out.println(
                        "Nombre = "
                        + c.getPersona().getNombre());
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: "
                    + e.getMessage());
        }
    }
    private void modificar() {
        try {
            System.out.print("Id cliente: ");
            int clienteId = sc.nextInt();

            Cliente c = servicio.buscarClientePorId(clienteId);
            if (c == null) {
                System.out.println(" No existe");
                return;
            }

            System.out.print("Nuevo persona id: ");
            int nuevaPersonaId = sc.nextInt();

            Persona p = new Persona();
            p.setPersonaId(nuevaPersonaId);
            c.setPersona(p);

            boolean ok = servicio.modificarCliente(c);

            if (ok) System.out.println(" Cliente modificado");
            else System.out.println(" No se pudo modificar");

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void borrar() {
        try {
            System.out.print("Id cliente: ");
            int id = sc.nextInt();

            boolean ok = servicio.borrarCliente(id);

            if (ok) System.out.println(" Cliente borrado");
            else  System.out.println(" No se pudo borrar");

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
}
