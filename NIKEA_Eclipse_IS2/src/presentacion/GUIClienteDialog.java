package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIClienteDialog extends JDialog {
	
	// CONSTRUCTORA
	
	public GUIClienteDialog(JFrame owner) {
	    super(owner, "Gestión de Cliente", false);
	    setResizable(false); 
	    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    
	    initGUI();
	    setLocationRelativeTo(owner);
	}
	
	// MÉTODO INITGUI
	
	private void initGUI() {
		JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
	    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    JButton btnAlta          = new JButton("Alta Cliente");
	    JButton btnBaja          = new JButton("Baja Cliente");
	    JButton btnModificar     = new JButton("Modificar Cliente");
	    JButton btnListar        = new JButton("Listar Clientes");
	    JButton btnBuscar        = new JButton("Buscar Cliente");

	    JButton[] botones = {btnAlta, btnBaja, btnModificar, btnBuscar, btnListar };
	    for (JButton b : botones) {
	        b.setFocusPainted(false);
	        panel.add(b);
	    }

	    add(panel, BorderLayout.CENTER);
        pack();

        // Listeners de los botones.
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_CLIENTE);
            abrirVistaBloqueante((JDialog) vista); 
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_CLIENTE);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnModificar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_CLIENTE);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_CLIENTE);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnListar.addActionListener(e -> { 
            Controlador.getInstance().accion(Eventos.MOSTRAR_CLIENTES, null);
        });
                
	}

	private void abrirVistaBloqueante(JDialog vista) {
    	vista.setModal(true);
    	vista.setVisible(true);
    }
}