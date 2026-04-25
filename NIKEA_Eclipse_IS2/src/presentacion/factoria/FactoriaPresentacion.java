package presentacion.factoria;

import presentacion.IGUI;
import presentacion.GUICliente.*;
import presentacion.GUIEmpleado.*;
import presentacion.controlador.Eventos;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {
	
	private VistaBuscarEmpleado vistaBuscar;

	public IGUI createVista(int idEvento) {
		switch (idEvento) {
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
				
			case Eventos.ALTA_EMPLEADO: 
				return new VistaAnadirEmpleado();
			case Eventos.BUSCAR_EMPLEADO: 
				if (vistaBuscar == null) { 
                    vistaBuscar = new VistaBuscarEmpleado();
                }
                return vistaBuscar;
			case Eventos.BAJA_EMPLEADO: 
				return new VistaEliminarEmpleado();
			case Eventos.VENTANA_BUSCAR_ID_EMPLEADO:
			    return new GUIBuscarIdModificar();
			case Eventos.MODIFICAR_EMPLEADO:
				return new VistaModificarEmpleado();
			case Eventos.MOSTRAR_EMPLEADOS:
				return new VistaMostrarEmpleados();
				
			
			//... 
			default:
				// Error inesperado.
				return null;
		}
	}
}
