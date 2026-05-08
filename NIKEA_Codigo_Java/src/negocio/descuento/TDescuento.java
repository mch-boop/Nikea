package negocio.descuento;

public class TDescuento {
    
    private int id;
    private String codigo;
    private String nombre;
    private int porcentaje;
    private boolean tipo; // true = Importe, false = Cantidad
    private double cantidad; 
    private boolean activo;

    public TDescuento() {
        this.activo = true;
    }

    public TDescuento(int id, String codigo, String nombre, int porcentaje, boolean tipo, double cantidad, boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.activo = activo;
    }
    
    public TDescuento(boolean tipo) {
        this.tipo = tipo;
        this.activo = true;
    }

    // --- GETTERS Y SETTERS ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPorcentaje() { return porcentaje; }
    public void setPorcentaje(int porcentaje) { this.porcentaje = porcentaje; }

    public boolean isTipo() { return tipo; }
    public void setTipo(boolean tipo) { this.tipo = tipo; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public void setImporteMin(Double value) { this.cantidad = value; }
    public void setProductosMin(Integer value) { this.cantidad = value.doubleValue(); }

    public Double getImporteMin() { return tipo ? cantidad : null; }
    public Integer getProductosMin() { return !tipo ? (int) cantidad : null; }

    @Override
    public String toString() {
        return "TDescuento [ID=" + id + ", Cod=" + codigo + ", %=" + porcentaje + 
               ", Tipo=" + (tipo ? "Importe" : "Cantidad") + ", Condicion=" + cantidad + "]";
    }
}