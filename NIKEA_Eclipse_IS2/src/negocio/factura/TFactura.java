package negocio.factura;

import java.util.ArrayList;
import java.util.List;

public class TFactura {

    private int id;
    private int idVendedor;
    private int idCliente;
    private int idDescuento;
    private String fecha;

    private double total;
    private boolean cerrada;

    private List<TLineaFactura> lineas;

    // Constructora por defecto
    public TFactura() {
        this.lineas = new ArrayList<>();
        this.total = 0.0;
        this.cerrada = false;
    }

    // GETTERS Y SETTERS

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    // Métodos

    public TLineaFactura buscarLinea(int idProducto) {

        if (this.lineas == null) {
            return null;
        }

        TLineaFactura existente = null;
        int i = 0;
        while (i < lineas.size() && existente == null) {
            if (lineas.get(i).getIdProducto() == idProducto) {
                existente = lineas.get(i);
            }
            i++;
        }
        return existente;
    }

    public void addLinea(TLineaFactura linea) {

        if (this.lineas == null) {
            this.lineas = new ArrayList<>();
        }
        this.lineas.add(linea);
    }

<<<<<<< Updated upstream
    public double getImporte() {
        double suma = 0.0;

        for (TLineaFactura l : lineas) {
            suma += l.getSubtotal(); 
        }

        return suma;
    }


=======
    public void removeLinea(TLineaFactura linea) {

        if (this.lineas == null) {
            return;
        }
        this.lineas.remove(linea);
    }

>>>>>>> Stashed changes
}