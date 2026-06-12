package servlet.usuario;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Usuario;
import servicio.UsuarioServicio;

/**
 * Servlet implementation class MisDatosServlet
 */
@WebServlet("/usuario/MisDatosServlet")
public class MisDatosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UsuarioServicio usuarioServicio =  new UsuarioServicio();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Usuario usuarioSesion =
				(Usuario) request.getSession()
				.getAttribute("usuario");

		if (usuarioSesion == null) {

			response.sendRedirect(
					request.getContextPath()
					+ "/login.jsp");

			return;
		}

		try {

			Usuario usuario =  usuarioServicio.buscarUsuarioPorId(
					usuarioSesion.getUsuarioId());

			request.setAttribute("usuario",	usuario);
			request.getRequestDispatcher("/usuario/misDatos.jsp")
			.forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();

			response.sendRedirect(
					request.getContextPath()
					+ "/index.jsp"
					+ "?error=No+se+pudieron+cargar+los+datos");
		}
	}
}

