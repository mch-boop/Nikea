package negocio.servicio;

import java.util.Collection;
import java.util.Optional;

public interface SAServicio {

	public int create(TServicio tServicio);
	public int delete(int id);
	public TServicio read(int id);
	public Optional<TServicio> readActive(int id);
	public int readToDelete(int id);
	public Collection<TServicio> readAll();
	public int update(TServicio ts);
	public TServicio getUltimoDuplicado();
	
	// CU extras
    public Optional<TArticulo> getMejorArticulo();
    public Collection<TArticulo> readAllArticulos();
    public Collection<TArticulo> readArticulosPorMarca(int idMarca);
}
