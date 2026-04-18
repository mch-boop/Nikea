package integracion.factoria;

import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOEmpleadoImp;
import integracion.empleado.DAOMontadorMontaje;
import integracion.empleado.DAOMontadorMontajeImp;

public class FactoriaIntegracion extends FactoriaAbstractaIntegracion {
	@Override
    public DAOEmpleado generarDAOEmpleado() {
        return new DAOEmpleadoImp();
    }
    
    @Override
    public DAOMontadorMontaje generarDAOMontadorMontaje() {
        return new DAOMontadorMontajeImp();
    }
}
