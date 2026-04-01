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
	
	// MÉTODOS
	
	// getters, setters y toString opcional
}
