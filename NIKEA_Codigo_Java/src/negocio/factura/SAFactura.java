package negocio.factura;

import java.util.List;
import java.util.Map;

import negocio.servicio.TServicio;

public interface SAFactura {
	public int iniciarVenta(TFactura factura);

	public boolean annadirLinea(TLineaFactura linea, TServicio servicio, int idMontador);

	public int annadirServicioAVenta(TLineaFactura linea);

	public int eliminarServicioDeVenta(TLineaFactura linea);

	public int cerrarVenta(TFactura factura);

	public TFactura mostrarPorId(int idFactura);

	public List<TFactura> mostrarPorCliente(int idCliente);

	public List<TFactura> mostrarTodas();

	public int annadirDescuento(int idFactura, int idDescuento);

	// Casos de uso extra
	public Map<String, Double> getVentasPorMarca();
}
