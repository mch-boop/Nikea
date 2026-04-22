package integracion.cliente;

import java.util.Collection;

import negocio.cliente.TCliente;

public interface DAOCliente {
	
	// Métodos de la interfaz
	public int create(TCliente tCliente);
	public int delete(int id);
	public TCliente read(int id);
	public Collection<TCliente> readAll();
	public int update(TCliente tCliente);
	public TCliente readByDNI(String dni);
	
}
