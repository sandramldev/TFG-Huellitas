package servlet.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import modelo.Usuario;
import servicio.UsuarioServicio;

@WebServlet("/admin/BorrarUsuarioServlet")
public class BorrarUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UsuarioServicio servicio = new UsuarioServicio();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuario");

		//  Validar sesión
		if (usuarioSesion == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		// Validar rol ADMIN
		if (!usuarioSesion.getRolUsuario().getRolNombre()
				.equalsIgnoreCase("admin")) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}

		try {

			int id = Integer.parseInt(request.getParameter("id"));

			boolean borrarUsuario = servicio.borrarUsuario(id);
			//Redirijo después de borrar
			if (borrarUsuario) {
				response.sendRedirect(request.getContextPath()
						+ "/admin/ListarUsuariosServlet"
						+ "?ok=Usuario+borrado");
			} else {
				response.sendRedirect(request.getContextPath()
						+ "/admin/ListarUsuariosServlet"
						+ "?error=No+se+pudo+borrar+el+usuario");
			}

			

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() 
					+ "/admin/ListarUsuariosServlet"
					+ "?error=No+se+pudo+borrar+el+usuario");
		}
	}
}
