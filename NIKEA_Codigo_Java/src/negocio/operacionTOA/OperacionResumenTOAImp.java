package negocio.operacionTOA;
 
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
 
import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.factura.DAOLineaFactura;
import integracion.servicio.DAOServicio;
import negocio.cliente.TCliente;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.servicio.TServicio;
 
public class OperacionResumenTOAImp implements OperacionResumenTOA {
 
	@Override
	public TResumenNegocio resumenShop(int mes, int anio) {
 
		DAOFactura facturaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		DAOCliente clienteDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		DAOServicio servicioDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOServicio();
		DAOLineaFactura lineaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
 
		List<TFactura> facturas = facturaDAO.readByMonth(mes, anio);
 
		// Clientes
		Collection<TCliente> clientes = clienteDAO.readAll();
 
		Set<Integer> idsClientes = facturas.stream().map(TFactura::getIdCliente)
				.collect(java.util.stream.Collectors.toSet());
 
		List<TCliente> clientesFiltrados = clientes.stream().filter(c -> idsClientes.contains(c.getId()))
				.collect(java.util.stream.Collectors.toList());
 
		// Servicios
		// Obtener IDs de facturas del mes
		Set<Integer> idsFacturas = facturas.stream().map(TFactura::getId).collect(Collectors.toSet());
		
		// Obtener todas las líneas y filtrar por facturas del mes
		Set<Integer> idsServicios = lineaDAO.readAll().stream()
				.filter(linea -> idsFacturas.contains(linea.getIdFactura()))
				.map(TLineaFactura::getIdServicio)
				.collect(java.util.stream.Collectors.toSet());
 
		Collection<TServicio> servicios = servicioDAO.readAll();
 
		List<TServicio> serviciosFiltrados = servicios.stream().filter(s -> idsServicios.contains(s.getId()))
				.collect(java.util.stream.Collectors.toList());
 
		// Marcas
		Set<String> marcas = serviciosFiltrados.stream().map(TServicio::getMarca).collect(Collectors.toSet());
 
		// Construcción
		return new TResumenNegocio(clientesFiltrados, serviciosFiltrados, facturas, marcas);
	}
}
