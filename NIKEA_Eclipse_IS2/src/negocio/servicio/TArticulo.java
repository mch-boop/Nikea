package negocio.servicio;

public class TArticulo extends TServicio {

	// ATRIBUTOS
	private int idMarca;
	private int ventas;

	// CONSTRUCTORA
	public TArticulo() {
		this.tipo = 1;
	}

	// GETTERS & SETTERS
	public void setMarcaId(int marca) {
		this.idMarca = marca;
	}

	public int getMarcaId() {
		return idMarca;
	}

	public int getVentas() {
		return ventas;
	}

	public void setVentas(int ventas) {
		this.ventas = ventas;
	}
}
