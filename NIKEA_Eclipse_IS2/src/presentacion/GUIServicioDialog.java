package presentacion;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class GUIServicioDialog extends JDialog {

	public GUIServicioDialog(JFrame owner) {
        super(owner, "Gestión de Servicio [SIN HACER]", false);
    	setSize(700, 150);
        setLocationRelativeTo(owner);
	}
}
