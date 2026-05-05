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
import negocio.empleado.TMontadorMontaje;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.servicio.SAServicio;
import negocio.servicio.TServicio;
import presentacion.controlador.Eventos;

public class SAFacturaImp implements SAFactura {
	private DAOMontadorMontaje daoMontaje = FactoriaAbstractaIntegracion.getInstance().crearDAOMontadorMontaje();
	private static TFactura facturaActual;
	private Map<Integer, Integer> servicioAMontador = new HashMap<>();
	private DAOEmpleado daoEmpleado = FactoriaAbstractaIntegracion.getInstance().crearDAOEmpleado();

	@Override
	public int iniciarVenta(TFactura factura) {

		if (factura == null)
			return Eventos.RES_INICIAR_VENTA_KO_GENERAL;

		if (factura.getIdVendedor() <= 0)
			return Eventos.RES_INICIAR_VENTA_KO_VENDEDOR_NO_EXISTE;

		DAOEmpleado daoEmpleado = FactoriaAbstractaIntegracion.getInstance().crearDAOEmpleado();

		TEmpleado emp = daoEmpleado.read(factura.getIdVendedor());

		if (emp == null)
			return Eventos.RES_INICIAR_VENTA_KO_VENDEDOR_NO_EXISTE;

		if (!emp.isActivo())
			return Eventos.RES_INICIAR_VENTA_KO_VENDEDOR_INACTIVO;

		if (facturaActual != null)
			return Eventos.RES_INICIAR_VENTA_KO_YA_EN_CURSO;

		facturaActual = new TFactura();
		facturaActual.setIdVendedor(factura.getIdVendedor());
		facturaActual.setLineas(new ArrayList<>());
		facturaActual.setCerrada(false);

		servicioAMontador.clear();

		return facturaActual.getId() + 1;

	}

	// Está hecha la lógica para vincular y desvincular montadores, queda el resto.
	@Override
	public boolean annadirLinea(TLineaFactura linea, TServicio servicio, int idMontador) {
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
	public int annadirServicioAVenta(TLineaFactura linea) {

		if (facturaActual == null || linea == null) {
			return -1;
		}

		if (linea.getIdProducto() <= 0 || linea.getCantidad() <= 0) {
			return 0;
		}

		TServicio servicio = FactoriaAbstractaIntegracion.getInstance().crearDAOServicio().read(linea.getIdProducto());

		if (servicio == null) {
			return -2;
		}

		if (!servicio.isActivo()) {
			return -3;
		}

		if (servicio.getPrecioActual() == null || servicio.getPrecioActual() < 0) {
			return -4;
		}

		TLineaFactura existente = facturaActual.buscarLinea(linea.getIdProducto());

		// Si el producto ya está añadido, aumentamos la cantidad
		if (existente != null) {
			existente.setCantidad(existente.getCantidad() + linea.getCantidad());
		}
		// De lo contrario, añadimos la nueva linea de factura
		else {
			TLineaFactura nuevaLinea = new TLineaFactura();
			nuevaLinea.setIdProducto(servicio.getId());
			nuevaLinea.setCantidad(linea.getCantidad());
			nuevaLinea.setPrecioUnitario(servicio.getPrecioActual());

			facturaActual.addLinea(nuevaLinea);
		}
		return 1;
	}

	@Override
	public int eliminarServicioDeVenta(TLineaFactura linea) {

		if (facturaActual == null || linea == null) {
			return -1;
		}

		if (linea.getIdProducto() <= 0 || linea.getCantidad() <= 0) {
			return 0;
		}

		TLineaFactura existente = facturaActual.buscarLinea(linea.getIdProducto());

		// Si el producto no existe, delvolvemos false
		if (existente == null) {
			return -2;
		}
		// Calculamos la nueva cantidad tras restar
		int nuevaCantidad = existente.getCantidad() - linea.getCantidad();

		// si necesario se borra la linea devolviendo el resultado correspondiente
		if (nuevaCantidad < 0) {
			return -3;
		} else if (nuevaCantidad == 0) {
			facturaActual.removeLinea(existente);
		} else {
			existente.setCantidad(nuevaCantidad);
		}
		return 1;
	}

	@Override
	public int cerrarVenta(TFactura factura) {

		if (facturaActual == null)
			return Eventos.RES_CERRAR_VENTA_KO_NO_INICIADA; // venta no iniciada

		if (facturaActual.getLineas() == null || facturaActual.getLineas().isEmpty())
			return Eventos.RES_CERRAR_VENTA_KO_SIN_LINEAS;

		if (factura.getFecha() == null)
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

		int id = dao.create(facturaActual);
		facturaActual.setId(id);

		for (TLineaFactura l : facturaActual.getLineas()) {
			l.setIdFactura(id);
			daoLinea.create(l);
		}
//para obtener el mejor articulo en sevicios
		SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();
		saServicio.getMejorArticulo();

		facturaActual = null;
		servicioAMontador.clear();

		return id;
	}

	@Override
	public TFactura mostrarPorId(int idFactura) {

		if (idFactura <= 0) {
			return null;
		}

		TFactura factura = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura().read(idFactura);

		if (factura == null) {
			return null;
		}

		factura.setLineas(FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura().read(idFactura));

		return factura;
	}

	@Override
	public List<TFactura> mostrarTodas() {

		return FactoriaAbstractaIntegracion.getInstance().crearDAOFactura().readAll();
	}

	@Override
	public List<TFactura> mostrarPorCliente(int idCliente) {

		if (idCliente <= 0) {
			return null;
		}

		TCliente cliente = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente().read(idCliente);
		if (cliente == null || !cliente.isActivo()) {
			return null;
		}

		List<TFactura> facturas = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura()
				.readByClient(idCliente);

		DAOLineaFactura linea = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		for (TFactura f : facturas) {
			f.setLineas(linea.read(f.getId()));
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
	
	@Override
	public int annadirDescuento(int idFactura, int idDescuento) {
		if (idFactura <= 0 || idDescuento <= 0) {
			return -4;
		}

		TFactura factura = mostrarPorId(idFactura);
		if (factura == null) {
			return -1;
		}
		
		if (factura.getIdDescuento() > 0) {
			return -5; // RES_ANNADIR_DESCUENTO_FACTURA_KO_YA_TIENE_DESCUENTO
		}

		DAODescuento daoDescuento = FactoriaAbstractaIntegracion.getInstance().crearDAODescuento();
		TDescuento descuento = daoDescuento.read(idDescuento);
		
		if (descuento == null || !descuento.isActivo()) {
			return -2;
		}

		boolean cumpleRequisitos = false;

		if (descuento.isTipo()) {
			double importeTotal = factura.getImporte();
			if (importeTotal >= descuento.getCantidad()) {
				cumpleRequisitos = true;
			}
		} else {
			int cantidadProductos = 0;
			if (factura.getLineas() != null) {
				for (TLineaFactura lf : factura.getLineas()) {
					cantidadProductos += lf.getCantidad();
				}
			}
			if (cantidadProductos >= descuento.getCantidad()) {
				cumpleRequisitos = true;
			}
		}

		if (!cumpleRequisitos) {
			return -3;
		}

		factura.setIdDescuento(idDescuento);
		
		double importeBase = factura.getImporte();
		double cantidadDescontada = importeBase * ((double) descuento.getPorcentaje() / 100.0);
		factura.setTotal(importeBase - cantidadDescontada);

		DAOFactura daoFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		boolean actualizado = daoFactura.update(factura);

		if (actualizado) {
			return 1;
		} else {
			return -4;
		}
	}
	
	
	// Casos de uso extra
	
	@Override
	 public Map<String, Double> getVentasPorMarca() {
		System.out.println("Falta en SAFacturaImp");
	    return null;
	}
}
