package negocio.empleado;

import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOMontadorMontaje;
import integracion.factoria.FactoriaIntegracion;
import integracion.servicio.DAOMontaje;
import negocio.servicio.TMontaje;

public class SAMontadorMontajeImp implements SAMontadorMontaje {

	@Override
	public int vincular(TMontadorMontaje tmm) {

	    if (tmm == null) return -3;

	    DAOEmpleado daoEmp = FactoriaIntegracion.getInstance().crearDAOEmpleado();
	    DAOMontadorMontaje daoMN = FactoriaIntegracion.getInstance().crearDAOMontadorMontaje();
	    DAOMontaje daoMontaje = FactoriaIntegracion.getInstance().crearDAOMontaje();
	    // 1. validar empleado
	    TEmpleado emp = daoEmp.read(tmm.getIdMontador());
	    if (emp == null || !emp.isActivo()) {
	        return -1;
	    }

	    if (emp.getTipo() != 2) { 
            return -4;
        }
	    
	    TMontaje montaje = daoMontaje.read(tmm.getIdMontaje());
        if (montaje == null) {
            return -5;
        }
        
	    // 2. evitar duplicado
	    if (daoMN.existeVinculacion(tmm)) {
	        return -2;
	    }

	    // 3. crear relación
	    return daoMN.vincular(tmm);
	}

	@Override
	public int desvincular(TMontadorMontaje tmm) {
		if (tmm == null) return -3;

	    DAOMontadorMontaje daoMN = FactoriaIntegracion.getInstance().crearDAOMontadorMontaje();

	    if (!daoMN.existeVinculacion(tmm)) {
	        return -1;
	    }

	    return daoMN.desvincular(tmm);
	}
	
}
