package negocio;

import negocio.cliente.TCliente;
import negocio.empleado.TVendedor;
import negocio.servicio.TServicio;
import negocio.marca.TMarca;

public interface TOAResumenMensual {

    TCliente getMejorCliente();
    TServicio getMejorServicio();
    TMarca getMejorMarca();
    TVendedor getMejorVendedor();

}