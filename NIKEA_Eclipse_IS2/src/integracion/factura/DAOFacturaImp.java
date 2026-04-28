package integracion.factura;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import negocio.factura.TFactura;

public class DAOFacturaImp implements DAOFactura {

	private final String PATH = "resources/BD/facturas.json";

	@Override
	public int crear(TFactura factura) {
		List<TFactura> lista = (List<TFactura>) leerTodas();

		int maxId = 0;
		for (TFactura f : lista) {
			if (f.getId() > maxId) {
				maxId = f.getId();
			}
		}

		factura.setId(maxId + 1);
		lista.add(factura);

		guardarEnArchivo(lista);

		return factura.getId();
	}

	@Override
	public TFactura leerPorId(int id) {
		for (TFactura f : leerTodas()) {
			if (f.getId() == id) {
				return f;
			}
		}
		return null;
	}

	@Override
	public List<TFactura> leerTodas() {
		List<TFactura> lista = new ArrayList<>();
		File file = new File(PATH);

		if (!file.exists())
			return lista;

		try (FileInputStream is = new FileInputStream(file)) {

			JSONTokener tokener = new JSONTokener(is);
			JSONArray array = new JSONArray(tokener);

			for (int i = 0; i < array.length(); i++) {

				JSONObject obj = array.getJSONObject(i);

				TFactura f = new TFactura();

				f.setId(obj.getInt("id"));
				f.setIdVendedor(obj.getInt("idVendedor"));
				f.setIdCliente(obj.optInt("idCliente", 0));
				f.setIdDescuento(obj.optInt("idDescuento", 0));
				f.setFecha(obj.getString("fecha"));
				f.setTotal(obj.getDouble("total"));
				f.setCerrada(obj.getBoolean("cerrada"));

				lista.add(f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public boolean actualizar(TFactura factura) {
		List<TFactura> lista = (List<TFactura>) leerTodas();

		for (int i = 0; i < lista.size(); i++) {

			if (lista.get(i).getId() == factura.getId()) {
				lista.set(i, factura);
				guardarEnArchivo(lista);
				return true;
			}
		}

		return false;
	}

	@Override
	public void eliminar(int id) {
		List<TFactura> lista = (List<TFactura>) leerTodas();

		lista.removeIf(f -> f.getId() == id);

		guardarEnArchivo(lista);

	}

	@Override
	public List<TFactura> leerPorCliente(int idCliente) {
		List<TFactura> res = new ArrayList<>();

		for (TFactura f : leerTodas()) {
			if (f.getIdCliente() == idCliente) {
				res.add(f);
			}
		}

		return res;
	}

	@Override
	public List<TFactura> leerPorRangoFechas(String fechaInicio, String fechaFin) {
		List<TFactura> res = new ArrayList<>();

		for (TFactura f : leerTodas()) {
			if (f.getFecha().compareTo(fechaInicio) >= 0 && f.getFecha().compareTo(fechaFin) <= 0) {
				res.add(f);
			}
		}
		return res;
	}

	private void guardarEnArchivo(List<TFactura> lista) {

		JSONArray array = new JSONArray();

		for (TFactura f : lista) {

			JSONObject obj = new JSONObject();

			obj.put("id", f.getId());
			obj.put("idVendedor", f.getIdVendedor());
			obj.put("idCliente", f.getIdCliente());
			obj.put("idDescuento", f.getIdDescuento());
			obj.put("fecha", f.getFecha());
			obj.put("total", f.getTotal());
			obj.put("cerrada", f.isCerrada());

			array.put(obj);
		}

		try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
			os.write(array.toString(4).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
