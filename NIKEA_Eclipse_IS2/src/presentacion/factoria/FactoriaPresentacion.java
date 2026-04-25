package presentacion.factoria;

import presentacion.IGUI;
import presentacion.GUICliente.*;
import presentacion.GUIEmpleado.*;
import presentacion.controlador.Eventos;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {
	
	// ATRIBUTOS PARA SINGLETON (EMPLEADO)
	private VistaAnadirEmpleado vistaAnadir;
	private VistaEliminarEmpleado vistaEliminar;
	private GUIBuscarIdModificar vistaBuscarIdModificar;
	private VistaModificarEmpleado vistaModificar;
	private VistaBuscarEmpleado vistaBuscar;
	private VistaMostrarEmpleados vistaMostrar;
		

	public IGUI createVista(int idEvento) {
		switch (idEvento) {
		
			// VISTAS DE CLIENTE
			case Eventos.ALTA_CLIENTE: 
				return new VistaAnadirCliente();
			case Eventos.BUSCAR_CLIENTE: 
				return new VistaBuscarCliente();
			case Eventos.BAJA_CLIENTE: 
				return new VistaEliminarCliente();
			case Eventos.MODIFICAR_CLIENTE:
				return new VistaModificarCliente();
			case Eventos.MOSTRAR_CLIENTES:
				return new VistaMostrarClientes();
			case Eventos.MOSTRAR_MEJOR_CLIENTE:
				return new VistaMostrarMejorCliente();
				
			// VISTAS DE EMPLEADO
			case Eventos.ALTA_EMPLEADO: 
				if (vistaAnadir == null) {
					vistaAnadir = new VistaAnadirEmpleado();
				}
				return vistaAnadir;
			case Eventos.BUSCAR_EMPLEADO: 
				if (vistaBuscar == null) { 
                    vistaBuscar = new VistaBuscarEmpleado();
                }
                return vistaBuscar;
			case Eventos.BAJA_EMPLEADO: 
				if (vistaEliminar == null) {
					vistaEliminar = new VistaEliminarEmpleado();
				}
				return vistaEliminar;
			case Eventos.VENTANA_BUSCAR_ID_EMPLEADO:
				if (vistaBuscarIdModificar == null) {
					vistaBuscarIdModificar = new GUIBuscarIdModificar();
				}
				return vistaBuscarIdModificar;
			case Eventos.MODIFICAR_EMPLEADO:
				if (vistaModificar == null) {
					vistaModificar = new VistaModificarEmpleado();
				}
				return vistaModificar;
			case Eventos.MOSTRAR_EMPLEADOS:
				if (vistaMostrar == null) {
					vistaMostrar = new VistaMostrarEmpleados();
				}
				return vistaMostrar;
				
			
			//... 
			default:
				// Error inesperado.
				return null;
		}
	}
}
