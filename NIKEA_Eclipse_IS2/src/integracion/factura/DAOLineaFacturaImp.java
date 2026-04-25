package integracion.factura;

import java.util.ArrayList;
import java.util.List;

import negocio.factura.TLineaFactura;

public class DAOLineaFacturaImp implements DAOLineaFactura {

	private List<TLineaFactura> lineas = new ArrayList<>();

	@Override
	public void crear(TLineaFactura lineaFactura) {
		lineas.add(lineaFactura);

	}

	@Override
	public List<TLineaFactura> leerPorFactura(int idFactura) {
		List<TLineaFactura> res = new ArrayList<>();

		for (TLineaFactura l : lineas) {
			if (l.getIdFactura() == idFactura) {
				res.add(l);
			}
		}
		return res;
	}

	@Override
	public TLineaFactura leerPorLinea(Integer idFactura, Integer idProducto) {
		for (TLineaFactura l : lineas) {
			if (l.getIdFactura() == idFactura && l.getIdProducto() == idProducto) {
				return l;
			}
		}
		return null;
	}

	@Override
	public void actualizar(TLineaFactura lineaFactura) {
		for (int i = 0; i < lineas.size(); i++) {
			TLineaFactura l = lineas.get(i);

			if (l.getIdFactura() == lineaFactura.getIdFactura() && l.getIdProducto() == lineaFactura.getIdProducto()) {

				lineas.set(i, lineaFactura);
				return;
			}
		}

	}

	@Override
	public void eliminar(Integer idFactura, Integer idProducto) {
		lineas.removeIf(l -> l.getIdFactura() == idFactura && l.getIdProducto() == idProducto);

	}

	@Override
	public void eliminarPorFactura(Integer idFactura) {
		lineas.removeIf(l -> l.getIdFactura() == idFactura);

	}

}
