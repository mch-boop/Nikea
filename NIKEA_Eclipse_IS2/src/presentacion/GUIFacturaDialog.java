package presentacion;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class GUIFacturaDialog  extends JDialog  {

	public GUIFacturaDialog(JFrame owner) {
        super(owner, "Gestión de Factura [SIN HACER]", false);
    	setSize(700, 150);
        setLocationRelativeTo(owner);
	}
}
