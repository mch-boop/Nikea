package presentacion.controlador;

import negocio.cliente.SACliente;
import negocio.cliente.TCliente;
import negocio.empleado.SAEmpleado;
import negocio.empleado.TEmpleado;
import negocio.factoria.FactoriaAbstractaNegocio;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class ControladorImp extends Controlador {
	
	public void accion(int evento, Object datos){
		switch (evento) {
			
			// EVENTOS DE CLIENTE
		
			case Eventos.ALTA_CLIENTE: {
				TCliente tCliente = (TCliente) datos;
				SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				int res = saCli.create(tCliente); //saCli usaría el DAO de Clientes
	
				//Según el valor de res, se actualiza la vista de una manera u otra.
				//Si todo OK el aspecto es este (faltaría el else):
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Eventos.RES_ALTA_CLIENTE_OK, res);
				//mostraría una ventana semipreparada informando
				//...
				break;
			} 
			
			// EVENTOS DE FACTURA
			
			// EVENTOS DE SERVICIO
			
			// EVENTOS DE EMPLEADO
			
			case Eventos.ALTA_EMPLEADO: {
				TEmpleado tEmpleado = (TEmpleado) datos;
				SAEmpleado saEmpleado = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();
				int res = saEmpleado.create(tEmpleado); //saCli usaría el DAO de Clientes
	
				//Según el valor de res, se actualiza la vista de una manera u otra.
				//Si todo OK el aspecto es este (faltaría el else):
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Eventos.RES_ALTA_CLIENTE_OK, res);
				//mostraría una ventana semipreparada informando
				//...
				break;
			}
			
			case Eventos.BAJA_EMPLEADO: {
							
			}
			
			case Eventos.MODIFICAR_EMPLEADO: {
				
			}
			
			case Eventos.BUSCAR_EMPLEADO: {
				
			}
			
			// EVENTOS DE MARCA
			
			// EVENTOS DE DESCUENTO
		}
	}
}
