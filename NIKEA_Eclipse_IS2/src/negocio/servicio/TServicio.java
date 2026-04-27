package negocio.servicio;

import org.json.JSONObject;

public abstract class TServicio {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private Integer precioActual;
    private boolean activo;
    private Integer tipo;

    public TServicio() { activo = true; }

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

    public void fromJSON(JSONObject obj) {}

    public Integer getId() {
        return id;
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
        return "TServicio [ID=" + id + ", Nombre=" + nombre +
               ", Descripcion=" + descripcion + ", Stock=" + stock +
               ", PrecioActual=" + precioActual +
               ", Activo=" + activo + ", Tipo=" + tipo + "]";
    }
}