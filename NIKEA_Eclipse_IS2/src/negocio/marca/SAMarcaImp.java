package negocio.marca;

import java.util.Collection;

import integracion.marca.DAOMarca;
import integracion.factoria.FactoriaAbstractaIntegracion;

public class SAMarcaImp implements SAMarca {

	private boolean reactivada;
	
	@Override
	public int create(TMarca tm) {

		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca(); 
		reactivada = false;
		
		// Buscamos si ya existe el nombre en el sistema
		TMarca existente = dao.readByNombre(tm.getNombre().trim());
		
        if (existente != null) {
            if (!existente.isActivo()) {
                // Reactivamos
                existente.setActivo(true);
                existente.setEspecialidades(tm.getEspecialidades());
                
                reactivada = true;
                return dao.update(existente);
            }
            return -1; // ya existe activa
        }

        tm.setActivo(true);
        return dao.create(tm);
	}
	
	public boolean isReactivada() { return reactivada; }

	@Override
	public TMarca read(int id) {
		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();
	    return dao.read(id);
	}

	@Override
	public int update(TMarca tm) {
        DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();

        TMarca existente = dao.read(tm.getId());
        if (existente == null) return -1; 
        if (!existente.isActivo()) return -2;

        // Comprobar nombre duplicado
        TMarca otra = dao.readByNombre(tm.getNombre());
        if (otra != null && otra.getId() != tm.getId()) return -3;

        tm.setActivo(true);
        existente.setNombre(tm.getNombre());
        existente.setEspecialidades(tm.getEspecialidades());
        return dao.update(tm);
	}
	
	@Override
	public Collection<TMarca> update_listar() {
	    DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();
	    return dao.readAll();
	}

	@Override
	public int delete(int id) {
		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();

	    TMarca tm = dao.read(id);
	    if (tm == null) return -1; // no existe
	    if (!tm.isActivo()) return -2; // ya inactivo

	    tm.setActivo(false);
	    return dao.update(tm);
	}

	@Override
	public Collection<TMarca> readAll() {
		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();
	    return dao.readAll();
	}

}
