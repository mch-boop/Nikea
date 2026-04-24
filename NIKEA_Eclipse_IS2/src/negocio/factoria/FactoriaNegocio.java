package negocio.factoria;

import negocio.cliente.SACliente;
import negocio.cliente.SAClienteImp;
import negocio.descuento.SADescuento;
import negocio.descuento.SADescuentoImp;
import negocio.empleado.SAEmpleado;
import negocio.empleado.SAEmpleadoImp;
import negocio.factura.SAFactura;
import negocio.factura.SAFacturaImp;
import negocio.marca.SAMarca;
import negocio.marca.SAMarcaImp;
import negocio.servicio.SAServicio;
import negocio.servicio.SAServicioImp;

public class FactoriaNegocio extends FactoriaAbstractaNegocio {

	// MÉTODOS DE LA INTERFAZ
	
	@Override
	public SACliente crearSACliente() {
		return new SAClienteImp();
	}

	@Override
	public SADescuento crearSADescuento() {
		return new SADescuentoImp();
	}

	@Override
	public SAEmpleado crearSAEmpleado() {
		return new SAEmpleadoImp();
	}

	@Override
	public SAFactura crearSAFactura() {
		return new SAFacturaImp();
	}

	@Override
	public SAMarca crearSAMarca() {
		return new SAMarcaImp();
	}

	@Override
	public SAServicio crearSAServicio() {
		return new SAServicioImp();
	}

}
