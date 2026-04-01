package negocio.cliente;

import java.util.Collection;

public interface SACliente {

	public int create(TCliente cl);
	public TCliente read(int id);
	public int update(TCliente cl);
	public int delete (int id);
	public Collection<TCliente> readAll();

}
