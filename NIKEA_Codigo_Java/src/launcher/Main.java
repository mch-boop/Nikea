package launcher;

import javax.swing.SwingUtilities;
import presentacion.GUIMain;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            // El main crea la GUI principal directamente
            GUIMain gui = new GUIMain();
            gui.setVisible(true);
        });
	}

}
