package negocio.factura;

import java.util.Collection;

public class TFactura {

	// ATRIBUTOS

	private int idFactura;
	// private Fecha fecha;
	private int importe;
	
	private int idCliente;
	private int idEmpleado;
	private int idDescuento;
	
	private Collection<TLineaFactura> lineaFactura;
	
	private boolean activo;

	public void setId(int i) {
		// TODO Auto-generated method stub
		
	}

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getIdCliente() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFecha() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// MÉTODOS
	
	// getters, setters y toString opcional
}
