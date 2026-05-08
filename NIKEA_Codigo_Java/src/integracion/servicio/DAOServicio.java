package integracion.servicio;

import java.util.Collection;

import negocio.servicio.TServicio;

public interface DAOServicio {
	
	public int create(TServicio tServicio);
	public int delete(int id);
	public TServicio read(int id);
	TServicio readByNombre(String nombre);	
	public Collection<TServicio> readAll();
	public int update(TServicio tServicio);
	
	
}
