package negocio.empleado;

import integracion.empleado.DAOMontadorMontaje;
import integracion.factoria.FactoriaIntegracion;

public class SAMontadorMontajeImp implements SAMontadorMontaje {

	@Override
	public int vincular(TMontadorMontaje tmm) {
		DAOMontadorMontaje daoMN = FactoriaIntegracion.getInstance().crearDAOMontadorMontaje();
		return daoMN.vincular(tmm);
	}

	@Override
	public int desvincular(TMontadorMontaje tmm) {
		DAOMontadorMontaje daoMN = FactoriaIntegracion.getInstance().crearDAOMontadorMontaje();
		return daoMN.desvincular(tmm);
	}
	
}
