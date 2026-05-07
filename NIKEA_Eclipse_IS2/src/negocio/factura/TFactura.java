package negocio.factura;

import java.util.Date;
import java.util.List;

public class TFactura {

    private int id;
    private int idVendedor;
    private int idCliente;
    private int idDescuento;
    private Date fecha;

    private double total;
    private boolean cerrada;

    private List<TLineaFactura> lineas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(int idDescuento) {
        this.idDescuento = idDescuento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date date) {
        this.fecha = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isCerrada() {
        return cerrada;
    }

    public void setCerrada(boolean cerrada) {
        this.cerrada = cerrada;
    }

    public List<TLineaFactura> getLineas() {
        return lineas;
    }

    public void setLineas(List<TLineaFactura> lineas) {
        this.lineas = lineas;
    }
}
