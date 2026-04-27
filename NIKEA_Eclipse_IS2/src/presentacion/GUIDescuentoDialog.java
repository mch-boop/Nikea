package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIDescuentoDialog extends JDialog {
	
	//CONSTRUCTORA
	
	public GUIDescuentoDialog(JFrame owner) {
	    super(owner, "Gestión de Descuento", false);
	    setResizable(false);
	    setSize(850, 150);
	    setLocationRelativeTo(owner);
	    
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
            ((JFrame) vista).setVisible(true);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_DESCUENTO);
            ((JFrame) vista).setVisible(true);
        });

        btnModificar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_DESCUENTO);
            ((JFrame) vista).setVisible(true);
        });

        btnListar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_DESCUENTOS);
            ((JFrame) vista).setVisible(true);
        });

        btnBuscar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_DESCUENTO);
            ((JFrame) vista).setVisible(true);
        });
               
        btnAnadir.addActionListener(e->{
        	IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ANADIR_DESCUENTO);
        	((JFrame) vista).setVisible(true);
        });
    }
}
