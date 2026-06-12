package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;
import modelo.Persona;
import util.ConexionOracle;

public class ClienteDAO {

	/* 
	 *  DAO encargado del acceso y gestión de datos
	 * relacionados con el objeto Cliente.
	 *
	 * Incluye operaciones CRUD y validaciones básicas
	 * de control nulo cuando son necesarias, y validaciones relacionadas
	 * con disponibilidad de fecha y estado de la cita.
	 *
	 * Esta clase depende de Persona para los datos comunes.
	 * 
	 * Autor: Sandra Marín
	 * Proyecto: https://www.huellitas.info/index.jsp (2026)
	 */	
	 	 
	 


	public boolean insertarCliente(Cliente cliente) throws SQLException {

		//Query		
		String sql= "INSERT INTO clientes (persona_id) VALUES (?)";

		//Conexión BD
		try (//Conexion bbdd
				Connection conexionBd = ConexionOracle.getConnection();

				//Preparar sql
				PreparedStatement ps = conexionBd.prepareStatement(sql)){

			//Rellenar y ejecutar sql
			ps.setInt(1, cliente.getPersona().getPersonaId());			

			//Variable para ejecutar
			int rows = ps.executeUpdate();			


			//Retorna si hay filas afectadas
			return rows == 1;
		}
	}

	//Método para ver todos los clientes de la BD
	public List<Cliente> listarClientes() throws SQLException {

		List<Cliente> lista = new ArrayList<>();

		//Query
		String sql = "SELECT c.cliente_id, p.persona_id, p.nombre "
				+ "FROM clientes c "
				+ "JOIN personas p ON c.persona_id = p.persona_id";

		//Conexión BD
		try (
				Connection conexionBd = ConexionOracle.getConnection();

				//Preparar y ejecutar sql	
				PreparedStatement ps = conexionBd.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()
				) {

			//Ejecutar al menos una vez
			while (rs.next()) {
				Persona p = new Persona();
				Cliente c = new Cliente();

				//Leer columnas y rellenar los Objetos
				p.setPersonaId(rs.getInt("persona_id"));
				p.setNombre(rs.getString("nombre"));

				c.setClienteId(rs.getInt("cliente_id"));
				c.setPersona(p);

				//Añado cliente a la lista
				lista.add(c);
			}
		}

		return lista;
	}

	//Método para saber si el cliente tiene mascota
	public boolean tieneMascotas(int clienteId) throws SQLException {

		String sql = "SELECT COUNT(*) FROM mascotas WHERE cliente_id = ?";

		try (Connection con = ConexionOracle.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, clienteId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0; // si hay alguna mascota → true
				}
			}
		}
		return false;
	}


	//Método para buscar cliente por id	
	public Cliente leerClientePorId (int id) throws SQLException {

		//Objeto Cliente con resultado null, se rellena en el nuevo Objeto		
		Cliente c = null;

		//Query
		String sql = "SELECT c.cliente_id, p.persona_id, p.nombre "
				+ "FROM clientes c "
				+ "JOIN personas p ON c.persona_id = p.persona_id "
				+ "WHERE cliente_id = ? ";

		// Conexión BD				
		try(
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)){

			//Leer por id
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				//Solo una fila (id)
				if(rs.next()) {

					//Objetos
					c = new Cliente();
					Persona p = new Persona();

					//Leer columnas y rellenar objetos
					c.setClienteId(rs.getInt("cliente_id"));
					p.setPersonaId(rs.getInt("persona_id"));
					p.setNombre(rs.getString("nombre"));
					c.setPersona(p);

				}

			}
		}
		return c; 
	}


	//Método para el registro de mascotas por el cliente
	public Cliente leerClientePorPersonaId(int personaId)
	        throws SQLException {

	    String sql =
	            "SELECT c.cliente_id, p.persona_id, p.nombre "
	          + "FROM clientes c "
	          + "JOIN personas p ON c.persona_id = p.persona_id "
	          + "WHERE p.persona_id = ?";

	    try (
	            Connection conexionBd =
	                    ConexionOracle.getConnection();

	            PreparedStatement ps =
	                    conexionBd.prepareStatement(sql)
	    ) {

	        ps.setInt(1, personaId);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {

	                return mapearCliente(rs);

	            }
	        }
	    }

	    return null;
	}

	//Método para modificar un cliente
	public boolean actualizarCliente (Cliente cliente) throws SQLException {

		//Query
		String sql = "UPDATE clientes "
				+ "SET persona_id = ? "
				+ "WHERE cliente_id = ?";

		//Conexión BD
		try
		(Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				){

			//Preparar y ejecutar sql
			ps.setInt(1, cliente.getPersona().getPersonaId());
			ps.setInt(2, cliente.getClienteId() );

			return ps.executeUpdate() ==1;
		}
	}

	// Método para eliminar una cliente existente en la BD
	public boolean eliminarCliente(int clienteId) throws SQLException {

		//Query
		String sql = "DELETE FROM clientes WHERE cliente_id = ?";

		//Conexión BD
		try (
				Connection conexionBd = ConexionOracle.getConnection();
				PreparedStatement ps = conexionBd.prepareStatement(sql)
				) {

			//Preparar y ejecutar sql	
			ps.setInt(1, clienteId);		    
			return ps.executeUpdate() == 1;

		}	    
	}

	private Cliente mapearCliente(ResultSet rs) throws SQLException {

	    Cliente c = new Cliente();
	    Persona p = new Persona();

	    c.setClienteId(rs.getInt("cliente_id"));
	    p.setPersonaId(rs.getInt("persona_id"));

	    // Si en la consulta viene el nombre:
	    try {
	        p.setNombre(rs.getString("nombre"));
	    } catch (SQLException e) {
	        
	    }
	    c.setPersona(p);

	    return c;
	}

}
