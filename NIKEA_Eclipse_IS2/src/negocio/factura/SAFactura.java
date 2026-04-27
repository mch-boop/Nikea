package negocio.factura;

import java.util.List;

public interface SAFactura {
	public boolean iniciarVenta(TFactura factura);

    public int cerrarVenta(TFactura factura);

    public List<TFactura> mostrarTodas();
}
