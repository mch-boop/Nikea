package negocio.empleado;

public class TVendedor extends TEmpleado {
	
	// Atributos
	private Integer numeroVentas;

	// Constructora 
    public TVendedor() {
        super();
        this.setTipo(1); // Identificador de tipo para Vendedor
    }

    // Constructor completo 
    public TVendedor(Integer id, String dni, String nombre, String apellido, Double sueldo, boolean activo, Integer numeroVentas) {
        super(id, dni, nombre, apellido, sueldo, activo, 1);
        this.numeroVentas = numeroVentas;
    }

    // Getters y Setters
    public Integer getNumeroVentas() {
        return numeroVentas;
    }

    public void setNumeroVentas(Integer numeroVentas) {
        this.numeroVentas = numeroVentas;
    }
    
    @Override
    public String toString() {
        return super.toString() + " -> TVendedor [Ventas=" + numeroVentas + "]";
    }
}
