package integracion.empleado;

import negocio.empleado.TEmpleado;
import negocio.empleado.TMontador;
import negocio.empleado.TVendedor;

import java.util.Collection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DAOEmpleadoImp implements DAOEmpleado {

	private final String PATH = "resources/BD/empleados.json";
	
    @Override
    public int create(TEmpleado te) {
    	List<TEmpleado> lista = (List<TEmpleado>) readAll();
        
        int maxId = 0;
        for (TEmpleado e : lista) {
            if (e.getId() > maxId) maxId = e.getId();
        }
        te.setId(maxId + 1);
        
        lista.add(te);
        guardarEnArchivo(lista);
        return te.getId();
    }
    
    @Override
    public int update(TEmpleado te) {
    	List<TEmpleado> lista = (List<TEmpleado>) readAll();
        
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equals(te.getId())) {
                lista.set(i, te); // Reemplaza la instancia antigua por la nueva
                guardarEnArchivo(lista);
                return te.getId();
            }
        }
        return -1; // No se encontró el ID
    }

    @Override
    public TEmpleado read(int id) {
        for (TEmpleado e : readAll()) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    @Override
    public TEmpleado readByDNI(String dni) {
        for (TEmpleado e : readAll()) {
            if (e.getDNI().equalsIgnoreCase(dni)) return e;
        }
        return null;
    }

    @Override
    public Collection<TEmpleado> readAll() {
    	
    	List<TEmpleado> lista = new ArrayList<>();
        File file = new File(PATH);
        
        if (!file.exists()) return lista;

        try (FileInputStream is = new FileInputStream(file)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONArray array = new JSONArray(tokener);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                
                /*
                   Al leer cada objeto del JSON, miramos el campo "tipo":
                   - Si tipo == 1 -> instanciamos TVendedor y mapeamos 'numeroVentas'.
                   - Si tipo == 2 -> instanciamos TMontador.
                */
                TEmpleado e;
                int tipo = obj.getInt("tipo");
                
                if (tipo == 1) {
                    e = new TVendedor();
                } else {
                    e = new TMontador();
                }
                
                // Datos comunes
                e.setId(obj.getInt("id"));
                e.setNombre(obj.getString("nombre"));
                e.setApellido(obj.getString("apellido"));
                e.setDNI(obj.getString("dni"));
                e.setSueldo(obj.getDouble("sueldo"));
                e.setActivo(obj.getBoolean("activo"));
                e.setTipo(tipo);
                
                // Datos específicos (cada clase sabe qué leer del JSON)
                e.fromJSON(obj);
                
                lista.add(e);
            }
        } catch (Exception e) {
            // Si hay error (archivo mal formado), devolvemos lista vacía
            e.printStackTrace();
        }
        return lista;
    }

    private void guardarEnArchivo(Collection<TEmpleado> lista) {
        // Convierte la lista a JSON y sobrescribe empleados.json
    	JSONArray array = new JSONArray();
        for (TEmpleado e : lista) {
            // Cada objeto genera su propio JSONObject
            array.put(e.asJSON());
        }

        try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
            os.write(array.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
