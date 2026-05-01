package negocio.servicio;

import java.util.Collection;
import java.util.List;

public interface SAServicio {

	public int create(TServicio tServicio);
	public int delete(int id);
	public TServicio read(int id);
	public Collection<TServicio> readAll();
	public int update(TServicio ts);
	public TServicio getUltimoDuplicado();
    public TServicio getMejorArticulo();
    public Collection<TArticulo> readAllArticulos();
    public List<String> obtenerArticulosActivosPorMarca(String nombre);
}
