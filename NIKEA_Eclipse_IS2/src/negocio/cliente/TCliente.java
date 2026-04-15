package negocio.cliente;

public class TCliente {
	
	// ATRIBUTOS
	
	private int idCliente;
	private String nombre;
	private String apellidos;
	private int telefono;
	private boolean activo;
	
	// MÉTODOS
	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellidos() {
		return this.apellidos;
	}
	
	public int getTelefono() {
		return this.telefono;
	}
	
	public int getID() {
		return this.id;
	}
	// getters, setters y toString opcional
}
