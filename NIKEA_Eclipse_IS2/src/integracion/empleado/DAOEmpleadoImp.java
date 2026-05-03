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
                
                TEmpleado e;
                int tipo = obj.getInt("tipo");
                
                if (tipo == 1) { // Vendedor
                    TVendedor v = new TVendedor();
                    v.setNumeroVentas(obj.optInt("numeroVentas", 0));
                    e = v;
                } else { // Montador (tipo 2)
                    e = new TMontador();
                }
                
                e.setId(obj.getInt("id"));
                e.setNombre(obj.getString("nombre"));
                e.setApellido(obj.getString("apellido"));
                e.setDNI(obj.getString("dni"));
                e.setSueldo(obj.getDouble("sueldo"));
                e.setActivo(obj.getBoolean("activo"));
                e.setTipo(tipo);
                
                lista.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    private void guardarEnArchivo(Collection<TEmpleado> lista) {
        JSONArray array = new JSONArray();
        
        for (TEmpleado e : lista) {
            JSONObject obj = new JSONObject();
            
            // Datos comunes 
            obj.put("id", e.getId());
            obj.put("nombre", e.getNombre());
            obj.put("apellido", e.getApellido());
            obj.put("dni", e.getDNI());
            obj.put("sueldo", e.getSueldo());
            obj.put("activo", e.isActivo());
            obj.put("tipo", e.getTipo()); 

            // Datos específicos basados en el valor de getTipo()
            if (e.getTipo() == 1) {
                TVendedor v = (TVendedor) e;
                obj.put("numeroVentas", v.getNumeroVentas());
            }           
            array.put(obj);
        }

        try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
            os.write(array.toString(4).getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
