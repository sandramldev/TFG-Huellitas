package controller;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import modelo.Cita;
import modelo.Mascota;
import servicio.CitaServicio;

public class CitaController {

	private CitaServicio servicio = new CitaServicio();
	private Scanner sc = new Scanner(System.in);

	public void menu() {

		int opcion;

		do {
			System.out.println("\n--- MENU CITAS ---");
			System.out.println("1. Crear cita");
			System.out.println("2. Listar citas");
			System.out.println("3. Buscar cita por id");
			System.out.println("4. Modificar cita");
			System.out.println("5. Cancelar cita");
			System.out.println("6. Marcar como realizada");
			System.out.println("7. Eliminar eliminar cita (admin)");
			System.out.println("8. Ver agenda veterinaria (admin)");
			System.out.println("0. Salir");

			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1 -> crearCita();
			case 2 -> listarCitas();
			case 3 -> buscarCita();
			case 4 -> modificarCita();
			case 5 -> cancelarCita();
			case 6 -> marcarComoRealizada();
			case 7 -> eliminarCitaAdmin();
			case 8 -> verAgendaVeterinaria();
			case 0 -> System.out.println("Saliendo...");
			default -> System.out.println("Opción inválida");
			}

		} while (opcion != 0);
	}

	// =========================
	// MÉTODOS CRUD
	// =========================


	//Crear cita
	private void crearCita() {
		try {

			Cita c = new Cita();
			Mascota m = new Mascota();

			System.out.println("Mascota id: ");
			int mascotaId = sc.nextInt();
			sc.nextLine();

			m.setMascotaId(mascotaId);
			c.setMascota(m);

			System.out.println("Fecha (YYYY-MM-DD): ");
			LocalDate fecha = LocalDate.parse(sc.nextLine());

			List<LocalDateTime> horas = servicio.obtenerHorasDisponibles(fecha);

			if (horas.isEmpty()) {
				System.out.println("No hay horas disponibles ese día.");
				return;
			}

			System.out.println("\nHoras disponibles:");

			for (int i = 0; i < horas.size(); i++) {
				System.out.println((i+1) + ") " + horas.get(i).toLocalTime());
			}

			System.out.println("Selecciona una hora:");
			int opcion = sc.nextInt();
			sc.nextLine();

			LocalDateTime fechaHora = horas.get(opcion-1);

			c.setFechaHora(Timestamp.valueOf(fechaHora));

			System.out.println("Motivo de la cita:");
			c.setCitaTipo(sc.nextLine());

			String resultado = servicio.crearCita(c);

			if ("OK".equals(resultado)) System.out.println("Cita creada correctamente");
			else System.out.println(resultado);

		} catch (Exception e) {
			System.out.println("Error al crear cita: " + e.getMessage());
		}
	}

	private void verAgendaVeterinaria() {

		try {

			System.out.println("Fecha (YYYY-MM-DD): ");
			LocalDate fecha = LocalDate.parse(sc.nextLine());

			Map<LocalDateTime, Cita> agenda = servicio.obtenerAgendaVeterinaria(fecha);

			System.out.println("\nAGENDA " + fecha);

			for (Map.Entry<LocalDateTime, Cita> entrada : agenda.entrySet()) {

				LocalTime hora = entrada.getKey().toLocalTime();
				Cita cita = entrada.getValue();

				if (cita == null) {

					System.out.println(hora + "  LIBRE");

				} else {

					System.out.println(
							hora + "  "
									+ cita.getCitaTipo()
									+ "  "
									+ cita.getMascota().getNombre()
									+ " (" 
									+ cita.getMascota().getCliente().getPersona().getNombre()
									+  " "
									+ cita.getMascota().getCliente().getPersona().getApellidos()
									+")"
							);
				}
			}

		} catch (Exception e) {

			System.out.println("Error al mostrar agenda: " + e.getMessage());
		}
	}


	private void listarCitas() {
		try {
			List<Cita> lista = servicio.listarCita();
			for (Cita c : lista) {
				System.out.println(c);
			}
		} catch (Exception e) {
			System.out.println(" Error al listar citas: " + e.getMessage());
		}
	}

	private void buscarCita() {
		try {
			System.out.println("Cita id: ");
			int id = sc.nextInt();
			sc.nextLine();

			Cita c = servicio.buscarCitaPorId(id);

			if (c == null) System.out.println("La cita no existe");
			else System.out.println(c);

		} catch (Exception e) {
			System.out.println(" Error al buscar cita: " + e.getMessage());
		}
	}

	private void modificarCita() {
		try {
			System.out.println("Cita id: ");
			int id = sc.nextInt();
			sc.nextLine();

			Cita c = servicio.buscarCitaPorId(id);
			if (c == null) {
				System.out.println("La cita no existe");
				return;
			}

			System.out.println("Nueva fecha (YYYY-MM-DD): Hora (HH:MM:SS): ");
			System.out.println("\nFormato: 2026-01-28 10:30:00");
			c.setFechaHora(Timestamp.valueOf(sc.nextLine()));

			//  System.out.println("Nueva hora (HH:MM): ");
			// c.setFechaHora(sc.nextLine());

			boolean ok = servicio.modificarCita(c);

			if (ok) System.out.println(" Cita modificada correctamente");
			else System.out.println(" No se pudo modificar");

		} catch (Exception e) {
			System.out.println(" Error al modificar cita: " + e.getMessage());
		}
	}

	/*-----------------------------------------------------------------------------
	 * Métodos para modificar citas
	 * -----------------------------------------------------------------------------*/
	private void cancelarCita() {
		try {
			System.out.print("Cita id: ");
			int id = sc.nextInt();
			sc.nextLine();

			boolean ok = servicio.cancelarCita(id);

			if (ok) System.out.println(" Cita cancelada correctamente");
			else System.out.println(" No se pudo cancelar (quizá no está pendiente)");

		} catch (Exception e) {
			System.out.println(" Error al cancelar cita: " + e.getMessage());
		}
	}

	private void marcarComoRealizada() {
		try {
			System.out.print("Cita id: ");
			int id = sc.nextInt();
			sc.nextLine();

			boolean ok = servicio.marcarComoRealizada(id);

			if (ok) System.out.println(" Cita marcada como realizada");
			else System.out.println(" No se pudo marcar (quizá no existe)");

		} catch (Exception e) {
			System.out.println(" Error: " + e.getMessage());
		}
	}
	/* -----------------------------------------------------------------------------*/
	private void eliminarCitaAdmin() {
		try {
			System.out.print("Cita id: ");
			int id = sc.nextInt();
			sc.nextLine();

			boolean ok = servicio.eliminarCitaAdmin(id);

			if (ok) System.out.println(" Cita eliminada");
			else System.out.println(" No existe la cita");

		} catch (Exception e) {
			System.out.println(" Error al borrar cita: " + e.getMessage());
		}
	}
}
