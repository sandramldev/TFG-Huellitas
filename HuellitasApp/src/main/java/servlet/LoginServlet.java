package servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Usuario;
import servicio.UsuarioServicio;

/*LoginServlet
Gestiona el proceso de autenticación del usuario.
Recibe el email como campo único y password del formulario.
Llama al servicio UsuarioServicio.login().
Si los datos son correctos: Guarda el usuario en sesión
Si los datos son incorrectos redirige a login.jsp*/

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    
    private UsuarioServicio servicio = new UsuarioServicio();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

        // Datos del formulario
        String email = request.getParameter("email");
        String password = request.getParameter("password");
    

        try {

            //Llamar al servicio
            Usuario u = servicio.login(email, password);

            //Comprobar resultado
            if (u != null) {

                // Guardar usuario en sesión
                request.getSession().setAttribute("usuario", u);
                
                // Login correcto → ir al index
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            } else {

                // Login incorrecto → volver al login
            	String error = URLEncoder.encode("Usuario o contraseña incorrectos", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/login.jsp?error=" + error);

            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Error+al+iniciar+sesión");
        }
    }
}
