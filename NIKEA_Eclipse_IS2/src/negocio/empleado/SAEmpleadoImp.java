package negocio.empleado;

import integracion.factoria.FactoriaIntegracion;
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

	    /*
	     * Códigos de retorno:
	     * > 0  -> Alta correcta / Reactivación automática exitosa
	     * -1   -> Ya existe activo con los MISMOS datos (mismo nombre/apellido)
	     * -100 -> Ya existe activo pero con DISTINTOS datos (DNI de otra persona)
	     * -2   -> Existe inactivo pero los datos no coinciden (pedir confirmación de reactivación)
	     * -3   -> Existe inactivo, mismos datos pero distinto TIPO (vendedor/montador)
	     * -300 -> Existe activo, mismos datos pero distinto TIPO (vendedor/montador)
	     */

	    // CASO 1: NO EXISTE EN EL SISTEMA 
	    if (existente == null) {
	        return dao.create(te);
	    }

	    // COMPARACIÓN DE DATOS
	    boolean mismoNombre = existente.getNombre() != null &&
	                          existente.getNombre().trim().equalsIgnoreCase(te.getNombre().trim());

	    boolean mismoApellido = ((existente.getApellido() == null && te.getApellido() == null) ||
	                            (existente.getApellido() != null && te.getApellido() != null &&
	                             existente.getApellido().trim().equalsIgnoreCase(te.getApellido().trim())));

	    boolean mismosDatosPersonales = mismoNombre && mismoApellido;
	    
	    // CASO 2: EL EMPLEADO EXISTE PERO ESTÁ INACTIVO
	    if (!existente.isActivo()) {
	        
	        // Mismos datos pero distinto tipo (No se puede reactivar cambiando el rol directamente)
	        if (mismosDatosPersonales && existente.getTipo() != te.getTipo()) {
	            return -3;
	        }

	        // Mismos datos + mismo tipo -> Reactivación automática
	        if (mismosDatosPersonales) {
	            existente.setActivo(true);
	            existente.setSueldo(te.getSueldo()); 
	            // Usamos el método update del DAO para persistir los cambios del objeto recuperado
	            return dao.update(existente);
	        }

	        // Existe inactivo pero con datos distintos (Nombre/Apellido no coinciden)
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
	            return -100; // Es otro empleado 
	        }
	    }

	    return -1; // Fallback de seguridad
	}
	
	@Override
	public int delete(int id) {
		DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado();
        TEmpleado te = dao.read(id);        
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
	
	public int readToDelete(Integer id) {
		DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado();
	    TEmpleado te = dao.read(id);
	    
	    if (te == null) return -3;      // No existe
	    if (!te.isActivo()) return -4; // Ya está inactivo
	    
	    return 1; // Es apto
	}
	
	@Override
	public TEmpleado readActive(int id) {
	    DAOEmpleado dao = FactoriaIntegracion.getInstance().crearDAOEmpleado();
	    TEmpleado te = dao.read(id);
	    
	    // Para las vistas de consulta/modificación, un empleado que no está activo "no existe".
	    if (te != null && te.isActivo()) {
	        return te;
	    }
	    
	    return null;
	}
	
}
