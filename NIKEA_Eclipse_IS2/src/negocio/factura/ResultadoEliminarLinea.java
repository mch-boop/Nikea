package negocio.factura;

public enum ResultadoEliminarLinea {
    OK, // se ha restado correctamente
    BORRADO_DE_MAS, // se intentó borrar más de lo que había
    NO_EXISTE, // el producto no estaba en la factura
    ERROR // no hay factura abierta o parámetro inválido
}
