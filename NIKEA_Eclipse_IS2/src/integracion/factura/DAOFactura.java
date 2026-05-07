package integracion.factura;

import java.util.List;
import negocio.factura.TFactura;

public interface DAOFactura {

	public int create(TFactura factura); // Crea una nueva factura y devuelve su ID

	public TFactura read(int id);  // Busca una factura por su ID

	public List<TFactura> readAll(); // Devuelve todas las facturas del sistema

	public boolean update(TFactura factura);// Actualiza los datos de una factura existente

	public List<TFactura> readByClient(int idCliente); //Buscar facturas de un cliente concreto

	public List<TFactura> readByDateRange(String fechaInicio, String fechaFin); //Buscar facturas entre dos fechas

	public List<TFactura> readByMonth(int mes, int anio); //Lee las facturas de un mes concreto
}
