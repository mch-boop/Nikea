package presentacion.factoria;

import presentacion.IGUI;
import presentacion.GUICliente.*;
import presentacion.GUIEmpleado.*;
import presentacion.GUIFactura.*;
import presentacion.GUIMarca.*;
import presentacion.controlador.Eventos;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {
	
	// ATRIBUTOS PARA SINGLETON (EMPLEADO)
	
	private VistaAnadirEmpleado vistaAnadirEmpleado;
	private VistaEliminarEmpleado vistaEliminarEmpleado;
	private GUIBuscarIdModificar vistaBuscarIdModificarEmpleado;
	private VistaModificarEmpleado vistaModificarEmpleado;
	private VistaBuscarEmpleado vistaBuscarEmpleado;
	private VistaMostrarEmpleados vistaMostrarEmpleado;
	
	private VistaIniciarVenta vistaIniciar;
	private VistaCerrarVenta vistaCerrar;
	private VistaMostrarFacturas vistaMostrarFacturas;
		
	private VistaAnadirMarca vistaAnadirMarca;

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
				if (vistaAnadirEmpleado == null) {
					vistaAnadirEmpleado = new VistaAnadirEmpleado();
				}
				return vistaAnadirEmpleado;
			case Eventos.BUSCAR_EMPLEADO: 
				if (vistaBuscarEmpleado == null) { 
                    vistaBuscarEmpleado = new VistaBuscarEmpleado();
                }
                return vistaBuscarEmpleado;
			case Eventos.BAJA_EMPLEADO: 
				if (vistaEliminarEmpleado == null) {
					vistaEliminarEmpleado = new VistaEliminarEmpleado();
				}
				return vistaEliminarEmpleado;
			case Eventos.VENTANA_BUSCAR_ID_EMPLEADO:
				if (vistaBuscarIdModificarEmpleado == null) {
					vistaBuscarIdModificarEmpleado = new GUIBuscarIdModificar();
				}
				return vistaBuscarIdModificarEmpleado;
			case Eventos.MODIFICAR_EMPLEADO:
				if (vistaModificarEmpleado == null) {
					vistaModificarEmpleado = new VistaModificarEmpleado();
				}
				return vistaModificarEmpleado;
			case Eventos.MOSTRAR_EMPLEADOS:
				if (vistaMostrarEmpleado == null) {
					vistaMostrarEmpleado = new VistaMostrarEmpleados();
				}
				return vistaMostrarEmpleado;
			
			// VISTAS DE FACTURA
			
			case Eventos.INICIAR_VENTA:
				if(vistaIniciar == null) {
					vistaIniciar = new VistaIniciarVenta();
				}
				return vistaIniciar;
			case Eventos.CERRAR_VENTA:
				if(vistaCerrar == null) {
					vistaCerrar = new VistaCerrarVenta();
				}
				return vistaCerrar;
			case Eventos.MOSTRAR_FACTURAS:
				if(vistaMostrarFacturas == null) {
					vistaMostrarFacturas = new VistaMostrarFacturas();
				}
				return vistaMostrarFacturas;
			
			// VISTAS DE MARCA
				
			case Eventos.ALTA_MARCA: 
				if (vistaAnadirMarca == null) {
					vistaAnadirMarca = new VistaAnadirMarca();
				}
				return vistaAnadirMarca;
				
			//... 
			default:
				// Error inesperado.
				return null;
		}
	}
}
