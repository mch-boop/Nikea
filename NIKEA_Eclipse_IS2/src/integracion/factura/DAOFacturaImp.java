package integracion.factura;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import integracion.factoria.FactoriaAbstractaIntegracion;
import negocio.factura.TFactura;

public class DAOFacturaImp implements DAOFactura {

	private final String PATH = "resources/BD/facturas.json";

	@Override
	public int create(TFactura factura) {
		List<TFactura> lista = readAll();

		factura.setId(lista.size() + 1);
		lista.add(factura);

		guardarEnArchivo(lista);

		return factura.getId();
	}

	@Override
	public TFactura read(int id) {
		for (TFactura f : readAll()) {
			if (f.getId() == id) {
				return f;
			}
		}
		return null;
	}

	@Override
	public List<TFactura> readAll() {

		List<TFactura> lista = new ArrayList<>();
		File file = new File(PATH);

		if (!file.exists() || file.length() == 0)
			return lista;

		DAOLineaFactura daoLinea = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();

		try (FileInputStream is = new FileInputStream(file)) {

			JSONArray array = new JSONArray(new JSONTokener(is));

			for (int i = 0; i < array.length(); i++) {

				JSONObject obj = array.getJSONObject(i);

				TFactura f = new TFactura();

				f.setId(obj.getInt("id"));
				f.setIdVendedor(obj.getInt("idVendedor"));
				f.setIdCliente(obj.optInt("idCliente", 0));
				f.setIdDescuento(obj.optInt("idDescuento", 0));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				f.setFecha(LocalDate.parse(obj.getString("fecha")));

				f.setTotal(obj.getDouble("total"));
				f.setCerrada(obj.getBoolean("cerrada"));

				f.setLineas(daoLinea.read(f.getId()));

				lista.add(f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public boolean update(TFactura factura) {
		List<TFactura> lista = readAll();

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
	public void delete(int id) {
		List<TFactura> lista = readAll();

		lista.removeIf(f -> f.getId() == id);

		guardarEnArchivo(lista);

	}

	@Override
	public List<TFactura> readByClient(int idCliente) {
		List<TFactura> res = new ArrayList<>();

		for (TFactura f : readAll()) {
			if (f.getIdCliente() == idCliente) {
				res.add(f);
			}
		}

		return res;
	}

	@Override
	public List<TFactura> readByDateRange(LocalDate inicio, LocalDate fin) {

	    return readAll().stream()
	        .filter(f -> !f.getFecha().isBefore(inicio)
	                  && !f.getFecha().isAfter(fin))
	        .toList();
	}

	@Override
	public List<TFactura> readByMonth(int month, int year) {

	    LocalDate start = LocalDate.of(year, month, 1);
	    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

	    return readByDateRange(start, end);
	}

	private void guardarEnArchivo(List<TFactura> lista) {

		JSONArray array = new JSONArray();

		for (TFactura f : lista) {
			array.put(asJSON(f));
		}

		try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
			os.write(array.toString(4).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JSONObject asJSON(TFactura f) {

		JSONObject obj = new JSONObject();

		obj.put("id", f.getId());
		obj.put("idVendedor", f.getIdVendedor());
		obj.put("idCliente", f.getIdCliente());
		obj.put("idDescuento", f.getIdDescuento());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		obj.put("fecha", sdf.format(f.getFecha()));

		obj.put("total", f.getTotal());
		obj.put("cerrada", f.isCerrada());

		return obj;
	}

}
