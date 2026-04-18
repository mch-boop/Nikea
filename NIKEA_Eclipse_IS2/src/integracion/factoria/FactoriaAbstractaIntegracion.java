package integracion.factoria;

import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOMontadorMontaje;

public abstract class FactoriaAbstractaIntegracion {
	private static FactoriaAbstractaIntegracion instancia;

    public static FactoriaAbstractaIntegracion getInstance() {
        if (instancia == null) {
            instancia = new FactoriaIntegracion();
        }
        return instancia;
    }

    public abstract DAOEmpleado generarDAOEmpleado();
    public abstract DAOMontadorMontaje generarDAOMontadorMontaje();
}
