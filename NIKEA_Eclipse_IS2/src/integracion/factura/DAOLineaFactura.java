package integracion.factura;

import java.util.List;
import negocio.factura.TLineaFactura; 

public interface DAOLineaFactura {
    
    public void create(TLineaFactura lineaFactura); //crea una nueva linea de factura
    
    public List<TLineaFactura> read(int idFactura); //devuelve todas las lineas de una factura
    
    public TLineaFactura readLine(Integer idFactura, Integer idProducto); //devuelve la linea de un producto concreto en una factura
    
    public void update(TLineaFactura lineaFactura); //hacer cambios
    
    public void deleteLine(Integer idFactura, Integer idProducto); //borrar una linea concreta de una factura
    
    public void deleteAll(Integer idFactura); //eliminar una por una todas las lineas de la factura
}

