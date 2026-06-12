package servlet.usuario;

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
import servicio.ClienteServicio;
import servicio.MascotaServicio;

/**
 * 
 */
@WebServlet("/usuario/CrearMascotaUsuarioServlet")
public class CrearMascotaUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
private MascotaServicio mascotaServicio = new MascotaServicio();
private ClienteServicio clienteServicio = new ClienteServicio();

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		
		//Validar sesion usuario
		Usuario usuarioSesion =
			    (Usuario) request.getSession().getAttribute("usuario");
		
		
		if(usuarioSesion == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		//Validar rol usuario
		if(!usuarioSesion.getRolUsuario().getRolNombre().equalsIgnoreCase("usuario")) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		
		try {
			// Obtener cliente asociado al usuario
			Cliente cliente =  clienteServicio.buscarClientePorPersonaId(
				        usuarioSesion.getPersona().getPersonaId());
			
			//Recibir datos
			String nombre =	request.getParameter("nombre");
			Date fechaNacimiento = 	Date.valueOf(request.getParameter("fechaNacimiento"));
		
			
			//Crear Mascota
			Mascota mascota = new Mascota();
			mascota.setCliente(cliente);
			mascota.setNombre(nombre);
			mascota.setFechaNacimiento(fechaNacimiento);
			
			//Crear mascota
			boolean insertarMascota = mascotaServicio.crearMascota(mascota);
			
			//Redirijo después de insertar
			if(insertarMascota){

				response.sendRedirect(request.getContextPath()
						+ "/usuario/MisMascotasServlet"
						+ "?ok=Mascota+creada");

			}else{

				response.sendRedirect(request.getContextPath()
						 + "/usuario/formCrearMascota.jsp"
					+ "?error=No+se+pudo+crear+la+mascota");
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();

			response.sendRedirect(request.getContextPath()
				+ "/admin/formCrearMascota.jsp"
				+ "?error=No+se+pudo+crear+la+mascota");

		}
		
	}


}
