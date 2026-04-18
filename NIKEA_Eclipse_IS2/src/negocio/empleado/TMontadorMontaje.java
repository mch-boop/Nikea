package negocio.empleado;

/**
 * Transfer que representa la relación M:N entre Montador y Montaje.
 * Se utiliza para gestionar las asignaciones de montadores a montajes específicos.
 */
public class TMontadorMontaje {

    // Atributos
    private int idMontador;
    private int idMontaje;

    // Constructores
    public TMontadorMontaje() {}

    public TMontadorMontaje(int idMontador, int idMontaje) {
        this.idMontador = idMontador;
        this.idMontaje = idMontaje;
    }

    // Getters y Setters
    public int getIdMontador() {
        return idMontador;
    }

    public void setIdMontador(int idMontador) {
        this.idMontador = idMontador;
    }

    public int getIdMontaje() {
        return idMontaje;
    }

    public void setIdMontaje(int idMontaje) {
        this.idMontaje = idMontaje;
    }

    @Override
    public String toString() {
        return "TMontadorMontaje [idMontador=" + idMontador + ", idMontaje=" + idMontaje + "]";
    }
}
