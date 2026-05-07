package negocio.servicio;

import integracion.factoria.FactoriaIntegracion;
import integracion.factura.DAOLineaFactura;
import integracion.factura.DAOFactura;
import integracion.servicio.DAOServicio;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.marca.TMarca;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SAServicioImp implements SAServicio {

    private TServicio ultimoDuplicado;

    @Override
    public int create(TServicio ts) {

        DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();

        TServicio existente = dao.readByNombre(ts.getNombre());

        this.ultimoDuplicado = null;

        /*
         * > 0  -> OK
         * -1   -> Ya existe activo con mismos datos
         * -100 -> Ya existe activo con distinto contenido
         * -2   -> Existe inactivo con datos distintos
         * -3   -> Existe inactivo mismo nombre pero distinto tipo
         * -300 -> Existe activo mismo nombre distinto tipo
         */

        if (existente == null) {
            return dao.create(ts);
        }

        this.ultimoDuplicado = existente;

        boolean mismaDescripcion = existente.getDescripcion() != null &&
                existente.getDescripcion().trim().equalsIgnoreCase(ts.getDescripcion().trim());

        boolean mismosDatos = mismaDescripcion;

        if (!existente.isActivo()) {

            if (mismosDatos && !existente.getTipo().equals(ts.getTipo())) {
                return -3;
            }

            if (mismosDatos) {
                existente.setActivo(true);
                existente.setStock(ts.getStock());
                existente.setPrecioActual(ts.getPrecioActual());

                dao.update(existente);
                return existente.getId();
            }

            return -2;
        }

        if (existente.isActivo()) {

            if (mismosDatos && !existente.getTipo().equals(ts.getTipo())) {
                return -300;
            }

            if (mismosDatos) {
                return -1;
            } else {
                return -100;
            }
        }

        return -1;
    }

    @Override
    public TServicio getUltimoDuplicado() {
        return this.ultimoDuplicado;
    }

    @Override
    public int delete(int id) {

        DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();
        TServicio ts = dao.read(id);

        if (ts == null) return -3;
        if (!ts.isActivo()) return -4;

        ts.setActivo(false);
        return dao.update(ts);
    }

    @Override
    public int update(TServicio ts) {

        DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();
        TServicio existente = dao.read(ts.getId());

        if (existente != null && existente.isActivo()) {

            if (ts.getTipo() != null && !ts.getTipo().equals(existente.getTipo())) {

                if (ts.getNombre() == null) ts.setNombre(existente.getNombre());
                if (ts.getDescripcion() == null) ts.setDescripcion(existente.getDescripcion());
                if (ts.getStock() == null) ts.setStock(existente.getStock());
                if (ts.getPrecioActual() == null) ts.setPrecioActual(existente.getPrecioActual());
                if (ts.getMarca() == null) ts.setMarca(existente.getMarca());

                ts.setActivo(existente.isActivo());

                return dao.update(ts);
            } else {

                if (ts.getNombre() != null) existente.setNombre(ts.getNombre());
                if (ts.getDescripcion() != null) existente.setDescripcion(ts.getDescripcion());
                if (ts.getStock() != null) existente.setStock(ts.getStock());
                if (ts.getPrecioActual() != null) existente.setPrecioActual(ts.getPrecioActual());
                if (ts.getMarca() != null) existente.setMarca(ts.getMarca());

                return dao.update(existente);
            }
        }

        return -1;
    }

    @Override
    public TServicio read(int id) {
        DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();
        return dao.read(id);
    }

    @Override
    public Optional<TServicio> readActive(int id) {
        TServicio servicio = read(id);
        if (servicio != null && servicio.isActivo()) {
            return Optional.of(servicio);
        }
        return Optional.empty();
    }

    @Override
    public int readToDelete(int id) {
        TServicio servicio = read(id);
        if (servicio == null) {
            return -3;
        }
        if (!servicio.isActivo()) {
            return -4;
        }
        return 1;
    }

    @Override
    public Collection<TServicio> readAll() {
        DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();
        return dao.readAll();
    }
    
    
    // Usadas en Marca
    
    @Override
    public Collection<TArticulo> readAllArticulos() {
    	Collection<TServicio> lista = readAll();
    	return lista.stream()
    		    .filter(s -> s.getTipo() == 1 && s.isActivo())
    		    .map(s -> (TArticulo) s).toList();
    }
    
    @Override
    public Collection<TArticulo> readArticulosPorMarca(int idMarca) {
    	Collection<TArticulo> lista = readAllArticulos();
    	if (idMarca != 0) {
    		TMarca marca = FactoriaIntegracion.getInstance().crearDAOMarca().read(idMarca);
    		if (marca == null || !marca.isActivo()) {
                	return Collections.emptyList();
            }
    	}
    	
    	for (TArticulo t : lista.stream()
    		    .filter(s -> s.getMarcaId() == idMarca)
    		    .toList())
    		System.out.println(t.getNombre());
    	return lista.stream()
    		    .filter(s -> s.getMarcaId() == idMarca)
    		    .toList();
    }
    
    // Para obtener el mejor artículo
    
    public Optional<TArticulo> getMejorArticulo() {
        DAOFactura daoFactura = FactoriaIntegracion.getInstance().crearDAOFactura();
        DAOLineaFactura daoLinea = FactoriaIntegracion.getInstance().crearDAOLineaFactura();
        DAOServicio daoServicio = FactoriaIntegracion.getInstance().crearDAOServicio();

        Map<Integer, Integer> ventasPorArticulo = new HashMap<>();
        Map<Integer, TArticulo> articulosActivos = new HashMap<>();

        for (TServicio servicio : daoServicio.readAll()) {
            if (servicio instanceof TArticulo && servicio.isActivo()) {
                articulosActivos.put(servicio.getId(), (TArticulo) servicio);
            }
        }

        for (TFactura factura : daoFactura.readAll()) {
            Collection<TLineaFactura> lineas = factura.getLineas();

            if (lineas == null || lineas.isEmpty()) {
                lineas = daoLinea.read(factura.getId());
            }

            for (TLineaFactura linea : lineas) {
                TServicio servicio = daoServicio.read(linea.getIdProducto());
                if (servicio instanceof TArticulo && servicio.isActivo()) {
                    int idArticulo = servicio.getId();
                    ventasPorArticulo.put(idArticulo,
                        ventasPorArticulo.getOrDefault(idArticulo, 0) + linea.getCantidad());
                }
            }
        }

        for (Map.Entry<Integer, TArticulo> entry : articulosActivos.entrySet()) {
            Integer idArticulo = entry.getKey();
            TArticulo articulo = entry.getValue();
            articulo.setVentas(ventasPorArticulo.getOrDefault(idArticulo, 0));
            daoServicio.update(articulo);
        }


        Optional<Integer> idMejorArticulo = getMaxEntero(ventasPorArticulo);
        if (idMejorArticulo.isEmpty()) {
            return Optional.empty();
        }

        TServicio mejor = daoServicio.read(idMejorArticulo.get());
        if (mejor instanceof TArticulo) {
            return Optional.of((TArticulo) mejor);
        }

        return Optional.empty();
	}

    private Optional<Integer> getMaxEntero(Map<Integer, Integer> mapa) {
        Integer best = null;
        int max = -1;

        for (Map.Entry<Integer, Integer> entry : mapa.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                best = entry.getKey();
            }
        }

        return Optional.ofNullable(best);
    }
}