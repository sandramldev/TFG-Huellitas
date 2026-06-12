package servlet.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Mascota;
import modelo.Usuario;
import servicio.MascotaServicio;


@WebServlet("/admin/EditarMascotaServlet")

public class EditarMascotaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;       
//Objeto 
	private MascotaServicio mascotaServicio = new MascotaServicio();
	
	
	// doGet para traer datos de la bd
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//Sesion usuario
		Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuario");
		
		//Validar Sesión
		if(usuarioSesion == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		
		//Validar rol Admin
		if(!usuarioSesion.getRolUsuario().getRolNombre().equalsIgnoreCase("admin")) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		
		try {
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			Mascota mascota = mascotaServicio.buscarMascotaPorId(id);
			
			//Si existe
			if (mascota != null) {
				request.setAttribute("mascota", mascota);
				request.getRequestDispatcher("/admin/editarMascota.jsp").forward(request, response);
			
			} else {
				response.sendRedirect(request.getContextPath() 
						+ "/admin/ListarMascotasServlet"
						+ "?error=No+se+pudo+editar+la+mascota");
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() 
					+ "/admin/ListarMascotasServlet"
					+ "?error=No+se+pudo+editar+la+mascota");
		}
	}


	

}
