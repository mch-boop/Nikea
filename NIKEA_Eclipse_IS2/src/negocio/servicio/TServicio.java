package negocio.servicio;

public abstract class TServicio {

	// ATRIBUTOS
	private String nombre;
	private Integer id;
	private String descripcion;
	private Integer stock;
	private Double precioActual;
	private boolean activo;
	protected Integer tipo;
	private String marca;
	
	// MÉTODOS

	public TServicio() {
		this.activo = true;
	}

	public TServicio(Integer id, String nombre, String descripcion, Integer stock, double precioActual, boolean activo, Integer tipo) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.stock = stock;
		this.precioActual = precioActual;
		this.activo = activo;
		this.tipo = tipo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrecioActual() {
		return precioActual;
	}

	public void setPrecioActual(Double precio) {
		this.precioActual = precio;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return "TServicio [ID=" + id + ", Nombre=" + nombre + ", Descripcion=" + descripcion + ", Stock=" + stock
				+ ", PrecioActual=" + precioActual + ", Activo=" + activo + ", Tipo=" + tipo + ", Marca=" + marca + "]";
	}
	

}
