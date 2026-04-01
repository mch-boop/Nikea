package presentacion.factoria;

import presentacion.GUICliente.VistaAnadirCl;
import presentacion.GUICliente.VistaBuscarCl;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	public IGUI createVista(int idEvento) {
		switch (idEvento) {
		case Evento.ALTA_CLIENTE: {
		return new VistaAnadirCl(); break;}
		case Evento.BUSCAR_CLIENTE: {
		return new VistaBuscarCl(); break;}
		case Evento.ELIMINAR_CLIENTE: {
		return new VistaEliminarCl(); break;}
		//... 
		}
	}
}
