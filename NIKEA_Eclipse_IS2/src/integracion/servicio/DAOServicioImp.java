package integracion.servicio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import negocio.servicio.TArticulo;
import negocio.servicio.TMontaje;
import negocio.servicio.TServicio;

public class DAOServicioImp implements DAOServicio {

    private static final String PATH = "resources/BD/servicios.json";

    @Override
    public int create(TServicio tServicio) {
        List<TServicio> lista = new ArrayList<>(readAll());

        if (tServicio.getId() == null || tServicio.getId() <= 0) {
            int maxId = 0;
            for (TServicio s : lista) {
                if (s.getId() != null && s.getId() > maxId) {
                    maxId = s.getId();
                }
            }
            tServicio.setId(maxId + 1);
            lista.add(tServicio);
        } else {
            boolean actualizado = false;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() != null && lista.get(i).getId().equals(tServicio.getId())) {
                    lista.set(i, tServicio);
                    actualizado = true;
                    break;
                }
            }
            if (!actualizado) {
                lista.add(tServicio);
            }
        }

        guardarEnArchivo(lista);
        return tServicio.getId();
    }

    @Override
    public int delete(int id) {
        List<TServicio> lista = new ArrayList<>(readAll());

        for (TServicio s : lista) {
            if (s.getId() != null && s.getId() == id) {
                s.setActivo(false);
                guardarEnArchivo(lista);
                return id;
            }
        }

        return -1;
    }

    @Override
    public TServicio read(int idServicio) {
        for (TServicio e : readAll()) {
            if (e.getId() != null && e.getId() == idServicio) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Collection<TServicio> readAll() {
        List<TServicio> lista = new ArrayList<>();
        File file = new File(PATH);

        if (!file.exists()) {
            return lista;
        }

        try (FileInputStream is = new FileInputStream(file)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONArray array = new JSONArray(tokener);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                lista.add(fromJSON(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public TServicio readByNombre(String nombre) {
        if (nombre == null) {
            return null;
        }

        for (TServicio s : readAll()) {
            if (s.getNombre() != null && s.getNombre().equalsIgnoreCase(nombre)) {
                return s;
            }
        }

        return null;
    }

    @Override
    public int update(TServicio tServicio) {
        return create(tServicio);
    }

    private void guardarEnArchivo(Collection<TServicio> lista) {
        JSONArray array = new JSONArray();

        for (TServicio s : lista) {
            array.put(toJSON(s));
        }

        try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
            os.write(array.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TServicio fromJSON(JSONObject obj) {
        TServicio servicio = obj.optInt("tipo", 1) == 1 ? new TArticulo() : new TMontaje();

        servicio.setId(obj.has("id") && !obj.isNull("id") ? obj.getInt("id") : null);
        servicio.setNombre(obj.has("nombre") && !obj.isNull("nombre") ? obj.getString("nombre") : null);
        servicio.setDescripcion(obj.has("descripcion") && !obj.isNull("descripcion") ? obj.getString("descripcion") : null);
        servicio.setStock(obj.has("stock") && !obj.isNull("stock") ? obj.getInt("stock") : null);
        servicio.setPrecioActual(obj.has("precioActual") && !obj.isNull("precioActual") ? obj.getDouble("precioActual") : null);
        servicio.setActivo(obj.has("activo") && !obj.isNull("activo") ? obj.getBoolean("activo") : true);
        servicio.setTipo(obj.has("tipo") && !obj.isNull("tipo") ? obj.getInt("tipo") : null);
        servicio.setMarca(obj.has("marca") && !obj.isNull("marca") ? obj.getString("marca") : null);

        if (servicio instanceof TArticulo) {
            TArticulo articulo = (TArticulo) servicio;
            articulo.setMarcaId(obj.has("idMarca") && !obj.isNull("idMarca") ? obj.getInt("idMarca") : 0);
            articulo.setVentas(obj.has("ventas") && !obj.isNull("ventas") ? obj.getInt("ventas") : 0);
        }

        return servicio;
    }

    private JSONObject toJSON(TServicio servicio) {
        JSONObject obj = new JSONObject();
        obj.put("id", servicio.getId());
        obj.put("nombre", servicio.getNombre());
        obj.put("descripcion", servicio.getDescripcion());
        obj.put("stock", servicio.getStock());
        obj.put("precioActual", servicio.getPrecioActual());
        obj.put("activo", servicio.isActivo());
        obj.put("tipo", servicio.getTipo());
        obj.put("marca", servicio.getMarca());

        if (servicio instanceof TArticulo) {
            TArticulo articulo = (TArticulo) servicio;
            obj.put("idMarca", articulo.getMarcaId());
            obj.put("ventas", articulo.getVentas());
        }

        return obj;
    }

}
