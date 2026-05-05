package negocio.operacionTOA;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import integracion.cliente.DAOCliente;
import integracion.empleado.DAOEmpleado;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.marca.DAOMarca;
import integracion.servicio.DAOServicio;
import negocio.cliente.TCliente;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.marca.TMarca;
import negocio.servicio.TServicio;

public class OperacionResumenTOAImp implements OperacionResumenTOA {

	private DAOFactura facturaDAO;
	private DAOCliente clienteDAO;
	private DAOServicio servicioDAO;
	private DAOMarca marcaDAO;
	private DAOEmpleado vendedorDAO;

	@Override
	public TResumenNegocio resumenShop(int mes, int anio) {

		facturaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		clienteDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		servicioDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOServicio();

		List<TFactura> facturas = facturaDAO.readByMes(mes, anio);

		// Clientes
		Collection<TCliente> clientes = clienteDAO.readAll();

		Set<Integer> idsClientes = facturas.stream().map(TFactura::getIdCliente)
				.collect(java.util.stream.Collectors.toSet());

		List<TCliente> clientesFiltrados = clientes.stream().filter(c -> idsClientes.contains(c.getId()))
				.collect(java.util.stream.Collectors.toList());

		// Servicios
		Set<Integer> idsServicios = facturas.stream().flatMap(f -> f.getLineas().stream())
				.map(TLineaFactura::getIdServicio).collect(java.util.stream.Collectors.toSet());

		Collection<TServicio> servicios = servicioDAO.readAll();

		List<TServicio> serviciosFiltrados = servicios.stream().filter(s -> idsServicios.contains(s.getId()))
				.collect(java.util.stream.Collectors.toList());

		// Marcas
		Set<String> marcas = serviciosFiltrados.stream().map(TServicio::getMarca).collect(Collectors.toSet());

		// Construcción
		return new TResumenNegocioImp(clientesFiltrados, serviciosFiltrados, facturas, marcas);
	}
}
