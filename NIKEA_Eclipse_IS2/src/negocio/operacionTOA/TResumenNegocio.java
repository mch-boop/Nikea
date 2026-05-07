package negocio.operacionTOA;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import negocio.cliente.TCliente;
import negocio.factura.TFactura;
import negocio.servicio.TServicio;

public class TResumenNegocio {
	private Collection<TCliente> clientes;
	private Collection<TServicio> servicios;
	private List<TFactura> facturas;
	private Collection<String> marcas;

	public TResumenNegocio(Collection<TCliente> clientes, Collection<TServicio> servicios, List<TFactura> facturas,
			Set<String> marcas2) {
		this.clientes = clientes;
		this.servicios = servicios;
		this.facturas = facturas;
		this.marcas = marcas2;
	}

	// getters
	 public int getTotalClientes() {
        return clientes != null ? clientes.size() : 0;
    }

	public int getTotalServicios() {
        return servicios != null ? servicios.size() : 0;
    }

	public int getTotalFacturas() {
        return facturas != null ? facturas.size() : 0;
    }

    public int getTotalMarcas() {
        return marcas != null ? marcas.size() : 0;
    }
	
}
