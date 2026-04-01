package negocio.cliente;

import java.util.Collection;

import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;

public class SAClienteImp implements SACliente {

	// MÉTODOS DE LA INTERFAZ
	
	@Override
	public int create(TCliente cl) {
		// [Esto es lo que viene en la diapositiva 40 del tema 5]
		/*
		int id = -1;
		//validaciones correspondientes, cl !=null, que el dni del
		//transfer tiene formato adecuado...
		DAOCliente daoCliente =
		FactoriaAbstractaIntegracion.getInstance().createDAOCliente();
		TCliente leido = daoCliente.readByDNI(cl.getDni());
		if (leido == null) //Si no ex.lo crea

		id = daoCliente.create(cl);

		else if (!leido.getActivo()) { //existe y no activo
		cl.setId(leido.getId()); //cojo id del que tenía
		cl.setActivo(true);
		id = daoCliente.update(cl);
		return id;
		*/
		
		return 0;
	}

	@Override
	public TCliente read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(TCliente cl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<TCliente> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
