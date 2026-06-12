package servicio;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.CitaDAO;
import dao.MascotaDAO;
import modelo.Mascota;

public class MascotaServicio {
	
	
	//MascotaDAO -> UsuarioServicio para conectar con la base de datos sin sql
		private MascotaDAO  mascotaDAO = new MascotaDAO();
		private CitaDAO citaDAO = new CitaDAO();

	
		public boolean crearMascota(Mascota m) throws SQLException {
			// Control null
			if(m == null)
				return false;

			// Fecha futura no permitida
			if(m.getFechaNacimiento().after(

						new Date(System.currentTimeMillis())

					)){

				return false;
			}
			
		    return mascotaDAO.insertarMascota(m);
		}

		/**
		 * Devuelve el listado completo de mascotas del sistema.
		 * Llama al DAO para recuperar los datos desde la base de datos.
		 */
		public List<Mascota>listarMascota() throws SQLException{
			return mascotaDAO.listarMascota();
		}

		//Buscar Mascota por ID
		public Mascota buscarMascotaPorId(int mId) throws SQLException {
			return mascotaDAO.obtenerMascotaPorId(mId);		
		}

		//Buscar mascota por usuario
		public List<Mascota>listarMascotasPorUsuario(int usuarioId)	throws SQLException {

			return mascotaDAO.listarMascotasPorUsuario(usuarioId);
		}
		//Modificar por id
		public boolean modificarMascotaPorId(Mascota m) throws SQLException {
		    Mascota mascotaBD = mascotaDAO.obtenerMascotaPorId(m.getMascotaId());
		    if (mascotaBD == null) return false;
		    return mascotaDAO.actualizarMascota(m);
		}


		
		//Borrar mascota por ID		
		public boolean borrarMascota(int pId) throws SQLException {			
			Mascota MascotaBD = mascotaDAO.obtenerMascotaPorId(pId);
			
			//Si no existe -> No borra
			if(MascotaBD == null) return false;
			
			// Borrar citas asociadas
			citaDAO.eliminarCitasPorMascota(pId);
			
			//Si existe -> Borra
			return mascotaDAO.eliminarMascota(pId);
		}

}
