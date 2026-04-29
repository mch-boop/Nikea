package negocio.servicio;

import org.json.JSONObject;

public abstract class TServicio {

	// ATRIBUTOS
	private String nombre;
	private Integer id;
	private String descripcion;
	private Integer stock;
	private Integer precioActual;
	private boolean activo;
	private Integer tipo;
	
	// MÉTODOS

	public TServicio() {
		this.activo = true;
	}

	public TServicio(Integer id, String nombre, String descripcion, Integer stock, Integer precioActual, boolean activo, Integer tipo) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.stock = stock;
		this.precioActual = precioActual;
		this.activo = activo;
		this.tipo = tipo;
	}

	public JSONObject asJSON() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("nombre", this.nombre);
		obj.put("descripcion", this.descripcion);
		obj.put("stock", this.stock);
		obj.put("precioActual", this.precioActual);
		obj.put("activo", this.activo);
		obj.put("tipo", this.tipo);
		return obj;
	}

	public void fromJSON(JSONObject obj) {
		if (obj == null) return;
		this.id = obj.has("id") && !obj.isNull("id") ? obj.getInt("id") : null;
		this.nombre = obj.has("nombre") && !obj.isNull("nombre") ? obj.getString("nombre") : null;
		this.descripcion = obj.has("descripcion") && !obj.isNull("descripcion") ? obj.getString("descripcion") : null;
		this.stock = obj.has("stock") && !obj.isNull("stock") ? obj.getInt("stock") : null;
		this.precioActual = obj.has("precioActual") && !obj.isNull("precioActual") ? obj.getInt("precioActual") : null;
		this.activo = obj.has("activo") && !obj.isNull("activo") ? obj.getBoolean("activo") : true;
		this.tipo = obj.has("tipo") && !obj.isNull("tipo") ? obj.getInt("tipo") : null;
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

	public Integer getPrecioActual() {
		return precioActual;
	}

	public void setPrecioActual(Integer precioActual) {
		this.precioActual = precioActual;
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

	@Override
	public String toString() {
		return "TServicio [ID=" + id + ", Nombre=" + nombre + ", Descripcion=" + descripcion + ", Stock=" + stock
				+ ", PrecioActual=" + precioActual + ", Activo=" + activo + ", Tipo=" + tipo + "]";
	}
	

}
