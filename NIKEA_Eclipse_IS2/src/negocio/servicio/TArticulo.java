package negocio.servicio;
import negocio.marca.TMarca;
public class TArticulo extends TServicio {
private TMarca marca;
private int ventas;



public void setMarca(TMarca marca) {
	this.marca = marca;
}

public Object getMarca() {
return marca;	 
}
}
