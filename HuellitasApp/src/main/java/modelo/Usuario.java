package modelo;

/*
 * Modelo que representa a un usuario registrado
 * en la clínica veterinaria.
 * 
 * Usuario del sistema asociado a una Persona 
 *  y a un Rol de acceso 
 *  
 *  Autor: Sandra Marín
 * Proyecto: https://www.huellitas.info/index.jsp (2026)
 */

public class Usuario {
	
	// Atributos	
	private int usuarioId;
	private Persona persona;
	private RolUsuario rolUsuario;
	private String password;
	
	//Constructores
	public Usuario() {}

	public Usuario(int usuarioId, Persona persona, RolUsuario rolUsuario, String password) {
		this.usuarioId = usuarioId;
		this.persona = persona;
		this.rolUsuario = rolUsuario;
		this.password = password;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public RolUsuario getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(RolUsuario rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//Método sobrescrito
	@Override
	public String toString() {
	    return "Usuario ID = " + usuarioId +
	           "\nNombre = " + (persona != null ? persona.getNombre() : "sin persona") +
	           "\nApellidos = " + persona.getApellidos() +
	           "\nTeléfono = " + persona.getTelefono() +
	           "\nEmail = " + persona.getEmail() +
	           "\nRol = " + rolUsuario.getRolNombre() + 
	           "\nPassword = " + this.password + "\n";
	}

	

}
