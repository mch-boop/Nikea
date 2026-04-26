package integracion.cliente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import negocio.cliente.TCliente;

public class DAOClienteImp implements DAOCliente {

	private final String PATH = "resources/BD/clientes.json";

	@Override
	public int create(TCliente tc) {
		List<TCliente> lista = (List<TCliente>) readAll();
        
        if (tc.getId() == null || tc.getId() <= 0) {
            // Lógica de ALTA
            int maxId = 0;
            for (TCliente c : lista) {
                if (c.getId() > maxId) maxId = c.getId();
            }
            tc.setId(maxId + 1);
            lista.add(tc);
        } else {
            // Lógica de MODIFICACIÓN
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId().equals(tc.getId())) {
                    // Reemplazamos la instancia antigua por la nueva 'tc'.
                    lista.set(i, tc); 
                    break;
                }
            }
        }
        
        guardarEnArchivo(lista); // Esto recorrerá la lista y llamará a asJSON() de cada uno
        return tc.getId();
	}

	@Override
	public TCliente read(int id) {
		// Recorro la lista de clientes que recibo de readAll.
		for (TCliente c : readAll()) {
            if (c.getId() == id) return c;
        }
        return null;
	}

	@Override
	public Collection<TCliente> readAll() {
		List<TCliente> lista = new ArrayList<>();
        File file = new File(PATH);
        
        // Si no tenemos fichero devolvemos la lista vacía.
        if (!file.exists()) return lista;

        try (FileInputStream is = new FileInputStream(file)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONArray array = new JSONArray(tokener);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                
                TCliente c = new TCliente();
                
                // Datos del cliente.
                c.setId(i);
                c.setNombre(obj.getString("nombre"));
                c.setApellidos(obj.getString("apellidos"));
                c.setDNI(obj.getString("DNI"));
                c.setTelefono(obj.getInt("teléfono"));
                c.setActivo(obj.getBoolean("activo"));
                
                // Añado el cliente a la lista.
                lista.add(c);
            }
        } catch (Exception e) {
            // Si hay error (archivo mal formado), devolvemos lista vacía
            e.printStackTrace();
        }
        return lista;
	}

	@Override
	public int update(TCliente tCliente) {
		// Si tiene ID, busca en la lista y reemplaza
        return this.create(tCliente);
	}

	@Override
	public TCliente readByDNI(String dni) {
		// Recorro la lista de clientes que recibo de readAll.
		for (TCliente c : readAll()) {
            if (c.getDNI().equalsIgnoreCase(dni)) return c;
        }
        return null;
	}
	
	// Método auxiliar.
	private void guardarEnArchivo(Collection<TCliente> lista) {
        // Convierte la lista a JSON y sobrescribe empleados.json
    	JSONArray array = new JSONArray();
        for (TCliente c : lista) {
            // Cada objeto genera su propio JSONObject
            array.put(c.asJSON());
        }

        try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
            os.write(array.toString(4).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
