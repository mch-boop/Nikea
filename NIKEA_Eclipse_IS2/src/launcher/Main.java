package launcher;

import javax.swing.SwingUtilities;

import presentacion.factoria.*;
import presentacion.GUIMain;
import presentacion.controlador.Controlador;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            // El main crea la GUI principal directamente
            GUIMain gui = new GUIMain();
            gui.setVisible(true);
        });
	}

}
