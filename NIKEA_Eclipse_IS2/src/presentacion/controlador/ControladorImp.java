package presentacion.controlador;

import java.util.Collection;

import negocio.cliente.SACliente;
import negocio.cliente.TCliente;
import negocio.descuento.SADescuento;
import negocio.descuento.TDescuento;
import negocio.empleado.SAEmpleado;
import negocio.empleado.SAMontadorMontaje;
import negocio.empleado.TEmpleado;
import negocio.empleado.TMontadorMontaje;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.SAFactura;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.marca.SAMarca;
import negocio.marca.TMarca;
import negocio.operacionTOA.OperacionResumenTOA;
import negocio.operacionTOA.TResumenNegocio;
import negocio.servicio.SAServicio;
import negocio.servicio.TArticulo;
import negocio.servicio.TServicio;
import presentacion.IGUI;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class ControladorImp extends Controlador {

	@Override
	public void accion(int evento, Object datos) {
		switch (evento) {

		// EVENTOS DE CLIENTE

		case Eventos.ALTA_CLIENTE: {
			TCliente tCliente = (TCliente) datos;
			SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
			int res = saCli.create(tCliente);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ALTA_CLIENTE_OK, res);
			} else if (res == -1) { // mismo cliente ya existente
				vista.actualizar(Eventos.RES_ALTA_CLIENTE_YA_EXISTE, null);
			} else {
				vista.actualizar(Eventos.RES_ALTA_CLIENTE_KO, tCliente);
			}
			break;
		}

		case Eventos.BAJA_CLIENTE: {
			Integer id = (Integer) datos;
			SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
			TCliente cli = saCli.read(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_CLIENTE);

			if (cli == null) {
				vista.actualizar(Eventos.RES_BAJA_CLIENTE_KO_NO_EXISTE, id);
			} else if (!cli.isActivo()) {
				vista.actualizar(Eventos.RES_BAJA_CLIENTE_KO_YA_INACTIVO, id);
			} else {
				vista.actualizar(Eventos.RES_BAJA_CLIENTE_OK, cli);
			}
			break;
		}

		case Eventos.CONFIRMAR_BAJA_CLIENTE: {
			Integer id = (Integer) datos;
			SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
			int res = saCli.delete(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_CLIENTE);

			if (res > 0) {
				vista.actualizar(Eventos.RES_BAJA_CLIENTE_CONFIRMADA, id);
			} else if (res == -3) {
				vista.actualizar(Eventos.RES_BAJA_CLIENTE_KO_NO_EXISTE, id);
			} else if (res == -4) {
				vista.actualizar(Eventos.RES_BAJA_CLIENTE_KO_YA_INACTIVO, id);
			} else {
				vista.actualizar(Eventos.RES_BAJA_CLIENTE_KO, id);
			}
			break;
		}

		case Eventos.BUSCAR_CLIENTE: {
			Integer id = (Integer) datos;
			SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
			TCliente cli = saCli.read(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_CLIENTE);

			if (cli != null) {
				vista.actualizar(Eventos.RES_BUSCAR_CLIENTE_OK, cli);
			} else {
				vista.actualizar(Eventos.RES_BUSCAR_CLIENTE_KO, id);
			}
			break;
		}

		case Eventos.MOSTRAR_CLIENTES: {
			SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
			Collection<TCliente> clientes = saCli.readAll();

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (clientes != null) {
				vista.actualizar(Eventos.RES_MOSTRAR_CLIENTES_OK, clientes);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_CLIENTES_KO, null);
			}
			break;
		}

		case Eventos.BUSCAR_CLIENTE_PARA_MODIFICAR: {
			Integer id = (Integer) datos;
			SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
			TCliente tc = saCli.read(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_CLIENTE);

			if (tc != null) {
				vista.actualizar(Eventos.RES_BUSCAR_CLIENTE_PARA_MODIFICAR_OK, tc);
			} else {
				vista.actualizar(Eventos.RES_MODIFICAR_CLIENTE_KO_NO_EXISTE, id);
			}
			break;
		}

		case Eventos.MODIFICAR_CLIENTE: {
			TCliente tc = (TCliente) datos;
			SACliente saCli = FactoriaAbstractaNegocio.getInstance().crearSACliente();
			int res = saCli.update(tc);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_MODIFICAR_CLIENTE_OK, res);
			} else if (res == -1) {
				vista.actualizar(Eventos.RES_MODIFICAR_CLIENTE_KO_NO_EXISTE, tc);
			}
			break;
		}

		// EVENTOS DE FACTURA

		case Eventos.INICIAR_VENTA: {
			TFactura tFactura = (TFactura) datos;
			SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();

			int res = saFactura.iniciarVenta(tFactura);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_INICIAR_VENTA_OK, res);
			} else {
				vista.actualizar(Eventos.RES_INICIAR_VENTA_KO, res);
			}
			break;
		}

		case Eventos.CERRAR_VENTA: {
			TFactura tFactura = (TFactura) datos;
			SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();

			int res = saFactura.cerrarVenta(tFactura);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_CERRAR_VENTA_OK, res);
			} else {
				vista.actualizar(Eventos.RES_CERRAR_VENTA_KO, res);
			}
			break;
		}

		case Eventos.BUSCAR_FACTURA: {
			Integer id = (Integer) datos;
			SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			TFactura factura = saFactura.mostrarPorId(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (factura != null) {
				vista.actualizar(Eventos.RES_BUSCAR_FACTURA_OK, factura);
			} else {
				vista.actualizar(Eventos.RES_BUSCAR_FACTURA_KO, id);
			}
			break;
		}

		case Eventos.MOSTRAR_FACTURAS: {
			SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			Collection<TFactura> facturas = saFactura.mostrarTodas();

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (facturas != null) {
				vista.actualizar(Eventos.RES_MOSTRAR_FACTURAS_OK, facturas);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_FACTURAS_KO, null);
			}
			break;
		}

		case Eventos.MOSTRAR_FACTURAS_CLIENTE: {
			Integer idCliente = (Integer) datos;
			SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			Collection<TFactura> facturasCliente = saFactura.mostrarPorCliente(idCliente);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (facturasCliente != null) {
				vista.actualizar(Eventos.RES_MOSTRAR_FACTURAS_CLIENTE_OK, facturasCliente);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_FACTURAS_CLIENTE_KO, idCliente);
			}
			break;
		}

		// EVENTOS DE SERVICIO

		case Eventos.ANNADIR_SERVICIO: {
			TLineaFactura tLinea = (TLineaFactura) datos;
			SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();

			int res = saFactura.annadirServicioAVenta(tLinea);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res == 1) {
				vista.actualizar(Eventos.RES_ANNADIR_SERVICIO_OK, res);
			} else if (res == -1) {
				vista.actualizar(Eventos.RES_ANNADIR_SERVICIO_KO_NO_VENTA, res);
			} else if (res == -2) {
				vista.actualizar(Eventos.RES_ANNADIR_SERVICIO_KO_NO_EXISTE, res);
			} else if (res == -3) {
				vista.actualizar(Eventos.RES_ANNADIR_SERVICIO_KO_INACTIVO, res);
			} else if (res == -4) {
				vista.actualizar(Eventos.RES_ANNADIR_SERVICIO_KO_PRECIO_INVALIDO, res);
			} else {
				vista.actualizar(Eventos.RES_ANNADIR_SERVICIO_KO, res);
			}
			break;
		}

		case Eventos.ELIMINAR_SERVICIO: {

			TLineaFactura tLinea = (TLineaFactura) datos;
			SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();

			int res = saFactura.eliminarServicioDeVenta(tLinea);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ELIMINAR_SERVICIO_OK, tLinea);
			} else if (res == -3) {
				vista.actualizar(Eventos.RES_ELIMINAR_SERVICIO_KO_BORRADO_DE_MAS, tLinea);
			} else if (res == -2) {
				vista.actualizar(Eventos.RES_ELIMINAR_SERVICIO_KO_NO_EXISTE, tLinea);
			} else {
				vista.actualizar(Eventos.RES_ELIMINAR_SERVICIO_KO, tLinea);
			}
			break;
		}

		case Eventos.ALTA_SERVICIO: {
			TServicio tServicio = (TServicio) datos;
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();

			int res = saServicio.create(tServicio);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ALTA_SERVICIO_OK, res);
			} else {
				switch (res) {
				case -1:
					vista.actualizar(Eventos.RES_ALTA_SERVICIO_YA_EXISTE_MISMO, null);
					break;
				case -100:
					vista.actualizar(Eventos.RES_ALTA_SERVICIO_YA_EXISTE_DISTINTO, null);
					break;
				case -2:
					vista.actualizar(Eventos.RES_ALTA_SERVICIO_REACTIVAR, null);
					break;
				case -3:
					vista.actualizar(Eventos.RES_ALTA_SERVICIO_CAMBIO_TIPO_REQUERIDO_INACTIVO, null);
					break;
				case -300:
					vista.actualizar(Eventos.RES_ALTA_SERVICIO_CAMBIO_TIPO_REQUERIDO_ACTIVO, null);
					break;
				default:
					vista.actualizar(Eventos.RES_ALTA_SERVICIO_KO, null);
					break;
				}
			}
			break;
		}

		case Eventos.REACTIVAR_SERVICIO: {
			TServicio tServicio = (TServicio) datos;
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();

			int res = saServicio.reactivate(tServicio);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_SERVICIO);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ALTA_SERVICIO_OK, res);
			} else {
				vista.actualizar(Eventos.RES_ALTA_SERVICIO_KO, null);
			}

			break;
		}

		case Eventos.BAJA_SERVICIO: {
			Integer id = (Integer) datos;
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();

			int estado = saServicio.readToDelete(id);
			TServicio servicio = saServicio.readActive(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (estado == -3) {
				vista.actualizar(Eventos.RES_BAJA_SERVICIO_KO_NO_EXISTE, id);
			} else if (estado == -4) {
				vista.actualizar(Eventos.RES_BAJA_SERVICIO_KO, id);
			} else if (servicio != null) {
				vista.actualizar(Eventos.RES_BAJA_SERVICIO_OK, servicio);
			} else {
				vista.actualizar(Eventos.RES_BAJA_SERVICIO_KO, id);
			}
			break;
		}

		case Eventos.BUSCAR_SERVICIO: {
			Integer id = (Integer) datos;
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();

			TServicio servicio = saServicio.readActive(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_SERVICIO);

			if (servicio != null) {
				vista.actualizar(Eventos.RES_BUSCAR_SERVICIO_OK, servicio);
			} else {
				vista.actualizar(Eventos.RES_BUSCAR_SERVICIO_KO, id);
			}

			break;
		}

		case Eventos.BUSCAR_SERVICIO_PARA_MODIFICAR: {
			Integer id = (Integer) datos;
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();

			TServicio servicio = saServicio.readActive(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_SERVICIO);

			if (servicio != null) {
				vista.actualizar(Eventos.RES_BUSCAR_SERVICIO_PARA_MODIFICAR_OK, servicio);
			} else {
				vista.actualizar(Eventos.RES_BUSCAR_SERVICIO_PARA_MODIFICAR_KO, id);
			}

			break;
		}

		case Eventos.CONFIRMAR_BAJA_SERVICIO: {
			Integer id = (Integer) datos;
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();
			int res = saServicio.delete(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_SERVICIO);
			if (res > 0) {
				vista.actualizar(Eventos.RES_BAJA_SERVICIO_CONFIRMADA, id);
			} else {
				vista.actualizar(Eventos.RES_BAJA_SERVICIO_KO, id);
			}
			break;
		}

		case Eventos.MOSTRAR_MEJOR_ARTICULO: {
			SAServicio saCli = FactoriaAbstractaNegocio.getInstance().crearSAServicio();

			TArticulo servicio = saCli.getMejorArticulo();

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (servicio != null) {
				vista.actualizar(Eventos.RES_MOSTRAR_MEJOR_ARTICULO_OK, servicio);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_MEJOR_ARTICULO_KO, null);
			}
			break;
		}

		case Eventos.MOSTRAR_SERVICIOS: {
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();
			Collection<TServicio> servicios = saServicio.readAll();

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (servicios != null && !servicios.isEmpty()) {
				vista.actualizar(Eventos.RES_MOSTRAR_SERVICIOS_OK, servicios);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_SERVICIOS_KO, null);
			}

			break;
		}

		case Eventos.MODIFICAR_SERVICIO: {
			TServicio tServicio = (TServicio) datos;
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();
			int res = saServicio.update(tServicio);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_SERVICIO);

			if (res > 0) {
				vista.actualizar(Eventos.RES_MODIFICAR_SERVICIO_OK, res);
			} else {
				vista.actualizar(Eventos.RES_MODIFICAR_SERVICIO_KO, tServicio);
			}
			break;
		}

		case Eventos.MOSTRAR_ARTICULOS_POR_MARCA: {
			Integer idMarca = (Integer) datos;
			SAServicio saServicio = FactoriaAbstractaNegocio.getInstance().crearSAServicio();
			Collection<TArticulo> res = saServicio.readArticulosPorMarca(idMarca);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_ARTICULOS_POR_MARCA);

			if (res.isEmpty()) {
				vista.actualizar(Eventos.RES_MOSTRAR_ARTICULOS_POR_MARCA_KO_NO_HAY_ARTICULOS, null);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_ARTICULOS_POR_MARCA_OK, res);
			}
			break;
		}

		// EVENTOS DE EMPLEADO

		case Eventos.ALTA_EMPLEADO: {
			TEmpleado tEmpleado = (TEmpleado) datos;
			SAEmpleado saEmpleado = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();

			int res = saEmpleado.create(tEmpleado);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ALTA_EMPLEADO_OK, res);
			} else {
				switch (res) {

				case -1: // ya existe activo (mismo empleado)
					vista.actualizar(Eventos.RES_ALTA_EMPLEADO_YA_EXISTE_MISMO, null);
					break;

				case -100: // DNI ya registrado (otra persona)
					vista.actualizar(Eventos.RES_ALTA_EMPLEADO_YA_EXISTE_DISTINTO, null);
					break;

				case -2: // existe inactivo con datos distintos
					vista.actualizar(Eventos.RES_ALTA_EMPLEADO_CONFIRMAR_REACTIVACION, tEmpleado);
					break;

				case -3: // inactivo mismo nombre distinto tipo
					vista.actualizar(Eventos.RES_ALTA_EMPLEADO_CAMBIO_TIPO_REQUERIDO_INACTIVO, null);
					break;

				case -300: // activo mismo nombre distinto tipo
					vista.actualizar(Eventos.RES_ALTA_EMPLEADO_CAMBIO_TIPO_REQUERIDO_ACTIVO, null);
					break;

				default:
					vista.actualizar(Eventos.RES_ALTA_EMPLEADO_KO, tEmpleado);
					break;
				}
			}
			break;
		}

		case Eventos.REACTIVAR_EMPLEADO: {
			TEmpleado t = (TEmpleado) datos;
			SAEmpleado sa = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();

			// El SA hace un update de los datos y cambiar activo a true
			int res = sa.reactivate(t);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_EMPLEADO);
			if (res > 0) {
				vista.actualizar(Eventos.RES_ALTA_EMPLEADO_OK, res);
			} else {
				vista.actualizar(Eventos.RES_ALTA_EMPLEADO_KO, res);
			}
			break;
		}

		case Eventos.BAJA_EMPLEADO: {
			Integer id = (Integer) datos;
			SAEmpleado saEmp = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();

			// El SA ya hace las comprobaciones (si es null devuelve -3, si es inactivo -4)
			int res = saEmp.readToDelete(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_EMPLEADO);

			if (res > 0) {
				vista.actualizar(Eventos.RES_BAJA_EMPLEADO_OK, saEmp.read(id));
			} else {
				switch (res) {
				case -3: // No existe
					vista.actualizar(Eventos.RES_BAJA_EMPLEADO_KO_NO_EXISTE, id);
					break;
				case -4: // Ya está inactivo
					vista.actualizar(Eventos.RES_BAJA_EMPLEADO_KO_YA_INACTIVO, id);
					break;
				default: // Error de escritura/persistencias
					vista.actualizar(Eventos.RES_BAJA_EMPLEADO_KO, id);
					break;
				}
			}
			break;
		}

		case Eventos.CONFIRMAR_BAJA_EMPLEADO: {
			Integer id = (Integer) datos;
			SAEmpleado saEmp = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();

			int res = saEmp.delete(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_EMPLEADO);
			if (res > 0) {
				// Avisamos a la vista de que el borrado lógico se completó
				vista.actualizar(Eventos.RES_BAJA_EMPLEADO_CONFIRMADA, id);
			} else {
				// Por si acaso hubiera un error de escritura en el último momento
				vista.actualizar(Eventos.RES_BAJA_EMPLEADO_KO, id);
			}
			break;
		}

		case Eventos.MODIFICAR_EMPLEADO: {
			TEmpleado tEmpleado = (TEmpleado) datos;
			SAEmpleado saEmp = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();
			int res = saEmp.update(tEmpleado);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_EMPLEADO);

			if (res > 0) {
				vista.actualizar(Eventos.RES_MODIFICAR_EMPLEADO_OK, res);
			} else if (res == -1) {
				vista.actualizar(Eventos.RES_MODIFICAR_EMPLEADO_KO_NO_EXISTE, tEmpleado);
			} else {
				vista.actualizar(Eventos.RES_MODIFICAR_EMPLEADO_KO_DATOS_INVALIDOS, tEmpleado);
			}
			break;
		}

		case Eventos.BUSCAR_EMPLEADO_PARA_MODIFICAR: {
			Integer id = (Integer) datos;
			SAEmpleado sa = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();

			// El SA ahora devuelve el Transfer solo si existe y está activo,
			// de lo contrario devuelve null.
			TEmpleado emp = sa.readActive(id);

			IGUI vBuscarId = FactoriaAbstractaPresentacion.getInstance()
					.createVista(Eventos.VENTANA_BUSCAR_ID_EMPLEADO);
			IGUI vModificar = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_EMPLEADO);

			if (emp != null) {
				vBuscarId.actualizar(Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_OK, id);
				vModificar.actualizar(Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_OK, emp);
			} else {
				vBuscarId.actualizar(Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_KO, id);
			}
			break;
		}

		case Eventos.BUSCAR_EMPLEADO: {
			Integer id = (Integer) datos;
			SAEmpleado saEmp = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();

			TEmpleado empleado = saEmp.readActive(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_EMPLEADO);

			if (empleado != null) {
				vista.actualizar(Eventos.RES_BUSCAR_EMPLEADO_OK, empleado);
			} else {
				vista.actualizar(Eventos.RES_BUSCAR_EMPLEADO_KO, id);
			}
			break;
		}

		case Eventos.MOSTRAR_EMPLEADOS: {
			SAEmpleado saEmp = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();
			Collection<TEmpleado> empleados = saEmp.readAll();

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);
			if (empleados != null) {
				vista.actualizar(Eventos.RES_MOSTRAR_EMPLEADOS_OK, empleados);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_EMPLEADOS_KO, null);
			}
			break;
		}

		// EVENTOS DE MONTADOR-MONTAJE

		case Eventos.VINCULAR_MONTADOR_MONTAJE: {
		    TMontadorMontaje tmm = (TMontadorMontaje) datos;
		    SAMontadorMontaje saMN = FactoriaAbstractaNegocio.getInstance().crearSAMontadorMontaje();

		    int res = saMN.vincular(tmm);

		    IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

		    if (res > 0) {
		        vista.actualizar(Eventos.RES_VINCULAR_MONTADOR_OK, res);

		    } else {
		        switch (res) {

		        case -1:
		            vista.actualizar(Eventos.RES_VINCULAR_MONTADOR_KO_NO_EXISTE_EMPLEADO, tmm.getIdMontador());
		            break;

		        case -2:
		            vista.actualizar(Eventos.RES_VINCULAR_MONTADOR_KO_YA_EXISTE, tmm);
		            break;

		        case -4:
		            vista.actualizar(Eventos.RES_VINCULAR_MONTADOR_KO_NO_ES_MONTADOR, tmm.getIdMontador());
		            break;

		        case -5:
		            vista.actualizar(Eventos.RES_VINCULAR_MONTADOR_KO_MONTAJE_NO_EXISTE, tmm.getIdMontaje());
		            break;

		        case -3:
		            vista.actualizar(Eventos.RES_VINCULAR_MONTADOR_KO, null);
		            break;

		        default:
		            vista.actualizar(Eventos.RES_VINCULAR_MONTADOR_KO, null);
		            break;
		        }
		    }
		    break;
		}

		case Eventos.DESVINCULAR_MONTADOR_MONTAJE: {
			TMontadorMontaje tmm = (TMontadorMontaje) datos;
			SAMontadorMontaje saMN = FactoriaAbstractaNegocio.getInstance().crearSAMontadorMontaje();
			int res = saMN.desvincular(tmm);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_DESVINCULAR_MONTADOR_OK, res);
			} else if (res == -1) {
				vista.actualizar(Eventos.RES_DESVINCULAR_MONTADOR_KO_RELACION_NO_EXISTE, null);
			} else if (res == -2) {
				vista.actualizar(Eventos.RES_DESVINCULAR_MONTADOR_KO_ID_NO_ENCONTRADO, null);
			} else {
				vista.actualizar(Eventos.RES_VINCULAR_MONTADOR_KO, null);
			}
			break;
		}

		// EVENTOS DE MARCA

		case Eventos.ALTA_MARCA: {
			TMarca tMarca = (TMarca) datos;
			SAMarca saMarca = FactoriaAbstractaNegocio.getInstance().crearSAMarca();

			int res = saMarca.create(tMarca);
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ALTA_MARCA_OK, res);
			} else if (res == -1) {
				vista.actualizar(Eventos.RES_ALTA_MARCA_YA_EXISTE, tMarca);
			} else if (res == -100) {
				vista.actualizar(Eventos.RES_ALTA_MARCA_REACTIVADA, res);
			} else {
				vista.actualizar(Eventos.RES_ALTA_MARCA_KO, res);
			}
			break;
		}

		case Eventos.BUSCAR_MARCA: {
			int id = (int) datos;
			SAMarca saMarca = FactoriaAbstractaNegocio.getInstance().crearSAMarca();

			TMarca tm = saMarca.read(id);
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (tm != null) {
				vista.actualizar(Eventos.RES_BUSCAR_MARCA_OK, tm);
			} else {
				vista.actualizar(Eventos.RES_BUSCAR_MARCA_KO, null);
			}
			break;
		}

		case Eventos.BAJA_MARCA: {
			int id = (int) datos;
			SAMarca saMarca = FactoriaAbstractaNegocio.getInstance().crearSAMarca();

			TMarca tm = saMarca.read(id);
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (tm != null) {
				if (!tm.getListaArticulos().isEmpty()) {
					vista.actualizar(Eventos.RES_BAJA_MARCA_KO_TIENE_ARTICULOS, null);
				} else {
					vista.actualizar(Eventos.RES_BAJA_MARCA_OK, tm);
				}
			} else {
				vista.actualizar(Eventos.RES_BAJA_MARCA_KO_NO_EXISTE, id);
			}
			break;
		}

		case Eventos.CONFIRMAR_BAJA_MARCA: {
			int id = (int) datos;
			SAMarca saMarca = FactoriaAbstractaNegocio.getInstance().crearSAMarca();

			int res = saMarca.delete(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_MARCA);

			if (res >= 0) {
				vista.actualizar(Eventos.RES_BAJA_MARCA_CONFIRMADA, res);
			} else {
				vista.actualizar(Eventos.RES_BAJA_MARCA_KO, res);
			}
			break;
		}

		case Eventos.BUSCAR_MARCA_PARA_MODIFICAR: {
			Integer id = (Integer) datos;
			SAMarca sa = FactoriaAbstractaNegocio.getInstance().crearSAMarca();
			TMarca tm = sa.read(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_MARCA);

			if (tm != null) {
				vista.actualizar(Eventos.RES_BUSCAR_MARCA_PARA_MODIFICAR_OK, tm);
			} else {
				vista.actualizar(Eventos.RES_MODIFICAR_MARCA_KO_NO_EXISTE, id);
			}
			break;
		}

		case Eventos.MODIFICAR_MARCA: {
			TMarca tm = (TMarca) datos;
			SAMarca sa = FactoriaAbstractaNegocio.getInstance().crearSAMarca();
			int res = sa.update(tm);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_MODIFICAR_MARCA_OK, res);
			} else if (res == -1) {
				vista.actualizar(Eventos.RES_MODIFICAR_MARCA_KO_NO_EXISTE, tm);
			} else if (res == -2) {
				vista.actualizar(Eventos.RES_MODIFICAR_MARCA_KO_INACTIVO, tm);
			} else if (res == -3) {
				vista.actualizar(Eventos.RES_MODIFICAR_MARCA_KO_NOMBRE_DUPLICADO, tm);
			} else {
				vista.actualizar(Eventos.RES_MODIFICAR_MARCA_KO_DATOS_INVALIDOS, tm);
			}
			break;
		}

		case Eventos.MOSTRAR_MARCAS: {
			SAMarca saMarca = FactoriaAbstractaNegocio.getInstance().crearSAMarca();
			Collection<TMarca> lista = saMarca.readAll();

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (lista != null) {
				vista.actualizar(Eventos.RES_MOSTRAR_MARCAS_OK, lista);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_MARCAS_KO, null);
			}
			break;
		}

		case Eventos.CARGAR_MARCAS_PARA_SERVICIO: {
			SAMarca saMarca = FactoriaAbstractaNegocio.getInstance().crearSAMarca();
			Collection<TMarca> lista = saMarca.readAll();

			IGUI vista = (IGUI) datos;

			if (lista != null && !lista.isEmpty()) {
				vista.actualizar(Eventos.RES_CARGAR_MARCAS_PARA_SERVICIO_OK, lista);
			} else {
				vista.actualizar(Eventos.RES_CARGAR_MARCAS_PARA_SERVICIO_KO, null);
			}
			break;
		}

		case Eventos.MOSTRAR_MARCAS_POR_ESPECIALIDAD: {
			TMarca.Especialidad esp = (TMarca.Especialidad) datos;
			SAMarca saMarca = FactoriaAbstractaNegocio.getInstance().crearSAMarca();
			Collection<TMarca> lista = saMarca.readPorEspecialidad(esp);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (lista != null) {
				vista.actualizar(Eventos.RES_MOSTRAR_MARCAS_POR_ESPECIALIDAD_OK, lista);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_MARCAS_POR_ESPECIALIDAD_KO, null);
			}
			break;
		}

		// EVENTOS DE DESCUENTO

		case Eventos.ALTA_DESCUENTO: {
			TDescuento tDescuento = (TDescuento) datos;
			SADescuento saDescuento = FactoriaAbstractaNegocio.getInstance().crearSADescuento();

			int res = saDescuento.create(tDescuento);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ALTA_DESCUENTO_OK, res);

			} else {
				switch (res) {

				case -1:
					vista.actualizar(Eventos.RES_ALTA_DESCUENTO_YA_EXISTE,
							saDescuento.readByCodigo(tDescuento.getCodigo()));
					break;

				case -2:
					vista.actualizar(Eventos.RES_ALTA_DESCUENTO_CONFIRMAR_REACTIVACION, tDescuento);
					break;

				case -3:
					vista.actualizar(Eventos.RES_ALTA_DESCUENTO_KO_CODIGO, tDescuento);
					break;

				case -4:
					vista.actualizar(Eventos.RES_ALTA_DESCUENTO_KO_PORCENTAJE, tDescuento);
					break;

				default:
					vista.actualizar(Eventos.RES_ALTA_DESCUENTO_KO, res);
					break;
				}
			}
			break;
		}

		case Eventos.REACTIVAR_DESCUENTO: {
			TDescuento tDescuento = (TDescuento) datos;
			SADescuento saDescuento = FactoriaAbstractaNegocio.getInstance().crearSADescuento();

			int res = saDescuento.reactivate(tDescuento);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_DESCUENTO);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ALTA_DESCUENTO_OK, res);
			} else {
				vista.actualizar(Eventos.RES_ALTA_DESCUENTO_KO, null);
			}
			break;
		}

		case Eventos.MOSTRAR_DESCUENTOS: {
			SADescuento saDescuento = FactoriaAbstractaNegocio.getInstance().crearSADescuento();
			Collection<TDescuento> lista = saDescuento.readAll();

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (lista != null) {
				vista.actualizar(Eventos.RES_MOSTRAR_DESCUENTOS, lista);
			} else {
				vista.actualizar(Eventos.RES_MOSTRAR_DESCUENTOS_KO, null);
			}
			break;
		}

		case Eventos.BUSCAR_PARA_BAJA_DESCUENTO: {
			int id = (int) datos;
			SADescuento saDescuento = FactoriaAbstractaNegocio.getInstance().crearSADescuento();

			TDescuento td = saDescuento.read(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_DESCUENTO);

			if (td != null) {
				vista.actualizar(Eventos.RES_BUSCAR_PARA_BAJA_DESCUENTO_OK, td);
			} else {
				vista.actualizar(Eventos.RES_BUSCAR_PARA_BAJA_DESCUENTO_KO, null);
			}
			break;
		}

		case Eventos.BAJA_DESCUENTO: {
			int id = (int) datos;
			SADescuento saDescuento = FactoriaAbstractaNegocio.getInstance().crearSADescuento();

			int res = saDescuento.delete(id);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res >= 0) {
				vista.actualizar(Eventos.RES_BAJA_DESCUENTO_OK, res);
			} else {
				vista.actualizar(Eventos.RES_BAJA_DESCUENTO_KO, res);
			}
			break;
		}

		case Eventos.BUSCAR_DESCUENTO: {
			int id = (int) datos;
			SADescuento saDescuento = FactoriaAbstractaNegocio.getInstance().crearSADescuento();
			TDescuento td = saDescuento.read(id);
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (td != null) {
				vista.actualizar(Eventos.RES_BUSCAR_DESCUENTO_OK, td);
			} else {
				vista.actualizar(Eventos.RES_BUSCAR_DESCUENTO_KO, null);
			}
			break;
		}

		case Eventos.MODIFICAR_DESCUENTO: {
			TDescuento td = (TDescuento) datos;
			SADescuento saDescuento = FactoriaAbstractaNegocio.getInstance().crearSADescuento();
			int res = saDescuento.update(td);
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res >= 0) {
				vista.actualizar(Eventos.RES_MODIFICAR_DESCUENTO_OK, res);
			} else if (res == -3) {
				vista.actualizar(Eventos.RES_MODIFICAR_DESCUENTO_KO_CODIGO, null);
			} else if (res == -4) {
				vista.actualizar(Eventos.RES_MODIFICAR_DESCUENTO_KO_PORCENTAJE, null);
			} else if (res == -1) {
				vista.actualizar(Eventos.RES_MODIFICAR_DESCUENTO_NO_ENCONTRADO, null);
			} else {
				vista.actualizar(Eventos.RES_MODIFICAR_DESCUENTO_KO, null);
			}
			break;
		}

		case Eventos.CARGAR_DESCUENTO_MODIFICAR: {
			int id = (int) datos;
			SADescuento sa = FactoriaAbstractaNegocio.getInstance().crearSADescuento();
			TDescuento td = sa.read(id);
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_DESCUENTO);

			if (td != null) {
				vista.actualizar(Eventos.RES_CARGAR_DESCUENTO_MOD_OK, td);
			} else {
				vista.actualizar(Eventos.RES_CARGAR_DESCUENTO_MOD_KO, null);
			}
			break;
		}

		case Eventos.ANNADIR_DESCUENTO_FACTURA: {
			int[] ids = (int[]) datos;
			int idFactura = ids[0];
			int idDescuento = ids[1];

			SAFactura saFactura = FactoriaAbstractaNegocio.getInstance().crearSAFactura();

			int res = saFactura.annadirDescuento(idFactura, idDescuento);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res > 0) {
				vista.actualizar(Eventos.RES_ANNADIR_DESCUENTO_FACTURA_OK, res);
			} else {
				switch (res) {
				case -1:
					vista.actualizar(Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO_FACTURA_NO_EXISTE, null);
					break;
				case -2:
					vista.actualizar(Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO_DESCUENTO_NO_EXISTE, null);
					break;
				case -3:
					vista.actualizar(Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO_REQUISITOS, null);
					break;
				case -5:
					vista.actualizar(Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO_YA_TIENE_DESCUENTO, null);
					break;
				default:
					vista.actualizar(Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO, res);
					break;
				}
			}
			break;
		}

		case Eventos.MOSTRAR_RESUMEN_MENSUAL: {

			int[] datosIn = (int[]) datos;
			int mes = datosIn[0];
			int anio = datosIn[1];

			OperacionResumenTOA op = FactoriaAbstractaNegocio.getInstance().crearOperacionResumenTOA();

			TResumenNegocio res = op.resumenShop(mes, anio);

			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			if (res != null) {
				vista.actualizar(Eventos.RES_RESUMEN_MENSUAL_OK, res);
			} else {
				vista.actualizar(Eventos.RES_RESUMEN_MENSUAL_KO, null);
			}

			break;
		}
		// DEFAULT
		default:
			System.err.println("Evento no reconocido: " + evento);
			break;
		}
	}
}
