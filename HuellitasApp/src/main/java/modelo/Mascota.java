package modelo;

import java.sql.Date;

/*
 * Modelo que representa a una mascota  registrada
 * en la clínica veterinaria.
 * 
 * Una mascota depende de un cliente para la gestión de sus citas 
 * en el sistema de la clínica
 * 
 * Autor: Sandra Marín
 * Proyecto: https://www.huellitas.info/index.jsp (2026)
 */


public class Mascota {
	
	//Atributos
	private int mascotaId;
	private Cliente cliente;
	private String nombre;
	private Date fechaNacimiento;
	private Date fechaFallecimiento;
	
	//Constructores
	public Mascota() {}

	public Mascota(int mascotaId, Cliente cliente, String nombre, Date fechaNacimiento, Date fechaFallecimiento) {
	
		this.mascotaId = mascotaId;
		this.cliente = cliente;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaFallecimiento = fechaFallecimiento;
	}

	public int getMascotaId() {
		return mascotaId;
	}

	public void setMascotaId(int mascotaId) {
		this.mascotaId = mascotaId;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNombre() {
		return nombre.trim();
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase().replace('Á', 'A').replace('É', 'E').replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U');
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaFallecimiento() {
		return fechaFallecimiento;
	}

	public void setFechaFallecimiento(Date fechaFallecimiento) {
		this.fechaFallecimiento = fechaFallecimiento;
	}

	

	@Override
	public String toString() {
		return "Mascota ID = " + this.mascotaId + "\n" +
				"Cliente = " + (cliente != null ? cliente.getClienteId() : "null") + "\n" +
				"Nombre = " + this.nombre + "\n" +
				"Fecha de nacimiento = " + this.fechaNacimiento + "\n" +
				"Fecha de fallecimiento = " + (this.fechaFallecimiento != null ? this.fechaFallecimiento : "-") + "\n";
	}
	
	
	
	
	
	
	

}
