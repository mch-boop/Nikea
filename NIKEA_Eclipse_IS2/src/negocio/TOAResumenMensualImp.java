package negocio;

import negocio.cliente.TCliente;
import negocio.empleado.TVendedor;
import negocio.servicio.TServicio;
import negocio.marca.TMarca;

public class TOAResumenMensualImp implements TOAResumenMensual {

	private TCliente mejorCliente;
	private TServicio mejorServicio;
	private TMarca mejorMarca;
	private TVendedor mejorVendedor;

	public TOAResumenMensualImp(TCliente cliente, TServicio servicio, TMarca marca, TVendedor vendedor) {
		this.mejorCliente = cliente;
		this.mejorServicio = servicio;
		this.mejorMarca = marca;
		this.mejorVendedor = vendedor;
	}

	@Override
	public TCliente getMejorCliente() {
		return mejorCliente;
	}

	@Override
	public TServicio getMejorServicio() {
		return mejorServicio;
	}

	@Override
	public TMarca getMejorMarca() {
		return mejorMarca;
	}

	@Override
	public TVendedor getMejorVendedor() {
		return mejorVendedor;
	}
}