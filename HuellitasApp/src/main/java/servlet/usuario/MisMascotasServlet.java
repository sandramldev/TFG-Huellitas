package servlet.usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Mascota;
import modelo.Usuario;

import servicio.MascotaServicio;

@WebServlet("/usuario/MisMascotasServlet")

public class MisMascotasServlet
extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Servicio
	private MascotaServicio mascotaServicio = new MascotaServicio();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException,
					IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// Usuario sesión
		Usuario usuarioSesion = (Usuario)	request.getSession().getAttribute("usuario");

		// Validar sesión
		if(usuarioSesion == null){

			response.sendRedirect(request.getContextPath()
					+ "/login.jsp");
			return;
		}

		try{

			// Obtener mascotas usuario
			List<Mascota> lista = mascotaServicio.listarMascotasPorUsuario(usuarioSesion.getUsuarioId());

			// Enviar lista
			request.setAttribute("listaMascotas",	lista);

			// Forward
			request.getRequestDispatcher("/usuario/misMascotas.jsp")
			.forward(request,response);

		}catch(SQLException e){

			e.printStackTrace();

			response.sendRedirect(

					request.getContextPath()
					+ "/admin/error.jsp"
					+ "?error=Mascota+usuario+no+se+puede+editar");
		}
	}
}
