package integracion.factura;

import java.util.List;

import negocio.factura.TFactura;

public class DAOFacturaImp implements DAOFactura {

	@Override
	public int crear(TFactura factura) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TFactura leerPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TFactura> leerTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(TFactura factura) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TFactura> leerPorCliente(String dniCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TFactura> leerPorRangoFechas(String fechaInicio, String fechaFin) {
		// TODO Auto-generated method stub
		return null;
	}

}
