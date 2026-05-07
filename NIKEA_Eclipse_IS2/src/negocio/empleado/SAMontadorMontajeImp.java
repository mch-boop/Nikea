package negocio.empleado;

import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOMontadorMontaje;
import integracion.factoria.FactoriaIntegracion;
import integracion.servicio.DAOServicio;
import negocio.servicio.TServicio;

public class SAMontadorMontajeImp implements SAMontadorMontaje {

	@Override
	public int vincular(TMontadorMontaje tmm) {

	    if (tmm == null) return -3;

	    DAOEmpleado daoEmp = FactoriaIntegracion.getInstance().crearDAOEmpleado();
	    DAOMontadorMontaje daoMN = FactoriaIntegracion.getInstance().crearDAOMontadorMontaje();
	    DAOServicio daoServicio = FactoriaIntegracion.getInstance().crearDAOServicio();
	    // 1. validar empleado
	    TEmpleado emp = daoEmp.read(tmm.getIdMontador());
	    if (emp == null || !emp.isActivo())
	        return -1;

	    if (emp.getTipo() != 2) 
            return -4;
	    
	    TServicio servicio = daoServicio.read(tmm.getIdMontaje());
	    if (servicio == null || servicio.getTipo() != 2) return -5;
     
        
	    // 2. evitar duplicado
	    if (daoMN.existeVinculacion(tmm))
	        return -2;

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
