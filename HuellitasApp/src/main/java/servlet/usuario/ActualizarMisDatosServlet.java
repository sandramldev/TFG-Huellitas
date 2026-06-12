package servlet.usuario;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Persona;
import servicio.PersonaServicio;

/**
 * Servlet implementation class ActualizarMisDatosServlet
 */

@WebServlet("/usuario/ActualizarMisDatosServlet")
public class ActualizarMisDatosServlet
extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PersonaServicio personaServicio = new PersonaServicio();

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {

            int personaId = Integer.parseInt(request.getParameter( "personaId"));
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String telefono = request.getParameter("telefono");
            String email = request.getParameter("email");
            
            //Control de espacios vacios
            nombre = nombre.trim();
            apellidos = apellidos.trim();
            telefono = telefono.trim();
            email = email.trim();
            
         // Validación nombre
            if(nombre == null ||
            		   nombre.trim().isEmpty() ||
            		   nombre.length() > 25){

                response.sendRedirect(
                    request.getContextPath()
                    + "/usuario/MisDatosServlet"
                    + "?error=El+nombre+no+puede+superar+25+caracteres");

                return;
            }

            // Validación apellidos
            if(apellidos == null ||
            		   apellidos.trim().isEmpty() ||
            		   apellidos.length() > 50){

                response.sendRedirect(
                    request.getContextPath()
                    + "/usuario/MisDatosServlet"
                    + "?error=Los+apellidos+no+pueden+superar+50+caracteres");

                return;
            }

            // Validación teléfono
            if(!telefono.matches("\\d{9}")){

                response.sendRedirect(
                    request.getContextPath()
                    + "/usuario/MisDatosServlet"
                    + "?error=El+teléfono+debe+tener+9+dígitos");

                return;
            }

            // Validación email
            if(!email.matches(
            		"^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){

                response.sendRedirect(
                    request.getContextPath()
                    + "/usuario/MisDatosServlet"
                    + "?error=Correo+electrónico+no+válido");

                return;
            }

            Persona p = new Persona();

            p.setPersonaId(personaId);
            p.setNombre(nombre);
            p.setApellidos(apellidos);
            p.setTelefono(telefono);
            p.setEmail(email);

            boolean ok =  personaServicio.modificarPersona(p);

            if (ok) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/usuario/MisDatosServlet"
                        + "?ok=Datos+actualizados");

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/usuario/MisDatosServlet"
                        + "?error=No+se+pudieron+actualizar");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/usuario/MisDatosServlet"
                    + "?error=Error+actualizando+datos");
        }
    }
}


