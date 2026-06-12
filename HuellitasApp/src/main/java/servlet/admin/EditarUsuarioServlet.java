package servlet.admin;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import modelo.Usuario;
import servicio.UsuarioServicio;

/**
 * Servlet encargado de cargar los datos de un usuario para su edición
 * desde la zona de administración.
 */
@WebServlet("/admin/EditarUsuarioServlet")
public class EditarUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsuarioServicio servicio = new UsuarioServicio();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		try {

			int id = Integer.parseInt(request.getParameter("id"));

			Usuario editarUsuario = servicio.buscarUsuarioPorId(id);

			if (editarUsuario != null) {
				request.setAttribute("usuario", editarUsuario);
				request.getRequestDispatcher("/admin/editarUsuario.jsp")
				.forward(request, response);
				
			} else {
				response.sendRedirect(request.getContextPath() 
						+ "/admin/ListarUsuariosServlet"
						+ "?error=Usuario+no+encontrado");
			}
		

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()
					+ "/admin/ListarUsuariosServlet"
					+ "?error=No+se+pudo+editar+el+usuario");
		}
	}
}
