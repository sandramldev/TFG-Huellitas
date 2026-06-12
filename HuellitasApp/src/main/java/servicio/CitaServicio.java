package servicio;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.CitaDAO;
import dao.MascotaDAO;
import dao.UsuarioDAO;
import modelo.Cita;
import modelo.Mascota;
import modelo.Usuario;
import validador.CitaValidador;




public class CitaServicio {

	CitaValidador validador = new CitaValidador();

	// Horario clínica
	private static final int HORA_INICIO_MANANA = 9;
	private static final int HORA_FIN_MANANA = 14;
	private static final int HORA_INICIO_TARDE = 16;
	private static final int HORA_FIN_TARDE = 20;



	//Servicio
	private CitaDAO  citaDAO = new CitaDAO();
	private UsuarioDAO usuarioDAO =	new UsuarioDAO();
	private MascotaDAO mascotaDAO =	new MascotaDAO();



	//Crear cita
	public String crearCita(Cita c) throws SQLException {

		//Control null
		if (c == null || c.getMascota() == null || c.getFechaHora() == null)
			return "Datos invalidos";

		//Validar mascota viva		
		if(!validador.validarMascotaViva(c.getMascota()))
			return "Mascota inactiva";

		//Validar fecha en pasado
		if (!validador.validarCitaEnPasado(c.getFechaHora()))
			return "Fecha incorrecta";

		//Existe cita?
		boolean existe =
			    citaDAO.existeCitaMascotaDia(

			        c.getMascota().getMascotaId(),

			        c.getFechaHora()
			         .toLocalDateTime()
			         .toLocalDate()
			);

			if(existe){

			    return "La mascota ya tiene una cita ese dia";
			}
		// Validar horario clínica
		if (!validador.validarHorario(c.getFechaHora()))
			return "Horario incorrecto";


		//Intervalos de 30 minutos
		if(!validador.validarIntervalo(c.getFechaHora(), 30))
			return "La cita se solapa con otra";

		c.setEstado("PENDIENTE");

		boolean insertada =
		        citaDAO.insertarCita(c);

		if(insertada){
			return "OK";
		}

		return "Error al guardar la cita";
	}	


	//Obtener listado de las Citas de la BD
	public List<Cita>listarCita() throws SQLException{
		return citaDAO.listarCita();
	}

	//Buscar Cita por ID
	public Cita buscarCitaPorId(int cId) throws SQLException {
		return citaDAO.leerCitaPorId(cId);		
	}
	
	//Cita por usuario
	public List<Cita>listarCitasPorUsuario(int usuarioId)throws SQLException{

		return citaDAO.listarCitasPorUsuario(usuarioId);
	}


	//Modificar Cita existente, con control de Objetos nulos para fechas
	public boolean modificarCita(Cita c) throws SQLException {

		//Control null
		if (c == null) return false;
		if (c.getFechaHora() == null) return false;

		Cita citaBD = citaDAO.leerCitaPorId(c.getCitaId());

		if (citaBD == null)
			return false;

		if (!"PENDIENTE".equalsIgnoreCase(citaBD.getEstado()))
			return false;
		
		// Solo validar fecha futura si la cita sigue pendiente
		if ("PENDIENTE".equalsIgnoreCase(c.getEstado())) {

		    if (!validador.validarCitaEnPasado(c.getFechaHora())) {

		        System.out.println("FALLA validarCitaEnPasado");
		        return false;
		    }
		}

		// Validar horario clínica
		if (!validador.validarHorario(c.getFechaHora())){
			  System.out.println("FALLA validarHorario");
			return false;
		}
		//Validar intervalo		
		if (!validador.validarIntervalo(c.getFechaHora(), c.getCitaId())) {
			 System.out.println("FALLA validarIntervalo");
			return false;}
		
		System.out.println("Estado nuevo = " + c.getEstado());
		System.out.println("FechaHora = " + c.getFechaHora());
			
		return citaDAO.actualizarCita(c);
	}

	public List<String> obtenerTiposCita()
			throws SQLException{

		return citaDAO.obtenerTiposCita();
	}

	//Cancelar cita si está en estado pendiente
	public boolean cancelarCita(int id) throws SQLException {

		Cita citaBD = citaDAO.leerCitaPorId(id);

		if (citaBD == null)
			return false;

		if (!"PENDIENTE".equalsIgnoreCase(citaBD.getEstado()))
			return false;

		citaBD.setEstado("CANCELADA");

		return citaDAO.actualizarCita(citaBD);
	}

	
	//Cancelar cita por usuario
	public boolean	cancelarCitaUsuario(int citaId,	int usuarioId) throws SQLException{

		Cita c = citaDAO.leerCitaPorId(	citaId);

		// Validar existencia
		if(c == null){

			return false;
		}

		// Solo pendientes
		if(!c.getEstado().equalsIgnoreCase(	"PENDIENTE")){	return false;
		}

		// Validar propietario
		Mascota mascota =	c.getMascota();
		Mascota mascotaCompleta =mascotaDAO.obtenerMascotaPorId(mascota.getMascotaId());

		if(mascotaCompleta == null){

			return false;
		}

		int usuarioMascota =

			mascotaCompleta
			.getCliente()
			.getPersona()
			.getPersonaId();

		Usuario u =	usuarioDAO.obtenerUsuarioPorId(usuarioId);

		if(u == null){

			return false;
		}

		if(usuarioMascota != u.getPersona().getPersonaId()){

			return false;
		}

		return citaDAO
			.eliminarCita(citaId);
	}

	
	/*---------------------------------------------------------------------------------*/
	//Cita realizada
	public boolean marcarComoRealizada(int citaId) throws SQLException {

		Cita citaBD = citaDAO.leerCitaPorId(citaId);

		if (citaBD == null)
			return false;

		citaBD.setEstado("REALIZADA");

		return citaDAO.actualizarCita(citaBD);
	}

	//Ver agenda veterinaria
	public Map<LocalDateTime, Cita> obtenerAgendaVeterinaria(LocalDate fecha) throws SQLException {

		List<Cita> citas = citaDAO.obtenerCitasPorDia(fecha);

		Map<LocalDateTime, Cita> agenda = new LinkedHashMap<>();


		LocalDateTime inicio = fecha.atTime(HORA_INICIO_MANANA, 0);
		LocalDateTime fin = fecha.atTime(HORA_FIN_TARDE, 0);

		while (inicio.isBefore(fin)) {

			agenda.put(inicio, null);
			inicio = inicio.plusMinutes(CitaValidador.INTERVALO_MINUTOS);
		}

		for (Cita cita : citas) {
			agenda.put(cita.getFechaHora().toLocalDateTime(), cita);
		}

		return agenda;
	}



//Eliminar admin
	public boolean eliminarCitaAdmin(int citaId)
			throws SQLException {

			Cita citaBD =
				citaDAO.leerCitaPorId(citaId);

			if (citaBD == null)
				return false;

			
			// No borrar realizadas
			if ("REALIZADA".equalsIgnoreCase(
					citaBD.getEstado()))
				return false;

			return citaDAO.eliminarCita(citaId);
		}




	//Lógica de la agenda


	//Horas disponibles para asignar nuevas citas
	public List<LocalDateTime> obtenerHorasDisponibles(LocalDate fecha) throws SQLException {

		List<LocalDateTime> disponibles = new ArrayList<>();

		// horas ocupadas en BD
		List<Timestamp> ocupadas = citaDAO.obtenerHorasOcupadasPorDia(fecha);

		Set<LocalDateTime> horasOcupadas = new HashSet<>();

		for (Timestamp ts : ocupadas) {
			horasOcupadas.add(ts.toLocalDateTime());
		}

		// ---- HORARIO MAÑANA ----
		LocalDateTime hora = fecha.atTime(HORA_INICIO_MANANA, 0);
		LocalDateTime finManana = fecha.atTime(HORA_FIN_MANANA,0);

		while (hora.isBefore(finManana)) {

			
			
			Timestamp inicio =
			        Timestamp.valueOf(hora);

			Timestamp fin =
			        Timestamp.valueOf(hora);

			boolean ocupada =
			        citaDAO.existeCitaEnRango(
			                inicio,
			                fin,
			                0);

			if(	!ocupada && ( !hora.toLocalDate().equals(LocalDate.now()) || hora.isAfter(
							LocalDateTime.now())

					)

				){

					disponibles.add(hora);
				}

			hora = hora.plusMinutes(CitaValidador.INTERVALO_MINUTOS);
		}

		// ---- HORARIO TARDE ----
		hora = fecha.atTime(HORA_INICIO_TARDE, 0);
		LocalDateTime finTarde = fecha.atTime(HORA_FIN_TARDE,0);

		while (hora.isBefore(finTarde)) {

			
			Timestamp inicio =
			        Timestamp.valueOf(hora);

			Timestamp fin =
			        Timestamp.valueOf(hora);

			boolean ocupada =
			        citaDAO.existeCitaEnRango(
			                inicio,
			                fin,
			                0);

			if(	!ocupada && ( !hora.toLocalDate().equals(LocalDate.now()) || hora.isAfter(
					LocalDateTime.now())

			)

		){

			disponibles.add(hora);
		}
			hora = hora.plusMinutes(CitaValidador.INTERVALO_MINUTOS);
		}

		return disponibles;
	}

}
