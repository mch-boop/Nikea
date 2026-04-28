package negocio.servicio;

public abstract class TServicio {

	// ATRIBUTOS
	
	private int idServicio;
	private String descripcion;
	private int stock;
	private int precioActual;
	private boolean activo;
	
	// MÉTODOS
	
	public int getId() {
		return idServicio;
	}
	public void setId(int id) {
		this.idServicio = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getPrecioActual() {
		return precioActual;
	}
	public void setPrecioActual(int precioActual) {
		this.precioActual = precioActual;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
