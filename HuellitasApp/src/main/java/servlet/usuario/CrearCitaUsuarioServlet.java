package servlet.usuario;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Cita;
import modelo.Mascota;
import modelo.Usuario;

import servicio.CitaServicio;

@WebServlet(
		"/usuario/CrearCitaUsuarioServlet")

public class
CrearCitaUsuarioServlet
extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Servicio
	private CitaServicio citaServicio = new CitaServicio();

	protected void doPost(HttpServletRequest request,HttpServletResponse response)	throws ServletException,
					IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// Usuario sesión
		Usuario usuarioSesion =	(Usuario)request.getSession().getAttribute("usuario");

		// Validar sesión
		if(usuarioSesion == null){

			response.sendRedirect(request.getContextPath()
					+ "/login.jsp");

			return;
		}

		try{

			// Leer datos
			int mascotaId = Integer.parseInt(request.getParameter("mascotaId"));
			String tipo = request.getParameter(	"tipo");
			String fecha = request.getParameter("fecha");
			String hora = request.getParameter("hora");
		
			Timestamp fechaHora = Timestamp.valueOf(fecha + " "	+ hora + ":00");

			// Objetos
			Mascota m = new Mascota();

			m.setMascotaId(	mascotaId);

			Cita c = new Cita();

			c.setMascota(m);
			c.setCitaTipo(tipo);
			c.setFechaHora(fechaHora);

			// Servicio
			String resultado = 	citaServicio.crearCita(c);

			// Redirect
			if(resultado.equalsIgnoreCase("OK")){

				response.sendRedirect(request.getContextPath()
						+ "/usuario/MisCitasServlet"
						+ "?ok=Cita+creada");

			}else{

				response.sendRedirect(request.getContextPath()
						+ "/usuario/FormCrearCitaUsuarioServlet"
						+ "?error="
						+ resultado.replace(
							" ",
							"+"));
			}

		}catch(Exception e){

			e.printStackTrace();

			response.sendRedirect(request.getContextPath()
					+ "/admin/error.jsp"
					+ "?error=Cita+usuario+no+creada");
		}
	}
}