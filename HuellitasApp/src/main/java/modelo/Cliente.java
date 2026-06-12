package modelo;


/*
 * Modelo que representa a un cliente registrado
 * en la clínica veterinaria.
 * 
 * El cliente podrá visualizar los servicios
 * de la clínica y solicitar citas si dispone
 * de acceso como usuario del sistema.
 * 
 * Autor: Sandra Marín
 * Proyecto: https://www.huellitas.info/index.jsp (2026)
 */

public class Cliente {
	
	// Atributos	
	private int clienteId;
	private Persona persona;
	
	
	//Constructores
	public Cliente() {}


	public Cliente(int clienteId, Persona persona) {
		
		this.clienteId = clienteId;
		this.persona = persona;
	}

	
	//Getters y Setters

	public int getClienteId() {
		return clienteId;
	}


	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}


	public Persona getPersona() {
		return persona;
	}


	public void setPersona(Persona persona) {
		this.persona = persona;
	}


	//Método sobrescrito
	@Override
	public String toString() {
		return "Cliente ID = " + this.clienteId + "\n" +
				"Persona ID = " + (persona != null ? persona.getPersonaId() : "null") + "\n";
	}
	
	
	
	
	
	

}
