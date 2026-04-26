package integracion.factoria;

import integracion.cliente.DAOCliente;
import integracion.cliente.DAOClienteImp;
import integracion.marca.DAOMarca;
import integracion.factura.DAOFacturaImp;
import integracion.factura.DAOFactura;
import integracion.descuento.DAODescuentoImp;
import integracion.descuento.DAODescuento;
import integracion.marca.DAOMarcaImp;
import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOEmpleadoImp;
import integracion.empleado.DAOMontadorMontaje;
import integracion.empleado.DAOMontadorMontajeImp;
import integracion.servicio.DAOMontaje;
import integracion.servicio.DAOMontajeImp;

public class FactoriaIntegracion extends FactoriaAbstractaIntegracion {
	
	@Override
    public DAOEmpleado crearDAOEmpleado() {
        return new DAOEmpleadoImp();
    }
    
    @Override
    public DAOMontadorMontaje crearDAOMontadorMontaje() {
        return new DAOMontadorMontajeImp();
    }

	@Override
	public DAOMontaje crearDAOMontaje() {
		return new DAOMontajeImp();
	}

	@Override
	public DAOCliente crearDAOCliente() {
		return new DAOClienteImp();
	}
	
	@Override
	public DAOMarca crearDAOMarca() {
		return new DAOMarcaImp();
	}

	@Override
	public DAODescuento crearDAODescuento() {
		return new DAODescuentoImp();
	}

	@Override
	public DAOFactura crearDAOFactura() {
		return new DAOFacturaImp();
	}
}
