package modelo;

/*
 * Modelo que representa a una persona registrado
 * en la clínica veterinaria.
 * 
 * Una Persona con datos comunes que pueden ser 
 * usuarios del sistema o  clientes de la clínica
 * 
 * Autor: Sandra Marín
 * Proyecto: https://www.huellitas.info/index.jsp (2026)
 */

public class Persona {

	// Atributos 	
	private int personaId;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String email;
	
	
	// Constructores
	public Persona() {}


	public Persona(int personaId, String nombre, String apellidos, String telefono, String email) {		
		this.personaId = personaId;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
	}


	// getters y setters
	public int getPersonaId() {
		return personaId;
	}


	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}


	public String getNombre() {
		return nombre.trim(); //Control de espacios en vacíos
	}


	public void setNombre(String nombre) {//Control de mayúsculas
		this.nombre = nombre.toUpperCase().replace('Á', 'A').replace('É', 'E').replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U');
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos.toUpperCase().replace('Á', 'A').replace('É', 'E').replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U');
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	//Métodos sobre escritos

	@Override
	public String toString() {	
		return "Persona ID " + this.personaId + "\n" +
				"Nombre " + this.nombre + "\n" +
				"Apellidos " + this.apellidos + "\n" +
				"Teléfono " + this.telefono + "\n" +
				"Email " + email + "\n" ;
	}
		
	
}
