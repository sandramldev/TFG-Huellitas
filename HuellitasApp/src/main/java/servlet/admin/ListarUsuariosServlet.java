package servlet.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import modelo.Usuario;
import servicio.UsuarioServicio;

/**
 * Servlet encargado de mostrar el listado completo de usuarios
 * registrados en el sistema desde la zona de administración.
 * El acceso está restringido a usuarios con rol administrador.
 */

@WebServlet("/admin/ListarUsuariosServlet")
public class ListarUsuariosServlet extends HttpServlet {

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
    	
    	//Listar  usuarios
        try {

            List<Usuario> lista = servicio.listarUsuarios();

            request.setAttribute("listaUsuarios", lista);
            request.getRequestDispatcher("/admin/usuarios.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() 
            		+ "/index.jsp"
            		+ "?error=No+se+pudo+listar+usuarios");
        }
    }
}


