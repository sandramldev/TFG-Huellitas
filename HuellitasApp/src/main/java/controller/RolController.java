package controller;

import servicio.RolServicio;

import java.util.List;
import java.util.Scanner;

import modelo.RolUsuario;

/*
RolController:
Se encarga de la interacción con la persona por consola.
NO accede a base de datos directamente.
Solo llama a UsuarioServicio.
 */
public class RolController {

	private RolServicio servicio = new RolServicio();

	private Scanner sc = new Scanner(System.in);
	public void menu() {

		int opcion;

		do {
			System.out.println("\n--- MENU ROLES ---");
			System.out.println("1. Crear rol");
			System.out.println("2. Listar roles");
			System.out.println("3. Buscar roles por id");
			System.out.println("4. Modificar rol");           
			System.out.println("0. Salir");

			opcion = sc.nextInt();

			switch (opcion) {
			case 1 -> crearRol();
			case 2 -> listarRoles();
			case 3 -> buscarRolPorId();
			case 4 -> modificarRol();            
			case 0 -> System.out.println("Saliendo...");
			default -> System.out.println("Opción inválida");
			}

		} while (opcion != 0);
	}



	// =========================
	// MÉTODOS CRUD
	// =========================
	/**
	 * Crear un nuevo rol
	 */
	public void crearRol( ) {
		try {

			sc.nextLine();//Limpiar bufer
			
			System.out.print("Nombre del rol: ");
			String nombreRol = sc.nextLine();
			
			
			RolUsuario r = new RolUsuario();
			r.setRolNombre(nombreRol);

			boolean ok = servicio.crearRolUsuario(r);

			if (ok) System.out.println(" Rol creado correctamente");
			else System.out.println("El rol ya existe");

		} catch (Exception e) {
			System.out.println(" Error al crear rol: " + e.getMessage());
		}
	}

	/**
	 * Listar todos los roles
	 */
	public void listarRoles() {
		try {

			List<RolUsuario> lista = servicio.listarRolUsuario();

			for (RolUsuario r : lista ) {
				System.out.println(r);
			}

		} catch (Exception e) {
			System.out.println(" Error al listar roles: " + e.getMessage());
		}
	}

	/**
	 * Buscar rol por ID
	 */
	public void buscarRolPorId() {
		try {
			System.out.print("Id rol: ");
			int rolId = sc.nextInt();
			sc.nextLine(); //Limpiar buffer

			RolUsuario r = servicio.bucarRolUsuario(rolId);

			if (r == null) System.out.println(" No existe el rol");
			else System.out.println(r);

		} catch (Exception e) {
			System.out.println(" Error al buscar rol: " + e.getMessage());
		}
	}

	/**
	 * Modificar nombre del rol
	 */
	public void modificarRol() {
		try {

			System.out.print("Id rol: ");
			int rolId  = sc.nextInt();
			sc.nextLine();

			RolUsuario r = servicio.bucarRolUsuario(rolId );

			if (r == null) {
				System.out.println(" No existe el rol");
				return;
			}
			System.out.println("Nuevo nombre del rol");
			String nuevoNombre = sc.nextLine();
			r.setRolNombre(nuevoNombre);

			boolean ok = servicio.modificarRolUsuario(r);

			if (ok) System.out.println(" Rol modificado");
			else System.out.println(" El rol ya tenía ese nombre");

		} catch (Exception e) {
			System.out.println(" Error al modificar rol: " + e.getMessage());
		}
	}

	/**
	 * Borrar rol
	 */
	public void borrarRol() {
		try {

			System.out.print("Id rol: ");
			int id = sc.nextInt();

			boolean ok = servicio.borrarRolUsuarioPorId(id);

			if (ok) System.out.println(" Rol eliminado");
			else System.out.println(" No existe el rol");

		} catch (Exception e) {
			System.out.println(" Error al borrar rol: " + e.getMessage());
		}
	}
}
