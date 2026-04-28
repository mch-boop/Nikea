    <szpackage negocio.empleado;

import org.json.JSONObject;

public class TEmpleado {

    // Atributos 
    private Integer id;
    private String dni;
    private String nombre;
    private String apellido; 
    private Double sueldo;
    private boolean activo;
    private Integer tipo; // 1 para Vendedor, 2 para Montador 

    // Constructora por defecto
    public TEmpleado() { activo = true; }

    // Constructora completa (para el DAO al leer del JSON)
    public TEmpleado(Integer id, String dni, String nombre, String apellido, Double sueldo, boolean activo, Integer tipo) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sueldo = sueldo;
        this.activo = activo;
        this.tipo = tipo;
    }
    
    // Métodos traducción de/a JSON
    public JSONObject asJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("nombre", this.nombre);
        obj.put("apellido", this.apellido);
        obj.put("dni", this.dni);
        obj.put("sueldo", this.sueldo);
        obj.put("activo", this.activo);
        obj.put("tipo", this.tipo);
        return obj;
    }
    
    // Permite a las subclases extraer sus datos específicos del JSON
    public void fromJSON(JSONObject obj) {}

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDNI() {
        return dni;
    }

    public void setDNI(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return "TEmpleado [ID=" + id + ", DNI=" + dni + ", Nombre=" + nombre + 
               ", Apellido=" + apellido + ", Sueldo=" + sueldo + 
               ", Activo=" + activo + ", Tipo=" + tipo + "]";
    }
}
