package integracion.servicio;

import negocio.servicio.TServicio;
import negocio.servicio.TArticulo;
import negocio.servicio.TMontaje;

import java.util.Collection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DAOServicioImp implements DAOServicio {

    private final String PATH = "resources/BD/servicios.json";

    @Override
    public int create(TServicio ts) {
        List<TServicio> lista = (List<TServicio>) readAll();

        if (ts.getId() == null || ts.getId() <= 0) {
            int maxId = 0;
            for (TServicio s : lista) {
                if (s.getId() > maxId) maxId = s.getId();
            }
            ts.setId(maxId + 1);
            lista.add(ts);
        } else {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId().equals(ts.getId())) {
                    lista.set(i, ts);
                    break;
                }
            }
        }

        guardarEnArchivo(lista);
        return ts.getId();
    }

    @Override
    public int update(TServicio ts) {
        return this.create(ts);
    }

    @Override
    public int delete(int id) {
        List<TServicio> lista = (List<TServicio>) readAll();

        for (TServicio s : lista) {
            if (s.getId() == id) {
                s.setActivo(false);
                guardarEnArchivo(lista);
                return id;
            }
        }
        return -1;
    }

    @Override
    public TServicio read(int id) {
        for (TServicio s : readAll()) {
            if (s.getId() == id) return s;
        }
        return null;
    }
    
    @Override
    public TServicio readByNombre(String nombre) {
        for (TServicio s : readAll()) {
            if (s.getNombre().equalsIgnoreCase(nombre)) return s;
        }
        return null;
    }

    @Override
    public Collection<TServicio> readAll() {

        List<TServicio> lista = new ArrayList<>();
        File file = new File(PATH);

        if (!file.exists()) return lista;

        try (FileInputStream is = new FileInputStream(file)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONArray array = new JSONArray(tokener);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                TServicio s;
                int tipo = obj.getInt("tipo");

                if (tipo == 1) {
                    s = new TArticulo();
                } else {
                    s = new TMontaje();
                }

                s.setId(obj.getInt("id"));
                s.setNombre(obj.getString("nombre"));
                s.setDescripcion(obj.getString("descripcion"));
                s.setStock(obj.getInt("stock"));
                s.setPrecioActual(obj.getInt("precioActual"));
                s.setActivo(obj.getBoolean("activo"));
                s.setTipo(tipo);

                s.fromJSON(obj);

                lista.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void guardarEnArchivo(Collection<TServicio> lista) {

        JSONArray array = new JSONArray();

        for (TServicio s : lista) {
            array.put(s.asJSON());
        }

        try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
            os.write(array.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}