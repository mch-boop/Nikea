package launcher;

import integracion.factoria.*;
import negocio.factoria.*;
import presentacion.factoria.*;

public class Main {

	public static void main(String[] args) {
		
		// FACTORÍAS DEL SISTEMA
		
		FactoriaAbstractaIntegracion fIntegracion = new FactoriaIntegracion();
		FactoriaAbstractaNegocio fNegocio = new FactoriaNegocio();
		FactoriaAbstractaPresentacion fPresentacion = new FactoriaPresentacion();
		
		System.out.println("Nikea");
	}

}
