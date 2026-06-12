package controller;

import java.util.List;
import java.util.Scanner;

import modelo.Persona;
import modelo.Usuario;
import modelo.RolUsuario;
/*
UsuarioController:
Se encarga de la interacción con la persona por consola.
NO accede a base de datos directamente.
Solo llama a UsuarioServicio.
 */
import servicio.UsuarioServicio;

public class UsuarioController {	

	//Objeto y Scanner
	private UsuarioServicio servicio = new UsuarioServicio();
	private Scanner sc = new Scanner(System.in);

	//Menú para la iteración 
	public void menu() {

		int opcion;

		do {
			System.out.println("\n--- MENU USUARIOS ---");
			System.out.println("1. Crear usuario");
			System.out.println("2. Listar usuarios");
			System.out.println("3. Buscar usuario por id");
			System.out.println("4. Modificar rol usuario");
			//System.out.println(". Modificar persona usuario");//Futura ampliación
			System.out.println("5. Borrar usuario");
			System.out.println("6. Login");
			System.out.println("0. Salir");

			//incializar variable con scanner
			opcion = sc.nextInt();
			sc.nextLine(); //Limpiar bufer

			switch (opcion) {
			case 1 -> crearU();
			case 2 -> listarU();
			case 3 -> buscarU();
			case 4 -> modificarU();
			case 5 -> borrarU();
			case 6 -> login();
			case 0 -> System.out.println("Saliendo...");
			default -> System.out.println("Opción inválida");
			}             

		} while (opcion != 0);	    	

	}

	// =========================
	// MÉTODOS CRUD
	// =========================

	private void crearU() {

		try {

			System.out.println("Crear usuario. \n");

			//Objetos
			Usuario u = new Usuario();
			Persona p = new Persona();
			RolUsuario r = new RolUsuario();

			//=========================
			//Pedir datos por consola
			//=========================

			//Pedir ID de persona (ya debe existir en BD)
			System.out.print("Id persona: ");
			int personaId = sc.nextInt();
			sc.nextLine(); // limpiar buffer

			p.setPersonaId(personaId);
			u.setPersona(p);

			// Rol 
			System.out.print("Id rol: ");
			int rolId = sc.nextInt();
			sc.nextLine(); // limpiar buffer

			System.out.println("Crear usuario ");			
			r.setRolUsuarioId(rolId);
			u.setRolUsuario(r);
			
			// Password
			System.out.print("Password: ");
			String password = sc.nextLine();
			u.setPassword(password);

			//Crear usuario
			boolean ok = servicio.crearUsuario(u);

			//Comprobar 
			if(ok)System.out.println("Usuario creada correctamente ");
			else System.out.println("Usuario no creado ");


		} catch (Exception e) {
			System.out.println("Error al crear el usario " + e);
		}
	}

	//Listar	
	public void listarU() {

		try {
			List<Usuario> lista = servicio.listarUsuarios();

			//Recorrer filas
			for(Usuario u : lista) {
				System.out.println(u);
			}

		} catch (Exception e) {
			System.out.println("Error al generar la lista " + e);
		}

	}


	//Modificar
	public void buscarU() {

		try {

			System.out.println("Buscar usuario por Id");
			int usuarioId = sc.nextInt();

			//Buscar
			Usuario u = servicio.buscarUsuarioPorId(usuarioId);

			//Comprobar busqueda
			if(u == null)System.out.println("El usuario no existe");
			else System.out.println(u);

		} catch (Exception e) {
			System.out.println("Error al buscar usuario por Id" + e);
		}
	}

	/*Cambiar el rol del usuario.
	 * Recibe el id del usuario y el id del nuevo rol*/
public void modificarU() {

   try {
	   
		System.out.println("Buscar usuario por Id");
		int usuarioId = sc.nextInt();
		sc.nextLine();//Limpiar buf.
		
       // Buscar el usuario
       Usuario u = servicio.buscarUsuarioPorId(usuarioId);

       if (u == null) {
           System.out.println("El usuario no existe");
           return;
       }

       // Cambio el rol
       System.out.println("Nuevo rol usuario");
		int nuevoRolId = sc.nextInt();    
       u.getRolUsuario().setRolUsuarioId(nuevoRolId);

       // Modifico el usuario
       boolean ok = servicio.modificarUsuario(u);

       // Muestrar resultado
       if (ok) {
           System.out.println(" Rol cambiado correctamente");
       } else {
           System.out.println(" No se ha cambiado (era el mismo rol)");
       }

   } catch (Exception e) {
       System.out.println(" Error: " + e.getMessage());
   }
}


	//Eliminar usuario
	public void borrarU() {
		try {

			System.out.print("Usuario id: ");
			int usuarioId = sc.nextInt();

			boolean ok = servicio.borrarUsuario(usuarioId);

			if (ok) System.out.println(" Usuario eliminado");
			else System.out.println(" No existe el usuario");

		} catch (Exception e) {
			System.out.println(" Error al borrar usuario: " + e.getMessage());
		}
	}
	
	private void login() {

	    try {

	        System.out.print("Email: ");
	        String email = sc.nextLine();

	        System.out.print("Password: ");
	        String password = sc.nextLine();

	        Usuario u = servicio.login(email, password);

	        if (u != null) {
	            System.out.println("Login correcto");
	            System.out.println("Usuario: " + u.getPersona().getNombre());
	            System.out.println("Rol: " + u.getRolUsuario().getRolNombre());
	        } else {
	            System.out.println("Email o password incorrectos");
	        }

	    } catch (Exception e) {
	        System.out.println("Error en login: " + e.getMessage());
	    }
	}


}



