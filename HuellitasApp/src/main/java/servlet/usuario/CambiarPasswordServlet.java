package servlet.usuario;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Usuario;
import servicio.UsuarioServicio;

/**
 * Servlet implementation class CambiarPasswordServlet
 */
@WebServlet("/usuario/CambiarPasswordServlet")
public class CambiarPasswordServlet
extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UsuarioServicio usuarioServicio =
			new UsuarioServicio();

	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType(
				"text/html;charset=UTF-8");

		try {

			Usuario usuarioSesion = (Usuario) request.getSession()
					.getAttribute("usuario");

			if(usuarioSesion == null){

				response.sendRedirect(request.getContextPath()
							+ "/login.jsp");

				return;
			}

			String passwordActual =
					request.getParameter(
							"passwordActual");

			String passwordNueva =
					request.getParameter(
							"passwordNueva");

			String passwordNueva2 =
					request.getParameter(
							"passwordNueva2");
			
			

			if(passwordActual == null ||
					passwordNueva == null ||
					passwordNueva2 == null){

				response.sendRedirect(
						request.getContextPath()
						+ "/usuario/MisDatosServlet"
						+ "?error=Todos+los+campos+son+obligatorios");

				return;
			}

			passwordActual = passwordActual.trim();
			passwordNueva = passwordNueva.trim();
			passwordNueva2 = passwordNueva2.trim();
			
			
			if(passwordActual.isEmpty() ||
					   passwordNueva.isEmpty() ||
					   passwordNueva2.isEmpty()){

					    response.sendRedirect(
					            request.getContextPath()
					            + "/usuario/MisDatosServlet"
					            + "?error=Todos+los+campos+son+obligatorios");

					    return;
					}
			
			if(!passwordNueva.equals(
					passwordNueva2)){

				response.sendRedirect(
						request.getContextPath()
						+ "/usuario/MisDatosServlet"
						+ "?error=Las+contraseñas+no+coinciden");

				return;
			}

			if(!passwordNueva.matches(
					"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")){

				response.sendRedirect(
						request.getContextPath()
						+ "/usuario/MisDatosServlet"
						+ "?error=Minimo+8+caracteres,+letras+y+numeros");

				return;
			}

			boolean ok =
					usuarioServicio.cambiarPassword(
							usuarioSesion.getUsuarioId(),
							passwordActual,
							passwordNueva);
			if(ok){

				String mensajeOk =
			            URLEncoder.encode(
			                    "Contraseña actualizada",
			                    "UTF-8");

			    response.sendRedirect(
			            request.getContextPath()
			            + "/usuario/MisDatosServlet"
			            + "?ok=" + mensajeOk);

			}else{

			    String mensajeError =
			            URLEncoder.encode(
			                    "La contraseña actual no es correcta",
			                    "UTF-8");

			    response.sendRedirect(
			            request.getContextPath()
			            + "/usuario/MisDatosServlet"
			            + "?error=" + mensajeError);
			}

		} catch (Exception e) {

			e.printStackTrace();

			response.sendRedirect(
					request.getContextPath()
					+ "/usuario/MisDatosServlet"
					+ "?error=Error+actualizando+contraseña");
		}
	}


}

