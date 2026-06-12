package modelo;

/*
 * Modelo que representa una cita registrada
 * en la clínica veterinaria.
 * 
 * Autor: Sandra Marín
 * Proyecto: https://www.huellitas.info/index.jsp (2026)
 */


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Cita {
	
	// Atributos
	private int citaId;
	private Mascota mascota;
	private String citaTipo;
	private Timestamp fechaHora;
	private String estado;
	
	//Constructores
	public Cita() {	}

	public Cita(int citaId, Mascota mascota, String citaTipo, Timestamp fechaHora, String estado) {
		
		this.citaId = citaId;
		this.mascota = mascota;
		this.citaTipo = citaTipo;
		this.fechaHora = fechaHora;
		this.estado = estado;
	}

	public int getCitaId() {
		return citaId;
	}

	public void setCitaId(int citaId) {
		this.citaId = citaId;
	}

	public Mascota getMascota() {
		return mascota;
	}

	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}

	public String getCitaTipo() {
		return citaTipo;
	}

	public void setCitaTipo(String citaTipo) {
		this.citaTipo = citaTipo;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

	
	@Override
	public String toString() {

	    SimpleDateFormat sdf =
	            new SimpleDateFormat("dd/MM/yyyy HH:mm");

	    return "Cita ID = " + this.citaId + "\n"
	            + "Mascota = "
	            + (mascota != null ? mascota.getMascotaId() : "null") + "\n"
	            + "Tipo de cita = " + this.citaTipo + "\n"
	            + "Fecha y hora = "
	            + (this.fechaHora != null
	                ? sdf.format(this.fechaHora)
	                : " - ") + "\n"
	            + "Estado = "
	            + (this.estado != null ? this.estado : "-") + "\n";
	}
	

}
