package integracion.servicio;

import java.util.Collection;

import negocio.servicio.TServicio;

public class DAOServicioImp implements DAOServicio {

	@Override
	public int create(TServicio tServicio) {
		 List<TServicio> lista = (List<TServicio>) readAll();
        
        if (tServicio.getId() == null || tServicio.getId() <= 0) {
            int maxId = 0;
            for (TServicio s : lista) {
                if (s.getId() > maxId) maxId = s.getId();
            }
            tServicio.setId(maxId + 1);
            lista.add(tServicio);
        } else {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId().equals(tServicio.getId())) {
                    lista.set(i, tServicio);
                    break;
                }
            }
        }
        
        guardarEnArchivo(lista);
        return tServicio.getId();
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TServicio read(int idServicio) {
 for (TServicio e : readAll()) {
            if (e.getId() == idServicio) return e;
        }		return null;
	}

	@Override
	public Collection<TServicio> readAll() {
		// TODO Auto-generated method stub
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
