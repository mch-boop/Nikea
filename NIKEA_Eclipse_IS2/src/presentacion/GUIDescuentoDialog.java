package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIDescuentoDialog extends JDialog {
	
	// CONSTRUCTORA
	
	public GUIDescuentoDialog(JFrame owner) {
	    super(owner, "Gestión de Descuento", false);
	    setResizable(false);
	    setSize(850, 150);
	    setLocationRelativeTo(owner);
	    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    
	    initGUI();
	}
	
	// MÉTODOS
    
    private void initGUI() {
    	
    	JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
	    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnAlta      = new JButton("Alta descuento");
        JButton btnBaja      = new JButton("Baja descuento");
        JButton btnModificar = new JButton("Modificar descuento");
        JButton btnListar    = new JButton("Listar descuentos");
        JButton btnBuscar    = new JButton("Buscar descuento");
        JButton btnAnadir    = new JButton("Añadir descuento a factura");

        JButton[] botones = {btnAlta, btnBaja, btnModificar, btnListar, btnBuscar, btnAnadir};
        for (JButton b : botones) {
            b.setFocusPainted(false);
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);
        
        // Listeners
        
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_DESCUENTO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_DESCUENTO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnModificar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_DESCUENTO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnListar.addActionListener(e -> {
            Controlador.getInstance().accion(Eventos.MOSTRAR_DESCUENTOS, null);
        });

        btnBuscar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_DESCUENTO);
            abrirVistaBloqueante((JFrame) vista);
        });
                
        btnAnadir.addActionListener(e->{
        	IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ANADIR_DESCUENTO);
        	abrirVistaBloqueante((JFrame) vista);
        });
    }

    private void abrirVistaBloqueante(Window ventanaSecundaria) {
        this.setEnabled(false);
        ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                GUIDescuentoDialog.this.setEnabled(true);
                GUIDescuentoDialog.this.toFront();
            }
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                GUIDescuentoDialog.this.setEnabled(true);
            }
        });
        ventanaSecundaria.setVisible(true);
    }
}