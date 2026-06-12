package servicio;

import java.sql.SQLException;
import java.util.List;

import dao.PersonaDAO;
import modelo.Persona;

public class PersonaServicio {

	//PersonaDAO -> UsuarioServicio para conectar con la base de datos sin sql
	private PersonaDAO personaDAO = new PersonaDAO();

	//Creo una nueva persona en la BD
	
	/*public int crearPersona(Persona p) throws SQLException {
	    return personaDAO.insertarPersona(p);
	}*/

	public int crearPersonaYDevolverId(Persona p) throws SQLException {
	    return personaDAO.insertarPersonaYDevolverId(p);
	}

	//Obtener listado de las personas de la BD
	public List<Persona>listarPersona() throws SQLException{
		return personaDAO.listarPersona();
	}

	//Buscar persona por ID
	public Persona buscarPersonaPorId(int pId) throws SQLException {
		return personaDAO.obtenerPersonaPorId(pId);		
	}

	//Modificar persona existente
	public boolean modificarPersona(Persona p) throws SQLException {

		Persona personaBD = personaDAO.obtenerPersonaPorId(p.getPersonaId());

		//Si no existe -> No modifica
		if(personaBD == null) return false;

		//Si no existe -> Modifica
		return personaDAO.actualizarPersona(p);
	}
	
	//Borrar persona por ID
	
	public boolean borrarPersona(int pId) throws SQLException {
		
		Persona personaBD = personaDAO.obtenerPersonaPorId(pId);
		
		//Si no existe -> No borra
		if(personaBD == null) return false;
		
		//Si existe -> Borra
		return personaDAO.eliminarPersona(pId);
	}
}
