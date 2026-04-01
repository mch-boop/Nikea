package negocio.factoria;

import negocio.cliente.SACliente;
import negocio.descuento.SADescuento;
import negocio.empleado.SAEmpleado;
import negocio.factura.SAFactura;
import negocio.marca.SAMarca;
import negocio.servicio.SAServicio;

public abstract class FactoriaAbstractaNegocio {
	
	private static FactoriaAbstractaNegocio instancia = null;

	public static FactoriaAbstractaNegocio getInstance() {
		if (instancia == null)
			instancia = new FactoriaNegocio();
		return instancia;
	}

	
	// MÉTODOS ABSTRACTOS
	
	public abstract SACliente crearSACliente();
	public abstract SADescuento crearSADescuento();
	public abstract SAEmpleado crearSAEmpleado();
	public abstract SAFactura crearSAFactura();
	public abstract SAMarca crearSAMarca();
	public abstract SAServicio crearSAServicio();
}
