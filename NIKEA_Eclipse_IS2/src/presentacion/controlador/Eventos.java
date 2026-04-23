package presentacion.controlador;

public class Eventos {
	
	// EVENTOS DE CLIENTE

	public static final int ALTA_CLIENTE = 101;
	public static final int RES_ALTA_CLIENTE_OK = 1011;
	public static final int RES_ALTA_CLIENTE_KO = 1010;
	
    public static final int BAJA_CLIENTE = 102;
    public static final int RES_BAJA_CLIENTE_OK = 1021;
    public static final int RES_BAJA_CLIENTE_KO = 1020;
    public static final int RES_BAJA_CLIENTE_KO_NO_EXISTE = 10201;
    public static final int RES_BAJA_CLIENTE_KO_YA_INACTIVO = 10202;
    
    public static final int MODIFICAR_CLIENTE = 103;
    public static final int RES_MODIFICAR_CLIENTE_OK = 1031;
    public static final int RES_MODIFICAR_CLIENTE_KO = 1030;
    public static final int RES_MODIFICAR_CLIENTE_KO_NO_EXISTE = 10300;
    public static final int RES_MODIFICAR_CLIENTE_KO_DATOS_INVALIDOS = 10301;
    
    public static final int BUSCAR_CLIENTE = 104;
    
    public static final int MOSTRAR_CLIENTES = 105;
    public static final int RES_MOSTRAR_CLIENTES_OK = 1051;
    public static final int RES_MOSTRAR_CLIENTES_KO = 1050;

    
    public static final int MOSTRAR_MEJOR_CLIENTE = 106;
    
    public static final int MOSTRAR_FACTURAS_CLIENTE = 107;

    
    // EVENTOS DE FACTURA
	
	// EVENTOS DE SERVICIO
	
	// EVENTOS DE EMPLEADO
    
    public static final int ALTA_EMPLEADO = 201;
    public static final int RES_ALTA_EMPLEADO_OK = 2011;
	public static final int RES_ALTA_EMPLEADO_KO = 2010;
    public static final int RES_ALTA_EMPLEADO_KO_SUELDO = 20101;
	public static final int RES_ALTA_EMPLEADO_KO_APELLIDO = 20102;
	public static final int RES_ALTA_EMPLEADO_KO_NOMBRE = 20103;
	public static final int RES_ALTA_EMPLEADO_KO_DNI = 20104;
	public static final int RES_ALTA_EMPLEADO_YA_EXISTE = 20105;
	
    public static final int BAJA_EMPLEADO = 202;
    public static final int RES_BAJA_EMPLEADO_OK = 2021;
    public static final int RES_BAJA_EMPLEADO_KO = 2020;
    public static final int RES_BAJA_EMPLEADO_KO_ID_VACIO = 20201;
    public static final int RES_BAJA_EMPLEADO_KO_ID_FORMATO = 20202;
	public static final int RES_BAJA_EMPLEADO_KO_NO_EXISTE = 20203;
	public static final int RES_BAJA_EMPLEADO_KO_YA_INACTIVO = 20204;
    
    public static final int MODIFICAR_EMPLEADO = 203;
    public static final int RES_MODIFICAR_EMPLEADO_OK = 2031;
    public static final int RES_MODIFICAR_EMPLEADO_KO_NO_EXISTE = 20301;
    public static final int RES_MODIFICAR_EMPLEADO_KO_DATOS_INVALIDOS = 20302;
    
    public static final int BUSCAR_EMPLEADO = 204;
    public static final int RES_BUSCAR_EMPLEADO_KO = 2040;
    public static final int RES_BUSCAR_EMPLEADO_OK = 2041;
    
    public static final int MOSTRAR_EMPLEADOS = 205;
    public static final int RES_MOSTRAR_EMPLEADOS_OK = 2051;
    public static final int RES_MOSTRAR_EMPLEADOS_KO = 2050;

	
	// EVENTOS DE MARCA
	
	// EVENTOS DE DESCUENTO
    
}
