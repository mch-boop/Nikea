package negocio.marca;

import java.util.Collection;
import java.util.ArrayList;

public class TMarca {
	
	// ATRIBUTOS
	
	public enum Especialidad {
	    MUEBLES,
	    ELECTRODOMESTICOS,
	    TECNOLOGIA,
	    ILUMINACION,
	    DECORACION,
	    JARDIN_EXTERIOR,
	    OFICINA,
	    OTROS;
	    
	    @Override
	    public String toString() {
	        switch (this) {
	            case MUEBLES: return "Muebles";
	            case ELECTRODOMESTICOS: return "Electrodomésticos";
	            case TECNOLOGIA: return "Tecnología";
	            case ILUMINACION: return "Iluminación";
	            case DECORACION: return "Decoración";
	            case JARDIN_EXTERIOR: return "Jardín / Exterior";
	            case OFICINA: return "Oficina";
	            case OTROS: return "Otros";
	            default: return super.toString();
	        }
	    }
	}
	
	private String nombre;
	private int idMarca;
	private Collection<Integer> listaArticulos; // lista de ids de articulos de la marca.
	private Collection<Especialidad> especialidades;
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
	public Collection<Especialidad> getEspecialidades() { return especialidades; }
	
	// Setters
	public void setNombre(String nom) { this.nombre = nom; }
	public void setId(int id) { this.idMarca = id; }
	public void setActivo(boolean ac) { this.activo = ac; }
	public void setListaArticulos(Collection<Integer> l) { this.listaArticulos = l; }
	public void setEspecialidades(Collection<Especialidad> l) { this.especialidades = l; }
	
	@Override
	public String toString() {
		return this.nombre.toUpperCase();
	}
}
