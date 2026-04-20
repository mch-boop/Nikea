package presentacion;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class GUIDescuentoDialog extends JDialog {
	
	public GUIDescuentoDialog(JFrame owner) {
        super(owner, "Gestión de Descuento [SIN HACER]", false);
    	setSize(700, 150);
        setLocationRelativeTo(owner);
	}
}
