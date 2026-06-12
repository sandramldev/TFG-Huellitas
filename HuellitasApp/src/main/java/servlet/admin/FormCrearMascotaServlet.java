package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Cliente;
import modelo.Usuario;
import servicio.ClienteServicio;

/**
 * Servlet implementation class FormCrearMascotaServlet
 */
@WebServlet("/admin/FormCrearMascotaServlet")
public class FormCrearMascotaServlet extends HttpServlet { 
	private static final long serialVersionUID = 1L;
   

	private ClienteServicio clienteServicio = new ClienteServicio();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
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
			// Lista clientes
			List<Cliente> listaClientes = clienteServicio.listarClientes();

			// Enviar lista
			request.setAttribute("listaClientes",listaClientes);

			// Forward
			request.getRequestDispatcher(
					"/admin/formCrearMascota.jsp").forward(request,	response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}


