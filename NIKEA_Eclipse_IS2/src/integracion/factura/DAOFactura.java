package integracion.factura;

import java.util.List;
import negocio.factura.TFactura;

public interface DAOFactura {

	public int crear(TFactura factura); // Crea una nueva factura y devuelve su ID

	public TFactura leerPorId(int id);  // Busca una factura por su ID

	public List<TFactura> leerTodas(); // Devuelve todas las facturas del sistema

	public boolean actualizar(TFactura factura);// Actualiza los datos de una factura existente

	public void eliminar(int id);

	public List<TFactura> leerPorCliente(Integer dniCliente); //Buscar facturas de un cliente concreto

	public List<TFactura> leerPorRangoFechas(String fechaInicio, String fechaFin); //Buscar facturas entre dos fechas
}
