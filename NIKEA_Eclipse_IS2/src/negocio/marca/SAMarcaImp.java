package negocio.marca;

import java.util.Collection;

import integracion.marca.DAOMarca;
import integracion.factoria.FactoriaAbstractaIntegracion;

public class SAMarcaImp implements SAMarca {

	@Override
	public int create(TMarca tm) {

		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca(); 
		
		// Buscamos si ya existe el nombre en el sistema
		TMarca existente = dao.readByNombre(tm.getNombre());
		
        if (existente != null) {
            if (!existente.isActivo()) {
                // Reactivamos
                existente.setActivo(true);
                existente.setEspecialidades(tm.getEspecialidades());
                return dao.update(existente);
            }
            return -1; // ya existe activa
        }

        tm.setActivo(true);
        return dao.create(tm);
	}

	@Override
	public TMarca read(int id) {
		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();

	    TMarca tm = dao.read(id);
	    if (tm != null && tm.isActivo()) return tm;
	    return null;
	}

	@Override
	public int update(TMarca tm) {
        DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();

        TMarca existente = dao.read(tm.getId());
        if (existente == null || !existente.isActivo()) return -1;

        // Comprobar nombre duplicado
        TMarca otra = dao.readByNombre(tm.getNombre());
        if (otra != null && otra.getId() != tm.getId()) return -1;

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
	    if (tm == null || !tm.isActivo()) return -1;

	    tm.setActivo(false);
	    return dao.update(tm);
	}

	@Override
	public Collection<TMarca> readAll() {
		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();

	    return dao.readAll().stream()
	        .filter(TMarca::isActivo)
	        .toList();
	}

}
