package negocio.servicio;

public class TMontaje extends TServicio {
	
	// ATRIBUTO
	private int idMontador;

	// CONSTRUCTORA
	public TMontaje() {
		this.tipo = 2;
	}

	// GETTERS & SETTERS
	public int getIdMontador() {
		return idMontador;
	}

	public void setIdMontador(int idMontador) {
		this.idMontador = idMontador;
	}
}
