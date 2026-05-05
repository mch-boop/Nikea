package negocio.cliente;

import java.util.Collection;

import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;

public class SAClienteImp implements SACliente {
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

	    // --- CASO 2: EL CLIENTE EXISTE PERO ESTÁ INACTIVO (BORRADO LÓGICO) ---
	    if (!existente.isActivo()) {

	        // Mismo DNI -> Reactivación automática
	        // Usamos el método update del DAO para persistir los cambios del objeto recuperado
	    	existente.setActivo(true);
            return dao.update(existente);
	    }

	    // --- CASO 3: CLIENTE CON ESE DNI ACTIVO ---
	    return -1; 
	}
	
	@Override
	public TCliente read(int id) {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		TCliente tc = dao.read(id);
		return (tc.isActivo() ? tc : null);
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
		Collection<TCliente> clientes = dao.readAll();
		return  (clientes.isEmpty() ? null : clientes);
	}
}
