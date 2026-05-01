package negocio.marca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import integracion.marca.DAOMarca;
import integracion.servicio.DAOServicio;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
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
		DAOFactura facturaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
	    DAOServicio servicioDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOServicio();
	    DAOMarca marcaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();
	    
	    List<TFactura> facturas = facturaDAO.leerTodas();

	    Map<Integer, Double> ventasMarca = new HashMap<>();

	    for (TFactura f : facturas) {

	        // Asegurar líneas
	        if (f.getLineas() == null || f.getLineas().isEmpty()) {
	            f.setLineas(
	                FactoriaAbstractaIntegracion.getInstance()
	                    .crearDAOLineaFactura()
	                    .leerPorFactura(f.getId())
	            );
	        }

	        for (TLineaFactura l : f.getLineas()) {

	            double subtotal = l.getSubtotal();

	            int idProducto = l.getIdProducto();
	            TArticulo art = (TArticulo) servicioDAO.read(idProducto);

	            if (art != null && art.getMarca() != null) {
	                int idMarca = art.getMarca().getId();

	                ventasMarca.put(
	                    idMarca,
	                    ventasMarca.getOrDefault(idMarca, 0.0) + subtotal
	                );
	            }
	        }
	    }

	    // ORDENAR por ventas (descendente)
	    List<Map.Entry<Integer, Double>> ranking = new ArrayList<>(ventasMarca.entrySet());

	    ranking.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

	    // CONSTRUIR resultado
	    List<TMarca> resultado = new ArrayList<>();
	    int limite = Math.min(5, ranking.size());

	    for (int i = 0; i < limite; i++) {
	        Integer idMarca = ranking.get(i).getKey();
	        TMarca marca = marcaDAO.read(idMarca);

	        if (marca != null && marca.isActivo()) {
	            resultado.add(marca);
	        }
	    }

	    return resultado;
	}
}
