package negocio.empleado;

import integracion.factoria.FactoriaIntegracion;
import integracion.servicio.DAOMontaje;
import negocio.servicio.TMontaje;
import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOMontadorMontaje; // DAO para la tabla intermedia M:N
import java.util.Collection;

public class SAEmpleadoImp implements SAEmpleado {
	
	private TEmpleado ultimoDuplicado;

	// Métodos de CU Básicos
	
	@Override
	public int create(TEmpleado te) {
		
	    DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado(); 
	    
	    // Buscamos si ya existe el DNI en el sistema
	    TEmpleado existente = dao.readByDNI(te.getDNI());
	    
	    this.ultimoDuplicado = null;

	    /*
	     * Códigos de retorno:
	     * > 0  -> Alta correcta / Reactivación automática exitosa
	     * -1   -> Ya existe activo con los MISMOS datos (mismo nombre/apellido)
	     * -100 -> Ya existe activo pero con DISTINTOS datos (DNI de otra persona)
	     * -2   -> Existe inactivo pero los datos no coinciden (pedir confirmación de reactivación)
	     * -3  -> Existe inactivo, mismos datos pero distinto TIPO (vendedor/montador)
	     * -300 -> Existe activo, mismos datos pero distinto TIPO (vendedor/montador)
	     */

	    // CASO 1: NO EXISTE EN EL SISTEMA 
	    if (existente == null) {
	        return dao.create(te);
	    }
	    
	    // Si existe (activo o inactivo), lo guardamos para que el Controlador y Vista lo consulten
	    this.ultimoDuplicado = existente;

	    // COMPARACIÓN DE DATOS
	    boolean mismoNombre = existente.getNombre() != null &&
	                          existente.getNombre().trim().equalsIgnoreCase(te.getNombre().trim());

	    boolean mismoApellido = ((existente.getApellido() == null && te.getApellido() == null) ||
	                            (existente.getApellido() != null && te.getApellido() != null &&
	                             existente.getApellido().trim().equalsIgnoreCase(te.getApellido().trim())));

	    boolean mismosDatosPersonales = mismoNombre && mismoApellido;
	    

	    // CASO 2: EL EMPLEADO EXISTE PERO ESTÁ INACTIVO
	    if (!existente.isActivo()) {
	        
	        // A) Mismos datos pero distinto tipo (No se puede reactivar cambiando el rol directamente)
	        if (mismosDatosPersonales && existente.getTipo() != te.getTipo()) {
	            return -3;
	        }

	        // B) Mismos datos + mismo tipo -> Reactivación automática
	        if (mismosDatosPersonales) {
	            existente.setActivo(true);
	            existente.setSueldo(te.getSueldo()); // Actualizamos el sueldo al nuevo valor
	            
	            // Usamos el método update del DAO para persistir los cambios del objeto recuperado
	            return dao.update(existente);
	        }

	        // C) Existe inactivo pero con datos distintos (Nombre/Apellido no coinciden)
	        // Devolvemos -2 para que la vista pida confirmación para "pisar" los datos antiguos
	        return -2;
	    }

	    // CASO 3: EL EMPLEADO YA EXISTE Y ESTÁ ACTIVO
	    if (existente.isActivo()) {
	    	// Mismos datos pero distinto tipo (No se puede reactivar cambiando el rol directamente)
			    if (mismosDatosPersonales && existente.getTipo() != te.getTipo()) {
		            return -300;
		        } 
			    
		        if (mismosDatosPersonales) {
		            return -1;   // Es el mismo empleado 
		        } else {
		            return -100; // Es otro empleado (Aviso: "DNI registrado a nombre de...")
		        }
	    }

	    return -1; // Fallback de seguridad
	}
	
	@Override
    public TEmpleado getUltimoDuplicado() {
        return this.ultimoDuplicado;
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
	        
	        // COMPROBAR CAMBIO DE TIPO
	        // Si el tipo de 'te' es distinto al de 'existente', usamos la nueva instancia 'te'
	        if (te.getTipo() != null && !te.getTipo().equals(existente.getTipo())) {
	            // Rellenamos los huecos de 'te' con lo que ya había en 'existente' si vienen vacíos
	            if (te.getNombre() == null) te.setNombre(existente.getNombre());
	            if (te.getApellido() == null) te.setApellido(existente.getApellido());
	            if (te.getSueldo() == -1.0) te.setSueldo(existente.getSueldo());
	            
	            te.setDNI(existente.getDNI());
	            te.setActivo(existente.isActivo());

	            // Guardamos directamente la nueva instancia 'te' (que es TVendedor o TMontador)
	            return dao.update(te);
	        } 
	        else {
	            // SI EL TIPO ES EL MISMO, seguimos con tu lógica habitual
	            if (te.getNombre() != null) existente.setNombre(te.getNombre());
	            if (te.getApellido() != null) existente.setApellido(te.getApellido());
	            if (te.getSueldo() != -1.0) existente.setSueldo(te.getSueldo());
	            
	            // Si es vendedor y el tipo no cambió, actualizamos también sus ventas
	            if (existente.getTipo() == 1 && te instanceof TVendedor) {
	                int nuevasVentas = ((TVendedor) te).getNumeroVentas();
	                if (nuevasVentas != -1) {
	                    ((TVendedor) existente).setNumeroVentas(nuevasVentas);
	                }
	            }

	            return dao.update(existente);
	        }
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
	
	@Override
	public int reactivate(TEmpleado tEmpleado) {
	    int res = -1;
	    DAOEmpleado daoEmpleado = FactoriaIntegracion.getInstance().crearDAOEmpleado();
	    
	    // Leemos el empleado que ya existe por su DNI
	    TEmpleado empleadoExistente = daoEmpleado.readByDNI(tEmpleado.getDNI());
	    
	    if (empleadoExistente != null) {
	        // Verificamos que sea del mismo tipo (No se puede reactivar un Vendedor como Montador)
	        if (empleadoExistente.getTipo() == tEmpleado.getTipo()) {
	            
	            // Actualizamos los datos del empleado existente con los nuevos del formulario
	            empleadoExistente.setNombre(tEmpleado.getNombre());
	            empleadoExistente.setApellido(tEmpleado.getApellido());
	            empleadoExistente.setSueldo(tEmpleado.getSueldo());
	            
	            // Cambiamos el estado a ACTIVO
	            empleadoExistente.setActivo(true);
	            
	            // Persistimos los cambios en el JSON a través del DAO
	            // El método update del DAO busca por ID y sobreescribe
	            res = daoEmpleado.update(empleadoExistente);
	            
	            // Si el update fue bien, devolvemos el ID del empleado reactivado
	            if (res > 0) {
	                res = empleadoExistente.getId();
	            }
	        } else {
	            // Error: El tipo no coincide (Caso -3) 
	            res = -3;
	        }
	    }
	    
	    return res;
	}
	
}
