package integracion.marca;

import java.util.Collection;

import negocio.marca.TMarca;

public interface DAOMarca {

	public int create(TMarca te);
    public int update(TMarca te);
    public TMarca read(int id);
    public TMarca readByNombre(String nombre);
    public Collection<TMarca> readAll();
    
}
