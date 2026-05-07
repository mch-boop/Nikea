package negocio.factura;

public class TLineaFactura {

    private int idFactura;
    private int idServicio;

    private int cantidad;
    private double precioUnitario;

    // Constructor por defecto
    public TLineaFactura() {
    }

    // GETTERS Y SETTERS

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario < 0) {
            this.precioUnitario = 0.0;
        } else {
            this.precioUnitario = precioUnitario;
        }
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    public Integer getIdServicio() {
        return idServicio;
    }
}
