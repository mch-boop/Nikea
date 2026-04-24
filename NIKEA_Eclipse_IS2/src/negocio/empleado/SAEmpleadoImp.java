package negocio.empleado;

import integracion.factoria.FactoriaIntegracion;
import integracion.servicio.DAOMontaje;
import negocio.servicio.TMontaje;
import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOMontadorMontaje; // DAO para la tabla intermedia M:N
import java.util.Collection;

public class SAEmpleadoImp implements SAEmpleado {

	// Métodos de CU Básicos
	
	@Override
	public int create(TEmpleado te) {
		
		DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado(); 
		
		// Buscamos si ya existe el DNI en el sistema
		TEmpleado existente = dao.readByDNI(te.getDNI());
		
		if(existente == null) { // No existe, es un alta nueva
			return dao.create(te); 
		}
		else {
			if(!existente.isActivo()) { // Existe pero está inactivo (borrado lógico) -> Reactivamos
				existente.setActivo(true);
				existente.setNombre(te.getNombre());
				existente.setApellido(te.getApellido());
				existente.setSueldo(te.getSueldo());
				// No cambiamos el ID original
				dao.update(existente); 
				return existente.getId();
			}
			else { // Existe y ya está activo
				return -1; 
			}
		}
	}

	@Override
	public int delete(int id) {
		DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado();
        TEmpleado te = dao.read(id);
        
        if (te == null) return -3; // No existe
        if (!te.isActivo()) return -4; // Ya está inactivo
        
        te.setActivo(false); // BORRADO LÓGICO
        return dao.update(te); // Persistimos el cambio en el JSON
	}

	@Override
	public int update(TEmpleado te) {
		DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado();
        TEmpleado existente = dao.read(te.getId());
        if (existente != null && existente.isActivo()) {
            // Campos opcionales
            if (te.getNombre() != null) existente.setNombre(te.getNombre());
            if (te.getApellido() != null) existente.setApellido(te.getApellido());
            if (te.getSueldo() != -1.0) existente.setSueldo(te.getSueldo());
            
            return dao.update(existente);
        }
        return -1;
	}

	@Override
	public TEmpleado read(int id) {
		DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado();
        return dao.read(id);
	}

	@Override
	public Collection<TEmpleado> readAll() {
		DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado();
        return dao.readAll();
	}

	// Métodos para la relación M:N (Montador-Montaje)
	
	/*
	 * -1: Error de Empleado.
	 * -2: Error de Rol (No es montador).
	 * -3: Error de Montaje.
	 * -4: Ya vinculado.
	 */
	
	@Override
	public int vincularMontadorAMontaje(TMontadorMontaje tmm) {
	    DAOEmpleado daoEmp = FactoriaIntegracion.getInstance().crearDAOEmpleado();
	    DAOMontaje daoMontaje = FactoriaIntegracion.getInstance().crearDAOMontaje();
	    DAOMontadorMontaje daoMN = FactoriaIntegracion.getInstance().crearDAOMontadorMontaje();

	    // COMPROBACIÓN: Existe el empleado y es un Montador activo?
	    TEmpleado emp = daoEmp.read(tmm.getIdMontador());
	    if (emp == null || !emp.isActivo()) {
	        return -1; // Error: El empleado no existe o está inactivo
	    }
	    
	    // Solo los Montadores (Tipo 2) pueden vincularse a un montaje
	    if (emp.getTipo() != 2) {
	        return -2; // Error: El empleado existe pero no es un montador (es vendedor)
	    }

	    // COMPROBACIÓN: ¿Existe el montaje y está activo/disponible?
	    TMontaje montaje = daoMontaje.read(tmm.getIdMontaje());
	    if (montaje == null) {
	        return -3; // Error: El montaje no existe
	    }

	    // COMPROBACIÓN: ¿Ya están vinculados? (Evitar duplicados en el JSON)
	    if (daoMN.existeVinculacion(tmm)) {
	        return -4; // Error: Esta vinculación ya existe
	    }

	    return daoMN.vincular(tmm);
	}

	@Override
	public int desvincularMontadorDeMontaje(TMontadorMontaje tmm) {
	    DAOMontadorMontaje daoMN = FactoriaIntegracion.getInstance().crearDAOMontadorMontaje();

	    // COMPROBACIÓN: ¿Existe la vinculación antes de intentar borrarla?
	    if (!daoMN.existeVinculacion(tmm)) {
	        return -1; // Error: No se puede desvincular lo que no está unido
	    }

	    return daoMN.desvincular(tmm);
	}
	
}
