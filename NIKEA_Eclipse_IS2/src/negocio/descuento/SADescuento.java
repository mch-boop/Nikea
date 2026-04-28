package negocio.descuento;

import java.util.Collection;

public interface SADescuento {
    public int create(TDescuento td);
    public int delete(int id);
    public int update(TDescuento td);
    public int reactivate(TDescuento td);
    public TDescuento getUltimoDuplicado();
    public TDescuento read(int id);
    public Collection<TDescuento> readAll();
}
