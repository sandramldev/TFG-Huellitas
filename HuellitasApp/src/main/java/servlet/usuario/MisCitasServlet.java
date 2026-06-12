package servlet.usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Cita;
import modelo.Usuario;

import servicio.CitaServicio;

@WebServlet("/usuario/MisCitasServlet")

public class MisCitasServlet
	extends HttpServlet {

	private static final long
	serialVersionUID = 1L;

	// Servicio
	private CitaServicio
	citaServicio =
		new CitaServicio();

	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,
		IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// Usuario sesión
		Usuario usuarioSesion =	(Usuario)request.getSession().getAttribute("usuario");

		// Validar sesión
		if(usuarioSesion == null){
			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		try{

			// Obtener citas usuario
			List<Cita> lista = citaServicio
				.listarCitasPorUsuario(usuarioSesion.getUsuarioId());

			// Enviar lista
			request.setAttribute("listaCitas",lista);

			// Forward
			request.getRequestDispatcher("/usuario/misCitas.jsp").forward(request,response);

		}catch(SQLException e){

			response.sendRedirect(request.getContextPath()
					+ "/usuario/misCitas.jsp"
					+ "?error=La+mascota+ya+tiene+una+cita");
		}
	}
}