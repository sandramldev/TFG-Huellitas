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

/**
 * 
 */
@WebServlet("/admin/CrearMascotaServlet")
public class CrearMascotaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private MascotaServicio mascotaServicio = new MascotaServicio();

	
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
		
		//Validar rol Admin
		if(!usuarioSesion.getRolUsuario().getRolNombre().equalsIgnoreCase("admin")) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		
		try {
			// Recibir datos
			int clienteId = Integer.parseInt(request.getParameter("clienteId"));
			String nombre =	request.getParameter("nombre");
			Date fechaNacimiento = 	Date.valueOf(request.getParameter("fechaNacimiento"));
			
			// Objeto Cliente
			Cliente cliente = new Cliente();
			cliente.setClienteId(clienteId);
			
			// Objeto Mascota
			Mascota mascota = new Mascota();
			mascota.setCliente(cliente);
			mascota.setNombre(nombre);
			mascota.setFechaNacimiento(fechaNacimiento);
			
			//Crear mascota
			boolean insertarMascota = mascotaServicio.crearMascota(mascota);
			
			//Redirijo después de insertar
			if(insertarMascota){

				response.sendRedirect(request.getContextPath()
						+ "/admin/ListarMascotasServlet"
						+ "?ok=Mascota+creada");

			}else{

				response.sendRedirect(request.getContextPath()
					+ "/admin/formCrearMascota.jsp"
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
