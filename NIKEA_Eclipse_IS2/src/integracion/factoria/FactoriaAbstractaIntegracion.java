package integracion.factoria;

import integracion.cliente.DAOCliente;
import integracion.empleado.DAOEmpleado;
import integracion.descuento.DAODescuento;
import integracion.marca.DAOMarca;
import integracion.factura.DAOFactura;
import integracion.empleado.DAOMontadorMontaje;
import integracion.servicio.DAOMontaje;

public abstract class FactoriaAbstractaIntegracion {
	private static FactoriaAbstractaIntegracion instancia;

    public static FactoriaAbstractaIntegracion getInstance() {
        if (instancia == null) {
            instancia = new FactoriaIntegracion();
        }
        return instancia;
    }

    public abstract DAOCliente crearDAOCliente();
    public abstract DAOMontaje crearDAOMontaje();
    public abstract DAOEmpleado crearDAOEmpleado();
    public abstract DAOMarca crearDAOMarca();
    public abstract DAODescuento crearDAODescuento();
    public abstract DAOFactura crearDAOFactura();
    public abstract DAOMontadorMontaje crearDAOMontadorMontaje();
}
