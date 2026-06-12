package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import modelo.RolUsuario;
import util.ConexionOracle;

public class RolDAO {

	/*
	 * DAO encargado del acceso y gestión de datos
	 * relacionados con el objeto Rol.
	 *
	 * Incluye operaciones CRUD y validaciones básicas
	 * de control nulo cuando son necesarias.
	 * 
	 * Esta clase se relaciona con la entidad Usuarios 
	 * para la asignación y gestión de roles.
	 * 
	 * Autor: Sandra Marín
	 * Proyecto: https://www.huellitas.info/index.jsp (2026)
	 */


	//Método para insertar Roles para los usuarios
	public boolean insertarRol(RolUsuario rol) throws SQLException {

		//Query
		String sql = "INSERT INTO roles_usuarios (rol_nombre) "
				+ "VALUES (?)";

		//Conexión BD
		try (//Conexion bbdd
				Connection conexionBd = ConexionOracle.getConnection();

				//Preparar sql
				PreparedStatement ps = conexionBd.prepareStatement(sql)){

			//Rellenar y ejecutar sql
			ps.setString(1, rol.getRolNombre());


			//Variable para ejecutar
			int rows = ps.executeUpdate();			


			//Retorna si hay filas afectadas
			return rows == 1;
		}
	}

	//Método para leer todos los roles de la BD
	public List<RolUsuario> listarRoles() throws SQLException{

		List<RolUsuario> lista = new ArrayList<>();

		//Query
		String sql = "SELECT r.rol_usuario_id, r.rol_nombre "
				+ "FROM roles_usuarios r"; 

		//Conexión BD
		try
		(
				Connection conexionBd = ConexionOracle.getConnection();

				//Preparar y ejecutar sql
				PreparedStatement ps = conexionBd.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){

			//Se ejecuta al menos una vez
			while(rs.next()) {

				//Objeto
				RolUsuario r = new RolUsuario();

				//Leer columnas y rellenar objeto
				r.setRolUsuarioId(rs.getInt("rol_usuario_id"));
				r.setRolNombre(rs.getString("rol_nombre"));

				//Añadir rol a la lista
				lista.add(r);
			}
		}

		return lista;

	}

	//Método para obtener un rol por id
	public RolUsuario obtenerRolPorId(int id) throws SQLException {

		//Objeto RolUsuario con resultado null, se rellena en Objetos
		RolUsuario r = null;


		//Query
		String sql = "SELECT r.rol_usuario_id, r.rol_nombre "
				+ "FROM roles_usuarios r "
				+ "WHERE rol_usuario_id = ?";

		//Conexión BD
		try
		(
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				){

			//Buscar por id
			ps.setInt(1, id);

			try(ResultSet rs = ps.executeQuery()){

				//Solo una fila (id)
				if(rs.next()) {


					//Leer columnas y rellenar Objeto
					r = new  RolUsuario();

					r.setRolUsuarioId(rs.getInt("rol_usuario_id"));
					r.setRolNombre(rs.getString("rol_nombre"));

				}

			}		

		}

		return r;
	} 

	//Obtener rol por nombre, afecta a RolController
	public RolUsuario obtenerRolPorNombre(String nombre) throws SQLException {
		RolUsuario r = null;

		String sql = "SELECT rol_usuario_id, rol_nombre FROM roles_usuarios WHERE rol_nombre = ?";

		try (Connection con = ConexionOracle.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, nombre);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					r = new RolUsuario();
					r.setRolUsuarioId(rs.getInt("rol_usuario_id"));
					r.setRolNombre(rs.getString("rol_nombre"));
				}
			}
		}
		return r;
	}


	//Método para modificar un rol

	public boolean actualizarRolUsuario(RolUsuario rol) throws SQLException {

		//Query
		String sql = "UPDATE roles_usuarios "
				+ "SET rol_nombre = ? "
				+ "WHERE rol_usuario_id = ?";

		//Conexión BD
		try
		(
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				){

			//Preparar y ejecutar sql
			ps.setString(1, rol.getRolNombre());
			ps.setInt(2, rol.getRolUsuarioId());


			return ps.executeUpdate() == 1;
		}

	}

	/*Método para eliminar un Rol
	 * NOTA: Si se elimina un rol, afecta a todos los usuarios de la empresa que estén asociados al mismo.
	 * Anular desde usuario.
	 **/

	public boolean eliminarRol(int rolId) throws SQLException {

		String sql = "DELETE FROM roles_usuarios WHERE rol_usuario_id = ?";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Preparar y ejecutar sql			
			ps.setInt(1, rolId);		

			//Retorna las filas afectadas
			return ps.executeUpdate() == 1;

		}	    
	}

}//Clase

