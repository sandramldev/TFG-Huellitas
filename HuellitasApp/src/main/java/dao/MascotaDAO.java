package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;
import modelo.Mascota;
import modelo.Persona;
import util.ConexionOracle;

/* DAO encargado del acceso y gestión de datos
 * relacionados con el objeto Mascota.
 *
 * Incluye operaciones CRUD y validaciones básicas
 * de control nulo cuando son necesarias.
 * 	
 *Esta clase depende de la entidad  Cliente
 *
 * Autor: Sandra Marín
 * Proyecto: https://www.huellitas.info/index.jsp (2026)
 */	


public class MascotaDAO {	

	// Método para inserta datos en la bbdd
	public boolean insertarMascota(Mascota mascota) throws SQLException {


		String sql = "INSERT INTO mascotas (cliente_id, nombre, fecha_nacimiento, fallecimiento) "
				+ "VALUES (?, ?, ?, ?) ";

		try (
				//Conexion bbdd
				Connection conexionBd = ConexionOracle.getConnection();

				//Preparar sql
				PreparedStatement ps = conexionBd.prepareStatement(sql)){

			//Rellenar sql
			ps.setInt(1, mascota.getCliente().getClienteId());
			ps.setString(2, mascota.getNombre());
			ps.setDate(3, mascota.getFechaNacimiento());		

			//Control de null Oracle
			if(mascota.getFechaFallecimiento() != null) ps.setDate(4, mascota.getFechaFallecimiento());
			else ps.setNull(4, java.sql.Types.DATE);


			// Variable para jecutar sql
			int rows = ps.executeUpdate();
			


			//Retorna si hay filas afectadas
			return rows == 1;

		}		
	}

	// Verificar mascota duplicada
	public boolean existeMascota(String nombre,	Date fechaNacimiento) throws SQLException {

		String sql = "SELECT COUNT(*) "
			+ "FROM mascotas "
			+ "WHERE UPPER(nombre) = UPPER(?) "
			+ "AND fecha_nacimiento = ?";

		try(

			Connection conexionBd = ConexionOracle.getConnection();
			PreparedStatement ps = 	conexionBd.prepareStatement(sql)

		){

			ps.setString(1, nombre);
			ps.setDate(2, fechaNacimiento);

			try(ResultSet rs =
					ps.executeQuery()){

				if(rs.next()){

					return rs.getInt(1) > 0;
				}
			}
		}

		return false;
	}

	//Método para listar los datos insertados en la tabla mascotas
	public List<Mascota> listarMascota() throws SQLException{

		List<Mascota> lista = new ArrayList<>();

		String sql = "SELECT m.mascota_id, m.nombre, m.fecha_nacimiento, m.fallecimiento, "
				+ "       c.cliente_id, "
				+ "       p.persona_id, p.nombre AS nombre_persona "
				+ "FROM mascotas m "
				+ "JOIN clientes c ON m.cliente_id = c.cliente_id "
				+ "JOIN personas p ON c.persona_id = p.persona_id ";

		try (//Conexion bbdd
				Connection conexionBd = ConexionOracle.getConnection();

				//Preparar y ejecutar sql
				PreparedStatement ps = conexionBd.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){

			// Se ejecuta al menos una vez		
			while(rs.next()) {

				// Objetos
				Persona p = new Persona();
				Cliente c = new Cliente();
				Mascota m = new Mascota();

				//Leer columnas y rellenar los objetos
				p.setPersonaId(rs.getInt("persona_id"));
				p.setNombre(rs.getString("nombre_persona"));

				c.setClienteId(rs.getInt("cliente_id"));
				c.setPersona(p);

				m.setMascotaId(rs.getInt("mascota_id"));
				m.setNombre(rs.getString("nombre"));
				m.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
				m.setFechaFallecimiento(rs.getDate("fallecimiento"));

				m.setCliente(c);			

				// Añado mascota a la lista
				lista.add(m);

			}			
		}

		//Retorna la lista con la consulta join		
		return lista;

	}
	
	

	//Método para obtener una Mascota por id
	public Mascota obtenerMascotaPorId(int id) throws SQLException {

		//Objeto Mascota con resultado null, se rellena en Objetos
		Mascota m = null;

		String sql = "SELECT m.mascota_id, m.nombre, m.fecha_nacimiento, m.fallecimiento, "
				+ "       c.cliente_id, "
				+ "       p.persona_id, p.nombre AS nombre_persona "
				+ "FROM mascotas m "
				+ "JOIN clientes c ON m.cliente_id = c.cliente_id "
				+ "JOIN personas p ON c.persona_id = p.persona_id "
				+ "WHERE m.mascota_id = ?";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				//Solo una fila (id)
				if (rs.next()) {

					//Objetos
					Persona p = new Persona();
					Cliente c = new Cliente();

					//Leer columnas y rellenar los objetos
					m = new Mascota();

					p.setPersonaId(rs.getInt("persona_id"));
					p.setNombre(rs.getString("nombre_persona"));

					c.setClienteId(rs.getInt("cliente_id"));
					c.setPersona(p);

					m.setMascotaId(rs.getInt("mascota_id"));
					m.setNombre(rs.getString("nombre"));
					m.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
					m.setFechaFallecimiento(rs.getDate("fallecimiento"));
					m.setCliente(c);
				}
			}
		}

		return m;
	}
	
	//Mascotas por usuarios
	public List<Mascota> listarMascotasPorUsuario(int usuarioId)
			throws SQLException {

		List<Mascota> lista = new ArrayList<>();

		String sql =

			"SELECT m.mascota_id, "
			+ "m.nombre, "
			+ "m.fecha_nacimiento, "
			+ "m.fallecimiento, "
			+ "c.cliente_id, "
			+ "p.persona_id, "
			+ "p.nombre AS nombre_persona "
			+ "FROM mascotas m "
			+ "JOIN clientes c "
			+ "ON m.cliente_id = c.cliente_id "
			+ "JOIN personas p "
			+ "ON c.persona_id = p.persona_id "
			+ "JOIN usuarios u "
			+ "ON p.persona_id = u.persona_id "
			+ "WHERE u.usuario_id = ?";

		try(

			Connection conexionBd =
				ConexionOracle.getConnection();

			PreparedStatement ps =
				conexionBd.prepareStatement(sql)

		){

			ps.setInt(1, usuarioId);

			try(ResultSet rs =
					ps.executeQuery()){

				while(rs.next()){

					Persona p = new Persona();
					Cliente c = new Cliente();
					Mascota m = new Mascota();

					p.setPersonaId(	rs.getInt("persona_id"));
					p.setNombre(rs.getString("nombre_persona"));
					c.setClienteId(rs.getInt("cliente_id"));
					c.setPersona(p);

					m.setMascotaId(rs.getInt("mascota_id"));
					m.setNombre(rs.getString("nombre"));
					m.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
					m.setFechaFallecimiento(rs.getDate("fallecimiento"));

					m.setCliente(c);

					lista.add(m);
				}
			}
		}

		return lista;
	}
	
	
	//Método para actualizar una Mascota existente * ID
	public boolean actualizarMascota(Mascota mascota) throws SQLException {

		//Query
		String sql = "UPDATE mascotas "
				+ "SET nombre = ?, fecha_nacimiento = ?, fallecimiento = ?, cliente_id = ? "
				+ "WHERE mascota_id = ?";

		//Conexión BD
		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Preparar y ejecutar sql
			ps.setString(1, mascota.getNombre());
			ps.setDate(2, mascota.getFechaNacimiento());

			//Control null Oracle
			if (mascota.getFechaFallecimiento() != null)
				ps.setDate(3, mascota.getFechaFallecimiento());
			else
				ps.setNull(3, java.sql.Types.DATE);

			ps.setInt(4, mascota.getCliente().getClienteId());
			ps.setInt(5, mascota.getMascotaId());

			return ps.executeUpdate() == 1;
		}
	}

	

	//Método para eliminar una Mascota
	public boolean eliminarMascota(int mascotaId) throws SQLException {

		String sql = "DELETE FROM mascotas WHERE mascota_id = ?";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Preparar y ejecutar sql			
			ps.setInt(1, mascotaId);		    
			return ps.executeUpdate() == 1;

		}	    
	}




}
