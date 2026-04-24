package negocio.empleado;

import java.util.Collection;

public interface SAEmpleado {
	
	// Métodos CU básicos
    public int create(TEmpleado te);
    public int delete(int id);
    public int update(TEmpleado te);
    int reactivate(TEmpleado tEmpleado);
    public TEmpleado read(int id);
    public Collection<TEmpleado> readAll();
    
    // Métodos para la relación M:N (Montador-Montaje)
    public int vincularMontadorAMontaje(TMontadorMontaje tmm);
    public int desvincularMontadorDeMontaje(TMontadorMontaje tmm);
}
