package negocio.empleado;

import java.util.Collection;

public interface SAEmpleado {
	// Métodos CU básicos
    public int altaEmpleado(TEmpleado te);
    public int bajaEmpleado(int id);
    public int modificarEmpleado(TEmpleado te);
    public TEmpleado consultarEmpleado(int id);
    public Collection<TEmpleado> listarEmpleados();
    
    // Métodos para la relación M:N (Montador-Montaje)
    public int vincularMontadorAMontaje(TMontadorMontaje tmm);
    public int desvincularMontadorDeMontaje(TMontadorMontaje tmm);
}
