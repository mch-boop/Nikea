package negocio.servicio;

public class TArticulo extends TServicio {
	private int idMarca;
	private int ventas;
	
	public void setMarca(int marca) {
		this.idMarca = marca;
	}
	
	public int getMarca() {
		return idMarca;	 
	}
}
