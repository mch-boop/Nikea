package negocio.cliente;

import java.util.Collection;
import java.util.List;

import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import negocio.factura.TFactura;

public class SAClienteImp implements SACliente {

	// Atributo.
	private TCliente ultimoDuplicado;

	// MÉTODOS DE LA INTERFAZ
	
	@Override
	public int create(TCliente tc) {
		// Obtenemos el DAO.
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente(); 
		
		// Buscamos si ya existe el DNI en el sistema
		TCliente existente = dao.readByDNI(tc.getDNI());
		
		/*
	     * Códigos de retorno:
	     * > 0  -> Alta correcta / Reactivación automática exitosa
	     * -1   -> Ya existe activo con los MISMOS datos (mismo nombre/apellido)
	     * -100 -> Ya existe activo pero con DISTINTOS datos (DNI de otra persona)
	     * -2   -> Existe inactivo pero los datos no coinciden (pedir confirmación de reactivación)
	     */
		
		// Caso 1: no existe en el sistema, alta en el sistema.
		if (existente == null) { 
			return dao.create(tc); 
		}
		
		// Si existe (activo o inactivo), lo guardamos para que el Controlador/Vista lo consulten
	    this.ultimoDuplicado = existente;

	    // --- LÓGICA DE COMPARACIÓN DE DATOS ---
	    boolean mismoNombre = existente.getNombre() != null &&
	                          existente.getNombre().trim().equalsIgnoreCase(tc.getNombre().trim());

	    boolean mismoApellido = ((existente.getApellidos() == null && tc.getApellidos() == null) ||
	                            (existente.getApellidos() != null && tc.getApellidos() != null &&
	                             existente.getApellidos().trim().equalsIgnoreCase(tc.getApellidos().trim())));

	    boolean mismosDatosPersonales = mismoNombre && mismoApellido;
	    

	    // --- CASO 2: EL CLIENTE EXISTE PERO ESTÁ INACTIVO (BORRADO LÓGICO) ---
	    if (!existente.isActivo()) {

	        // Mismos datos -> Reactivación automática
	        if (mismosDatosPersonales) {
	            existente.setActivo(true);
	            existente.setTelefono(tc.getTelefono()); // Actualizamos el teléfono al nuevo valor
	            
	            dao.update(existente);
	            return existente.getId();
	        }

	        // Existe inactivo pero con datos distintos (Nombre/Apellido no coinciden)
	        // Devolvemos -2 para que la vista pida confirmación para "pisar" los datos antiguos
	        return -2;
	    }

	    // --- CASO 3: EL CLIENTE YA EXISTE Y ESTÁ ACTIVO ---
	    if (existente.isActivo()) {
	    	if (mismosDatosPersonales) {
	            return -1;   // Es el mismo cliente (Aviso: "Este cliente ya existe")
	        } else {
	            return -100; // Es otro cliente (Aviso: "DNI registrado a nombre de...")
	        }
	    }

	    return -1; // Fallback de seguridad
	}

	@Override
    public TCliente getUltimoDuplicado() {
        return this.ultimoDuplicado;
    }
	
	@Override
	public TCliente read(int id) {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		return dao.read(id);
	}

	@Override
	public int update(TCliente tc) {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		TCliente existente = dao.read(tc.getId());
		
		if (existente != null && existente.isActivo()) {
            if (tc.getNombre() != null) existente.setNombre(tc.getNombre());
            if (tc.getApellidos() != null) existente.setApellidos(tc.getApellidos());
            if (tc.getTelefono() != -1.0) existente.setTelefono(tc.getTelefono());
            if (tc.getDNI() != null) existente.setDNI(tc.getDNI());
            
            return dao.update(existente);
	    }
	    return -1;
	}

	@Override
	public int delete(int id) {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
        TCliente tc = dao.read(id);
        
        if (tc == null) return -3; // No existe
        if (!tc.isActivo()) return -4; // Ya está inactivo
        
        // Caso existe y está activo.
        tc.setActivo(false); // BORRADO LÓGICO
        return dao.update(tc); // Persistimos el cambio en el JSON
	}

	@Override
	public Collection<TCliente> readAll() {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		return dao.readAll();
	}

	@Override
	public TCliente getMejorCliente() {
		// TOAFactura fact = FactoriaAbstractaIntegracion.getInstance().crearTOAFactura();
		// return fact.getMejorCliente();
		return null;
	}

}
