package presentacion.factoria;

import presentacion.IGUI;

import presentacion.GUICliente.*;
import presentacion.GUIDescuento.*;
import presentacion.GUIEmpleado.*;
import presentacion.GUIFactura.*;
import presentacion.GUIMarca.*;
//import presentacion.GUIServicio.*;
import presentacion.controlador.Eventos;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {
	
	// ATRIBUTOS PARA SINGLETON (CLIENTE)
	
	private static VistaAnadirCliente vistaAnadirCliente;
	private static VistaEliminarCliente vistaEliminarCliente;
	private static VistaModificarCliente vistaModificarCliente;
	private static VistaBuscarCliente vistaBuscarCliente;
	private static VistaMostrarClientes vistaMostrarClientes;
	private static VistaMostrarMejorCliente vistaMostrarMejorCliente;
	
	// ATRIBUTOS PARA SINGLETON (EMPLEADO)
	
	private static VistaAnadirEmpleado vistaAnadirEmpleado;
	private static VistaEliminarEmpleado vistaEliminarEmpleado;
	private static GUIBuscarIdModificar vistaBuscarIdModificarEmpleado;
	private static VistaModificarEmpleado vistaModificarEmpleado;
	private static VistaBuscarEmpleado vistaBuscarEmpleado;
	private static VistaMostrarEmpleados vistaMostrarEmpleado;

	// ATRIBUTOS PARA SINGLETON (FACTURA)
	
	private static VistaIniciarVenta vistaIniciar;
	private static VistaCerrarVenta vistaCerrar;
	private static VistaMostrarFacturas vistaMostrarFacturas;

	// ATRIBUTOS PARA SINGLETON (MARCA)
		
	private static VistaAnadirMarca vistaAnadirMarca;
	private static VistaEliminarMarca vistaEliminarMarca;
	private static VistaModificarBuscarMarca vistaModificarBuscarMarca;
	private static VistaModificarMarca vistaModificarMarca;
	private static VistaBuscarMarca vistaBuscarMarca;
	private static VistaMostrarMarcas vistaMostrarMarcas;
	private static VistaMostrarMejorMarca vistaMostrarMejorMarca;

	// ATRIBUTOS PARA SINGLETON (DESCUENTO)
		
	private static VistaAltaDescuento vistaAltaDescuento;
	private static VistaMostrarDescuentos VistaMostrarDescuentos;
	
	// ATRIBUTOS PARA SINGLETON (SERVICIO)
	/*
	private static VistaAltaServicio vistaAltaServicio;
	private static VistaEliminarServicio vistaEliminarServicio;
	private static VistaMostrarServicios vistaMostrarServicios;
	*/

	public IGUI createVista(int idEvento) {
		switch (idEvento) {
		
			// VISTAS DE CLIENTE
		
			case Eventos.ALTA_CLIENTE: 
				if (vistaAnadirCliente == null) {
					vistaAnadirCliente = new VistaAnadirCliente();
				}
				return vistaAnadirCliente;
			case Eventos.BUSCAR_CLIENTE: 
				if (vistaBuscarCliente == null) {
					vistaBuscarCliente = new VistaBuscarCliente();
				}
				return vistaBuscarCliente;
			case Eventos.BAJA_CLIENTE: 
				if (vistaEliminarCliente == null) {
					vistaEliminarCliente = new VistaEliminarCliente();
				}
				return vistaEliminarCliente;
			case Eventos.MODIFICAR_CLIENTE:
				if (vistaModificarCliente == null) {
					vistaModificarCliente = new VistaModificarCliente();
				}
				return vistaModificarCliente;
			case Eventos.MOSTRAR_CLIENTES:
				if (vistaMostrarClientes == null) {
					vistaMostrarClientes = new VistaMostrarClientes();
				}
				return vistaMostrarClientes;
			case Eventos.MOSTRAR_MEJOR_CLIENTE:
				if (vistaMostrarMejorCliente == null) {
					vistaMostrarMejorCliente = new VistaMostrarMejorCliente();
				}
				return vistaMostrarMejorCliente;
			case Eventos.BUSCAR_CLIENTE_PARA_MODIFICAR:
				if (vistaModificarCliente == null) {
					vistaModificarCliente = new VistaModificarCliente();
				}
				return vistaModificarCliente;
				
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
			case Eventos.BUSCAR_MARCA: 
				if (vistaBuscarMarca == null) { 
                    vistaBuscarMarca = new VistaBuscarMarca();
                }
                return vistaBuscarMarca;
			case Eventos.BAJA_MARCA: 
				if (vistaEliminarMarca == null) { 
                    vistaEliminarMarca = new VistaEliminarMarca();
                }
                return vistaEliminarMarca;
			case Eventos.VENTANA_BUSCAR_ID_MARCA:
				if (vistaModificarBuscarMarca == null) {
					vistaModificarBuscarMarca = new VistaModificarBuscarMarca();
				}
				return vistaModificarBuscarMarca;
			case Eventos.MODIFICAR_MARCA:
				if (vistaModificarMarca == null) {
					vistaModificarMarca = new VistaModificarMarca();
				}
				return vistaModificarMarca;
			case Eventos.MOSTRAR_MARCAS:
				if (vistaMostrarMarcas == null) {
					vistaMostrarMarcas = new VistaMostrarMarcas();
				}
				return vistaMostrarMarcas;
			case Eventos.MOSTRAR_RANKING_MARCA:
				if (vistaMostrarMejorMarca == null) {
					vistaMostrarMejorMarca = new VistaMostrarMejorMarca();
				}
				return vistaMostrarMejorMarca;

			// VISTAS DE DESCUENTO
			case Eventos.ALTA_DESCUENTO:
				if(vistaAltaDescuento == null) {
					vistaAltaDescuento = new VistaAltaDescuento();
				}
				return vistaAltaDescuento;
				
			case Eventos.MOSTRAR_DESCUENTOS:
			    if (VistaMostrarDescuentos == null) {
			        VistaMostrarDescuentos = new VistaMostrarDescuentos();
			    }
			    return VistaMostrarDescuentos;
			// VISTAS DE SERVICIO
				/*
			case Eventos.ALTA_SERVICIO:
				if (vistaAltaServicio == null) {
					vistaAltaServicio = new VistaAltaServicio();
				}
				return vistaAltaServicio;
			case Eventos.BAJA_SERVICIO:
				if (vistaEliminarServicio == null) {
					vistaEliminarServicio = new VistaEliminarServicio();
				}
				return vistaEliminarServicio;
			case Eventos.MOSTRAR_SERVICIOS:
				if (vistaMostrarServicios == null) {
					vistaMostrarServicios = new VistaMostrarServicios();
				}
				return vistaMostrarServicios;
				*/
				
			default:
				// Error inesperado.
				return null;
		}
	}
}
