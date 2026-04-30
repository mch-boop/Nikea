package negocio.factura;

import java.util.List;

import negocio.servicio.TServicio;

public interface SAFactura {
    public boolean iniciarVenta(TFactura factura);

    public boolean añadirLinea(TLineaFactura linea, TServicio servicio, int idMontador);

    public boolean añadirServicioAVenta(TLineaFactura linea);

    public ResultadoEliminarLinea eliminarServicioDeVenta(TLineaFactura linea);

    public int cerrarVenta(TFactura factura);

    public TFactura mostrarPorId(int idFactura);

    public List<TFactura> mostrarPorCliente(int idCliente);

    public List<TFactura> mostrarTodas();
}
