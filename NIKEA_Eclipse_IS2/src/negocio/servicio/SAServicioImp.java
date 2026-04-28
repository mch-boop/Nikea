package negocio.servicio;

import integracion.factoria.FactoriaIntegracion;
import integracion.servicio.DAOServicio;

import java.util.Collection;

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

                ts.setActivo(existente.isActivo());

                return dao.update(ts);
            } else {

                if (ts.getNombre() != null) existente.setNombre(ts.getNombre());
                if (ts.getDescripcion() != null) existente.setDescripcion(ts.getDescripcion());
                if (ts.getStock() != null) existente.setStock(ts.getStock());
                if (ts.getPrecioActual() != null) existente.setPrecioActual(ts.getPrecioActual());

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
    public Collection<TServicio> readAll() {
        DAOServicio dao = FactoriaIntegracion.getInstance().crearDAOServicio();
        return dao.readAll();
    }
}