package integracion.factura;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import negocio.factura.TLineaFactura;

public class DAOLineaFacturaImp implements DAOLineaFactura {

	private final String PATH = "resources/BD/lineasFactura.json";

	@Override
	public void create(TLineaFactura lineaFactura) {
		List<TLineaFactura> lista = readAll();

		lista.add(lineaFactura);

		guardar(lista);

	}

	@Override
	public List<TLineaFactura> read(int idFactura) {

		List<TLineaFactura> res = new ArrayList<>();

		for (TLineaFactura l : readAll()) {

			if (l.getIdFactura() == idFactura) {
				res.add(l);
			}
		}

		return res;
	}

	@Override
	public TLineaFactura readLine(Integer idFactura, Integer idProducto) {

		for (TLineaFactura l : readAll()) {

			if (l.getIdFactura() == idFactura && l.getIdProducto() == idProducto) {

				return l;
			}
		}

		return null;
	}

	@Override
	public void update(TLineaFactura lineaFactura) {

		List<TLineaFactura> lista = readAll();

		for (int i = 0; i < lista.size(); i++) {

			TLineaFactura l = lista.get(i);

			if (l.getIdFactura() == lineaFactura.getIdFactura() && l.getIdProducto() == lineaFactura.getIdProducto()) {

				lista.set(i, lineaFactura);
				break;
			}
		}

		guardar(lista);
	}

	@Override
	public void deleteLine(Integer idFactura, Integer idProducto) {

		List<TLineaFactura> lista = readAll();

		lista.removeIf(l -> l.getIdFactura() == idFactura && l.getIdProducto() == idProducto);

		guardar(lista);
	}

	@Override
	public void deleteAll(Integer idFactura) {

		List<TLineaFactura> lista = readAll();

		lista.removeIf(l -> l.getIdFactura() == idFactura);

		guardar(lista);
	}

	public List<TLineaFactura> readAll() {

		List<TLineaFactura> lista = new ArrayList<>();

		File file = new File(PATH);

		if (!file.exists())
			return lista;

		try (FileInputStream is = new FileInputStream(file)) {

			JSONArray array = new JSONArray(new JSONTokener(is));

			for (int i = 0; i < array.length(); i++) {

				JSONObject obj = array.getJSONObject(i);

				TLineaFactura l = new TLineaFactura();

				l.setIdFactura(obj.getInt("idFactura"));
				l.setIdServicio(obj.getInt("idProducto"));
				l.setCantidad(obj.getInt("cantidad"));
				l.setPrecioUnitario(obj.getDouble("precioUnitario"));

				lista.add(l);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	private void guardar(List<TLineaFactura> lista) {

		JSONArray array = new JSONArray();

		for (TLineaFactura l : lista) {

			JSONObject obj = new JSONObject();

			obj.put("idFactura", l.getIdFactura());
			obj.put("idProducto", l.getIdProducto());
			obj.put("cantidad", l.getCantidad());
			obj.put("precioUnitario", l.getPrecioUnitario());

			array.put(obj);
		}

		try (FileOutputStream os = new FileOutputStream(PATH)) {
			os.write(array.toString(4).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
