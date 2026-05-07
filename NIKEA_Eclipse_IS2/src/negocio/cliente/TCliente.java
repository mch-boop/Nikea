package negocio.cliente;

public class TCliente {
	
	// ATRIBUTOS
	
	private String DNI;
	private Integer idCliente;
	private String nombre;
	private String apellidos;
	private Integer telefono;
	private boolean activo;
	
	// MÉTODOS
	
	// Constructora por defecto
    public TCliente() { activo = true; }

    // Constructora completa (para el DAO al leer del JSON)
    public TCliente(Integer id, String dni, String nombre, String apellidos, Integer telefono, boolean activo) {
        this.idCliente = id;
        this.DNI = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.activo = activo;
    }
 
    
	// Getters
	public String getNombre() { return this.nombre; }
	public String getApellidos() { return this.apellidos; }
	public String getDNI() { return this.DNI; } 
	public Integer getTelefono() { return this.telefono; }
	public Integer getId() { return this.idCliente; }
	public boolean isActivo() { return this.activo; }
	
	// Setters
	public void setNombre(String nom) { this.nombre = nom; }
	public void setApellidos(String ap) { this.apellidos = ap; }
	public void setDNI(String dni) { this.DNI = dni; } 
	public void setTelefono(int tfno) { this.telefono = tfno; }
	public void setId(int id) { this.idCliente = id; }
	public void setActivo(boolean ac) { this.activo = ac; }
}
