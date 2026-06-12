package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Cita;
import modelo.Cliente;
import modelo.Mascota;
import modelo.Persona;
import util.ConexionOracle;

public class CitaDAO {

	/* DAO encargado del acceso y gestión de datos
	 * relacionados con el objeto Cita.
	 *
	 * Incluye operaciones CRUD y validaciones básicas
	 * de control nulo cuando son necesarias.
	 *
	 * Esta clase depende de las entidades Mascotas, Usuarios
	 * para la gestion de las citas
	 * 
	 * Autor: Sandra Marín
	 * Proyecto: https://www.huellitas.info/index.jsp (2026)
	 */	

	//Comprobar si existe citas para la mascota
	public boolean existeCitaMascotaDia(
	        int mascotaId,
	        LocalDate fecha)
	        throws SQLException{

	    String sql =
	        "SELECT COUNT(*) " +
	        "FROM citas " +
	        "WHERE mascota_id = ? " +
	        "AND TRUNC(fecha_hora) = ?";

	    try(Connection con =
	            ConexionOracle.getConnection();

	        PreparedStatement ps =
	            con.prepareStatement(sql)){

	        ps.setInt(1, mascotaId);

	        ps.setDate(
	            2,
	            java.sql.Date.valueOf(fecha));

	        ResultSet rs =
	            ps.executeQuery();

	        if(rs.next()){

	            return rs.getInt(1) > 0;
	        }
	    }

	    return false;
	}

	//Método para insetar citas en la bbdd
	public boolean insertarCita(Cita cita) throws SQLException {

		//Query
		String sql = "INSERT INTO citas (mascota_id, cita_tipo, fecha_hora, estado) "
				+ "VALUES (?, ?, ?, ?) ";

		try (//Conexion bbdd
				Connection conexionBd = ConexionOracle.getConnection();

				//Preparar sql
				PreparedStatement ps = conexionBd.prepareStatement(sql)){

			//Rellenar bbdd			
			ps.setInt(1, cita.getMascota().getMascotaId());
			ps.setString(2, cita.getCitaTipo());

			//Control de null Oracle
			if (cita.getFechaHora() != null)ps.setTimestamp(3, cita.getFechaHora());
			else ps.setNull(3, java.sql.Types.TIMESTAMP);

			ps.setString(4, cita.getEstado());

			//Ejecutar sql
			int rows = ps.executeUpdate();
			System.out.println("Filas insertadas: " + rows);


			//Retorna si hay filas afectadas
			return rows == 1;

		}			
	}

	//Método para la listar todas las citas que tenga la Clínica	
	public List<Cita> listarCita() throws SQLException{

		//Nuevo objeto
		List<Cita> lista = new ArrayList<>();

		//Query
		String sql = "SELECT c.cita_id, c.cita_tipo, c.fecha_hora, c.estado, "
				+ "m.mascota_id, m.nombre "
				+ "FROM citas c "
				+ "JOIN mascotas m ON c.mascota_id = m.mascota_id "
				+ "ORDER BY c.fecha_hora ASC ";

		try(//Conexion bbdd
				Connection conexionBd = ConexionOracle.getConnection();

				//Preparar y ejecutar sql
				PreparedStatement ps = conexionBd.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()){

			//Ejecutar al menos una vez
			while(rs.next()){

				//Objetos
				Cita c = new Cita();
				Mascota m = new Mascota();

				//Leer columnas y rellenar los Objetos
				c.setCitaId(rs.getInt("cita_id"));
				c.setCitaTipo(rs.getString("cita_tipo"));
				c.setFechaHora(rs.getTimestamp("fecha_hora"));
				c.setEstado(rs.getString("estado"));
				m.setMascotaId(rs.getInt("mascota_id"));
				m.setNombre(rs.getString("nombre"));
				c.setMascota(m);


				//Añado cita a la lista
				lista.add(c);

			}

		}

		return lista;
	}


	//Método para leer una cita por ID	
	public Cita leerCitaPorId(int id) throws SQLException{

		// Objeto Cita con resultado null, se rellena en el nuevo Objeto
		Cita c = null;

		//Query
		String sql = "SELECT c.cita_id, c.cita_tipo, c.fecha_hora, c.estado, "
				+ "m.mascota_id, m.nombre "
				+ "FROM citas c "
				+ "JOIN mascotas m ON c.mascota_id = m.mascota_id "
				+ "WHERE c.cita_id = ? ";

		//Conexión BD
		try(				
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Leer por id
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				//Solo una fila (id)
				if(rs.next()) {

					//Objetos
					c = new Cita();
					Mascota m = new Mascota();

					//Leer columnas y rellenar los Objetos
					c.setCitaId(rs.getInt("cita_id"));
					c.setCitaTipo(rs.getString("cita_tipo"));
					c.setFechaHora(rs.getTimestamp("fecha_hora"));
					c.setEstado(rs.getString("estado"));
					m.setMascotaId(rs.getInt("mascota_id"));
					m.setNombre(rs.getString("nombre"));
					c.setMascota(m);

				}

			}

		}
		return c;

	}
	
	//Listar citas por usuario
	public List<Cita>listarCitasPorUsuario(	int usuarioId) throws SQLException{

		List<Cita> lista =	new ArrayList<>();

		String sql = "SELECT c.cita_id, "
				+ "c.cita_tipo, "
				+ "c.fecha_hora, "
				+ "c.estado, "
				+ "m.mascota_id, "
				+ "m.nombre "
				+ "FROM citas c "
				+ "JOIN mascotas m "
				+ "ON c.mascota_id = m.mascota_id "
				+ "JOIN clientes cl "
				+ "ON m.cliente_id = cl.cliente_id "
				+ "JOIN personas p "
				+ "ON cl.persona_id = p.persona_id "
				+ "JOIN usuarios u "
				+ "ON p.persona_id = u.persona_id "
				+ "WHERE u.usuario_id = ? "
				+ "ORDER BY c.fecha_hora ASC";

		try(

			Connection conexionBd =
				ConexionOracle.getConnection();

			PreparedStatement ps =
				conexionBd.prepareStatement(sql)

		){

			ps.setInt(1, usuarioId);

			try(ResultSet rs = ps.executeQuery()){

				while(rs.next()){

					Cita c = new Cita();
					Mascota m =	new Mascota();
					
					//Cita
					c.setCitaId(rs.getInt("cita_id"));
					c.setCitaTipo(rs.getString(	"cita_tipo"));
					c.setFechaHora(rs.getTimestamp("fecha_hora"));
					c.setEstado(rs.getString("estado"));
					//Mascota
					m.setMascotaId(rs.getInt("mascota_id"));
					m.setNombre(rs.getString("nombre"));
					c.setMascota(m);

					lista.add(c);
				}
			}
		}

		return lista;
	}
	
	///Tipos de citas
	public List<String> obtenerTiposCita() throws SQLException{

	    List<String> listaTipos =
	            new ArrayList<>();

	    String sql =
	        "SELECT DISTINCT cita_tipo " +
	        "FROM citas " +
	        "WHERE cita_tipo IS NOT NULL " +
	        "ORDER BY cita_tipo";

	    try(
	        Connection conexionBd =
	                ConexionOracle.getConnection();

	        PreparedStatement ps =
	                conexionBd.prepareStatement(sql);

	        ResultSet rs =
	                ps.executeQuery();
	    ){

	        while(rs.next()){

	            listaTipos.add(
	                    rs.getString("cita_tipo"));
	        }
	    }

	    return listaTipos;
	}

	//Método para modificar una cita existente
	public boolean actualizarCita(Cita cita) throws SQLException {

		//Query
		String sql = "UPDATE citas "
				+ "SET cita_tipo = ?, fecha_hora = ?, estado = ? "
				+ "WHERE cita_id = ?";

		//Conexión BD
		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Preparar y ejecutar sql
			ps.setString(1, cita.getCitaTipo());

			//Constrol null Oracle
			if(cita.getFechaHora() != null)
				ps.setTimestamp(2, cita.getFechaHora());
			else ps.setNull(2, java.sql.Types.TIMESTAMP);
			
			ps.setString(3, cita.getEstado());
			ps.setInt(4, cita.getCitaId());


			return ps.executeUpdate() ==1;
		}	

	}



	// Método para eliminar una cita existente en la BD
	public boolean eliminarCita(int citaId) throws SQLException {

		//Query
		String sql = "DELETE FROM citas WHERE cita_id = ?";

		//Conexión BD
		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Preparar y ejecutar sql	
			ps.setInt(1, citaId);		    
			return ps.executeUpdate() == 1;

		}	    
	}

	//Eliminar cita por mascota	
	public boolean eliminarCitasPorMascota(int mascotaId)throws SQLException {

		String sql ="DELETE FROM citas "
			+ "WHERE mascota_id = ?";

		try(

			Connection conexionBd = ConexionOracle.getConnection();

			PreparedStatement ps = 	conexionBd.prepareStatement(sql)

		){

			ps.setInt(1, mascotaId);
			ps.executeUpdate();

			return true;
		}
	}

	/*------------------------------------------------------------------*/
	//Consultas auxiliares

	//Método para el control de las fechas hora, bloquea si la fecha ya está asignada y en estado pendiente

	public boolean existeCitaEnRango(Timestamp inicio, Timestamp fin, int citaId) throws SQLException {

		String sql = "SELECT COUNT(*) FROM citas "
				+ "WHERE estado = 'PENDIENTE' "
				+ "AND fecha_hora BETWEEN ? AND ? "
				+ "AND cita_id <> ?";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			ps.setTimestamp(1, inicio);
			ps.setTimestamp(2, fin);
			ps.setInt(3, citaId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}

		return false;
	}

	//Control de las horas ocupadas por citas pendientes
	public List<Timestamp> obtenerHorasOcupadasPorDia(LocalDate fecha) throws SQLException {

		List<Timestamp> horas = new ArrayList<>();

		String sql = "SELECT fecha_hora FROM citas "
				+ "WHERE TRUNC(fecha_hora) = ? "
				+ "AND estado = 'PENDIENTE'";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			ps.setDate(1, java.sql.Date.valueOf(fecha));

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					horas.add(rs.getTimestamp("fecha_hora"));
				}

			}
		}

		return horas;
	}

	public List<Cita> obtenerCitasPorDia(LocalDate fecha) throws SQLException {

		List<Cita> lista = new ArrayList<>();

		String sql = "SELECT c.cita_id, "
				+ "       c.cita_tipo, "
				+ "       c.fecha_hora, "
				+ "       c.estado, "
				+ "       m.mascota_id, "
				+ "       m.nombre AS mascota_nombre, "
				+ "       p.nombre AS persona_nombre, "
				+ "       p.apellidos "
				+ "FROM citas c "
				+ "JOIN mascotas m ON c.mascota_id = m.mascota_id "
				+ "JOIN clientes cl ON m.cliente_id = cl.cliente_id "
				+ "JOIN personas p ON cl.persona_id = p.persona_id "
				+ "WHERE TRUNC(c.fecha_hora) = ? ";

		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			ps.setDate(1, java.sql.Date.valueOf(fecha));

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					Cita c = new Cita();
					Mascota m = new Mascota();
					Cliente cl = new Cliente();
					Persona p = new Persona();

					c.setCitaId(rs.getInt("cita_id"));
					c.setCitaTipo(rs.getString("cita_tipo"));
					c.setFechaHora(rs.getTimestamp("fecha_hora"));
					c.setEstado(rs.getString("estado"));

					m.setMascotaId(rs.getInt("mascota_id"));
					m.setNombre(rs.getString("mascota_nombre"));

					p.setNombre(rs.getString("persona_nombre"));
					p.setApellidos(rs.getString("apellidos"));

					// conectar jerarquía de objetos
					cl.setPersona(p);
					m.setCliente(cl);
					c.setMascota(m);

					lista.add(c);
				}
			}
		}

		return lista;
	}




}
