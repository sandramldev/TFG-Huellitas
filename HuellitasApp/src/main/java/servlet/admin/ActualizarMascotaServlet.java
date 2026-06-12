package servlet.admin;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Cliente;
import modelo.Mascota;
import modelo.Usuario;
import servicio.MascotaServicio;

/*
 * Este servlet recibe los datos enviados mediante método POST
 * desde el formulario de edición (editarMascota.jsp), construye
 * un objeto Mascota completo —incluyendo su relación con Cliente—
 * y delega la operación de actualización al servicio correspondiente.
 *
 * Se valida previamente:
 *  - La existencia de sesión activa.
 *  - Que el usuario tenga rol ADMIN.
 *
 * Tras la actualización, se redirige al listado de mascotas
 * para reflejar los cambios realizados.
 * */
@WebServlet("/admin/ActualizarMascotaServlet")

public class ActualizarMascotaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//Objeto
	
	MascotaServicio mascotaServicio = new MascotaServicio();
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
	
		//Objeto
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
			
			//Datos formulario
			int id = Integer.parseInt(request.getParameter("id"));
			int clienteId = Integer.parseInt(request.getParameter("clienteId"));
			String nombre = request.getParameter("nombre");
			Date fechaNacimiento = Date.valueOf(request.getParameter("fechaNacimiento"));
			String estado = request.getParameter("estado");
			
			
			//Objetos
			Mascota m = new Mascota();
			m.setMascotaId(id);
			m.setNombre(nombre);
			m.setFechaNacimiento(fechaNacimiento);
			
			Cliente c = new Cliente();
			c.setClienteId(clienteId);
			m.setCliente(c);
			
			//Estado de la mascota viva/fallecida	
			if ("fallecida".equals(estado)) {
			    m.setFechaFallecimiento(new Date(System.currentTimeMillis()));
			
			
			} else {
			    m.setFechaFallecimiento(null);
			}		
								
			//Actualizo mascota	
			boolean actualizarMascota = mascotaServicio.modificarMascotaPorId(m);

			//Redirijo después de actualizar
				if(actualizarMascota){					
					response.sendRedirect(request.getContextPath()
						+ "/admin/ListarMascotasServlet"
						+ "?ok=Mascota+actualizada");

				}else{
					response.sendRedirect(request.getContextPath()
						+ "/admin/ListarMascotasServlet"
						+ "?error=No+se+pudo+actualizar+la+mascota");
				}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() 
					+ "/admin/ListarMascotasServlet"
					+ "?error=No+se+pudo+actualizar+la+mascota");
			
		}
	}

}
