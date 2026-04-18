package negocio.empleado;

public class TMontador extends TEmpleado {

	// Constructora
	public TMontador() {
        super();
        this.setTipo(2); // Identificador de tipo para Montador
    }

    // Constructor completo
    public TMontador(Integer id, String dni, String nombre, String apellido, Double sueldo, boolean activo) {
        super(id, dni, nombre, apellido, sueldo, activo, 2);
    }
    
    @Override
    public String toString() {
        return super.toString() + " -> TMontador";
    }
}
