package servlet.admin;

import java.io.IOException;
import java.net.URLEncoder;
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

/**
 * Clase para la gestión lógica de las citas por para del administrador
 */
@WebServlet("/admin/CrearCitaServlet")
public class CrearCitaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	//Crear servicio
	
	private CitaServicio citaServicio = new CitaServicio();
	
	
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
					System.out.println(
						    request.getParameter("mascotaId"));

					
					//Recibir  datos del formulario					
					int mascotaId = Integer.parseInt(request.getParameter("mascotaId"));
					
					 String fechaStr = request.getParameter("fecha");
					 String horaStr = request.getParameter("hora");

			            String tipo =  request.getParameter("tipo");
			            
			            /*Convertir fecha datetime - local
			             *   html recibe 2026-04-01T10:30
			             *   java necesita 2026-04-01 10:30:00	
			             * */
					
			            String fechaHoraStr = fechaStr + " " + horaStr + ":00";			        
			            Timestamp fechaHora = Timestamp.valueOf(fechaHoraStr);
			            
			            
			            //Objetos: Cita -> Mascota			            
			            Mascota mascota = new Mascota();
			            mascota.setMascotaId(mascotaId);
			            
			            Cita cita = new Cita();
			            cita.setMascota(mascota);
			            cita.setFechaHora(fechaHora);
			            cita.setCitaTipo(tipo);
			            
			            //Si la cita se crea correctamente			            
			            String resultado = citaServicio.crearCita(cita);
			            System.out.println("Resultado crear cita = " + resultado);
			            
			            if("OK".equals(resultado)) {
			            	response.sendRedirect(request.getContextPath() 
			            			+ "/admin/ListarCitasServlet"
			            			+ "?ok=Cita+creada");
			            	
			            }else {
			            	response.sendRedirect(request.getContextPath() 
			            			+ "/admin/FormCrearCitaServlet"
									+ "?error="
			            			+ URLEncoder.encode(resultado, "UTF-8"));
			            } 
			   
				} catch (Exception e) {
					
					e.printStackTrace();					
					
					response.sendRedirect( request.getContextPath() 
							+ "/admin/FormCrearCitaServlet"
							 + "?error=No+se+pudo+crear+la+cita");
					
				}
	
	}

}
