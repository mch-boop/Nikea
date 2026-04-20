package launcher;

import javax.swing.SwingUtilities;

import presentacion.factoria.*;
import presentacion.controlador.Controlador;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
            FactoriaAbstractaPresentacion factoria = FactoriaPresentacion.getInstance();
            //IGUI gui = factoria.crearGUI();
            //gui.iniciar();
        });
	}

}
