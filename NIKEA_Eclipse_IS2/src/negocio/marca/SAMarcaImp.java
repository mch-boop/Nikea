package negocio.marca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import integracion.marca.DAOMarca;
import integracion.servicio.DAOServicio;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.SAFactura;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.servicio.SAServicio;
import negocio.servicio.TArticulo;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;

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

        existente.setActivo(true);
        if (tm.getNombre() != null)
            existente.setNombre(tm.getNombre());
        if (tm.getEspecialidades() != null)
            existente.setEspecialidades(tm.getEspecialidades());
       
        return dao.update(existente);
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
	    if (tm.getListaArticulos().isEmpty()) return -3; // todavía tiene artículos

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

	
	// CASOS DE USO EXTRA
	
	@Override
	public List<TMarca> getTop5Marcas() {
		SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
	    Map<String, Double> ventas = saFactura.getVentasPorMarca();
	    Collection<TMarca> marcas = readAll();

	    return marcas.stream()
	        .filter(TMarca::isActivo)
	        .filter(m -> ventas.containsKey(m.getNombre()))
	        .sorted((m1, m2) -> Double.compare(
	            ventas.get(m2.getNombre()),
	            ventas.get(m1.getNombre())
	        )).limit(5).toList();
	}
	
	public Collection<String> articulosMarca() {
		SAServicio todos = FactoriaAbstractaNegocio.getInstance().crearSAServicio();
		List<TArticulo> art = (List<TArticulo>) todos.readAllArticulos();
		return art.stream().map(s -> s.getNombre()).toList();
	}
}
