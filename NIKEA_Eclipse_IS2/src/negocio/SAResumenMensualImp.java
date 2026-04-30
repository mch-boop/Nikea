package negocio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import integracion.cliente.DAOCliente;
import integracion.empleado.DAOEmpleado;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.marca.DAOMarca;
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

        // Inicializar DAOs
        facturaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
        clienteDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
        servicioDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOServicio();
        marcaDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOMarca();
        vendedorDAO = FactoriaAbstractaIntegracion.getInstance().crearDAOEmpleado();

        List<TFactura> facturas = facturaDAO.readByMes(mes, anio);

        Map<Integer, Double> gastoCliente = new HashMap<>();
        Map<Integer, Double> ventasServicio = new HashMap<>();
        Map<Integer, Double> ventasMarca = new HashMap<>();
        Map<Integer, Double> ventasVendedor = new HashMap<>();

        for (TFactura f : facturas) {

            double importe = f.getTotal();

            // CLIENTE
            gastoCliente.put(f.getIdCliente(),
                gastoCliente.getOrDefault(f.getIdCliente(), 0.0) + importe);

            // VENDEDOR
            ventasVendedor.put(f.getIdVendedor(),
                ventasVendedor.getOrDefault(f.getIdVendedor(), 0.0) + importe);

            // Asegurar que tiene líneas
            if (f.getLineas() == null || f.getLineas().isEmpty()) {
                f.setLineas(
                    FactoriaAbstractaIntegracion.getInstance()
                        .crearDAOLineaFactura()
                        .leerPorFactura(f.getId())
                );
            }

            for (TLineaFactura l : f.getLineas()) {

                double subtotal = l.getSubtotal();

                // SERVICIO
                ventasServicio.put(l.getIdServicio(),
                    ventasServicio.getOrDefault(l.getIdServicio(), 0.0) + subtotal);

                // MARCA
                int idProducto = l.getIdProducto();

                TArticulo art = (TArticulo) servicioDAO.read(idProducto);

                if (art != null) {
                	TMarca marca = (TMarca) art.getMarca();
                    int idMarca = marca.getId();

                    ventasMarca.put(idMarca,
                        ventasMarca.getOrDefault(idMarca, 0.0) + subtotal);
                }
            }
        }

        // Evitar nulls
        Integer idCliente = getMax(gastoCliente);
        Integer idServicio = getMax(ventasServicio);
        Integer idMarca = getMax(ventasMarca);
        Integer idVendedor = getMax(ventasVendedor);

        TCliente mejorCliente = (idCliente != null) ? clienteDAO.read(idCliente) : null;
        TServicio mejorServicio = (idServicio != null) ? servicioDAO.read(idServicio) : null;
        TMarca mejorMarca = (idMarca != null) ? marcaDAO.read(idMarca) : null;
        TVendedor mejorVendedor = (idVendedor != null) ? (TVendedor) vendedorDAO.read(idVendedor) : null;

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