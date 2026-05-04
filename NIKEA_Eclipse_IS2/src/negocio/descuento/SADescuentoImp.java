package negocio.descuento;

import integracion.factoria.FactoriaIntegracion;
import integracion.descuento.DAODescuento;

import java.util.ArrayList;
import java.util.Collection;

public class SADescuentoImp implements SADescuento {

    private TDescuento ultimoDuplicado;

    @Override
    public int create(TDescuento td) {
        DAODescuento dao = FactoriaIntegracion.getInstance().crearDAODescuento();
        TDescuento existente = dao.readByCodigo(td.getCodigo());
        
        this.ultimoDuplicado = null;

        if (td.getCodigo() == null || td.getCodigo().trim().isEmpty()) return -3;
        if (td.getPorcentaje() <= 0 || td.getPorcentaje() > 100) return -4;

        if (existente == null) {
            return dao.create(td);
        }

        this.ultimoDuplicado = existente;

        if (existente.isActivo()) {
            return -1;
        } else {
            // Si es inactivo, comparamos si los datos son iguales para reactivación automática
            if (existente.isTipo() == td.isTipo() &&
            	Double.compare(existente.getPorcentaje(), td.getPorcentaje()) == 0 &&
                Double.compare(existente.getCantidad(), td.getCantidad()) == 0) {
                
                existente.setActivo(true);
                existente.setNombre(td.getNombre());
                dao.update(existente);
                return existente.getId();
            }
            
            this.ultimoDuplicado = td;
            this.ultimoDuplicado.setId(existente.getId()); // Arrastramos el ID viejo
            return -2;
        }
    }

    @Override
    public int reactivate(TDescuento td) {
        DAODescuento dao = FactoriaIntegracion.getInstance().crearDAODescuento();
        td.setActivo(true); // Nos aseguramos de que va activo
        return dao.update(td);
    }

    @Override
    public int update(TDescuento td) {
        DAODescuento dao = FactoriaIntegracion.getInstance().crearDAODescuento();
        if (td.getCodigo() == null || td.getCodigo().trim().isEmpty()) return -3;
        if (td.getPorcentaje() <= 0 || td.getPorcentaje() > 100) return -4;
        
        return dao.update(td);
    }

    @Override
    public TDescuento read(int id) {
        TDescuento td = FactoriaIntegracion.getInstance().crearDAODescuento().read(id);
        if (td != null && !td.isActivo()) return null;
        return td;
    }

 // En SADescuentoImp
    @Override
    public Collection<TDescuento> readAll() {
        Collection<TDescuento> todos = FactoriaIntegracion.getInstance().crearDAODescuento().readAll();
        Collection<TDescuento> activos = new ArrayList<>();
        for (TDescuento d : todos) {
            if (d.isActivo()) activos.add(d);
        }
        return activos;
    }

    @Override
    public TDescuento getUltimoDuplicado() {
        return this.ultimoDuplicado;
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