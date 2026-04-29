package negocio.factura;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.factura.DAOLineaFactura;
import integracion.empleado.DAOEmpleado;
import integracion.empleado.DAOMontadorMontaje;
import negocio.empleado.TEmpleado;
import negocio.empleado.TMontador;
import negocio.empleado.TMontadorMontaje;
import negocio.servicio.TServicio;

public class SAFacturaImp implements SAFactura {
	private DAOMontadorMontaje daoMontaje = FactoriaAbstractaIntegracion.getInstance().crearDAOMontadorMontaje();
	private TFactura facturaActual;
	private Map<Integer, Integer> servicioAMontador = new HashMap<>();
    private DAOEmpleado daoEmpleado =
        FactoriaAbstractaIntegracion.getInstance().crearDAOEmpleado();
	@Override
	public boolean iniciarVenta(TFactura factura) {

		if (factura == null || factura.getIdVendedor() <= 0)
			return false;

		facturaActual = new TFactura();
		facturaActual.setIdVendedor(factura.getIdVendedor());
		facturaActual.setLineas(new ArrayList<>());
		facturaActual.setCerrada(false);

		servicioAMontador.clear();
		return true;
	}
	
	//Está hecha la lógica para vincular y desvincular montadores, queda el resto.
	@Override
	public boolean añadirLinea(TLineaFactura linea, TServicio servicio, int idMontador) {
		if (facturaActual == null || linea == null || servicio == null)
	        return false;

	    facturaActual.getLineas().add(linea);

	    //if (esMontaje(servicio)) {

            Integer idMontador = obtenerMontadorDisponible();

            if (idMontador == null)
                return false;

            servicioAMontador.put(servicio.getId(), idMontador);

            TMontadorMontaje tm = new TMontadorMontaje(
                idMontador,
                servicio.getId()
            );

            if (!daoMontaje.existeVinculacion(tm)) {
                daoMontaje.vincular(tm);
            }
        //}
		return true;
		
	}

	public boolean eliminarLinea(TLineaFactura linea, TServicio servicio) {

        if (facturaActual == null || linea == null || servicio == null)
            return false;

        facturaActual.getLineas().remove(linea);

        if (esMontaje(servicio)) {

            Integer idMontador = servicioAMontador.get(servicio.getId());

            if (idMontador != null) {

                TMontadorMontaje tm = new TMontadorMontaje(
                    idMontador,
                    servicio.getId()
                );

                daoMontaje.desvincular(tm);
                servicioAMontador.remove(servicio.getId());
            }
        }

        return true;
    }
	@Override
	public int cerrarVenta(TFactura factura) {

		if (facturaActual == null)
			return -1;

		DAOFactura dao = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		DAOLineaFactura daoLinea = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		facturaActual.setIdCliente(factura.getIdCliente());
		facturaActual.setIdDescuento(factura.getIdDescuento());
		facturaActual.setFecha(factura.getFecha());

		double total = 0;

		for (TLineaFactura l : facturaActual.getLineas()) {
			total += l.getCantidad() * l.getPrecioUnitario();
		}

		facturaActual.setTotal(total);
		facturaActual.setCerrada(true);

		int id = dao.crear(facturaActual);
		facturaActual.setId(id);

		for (TLineaFactura l : facturaActual.getLineas()) {
			l.setIdFactura(id);
			daoLinea.crear(l);
		}

		facturaActual = null;
		servicioAMontador.clear();
		
		return id;
	}

	@Override
	public List<TFactura> mostrarTodas() {

		return FactoriaAbstractaIntegracion.getInstance().crearDAOFactura().leerTodas();
	}
	
	// Funciones Auxiliares
	
	private boolean esMontaje(TServicio servicio) {
        return servicio.getTipo() != null && servicio.getTipo() == 2;
    }

    private Integer obtenerMontadorDisponible() {

        for (TEmpleado e : daoEmpleado.readAll()) {

            if (e.getTipo()== 2 && e.isActivo()) {
                return e.getId();
            }
        }

        return null;
    }
}
