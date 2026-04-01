package integracion.cliente;

import java.util.Collection;

import negocio.cliente.TCliente;

public class DAOClienteImp implements DAOCliente {
	
	// MÉTODOS DE LA INTERFAZ
	
	@Override
	public int create(TCliente tCliente) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TCliente read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TCliente> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(TCliente tCliente) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TCliente readByDNI(String dni) {
		// TODO Auto-generated method stub
		return null;
	}

}
