package servlet.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Usuario;
import servicio.CitaServicio;

/**
 * Servlet implementation class EliminarCitaServlet
 */
@WebServlet("/admin/EliminarCitaServlet")
public class EliminarCitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	//Servicio	
	private CitaServicio citaServicio = new CitaServicio();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");


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

			//Llamar servicio
			boolean eliminarCita = citaServicio.eliminarCitaAdmin(citaId);

			//Redirección
			if(eliminarCita) {response.sendRedirect(request.getContextPath()
					+ "/admin/ListarCitasServlet"
					+ "?ok=Cita+eliminada");
			
			}else {response.sendRedirect(request.getContextPath()
					+ "/admin/ListarCitasServlet"
					+ "?error=Cita+no+encontrada");}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() 
					+ "/admin/error.jsp"
					+ "?error=No+se+pudo+eliminar+la+cita");
		}
	}

}
