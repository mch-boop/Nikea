package negocio.factura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.factura.DAOLineaFactura;
import integracion.cliente.DAOCliente;
import integracion.descuento.DAODescuento;
import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOMontadorMontaje;
import negocio.cliente.TCliente;
import negocio.descuento.TDescuento;
import negocio.empleado.TEmpleado;
import negocio.empleado.TMontador;
import negocio.empleado.TMontadorMontaje;
import negocio.servicio.TServicio;
import presentacion.controlador.Eventos;

public class SAFacturaImp implements SAFactura {
	private DAOMontadorMontaje daoMontaje = FactoriaAbstractaIntegracion.getInstance().crearDAOMontadorMontaje();
	private static TFactura facturaActual;
	private Map<Integer, Integer> servicioAMontador = new HashMap<>();
	private DAOEmpleado daoEmpleado = FactoriaAbstractaIntegracion.getInstance().crearDAOEmpleado();

	@Override
	public boolean iniciarVenta(TFactura factura) {

		if (factura == null || factura.getIdVendedor() <= 0)
			return false;

		facturaActual = new TFactura();
		facturaActual.setIdVendedor(factura.getIdVendedor());
		facturaActual.setLineas(new ArrayList<>());
		facturaActual.setCerrada(false);

		servicioAMontador.clear();
		return true;
	}

	// Está hecha la lógica para vincular y desvincular montadores, queda el resto.
	@Override
	public boolean añadirLinea(TLineaFactura linea, TServicio servicio, int idMontador) {
		if (facturaActual == null || linea == null || servicio == null)
			return false;

		facturaActual.getLineas().add(linea);

		// if (esMontaje(servicio)) {

		Integer idMontador1 = obtenerMontadorDisponible();

		if (idMontador1 == null)
			return false;

		servicioAMontador.put(servicio.getId(), idMontador1);

		TMontadorMontaje tm = new TMontadorMontaje(idMontador1, servicio.getId());

		if (!daoMontaje.existeVinculacion(tm)) {
			daoMontaje.vincular(tm);
		}
		// }
		return true;

	}

	public boolean eliminarLinea(TLineaFactura linea, TServicio servicio) {

		if (facturaActual == null || linea == null || servicio == null)
			return false;

		facturaActual.getLineas().remove(linea);

		if (esMontaje(servicio)) {

			Integer idMontador = servicioAMontador.get(servicio.getId());

			if (idMontador != null) {

				TMontadorMontaje tm = new TMontadorMontaje(idMontador, servicio.getId());

				daoMontaje.desvincular(tm);
				servicioAMontador.remove(servicio.getId());
			}
		}

		return true;
	}

	@Override
	public boolean añadirServicioAVenta(TLineaFactura linea) {

		if (facturaActual == null || linea == null) {
			return false;
		}

		TLineaFactura existente = facturaActual.buscarLinea(linea.getIdProducto());

		// Si el producto ya está añadido, aumentamos la cantidad
		if (existente != null) {
			existente.setCantidad(existente.getCantidad() + linea.getCantidad());
		}
		// De lo contrario, añadimos la nueva linea de factura
		else {
			facturaActual.addLinea(linea);
		}
		return true;
	}

	@Override
	public ResultadoEliminarLinea eliminarServicioDeVenta(TLineaFactura linea) {

		if (facturaActual == null || linea == null) {
			return ResultadoEliminarLinea.ERROR;
		}

		TLineaFactura existente = facturaActual.buscarLinea(linea.getIdProducto());

		// Si el producto no existe, delvolvemos false
		if (existente == null) {
			return ResultadoEliminarLinea.NO_EXISTE;
		}
		// Calculamos la nueva cantidad tras restar
		int nuevaCantidad = existente.getCantidad() - linea.getCantidad();

		// si necesario se borra la linea devolviendo el resultado correspondiente
		if (nuevaCantidad < 0) {
			facturaActual.removeLinea(existente);
			return ResultadoEliminarLinea.BORRADO_DE_MAS;
		} else if (nuevaCantidad == 0) {
			facturaActual.removeLinea(existente);
		} else {
			existente.setCantidad(nuevaCantidad);
		}
		return ResultadoEliminarLinea.OK;
	}

	@Override
	public int cerrarVenta(TFactura factura) {

		if (facturaActual == null)
			return Eventos.RES_CERRAR_VENTA_KO_NO_INICIADA; // venta no iniciada

		if (facturaActual.getLineas() == null || facturaActual.getLineas().isEmpty())
			return Eventos.RES_CERRAR_VENTA_KO_SIN_LINEAS;

		if (factura.getFecha() == null || factura.getFecha().isEmpty())
			return Eventos.RES_CERRAR_VENTA_KO_FECHA_INVALIDA;

		DAOCliente daoCliente = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		DAODescuento daoDescuento = FactoriaAbstractaIntegracion.getInstance().crearDAODescuento();

		TCliente cliente = daoCliente.read(factura.getIdCliente());
		if (cliente == null)
			return Eventos.RES_CERRAR_VENTA_KO_CLIENTE_NO_EXISTE;

		if (!cliente.isActivo())
			return Eventos.RES_CERRAR_VENTA_KO_CLIENTE_INACTIVO;

		int idDesc = factura.getIdDescuento();
		if (idDesc != 0) {

			TDescuento descuento = daoDescuento.read(idDesc);

			if (descuento == null)
				return Eventos.RES_CERRAR_VENTA_KO_DESCUENTO_NO_EXISTE;

			if (!descuento.isActivo())
				return Eventos.RES_CERRAR_VENTA_KO_DESCUENTO_INACTIVO;
		}

		facturaActual.setIdCliente(factura.getIdCliente());
		facturaActual.setIdDescuento(factura.getIdDescuento());
		facturaActual.setFecha(factura.getFecha());

		double total = 0;

		for (TLineaFactura l : facturaActual.getLineas()) {
			total += l.getCantidad() * l.getPrecioUnitario();
		}

		facturaActual.setTotal(total);
		facturaActual.setCerrada(true);

		DAOFactura dao = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		DAOLineaFactura daoLinea = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();

		int id = dao.crear(facturaActual);
		facturaActual.setId(id);

		for (TLineaFactura l : facturaActual.getLineas()) {
			l.setIdFactura(id);
			daoLinea.crear(l);
		}

		facturaActual = null;
		servicioAMontador.clear();

		return id;
	}

	@Override
	public TFactura mostrarPorId(int idFactura) {

		TFactura factura = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura().leerPorId(idFactura);

		if (factura == null) {
			return null;
		}

		factura.setLineas(FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura().leerPorFactura(idFactura));

		return factura;
	}

	@Override
	public List<TFactura> mostrarTodas() {

		List<TFactura> facturas = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura().leerTodas();
		DAOLineaFactura linea = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		for (TFactura f : facturas) {
			f.setLineas(linea.leerPorFactura(f.getId()));
		}
		return facturas;
	}

	@Override
	public List<TFactura> mostrarPorCliente(int idCliente) {

		List<TFactura> facturas = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura()
				.leerPorCliente(idCliente);

		DAOLineaFactura linea = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		for (TFactura f : facturas) {
			f.setLineas(linea.leerPorFactura(f.getId()));
		}
		return facturas;
	}

	// Funciones Auxiliares

	private boolean esMontaje(TServicio servicio) {
		return servicio.getTipo() != null && servicio.getTipo() == 2;
	}

	private Integer obtenerMontadorDisponible() {

		for (TEmpleado e : daoEmpleado.readAll()) {

			if (e.getTipo() == 2 && e.isActivo()) {
				return e.getId();
			}
		}

		return null;
	}
}
