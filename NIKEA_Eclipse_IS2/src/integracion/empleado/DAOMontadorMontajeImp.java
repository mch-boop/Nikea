package integracion.empleado;

import negocio.empleado.TMontadorMontaje;
import java.util.Collection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class DAOMontadorMontajeImp implements DAOMontadorMontaje {
	
	private final String PATH = "montajes_empleados.json";

	@Override
	public int vincular(TMontadorMontaje tmm) {

	    if (existeVinculacion(tmm)) return -2;

	    List<TMontadorMontaje> lista = new ArrayList<>(readAll());
	    lista.add(tmm);
	    guardarEnArchivo(lista);

	    return 1;
	}
	
	@Override
	public int desvincular(TMontadorMontaje tmm) {

	    List<TMontadorMontaje> lista = new ArrayList<>(readAll());

	    boolean removed = lista.removeIf(x ->
	        x.getIdMontador() == tmm.getIdMontador() &&
	        x.getIdMontaje() == tmm.getIdMontaje()
	    );

	    if (!removed) return -1;

	    guardarEnArchivo(lista);

	    return 1;
	}

    @Override
    public boolean existeVinculacion(TMontadorMontaje tmm) {
        Collection<TMontadorMontaje> todas = readAll();
        
        for (TMontadorMontaje actual : todas) {
            // Si coincide tanto el montador como el montaje, es que ya están vinculados
            if (actual.getIdMontador() == tmm.getIdMontador() && 
                actual.getIdMontaje() == tmm.getIdMontaje()) {
                return true;
            }
        }
        return false;
    }
    
    private Collection<TMontadorMontaje> readAll() {

        List<TMontadorMontaje> lista = new ArrayList<>();

        File f = new File(PATH);
        if (!f.exists()) return lista;

        try {
            String content = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);

            if (content.isEmpty()) return lista;

            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {

                JSONObject obj = array.getJSONObject(i);

                int idMontador = obj.getInt("idMontador");
                int idMontaje = obj.getInt("idMontaje");

                lista.add(new TMontadorMontaje(idMontador, idMontaje));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void guardarEnArchivo(Collection<TMontadorMontaje> lista) {

        JSONArray array = new JSONArray();

        for (TMontadorMontaje t : lista) {

            JSONObject obj = new JSONObject();

            obj.put("idMontador", t.getIdMontador());
            obj.put("idMontaje", t.getIdMontaje());

            array.put(obj);
        }

        try (FileOutputStream os = new FileOutputStream(new File(PATH))) {
            os.write(array.toString(4).getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
