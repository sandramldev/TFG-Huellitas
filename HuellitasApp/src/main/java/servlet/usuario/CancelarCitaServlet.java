package servlet.usuario;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Usuario;

import servicio.CitaServicio;

@WebServlet("/usuario/CancelarCitaServlet")

public class CancelarCitaServlet extends HttpServlet {

	private static final long	serialVersionUID = 1L;

	// Servicio
	private CitaServicio citaServicio =	new CitaServicio();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException,
		IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// Usuario sesión
		Usuario usuarioSesion =	(Usuario) request.getSession().getAttribute("usuario");

		// Validar sesión
		if(usuarioSesion == null){

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		try{

			// Leer id cita
			int citaId = Integer.parseInt(request.getParameter("id"));

			// Cancelar
			boolean ok = citaServicio.cancelarCitaUsuario(citaId, usuarioSesion.getUsuarioId());

			// Redirect
			if(ok){

				response.sendRedirect(request.getContextPath() 
						+ "/usuario/MisCitasServlet"
						+ "?ok=Cita+cancelada");

			}else{

				response.sendRedirect(request.getContextPath()
					+ "/usuario/MisCitasServlet"
					+ "?error=No+se+pudo+cancelar");
			}

		}catch(Exception e){

			e.printStackTrace();

			response.sendRedirect(request.getContextPath()
				+ "/admin/error.jsp");
		}
	}
}