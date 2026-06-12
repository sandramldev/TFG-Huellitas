package vista;

import java.util.Scanner;

import controller.CitaController;
import controller.ClienteController;
import controller.MascotaController;
import controller.PersonaController;
import controller.RolController;
import controller.UsuarioController;
import modelo.Cliente;
import servicio.ClienteServicio;

public class Main {

	public static void main(String[] args) {



		//Objetos
		RolController rolController = new RolController();
		ClienteController clienteController = new ClienteController();
		PersonaController personaController = new PersonaController();
		UsuarioController usuarioController = new UsuarioController();
		MascotaController mascotaController = new MascotaController();
		CitaController citaController = new CitaController();


		//Scanner y menú con los métodos crud de los objetos
		Scanner sc = new Scanner(System.in);
		int opcion;

		do {
			System.out.println("=== MENU PRINCIPAL ===");
			System.out.println("1. Roles");
			System.out.println("2. Clientes");
			System.out.println("3. Personas");
			System.out.println("4. Usuarios");
			System.out.println("5. Mascotas");		
			System.out.println("6. Citas");
			
			
			
			
			System.out.println("0. Salir");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1 -> rolController.menu();
			case 2 -> clienteController.menu();
			case 3 -> personaController.menu();
			case 4 -> usuarioController.menu();
			case 5 -> mascotaController.menu();
			case 6 -> citaController.menu();
			
			}

		} while (opcion != 0);

		sc.close();
		
	
	}
	


}


