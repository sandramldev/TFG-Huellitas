package servlet.admin;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Cita;
import modelo.Usuario;
import servicio.CitaServicio;

/**

 */
@WebServlet("/admin/ActualizarCitaServlet")
public class ActualizarCitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Servicio	
	private CitaServicio citaServicio = new CitaServicio();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("ENTRANDO EN ActualizarCitaServlet");

		//Validar usuario
		Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuario");

		//Validar sesión
		if(usuarioSesion == null) {			
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		//Validar rol admin		
		if(!usuarioSesion.getRolUsuario().getRolNombre().equalsIgnoreCase("admin")) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;			
		}

		try {

			//Leer datos
			int citaId = Integer.parseInt(request.getParameter("id"));
			String fecha = request.getParameter("fecha");
			String hora = request.getParameter("hora");
			String tipo = request.getParameter("tipo");
			String estado = request.getParameter("estado");

			//Control de errores
			if(hora == null || hora.isBlank()) {
				response.sendRedirect(request.getContextPath() 
						+ "/admin/FormEditarCitaServlet?id="
						+ citaId
						+ "&error=Selecciona+una+hora");
				return;						
			}

			//timeStamp
			Timestamp fechaHora = Timestamp.valueOf(fecha + " " + hora + ":00");
			
			// No permitir realizada en fecha futura
			if("REALIZADA".equalsIgnoreCase(estado)

				&& fechaHora.after(
					new Timestamp(
						System.currentTimeMillis()))){

				response.sendRedirect(request.getContextPath()
					+ "/admin/FormEditarCitaServlet?id="
					+ citaId
					+ "&error=No+puedes+marcar+una+cita+futura+como+realizada");

				return;
			}

			//Objeto cita
			Cita c = new Cita();
			c.setCitaId(citaId);
			c.setFechaHora(fechaHora);
			c.setCitaTipo(tipo);
			c.setEstado(estado);

			//Actualizar cita
			boolean actualizarCita = citaServicio.modificarCita(c);

			//Redirección
			if(actualizarCita) {response.sendRedirect(request.getContextPath()
					+ "/admin/ListarCitasServlet"
					+ "?ok=Cita+actualizada");
        	
			
			}else {response.sendRedirect(request.getContextPath()
					+ "/admin/FormEditarCitaServlet?id="
					+ citaId
					+ "&error=No+se+pudo+actualizar+la+cita");}



		} catch (Exception e) {

			e.printStackTrace();
			response.sendRedirect(request.getContextPath() 
					+ "/admin/error.jsp"
					+ "?error=No+se+pudo+actualizar+la+cita");

		}	

	}

}
