package servicio;

import java.sql.SQLException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.PasswordSecurity;

public class UsuarioServicio {

	//UsuarioDAO -> UsuarioServicio para conectar con la base de datos sin sql
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private PersonaServicio personaServicio = new PersonaServicio();
	/**
	 * Crea un nuevo usuario en la base de datos.	
	 * Devuelve true si se ha insertado correctamente.
	 */
	public boolean crearUsuario(Usuario u) throws SQLException {

		// Obtener password del formulario
	    String passwordPlano = u.getPassword();

	    // Generar hash
	    String passwordHash = PasswordSecurity.hashPassword(passwordPlano);

	    // Sustituir el password del formulario por el hash
	    u.setPassword(passwordHash);
	    
	    return usuarioDAO.insertarUsuario(u);
	}



	// Obtener lista con todos los usuarios de la BD.     
	public List<Usuario> listarUsuarios() throws SQLException {
		return usuarioDAO.listarUsuarios();
	}


	// Buscar usuario por su ID.     
	public Usuario buscarUsuarioPorId(int id) throws SQLException {
	    return usuarioDAO.obtenerUsuarioPorId(id);
	}



	 // Modifica un usuario existente.	
	public boolean modificarUsuario(Usuario u) throws SQLException {

		//Si existe -> Modifica el usuario
		return usuarioDAO.actualizarUsuario(u);
	}

	//
	
	// Borra un usuario por su ID.     
	public boolean borrarUsuario(int id)
			throws SQLException {

		Usuario usuarioBD =

			usuarioDAO
			.obtenerUsuarioPorId(id);

		// Si no existe
		if(usuarioBD == null){

			return false;
		}

		// Recuperar persona
		int personaId = usuarioBD.getPersona().getPersonaId();

		// Borrar usuario
		boolean usuarioBorrado = usuarioDAO.eliminarUsuario(id);

		// Si usuario eliminado - borrar persoa
		if(usuarioBorrado){

			return personaServicio.borrarPersona(personaId);
		}

		return false;
	}
	
	public Usuario login(String email, String password) throws SQLException {
	    return usuarioDAO.login(email, password);
	}
	
	/*Cambiar contraseña por el usuario*/
	public boolean cambiarPassword(
	        int usuarioId,
	        String passwordActual,
	        String passwordNueva)
	        throws SQLException {

	    String hashActual =
	            usuarioDAO.obtenerPasswordPorUsuarioId(
	                    usuarioId);

	    if (hashActual == null) {
	        return false;
	    }

	    if (!BCrypt.checkpw(
	            passwordActual,
	            hashActual)) {

	        return false;
	    }

	    String nuevoHash =
	            BCrypt.hashpw(
	                    passwordNueva,
	                    BCrypt.gensalt());

	    return usuarioDAO.actualizarPassword(
	            usuarioId,
	            nuevoHash);
	}
	
	/*Actualizar passwd por el admin*/
	public boolean actualizarPasswordAdmin(
	        int usuarioId,
	        String passwordNueva)
	        throws SQLException {

	    String passwordHash =
	            PasswordSecurity.hashPassword(
	                    passwordNueva);
	    System.out.println(
	    	    "ACTUALIZAR PASSWORD ADMIN");
	    System.out.println(
	    	    "Hash generado = " + passwordHash);

	    return usuarioDAO.actualizarPassword(
	            usuarioId,
	            passwordHash);
	}

}


