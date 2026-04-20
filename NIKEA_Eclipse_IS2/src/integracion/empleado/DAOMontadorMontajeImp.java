package integracion.empleado;

import negocio.empleado.TMontadorMontaje;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class DAOMontadorMontajeImp implements DAOMontadorMontaje {
	
	private final String PATH = "montajes_empleados.json";

    @Override
    public int vincular(TMontadorMontaje tmm) {
        // 1. Leemos todas las vinculaciones actuales del archivo JSON
        List<TMontadorMontaje> lista = (List<TMontadorMontaje>) leerTodas();

        // 2. Añadimos la nueva pareja de IDs
        lista.add(tmm);

        // 3. Volcamos la lista actualizada al archivo
        guardarEnArchivo(lista);

        // Devolvemos un código de éxito
        return tmm.getIdMontador();
    }

    @Override
    public int desvincular(TMontadorMontaje tmm) {
        List<TMontadorMontaje> lista = (List<TMontadorMontaje>) leerTodas();
        boolean encontrado = false;

        // Buscamos la pareja exacta (ID_Montador + ID_Montaje) para eliminarla
        for (int i = 0; i < lista.size(); i++) {
            TMontadorMontaje actual = lista.get(i);
            if (actual.getIdMontador() == tmm.getIdMontador() && 
                actual.getIdMontaje() == tmm.getIdMontaje()) {
                
                lista.remove(i);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            guardarEnArchivo(lista);
            return 1; // Éxito
        }
        return -1; // No existía esa vinculación
    }

    @Override
    public boolean existeVinculacion(TMontadorMontaje tmm) {
        Collection<TMontadorMontaje> todas = leerTodas();
        
        for (TMontadorMontaje actual : todas) {
            // Si coincide tanto el montador como el montaje, es que ya están vinculados
            if (actual.getIdMontador() == tmm.getIdMontador() && 
                actual.getIdMontaje() == tmm.getIdMontaje()) {
                return true;
            }
        }
        return false;
    }
    
    private Collection<TMontadorMontaje> leerTodas() {
        // Para abrir montajes_empleados.json y parsear a lista de TMontadorMontaje
        return new ArrayList<TMontadorMontaje>();
    }

    private void guardarEnArchivo(Collection<TMontadorMontaje> lista) {
        // Lógica para convertir la lista a JSON
    }
}
