package negocio.servicio;

public class TArticulo extends TServicio {
	private int idMarca;
	private int ventas;
	
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
