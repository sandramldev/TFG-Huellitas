package validador;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import dao.CitaDAO;
import modelo.Mascota;



/* La validación del horario de las citas se implementa en la capa de servicio
 * para garantizar la integridad de la lógica de negocio independientemente
 * de la interfaz utilizada (consola, web, API, etc.).
 *
 * Actualmente se controlan:
 * - fines de semana (no se permiten citas)
 * - horario de atención de la clínica (mañana - tarde)
 * - Agenda diaría (admin/veterinari@)
 *
 * El control de festivos se plantea como mejora futura del sistema.
 */

public class CitaValidador {

	// Horario clínica
	private static final int HORA_INICIO_MANANA = 9;
	private static final int HORA_FIN_MANANA = 14;
	private static final int HORA_INICIO_TARDE = 16;
	private static final int HORA_FIN_TARDE = 20;
	public static final int INTERVALO_MINUTOS = 30;

	private CitaDAO  citaDAO = new CitaDAO();


	public boolean validarHorario(Timestamp fechaHora) {

		if (fechaHora == null) return false;

		LocalDateTime fecha = fechaHora.toLocalDateTime();
		int hora = fecha.getHour();

		// No fines de semana
		DayOfWeek dia = fecha.getDayOfWeek();
		if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY)
			return false;

		// Horario mañana
		if (hora >= HORA_INICIO_MANANA && hora < HORA_FIN_MANANA)
			return true;

		// Horario tarde
		if (hora >= HORA_INICIO_TARDE && hora < HORA_FIN_TARDE)
			return true;

		return false;
	}


	public boolean validarIntervalo(Timestamp fechaHora, int citaId) throws SQLException {

		if(fechaHora == null) return false;

		LocalDateTime fecha = fechaHora.toLocalDateTime();

		//Calcular el rango permitido al rededor de la cita

		Timestamp inicio =
		        Timestamp.valueOf(fecha);

		Timestamp fin =
		        Timestamp.valueOf(fecha);


		//Comprobar si la cita existe en este rango
		return !citaDAO.existeCitaEnRango(inicio, fin, citaId);

	}
	
	
	//Validar cita en pasado	
	public boolean validarCitaEnPasado(Timestamp fechaHora) {
		
		if(fechaHora == null) return false;
		
		LocalDateTime ahora = LocalDateTime.now();
		LocalDateTime fecha = fechaHora.toLocalDateTime();
		
		//La fecha debe ser igual o posterior al momento, entonces es válida
		return !fecha.isBefore(ahora);		
		
	}
	
	/*----------------------------------------------------------------------------------*/
	
	//Validaciones mascota: No permite citas para mascotas fallecidas
	
	public boolean validarMascotaViva(Mascota mascota) {
		
		if(mascota == null) return false;
		
		return mascota.getFechaFallecimiento() == null;
		
		
	}
	

}


