package controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import modelo.Cliente;
import modelo.Mascota;
import servicio.MascotaServicio;

/*
MascotaController:
Se encarga de la interacción con la persona por consola.
NO accede a base de datos directamente.
Solo llama a UsuarioServicio.
 */

public class MascotaController {


	//Objeto
	private MascotaServicio servicio = new MascotaServicio();

	//Scanner para pedir datos por consola
	Scanner sc = new Scanner(System.in);

	//Ménu para la iteración 

	public void menu() {

		//Variable para las diferentes opciones del menú		
		int opcion;		

		do {
			System.out.println("\n--- MENU MASCOTAS ---");
			System.out.println("1. Crear mascota");
			System.out.println("2. Listar mascotas");
			System.out.println("3. Buscar mascota por id");
			System.out.println("4. Modificar mascota cliente");   
			System.out.println("5. Eliminar mascota");
			System.out.println("0. Salir");

			//Incialización de la variable con scanner
			opcion = sc.nextInt();
			sc.nextLine(); //Limpiar bufer

			//Llamar métodos crud
			switch (opcion) { 
			case 1 -> crearM();
			case 2 ->  listarM();
			case 3 -> buscarM();
			case 4 -> modificarM();
			case 5 -> eliminarM();			
			case 0 -> System.out.println("Saliendo...");
			default -> System.out.println("Opción inválida");

			}

		} while (opcion !=0);

	}

	// =========================
	// MÉTODOS CRUD
	// =========================

	//Crear
	public void crearM() {

		try {
			
			//nuevo Objeto
			Mascota m = new Mascota();
			Cliente c = new Cliente();
			
			//=========================
			//Pedir datos por consola
			//=========================
			
			//Pedir ID de cliente (ya debe existir en BD)
			System.out.println("Cliente id: ");
			int clienteId = sc.nextInt();
			sc.nextLine(); // limpiar buffer
			
			//Relación Cliente -> Mascota
			c.setClienteId(clienteId);
			m.setCliente(c);
			
			//Datos de la nueva mascota
			System.out.println("Nombre: ");
			m.setNombre(sc.nextLine());
			
			System.out.println("Fecha de nacimiento: ");
			m.setFechaNacimiento(Date.valueOf(sc.nextLine()));
			
			System.out.println("Fecha de fallecimiento (ENTER si la mascota está viva): ");
			String fecha = sc.nextLine();

			if (fecha.isEmpty()) {
			    m.setFechaFallecimiento(null); // mascota viva
			} else {
			    m.setFechaFallecimiento(Date.valueOf(fecha));
			}

			
			//Crear mascota			
			boolean ok = servicio.crearMascota(m);
			
			//Comprobar
			if(ok) System.out.println(" Mascota creada correctamente");
			else  System.out.println(" No se pudo crear (ya existe)");
			
		} catch (Exception e) {
			System.out.println(" Error al crear la mascota: " + e.getMessage());
		}
	}
	
	//Listar
	public void listarM() {
		
		try {
			
		List<Mascota> lista = servicio.listarMascota();
		for(Mascota m : lista) {
			System.out.println(m);
		}			
			
		} catch (Exception e) {
			System.out.println(" Error al listar mascotas: " + e.getMessage());
		}		
	}
	
	//Buscar 
	
	public void buscarM() {
		
		try {
			
			//=========================
			//Pedir datos por consola
			//=========================			
			System.out.println("Mascota id: ");
			int mascotaId = sc.nextInt();
			sc.nextLine(); //Limpiar buffer
			
			//Consulta por capas
			Mascota m = servicio.buscarMascotaPorId(mascotaId);
			
			//Comprobar si los datos existen
			if(m == null)System.out.println("La mascota no existe ");
			else System.out.println(m);
			
			
		} catch (Exception e) {
			System.out.println(" Error al buscar mascota por Id: " + e.getMessage());
		}
	}
	
	//Modificar 
	public void modificarM() {
		
		try {
			//=========================
			//Pedir datos por consola
			//=========================	
			
			System.out.println("Buscar mascota por Id");
			int mascotaId = sc.nextInt();
			sc.nextLine();//Limpiar buf.
			
			//Buscar la mascota			
			Mascota m = servicio.buscarMascotaPorId(mascotaId);
			
			//Comprobar
			if (m == null) {
			    System.out.println("La mascota no existe ");
			    return;
			}

			
			//Cambio el cliente		
			Cliente c = new Cliente();
			System.out.println("Nuevo cliente id: ");
			int nuevoClienteId = sc.nextInt();
			c.setClienteId(nuevoClienteId);
			m.setCliente(c);
			
			//Modifico la mascota
			boolean ok = servicio.modificarMascotaPorId(m);
			
			//Mostrar resultado
			if(ok)System.out.println(" Mascota modificada correctamente");
			else  System.out.println(" No se pudo modificar");
						
			
		} catch (Exception e) {
			System.out.println(" Error al modificar los datos de la mascota: " + e.getMessage());
		}
		
	}
	
	
	//Borrar
	 public void eliminarM() {
	        try {
	        	
	        	System.out.print("Id mascota: ");
	            int mascotaId = sc.nextInt();
	            
	            boolean ok = servicio.borrarMascota(mascotaId);
	            if (ok) System.out.println(" Mascota eliminada");
	            else System.out.println(" No existe la mascota");

	        } catch (Exception e) {
	            System.out.println(" Error al borrar mascota: " + e.getMessage());
	        }
	    }
}
