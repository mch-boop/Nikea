package integracion.empleado;

import negocio.empleado.TEmpleado;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class DAOEmpleadoImp implements DAOEmpleado {

    @Override
    public int write(TEmpleado te) {
        List<TEmpleado> lista = (List<TEmpleado>) readAll();
        
        // Si es un alta (id null o 0), generamos uno nuevo
        if (te.getId() == null || te.getId() <= 0) {
            int maxId = 0;
            for (TEmpleado e : lista) {
                if (e.getId() > maxId) maxId = e.getId();
            }
            te.setId(maxId + 1);
            lista.add(te);
        } else {
            // Modificación / Borrado Lógico: Reemplazamos el existente
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId().equals(te.getId())) {
                    lista.set(i, te);
                    break;
                }
            }
        }
        
        guardarEnArchivo(lista);
        return te.getId();
    }
    
    @Override
    public int update(TEmpleado te) {
        // "Si tiene ID, busca en la lista y reemplaza"
        return this.write(te);
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
        /*
           Al leer cada objeto del JSON, miramos el campo "tipo":
           - Si tipo == 1 -> instanciamos TVendedor y mapeamos 'numeroVentas'.
           - Si tipo == 2 -> instanciamos TMontador.
        */
        return new ArrayList<TEmpleado>(); // Retornar la lista real procesada
    }

    private void guardarEnArchivo(Collection<TEmpleado> lista) {
        // Convierte la lista a JSON y sobrescribe empleados.json
    }
}
