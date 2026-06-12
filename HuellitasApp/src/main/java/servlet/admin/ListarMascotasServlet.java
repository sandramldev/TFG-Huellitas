package servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import modelo.Mascota;
import modelo.Usuario;
import servicio.MascotaServicio;

/**
 * Servlet encargado de la gestión de mascotas por parte del administrador.
 * Permite el listado completo de las mascotas registradas en el sistema (CRUD básico).
 * “Las rutas web se diseñaron de forma semántica, 
 * agrupando las funcionalidades administrativas bajo el prefijo /admin,
 *  lo que facilita el control de acceso y la comprensión del sistema.”*/
 


@WebServlet("/admin/ListarMascotasServlet")

public class ListarMascotasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Objeto MascotaServicio para listar mascotas
	private MascotaServicio  mascotaServicio = new MascotaServicio();


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


		//Listar mascotas
		try{

			List<Mascota> listaMascota = mascotaServicio.listarMascota();
			request.setAttribute("listaMascotas", listaMascota);
			
			//forward
			request.getRequestDispatcher("/admin/mascota.jsp").forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() 
					+ "/index.jsp"
					+ "?error=No+se+pudo+editar+mascota");
		}
	}

}
