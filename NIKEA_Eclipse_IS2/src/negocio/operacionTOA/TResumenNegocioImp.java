package negocio.operacionTOA;

import java.util.Collection;
import java.util.List;

import negocio.cliente.TCliente;
import negocio.factura.TFactura;
import negocio.marca.TMarca;
import negocio.servicio.TServicio;

public class TResumenNegocioImp implements TResumenNegocio {
	private Collection<TCliente> clientes;
	private Collection<TServicio> servicios;
	private List<TFactura> facturas;
	private Collection<TMarca> marcas;

	public TResumenNegocioImp(Collection<TCliente> clientes, Collection<TServicio> servicios, List<TFactura> facturas,
			Collection<TMarca> marcas) {
		this.clientes = clientes;
		this.servicios = servicios;
		this.facturas = facturas;
		this.marcas = marcas;
	}

	// getters
	@Override
	 public int getTotalClientes() {
        return clientes != null ? clientes.size() : 0;
    }

	@Override
	public int getTotalServicios() {
        return servicios != null ? servicios.size() : 0;
    }

	@Override
	public int getTotalFacturas() {
        return facturas != null ? facturas.size() : 0;
    }

	@Override
    public int getTotalMarcas() {
        return marcas != null ? marcas.size() : 0;
    }
	
	// Getters
    public Collection<TCliente> getClientes() { return clientes; }
    public Collection<TServicio> getServicios() { return servicios; }
    public List<TFactura> getFacturas() { return facturas; }
    public Collection<TMarca> getMarcas() { return marcas; }
}
