package servlet.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Usuario;
import servicio.MascotaServicio;


@WebServlet("/admin/BorrarMascotaServlet")

public class BorrarMascotaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	//Objeto mascota servicio

	MascotaServicio mascotaServicio = new MascotaServicio();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

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

			int id = Integer.parseInt(request.getParameter("id"));

			//Borrar mascota	
			boolean borrarMascota = mascotaServicio.borrarMascota(id);
			
			//Redirijo después de borrar
			if (borrarMascota) {
				response.sendRedirect(request.getContextPath()
						+ "/admin/ListarMascotasServlet"
						+ "?ok=Mascota+borrada");
			} else {
				response.sendRedirect(request.getContextPath()
						+ "/admin/ListarMascotasServlet"
						+ "?error=Mascota+no+encontrada");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()
					+ "/admin/ListarMascotasServlet"
					+ "?error=No+se+pudo+borrar+la+mascota");
		}
	}

}
