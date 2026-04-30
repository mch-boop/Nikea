package negocio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import integracion.cliente.DAOCliente;
import integracion.empleado.DAOEmpleado;
import integracion.factura.DAOFactura;
import integracion.marca.DAOMarca;
import integracion.servicio.DAOArticulo;
import integracion.servicio.DAOServicio;
import negocio.cliente.TCliente;
import negocio.empleado.TVendedor;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.marca.TMarca;
import negocio.servicio.TArticulo;
import negocio.servicio.TServicio;

public class SAResumenMensualImp implements SAResumenMensual {

    private DAOFactura facturaDAO;
    private DAOCliente clienteDAO;
    private DAOServicio servicioDAO;
    private DAOMarca marcaDAO;
    private DAOEmpleado vendedorDAO;

    @Override
    public TOAResumenMensual generarResumenMensual(int mes, int anio) {

        List<TFactura> facturas = facturaDAO.readByMes(mes, anio);

        Map<Integer, Double> gastoCliente = new HashMap<>();
        Map<Integer, Double> ventasServicio = new HashMap<>();
        Map<Integer, Double> ventasMarca = new HashMap<>();
        Map<Integer, Double> ventasVendedor = new HashMap<>();

        for (TFactura f : facturas) {

            double importe = f.getImporte();

            gastoCliente.put(f.getIdCliente(),
                gastoCliente.getOrDefault(f.getIdCliente(), 0.0) + importe);


            for (TLineaFactura l : f.getLineas()) {

                double subtotal = l.getSubtotal();

                // SERVICIO
                ventasServicio.put(l.getIdServicio(),
                    ventasServicio.getOrDefault(l.getIdServicio(), 0.0) + subtotal);

                int idProducto = l.getIdProducto();

                //TServicio p = DAOArticulo.read(idProducto);

                //int idMarca = p.getIdMarca();

                //ventasMarca.put(idMarca,
                    //ventasMarca.getOrDefault(idMarca, 0.0) + l.getSubtotal());
            }
        }

        TCliente mejorCliente = clienteDAO.read(getMax(gastoCliente));
        TServicio mejorServicio = servicioDAO.read(getMax(ventasServicio));
        TMarca mejorMarca = marcaDAO.read(getMax(ventasMarca));
        TVendedor mejorVendedor = (TVendedor) vendedorDAO.read(getMax(ventasVendedor));

        return new TOAResumenMensualImp(
            mejorCliente,
            mejorServicio,
            mejorMarca,
            mejorVendedor
        );
    }

    private Integer getMax(Map<Integer, Double> mapa) {
        Integer best = null;
        double max = -1;

        for (Map.Entry<Integer, Double> e : mapa.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                best = e.getKey();
            }
        }

        return best;
    }
}