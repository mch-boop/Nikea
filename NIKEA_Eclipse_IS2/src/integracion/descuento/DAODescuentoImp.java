package integracion.descuento;

import negocio.descuento.TDescuento;
import java.util.Collection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DAODescuentoImp implements DAODescuento {

    private final String PATH = "resources/BD/descuentos.json";
    
    @Override
    public int create(TDescuento td) {
        List<TDescuento> lista = (List<TDescuento>) readAll();
        
        if (td.getId() <= 0) {
            int maxId = 0;
            for (TDescuento d : lista) {
                if (d.getId() > maxId) maxId = d.getId();
            }
            td.setId(maxId + 1);
            lista.add(td);
        } else {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() == td.getId()) {
                    lista.set(i, td); 
                    break;
                }
            }
        }
        
        guardarEnArchivo(lista);
        return td.getId();
    }
    
    @Override
    public int update(TDescuento td) {
        List<TDescuento> lista = (List<TDescuento>) readAll();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == td.getId()) {
                lista.set(i, td);
                guardarEnArchivo(lista);
                return td.getId();
            }
        }
        return -1;
    }

    @Override
    public TDescuento read(int id) {
        for (TDescuento d : readAll()) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    @Override
    public TDescuento readByCodigo(String codigo) {
        for (TDescuento d : readAll()) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) return d;
        }
        return null;
    }

    @Override
    public Collection<TDescuento> readAll() {        
        List<TDescuento> lista = new ArrayList<>();
        File file = new File(PATH);
        
        if (!file.exists()) return lista;

        try (FileInputStream is = new FileInputStream(file)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONArray array = new JSONArray(tokener);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                TDescuento td = new TDescuento(); 
                
                td.setId(obj.getInt("id"));
                td.setNombre(obj.getString("nombre"));
                td.setCodigo(obj.getString("codigo"));
                td.setPorcentaje(obj.getInt("porcentaje"));
                td.setActivo(obj.getBoolean("activo"));
                td.setTipo(obj.getBoolean("tipo"));
                td.setCantidad(obj.getDouble("cantidad"));
                
                lista.add(td);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void guardarEnArchivo(Collection<TDescuento> lista) {
        JSONArray array = new JSONArray();
        for (TDescuento d : lista) {
            JSONObject obj = new JSONObject();
            obj.put("id", d.getId());
            obj.put("nombre", d.getNombre());
            obj.put("codigo", d.getCodigo());
            obj.put("porcentaje", d.getPorcentaje());
            obj.put("activo", d.isActivo());
            obj.put("tipo", d.isTipo());
            obj.put("cantidad", d.getCantidad());
            
            array.put(obj);
        }

        try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
            os.write(array.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}