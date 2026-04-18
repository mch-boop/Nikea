package integracion.empleado;

import negocio.empleado.TMontadorMontaje;

public interface DAOMontadorMontaje {
    public int vincular(TMontadorMontaje tmm);
    public int desvincular(TMontadorMontaje tmm);
    public boolean existeVinculacion(TMontadorMontaje tmm);
}
