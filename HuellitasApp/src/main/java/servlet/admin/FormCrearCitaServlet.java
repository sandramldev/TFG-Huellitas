package servlet.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Mascota;
import modelo.Usuario;
import servicio.CitaServicio;
import servicio.MascotaServicio;

/**
 * Servlet encargado de preparar el formulario
 * de creación de citas para administradores.
 *
 * Carga mascotas disponibles y horas libres
 * según la fecha seleccionada.
 */

@WebServlet("/admin/FormCrearCitaServlet")

public class FormCrearCitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	//Servicios
	private MascotaServicio mascotaServicio = new MascotaServicio();
	private CitaServicio citaServicio = new CitaServicio();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//Sesion usuario
		Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuario");

		//Validar Sesión
		if(usuarioSesion == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}


		//Validar rol Admin
		if(!usuarioSesion.getRolUsuario().getRolNombre().equalsIgnoreCase("admin")) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}

	

		try {

			//Cargar lista mascotas
			List<Mascota> listaMascotas = mascotaServicio.listarMascota();

			//Enviar al jsp el listado de las mascotas
			request.setAttribute("listaMascotas",listaMascotas);
			
			//Cargar tiposCitas
			List<String> tiposCita = citaServicio.obtenerTiposCita();
			request.setAttribute("tiposCita", tiposCita);			

				String fechaStr = request.getParameter("fecha");

				//Control de errores
				if(fechaStr != null && !fechaStr.isBlank()) {
					
					LocalDate fecha =
					        LocalDate.parse(fechaStr);

					List<LocalDateTime> horasDisponibles =
					        citaServicio.obtenerHorasDisponibles(
					                fecha);

					request.setAttribute(
					        "horasDisponibles",
					        horasDisponibles);

				//Si no hay horas
				if(horasDisponibles.isEmpty()){
					request.setAttribute("error", "No hay horas disponibles");
				}

				}
			
			//forward formulario
			request.getRequestDispatcher( "/admin/crearCita.jsp").forward(request, response);   

		} catch (Exception e) {

			e.printStackTrace();

			response.sendRedirect(
					request.getContextPath()+ 
					"/admin/error.jsp"
					+ "?error=No+se+pudo+crear+la+cita");
		}

	}


}
