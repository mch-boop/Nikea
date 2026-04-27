package negocio.factura;

import java.util.ArrayList;
import java.util.List;

import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.factura.DAOLineaFactura;

public class SAFacturaImp implements SAFactura {

	private TFactura facturaActual;

	@Override
	public boolean iniciarVenta(TFactura factura) {

		if (factura == null || factura.getIdVendedor() <= 0)
			return false;

		facturaActual = new TFactura();
		facturaActual.setIdVendedor(factura.getIdVendedor());
		facturaActual.setLineas(new ArrayList<>());
		facturaActual.setCerrada(false);

		return true;
	}

	@Override
	public int cerrarVenta(TFactura factura) {

		if (facturaActual == null)
			return -1;

		DAOFactura dao = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		DAOLineaFactura daoLinea = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		facturaActual.setIdCliente(factura.getIdCliente());
		facturaActual.setIdDescuento(factura.getIdDescuento());
		facturaActual.setFecha(factura.getFecha());

		double total = 0;

		for (TLineaFactura l : facturaActual.getLineas()) {
			total += l.getCantidad() * l.getPrecioUnitario();
		}

		facturaActual.setTotal(total);
		facturaActual.setCerrada(true);

		int id = dao.crear(facturaActual);
		facturaActual.setId(id);

		for (TLineaFactura l : facturaActual.getLineas()) {
			l.setIdFactura(id);
			daoLinea.crear(l);
		}

		facturaActual = null;

		return id;
	}

	@Override
	public List<TFactura> mostrarTodas() {

		return FactoriaAbstractaIntegracion.getInstance().crearDAOFactura().leerTodas();
	}
}
