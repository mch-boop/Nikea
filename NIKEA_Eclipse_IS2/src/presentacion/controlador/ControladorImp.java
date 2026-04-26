package presentacion.controlador;

import java.util.Collection;

import javax.swing.JFrame;

import negocio.cliente.SACliente;
import negocio.cliente.TCliente;
import negocio.empleado.SAEmpleado;
import negocio.empleado.TEmpleado;
import negocio.marca.SAMarca;
import negocio.marca.TMarca;
import negocio.factoria.FactoriaAbstractaNegocio;
import presentacion.IGUI;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class ControladorImp extends Controlador {
	
	@Override
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

			    // El create devuelve el ID (>0) o un código de error (<0)
			    int res = saEmpleado.create(tEmpleado);

			    IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(evento);

			    if (res > 0) {
			        // Alta o reactivación exitosa
			        vista.actualizar(Eventos.RES_ALTA_EMPLEADO_OK, res);
			    } 
			    else {
			        switch (res) {
			            case -1: // Ya existe y está activo
			            	vista.actualizar(Eventos.RES_ALTA_EMPLEADO_YA_EXISTE_MISMO, saEmpleado.getUltimoDuplicado());
			                break;
			                
			            case -100: // Existe activo pero es OTRA persona (DNI ocupado)
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_YA_EXISTE_DISTINTO, saEmpleado.getUltimoDuplicado());
			                break;

			            case -2: // Existe inactivo con datos distintos
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_CONFIRMAR_REACTIVACION, saEmpleado.getUltimoDuplicado());
			                break;

			            case -3: // Existe inactivo, mismos datos pero distinto tipo
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_CAMBIO_TIPO_REQUERIDO_INACTIVO, saEmpleado.getUltimoDuplicado());
			                break;
			                
			            case -300: // Activo, mismo nombre, distinto tipo
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_CAMBIO_TIPO_REQUERIDO_ACTIVO, saEmpleado.getUltimoDuplicado());
			                break;

			            case -4: // DNI inválido
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_KO_DNI, tEmpleado);
			                break;

			            case -5: // Nombre inválido
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_KO_NOMBRE, tEmpleado);
			                break;

			            case -6: // Apellido inválido
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_KO_APELLIDO, tEmpleado);
			                break;

			            case -7: // Sueldo inválido
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_KO_SUELDO, tEmpleado);
			                break;

			            default: // Error genérico o fallo de persistencia
			                vista.actualizar(Eventos.RES_ALTA_EMPLEADO_KO, res);
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
			    
			    TEmpleado emp = saEmp.read(id); 
			    
			    IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_EMPLEADO);
			    
			    if (emp == null) {
			        // El empleado no existe en el sistema
			        vista.actualizar(Eventos.RES_BAJA_EMPLEADO_KO_NO_EXISTE, id);
			    } else if (!emp.isActivo()) {
			        // El empleado existe pero ya está de baja
			        vista.actualizar(Eventos.RES_BAJA_EMPLEADO_KO_YA_INACTIVO, id);
			    } else {
			        // El empleado existe y está activo: mandamos una vista para confirmar la baja
			        vista.actualizar(Eventos.RES_BAJA_EMPLEADO_OK, emp);
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
			    TEmpleado emp = sa.read(id);
			    
			    IGUI vBuscarId = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.VENTANA_BUSCAR_ID_EMPLEADO);
			    IGUI vModificar = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_EMPLEADO);

			    if (emp != null && emp.isActivo()) {
			        // Ocultamos la ventana pequeña de "Introduce ID"
			        ((JFrame)vBuscarId).setVisible(false);

			        // Pasamos los datos a la de Modificar
			        vModificar.actualizar(Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_OK, emp);
			        
			        
			        ((JFrame)vModificar).setVisible(true);
			        ((JFrame)vModificar).toFront(); // La traemos al frente
			    } else {
			        // Si no existe o está inactivo, avisamos a la pequeña para que muestre error
			        vBuscarId.actualizar(Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_KO, id);
			    }
			    break; 
			}
			
			case Eventos.BUSCAR_EMPLEADO: {
			    Integer id = (Integer) datos;
			    SAEmpleado saEmp = FactoriaAbstractaNegocio.getInstance().crearSAEmpleado();
			    TEmpleado empleado = saEmp.read(id);
			    
			    IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_EMPLEADO);
			    
			    // Si el empleado es null O no está activo, mandamos KO (No existe)
			    if (empleado != null && empleado.isActivo()) {
			        vista.actualizar(Eventos.RES_BUSCAR_EMPLEADO_OK, empleado);
			    } else {
			        // Se trata igual que si no existiera
			        vista.actualizar(Eventos.RES_BUSCAR_EMPLEADO_KO, id);
			    }
			    
			    ((JFrame)vista).setVisible(true);
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
			
			// EVENTOS DE MARCA
			
			case Eventos.ALTA_MARCA: {
			    TMarca tMarca = (TMarca) datos;
			    SAMarca saMarca = FactoriaAbstractaNegocio.getInstance().crearSAMarca();
			    int res = saMarca.create(tMarca);

			    IGUI vista = FactoriaAbstractaPresentacion.getInstance()
			            .createVista(evento);

			    if (res > 0) {
			        vista.actualizar(Eventos.RES_ALTA_MARCA_OK, res);
			    }
			    else if (res == -1) {
			        vista.actualizar(Eventos.RES_ALTA_MARCA_YA_EXISTE, tMarca);
			    }
			    else if (res == -2) {
			        vista.actualizar(Eventos.RES_ALTA_MARCA_REACTIVADA, tMarca);
			    }
			    else if (res == -3) {
			        vista.actualizar(Eventos.RES_ALTA_MARCA_KO_NOMBRE, tMarca);
			    }
			    else {
			        vista.actualizar(Eventos.RES_ALTA_MARCA_KO, res);
			    }

			    break;
			}
			
			// EVENTOS DE DESCUENTO
			
			default:
				System.err.println("Evento no reconocido: " + evento);
				break;
		}
	}
}
