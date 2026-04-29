package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIClienteDialog extends JDialog {
	
	// CONSTRUCTORA
	
	public GUIClienteDialog(JFrame owner) {
	    super(owner, "Gestión de Cliente", false);
	    setResizable(false); 
	    setSize(800, 150); 
	    setLocationRelativeTo(owner);
	    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    
	    initGUI();
	}
	
	// MÉTODO INITGUI
	
	private void initGUI() {
		JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
	    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    JButton btnAlta          = new JButton("Alta Cliente");
	    JButton btnBaja          = new JButton("Baja Cliente");
	    JButton btnModificar     = new JButton("Modificar Cliente");
	    JButton btnListar        = new JButton("Listar Clientes");
	    JButton btnMostrarMejor  = new JButton("Mejor Cliente");
	    JButton btnBuscar        = new JButton("Buscar Cliente");

	    JButton[] botones = {btnAlta, btnBaja, btnModificar, btnListar, btnMostrarMejor, btnBuscar};
	    for (JButton b : botones) {
	        b.setFocusPainted(false);
	        panel.add(b);
	    }

	    add(panel, BorderLayout.CENTER);
        

        // Listeners de los botones.
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_CLIENTE);
            abrirVistaBloqueante((JFrame) vista); 
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_CLIENTE);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnModificar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_CLIENTE);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_CLIENTE);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnListar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_CLIENTES);
            abrirVistaBloqueante((JFrame) vista);
        });
        
        btnMostrarMejor.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_MEJOR_CLIENTE);
            abrirVistaBloqueante((JFrame) vista);
        });
        
	}

    private void abrirVistaBloqueante(Window ventanaSecundaria) {
        this.setEnabled(false);

        ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                GUIClienteDialog.this.setEnabled(true);
                GUIClienteDialog.this.toFront();
            }
            
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                GUIClienteDialog.this.setEnabled(true);
            }
        });

        ventanaSecundaria.setVisible(true);
    }
}