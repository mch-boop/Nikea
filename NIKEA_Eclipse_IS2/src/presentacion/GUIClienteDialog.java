package presentacion;

import java.awt.Dialog;

import javax.swing.JFrame;

public class GUIClienteDialog extends Dialog {

	public GUIClienteDialog(JFrame owner) {
        super(owner, "Gestión de Cliente [SIN HACER]", false);
    	setSize(700, 150);
        setLocationRelativeTo(owner);
	}
}
