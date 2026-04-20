package presentacion;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class GUIMarcaDialog extends JDialog {

	public GUIMarcaDialog(JFrame owner) {
        super(owner, "Gestión de Marca [SIN HACER]", false);
    	setSize(700, 150);
        setLocationRelativeTo(owner);
	}
}
