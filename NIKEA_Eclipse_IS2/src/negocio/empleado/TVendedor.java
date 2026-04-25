package negocio.empleado;

import org.json.JSONObject;

public class TVendedor extends TEmpleado {
	
	// Atributos
	private Integer numeroVentas = 0;

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
    
    // Métodos de traducción de/a JSON
    @Override
    public JSONObject asJSON() {
        JSONObject obj = super.asJSON();
        // Añadimos lo específico de Vendedor
        obj.put("numeroVentas", this.numeroVentas);
        return obj;
    }

    @Override
    public void fromJSON(JSONObject obj) {
        this.numeroVentas = obj.optInt("numeroVentas", 0);
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
