package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import modelo.Persona;
import util.ConexionOracle;

public class PersonaDAO {

	/* 
	 *  DAO encargado del acceso y gestión de datos
	 * relacionados con el objeto Persona.
	 *
	 * Incluye operaciones CRUD y validaciones básicas
	 * de control nulo cuando son necesarias.
	 *  
	 * Esta clase se relaciona con la entidades Usuarios
	 * 
	 * Autor: Sandra Marín
	 * Proyecto: https://www.huellitas.info/index.jsp (2026)
	 */

	

	//Insert + Select utilizando el campo unico email
	public int insertarPersonaYDevolverId(Persona persona) throws SQLException {

	    String insertSql =
	        "INSERT INTO personas (nombre, apellidos, telefono, email) " +
	        "VALUES (?, ?, ?, ?)";

	    String selectSql =
	        "SELECT persona_id FROM personas WHERE email = ?";

	    try (Connection con = ConexionOracle.getConnection()) {

	        // INSERT
	        try (PreparedStatement ps = con.prepareStatement(insertSql)) {
	            ps.setString(1, persona.getNombre());
	            ps.setString(2, persona.getApellidos());
	            ps.setString(3, persona.getTelefono());
	            ps.setString(4, persona.getEmail());
	            ps.executeUpdate();
	        }

	        // SELECT del ID recién insertado
	        try (PreparedStatement ps = con.prepareStatement(selectSql)) {
	            ps.setString(1, persona.getEmail());
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("persona_id");
	                }
	            }
	        }
	    }

	    return -1;
	}


	/*Método para listar todos
	  los datos de las personas registradas en la clínica*/	
	public List<Persona> listarPersona() throws SQLException{

		List<Persona> lista = new ArrayList<>();

		//Query
		String sql = "SELECT p.persona_id, p.nombre, p.apellidos, p.telefono, p.email "
				+ "FROM personas p ";

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
				Persona p = new Persona();

				//Leer columnas y rellenar objeto
				p.setPersonaId(rs.getInt("persona_id"));
				p.setNombre(rs.getString("nombre"));
				p.setApellidos(rs.getString("apellidos"));
				p.setTelefono(rs.getString("telefono"));
				p.setEmail(rs.getString("email"));	


				//Añado persona a la lista
				lista.add(p);
			}
		}
		//Retorna la lista con la consulta
		return lista;

	}

	//Método para obtener una persona por id
	public Persona obtenerPersonaPorId(int id) throws SQLException {

		//Objeto Persona con resultado null, se rellena en Objetos
		Persona p = null;


		//Query
		String sql = "SELECT p.persona_id, p.nombre, p.apellidos, p.telefono, p.email "
				+ "FROM personas p "
				+ "WHERE persona_id = ?";

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
					p = new  Persona();

					p.setPersonaId(rs.getInt("persona_id"));
					p.setNombre(rs.getString("nombre"));
					p.setApellidos(rs.getString("apellidos"));
					p.setTelefono(rs.getString("telefono"));
					p.setEmail(rs.getString("email"));
				}

			}		

		}

		return p;

	}

	//Método para modificar los datos de una persona
	public boolean actualizarPersona(Persona persona) throws SQLException {

		//Query
		String sql = "UPDATE personas "
				+ "SET nombre = ?, apellidos = ?, telefono = ?, email = ? "
				+ "WHERE persona_id = ?";

		//Conexión BD
		try
		(
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				){

			//Preparar y ejecutar sql
			ps.setString(1, persona.getNombre());
			ps.setString(2, persona.getApellidos());			
			ps.setString(3, persona.getTelefono());
			ps.setString(4, persona.getEmail());
			ps.setInt(5, persona.getPersonaId());

			return ps.executeUpdate() == 1;
		}

	}

	//Método para eliminar una Persona
	public boolean eliminarPersona(int personaId) throws SQLException {

		String sql = "DELETE FROM personas WHERE persona_id = ?";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Preparar y ejecutar sql			
			ps.setInt(1, personaId);		    
			return ps.executeUpdate() == 1;

		}	    
	}


}
