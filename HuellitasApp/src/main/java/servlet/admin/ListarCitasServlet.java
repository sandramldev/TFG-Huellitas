package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Cita;
import modelo.Usuario;
import servicio.CitaServicio;

/**
 *ListarCitas: Clase para trae todas las citas de la agenda desde admin
 * Primero listar citas, después editar, eliminar, etc
 */

@WebServlet("/admin/ListarCitasServlet")
public class ListarCitasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//Objeto CitaServivicio para  listar citas
	private CitaServicio citaServicio = new CitaServicio();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
	
		
		//Validar sesion usuario
		Usuario usuarioSesion =
			    (Usuario) request.getSession().getAttribute("usuario");
		
		
		if(usuarioSesion == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		//Validar rol Admin
		if(!usuarioSesion.getRolUsuario().getRolNombre().equalsIgnoreCase("admin")) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		
	
		
		//Cargar lista citas
		try {
			
			 List<Cita> lista = citaServicio.listarCita(); 	
			 
			 request.setAttribute("listaCitas", lista);
			 
			 request.getRequestDispatcher("/admin/listarCitas.jsp").forward(request, response);
			 
			
		} catch (Exception e) {
			
			e.printStackTrace();
			response.sendRedirect("/admin/error.jsp"
					+ "?error=No+se+pudo+editar+la+cita");
						
		}
		
		
		
	}



}
