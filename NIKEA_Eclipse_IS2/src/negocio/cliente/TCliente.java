package negocio.cliente;

public class TCliente {
	
	// ATRIBUTOS
	
	private String DNI;
	private int idCliente;
	private String nombre;
	private String apellidos;
	private int telefono;
	private boolean activo;
	
	// MÉTODOS
	
	// Getters
	public String getNombre() { return this.nombre; }
	public String getApellidos() { return this.apellidos; }
	public String getDNI() { return this.DNI; } 
	public int getTelefono() { return this.telefono; }
	public int getId() { return this.idCliente; }
	public boolean isActivo() { return this.activo; }
	
	// Setters
	public void setNombre(String nom) { this.nombre = nom; }
	public void setApellidos(String ap) { this.apellidos = ap; }
	public void setDNI(String dni) { this.DNI = dni; } 
	public void setTelefono(int tfno) { this.telefono = tfno; }
	public void setID(int id) { this.idCliente = id; }
	public void setActivo(boolean ac) { this.activo = ac; }
}
