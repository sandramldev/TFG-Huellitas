package servlet.usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Mascota;
import modelo.Usuario;
import servicio.CitaServicio;
import servicio.MascotaServicio;

@WebServlet(
"/usuario/FormCrearCitaUsuarioServlet")

public class
FormCrearCitaUsuarioServlet extends HttpServlet {  
	private static final long serialVersionUID = 1L;

	// Servicio
	private MascotaServicio mascotaServicio =	new MascotaServicio();
	private CitaServicio citaServicio = new CitaServicio();
	

	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,
		IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// Usuario sesión
		Usuario usuarioSesion =	(Usuario) request.getSession().getAttribute("usuario");

		// Validar sesión
		if(usuarioSesion == null){

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		try{

			// Mascotas usuario
			List<Mascota> mascotas = mascotaServicio.listarMascotasPorUsuario(
					usuarioSesion.getUsuarioId());

			// Enviar lista
			request.setAttribute("mascotas", mascotas);
			
			// Leer fecha seleccionada
			String fecha = 	request.getParameter("fecha");

			// Si existe fecha
			if(fecha != null
				&& !fecha.isBlank()){

				LocalDate dia = LocalDate.parse(fecha);

				List<LocalDateTime> horasDisponibles = 	citaServicio.obtenerHorasDisponibles(dia);

				request.setAttribute("horasDisponibles", horasDisponibles);
			}
			
			// Forward
			request.getRequestDispatcher("/usuario/pedirCita.jsp").forward(request, response);

		}catch(SQLException e){

			e.printStackTrace();

			response.sendRedirect(

				request.getContextPath()
				+ "/admin/error.jsp");
		}
	}
}