package presentacion.GUIEmpleado;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import negocio.empleado.TEmpleado;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;

public class GUIConfirmarModificar extends JDialog {

	// ATRIBUTOS
	private boolean confirmado = false; 
	
	// CONSTRUCTORA
	public GUIConfirmarModificar(JFrame owner, TEmpleado datosViejos, TEmpleado datosNuevos) {
        super(owner, "Confirmar Cambios", true);
        initGUI(datosViejos, datosNuevos);
    }
	
	// MÉTODOS
	private void initGUI(TEmpleado viejo, TEmpleado nuevo) {
		
	}

}
