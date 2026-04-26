package negocio.marca;

import java.util.Collection;
import java.util.ArrayList;

public class TMarca {
	
	// ATRIBUTOS
	
	private String nombre;
	private int idMarca;
	private Collection<Integer> listaArticulos; // lista de ids de articulos de la marca.
	private boolean activo;
	
	// CONSTRUCTORA
	
	public TMarca() {
		listaArticulos = new ArrayList<>();
	}
	
	// MÉTODOS
	
	// Getters
	public String getNombre() { return this.nombre; }
	public int getId() { return this.idMarca; }
	public boolean isActivo() { return this.activo; }
	public Collection<Integer> getListaArticulos() { return new ArrayList<>(this.listaArticulos); }
	
	// Setters
	public void setNombre(String nom) { this.nombre = nom; }
	public void setId(int id) { this.idMarca = id; }
	public void setActivo(boolean ac) { this.activo = ac; }
	public void setListaArticulos(Collection<Integer> l) { this.listaArticulos = l; }

	@Override
	public String toString() {
		String lista = "";
		for (int x : listaArticulos) lista += x + " ";
		return "TMarca [ID: " + idMarca + "]\n" + 
				"nombre: " + nombre + '\n' +
				"activo: " + activo +
				"articulos: " + lista;
	}
}
