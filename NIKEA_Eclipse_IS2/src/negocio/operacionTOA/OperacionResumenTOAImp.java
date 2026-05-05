package negocio.operacionTOA;

import java.util.Collection;
import java.util.List;

import integracion.cliente.DAOCliente;
import integracion.empleado.DAOEmpleado;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.marca.DAOMarca;
import integracion.servicio.DAOServicio;
import negocio.cliente.TCliente;
import negocio.factura.TFactura;
import negocio.marca.TMarca;
import negocio.servicio.TServicio;

public class OperacionResumenTOAImp implements OperacionResumenTOA {

	private DAOFactura facturaDAO;
    private DAOCliente clienteDAO;
    private DAOServicio servicioDAO;
    private DAOMarca marcaDAO;
    private DAOEmpleado vendedorDAO;
    
    @Override
    public TResumenNegocio resumenShop() {

    	facturaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
        clienteDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
        servicioDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOServicio();
        marcaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();
        vendedorDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOEmpleado();
        
        Collection<TCliente> clientes = clienteDAO.readAll();
        Collection<TServicio> servicios = servicioDAO.readAll();
        List<TFactura> facturas = facturaDAO.leerTodas();
        Collection<TMarca> marcas = marcaDAO.readAll();

        return new TResumenNegocioImp(clientes, servicios, facturas, marcas);
    }
}
