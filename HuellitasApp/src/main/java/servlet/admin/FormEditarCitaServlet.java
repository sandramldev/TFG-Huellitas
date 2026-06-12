package servlet.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Cita;
import modelo.Usuario;
import servicio.CitaServicio;


/**
 * Servlet encargado de traer el listado
 *  de citas para administradores.
 *
 * Modificar mascotas, horas, fechas
 * 
 */
@WebServlet("/admin/FormEditarCitaServlet")
public class FormEditarCitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  	
		//Servicios		
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
			
			//Acción válida desde el formulario
			int citaId = Integer.parseInt(request.getParameter("id"));
			
			
			try {
				//Cargar Cita
				Cita cita = citaServicio.buscarCitaPorId(citaId);
				//Enviar cita
				request.setAttribute("cita", cita);
				
				//Tipos de cita
				List<String> tiposCita = citaServicio.obtenerTiposCita();
				request.setAttribute("tiposCita", tiposCita);
				

				String fechaStr = request.getParameter("fecha");
				LocalDate fecha;

				if(fechaStr != null
				        && !fechaStr.isBlank()) {
				    fecha = LocalDate.parse(fechaStr);

				} else {

				    fecha =
				        cita.getFechaHora()
				            .toLocalDateTime()
				            .toLocalDate();
				}

				List<LocalDateTime> horasDisponibles = citaServicio.obtenerHorasDisponibles(
				                fecha);
				
				LocalDateTime horaActual = cita.getFechaHora().toLocalDateTime();

				if(!horasDisponibles.contains(horaActual)){
				    horasDisponibles.add(horaActual);
				    Collections.sort(horasDisponibles);
				}

				request.setAttribute("horasDisponibles", horasDisponibles);

				if(horasDisponibles.isEmpty()) {
				    request.setAttribute("error", "No hay horas disponibles");
				}
					
				
				//forward
				request.getRequestDispatcher("/admin/editarCita.jsp").forward(request, response);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() 
						+ "/admin/error.jsp"
						+ "?error=No+se+pudo+editar+la+cita");
			}
					
		
	}

	
}
