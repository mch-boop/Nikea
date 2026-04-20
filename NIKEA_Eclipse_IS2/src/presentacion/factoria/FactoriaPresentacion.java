package presentacion.factoria;

import presentacion.IGUI;
import presentacion.GUICliente.*;
import presentacion.controlador.Eventos;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	public IGUI createVista(int idEvento) {
		switch (idEvento) {
			case Eventos.ALTA_CLIENTE: 
				return new VistaAnadirCl(); 
			case Eventos.BUSCAR_CLIENTE: 
				return new VistaBuscarCl();
			case Eventos.BAJA_CLIENTE: 
				return new VistaEliminarCl();
			//... 
			default:
				// Error inesperado.
				return null;
		}
	}
}
