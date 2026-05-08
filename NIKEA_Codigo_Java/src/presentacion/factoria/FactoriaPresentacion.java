package presentacion.factoria;

import presentacion.IGUI;

import presentacion.GUICliente.*;
import presentacion.GUIDescuento.*;
import presentacion.GUIEmpleado.*;
import presentacion.GUIFactura.*;
import presentacion.GUIMarca.*;
import presentacion.GUIServicio.*;
import presentacion.controlador.Eventos;
import presentacion.operacionResumenTOA.VistaOperacionResumen;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	// ATRIBUTOS PARA SINGLETON (CLIENTE)

	private static VistaAnadirCliente vistaAnadirCliente;
	private static VistaEliminarCliente vistaEliminarCliente;
	private static VistaModificarCliente vistaModificarCliente;
	private static VistaBuscarCliente vistaBuscarCliente;
	private static VistaMostrarClientes vistaMostrarClientes;

	// ATRIBUTOS PARA SINGLETON (EMPLEADO)

	private static VistaAnadirEmpleado vistaAnadirEmpleado;
	private static VistaEliminarEmpleado vistaEliminarEmpleado;
	private static GUIBuscarIdModificar vistaBuscarIdModificarEmpleado;
	private static VistaModificarEmpleado vistaModificarEmpleado;
	private static VistaBuscarEmpleado vistaBuscarEmpleado;
	private static VistaMostrarEmpleados vistaMostrarEmpleado;
	
	// ATRIBUTOS PARA SINGLETON (RELACIÓN M A N)
	
	private static VistaVincularMontadorMontaje vistaVincularMontadorMontaje;
	private static VistaDesvincularMontadorMontaje vistaDesvincularMontadorMontaje;

	// ATRIBUTOS PARA SINGLETON (FACTURA)

	private static VistaIniciarVenta vistaIniciar;
	private static VistaCerrarVenta vistaCerrar;
	private static VistaMostrarFacturas vistaMostrarFacturas;
	private static VistaAnnadirServicioFactura vistaAnnadirServicio;
	private static VistaEliminarServicioFactura vistaEliminarServicioFactura;	
	private static VistaMostrarFacturaPorId vistaMostrarFacturaPorId;
	private static VistaMostrarFacturasCliente vistaMostrarFacturasCliente;
	
	// ATRIBUTOS PARA SINGLETON (MARCA)

	private static VistaAnadirMarca vistaAnadirMarca;
	private static VistaEliminarMarca vistaEliminarMarca;
	private static VistaModificarMarca vistaModificarMarca;
	private static VistaBuscarMarca vistaBuscarMarca;
	private static VistaMostrarMarcas vistaMostrarMarcas;
	private static VistaMostrarMarcaPorEspecialidad vistaMostrarMejorMarca;

	// ATRIBUTOS PARA SINGLETON (DESCUENTO)

	private static VistaAltaDescuento vistaAltaDescuento;
	private static VistaMostrarDescuentos VistaMostrarDescuentos;
	private static VistaBajaDescuento vistaBajaDescuento;
	private static VistaBuscarDescuento vistaBuscarDescuento;
	private static VistaModificarDescuento vistaModificarDescuento;
	private static VistaAnnadirDescuento vistaAnnadirDescuento;

	// ATRIBUTOS PARA SINGLETON (SERVICIO)

	private static VistaAltaServicio vistaAltaServicio;
	private static VistaEliminarServicio vistaEliminarServicio;
	private static VistaBuscarServicio vistaBuscarServicio;
	private static VistaModificarServicio vistaModificarServicio;
	private static VistaMostrarServicios vistaMostrarServicios;
	private static VistaMostrarMejorArticulo vistaMostrarMejorArticulo;
	private static VistaMostrarArticulosPorMarca vistaMostrarArticulosPorMarca;
	
	// ATRIBUTOS PARA SINGLETON (RESUMEN MENSUAL)
	private static VistaOperacionResumen vistaOperacionResumen;

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

			// VISTAS DE LA RELACIÓN M A N
			
			case Eventos.VINCULAR_MONTADOR_MONTAJE:
				if (vistaVincularMontadorMontaje == null) {
					vistaVincularMontadorMontaje = new VistaVincularMontadorMontaje();
				}
				return vistaVincularMontadorMontaje;
			case Eventos.DESVINCULAR_MONTADOR_MONTAJE:
				if (vistaDesvincularMontadorMontaje == null) {
					vistaDesvincularMontadorMontaje = new VistaDesvincularMontadorMontaje();
				}
				return vistaDesvincularMontadorMontaje;
			
			// VISTAS DE FACTURA

			case Eventos.INICIAR_VENTA:
				if (vistaIniciar == null) {
					vistaIniciar = new VistaIniciarVenta();
				}
				return vistaIniciar;
			case Eventos.CERRAR_VENTA:
				if (vistaCerrar == null) {
					vistaCerrar = new VistaCerrarVenta();
				}
				return vistaCerrar;
			case Eventos.ANNADIR_SERVICIO:
				if (vistaAnnadirServicio == null) {
					vistaAnnadirServicio = new VistaAnnadirServicioFactura();
				}
				return vistaAnnadirServicio;
			case Eventos.ELIMINAR_SERVICIO:
				if (vistaEliminarServicioFactura == null) {
					vistaEliminarServicioFactura = new VistaEliminarServicioFactura();
				}
				return vistaEliminarServicioFactura;
			case Eventos.MOSTRAR_FACTURAS:
				if (vistaMostrarFacturas == null) {
					vistaMostrarFacturas = new VistaMostrarFacturas();
				}
				return vistaMostrarFacturas;
			case Eventos.BUSCAR_FACTURA:
				if (vistaMostrarFacturaPorId == null) {
					vistaMostrarFacturaPorId = new VistaMostrarFacturaPorId();
				}
				return vistaMostrarFacturaPorId;
			case Eventos.MOSTRAR_FACTURAS_CLIENTE:
				if (vistaMostrarFacturasCliente == null) {
					vistaMostrarFacturasCliente = new VistaMostrarFacturasCliente();
				}
				return vistaMostrarFacturasCliente;

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
			case Eventos.MOSTRAR_MARCAS_POR_ESPECIALIDAD:
				if (vistaMostrarMejorMarca == null) {
					vistaMostrarMejorMarca = new VistaMostrarMarcaPorEspecialidad();
				}
				return vistaMostrarMejorMarca;

			// VISTAS DE DESCUENTO
			case Eventos.ALTA_DESCUENTO:
				if (vistaAltaDescuento == null) {
					vistaAltaDescuento = new VistaAltaDescuento();
				}
				return vistaAltaDescuento;

			case Eventos.MOSTRAR_DESCUENTOS:
				if (VistaMostrarDescuentos == null) {
					VistaMostrarDescuentos = new VistaMostrarDescuentos();
				}
				return VistaMostrarDescuentos;
			case Eventos.BAJA_DESCUENTO:
				if (vistaBajaDescuento == null) {
					vistaBajaDescuento = new VistaBajaDescuento();
				}
				return vistaBajaDescuento;
			case Eventos.BUSCAR_DESCUENTO:
				if (vistaBuscarDescuento == null) {
					vistaBuscarDescuento = new VistaBuscarDescuento();
				}
				return vistaBuscarDescuento;
			case Eventos.MODIFICAR_DESCUENTO:
				if (vistaModificarDescuento == null) {
					vistaModificarDescuento = new VistaModificarDescuento();
				}
				return vistaModificarDescuento;
			case Eventos.ANNADIR_DESCUENTO_FACTURA:
				if (vistaAnnadirDescuento == null) {
					vistaAnnadirDescuento = new VistaAnnadirDescuento();
				}
				return vistaAnnadirDescuento;

			// VISTAS DE SERVICIO

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
			case Eventos.BUSCAR_SERVICIO:
				if (vistaBuscarServicio == null) {
					vistaBuscarServicio = new VistaBuscarServicio();
				}
				return vistaBuscarServicio;
			case Eventos.MODIFICAR_SERVICIO:
				if (vistaModificarServicio == null) {
					vistaModificarServicio = new VistaModificarServicio();
				}
				return vistaModificarServicio;
			case Eventos.MOSTRAR_MEJOR_ARTICULO:
				if (vistaMostrarMejorArticulo == null) {
					vistaMostrarMejorArticulo = new VistaMostrarMejorArticulo();
				}
				return vistaMostrarMejorArticulo;
			case Eventos.BUSCAR_SERVICIO_PARA_MODIFICAR:
				if (vistaModificarServicio == null) {
					vistaModificarServicio = new VistaModificarServicio();
				}
				return vistaModificarServicio;
			case Eventos.MOSTRAR_SERVICIOS:
				if (vistaMostrarServicios == null) {
					vistaMostrarServicios = new VistaMostrarServicios();
				}
				return vistaMostrarServicios;
			case Eventos.MOSTRAR_ARTICULOS_POR_MARCA:
				if (vistaMostrarArticulosPorMarca == null) {
					vistaMostrarArticulosPorMarca = new VistaMostrarArticulosPorMarca();
				}
				return vistaMostrarArticulosPorMarca;
			case Eventos.MOSTRAR_RESUMEN_MENSUAL:
				if(vistaOperacionResumen == null) {
					vistaOperacionResumen = new VistaOperacionResumen();
				}
				return vistaOperacionResumen;
			default:
				// Error inesperado.
				return null;
		}
	}
}
