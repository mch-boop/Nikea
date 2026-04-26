package negocio.marca;

import java.util.Collection;

public interface SAMarca {
	
	public int create(TMarca tm);
	public TMarca read(int id);
	public int update(TMarca tm);
	public Collection<TMarca> update_listar();
	public int delete (int id);
	public Collection<TMarca> readAll();
	
	// auxiliares
	public boolean isReactivada();
}
