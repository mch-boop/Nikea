package integracion.factura;

import java.util.ArrayList;
import java.util.List;

import negocio.factura.TFactura;

public class DAOFacturaImp implements DAOFactura {

	private List<TFactura> facturas = new ArrayList<>();
	private int nextId = 1;

	@Override
	public int crear(TFactura factura) {
		factura.setId(nextId++);
		facturas.add(factura);
		return factura.getId();
	}

	@Override
	public TFactura leerPorId(int id) {
		for (TFactura f : facturas) {
			if (f.getId() == id) {
				return f;
			}
		}
		return null;
	}

	@Override
	public List<TFactura> leerTodas() {
		return new ArrayList<>(facturas);
	}

	@Override
	public boolean actualizar(TFactura factura) {
		for (int i = 0; i < facturas.size(); i++) {
			if (facturas.get(i).getId() == factura.getId()) {
				facturas.set(i, factura);
				return true;
			}
		}
		return false;
	}

	@Override
	public void eliminar(int id) {
		facturas.removeIf(f -> f.getId() == id);

	}

	@Override
	public List<TFactura> leerPorCliente(Integer idCliente) {
		List<TFactura> res = new ArrayList<>();

		for (TFactura f : facturas) {
			if (f.getIdCliente() == idCliente) {
				res.add(f);
			}
		}
		return res;
	}

	@Override
	public List<TFactura> leerPorRangoFechas(String fechaInicio, String fechaFin) {
		List<TFactura> res = new ArrayList<>();

		for (TFactura f : facturas) {
			if (f.getFecha().compareTo(fechaInicio) >= 0 && f.getFecha().compareTo(fechaFin) <= 0) {
				res.add(f);
			}
		}
		return res;
	}

}
