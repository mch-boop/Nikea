package negocio.factura;

import java.util.List;

import negocio.servicio.TServicio;

public interface SAFactura {
	public boolean iniciarVenta(TFactura factura);
	public boolean añadirLinea(TLineaFactura linea, TServicio servicio, int idMontador);
    public int cerrarVenta(TFactura factura);

    public List<TFactura> mostrarTodas();
}
