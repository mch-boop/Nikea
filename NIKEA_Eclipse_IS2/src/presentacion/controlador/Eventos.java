package presentacion.controlador;

public class Eventos {
	
	// EVENTOS DE CLIENTE

	public static final int ALTA_CLIENTE = 101;
	public static final int RES_ALTA_CLIENTE_OK = 1011;
	public static final int RES_ALTA_CLIENTE_KO = 1010;
    public static final int RES_ALTA_CLIENTE_KO_TELEFONO = 10101;
	public static final int RES_ALTA_CLIENTE_KO_APELLIDO = 10102;
	public static final int RES_ALTA_CLIENTE_KO_NOMBRE = 10103;
	public static final int RES_ALTA_CLIENTE_KO_DNI = 10104;
	public static final int RES_ALTA_CLIENTE_YA_EXISTE_MISMO = 101051;
	public static final int RES_ALTA_CLIENTE_YA_EXISTE_DISTINTO = 101052;
	public static final int REACTIVAR_CLIENTE = 10111;
	public static final int RES_ALTA_CLIENTE_CONFIRMAR_REACTIVACION = 101111;

	
    public static final int BAJA_CLIENTE = 102;
    public static final int RES_BAJA_CLIENTE_OK = 1021;
    public static final int RES_BAJA_CLIENTE_KO = 1020;
    public static final int RES_BAJA_CLIENTE_KO_ID_VACIO = 10201;
    public static final int RES_BAJA_CLIENTE_KO_ID_FORMATO = 10202;
	public static final int RES_BAJA_CLIENTE_KO_NO_EXISTE = 10203;
	public static final int RES_BAJA_CLIENTE_KO_YA_INACTIVO = 10204;
	public static final int CONFIRMAR_BAJA_CLIENTE = 10211;
	public static final int RES_BAJA_CLIENTE_CONFIRMADA = 102111;
    
    public static final int MODIFICAR_CLIENTE = 103;
    public static final int RES_MODIFICAR_CLIENTE_OK = 1031;
    public static final int RES_MODIFICAR_CLIENTE_KO_NO_EXISTE = 10300;
    public static final int RES_MODIFICAR_CLIENTE_KO_DATOS_INVALIDOS = 10301;
    public static final int BUSCAR_CLIENTE_PARA_MODIFICAR = 10303; 
    public static final int VENTANA_BUSCAR_ID_CLIENTE = 103030;
    public static final int RES_BUSCAR_CLIENTE_PARA_MODIFICAR_OK = 103031;
    public static final int RES_BUSCAR_CLIENTE_PARA_MODIFICAR_KO = 103032;
    
    public static final int BUSCAR_CLIENTE = 104;
    public static final int RES_BUSCAR_CLIENTE_KO = 1040;
    public static final int RES_BUSCAR_CLIENTE_OK = 1041;
    
    public static final int MOSTRAR_CLIENTES = 105;
    public static final int RES_MOSTRAR_CLIENTES_OK = 1051;
    public static final int RES_MOSTRAR_CLIENTES_KO = 1050;

    public static final int MOSTRAR_MEJOR_CLIENTE = 106;
    public static final int RES_MOSTRAR_MEJOR_CLIENTE_OK = 1061;
    public static final int RES_BUSCAR_MEJOR_CLIENTE_PARA_MOSTRAR_OK = 1062;
    public static final int RES_MOSTRAR_MEJOR_CLIENTE_KO = 1060;
    
	// EVENTOS DE EMPLEADO
    
    public static final int ALTA_EMPLEADO = 201;
    public static final int RES_ALTA_EMPLEADO_OK = 2011;
	public static final int RES_ALTA_EMPLEADO_KO = 2010;
    public static final int RES_ALTA_EMPLEADO_KO_SUELDO = 20101;
	public static final int RES_ALTA_EMPLEADO_KO_APELLIDO = 20102;
	public static final int RES_ALTA_EMPLEADO_KO_NOMBRE = 20103;
	public static final int RES_ALTA_EMPLEADO_KO_DNI = 20104;
	public static final int RES_ALTA_EMPLEADO_YA_EXISTE_MISMO = 201051;
	public static final int RES_ALTA_EMPLEADO_YA_EXISTE_DISTINTO = 201052;
	public static final int REACTIVAR_EMPLEADO = 20111;
	public static final int RES_ALTA_EMPLEADO_CONFIRMAR_REACTIVACION = 201111;
	public static final int RES_ALTA_EMPLEADO_CAMBIO_TIPO_REQUERIDO_INACTIVO = 201061;
	public static final int RES_ALTA_EMPLEADO_CAMBIO_TIPO_REQUERIDO_ACTIVO = 201062;
	
    public static final int BAJA_EMPLEADO = 202;
    public static final int RES_BAJA_EMPLEADO_OK = 2021;
    public static final int RES_BAJA_EMPLEADO_KO = 2020;
    public static final int RES_BAJA_EMPLEADO_KO_ID_VACIO = 20201;
    public static final int RES_BAJA_EMPLEADO_KO_ID_FORMATO = 20202;
	public static final int RES_BAJA_EMPLEADO_KO_NO_EXISTE = 20203;
	public static final int RES_BAJA_EMPLEADO_KO_YA_INACTIVO = 20204;
	public static final int CONFIRMAR_BAJA_EMPLEADO = 20211;
	public static final int RES_BAJA_EMPLEADO_CONFIRMADA = 202111;
    
    public static final int MODIFICAR_EMPLEADO = 203;
    public static final int RES_MODIFICAR_EMPLEADO_OK = 2031;
    public static final int RES_MODIFICAR_EMPLEADO_KO_NO_EXISTE = 20301;
    public static final int RES_MODIFICAR_EMPLEADO_KO_DATOS_INVALIDOS = 20302;
    public static final int BUSCAR_EMPLEADO_PARA_MODIFICAR = 20303; 
    public static final int VENTANA_BUSCAR_ID_EMPLEADO = 203030;
    public static final int RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_OK = 203031;
    public static final int RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_KO = 203032;
    
    public static final int BUSCAR_EMPLEADO = 204;
    public static final int RES_BUSCAR_EMPLEADO_KO = 2040;
    public static final int RES_BUSCAR_EMPLEADO_OK = 2041;
    
    public static final int MOSTRAR_EMPLEADOS = 205;
    public static final int RES_MOSTRAR_EMPLEADOS_OK = 2051;
    public static final int RES_MOSTRAR_EMPLEADOS_KO = 2050;
    
    // Relación M a N
    public static final int VINCULAR_MONTADOR_MONTAJE = 206;
    public static final int RES_VINCULAR_MONTADOR_OK = 2061;
    public static final int RES_VINCULAR_MONTADOR_KO = 2060;
    public static final int RES_VINCULAR_MONTADOR_KO_NO_EXISTE_EMPLEADO = 20601;
    
    public static final int DESVINCULAR_MONTADOR_MONTAJE = 207;
    public static final int RES_DESVINCULAR_MONTADOR_OK = 2071;
    public static final int RES_DESVINCULAR_MONTADOR_KO = 2070;
    public static final int RES_DESVINCULAR_MONTADOR_KO_RELACION_NO_EXISTE = 20701;
    public static final int RES_DESVINCULAR_MONTADOR_KO_ID_NO_ENCONTRADO = 20702;
    
    
    // EVENTOS DE FACTURA
    
    public static final int INICIAR_VENTA = 301;
    public static final int RES_INICIAR_VENTA_OK = 3011;
	public static final int RES_INICIAR_VENTA_KO = 3010;
    
    public static final int CERRAR_VENTA = 302;
    public static final int RES_CERRAR_VENTA_OK = 3021;
	public static final int RES_CERRAR_VENTA_KO = 3020;
	
    public static final int ANNADIR_SERVICIO = 303;
    public static final int RES_ANNADIR_SERVICIO_OK = 3031;
    public static final int RES_ANNADIR_SERVICIO_KO = 3030;

    
    public static final int BUSCAR_FACTURA = 304;
    public static final int RES_BUSCAR_FACTURA_OK = 3041;
    public static final int RES_BUSCAR_FACTURA_KO = 3040;

    
    public static final int MOSTRAR_FACTURAS = 305;
    public static final int RES_MOSTRAR_FACTURAS_OK = 3051;
	public static final int RES_MOSTRAR_FACTURAS_KO = 3050;
	
    public static final int MOSTRAR_FACTURAS_CLIENTE = 306;
    public static final int RES_MOSTRAR_FACTURAS_CLIENTE_OK = 3061;
    public static final int RES_MOSTRAR_FACTURAS_CLIENTE_KO = 3060;

    
    
    // EVENTOS DE DESCUENTO
    
    public static final int ALTA_DESCUENTO = 401;
    public static final int RES_ALTA_DESCUENTO_OK = 4011;
    public static final int RES_ALTA_DESCUENTO_KO = 4010;
    public static final int RES_ALTA_DESCUENTO_KO_CODIGO = 40101;
    public static final int RES_ALTA_DESCUENTO_KO_PORCENTAJE = 40102;
    public static final int RES_ALTA_DESCUENTO_YA_EXISTE = 40105;
    public static final int RES_ALTA_DESCUENTO_CONFIRMAR_REACTIVACION = 40111;
    public static final int REACTIVAR_DESCUENTO = 40112;
    
    public static final int MOSTRAR_DESCUENTOS     = 402;
    public static final int RES_MOSTRAR_DESCUENTOS = 4021;
    public static final int RES_MOSTRAR_DESCUENTOS_KO = 4020;
    
    public static final int MODIFICAR_DESCUENTO = 403;
    public static final int CARGAR_DESCUENTO_MODIFICAR = 4032;
    public static final int RES_CARGAR_DESCUENTO_MOD_OK = 40321;
    public static final int RES_CARGAR_DESCUENTO_MOD_KO = 40320;
    public static final int RES_MODIFICAR_DESCUENTO_OK = 4031;
    public static final int RES_MODIFICAR_DESCUENTO_KO = 4030;
    public static final int RES_MODIFICAR_DESCUENTO_NO_ENCONTRADO = 40301;
    public static final int RES_MODIFICAR_DESCUENTO_KO_CODIGO = 40302;
    public static final int RES_MODIFICAR_DESCUENTO_KO_PORCENTAJE = 40303;
    
    public static final int BAJA_DESCUENTO = 404;
    public static final int RES_BAJA_DESCUENTO_OK = 4041;
    public static final int RES_BAJA_DESCUENTO_KO = 4040;
            
    public static final int BUSCAR_DESCUENTO = 405;
    public static final int RES_BUSCAR_DESCUENTO_OK = 4051;
    public static final int RES_BUSCAR_DESCUENTO_KO = 4050;
    
    public static final int ANADIR_DESCUENTO = 406;
    
    
    
	
    
	// EVENTOS DE MARCA
	
    public static final int ALTA_MARCA = 501;
    public static final int RES_ALTA_MARCA_OK = 5011;
    public static final int RES_ALTA_MARCA_REACTIVADA = 50111;
    public static final int RES_ALTA_MARCA_KO = 5010;
    public static final int RES_ALTA_MARCA_KO_NOMBRE = 50101;
    public static final int RES_ALTA_MARCA_YA_EXISTE = 50105;

    public static final int BAJA_MARCA = 502;
    public static final int RES_BAJA_MARCA_OK = 5021;
    public static final int RES_BAJA_MARCA_KO = 5020;
    public static final int RES_BAJA_MARCA_KO_ID_VACIO = 50201;
    public static final int RES_BAJA_MARCA_KO_ID_FORMATO = 50202;
    public static final int CONFIRMAR_BAJA_MARCA = 512;
    public static final int RES_BAJA_MARCA_CONFIRMADA = 5121;
    public static final int RES_BAJA_MARCA_KO_YA_INACTIVO = 51201;
    public static final int RES_BAJA_MARCA_KO_NO_EXISTE = 51202;

    public static final int MODIFICAR_MARCA = 503;
    public static final int RES_MODIFICAR_MARCA_OK = 5031;
    public static final int RES_MODIFICAR_MARCA_KO = 5030;
    public static final int RES_MODIFICAR_MARCA_KO_NO_EXISTE = 50301;
    public static final int RES_MODIFICAR_MARCA_KO_DATOS_INVALIDOS = 50302;
    public static final int BUSCAR_MARCA_PARA_MODIFICAR = 5032;
    public static final int RES_BUSCAR_MARCA_PARA_MODIFICAR_OK = 50321;
    public static final int RES_BUSCAR_MARCA_PARA_MODIFICAR_KO = 50320;
    public static final int VENTANA_BUSCAR_ID_MARCA = 5033;
    
    public static final int BUSCAR_MARCA = 504;
    public static final int RES_BUSCAR_MARCA_OK = 5041;
    public static final int RES_BUSCAR_MARCA_KO = 5040;

    public static final int MOSTRAR_MARCAS = 505;
    public static final int RES_MOSTRAR_MARCAS_OK = 5051;
    public static final int RES_MOSTRAR_MARCAS_KO = 5050;

    public static final int MOSTRAR_RANKING_MARCA = 506;
    public static final int RES_MOSTRAR_RANKING_MARCA_OK = 5061;
    public static final int RES_MOSTRAR_RANKING_MARCA_KO = 5060;
    
    
    // EVENTOS DE SERVICIO
    
    public static final int ALTA_SERVICIO = 601;
    public static final int RES_ALTA_SERVICIO_OK = 6011;
    public static final int RES_ALTA_SERVICIO_KO = 6010;
    public static final int RES_ALTA_SERVICIO_YA_EXISTE = 60105;

    public static final int BAJA_SERVICIO = 602;
    public static final int RES_BAJA_SERVICIO_OK = 6021;
    public static final int RES_BAJA_SERVICIO_KO = 6020;
    public static final int RES_BAJA_SERVICIO_KO_NO_EXISTE = 60203;
    public static final int CONFIRMAR_BAJA_SERVICIO = 6022;
    public static final int RES_BAJA_SERVICIO_CONFIRMADA = 60221;

    public static final int MODIFICAR_SERVICIO = 603;
    public static final int RES_MODIFICAR_SERVICIO_OK = 6031;
    public static final int RES_MODIFICAR_SERVICIO_KO = 6030;

    public static final int BUSCAR_SERVICIO_PARA_MODIFICAR = 6032;
    public static final int RES_BUSCAR_SERVICIO_PARA_MODIFICAR_OK = 60321;
    public static final int RES_BUSCAR_SERVICIO_PARA_MODIFICAR_KO = 60320;

    public static final int BUSCAR_SERVICIO = 604;
    public static final int RES_BUSCAR_SERVICIO_OK = 6041;
    public static final int RES_BUSCAR_SERVICIO_KO = 6040;

    public static final int MOSTRAR_SERVICIOS = 605;
    public static final int RES_MOSTRAR_SERVICIOS_OK = 6051;
    public static final int RES_MOSTRAR_SERVICIOS_KO = 6050;

    public static final int MOSTRAR_MEJOR_ARTICULO = 606;
    public static final int RES_MOSTRAR_MEJOR_ARTICULO_OK = 6061;
    public static final int RES_MOSTRAR_MEJOR_ARTICULO_KO = 6062;
  

    
    public static final int ORGANIZAR_MONTAJE = 607;
    public static final int RES_ORGANIZAR_MONTAJE_OK = 6071;
	
    
}
