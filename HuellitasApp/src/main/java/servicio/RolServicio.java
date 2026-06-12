package servicio;

import java.sql.SQLException;

import java.util.List;

import modelo.RolUsuario;
import dao.RolDAO;



public class RolServicio {

	//RolDAO -> RolServicio para conectar con la base de datos sin sql
	private RolDAO rolDAO = new RolDAO();

	/**
	 * Crea un nuevo RolUsuario en la base de datos.
	 * Recibe un objeto RolUsuario, devuelve true si se ha insertado correctamente.
	 */

	/*public boolean crearRolUsuario(RolUsuario r) throws SQLException {

		return rolDAO.insertarRol(r);
	}*/

	public boolean crearRolUsuario(RolUsuario r) throws SQLException {

		RolUsuario rolBD = rolDAO.obtenerRolPorNombre(r.getRolNombre());

		if (rolBD != null) {
			return false;
		}

		return rolDAO.insertarRol(r);
	}


	//Obtener una lista con todos los roles de la BD
	public List<RolUsuario> listarRolUsuario() throws SQLException {

		return rolDAO.listarRoles();
	}

	//Buscar Rol por ID
	public RolUsuario bucarRolUsuario(int rolId) throws SQLException {

		return rolDAO.obtenerRolPorId(rolId);
	}

	//Modificar un Rol existente	
	public boolean modificarRolUsuario(RolUsuario r) throws SQLException {

		//Busco el rol actual en DB
		RolUsuario rolBD = rolDAO.obtenerRolPorId(r.getRolUsuarioId());

		//Si el rol no existe _ No modifica
		if(rolBD == null) return false;

		//Comparo rol_nombre actual con el nuevo, antes de modificar		
		String nombreActual = rolBD.getRolNombre();
		String nombreNuevo = r.getRolNombre();

		//Si el nombre es el mismo no modifico
		if(nombreActual.equalsIgnoreCase(nombreNuevo)) return false;

		// Compruebo que no exista OTRO rol con ese nombre
		RolUsuario rolConEseNombre = rolDAO.obtenerRolPorNombre(nombreNuevo);

		if (rolConEseNombre != null && 
				rolConEseNombre.getRolUsuarioId() != r.getRolUsuarioId()) {
			return false; // ya existe otro rol con ese nombre
		}

		// Si pasa los controles → actualizo
		return rolDAO.actualizarRolUsuario(r);

	}

	/*Borrar ROL.
	 * * NOTA: Si se elimina un rol, afecta a todos 
	 * los usuarios de la empresa que estén asociados al mismo.
	 * Anular desde UsuarioServicio.
	 **/

	public boolean borrarRolUsuarioPorId(int rolId) throws SQLException {

		RolUsuario rolBD = rolDAO.obtenerRolPorId(rolId);

		//Si no existe -> No puedo borrar
		if (rolBD == null) {
			return false;
		}

		//Si existe -> borro el rol por id
		return rolDAO.eliminarRol(rolId);
	}


}
