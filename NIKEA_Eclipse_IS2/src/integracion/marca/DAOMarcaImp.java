package integracion.marca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import negocio.marca.TMarca;

public class DAOMarcaImp implements DAOMarca {

	private final String PATH = "resources/BD/marcas.json";
	
	@Override
	public int create(TMarca te) {
		List<TMarca> lista = (List<TMarca>) readAll();
		
	    int maxId = 0;
	    for (TMarca m : lista) {
	        if (m.getId() > maxId) maxId = m.getId();
	    }

	    te.setId(maxId + 1);
	    lista.add(te);

	    guardarEnArchivo(lista);
	    return te.getId();
	}

	@Override
	public int update(TMarca tm) {
		List<TMarca> lista = (List<TMarca>) readAll();

	    for (int i = 0; i < lista.size(); i++) {
	        if (lista.get(i).getId() == tm.getId()) {
	            lista.set(i, tm);
	            guardarEnArchivo(lista);
	            return 1;
	        }
	    }

	    return -1; // no encontrado
	}

	@Override
	public TMarca read(int id) {
		for (TMarca e : readAll()) {
            if (e.getId() == id) return e;
        }
        return null;
	}

	@Override
	public TMarca readByNombre(String nombre) {
		for (TMarca e : readAll()) {
            if (e.getNombre().equalsIgnoreCase(nombre)) return e;
        }
        return null;
	}

	@Override
	public Collection<TMarca> readAll() {
		List<TMarca> lista = new ArrayList<>();
        File file = new File(PATH);

        if (!file.exists()) return lista;

        try (FileInputStream is = new FileInputStream(file)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONArray array = new JSONArray(tokener);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                TMarca m = new TMarca();

                m.setId(obj.getInt("idMarca"));
                m.setNombre(obj.getString("nombre"));
                m.setActivo(obj.getBoolean("activo"));

                // listaArticulos
                List<Integer> listaArticulos = new ArrayList<>();
                JSONArray arrArt = obj.optJSONArray("listaArticulos");

                if (arrArt != null) {
                    for (int j = 0; j < arrArt.length(); j++) {
                        listaArticulos.add(arrArt.getInt(j));
                    }
                }

                m.setListaArticulos(listaArticulos);

                lista.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
	}
	
	
	// MÉTODO AUXILIAR
	
	private void guardarEnArchivo(Collection<TMarca> lista) {

        JSONArray array = new JSONArray();

        for (TMarca m : lista) {
            JSONObject obj = new JSONObject();

            obj.put("idMarca", m.getId());
            obj.put("nombre", m.getNombre());
            obj.put("activo", m.isActivo());

            JSONArray arrArt = new JSONArray();
            if (m.getListaArticulos() != null) {
                for (Integer idArt : m.getListaArticulos()) {
                    arrArt.put(idArt);
                }
            }

            obj.put("listaArticulos", arrArt);

            array.put(obj);
        }

        try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
            os.write(array.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
