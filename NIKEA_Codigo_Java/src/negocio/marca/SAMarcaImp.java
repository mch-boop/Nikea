package negocio.marca;

import java.util.Collection;

import integracion.marca.DAOMarca;
import integracion.servicio.DAOServicio;
import negocio.servicio.TArticulo;
import integracion.factoria.FactoriaAbstractaIntegracion;

public class SAMarcaImp implements SAMarca {

	@Override
	public int create(TMarca tm) {

		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca(); 
		
		// Buscamos si ya existe el nombre en el sistema
		TMarca existente = dao.readByNombre(tm.getNombre().trim());
		
		/*
		 * -1 	-> Ya existente y activa
		 * -100 -> Ya existente pero inactiva
		 */
		
        if (existente != null) {
            if (!existente.isActivo()) {
                // Reactivamos
                existente.setActivo(true);
                dao.update(existente); // Actualizo en el DAO para guardarlo en el archivo
                return -100;
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
		if (tm == null) return null;
		if (tm.isActivo()) return tm;
		else return null;
	}

	@Override
	public int update(TMarca tm) {
        DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();

        /*
         * -1 	-> No existe
         * -2 	-> Existe pero inactivo
         * -3	-> Nombre duplicado
         */
        
        TMarca existente = dao.read(tm.getId());
        if (existente == null) return -1; 
        if (!existente.isActivo()) return -2;

        // Comprobar nombre duplicado
        TMarca otra = dao.readByNombre(tm.getNombre().trim());
        if (otra != null && otra.getId() != tm.getId()) return -3;

        existente.setActivo(true);
        if (tm.getNombre().trim() != null) {
            existente.setNombre(tm.getNombre().trim());
        }
        if (tm.getEspecialidades() != null)
            existente.setEspecialidades(tm.getEspecialidades());
       
        return dao.update(existente);
	}

	@Override
	public int delete(int id) {
		DAOMarca dao = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();
		DAOServicio daoS = FactoriaAbstractaIntegracion.getInstance().crearDAOServicio();

	    TMarca tm = dao.read(id);
	    if (tm == null) return -1; // no existe o inactivo
	    if (!tm.isActivo()) return -1; // no existe o inactivo
	    if (!daoS.readAll().stream().filter(s -> s.getTipo() == 1 && s.isActivo()).map(s -> (TArticulo) s).filter(s -> s.getMarcaId() == id)
	    		.toList().isEmpty()) return -3; // todavía tiene artículos

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

	@Override
	public Collection<TMarca> readPorEspecialidad(TMarca.Especialidad esp) {
		return readAll().stream().filter(s -> s.getEspecialidades().contains(esp)).toList();
	}
}
