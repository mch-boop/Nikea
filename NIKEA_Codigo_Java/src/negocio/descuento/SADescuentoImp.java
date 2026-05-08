package negocio.descuento;

import integracion.factoria.FactoriaIntegracion;
import integracion.descuento.DAODescuento;

import java.util.ArrayList;
import java.util.Collection;

public class SADescuentoImp implements SADescuento {

	@Override
	public int create(TDescuento td) {
	    DAODescuento dao = FactoriaIntegracion.getInstance().crearDAODescuento();

	    // Validaciones
	    if (td.getCodigo() == null || td.getCodigo().trim().isEmpty())
	        return -3;
	    if (td.getPorcentaje() <= 0 || td.getPorcentaje() > 100)
	        return -4;

	    TDescuento existente = dao.readByCodigo(td.getCodigo());

	    // No existe -> alta normal
	    if (existente == null) {
	        return dao.create(td);
	    }

	    // Existe activo -> duplicado
	    if (existente.isActivo()) {
	        return -1;
	    }

	    // Existe inactivo -> posible reactivación
	    return -2;
	}

	@Override
	public int reactivate(TDescuento td) {
	    DAODescuento dao = FactoriaIntegracion.getInstance().crearDAODescuento();

	    if (td.getCodigo() == null || td.getCodigo().trim().isEmpty()) return -3;
	    if (td.getPorcentaje() <= 0 || td.getPorcentaje() > 100) return -4;

	    TDescuento existente = dao.readByCodigo(td.getCodigo());

	    if (existente == null || existente.isActivo()) {
	        return -1; // no existe o ya activo
	    }

	    // Actualizamos datos nuevos
	    existente.setActivo(true);
	    existente.setNombre(td.getNombre());
	    existente.setPorcentaje(td.getPorcentaje());
	    existente.setTipo(td.isTipo());
	    if (td.isTipo()) {
	        if (td.getImporteMin() != null) {
	            existente.setImporteMin(td.getImporteMin());
	        }
	    } else {
	        if (td.getProductosMin() != null) {
	            existente.setProductosMin(td.getProductosMin());
	        }
	    }

	    dao.update(existente);

	    return existente.getId();
	}
	
	@Override
	public TDescuento readByCodigo(String codigo) {
	    Collection<TDescuento> todos = readAll(); 
	    
	    for (TDescuento td : todos) {
	        if (td.getCodigo().equalsIgnoreCase(codigo)) {
	            return td;
	        }
	    }
	    return null; // Si no lo encuentra
	}

	@Override
	public int update(TDescuento td) {
		DAODescuento dao = FactoriaIntegracion.getInstance().crearDAODescuento();
		if (td.getCodigo() == null || td.getCodigo().trim().isEmpty())
			return -3;
		if (td.getPorcentaje() <= 0 || td.getPorcentaje() > 100)
			return -4;

		TDescuento existente = dao.read(td.getId());
		if (existente == null || !existente.isActivo()) {
			return -1;
		}

		return dao.update(td);
	}

	@Override
	public TDescuento read(int id) {
		TDescuento td = FactoriaIntegracion.getInstance().crearDAODescuento().read(id);

		if (td != null && !td.isActivo()) {
			return null;
		}
		return td;
	}

	@Override
	public Collection<TDescuento> readAll() {
		Collection<TDescuento> todos = FactoriaIntegracion.getInstance().crearDAODescuento().readAll();
		Collection<TDescuento> activos = new ArrayList<>();

		if (todos != null) {
			for (TDescuento d : todos) {
				if (d.isActivo())
					activos.add(d);
			}
		}

		return activos.isEmpty() ? null : activos;
	}

	@Override
	public int delete(int id) {
		DAODescuento dao = FactoriaIntegracion.getInstance().crearDAODescuento();
		TDescuento td = dao.read(id);

		if (td != null && td.isActivo()) {
			td.setActivo(false);
			return dao.update(td);
		}
		return -1;
	}
}