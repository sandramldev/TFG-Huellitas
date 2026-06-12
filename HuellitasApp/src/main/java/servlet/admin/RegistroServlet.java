package servlet.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Cliente;
import modelo.Persona;
import modelo.RolUsuario;
import modelo.Usuario;
import servicio.ClienteServicio;
import servicio.PersonaServicio;
import servicio.UsuarioServicio;

/**
 * Servlet encargado del registro de usuarios desde la zona de administración.
 * Permite al administrador crear nuevos usuarios en el sistema,
 * aplicando control de sesión y validación de permisos.
 *
 * La ruta se define de forma semántica bajo el prefijo /admin para
 * diferenciar claramente las funcionalidades administrativas del
 * resto de accesos de usuario.
 */


@WebServlet("/admin/RegistroServlet")
public class RegistroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioServicio usuarioServicio = new UsuarioServicio();
	private PersonaServicio personaServicio = new PersonaServicio();
	private ClienteServicio clienteServicio = new ClienteServicio();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		try {						

			String nombre = request.getParameter("nombre");
			String apellidos = request.getParameter("apellidos");
			String email = request.getParameter("email");
			String telefono = request.getParameter("telefono");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");

			System.out.println("Nombre recibido: " + nombre);
			System.out.println("Apellidos recibidos: " + apellidos);

			boolean hayErrores = false;
			
			// Validación contraseña
			if (password == null || password2 == null || !password.equals(password2)) {

			    request.setAttribute(
			        "errorPassword",
			        "Las contraseñas no coinciden");

			    hayErrores = true;

			} else if (!password.matches(
			        "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {

			    request.setAttribute(
			        "errorPassword",
			        "Debe tener al menos 8 caracteres, letras y números");

			    hayErrores = true;
			}
			

			//Validación email			
			if(!email.matches(
					"^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
				
				request.setAttribute(
					    "errorEmail",
					    "Correo electrónico no válido"
					);
				hayErrores = true;
			
			}
			
			//Validación teléfono
			if(!telefono.matches("\\d{9}")){
				request.setAttribute(
					    "errorTelefono",
					    "Debe contener 9 dígitos"
					);

			    hayErrores = true;
			
			}
			
			//Si hay errores volcel al registro
			if (hayErrores) {
				request.setAttribute("nombre", nombre);
				request.setAttribute("apellidos", apellidos);
				request.setAttribute("telefono", telefono);
				request.setAttribute("email", email);
				
			    request.getRequestDispatcher("/registro.jsp")
			           .forward(request, response);

			    return;
			}
			
			//Crear persona
			Persona p = new Persona();
			p.setNombre(nombre);
			p.setApellidos(apellidos);
			p.setEmail(email);
			p.setTelefono(telefono);

			
			//Crear usuario
			Usuario u = new Usuario();
			
			int personaId = personaServicio.crearPersonaYDevolverId(p);

			if(personaId == -1){
				response.sendRedirect(request.getContextPath() + "/registro.jsp");
				return;
			}

			p.setPersonaId(personaId);
			u.setPersona(p);



			// Rol por defecto 
			RolUsuario r = new RolUsuario();
			r.setRolUsuarioId(2);
			u.setRolUsuario(r);
			u.setPassword(password);
			
			System.out.println("ROL ASIGNADO = "
					+ r.getRolUsuarioId());

			boolean creado = usuarioServicio.crearUsuario(u);
			
			
			// Crear cliente
			Cliente cliente = new Cliente();

			cliente.setPersona(p);

			boolean clienteCreado = clienteServicio.crearCliente(cliente);

			System.out.println("CLIENTE CREADO = "
					+ clienteCreado);
			

			if(creado && clienteCreado){
				response.sendRedirect(request.getContextPath() 
						+ "/login.jsp"
						+ "?ok=Usuario+registrado");

			}else{
				response.sendRedirect(request.getContextPath() 
						+ "/registro.jsp"
						+ "?error=No+se+pudo+completar+el+registro");

			}

			//Control de erros SQL
		} catch (SQLException e) {

			e.printStackTrace();

			if(e.getMessage().contains("ORA-00001")) {

				response.sendRedirect(request.getContextPath()
						+ "/registro.jsp"
						+ "?error=Usuario+ya+existe");

				return;
			}

			response.sendRedirect(
					request.getContextPath()
					+ "/admin/error.jsp"
					+ "?error=Error+durante+el+registro");
		}
	}
}
