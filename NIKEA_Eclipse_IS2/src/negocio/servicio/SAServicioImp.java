package negocio.servicio;

import integracion.factoria.FactoriaIntegracion;
import integracion.factura.DAOLineaFactura;
import integracion.factura.DAOFactura;
import integracion.servicio.DAOServicio;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.marca.TMarca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SAServicioImp implements SAServicio {

	@Override
	public int create(TServicio ts) {

		DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();

		TServicio existente = dao.readByNombre(ts.getNombre());

		/*
		 * Códigos de retorno: > 0 -> Alta correcta / Reactivación automática -1 -> Ya
		 * existe activo con mismos datos -100 -> Ya existe activo con distinto
		 * contenido -2 -> Existe inactivo con datos distintos -3 -> Existe inactivo
		 * mismo nombre distinto tipo -300 -> Existe activo mismo nombre distinto tipo
		 */

		// CASO 1: No existe
		if (existente == null) {
			return dao.create(ts);
		}

		// Comparación de datos
		boolean mismaDescripcion = existente.getDescripcion() != null
				&& existente.getDescripcion().trim().equalsIgnoreCase(ts.getDescripcion().trim());

		boolean mismosDatos = mismaDescripcion;

		// CASO 2: Existe pero está inactivo
		if (!existente.isActivo()) {

			// Mismo servicio pero distinto tipo
			if (mismosDatos && !existente.getTipo().equals(ts.getTipo())) {
				return -3;
			}

			// Reactivación automática
			if (mismosDatos) {

				existente.setActivo(true);
				existente.setStock(ts.getStock());
				existente.setPrecioActual(ts.getPrecioActual());

				dao.update(existente);

				return existente.getId();
			}

			// Datos distintos
			return -2;
		}

		// CASO 3: Existe y está activo
		if (existente.isActivo()) {

			// Distinto tipo
			if (mismosDatos && !existente.getTipo().equals(ts.getTipo())) {

				return -300;
			}

			// Mismos datos
			if (mismosDatos) {
				return -1;
			}

			// Otro servicio con mismo nombre
			return -100;
		}

		return -1;
	}

	@Override
	public int reactivate(TServicio ts) {

		DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();

		TServicio existente = dao.readByNombre(ts.getNombre());

		if (existente != null) {

			existente.setDescripcion(ts.getDescripcion());
			existente.setPrecioActual(ts.getPrecioActual());
			existente.setStock(ts.getStock());
			existente.setActivo(true);

			return dao.update(existente);
		}

		return -1;
	}

	@Override
	public int delete(int id) {

		DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();
		TServicio ts = dao.read(id);

		if (ts == null)
			return -3;
		if (!ts.isActivo())
			return -4;

		ts.setActivo(false);
		return dao.update(ts);
	}

	@Override
	public int update(TServicio ts) {

		DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();
		TServicio existente = dao.read(ts.getId());

		if (existente != null && existente.isActivo()) {

			if (ts.getTipo() != null && !ts.getTipo().equals(existente.getTipo())) {

				if (ts.getNombre() == null)
					ts.setNombre(existente.getNombre());
				if (ts.getDescripcion() == null)
					ts.setDescripcion(existente.getDescripcion());
				if (ts.getStock() == null)
					ts.setStock(existente.getStock());
				if (ts.getPrecioActual() == null)
					ts.setPrecioActual(existente.getPrecioActual());
				if (ts.getMarca() == null)
					ts.setMarca(existente.getMarca());

				ts.setActivo(existente.isActivo());

				return dao.update(ts);
			} else {

				if (ts.getNombre() != null)
					existente.setNombre(ts.getNombre());
				if (ts.getDescripcion() != null)
					existente.setDescripcion(ts.getDescripcion());
				if (ts.getStock() != null)
					existente.setStock(ts.getStock());
				if (ts.getPrecioActual() != null)
					existente.setPrecioActual(ts.getPrecioActual());
				if (ts.getMarca() != null)
					existente.setMarca(ts.getMarca());

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
	public TServicio readActive(int id) {
	    DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();
	    TServicio ts = dao.read(id);
	    if (ts != null && ts.isActivo()) {
	        return ts;
	    }
	    return null;
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
		Collection<TServicio> servicios = dao.readAll();
		Collection<TServicio> salida = new ArrayList<TServicio>();
		for (TServicio s : servicios)
			if (s.isActivo())
				salida.add(s);
		
		return salida;
	}

	// Usadas en Marca

	@Override
	public Collection<TArticulo> readAllArticulos() {
		Collection<TServicio> lista = readAll();
		return lista.stream().filter(s -> s.getTipo() == 1 && s.isActivo()).map(s -> (TArticulo) s).toList();
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

		for (TArticulo t : lista.stream().filter(s -> s.getMarcaId() == idMarca).toList())
			System.out.println(t.getNombre());
		return lista.stream().filter(s -> s.getMarcaId() == idMarca).toList();
	}

	// Para obtener el mejor artículo

	@Override
	public TArticulo getMejorArticulo() {

	    DAOFactura daoFactura = FactoriaIntegracion.getInstance().crearDAOFactura();
	    DAOLineaFactura daoLinea = FactoriaIntegracion.getInstance().crearDAOLineaFactura();
	    DAOServicio daoServicio = FactoriaIntegracion.getInstance().crearDAOServicio();

	    Map<Integer, Integer> ventasPorArticulo = new HashMap<>();
	    Map<Integer, TArticulo> articulosActivos = new HashMap<>();

	    // Cargar artículos activos
	    for (TServicio servicio : daoServicio.readAll()) {
	        if (servicio.isActivo() && servicio.getTipo() == 1) {
	            TArticulo art = (TArticulo) servicio;
	            articulosActivos.put(art.getId(), art);
	        }
	    }

	    // Calcular ventas
	    for (TFactura factura : daoFactura.readAll()) {

	        Collection<TLineaFactura> lineas = factura.getLineas();

	        if (lineas == null || lineas.isEmpty()) {
	            lineas = daoLinea.read(factura.getId());
	        }

	        for (TLineaFactura linea : lineas) {

	            TServicio servicio = daoServicio.read(linea.getIdProducto());

	            if (servicio != null
	                    && servicio.isActivo()
	                    && servicio.getTipo() == 1) {

	                int idArticulo = servicio.getId();

	                ventasPorArticulo.put(
	                        idArticulo,
	                        ventasPorArticulo.getOrDefault(idArticulo, 0) + linea.getCantidad()
	                );
	            }
	        }
	    }

	    // Actualizar ventas en BD
	    for (Map.Entry<Integer, TArticulo> entry : articulosActivos.entrySet()) {
	        Integer id = entry.getKey();
	        TArticulo art = entry.getValue();

	        art.setVentas(ventasPorArticulo.getOrDefault(id, 0));
	        daoServicio.update(art);
	    }

	    // Buscar máximo
	    Integer idMejor = null;
	    int max = -1;

	    for (Map.Entry<Integer, Integer> entry : ventasPorArticulo.entrySet()) {
	        if (entry.getValue() > max) {
	            max = entry.getValue();
	            idMejor = entry.getKey();
	        }
	    }

	    // Resultado final
	    if (idMejor == null) {
	        return null;
	    }

	    TServicio mejor = daoServicio.read(idMejor);

	    if (mejor != null && mejor.getTipo() == 1 && mejor.isActivo()) {
	        return (TArticulo) mejor;
	    }

	    return null;
	}
}