package presentacion.controlador;

import negocio.cliente.SACliente;
import negocio.cliente.TCliente;
import negocio.factoria.FactoriaAbstractaNegocio;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class ControladorImp extends Controlador {
	
	public void accion(int evento, Object datos){
		switch (evento) {
			case Eventos.ALTA_CLIENTE: {
				TCliente tCliente = (TCliente) datos;
				SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				int res = saCli.create(tCliente); //saCli usaría el DAO de Clientes
	
				//Según el valor de res, se actualiza la vista de una manera u otra.
				//Si todo OK el aspecto es este (faltaría el else):
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_ALTA_CLIENTE_OK, res);
				//mostraría una ventana semipreparada informando
				//...
				break;
			} 
			//... 
		}
}
