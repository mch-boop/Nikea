package presentacion.factoria;

import presentacion.IGUI;
import presentacion.GUICliente.*;
import presentacion.controlador.Eventos;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	public IGUI createVista(int idEvento) {
		switch (idEvento) {
			case Eventos.ALTA_CLIENTE: 
				return new VistaAnadirCliente(); 
			case Eventos.BUSCAR_CLIENTE: 
				return new VistaBuscarCliente();
			case Eventos.BAJA_CLIENTE: 
				return new VistaEliminarCliente();
			//... 
			default:
				// Error inesperado.
				return null;
		}
	}
}
