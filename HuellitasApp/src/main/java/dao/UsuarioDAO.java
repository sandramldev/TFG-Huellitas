package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

import modelo.Persona;
import modelo.RolUsuario;
import modelo.Usuario;
import util.ConexionOracle;

public class UsuarioDAO {

	/*
	 * DAO encargado del acceso y gestión de datos
	 * relacionados con el objeto Usuario.
	 *
	 * Incluye operaciones CRUD y validaciones básicas
	 * de control nulo cuando son necesarias.
	 *
	 * Esta clase depende de las entidades Persona
	 * (datos comunes del usuario) y RolUsuario
	 * (gestión de roles y permisos).
	 *
	 * Autor: Sandra Marín
	 * Proyecto: https://www.huellitas.info/index.jsp (2026)
	 */



	//Método para insertar usuarios	
	public boolean insertarUsuario(Usuario usuario) throws SQLException {

		String sql = "INSERT INTO usuarios (persona_id, rol_usuario_id, password) "
				+ "VALUES (?, ?, ?)";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			ps.setInt(1, usuario.getPersona().getPersonaId());		
			ps.setInt(2, usuario.getRolUsuario().getRolUsuarioId());			
			ps.setString(3, usuario.getPassword());
			
			
			int rows = ps.executeUpdate();

			return rows == 1;
		}
	}



	//Método para listar usuarios
	public List<Usuario> listarUsuarios() throws SQLException {

		List<Usuario> lista = new ArrayList<>();

		String sql = "SELECT u.usuario_id, "
				+ "p.persona_id, p.nombre, p.apellidos, p.telefono, p.email, "
				+ "r.rol_usuario_id, r.rol_nombre "
				+ "FROM usuarios u "
				+ "JOIN personas p ON u.persona_id = p.persona_id "
				+ "JOIN roles_usuarios r ON u.rol_usuario_id = r.rol_usuario_id";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()
				) {

			while (rs.next()) {

				// Objetos
				Usuario u = new Usuario();
				Persona p = new Persona();
				RolUsuario r = new RolUsuario();

				// Persona
				p.setPersonaId(rs.getInt("persona_id"));
				p.setNombre(rs.getString("nombre"));
				p.setApellidos(rs.getString("apellidos"));
				p.setTelefono(rs.getString("telefono"));
				p.setEmail(rs.getString("email"));

				// Rol
				r.setRolUsuarioId(rs.getInt("rol_usuario_id"));
				r.setRolNombre(rs.getString("rol_nombre"));

				// Usuario
				u.setUsuarioId(rs.getInt("usuario_id"));
				u.setPersona(p);
				u.setRolUsuario(r);

				lista.add(u);
			}
		}

		return lista;
	}

	
	// Buscar usuario por usuario_id
	public Usuario obtenerUsuarioPorId(int usuarioId) throws SQLException {

	    Usuario u = null;

	    String sql =
	        "SELECT u.usuario_id, " +
	        "p.persona_id, p.nombre, p.apellidos, p.telefono, p.email, " +
	        "r.rol_usuario_id, r.rol_nombre " +
	        "FROM usuarios u " +
	        "JOIN personas p ON u.persona_id = p.persona_id " +
	        "JOIN roles_usuarios r ON u.rol_usuario_id = r.rol_usuario_id " +
	        "WHERE u.usuario_id = ?";

	    try (Connection con = ConexionOracle.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, usuarioId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            Persona p = new Persona();
	            RolUsuario r = new RolUsuario();
	            u = new Usuario();

	            p.setPersonaId(rs.getInt("persona_id"));
	            p.setNombre(rs.getString("nombre"));
	            p.setApellidos(rs.getString("apellidos"));
	            p.setTelefono(rs.getString("telefono"));
	            p.setEmail(rs.getString("email"));

	            r.setRolUsuarioId(rs.getInt("rol_usuario_id"));
	            r.setRolNombre(rs.getString("rol_nombre"));

	            u.setUsuarioId(rs.getInt("usuario_id"));
	            u.setPersona(p);
	            u.setRolUsuario(r);
	        }
	    }

	    return u;
	}


	//Método para modificar un usuario

	public boolean actualizarUsuario(Usuario usuario) throws SQLException {

		//Query
		String sql = "UPDATE usuarios SET rol_usuario_id=? WHERE usuario_id=?";

		//Conexión BD
		try
		(Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				){

			//Preparar y ejecutar sql
			ps.setInt(1, usuario.getRolUsuario().getRolUsuarioId());
	        ps.setInt(2, usuario.getUsuarioId());

			return ps.executeUpdate() ==1;
		}

	}

	// Método para eliminar una usuario existente en la BD
	public boolean eliminarUsuario(int usuarioId) throws SQLException {

		//Query
		String sql = "DELETE FROM usuarios WHERE usuario_id = ?";

		//Conexión BD
		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Preparar y ejecutar sql	
			ps.setInt(1, usuarioId);		 
			return ps.executeUpdate() == 1;

		}	    
	}

	
	// Login por email y password (campo único email para el registro)
	public Usuario login(String email, String password) throws SQLException {

		//Sin espacios vacíos, con mayúsculas
	    email = email.trim().toLowerCase();
	    password = password.trim();

	    String sql = "SELECT u.usuario_id, u.password, " +
	                 "p.persona_id, p.nombre, p.apellidos, p.email, " +
	                 "r.rol_usuario_id, r.rol_nombre " +
	                 "FROM usuarios u " +
	                 "JOIN personas p ON u.persona_id = p.persona_id " +
	                 "JOIN roles_usuarios r ON u.rol_usuario_id = r.rol_usuario_id " +
	                 "WHERE LOWER(p.email) = ?";

	    try (Connection con = ConexionOracle.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	    	//Campo único
	        ps.setString(1, email);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {

	                String passwordHash = rs.getString("password");

	                // Comprobación datos encriptados
	                if (!BCrypt.checkpw(password, passwordHash)) {
	                    return null;
	                }

	                Usuario u = new Usuario();
	                Persona p = new Persona();
	                RolUsuario r = new RolUsuario();

	                u.setUsuarioId(rs.getInt("usuario_id"));
	                u.setPassword(passwordHash);

	                p.setPersonaId(rs.getInt("persona_id"));
	                p.setNombre(rs.getString("nombre"));
	                p.setApellidos(rs.getString("apellidos"));
	                p.setEmail(rs.getString("email"));

	                r.setRolUsuarioId(rs.getInt("rol_usuario_id"));
	                r.setRolNombre(rs.getString("rol_nombre"));

	                u.setPersona(p);
	                u.setRolUsuario(r);

	                return u;
	            }
	        }
	    }
	    return null;
	}

	
	/*Obtener el hash actual*/
	public String obtenerPasswordPorUsuarioId(int usuarioId)
	        throws SQLException {

	    String sql =
	            "SELECT password "
	          + "FROM usuarios "
	          + "WHERE usuario_id = ?";

	    try (Connection con = ConexionOracle.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, usuarioId);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                return rs.getString("password");
	            }
	        }
	    }

	    return null;
	}
	
	/*Actulaizar contraseña*/
	public boolean actualizarPassword(
	        int usuarioId,
	        String passwordHash)
	        throws SQLException {

	    String sql =
	            "UPDATE usuarios "
	          + "SET password = ? "
	          + "WHERE usuario_id = ?";

	    try (Connection con = ConexionOracle.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, passwordHash);
	        ps.setInt(2, usuarioId);

	        return ps.executeUpdate() > 0;
	    }
	}

}
