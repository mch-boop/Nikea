package integracion.factura;

import java.util.List;
import negocio.factura.TLineaFactura; 

public interface DAOLineaFactura {
    
    public void crear(TLineaFactura lineaFactura); //crea una nueva linea de factura
    
    public List<TLineaFactura> leerPorFactura(int idFactura); //devuelve todas las lineas de una factura
    
    public TLineaFactura leerPorLinea(Integer idFactura, Integer idProducto); //devuelve la linea de un producto concreto en una factura
    
    public void actualizar(TLineaFactura lineaFactura); //hacer cambios
    
    public void eliminar(Integer idFactura, Integer idProducto); //borrar una linea concreta de una factura
    
    public void eliminarPorFactura(Integer idFactura); //eliminar una por una todas las lineas de la factura
}

