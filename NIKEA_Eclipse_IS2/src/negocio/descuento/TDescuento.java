package negocio.descuento;

public class TDescuento {
	
	// ATRIBUTOS
	
	public TDescuento(boolean importe) {
		// TODO Auto-generated constructor stub
	}
	private int idDescuento;
	private int porcentaje;
	private String nombre;
	
	private boolean activo;
	
	public void setCodigo(String id) {
		this.idDescuento = Integer.parseInt(id);
	}
	public void setPorcentaje(String text) {
		this.porcentaje = Integer.parseInt(text);
		
	}
	public void setNombre(String text) {
		// TODO Auto-generated method stub
		
	}
	public void setImporteMin(Double value) {
		// TODO Auto-generated method stub
		
	}
	public void setProductosMin(Integer value) {
		// TODO Auto-generated method stub
		
	}
	public int getCodigo(String text) {
		return idDescuento;
	}
	public void getPorcentaje(String text) {
		// TODO Auto-generated method stub
		
	}
	public void getNombre(String text) {
		// TODO Auto-generated method stub
		
	}
	public void getImporteMin(Double value) {
		// TODO Auto-generated method stub
		
	}
	public void getProductosMin(Integer value) {
		// TODO Auto-generated method stub
		
	}
	
	
	// MÉTODOS
	
	// getters, setters y toString opcional
}
