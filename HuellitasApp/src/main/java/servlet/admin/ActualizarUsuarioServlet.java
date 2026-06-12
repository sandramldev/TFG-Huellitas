package servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import modelo.Usuario;
import modelo.RolUsuario;
import servicio.UsuarioServicio;

/*Servlet encargado de la gestión de los usuarios por parte del administrador.
 * Permite el listado completo de las mascotas registradas en el sistema (CRUD básico).
 * “Las rutas web se diseñaron de forma semántica, 
 * agrupando las funcionalidades administrativas bajo el prefijo /admin,
 *  lo que facilita el control de acceso y la comprensión del sistema.”*/

@WebServlet("/admin/ActualizarUsuarioServlet")
public class ActualizarUsuarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioServicio servicio = new UsuarioServicio();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		

        	try {

        	    // Datos del formulario
        	    int userId = Integer.parseInt(request.getParameter("id"));

        	    int rolId =
        	            Integer.parseInt(
        	                    request.getParameter("rolId"));

        	    String passwordNueva =
        	            request.getParameter(
        	                    "passwordNueva");

        	    String passwordNueva2 =
        	            request.getParameter(
        	                    "passwordNueva2");

        	    boolean cambiarPassword =

        	            passwordNueva != null
        	            && !passwordNueva.isBlank()
        	            && passwordNueva2 != null
        	            && !passwordNueva2.isBlank();

        	    // Construir usuario
        	    Usuario u = new Usuario();
        	    u.setUsuarioId(userId);

        	    RolUsuario r = new RolUsuario();
        	    r.setRolUsuarioId(rolId);
        	    u.setRolUsuario(r);


        	    // Por defecto la contraseña está OK
        	    boolean passwordActualizada = true;

        	    // Si se quiere cambiar contraseña
        	    if(cambiarPassword){
        	    	
        	        // Coincidencia
        	        if(!passwordNueva.equals(
        	                passwordNueva2)){

        	            response.sendRedirect(
        	                    request.getContextPath()
        	                    + "/admin/EditarUsuarioServlet?id="
        	                    + userId
        	                    + "?error=Las+contraseñas+no+coinciden");

        	            return;
        	        }

        	        // Formato
        	        if(!passwordNueva.matches(
        	                "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")){

        	            response.sendRedirect(
        	                    request.getContextPath()
        	                    + "/admin/EditarUsuarioServlet?id="
        	                    + userId
        	                    + "?error=Minimo+8+caracteres,+letras+y+numeros");

        	            return;
        	        }
        	       
        	        passwordActualizada =
        	                servicio.actualizarPasswordAdmin(
        	                        userId,
        	                        passwordNueva);
        	    }

        	    // Actualizar rol
        	    boolean actualizarUsuario =
        	            servicio.modificarUsuario(u);

        	    // Resultado final
        	    if(actualizarUsuario
        	            && passwordActualizada){

        	        response.sendRedirect(
        	                request.getContextPath()
        	                + "/admin/ListarUsuariosServlet"
        	                + "?ok=Usuario+actualizado");

        	    }else{

        	        response.sendRedirect(
        	                request.getContextPath()
        	                + "/admin/ListarUsuariosServlet"
        	                + "?error=No+se+pudo+actualizar+el+usuario");
        	    }

        	} catch (Exception e) {

        	    e.printStackTrace();

        	    response.sendRedirect(
        	            request.getContextPath()
        	            + "/admin/ListarUsuariosServlet"
        	            + "?error=No+se+pudo+actualizar+el+usuario");
        	}
              

    }
}
