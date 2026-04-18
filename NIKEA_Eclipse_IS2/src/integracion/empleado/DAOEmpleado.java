package integracion.empleado;

import negocio.empleado.TEmpleado;
import java.util.Collection;

public interface DAOEmpleado {
    public int write(TEmpleado te);          // Crea o actualiza en el JSON
    public int update(TEmpleado te);		 // Para reactivar o modificar
    public TEmpleado read(int id);           // Busca por ID
    public TEmpleado readByDNI(String dni);  // Busca por DNI
    public Collection<TEmpleado> readAll();  // Lista todos los empleados
}
