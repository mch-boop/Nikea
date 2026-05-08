package presentacion.controlador;

public abstract class Controlador {

	private static Controlador instancia = null;
	
	static public Controlador getInstance() {
		if (instancia == null)
			instancia = new ControladorImp();
		return instancia;
	}
	
	public abstract void accion(int evento, Object datos);
	
}
