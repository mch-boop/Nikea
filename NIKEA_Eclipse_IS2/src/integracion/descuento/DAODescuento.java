package integracion.descuento;

import negocio.descuento.TDescuento;
import java.util.Collection;

public interface DAODescuento {
    
    public int create(TDescuento td);              	// Crea o actualiza en el JSON
    public int update(TDescuento td);              	// Para reactivar o modificar
    public TDescuento read(int id);                	// Busca por ID
    public TDescuento readByCodigo(String codigo); 	// Busca por código (equivalente al DNI)
    public Collection<TDescuento> readAll();   		// Lista todos los descuentos
    
}