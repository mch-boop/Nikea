package negocio.descuento;

import integracion.factoria.FactoriaIntegracion;
import integracion.descuento.DAODescuento;
import java.util.Collection;

public class SADescuentoImp implements SADescuento {

    private TDescuento ultimoDuplicado;

    @Override
    public int create(TDescuento td) {
        // DAO
        DAODescuento dao = FactoriaIntegracion.getInstance().crearDAODescuento();
        
        //Buscar por codigo
        TDescuento existente = dao.readByCodigo(td.getCodigo());
        
        this.ultimoDuplicado = null;

        // --- VALIDACIONES DE NEGOCIO ---
        if (td.getCodigo() == null || td.getCodigo().trim().isEmpty()) return -3;
        if (td.getPorcentaje() <= 0 || td.getPorcentaje() > 100) return -4;

        // Caso 1: No existe ya
        if (existente == null) {
            return dao.create(td);
        }

        //Caso 2: Ya existe (guardamos dupli)
        this.ultimoDuplicado = existente;

        // Caso 2.1: existe activo
        if (existente.isActivo()) {
            return -1;
        }

        // Caso 2.2: Existe inactivo
        if (!existente.isActivo()) {
            // Si todo coincide reactivamos
            if (existente.isTipo() == td.isTipo() && existente.getPorcentaje() == td.getPorcentaje()) {
                existente.setActivo(true);
                existente.setNombre(td.getNombre());
                dao.update(existente);
                return existente.getId();
            }
            
            // Si algo ha cambiado llamamos para confirmar
            return -2;
        }

        return -1;
    }

    @Override
    public TDescuento getUltimoDuplicado() {
        return this.ultimoDuplicado;
    }

    //TODO

    @Override
    public int reactivate(TDescuento td) {
        return -1;
    }

    @Override
    public int delete(int id) {
        return -1;
    }

    @Override
    public int update(TDescuento td) {
        return -1;
    }

    @Override
    public TDescuento read(int id) {
        return null;
    }

    @Override
    public Collection<TDescuento> readAll() {
        return null;
    }
}