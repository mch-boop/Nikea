package integracion.factoria;

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
}
